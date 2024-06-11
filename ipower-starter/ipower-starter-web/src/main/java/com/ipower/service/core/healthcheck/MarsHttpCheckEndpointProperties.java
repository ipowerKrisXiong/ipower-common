package com.ipower.service.core.healthcheck;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.mars.springcloud.httpcheck-endpoint")
public class MarsHttpCheckEndpointProperties {

    /**e
     * 打开自定义http检查端点，可用于LB检查，K8S检查等
     */
    private boolean enable=true;

}
