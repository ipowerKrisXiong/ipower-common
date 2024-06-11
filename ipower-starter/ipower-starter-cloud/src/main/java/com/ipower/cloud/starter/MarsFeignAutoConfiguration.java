package com.ipower.cloud.starter;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import com.ipower.cloud.enhancecore.feign.config.EnhanceFeignErrorDecoder;
import com.ipower.cloud.enhancecore.feign.config.MarsFeignProperties;
import com.ipower.cloud.enhancecore.updownaware.component.NacosApplicationReadyRegister;
import com.ipower.cloud.enhancecore.updownaware.component.NacosAutoRegisterPulloff;
import com.ipower.cloud.enhancecore.updownaware.component.NacosDeregistProcesser;
import com.ipower.cloud.enhancecore.updownaware.component.ShutdownPreStopEndpoint;
import feign.codec.ErrorDecoder;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Description 配置默认的httpCenlient，解码器等
 *  使用feginhttp-client后，默认的是HttpClientBuilder.create().build()，里面的线程池数量并没有设置,这里设置一个定制的连接池再返回
 *
 *  FeignRibbonClientAutoConfiguration会先于FeignAutoConfiguration进行配置
 *  当feign和ribbon联合使用的时候 FeignRibbonClientAutoConfiguration中Import HttpClientFeignLoadBalancedConfiguration 的 feignClient方法会用下面生成的httpclientbean
 *  如果没有和ribbon一起用，则是在FeignAutoConfiguration中生成feignClient并使用本配置中的httpClient
 * @Author xl
 * @Date 2020/4/2
 *
 * 在引入了加强包后，主工程对feign和hystrix的使用还是必须在启动入口主类上加上下面三个注解才会启用生效
 * //开启feignClients扫描
 * @EnableFeignClients
 * //开启断路器功能
 * @EnableCircuitBreaker
 * //开启断路器监控dashboard
 * @EnableHystrixDashboard
 */

@Configuration
@ConditionalOnClass({MarsFeignProperties.class})
//@AutoConfigureAfter(MarsHttpCheckEndPointAutoConfiguration.class)
@EnableConfigurationProperties(MarsFeignProperties.class)
@ConditionalOnProperty(prefix = "com.mars.springcloud.feign", name = "enable",havingValue="true", matchIfMissing = false)
public class MarsFeignAutoConfiguration {


    @Bean(destroyMethod = "close")
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(MarsFeignProperties marsFeignProperties){

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(marsFeignProperties.getHttpClientMaxPerHostNum());
        connectionManager.setMaxTotal(marsFeignProperties.getHttpClientMaxNum());
        return connectionManager;
    }


//因为2x版本里面会探测有httpclient的class在就自己回创建httpclient这里就不用自己的去替换springboot原有的了

    @Bean
    public HttpClientBuilder httpClientBuilder(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager, MarsFeignProperties marsFeignProperties){
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(marsFeignProperties.getHttpClientConectTimeOut())
                .setSocketTimeout(marsFeignProperties.getHttpClientSocketTimeOut())//数据返回超时时间
                .setConnectionRequestTimeout(marsFeignProperties.getHttpClientPoolGetTimeOut())//从连接池取连接超时时间
                .build();
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setConnectionManager(poolingHttpClientConnectionManager);
        builder.setDefaultRequestConfig(requestConfig);
        //因为本身ribbon以后重试，这里就不要再搞重试了
        builder.setRetryHandler(new DefaultHttpRequestRetryHandler(0,false));
        return builder;
    }

    @Bean
    @Primary
    //这个地方会优先使用自己定义的httpclient，最后在生成feign的时候，就会使用这个来创建feignClient，和ribbon一起用的时候在FeignRibbonClientAutoConfiguration->HttpClientFeignLoadBalancedConfiguration中首先使用
    public HttpClient httpClient(HttpClientBuilder httpClientBuilder){
        return httpClientBuilder.build();
    }

    //替换异常处理解码器对象
    @Bean
    @Primary
    public ErrorDecoder errorDecoder() {
        return new EnhanceFeignErrorDecoder();
    }

    @Bean
    public NacosDeregistProcesser nacosDeregistWaiter(NacosDiscoveryProperties nacosDiscoveryProperties){
        return new NacosDeregistProcesser(nacosDiscoveryProperties);
    }

    @Bean
    public ShutdownPreStopEndpoint shutdownPreStopEndpoint(NacosDeregistProcesser nacosDeregistWaiter){
        return new ShutdownPreStopEndpoint(nacosDeregistWaiter);
    }

    @Bean
    public NacosAutoRegisterPulloff nacosRegistStartHealthChecker(NacosDiscoveryProperties nacosDiscoveryProperties, HealthEndpoint healthEndpoint){
        return new NacosAutoRegisterPulloff(nacosDiscoveryProperties,healthEndpoint);
    }

    @Bean
    public NacosApplicationReadyRegister nacosApplicationReadyRegister(NacosDiscoveryProperties nacosDiscoveryProperties, NacosAutoServiceRegistration registration, HealthEndpoint healthEndpoint){
        return new NacosApplicationReadyRegister(nacosDiscoveryProperties,registration,healthEndpoint);
    }



}
