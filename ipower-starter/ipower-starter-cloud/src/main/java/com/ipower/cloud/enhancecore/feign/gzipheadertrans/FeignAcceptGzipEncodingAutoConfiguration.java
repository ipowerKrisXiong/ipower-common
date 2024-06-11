package com.ipower.cloud.enhancecore.feign.gzipheadertrans;//package com.mars.springcloud.enhancecore.feign.gzipheadertrans;
//
//import feign.Feign;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
//import org.springframework.cloud.netflix.feign.encoding.FeignAcceptGzipEncodingInterceptor;
//import org.springframework.cloud.netflix.feign.encoding.FeignClientEncodingProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
///***
// * 参考文献： https://blog.csdn.net/xjune/article/details/80198850
// * 源码中因为和ribbon一起使用的时候 ApacheHttpClient不会生成，所以源码的FeignAcceptGzipEncodingAutoConfiguration不会生效，这里自己弄一个一样的来生效
// *
// * 在往后端发送请求的时候，强制加上http协议header上加上的accept-encoding=gzip，标识我可以接受gzip压缩信息。
// * 但是需要全靠服务器对这个协议解析正确
// */
//@Configuration
//@EnableConfigurationProperties(FeignClientEncodingProperties.class)
//@ConditionalOnClass(Feign.class)
////@ConditionalOnBean(ApacheHttpClient.class)
//@ConditionalOnProperty(value = "feign.compression.response.enabled", matchIfMissing = false)
//@AutoConfigureAfter(FeignAutoConfiguration.class)
//public class FeignAcceptGzipEncodingAutoConfiguration {
//
//    @Bean
//    public FeignAcceptGzipEncodingInterceptor feignAcceptGzipEncodingInterceptor(FeignClientEncodingProperties properties) {
//        return new FeignAcceptGzipEncodingInterceptor(properties);
//    }
//}
