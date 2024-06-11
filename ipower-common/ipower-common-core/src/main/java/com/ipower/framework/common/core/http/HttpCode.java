package com.ipower.framework.common.core.http;

import com.ipower.framework.common.core.constant.StringPool;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Http状态码.
 *
 * @author kris
 * @since 1.1.0
 */
@Data
@AllArgsConstructor
public class HttpCode {

    // region standard

    /**
     * 操作成功
     */
    public static final HttpCode OK = build(200, "操作成功", "ok");

    /**
     * 请求失败
     */
    public static final HttpCode FAIL = build(400, "请求失败", "bad request");

    /**
     * 请求未授权
     */
    public static final HttpCode UNAUTHORIZED = build(401, "请求未授权", "unauthorized");

    /**
     * 请求被拒绝
     */
    public static final HttpCode FORBIDDEN = build(403, "请求被拒绝", "forbidden");

    /**
     * 404 找到不请求
     */
    public static final HttpCode NOT_FOUND = build(404, "404 找不到请求", "not found");

    /**
     * 请求方法被禁止
     */
    public static final HttpCode METHOD_NOT_ALLOWED = build(405, "请求方法被禁止", "method not allowed");

    /**
     * 请求超时
     */
    public static final HttpCode REQUEST_TIMEOUT = build(408, "请求超时", "request timeout");

    /**
     * 请求发生冲突
     */
    public static final HttpCode CONFLICT = build(409, "请求发生冲突", "conflict");

    /**
     * 请求资源已被删除
     */
    public static final HttpCode GONE = build(410, "请求资源已被删除", "gone");

    /**
     * 不支持的媒体类型
     */
    public static final HttpCode UNSUPPORTED_MEDIA_TYPE = build(415, "不支持的媒体类型", "unsupported media type");

    /**
     * 服务器异常
     */
    public static final HttpCode INTERNAL_SERVER_ERROR = build(500, "500 服务器异常", "internal server error");

    // endregion

    // region custom

    /**
     * 默认token异常
     */
    public static final HttpCode TOKEN_FORBIDDEN = build(4201, "请求token被拒绝", "token forbidden");

    /**
     * 默认参数校验失败
     */
    public static final HttpCode PARAMETER_VERIFICATION_FAILED = build(4301, "参数校验失败", "parameter verification failed");

    /**
     * 默认业务处理失败
     */
    public static final HttpCode BUSINESS_HANDLER_FAILED = build(4401, "业务处理失败", "business handler failed");

    /**
     * 默认FeignClient异常
     */
    public static final HttpCode FEIGN_CLIENT_FALLBACK = build(4501, "FeignClient请求异常", "feign client fallback");

    // endregion

    /**
     * 构建httpCode的方法
     *
     * @param code    响应编码
     * @param message 消息
     * @return HttpCode对象
     */
    public static HttpCode build(int code, String message) {
        return new HttpCode(code, message, StringPool.EMPTY);
    }

    /**
     * 构建httpCode的方法
     *
     * @param code        响应编码
     * @param message     消息
     * @param description 描述信息
     * @return HttpCode对象
     */
    public static HttpCode build(int code, String message, String description) {
        return new HttpCode(code, message, description);
    }

    /**
     * 响应编码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应描述信息
     */
    private String description;

}
