package com.ipower.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/**
 * 自定义初始化防火墙，参考文档：
 * https://www.freesion.com/article/7534811944/
 * https://blog.csdn.net/qq_39748549/article/details/123426917
 * HttpFirewall是spring security提供的HTTP防火墙，它可以用于拒绝潜在的危险请求或者包装这些请求进而控制其行为。
 * HttpFirewall被注入到FilterChainProxy中，并在spring security过滤器链执行之前被触发。
 */
@Configuration
public class HttpFirewallConfiguration {

    /**
     * 这里new一个自己的防火墙出来,方便做定制
     * 有DefaultHttpFirewall和StrictHttpFirewall，默认使用StrictHttpFirewall，这里采用严格的防火墙
     * 默认创建是在FilterChainProxy中new出来的。
     * 代码：FilterChainProxy  private HttpFirewall firewall = new StrictHttpFirewall();
     * 防火墙生效入口：
     * private void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain)
     *         throws IOException, ServletException {
     *     // 请求的校验主要是在getFirewalledRequest方法中完成的。在进入spring security过滤器链之前，请求对象和响应对象
     *     // 都分别换成FirewalledRequest和FirewalledResponse了
     *     FirewalledRequest firewallRequest = this.firewall.getFirewalledRequest((HttpServletRequest) request);
     *     HttpServletResponse firewallResponse = this.firewall.getFirewalledResponse((HttpServletResponse) response);
     *     List<Filter> filters = getFilters(firewallRequest);
     *     // 省略其他
     *     virtualFilterChain.doFilter(firewallRequest, firewallResponse);
     * }
     * @return
     */
    @Bean
    HttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        //设置允许的请求type默认都可以,默认是8种
        //firewall.setUnsafeAllowAnyHttpMethod(true);
        //请求地址可以有分号，比如http://localhost:8080/hello;jsessionid=xx，为了支持某些框架这样传参，就需要开启
        //注意，在 URL 地址中，; 编码之后是 %3b 或者 %3B，所以地址中同样不能出现 %3b 或者 %3B
//        firewall.setAllowSemicolon(true);
        //判定必须是标准化 URL,路径不能包含"./", "/../" or "/."，这样可以防止Path Traversal 路径遍历攻击，以免往上访问
        //源码中StrictHttpFirewall#isNormalized方法判断
        //如果请求地址中包含不可打印的 ASCII 字符，请求则会被拒绝，我们可以从源码中看出端倪：
        //判断如果请求地址中包含不可打印的 ASCII 字符，请求则会被拒绝，我们可以从源码中看出端倪：
        //StrictHttpFirewall#containsOnlyPrintableAsciiCharacters

        //双斜杠不被允许
        //如果请求地址中出现双斜杠，这个请求也将被拒绝。双斜杠 // 使用 URL 地址编码之后，是 %2F%2F，其中 F 大小写无所谓，所以请求地址中也能不出现 “%2f%2f”, “%2f%2F”, “%2F%2f”, “%2F%2F”。
//        firewall.setAllowUrlEncodedDoubleSlash(true)
        //如果请求地址中出现 %，这个请求也将被拒绝。URL 编码后的 % 是 %25，所以 %25 也不能出现在 URL 地址中。
//        firewall.setAllowUrlEncodedPercent(true);
        //正反斜杠不被允许
        //firewall.setAllowBackSlash(true);
        //firewall.setAllowUrlEncodedSlash(true);
        //. 不被允许
        //如果请求地址中存在 . 编码之后的字符 %2e、%2E，则请求将被拒绝。
        //如需支持，按照如下方式进行配置
        //firewall.setAllowUrlEncodedPeriod(true);

        //需要强调一点，上面所说的这些限制，都是针对请求的 requestURI 进行的限制，而不是针对请求参数。例如你的请求格式是：
        //http://localhost:8080/hello?param=aa%2ebb
        //则不受上面限制

        return firewall;
    }

}
