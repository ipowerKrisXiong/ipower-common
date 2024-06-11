//package com.ipower.cloud.enhancecore.ribbon.rule;
//
//import com.netflix.client.config.IClientConfig;
//import com.netflix.loadbalancer.*;
//
//import java.util.List;
//
///**自定义一个负载均衡策略，实现IRule接口，这里是继承的最顶端的抽象类*/
//public class MarsEnhanceRouteRule extends AbstractLoadBalancerRule
//{
//
//    /**总共被调用的次数，目前要求每台被调用5次*/
//    private int total = 0;
//    /**当前提供服务的机器号*/
//    private int currentIndex = 0;
//
//    @Override
//    public void initWithNiwsConfig(IClientConfig clientConfig) {
//
//
//    }
//
//    //选择哪台机器
//    @Override
//    public Server choose(Object key) {
//        //父类方法去除当前loadBalancer
//        ILoadBalancer lb = getLoadBalancer();
//
//        if(lb==null){
//            return null;
//        }
//
//        Server server = null;
//
//        while (server == null) {
//
//            if (Thread.interrupted()) {
//                return null;
//            }
//
//            RouteKeyGroup routeKeyGroup = RibbonRouteKeyGroupHolder.get();
//
//            //当前存活的服务，如果不是UP状态，或者被从eureka中心delete，ribbon节点感应到后，会从ReachableList里面移除掉
//            List<Server> upList = lb.getReachableServers();
//            //获取全部的服务
//            List<Server> allList = lb.getAllServers();
//
//            int serverCount = allList.size();
//            if (serverCount == 0) {
//                return null;
//            }
//
//            int upServerCount = upList.size();
//            if (upServerCount == 0) {
//                return null;
//            }
//
//
//            String routePK = null;
//            if(routeKeyGroup.getImei()!=null){
//                routePK = routeKeyGroup.getImei();
//            }
//
//            //如果没有用户ID，则采用每个服务器轮询5次的方式
//            if(routeKeyGroup==null){
//                if(total < 5)
//                {
//                    server = upList.get(currentIndex);
//                    total++;
//                }else {
//                    total = 0;
//                    currentIndex++;
//                    if(currentIndex >= upList.size())
//                    {
//                        currentIndex = 0;
//                    }
//                }
//            //如果有用户ID，则采用用户ID取当前可用服务器取模的方式
//            }else{
//                if(upList!=null&&upList.size()>0){
//                    int serverIdx = toPositive(murmur2Hash(routePK.getBytes())) % upList.size();
//                    server = upList.get(serverIdx);
//                }
//            }
//
//
//            if (server == null) {
//                Thread.yield();
//                continue;
//            }
//
//            if (server.isAlive()) {
//                return (server);
//            }
//
//            // Shouldn't actually happen.. but must be transient or a bug.
//            server = null;
//            Thread.yield();
//        }
//        //用完后记得移除，以免发生内存泄漏
//        RibbonRouteKeyGroupHolder.remove();
//        return server;
//    }
//
//
//
//    private static int murmur2Hash(byte[] data) {
//        int length = data.length;
//        int seed = -1756908916;
//        int m = 1540483477;
//        int h = seed ^ length;
//        int length4 = length / 4;
//
//        for(int i = 0; i < length4; ++i) {
//            int i4 = i * 4;
//            int k = (data[i4 + 0] & 255) + ((data[i4 + 1] & 255) << 8) + ((data[i4 + 2] & 255) << 16) + ((data[i4 + 3] & 255) << 24);
//            k *= 1540483477;
//            k ^= k >>> 24;
//            k *= 1540483477;
//            h *= 1540483477;
//            h ^= k;
//        }
//
//        switch(length % 4) {
//            case 3:
//                h ^= (data[(length & -4) + 2] & 255) << 16;
//            case 2:
//                h ^= (data[(length & -4) + 1] & 255) << 8;
//            case 1:
//                h ^= data[length & -4] & 255;
//                h *= 1540483477;
//            default:
//                h ^= h >>> 13;
//                h *= 1540483477;
//                h ^= h >>> 15;
//                return h;
//        }
//    }
//
//    private static int toPositive(int number) {
//        return number & 2147483647;
//    }
//
//}
