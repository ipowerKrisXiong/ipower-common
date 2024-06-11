package com.ipower.service.core.healthcheck;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @Description  包装HttpCheckReadyEndPoint，代理实现缓存，还有请求参数传递等功能
 * @Author xl
 * @Date 2020/3/31
 * WebEndpoint的ID命名不支持驼峰，只支持“-”，和浏览器的地址规则一样
 */


@Endpoint(id = "readycheck",enableByDefault=true)
public class HttpCheckReadyMvcEndPoint {

    private long lastAccess = 0;

    private String cached;

    public static final String READY = "READY";

    public static final String NO_READY = "NO_READY";
    /**
     * Time to live for cached result, in milliseconds.
     */
    private long timeToLive = 1000;

    /**
     * Time to live for cached result. This is particularly useful to cache the result of
     * this endpoint to prevent a DOS attack if it is accessed anonymously.
     * @return time to live in milliseconds (default 1000)
     */
    public long getTimeToLive() {
        return this.timeToLive;
    }

    public void setTimeToLive(long ttl) {
        this.timeToLive = ttl;
    }

    //springboot自带的bean healthEndPoint端点
    private HealthEndpoint healthEndpoint;

    public HttpCheckReadyMvcEndPoint(
            HealthEndpoint healthEndpoint) {
        this.healthEndpoint = healthEndpoint;
    }


    public String checkReady() {

        //如果启用了health端点，则盘点health端点情况
        if(healthEndpoint!=null){
            if(Status.UP == healthEndpoint.health().getStatus()){
                return READY;
            }else{
                return NO_READY;
            }
        //否则只要http状态对了(tomcat一般在最后启动,这个时候端口通了直接返回就行)
        } else {
//            //设置http状态码，服务还不可用
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return NO_READY;
        }

        return READY;
    }


    //带缓存检查
    private String getEndPointResult() {
        long accessTime = System.currentTimeMillis();
        //如果缓存失效,则获取一次结果
        if (isCacheStale(accessTime)) {
            this.lastAccess = accessTime;
            this.cached = checkReady();
        }
        return this.cached;
    }

    //是否缓存失效
    private boolean isCacheStale(long accessTime) {
        if (this.cached == null) {
            return true;
        }
        return (accessTime - this.lastAccess) >= getTimeToLive();
    }


    //端点mvc入口方法
    @ReadOperation
    public Object invoke(
            //@Selector  String onlyCheckPort 跟踪看了源码，不知道为啥，加了个，在注册endPoint的时候path被认为是/actuator/livecheck/{arg0} 会被判断为不属于宇哥urlpath，注册不到urllookup中去，则请求的时候就找不到对应的hanlder，所以这里先去了，以后再来研究
//            源码位置在AbstractHandlerMethodMapping.register方法的602行
//            @Selector String onlyCheckPort
    ){
//        if (StringUtils.isNotBlank(onlyCheckPort)
//                &&onlyCheckPort.equalsIgnoreCase("onlyCheckPort")) {
//            return READY;
//        }

        String endPointResult = getEndPointResult();
        if(endPointResult==null||!endPointResult.equals(READY)){
            return new ResponseEntity<String>(endPointResult, HttpStatus.SERVICE_UNAVAILABLE);
        }else{
            return endPointResult;
        }

    }

}
