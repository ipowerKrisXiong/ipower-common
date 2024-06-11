package com.ipower.service.core.cors;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
@ConfigurationProperties(prefix = "mars.cors")
@Data
public class MarsCorsFilterProperties {

    private Filter filter = new Filter();

    @Data
    public static class Filter {
        /**
         * 跨域设置，不支持模糊匹配，模糊匹配只支持*一种
         * *
         * xx.xxx.com,xx.xx.xxx.com
         * xx.xx.com:10001,xxx.xx.com:10002
         * 例子：
         * e.g.1 mars.cors.filter.corsdomains[0]=*
         * e.g.2 mars.cors.filter.corsdomains[1]=xx.xxx.com
         * e.g.3 mars.cors.filter.corsdomains[2]=xx.xx.xxx.com
         * e.g.4 mars.cors.filter.corsdomains[3]=xx.xx.com:10001
         * e.g.4 mars.cors.filter.corsdomains[4]=xxx.xx.com:10002
         * e.g.5 mars.cors.filter.corsdomains[5]=xx.xxx.com
         * e.g.5 mars.cors.filter.corsdomains[5]=xx.xx.xxx.com
         * */
        private List<String> corsdomains=new ArrayList<>();
    }




}
