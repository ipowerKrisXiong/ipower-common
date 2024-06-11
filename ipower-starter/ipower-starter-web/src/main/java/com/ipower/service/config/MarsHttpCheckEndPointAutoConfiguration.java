package com.ipower.service.config;

import com.ipower.service.core.healthcheck.HttpCheckLiveMvcEndPoint;
import com.ipower.service.core.healthcheck.HttpCheckReadyMvcEndPoint;
import com.ipower.service.core.healthcheck.MarsHttpCheckEndpointProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动化配置 标准化http检查端点
 */
@Configuration
@ConditionalOnClass({EndpointAutoConfiguration.class})
@AutoConfigureAfter(EndpointAutoConfiguration.class)
@EnableConfigurationProperties(MarsHttpCheckEndpointProperties.class)
@ConditionalOnProperty(prefix = "com.mars.springcloud.httpcheck-endpoint", name = "enable",havingValue="true", matchIfMissing = true)
public class MarsHttpCheckEndPointAutoConfiguration {


    @Configuration
    public static class CreateHttpCheckMvcEndPointConfiguration {

        @Autowired
        HealthEndpoint healthEndpoint;

        @Bean
        public HttpCheckLiveMvcEndPoint httpCheckLiveMvcEndPoint(){
            return new HttpCheckLiveMvcEndPoint();
        }

        @Bean
        public HttpCheckReadyMvcEndPoint httpCheckReadyMvcEndPoint(){
            return new HttpCheckReadyMvcEndPoint(healthEndpoint);
        }

    }


}
