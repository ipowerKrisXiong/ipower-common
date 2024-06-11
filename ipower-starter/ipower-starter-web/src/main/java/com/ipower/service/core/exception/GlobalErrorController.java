package com.ipower.service.core.exception;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author kris
 * @date 2021/7/22
 *  异常信息最终包装处理器
 *  这个controller是继承了默认的 /error处理器BasicErrorController
 *  DispatcherServlet 调用controller方法后出现异常，返回后会先走@RestControllerAdvice注解的controller切面进行处理
 *  如果依然抛出异常，则会走DispatcherServlet的processDispatchResult进行异常处理，继续网上抛给tomcat，StandardHostValve中依次调用throwable这里面如果有异常，则转发(dispatcher)到/error处理器进行处理
 *  默认的/error处理器是BasicErrorController，里面errorHtml和error是进行返回处理的关键方法，分别对应text/html(认为是网页) 和其它的处理方式
 *  这里是扩展处理一下自己application/json方面的异常的处理
 *
 *  BasicErrorController使用的地方很多，一个的普通的spingmvc这儿处理异常，还有一个是在Zuul中最后也是这么处理的
 *
 */
//@Controller
public class GlobalErrorController extends BasicErrorController {

    //因为父类是private，所以这里只能再设置一次
    private final ErrorAttributes errorAttributes;

    public GlobalErrorController(ErrorAttributes errorAttributes,
                                 ErrorProperties errorProperties,
                                 List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties,errorViewResolvers);
        this.errorAttributes = errorAttributes;
    }

    //GlobalExceptionHandler未处理的异常，落到这儿统一返回一个默认异常
    @Override
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {

//        super.error返回了哪些key
//        {
//            "timestamp": "2022-04-27T17:43:19.920+08:00",
//                "status": 504,
//                "error": "Gateway Timeout",
//                "exception": "com.netflix.zuul.exception.ZuulException",
//                "trace": "com.netflix.zuul.exception.ZuulException: \r\n\tat org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter.findZuulException(SendErrorFilter.java:118)\r\n\tat org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter.run(SendErrorFilter.java:78)\r\n\tat com.netflix.zuul.ZuulFilter.runFilter(ZuulFilter.java:117)\r\n\tat com.netflix.zuul.FilterProcessor.processZuulFilter(FilterProcessor.java:193)\r\n\tat com.netflix.zuul.FilterProcessor.runFilters(FilterProcessor.java:157)\r\n\tat com.netflix.zuul.FilterProcessor.error(FilterProcessor.java:105)\r\n\tat com.netflix.zuul.ZuulRunner.error(ZuulRunner.java:112)\r\n\tat com.netflix.zuul.http.ZuulServlet.error(ZuulServlet.java:145)\r\n\tat com.netflix.zuul.http.ZuulServlet.service(ZuulServlet.java:83)\r\n\tat org.springframework.web.servlet.mvc.ServletWrappingController.handleRequestInternal(ServletWrappingController.java:166)\r\n\tat org.springframework.cloud.netflix.zuul.web.ZuulController.handleRequest(ZuulController.java:45)\r\n\tat org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter.handle(SimpleControllerHandlerAdapter.java:52)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1040)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:943)\r\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\r\n\tat org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909)\r\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:652)\r\n\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\r\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:733)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:227)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:320)\r\n\tat org.springframework.security.web.access.intercept.FilterSecurityInterceptor.invoke(FilterSecurityInterceptor.java:126)\r\n\tat org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(FilterSecurityInterceptor.java:90)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\r\n\tat org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:118)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\r\n\tat org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:137)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\r\n\tat org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:111)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\r\n\tat org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:158)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\r\n\tat org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:63)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\r\n\tat org.springframework.security.web.authentication.www.BasicAuthenticationFilter.doFilterInternal(BasicAuthenticationFilter.java:155)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\r\n\tat org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:116)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\r\n\tat org.springframework.security.web.header.HeaderWriterFilter.doHeadersAfter(HeaderWriterFilter.java:92)\r\n\tat org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:77)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\r\n\tat org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:105)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\r\n\tat org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:56)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n\tat org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:334)\r\n\tat org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:215)\r\n\tat org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:178)\r\n\tat org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:358)\r\n\tat org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:271)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n\tat org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n\tat org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFilter.doFilterInternal(WebMvcMetricsFilter.java:97)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n\tat org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:92)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)\r\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:97)\r\n\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:542)\r\n\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:143)\r\n\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\r\n\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:78)\r\n\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:357)\r\n\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:374)\r\n\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65)\r\n\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:893)\r\n\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1707)\r\n\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\r\n\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\r\n\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\r\n\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\r\n\tat java.lang.Thread.run(Thread.java:748)\r\n",
//                "message": "com.netflix.zuul.exception.ZuulException: Hystrix Readed time out"
//        }

//        WebRequest webRequest = new ServletWebRequest(request);
//        //获取原来异常信息
//        Throwable throwable = this.errorAttributes.getError(webRequest);

        ResponseEntity<Map<String, Object>> response = super.error(request);

        //在原有返回{json}对象得基础上，增加几个自己的key
        response.getBody().put("code", 500);
        response.getBody().put("msg", "error");
        response.getBody().put("data", null);
        //保持HttpStatus！=200
        return response;

    }
}
