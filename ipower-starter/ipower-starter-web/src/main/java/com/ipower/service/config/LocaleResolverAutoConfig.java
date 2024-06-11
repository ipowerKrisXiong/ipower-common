package com.ipower.service.config;

import com.ipower.service.core.locale.HeaderLocaleResolver;
import com.ipower.service.core.locale.MarsInheritableLocaleChangeInterceptor;
import com.ipower.service.core.locale.MarsLocaleProperties;
import com.ipower.service.core.locale.MsgUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.util.Locale;
import java.util.TimeZone;

@EnableConfigurationProperties(MarsLocaleProperties.class)
@Configuration
public class LocaleResolverAutoConfig implements WebMvcConfigurer, ApplicationContextAware {

    //不使用配置时，可以考虑用这种方式声明 MessageSource
//    @Bean
//    @Primary
//    public ResourceBundleMessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasenames("i18n/messages/messages");
//        messageSource.setDefaultEncoding("UTF8");
//        return messageSource;
//    }
//
    /**
     * 这个如果不存在，则会抛异常: nested exception is java.lang.UnsupportedOperationException: Cannot change HTTP accept header - use a different locale resolution strategy
     *
     * local识别器默认使用的AcceptHeaderLocaleResolver，是根据http请求传递的参数accept-language头部来解析区域，这个异步读取的是浏览器的操作系统设置，一般不好改
     * 还有SessionLocaleResolver，这里采用Cookie解析器
     *
     * 默认LocaleResolver的设置在WebMvcAutoConfiguration中
     *
     * 参考：https://blog.csdn.net/A232222/article/details/108238133
     * https://blog.csdn.net/pengkai411429850/article/details/51964846
     * 官方参考：https://books.didispace.com/spring-mvc-4-tutorial/publish/21-8/5-locale-change-interceptor.html
     * 设置了CookieLocaleResolver后，local会同步写入本地Cookie，cookie值示例：org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE=en-US
     *
     * LocaleResolver提供了local和timezone两种本地相关信息，这里主要只用local地区信息，时区信息以后要用到的时候再扩展
     *
     * Locale类默认地区：
     * 本机Windows机器如下方式实现：
     * （1）可通过修改操作系统的语言设置。
     * （2）Java启动参数添加：-Duser.language=en -Duser.country=US。
     *
     * linux可通过修改系统语言来实现。
     *
     *  @return
     */
    @Bean
    public LocaleResolver localeResolver(MarsLocaleProperties marsLocaleProperties) {
        //按配置设置固定本地区域方言和时区
        if(marsLocaleProperties.getFixedLocalZone()){
            String localStr = marsLocaleProperties.getFixedLocale();
            if(StringUtils.isBlank(localStr)){
                throw new RuntimeException("mars.locale.fixedLocale 设置异常");
            }
            String zoneIdStr = marsLocaleProperties.getFixedTimeZone();
            if(StringUtils.isBlank(zoneIdStr)){
                throw new RuntimeException("mars.locale.fixedTimeZone 设置异常");
            }
            //local
            String[] localeArray = localStr.split("_");
            if(localeArray.length!=2){
                throw new RuntimeException("mars.locale.fixedLocale 格式不正确");
            }
            Locale locale = new Locale(localeArray[0],localeArray[1]);
            //timeZone
            TimeZone timeZone =TimeZone.getTimeZone(zoneIdStr);
            FixedLocaleResolver localeResolver  = new FixedLocaleResolver(locale,timeZone);
            return localeResolver;
        //cookie，header的方式动态设置本地区域
        }else{
            // 也可以换成 SessionLocalResolver, 区别在于国际化的应用范围
//            CookieLocaleResolver localeResolver = new CookieLocaleResolver();
            //用自定义headerResolver从header中取出locale信息，避免cookie的干扰
            HeaderLocaleResolver localeResolver = new HeaderLocaleResolver(marsLocaleProperties);
            return localeResolver;
        }

    }

    /**
     * 根据请求参数，使用设置的LocaleResolver来进行本地化处理
     *
     * 在拦截器中，分析对应请求参数，然后设置上面那个LocalResolver，把local设置到request的attibute=LOCALE_REQUEST_ATTRIBUTE_NAME中
     * 然后在duPost or doGet之后，framworkServlet的initContextHolders方法，会LocaleContextHolder.setLocaleContext(localeContext//就是上面设置的CookieLocaleResolver, this.threadContextInheritable);
     * 来设置当前local到LocaleContextHolder中，注意这个时候默认参数是不可继承的，所以后面在用LocaleContextHolder.getLocal的时候，如果开了子线程，则获取不到cookie传上来的local，只能得到default
     *
     * @return
     */
    //变为springbean这样才能往MarsInheritableLocaleChangeInterceptor中注入对象
    @Bean
    public MarsInheritableLocaleChangeInterceptor localeChangeInterceptor() {
        MarsInheritableLocaleChangeInterceptor localeChangeInterceptor = new MarsInheritableLocaleChangeInterceptor();
        // Defaults to "locale" if not set，设置URL请求参数key值，例如：http://127.0.0.1:22007/web/testapi/v1.0/xxx?locale=en_US
        return localeChangeInterceptor;
    }

    //添加拦截器，这个方法必须要有，否则拦截器不会生效
    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(localeChangeInterceptor());
    }

    //初始化静态工具
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //初始化静态工具
        MsgUtil.inti(applicationContext.getBean(MessageSource.class));
    }
}
