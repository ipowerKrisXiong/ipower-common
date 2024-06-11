package com.ipower.framework.common.core.http;

import com.alibaba.fastjson2.JSONObject;
import com.ipower.framework.common.core.constant.StringPool;
import com.ipower.framework.common.core.lang.ObjectUtil;
import com.ipower.framework.common.core.lang.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author kris
 */
public class HttpUtil {

    private HttpUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    public static JSONObject doPostByJson(String reqUrl, Map<String, Object> param) throws IOException {
        return doPostByJson(reqUrl, new JSONObject(param));
    }

    public static JSONObject doPostByJson(String reqUrl, JSONObject param) throws IOException {
        return doPostByJson(reqUrl, param, 20000);
    }

    public static JSONObject doPostByJson(String reqUrl, JSONObject param, Integer timeout) throws IOException {
        HttpURLConnection connection = null;
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        JSONObject returnObj = null;
        try {
            //创建连接
            URL url = new URL(reqUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setReadTimeout(ObjectUtil.nullToDefault(timeout, 60 * 1000));
            connection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            connection.connect();
            //POST请求
            out = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
            System.out.println("请求参数:" + param.toString());
            out.write(param.toString());
            out.flush();
            //读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String lines;
            StringBuilder sb = new StringBuilder();
            while ((lines = reader.readLine()) != null) {
                sb.append(lines);
            }
            System.out.println("响应参数：" + sb);
            if (!sb.isEmpty()) {
                returnObj = JSONObject.parseObject(sb.toString().replaceAll(StringPool.NEWLINE, "")
                        .replaceAll(StringPool.NULL, "\"null\""));
            }
            // 断开连接
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return returnObj;
    }

    /**
     * 根据request获取请求的ip
     *
     * @param request request对象
     * @return String IP地址
     */
    public static String getRequestIp(HttpServletRequest request) {
        //从请求头中x-forwarded-for获取ip
        String ip = request.getHeader("x-forwarded-for");
        if (ipNotEmpty(ip)) {
            return ip;
        }
        //如果ip为空，继续从请求头中Proxy-Client-IP获取ip
        ip = request.getHeader("Proxy-Client-IP");
        if (ipNotEmpty(ip)) {
            return ip;
        }
        //如果ip为空，继续从请求头中WL-Proxy-Client-IP获取ip
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (ipNotEmpty(ip)) {
            return ip;
        }
        //如果ip为空，继续从请求头中HTTP_CLIENT_IP获取ip
        ip = request.getHeader("HTTP_CLIENT_IP");
        if (ipNotEmpty(ip)) {
            return ip;
        }
        //如果ip为空，继续从请求头中HTTP_X_FORWARDED_FOR获取ip
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ipNotEmpty(ip)) {
            return ip;
        }
        //如果ip为空，继续从请求头中X-Real-IP获取ip
        ip = request.getHeader("X-Real-IP");
        //如果ip为空，从request代理对象中获取ip
        return ipNotEmpty(ip) ? ip : request.getRemoteAddr();
    }

    private static boolean ipNotEmpty(String ip) {
        return StringUtil.isNotEmpty(ip) && ObjectUtil.notEquals("unknown", StringUtil.lowerCase(ip));
    }
}
