package com.ipower.cloud.enhancecore.feign.gzipheadertrans;//package com.mars.springcloud.enhancecore.feign.gzipheadertrans;
//
//import feign.RequestTemplate;
//import org.springframework.cloud.netflix.feign.encoding.BaseRequestInterceptor;
//import org.springframework.cloud.netflix.feign.encoding.FeignClientEncodingProperties;
//import org.springframework.cloud.netflix.feign.encoding.HttpEncoding;
//
//public class FeignAcceptGzipEncodingInterceptor extends BaseRequestInterceptor {
//
//    /**
//     * Creates new instance of {@link org.springframework.cloud.netflix.feign.encoding.FeignAcceptGzipEncodingInterceptor}.
//     *
//     * @param properties the encoding properties
//     */
//    public FeignAcceptGzipEncodingInterceptor(FeignClientEncodingProperties properties) {
//        super(properties);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public void apply(RequestTemplate template) {
//
//        addHeader(template, HttpEncoding.ACCEPT_ENCODING_HEADER, HttpEncoding.GZIP_ENCODING,
//                HttpEncoding.DEFLATE_ENCODING);
//    }
//}
