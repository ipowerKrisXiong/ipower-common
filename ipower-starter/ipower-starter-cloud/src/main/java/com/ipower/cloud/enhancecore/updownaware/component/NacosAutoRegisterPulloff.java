package com.ipower.cloud.enhancecore.updownaware.component;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.annotation.Order;

/**
 * 推迟到后面application完全Ready后再注册
 * 在启动的时候检查其它容器中其它中间件是否已完全就绪，完全就绪才让nacos进行注册，否则不让推迟到后面再注册
 * InstancePreRegisteredEvent事件是
 * org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration#onApplicationEvent(WebServerInitializedEvent（web服务初始化完成)
 * 容器初始化事件抛出后，NacosAutoServiceRegistration作为AbstractAutoServiceRegistration的实现子类，里面会执行bind，并调用AbstractAutoServiceRegistration.start方法
 *
 * 可在NacosAutoServiceRegistration自动执行之前先设置不让启动，后置再启动
 *
 * https://blog.csdn.net/shin627077/article/details/122664152
 * https://blog.csdn.net/BruceLiu_code/article/details/126088029
 *
 */
@Order
@Slf4j
public class NacosAutoRegisterPulloff implements SmartApplicationListener {

    NacosDiscoveryProperties nacosDiscoveryProperties;

    HealthEndpoint healthEndpoint;


    public NacosAutoRegisterPulloff(NacosDiscoveryProperties nacosDiscoveryProperties, HealthEndpoint healthEndpoint) {
        this.nacosDiscoveryProperties = nacosDiscoveryProperties;
        this.healthEndpoint = healthEndpoint;
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if(eventType ==  ServletWebServerInitializedEvent.class){
            return true;
        }else{
            return false;
        }
    }

    //设置顺序，要保证比NacosAutoServiceRegistration监听到WebServerInitializedEvent事件早，方便前置处理
    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if(event instanceof ServletWebServerInitializedEvent){
            //设置状态,推迟nacosAotu自动注册,NacosApplicationReadyRegister的时候再去注册
            log.info("推迟nacos注册到applicationReady阶段---------------");
            nacosDiscoveryProperties.setRegisterEnabled(false);
        }

    }
}
