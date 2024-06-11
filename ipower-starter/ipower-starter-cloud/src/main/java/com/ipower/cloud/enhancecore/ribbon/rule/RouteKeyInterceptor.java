//package com.ipower.cloud.enhancecore.ribbon.rule;
//
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @Description springMVC拦截器，用来拦截请求并把用于路由的header设置到ThreadLocal中
// * @Author xl
// * @Date 2020/4/3
// */
//public class RouteKeyInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//
//        String deviceVal = httpServletRequest.getHeader(RouteKeyGroup.DEVICE);
//        String imeiVal = httpServletRequest.getHeader(RouteKeyGroup.IMEI);
//        String osverVal = httpServletRequest.getHeader(RouteKeyGroup.OSVER);
//        String platformVal = httpServletRequest.getHeader(RouteKeyGroup.PLATFORM);
//        String xAccessTokenVal = httpServletRequest.getHeader(RouteKeyGroup.X_ACCESS_TOKEN);
//        String userIdVal = httpServletRequest.getHeader(RouteKeyGroup.USERID);
//        String userJWTVal = httpServletRequest.getHeader(RouteKeyGroup.USERJWT);
//        String tagsVal = httpServletRequest.getHeader(RouteKeyGroup.TAGS);
//
//        RouteKeyGroup routeKeyGroup = new RouteKeyGroup();
//        routeKeyGroup.setDevice(deviceVal);
//        routeKeyGroup.setImei(imeiVal);
//        routeKeyGroup.setOsver(osverVal);
//        routeKeyGroup.setPlatform(platformVal);
//        routeKeyGroup.setxAccessToken(xAccessTokenVal);
//        routeKeyGroup.setUserid(userIdVal);
//        routeKeyGroup.setJwtoken(userJWTVal);
//        routeKeyGroup.setTags(tagsVal);
//
//        RibbonRouteKeyGroupHolder.put(routeKeyGroup);
//
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//
//    }
//}
