package com.ipower.cloud.enhancecore.updownaware.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.stereotype.Component;

/**
 * 因为默认的springboot的shutdown端点如果被外部调用，会马上进行context.doClose
 * 这里再加一个只从注册中心注销而不关闭容器的钩子.
 * k8s如果想关闭直接closeContext可以调用shutdown端口，否则也可以调用prestop，最终杀pod的时候才会关闭容器
 * 发送的http类型要用delete
 */
@Component
@Endpoint(id = "prestop")
@Slf4j
public class ShutdownPreStopEndpoint {

    private final NacosDeregistProcesser nacosDeregistWaiter;

    public ShutdownPreStopEndpoint(NacosDeregistProcesser nacosDeregistWaiter) {
        this.nacosDeregistWaiter = nacosDeregistWaiter;
    }

    private final String success="success";

    /**
     * 如果加了参数@Selector，访问路径会变为 http://localhost:8088/actuator/prestop/{arg0}，必须带上参数，否则最后会报404
     * 且Selector只能往String转，否则会报错
//     * @param seconds
     * @return
     */
    @DeleteOperation
//    public String deregisterAndSleep(@Selector String seconds) {
    public String deregisterAndSleep(){
        nacosDeregistWaiter.deregisterAndSleep();
        return success;
    }

}
