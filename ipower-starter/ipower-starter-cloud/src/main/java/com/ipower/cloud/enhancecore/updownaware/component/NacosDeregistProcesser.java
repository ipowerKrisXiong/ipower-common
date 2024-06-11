package com.ipower.cloud.enhancecore.updownaware.component;


import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 在容器关闭前第一个先去注册中心注销，然后等待x秒，让其它服务和网关感知，这样其它服务就不会再往这边路由，然后再关闭虚拟机
 * 采用springcotext容器生命周期管理类，用于在容器启动关闭的时候设置感知时间
 * 这里实现一个最高优先级的SmartLifecycle，监听容器关闭事件，在容器关闭之前第一事件进行nacos注销
 * springboot,springcloud有以下两个定义好的端点，可做扩展，两个端点暴漏后需要配置相应安全策略密码，最后只让内网可以调用
 * org.springframework.cloud.context.restart.RestartEndpoint, 调用后context.close执行，进行容器关闭
 * org.springframework.boot.actuate.context.ShutdownEndpoint, pause和resume调用后PauseHandler接口的实现
 * ServiceClosePrePauseWaiter就是扩展RestartEndpoint关闭容器后第一时间进行nacos注销
 *
 * Springboot Endpoint之二：Endpoint源码剖析参考：
 * https://blog.csdn.net/weixin_30877227/article/details/99446728
 *  */
//@Component
@Slf4j
public class NacosDeregistProcesser implements SmartLifecycle,Ordered {

    NacosDiscoveryProperties nacosDiscoveryProperties;


    public NacosDeregistProcesser(NacosDiscoveryProperties nacosDiscoveryProperties) {
        this.nacosDiscoveryProperties = nacosDiscoveryProperties;
    }

    //    //先写死 则正常下线感知时间只需要 eureka.client.registry-fetch-interval-seconds(本包配置为5S) + ribbon更新缓存时间(本包配置5S)+eurekaServerReadOnly缓存时间(eurekaServer配置0S)+容错5S）
//    private static final long  SPRINGCLOUD_AWARE_UNREGIST_MILSEC= (5+5+0+5)*1000L;

    //nacos下感知很快,一般只需要考虑ribbon重复拉取注册中心记录表的循环即可,再加3秒冗余
    //nacos默认注册心跳间隔是30S所以这儿注销后一般不会马上又被注册上  preserved.heart.beat.interval
    private static final long  SPRINGCLOUD_AWARE_UNREGIST_MILSEC= (5+3)*1000L;


    //避免方法被调用多次，做并发锁用
    AtomicBoolean running = new AtomicBoolean();


    //跟随spring容器自动启动关闭
    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        stop();
        callback.run();
    }

    //启动时候设置系统状态为running中
    @Override
    public void start() {
        running.set(true);
    }

    // 注册容器关闭钩子,检查到进程关闭信号进行优雅停机，主要用于除了preStop主动调用注销注册外，还有人直接用kill命令或其它异常状态导致的停机，可以在停机前主动从那nacos注销
    @Override
    public void stop() {
        deregisterAndSleep();
    }

    @Override
    public boolean isRunning() {
        return running.get();
    }

    //设置执行阶段，放到所有SmartLifecycle的第一阶段执行
    @Override
    public int getPhase() {
        return Integer.MIN_VALUE;
    }

    //如果是调用shutdown endpoint方式关闭，在执行smartlife前会有ContextClosedEvent抛出，这个执行在smartlife之前，所以必须实现这个，让这个先执行
    @EventListener({ContextClosedEvent.class})
    public void onApplicationEvent(ContextClosedEvent event) {
        this.stop();
    }

    //控制smartlife的顺序，这个放前面点，设置为负数最小值
    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    //注销服务
    public void deregisterAndSleep(){
        //避免重复请求多次执行
        if(running.get()){

            String ip = nacosDiscoveryProperties.getIp();
            String serviceName = nacosDiscoveryProperties.getService();
            int port = nacosDiscoveryProperties.getPort();

            //负1说明启动的时候没有获取到端口，nacosDiscoveryProperties.isRegisterEnabled()=false说明启动的时候没有去nacos注册，则不用执行注销，直接返回
            if(port==-1 || !nacosDiscoveryProperties.isRegisterEnabled()){
                return;
            }

            log.info("容器关闭前注销微服务{}----------------------------执行中！",serviceName+" "+ip+":"+port);

            //namingService不是springbean,无法直接注入得到

            try {
                NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
                if(namingService!=null){
                    namingService.deregisterInstance(serviceName,ip,port);
                }
            } catch (NacosException e) {
                log.error("容器关闭前注销微服务----------------------------调用注册中心接口失败！"+e.getMessage(), e);
            }
            //调用注册中心注销完毕
            running.set(false);

            //睡x秒，等待其他服务感应微服务下线
            try {
                Thread.sleep(SPRINGCLOUD_AWARE_UNREGIST_MILSEC);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("容器关闭前注销微服务----------------------------完成！");

        }
    }
}
