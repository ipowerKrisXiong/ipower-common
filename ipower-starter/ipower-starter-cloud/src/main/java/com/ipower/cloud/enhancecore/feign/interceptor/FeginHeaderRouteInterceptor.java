package com.ipower.cloud.enhancecore.feign.interceptor;

import com.mars.service.core.locale.HeaderLocaleResolver;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Description 如果全部都是用feign进行远程调用
 * 则用feign拦截器，把header的东西进行设置往后面的服务传
 * @Author xl
 * @Date 2020/4/3
 */
public class FeginHeaderRouteInterceptor implements RequestInterceptor {


    public String DEVICE = "device";
    public String IMEI = "imei";
    public String OSVER = "osver";
    public String PLATFORM = "platform";
    public String X_ACCESS_TOKEN = "x-access-token";

    public String USERID = "userId";
    public String USERJWT = "jwtoken";
    public String CLIENT_TOKEN = "client-token";

    @Override
    public void apply(RequestTemplate requestTemplate) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String headerVal = request.getHeader(DEVICE);
        requestTemplate.header(DEVICE, headerVal);

        String imeiVal = request.getHeader(IMEI);
        requestTemplate.header(IMEI, imeiVal);

        String osverVal = request.getHeader(OSVER);
        requestTemplate.header(OSVER, osverVal);

        String platformVal = request.getHeader(PLATFORM);
        requestTemplate.header(PLATFORM, platformVal);

        String xAccessTokenVal = request.getHeader(X_ACCESS_TOKEN);
        requestTemplate.header(X_ACCESS_TOKEN, xAccessTokenVal);

        String userIdVal = request.getHeader(USERID);
        requestTemplate.header(USERID, userIdVal);

        String userJWTVal = request.getHeader(USERJWT);
        requestTemplate.header(USERJWT, userJWTVal);

        String clientToken = request.getHeader(CLIENT_TOKEN);
        requestTemplate.header(CLIENT_TOKEN, clientToken);

//        //转发所有header
//        Enumeration<String> headerNames = request.getHeaderNames();
//        if (headerNames != null) {
//            while (headerNames.hasMoreElements()) {
//                String name = headerNames.nextElement();
//                String values = request.getHeader(name);
//                requestTemplate.header(name, values);
//            }
//        }

        // 设置request中的attribute到header:主要是设置自行设置的token、userId等信息，以便转发到Feign调用的服务
        Enumeration<String> reqAttrbuteNames = request.getAttributeNames();
        if (reqAttrbuteNames != null) {
            while (reqAttrbuteNames.hasMoreElements()) {
                String attrName = reqAttrbuteNames.nextElement();
                String values = request.getAttribute(attrName).toString();
                requestTemplate.header(attrName, values);
            }
        }

        //把区域信息加到header中传递到下一个去
        String localStr = LocaleContextHolder.getLocale().toString();
        if(StringUtils.isNotBlank(localStr)){
            requestTemplate.header(HeaderLocaleResolver.LOCALE_PARAM_HEADER_NAME, localStr);
        }

        String timeZoneIdStr = LocaleContextHolder.getTimeZone().getID();
        if(StringUtils.isNotBlank(timeZoneIdStr)){
            requestTemplate.header(HeaderLocaleResolver.TIMEZONE_PARAM_HEADER_NAME, timeZoneIdStr);
        }

    }

}
