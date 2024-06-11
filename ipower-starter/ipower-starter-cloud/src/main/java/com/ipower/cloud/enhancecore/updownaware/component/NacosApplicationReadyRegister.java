package com.ipower.cloud.enhancecore.updownaware.component;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * NacosAutoRegisterPulloff 会推迟nacos自动注册到整个application启动完毕后再注册，保证其它组件以已经处于health可用状态
 * https://www.oomake.com/question/15635997
 *
 */
@Slf4j
public class NacosApplicationReadyRegister implements ApplicationListener<ApplicationReadyEvent> {

    private NacosAutoServiceRegistration registration;

    private NacosDiscoveryProperties nacosDiscoveryProperties;

    private HealthEndpoint healthEndpoint;

    public NacosApplicationReadyRegister(NacosDiscoveryProperties nacosDiscoveryProperties,NacosAutoServiceRegistration registration, HealthEndpoint healthEndpoint) {
        this.registration = registration;
        this.healthEndpoint = healthEndpoint;
        this.nacosDiscoveryProperties=nacosDiscoveryProperties;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        // 判断所有服务为健康状态
        if(Status.UP.equals(healthEndpoint.health().getStatus())){
            checkHealthAndRegist(false);
        //否则用一个异步线程去启动自动注册
        }else{
            log.info("application 启动完毕, 尝试nacos注册,健康状态!=UP，准备10秒后重试------------！");
            Thread checkHeathToRegister = new Thread(new Runnable() {
                @Override
                public void run() {
                    int retryTimes = 0;
                    final int RETRY_TIMES_MAX=90;//重试90次，总共15分钟
                    while(retryTimes<RETRY_TIMES_MAX && !registration.isRunning()){
                        //10s重新检查1次
                        try {
                            Thread.sleep(10000L);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        //重新检查健康程度，再次注册
                        checkHealthAndRegist(true);
                        //如果已经启动，则跳出循环,结束
                        if(registration.isRunning()){
                            break;
                        }else{
                            retryTimes++;
                            if(retryTimes==RETRY_TIMES_MAX){
                                log.info("尝试nacos注册，健康状态!=UP，已重试"+retryTimes+"次，放弃nacos注册------------!");
                            }else{
                                log.info("尝试nacos注册，健康状态!=UP，已重试"+retryTimes+"次,等待10S后重试------------!");
                            }
                        }
                    }
                }
            });
            //启动异步线程进行nacos自动注册重试
            checkHeathToRegister.start();
        }
    }


    void checkHealthAndRegist(Boolean recheck){
        if(Status.UP.equals(healthEndpoint.health().getStatus())){
            if(!registration.isRunning()){
                nacosDiscoveryProperties.setRegisterEnabled(true);
                registration.start();
                log.info("application 启动完毕，健康状态全部=UP，启动nacos注册------------！");
            }
        }
    }

}
