package com.ipower.service.core.healthcheck;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @Description  包装HttpCheckReadyEndPoint，代理实现缓存，还有请求参数传递等功能
 * @Author xl
 * @Date 2020/3/31
 * 自定义endpoint方法
 * https://www.jb51.net/article/224284.htm
 */

@Endpoint(id = "livecheck",enableByDefault=true)
public class HttpCheckLiveMvcEndPoint{

    public static final String LIVE = "LIVE";
    public static final String NO_LIVE = "NO LIVE";

    private long timeToLive = 1000;
    private long lastAccess = 0;

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

    private String cached;

    private String getEndPointResult() {
        long accessTime = System.currentTimeMillis();
        //如果缓存失效,则获取一次结果
        if (isCacheStale(accessTime)) {
            this.lastAccess = accessTime;
            this.cached = isAlive();
        }

        return this.cached;
    }

    /**
     * @Description  存活状态端口
     * 目前只考虑直接返回，及端口通则ok。
     * 因为考虑到如果采用springboot的 health的话，万一又中间件挂了，重启依然会挂
     * 而且因为加了UpDownAwareHealth自定义health，在关闭的时候会变成down状态，这个时候live探测到自动重启就尴尬了
     * @param
     * @return
     */
    public String isAlive() {

        return LIVE;
//          这个地方的live判断条件，一旦不成立k8s则会进行重启，要看什么条件是需要重启的？比如vm fullgc 5分钟以上？
//          不过本身springcloud就有注册机制来踢出假死进程，这儿也可以不管了,可以后面考虑下磁盘类的
//        if (true) {
//            // 做该做的逻辑
//            return LIVE;
//        } else {
//            //设置http状态码，服务还不可用
//            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//            return NO_LIVE;
//        }
    }


    //是否缓存失效
    private boolean isCacheStale(long accessTime) {
        if (this.cached == null) {
            return true;
        }
        return (accessTime - this.lastAccess) >= getTimeToLive();
    }



    @ReadOperation
    public Object invoke(
//            @Selector  String onlyCheckPort 跟踪看了源码，不知道为啥，加了个，在注册endPoint的时候path被认为是/actuator/livecheck/{arg0} 会被判断为不属于宇哥urlpath，注册不到urllookup中去，则请求的时候就找不到对应的hanlder，所以这里先去了，以后再来研究
//                源码位置在AbstractHandlerMethodMapping.register方法的602行
                    ){
//        if (!StringUtils.isBlank(onlyCheckPort)&&onlyCheckPort.equalsIgnoreCase("onlyCheckPort")) {
//            return LIVE;
//        }

        String endPointResult = getEndPointResult();
        if(endPointResult==null||!endPointResult.equals(LIVE)){
            return new ResponseEntity<String>(endPointResult, HttpStatus.SERVICE_UNAVAILABLE);
        }else{
            return endPointResult;
        }

    }


}
