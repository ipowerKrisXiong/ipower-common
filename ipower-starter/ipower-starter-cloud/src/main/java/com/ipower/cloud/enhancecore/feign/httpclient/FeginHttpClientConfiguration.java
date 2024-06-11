package com.ipower.cloud.enhancecore.feign.httpclient;//package com.mars.springcloud.enhancecore.feign.httpclient;
//
//import com.mars.springcloud.enhancecore.feign.config.MarsFeignProperties;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//改到starter里面去设置了
///**使用feginhttp-client后，默认的是HttpClientBuilder.create().build()，里面的线程池数量并没有设置,这里设置一个定制的连接池再返回
// *
// * FeignRibbonClientAutoConfiguration会先于FeignAutoConfiguration进行配置
// * 当feign和ribbon联合使用的时候 FeignRibbonClientAutoConfiguration中Import HttpClientFeignLoadBalancedConfiguration 的 feignClient方法会用下面生成的httpclientbean
// * 如果没有和ribbon一起用，则是在FeignAutoConfiguration中生成feignClient并使用本配置中的httpClient
// * */
//@Configuration
//public class FeginHttpClientConfiguration {
//
//
//    @Bean(destroyMethod = "close")
//    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(MarsFeignProperties marsFeignProperties){
//
//        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
//        connectionManager.setDefaultMaxPerRoute(marsFeignProperties.getHttpClientMaxPerHostNum());
//        connectionManager.setMaxTotal(marsFeignProperties.getHttpClientMaxNum());
//        return connectionManager;
//    }
//
//    @Bean
//    public HttpClientBuilder httpClientBuilder(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,MarsFeignProperties marsFeignProperties){
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setConnectTimeout(marsFeignProperties.getHttpClientConectTimeOut())
//                .setSocketTimeout(marsFeignProperties.getHttpClientSocketTimeOut())//数据返回超时时间
//                .setConnectionRequestTimeout(marsFeignProperties.getHttpClientPoolGetTimeOut())//从连接池取连接超时时间
//                .build();
//        HttpClientBuilder builder = HttpClientBuilder.create();
//        builder.setConnectionManager(poolingHttpClientConnectionManager);
//        builder.setDefaultRequestConfig(requestConfig);
//        //因为本身ribbon以后重试，这里就不要再搞重试了
//        builder.setRetryHandler(new DefaultHttpRequestRetryHandler(0,false));
//        return builder;
//    }
//
//    @Bean
//    @Primary
//    //这个地方会优先使用自己定义的httpclient，最后在生成feign的时候，机会使用这个来创建feignClient，和ribbon一起用的时候在FeignRibbonClientAutoConfiguration->HttpClientFeignLoadBalancedConfiguration中首先使用
//    public HttpClient httpClient(HttpClientBuilder httpClientBuilder){
//        return httpClientBuilder.create().build();
//    }
//
//}
