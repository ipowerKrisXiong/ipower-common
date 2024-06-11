package com.ipower.service.config;

import com.ipower.service.core.healthcheck.HttpCheckLiveMvcEndPoint;
import com.ipower.service.core.healthcheck.HttpCheckReadyMvcEndPoint;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 配置对应的endpoint端点的请求不走用户密码验证
 * springboot2x的actuator的变化可以参考这篇文章 https://mp.weixin.qq.com/s?src=11&timestamp=1622343133&ver=3099&signature=3mHqimmywxRZzmHHX72sj80oItzJ5GkSqTt21nXjjR8PDaksfmNzVJsgO5DIl0najLGHTrUGpdFdTGSDtOAQrDsK7D9Es1K6LvxnEcrQSaS1bHCB-B1r4rlh7xUWwwg7&new=1
 * 另外的可参考：开启http安全头信息提升站点安全: https://blog.csdn.net/myle69/article/details/83747018
 *
 * csrf(跨站请求伪造攻击),如果有把身份令牌存cookie的情况，则需要做这个处理，没有则不可以。
 * 处理方式参考：https://www.jianshu.com/p/a205e98058cf
 * https://www.csdn.net/tags/OtTaYgysNjI2MDItYmxvZwO0O0OO0O0O.html
 * https://www.lmlphp.com/user/16538/article/item/545035/
 *
 * Xss攻击(跨站脚本攻击)，处理方式主要是对录入的文本进行转义编码，里面的<script></>等这些进行转义后再存储，以免读出后自动在页面上执行
 * 如果前后端分离，现在很多前端框架，比如VUE element Ui这些，富文本组件已经对发上来的信息做了转义的情况下，后端就不用再做了，重复转移再显示会有问题。
 * 可以参考：https://zhuanlan.zhihu.com/p/101300360
 * https://blog.csdn.net/sinat_31420295/article/details/121519010
 * https://blog.csdn.net/m0_55854679/article/details/123028852
 *
 */
@Configuration(proxyBeanMethods = false)
public class DefaultHttpSecurityConfiguration extends WebSecurityConfigurerAdapter {

//    官方文档地址(Securing HTTP Endpoints部分)https://docs.spring.io/spring-boot/docs/2.2.13.RELEASE/reference/html/production-ready-features.html#production-ready-endpoints-security

    @Override
    protected void configure(HttpSecurity security) throws Exception {

        //先设置不需要身份验证就可以访问的端点
        security.authorizeRequests()
                .requestMatchers(
                        EndpointRequest.to(
                                HttpCheckLiveMvcEndPoint.class,
                                HttpCheckReadyMvcEndPoint.class,
                                HealthEndpoint.class,
                                InfoEndpoint.class
                        )).permitAll()
        //然后剩下的设置保持和父类方法一致，这样保证其他的行为和默认一致，注意这里不能先调用super.configure方法再进行设置，因为requestMatchers必须在anyRequest之前调用
//        .anyRequest().authenticated().and().formLogin().and().httpBasic();
          //这里稍微改一下，不然按父类开了securety安全后，自己写的controller都要被拦截,这里改成其他的endpoint
          .requestMatchers(EndpointRequest.toAnyEndpoint()).authenticated()
                //剩下的全部开放
                .anyRequest().permitAll()
                //避免web后台form表单提交被springScurety拦截
//              .and().formLogin()
//                //避免web后台frame嵌套被springScurety拦截
//                .and().headers().frameOptions().disable()
//                .and().headers().frameOptions().sameOrigin() 相同来源可以嵌套
                .and().httpBasic();
//                .disable();
        //关闭csrf，默认行为是所有post请求需要检查csrftoken，不然post请求要被springsecurity默认开启的的scrfFilter拦截，这里先关闭，有需要后面再单独配置
        security.csrf().disable();
        //禁用X-Frame-Options
        //X-Frame-Options 用来告诉浏览器，页面能不能以 frame、 iframe、 object 形式嵌套在其他站点中，用来避免点击劫持(clickjacking)攻击。例如用下面代码将百度以 iframe 嵌入到自己的站点，然后监听 iframe 事件做些其他事情，用户如果不看URL估计以为自己在用百度。
        //<iframe src="https://www.baidu.com/" width="100%" height="100%" frameborder="no"></iframe>
        //可选值：
        //DENY：页面不允许在 frame 中展示
        //SAMEORIGIN：same origin，页面可以在相同域名页面的 frame 中展示
        //ALLOW-FROM uri：页面可以在指定来源的 frame 中展示
        //当你用了iframe页面嵌套页面的时候需要注意，这个默认开启，可能导致iframe嵌套失败。
        //默认使用的DENY策略，也就是不允许iframe嵌套,改为同域名可以嵌套，用于防治点击劫持(clickjacking)
        security.headers().frameOptions().sameOrigin();
        //开启浏览器端，xss防护，默认responseheader返回是X-XSS-Protection: 1; mode=block
        security.headers().xssProtection();
        //默认防火墙策略
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        //忽略swagger3所需要用到的静态资源，允许访问
//        web.ignoring().antMatchers("/swagger-ui.html",
//                                    "/swagger-ui/**",
//                                    "/swagger-resources/**",
//                                    "/v2/api-docs",
//                                    "/v3/api-docs",
//                                    "/webjars/**");
//    }

//    public static void main(String[] args) {
        //html符号转义测试
//        String sql="1' or '1'='1";
//        System.out.println("防SQL注入:"+ StringEscapeUtils.escapeSql(sql));	//防SQL注入
//
//        System.out.println("转义HTML,注意汉字:"+StringEscapeUtils.escapeHtml("<font>chen磊  xing</font>")); 	//转义HTML,注意汉字
//        System.out.println("反转义HTML:"+StringEscapeUtils.unescapeHtml("<font>chen磊  xing</font>"));	//反转义HTML
//
//
//        System.out.println("转成Unicode编码："+StringEscapeUtils.escapeJava("陈磊兴")); 	//转义成Unicode编码
//
//        System.out.println("转义XML："+StringEscapeUtils.escapeXml("<name>陈磊兴</name>")); 	//转义xml
//        System.out.println("反转义XML："+StringEscapeUtils.unescapeXml("<name>陈磊兴</name>")); 	//转义xml

        //html符号转义测试
//        System.out.println("测试1.0: "+StringEscapeUtils.escapeHtml(" <p>df</p> AND 8413=8413-- toly"));
//        System.out.println("测试1.0: "+StringEscapeUtils.escapeHtml(StringEscapeUtils.escapeHtml(" <p>df</p> AND 8413=8413-- toly")));
//        System.out.println("测试1.1: "+StringEscapeUtils.escapeHtml(" &lt;p&gt;df&lt;/p&gt; AND 8413=8413-- toly"));
//        System.out.println("测试2: "+StringEscapeUtils.escapeHtml("<script src=\"http://......com/xss/xsstest.js\"></script>"));
//    }

}