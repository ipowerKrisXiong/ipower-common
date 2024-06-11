//package com.ipower.cloud.enhancecore.ribbon.rule;
//
//public class RibbonRouteKeyGroupHolder {
//
//    //InheritableThreadLocal可以在当前线程正常创建的子线程下自动传递值
//    private static ThreadLocal<RouteKeyGroup>  routeKeyGroupThreadLocal = new InheritableThreadLocal();
//
//    public static void put(RouteKeyGroup routeKeyGroup){
//        routeKeyGroupThreadLocal.set(routeKeyGroup);
//    }
//
//    public static RouteKeyGroup get(){
//        return routeKeyGroupThreadLocal.get();
//    }
//
//    //用完必须要释放掉
//    public static void remove(){
//        routeKeyGroupThreadLocal.remove();
//    }
//
//
//}
