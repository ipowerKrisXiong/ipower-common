package com.ipower.cloud.enhancecore.feign.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.mars.springcloud.feign")
public class MarsFeignProperties {

    /**
     * 打开加强默认自动配置
     */
    private boolean enable=true;

    private Integer httpClientMaxNum=200;

    private Integer httpClientMaxPerHostNum=200;

    //创建连接超时时间
    private Integer httpClientConectTimeOut=2000;

    //数据返回超时时间
    private Integer httpClientSocketTimeOut=10000;

    //从连接池取连接超时时间
    private Integer httpClientPoolGetTimeOut=2000;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Integer getHttpClientMaxNum() {
        return httpClientMaxNum;
    }

    public void setHttpClientMaxNum(Integer httpClientMaxNum) {
        this.httpClientMaxNum = httpClientMaxNum;
    }

    public Integer getHttpClientMaxPerHostNum() {
        return httpClientMaxPerHostNum;
    }

    public void setHttpClientMaxPerHostNum(Integer httpClientMaxPerHostNum) {
        this.httpClientMaxPerHostNum = httpClientMaxPerHostNum;
    }

    public Integer getHttpClientConectTimeOut() {
        return httpClientConectTimeOut;
    }

    public void setHttpClientConectTimeOut(Integer httpClientConectTimeOut) {
        this.httpClientConectTimeOut = httpClientConectTimeOut;
    }

    public Integer getHttpClientSocketTimeOut() {
        return httpClientSocketTimeOut;
    }

    public void setHttpClientSocketTimeOut(Integer httpClientSocketTimeOut) {
        this.httpClientSocketTimeOut = httpClientSocketTimeOut;
    }

    public Integer getHttpClientPoolGetTimeOut() {
        return httpClientPoolGetTimeOut;
    }

    public void setHttpClientPoolGetTimeOut(Integer httpClientPoolGetTimeOut) {
        this.httpClientPoolGetTimeOut = httpClientPoolGetTimeOut;
    }
}
