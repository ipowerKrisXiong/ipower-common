package com.ipower.service.config;

import com.ipower.service.core.cors.MarsCorsFilterProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * 这个跨域过滤器目前在http下在zuul下运行良好
 * 但是如果在https下还有问题的话，请参考：
 * https://segmentfault.com/a/1190000015870281
 * cors协议分简单请求和复杂请求，需要做不同处理，参考下面这篇
 * https://zhuanlan.zhihu.com/p/66789473
 */
@EnableConfigurationProperties(MarsCorsFilterProperties.class)
@Configuration
public class CrosFilterConfiguration {


    @ConditionalOnProperty(value = "mars.cors.filter.corsdomains[0]", matchIfMissing = false)
    @ConditionalOnMissingBean
    @Bean
    public FilterRegistrationBean corsFilter(MarsCorsFilterProperties marsCorsFilterProperties) {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        final CorsConfiguration config = new CorsConfiguration();

        List<String> allowOrigins = marsCorsFilterProperties.getFilter().getCorsdomains();
        if(allowOrigins!=null&&allowOrigins.size()>0){
            //跨域设置
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            config.setAllowCredentials(true);
            //添加支持多个跨域
            for(String allowOrigin : allowOrigins){
                if("*".equals(allowOrigin)){
                    //config.addAllowedOrigin("*");
//              config.addAllowedOrigin("http://192.168.0.116:8081");
                    config.addAllowedOrigin("*");
                }else{
                    String httpAllowOrigin = "http://"+allowOrigin;
                    String httpsAllowOrigin = "https://"+allowOrigin;
                    config.addAllowedOrigin(httpAllowOrigin);
                    config.addAllowedOrigin(httpsAllowOrigin);
                }
            }
        }

        //这个请求头在https中会出现,但是有点问题
        //config.addExposedHeader("X-forwared-port, X-forwarded-host");
        //设置过滤器
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new org.springframework.web.filter.CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;

    }




}
