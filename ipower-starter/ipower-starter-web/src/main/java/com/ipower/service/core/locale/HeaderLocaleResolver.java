package com.ipower.service.core.locale;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleContextResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 仿造CookieLocaleResolver做一个header的LocalResolver
 * 这样就不用使用默认的cookie方式
 */
public class HeaderLocaleResolver implements LocaleContextResolver {

    public static final String LOCALE_REQUEST_ATTRIBUTE_NAME = HeaderLocaleResolver.class.getName() + ".LOCALE";
    public static final String TIME_ZONE_REQUEST_ATTRIBUTE_NAME = HeaderLocaleResolver.class.getName() + ".TIME_ZONE";

    public static final String LOCALE_PARAM_HEADER_NAME = "locale";
    //时区信息zoneId e.g. Asia/Tokyo
    public static final String TIMEZONE_PARAM_HEADER_NAME = "timeZone";

    MarsLocaleProperties marsLocaleProperties;

    public HeaderLocaleResolver(MarsLocaleProperties marsLocaleProperties) {
        this.marsLocaleProperties = marsLocaleProperties;
    }

    /**
     * 解析创建获取默认LocaleContext
     */
    @Override
    public LocaleContext resolveLocaleContext(HttpServletRequest request) {
        parseLocaleAndTimeZoneIfNecessary(request);
        return new TimeZoneAwareLocaleContext() {
            @Override
            @Nullable
            public Locale getLocale() {
                return (Locale) request.getAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME);
            }
            @Override
            @Nullable
            public TimeZone getTimeZone() {
                return (TimeZone) request.getAttribute(TIME_ZONE_REQUEST_ATTRIBUTE_NAME);
            }
        };
    }

    /**
     * 设置替换localeContext
     */
    @Override
    public void setLocaleContext(HttpServletRequest request, HttpServletResponse response, LocaleContext localeContext) {
        Assert.notNull(response, "HttpServletResponse is required for HeaderLocaleResolver");

        Locale locale = null;
        TimeZone timeZone = null;
        //从设置的localeContext中拿出locale和timeZone信息对request的属性做覆盖
        if (localeContext != null) {
            locale = localeContext.getLocale();
            if (localeContext instanceof TimeZoneAwareLocaleContext) {
                timeZone = ((TimeZoneAwareLocaleContext) localeContext).getTimeZone();
            }
        }
        else {
            //如果是空则什么都不做
        }
        request.setAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME,
                (locale != null ? locale : determineDefaultLocale()));
        //开启时区自动转换的时候则要设置请求过来的时区
        if(marsLocaleProperties.getSupportTimeZoneTrans()){
            request.setAttribute(TIME_ZONE_REQUEST_ATTRIBUTE_NAME,
                    (timeZone != null ? timeZone : determineDefaultTimeZone()));
        }
    }

    /**默认解析locale*/
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        parseLocaleAndTimeZoneIfNecessary(request);
        return (Locale) request.getAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME);
    }

    /**用设置值进行覆盖*/
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        setLocaleContext(request, response, (locale != null ? new SimpleLocaleContext(locale) : null));
    }

    private void parseLocaleAndTimeZoneIfNecessary(HttpServletRequest request) {

        //header中找地区和时区信息
        String reqLocale = request.getHeader(LOCALE_PARAM_HEADER_NAME);
        String reqTimeZone = request.getHeader(TIMEZONE_PARAM_HEADER_NAME);
        TimeZone timeZone =null;
        Locale locale =null;
        //重新设置timeZone
        if(!StringUtils.isEmpty(reqTimeZone)){
            timeZone = TimeZone.getTimeZone(reqTimeZone);
        }
        //重新设置locale
        if(!StringUtils.isEmpty(reqLocale)){
            locale = StringUtils.parseLocale(reqLocale);
        }

        request.setAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME,
                (locale != null ? locale : determineDefaultLocale()));

        //开启时区自动转换的时候则要设置请求过来的时区
        if(marsLocaleProperties.getSupportTimeZoneTrans()){
            request.setAttribute(TIME_ZONE_REQUEST_ATTRIBUTE_NAME,
                    (timeZone != null ? timeZone : determineDefaultTimeZone()));
        }

    }

    //获取系统默认locale
    protected Locale determineDefaultLocale() {
        return Locale.getDefault();
    }

    //获取系统默认timeZone
    @Nullable
    protected TimeZone determineDefaultTimeZone() {
        return TimeZone.getDefault();
    }
}
