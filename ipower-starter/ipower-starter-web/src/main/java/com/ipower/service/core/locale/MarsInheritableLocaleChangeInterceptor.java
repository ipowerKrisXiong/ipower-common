package com.ipower.service.core.locale;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 把LocaleContext设置为可以对派生子线程生效，这样异步子线程才能去到相应的locale信息
 */
public class MarsInheritableLocaleChangeInterceptor extends LocaleChangeInterceptor {


//    @Autowired
//    MarsLocaleProperties marsLocaleProperties;

    public MarsInheritableLocaleChangeInterceptor() {
        //DEFAULT_PARAM_NAME同时作为header名称传递feign到下一个微服务
//        super.setParamName(MarsInheritableLocaleChangeInterceptor.LOCALE_PARAM_NAME);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException {
//        //这一块方法和父类都是一样的，区别就是先找URL中的参数
//        //优先级 URL参数 --> 没有的话找header
//        String reqLocale = request.getParameter(LOCALE_PARAM_NAME);
//        String reqTimeZone = request.getParameter(TIMEZONE_PARAM_NAME);
//        //找header
//        if(StringUtils.isBlank(reqLocale)){
//            reqLocale =  request.getHeader(LOCALE_PARAM_NAME);
//        }
//        //找header
//        if(StringUtils.isBlank(reqTimeZone)){
//            reqTimeZone =  request.getHeader(TIMEZONE_PARAM_NAME);
//        }
//
//        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
//        //如果是定死了本地信息FixedLocaleResolver，则不做处理，否则做处理
//        if(localeResolver!=null && !(localeResolver instanceof FixedLocaleResolver)){
//            //父类原逻辑处理locale的设置,父类这种resolver只会粗合理cookie中的locale
//            super.preHandle(request,response,handler);
//            Locale locale = ((LocaleContextResolver)localeResolver).resolveLocaleContext(request).getLocale();
//            TimeZone timeZone = LocaleContextHolder.getTimeZone();
//            //处理timeZone的设置
//            if (checkHttpMethod(request.getMethod()) && marsLocaleProperties.getAutoTimeZoneTrans()) {
//                //重新设置timeZone
//                if(StringUtils.isNotBlank(reqTimeZone)){
//                    timeZone = TimeZone.getTimeZone(reqTimeZone);
//                }
//                //重新设置locale
//                if(StringUtils.isNotBlank(reqLocale)){
//                    String[] localeArray = reqLocale.split("_");
//                    if(localeArray.length!=2){
//                        throw new RuntimeException("header local error");
//                    }
//                    locale = new Locale(localeArray[0],localeArray[1]);
//                }
//
//                ((LocaleContextResolver)localeResolver).setLocaleContext(request, response, new SimpleTimeZoneAwareLocaleContext(locale, timeZone));
//            }
//        }

        //默认FrameworkServlet init LocaleContext的时候是不能让子线程继承生效的
        //为了避免LocaleContext对子线程不生效，先拿出来，然后重新设置一次对子线程生效,不对FrameworkServlet做全局更改,以免造成更多影响
        LocaleContext localeContext = LocaleContextHolder.getLocaleContext();
        LocaleContextHolder.setLocaleContext(localeContext, true);
        return true;
    }




}
