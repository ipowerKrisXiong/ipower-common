package com.ipower.framework.common.core.api;

import com.ipower.framework.common.core.exception.ExceptionUtil;
import com.ipower.framework.common.core.exception.unchecked.BusinessException;
import com.ipower.framework.common.core.exception.unchecked.UncheckedException;
import com.ipower.framework.common.core.http.HttpCode;
import com.ipower.framework.common.core.lang.ObjectUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一响应结果
 *
 * @author kris
 * @since 1.1.0
 */
@Data
@NoArgsConstructor
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -5589391309467711383L;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    ///////////////////////////// 构造函数 ///////////////////////////////////////////////////////////////////

    /**
     * 含有http状态码{@link HttpCode}的构造函数
     *
     * @param httpCode http状态码
     */
    public Result(HttpCode httpCode) {
        this(httpCode, httpCode.getMessage());
    }

    /**
     * 含有http状态码{@link HttpCode}、自定义返回消息的构造函数
     *
     * @param httpCode http状态码
     * @param message  自定义返回消息
     */
    public Result(HttpCode httpCode, String message) {
        this(httpCode, message, null);
    }

    /**
     * 含有http状态码{@link HttpCode}、返回数据的构造函数
     *
     * @param httpCode http状态码
     * @param data     返回数据
     */
    public Result(HttpCode httpCode, T data) {
        this(httpCode, httpCode.getMessage(), data);
    }

    /**
     * 含有http状态码{@link HttpCode}、自定义返回消息、返回数据的构造函数
     *
     * @param httpCode http状态码
     * @param message  自定义返回消息
     * @param data     返回数据
     */
    public Result(HttpCode httpCode, String message, T data) {
        this(httpCode.getCode(), message, data);
    }

    /**
     * 含有自定义状态码、自定义返回消息、返回数据的构造函数
     *
     * @param code    自定义状态码
     * @param message 自定义返回消息
     * @param data    返回数据
     */
    public Result(int code, String message, T data) {
        this(ObjectUtil.equals(code, HttpCode.OK.getCode()), code, message, data);
    }

    /**
     * 含有自定义状态码、自定义返回消息、返回数据的构造函数
     *
     * @param success 状态
     * @param code    自定义状态码
     * @param message 自定义返回消息
     * @param data    返回数据
     */
    public Result(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    ///////////////////////////// 安全取数据的方法 ///////////////////////////////////////////////////////////////////

    /**
     * 获取验证成功的数据，会优先判断是否响应成功，如果{@code success == true}，返回 {@link #data} 数据
     * 否则，则抛出 {@link BusinessException} 异常
     *
     * @return 验证成功的数据
     */
    public T successfulData() {
        return successfulData(this.message);
    }

    /**
     * 获取验证成功的数据，会优先判断是否响应成功，如果{@code success == true}，返回 {@link #data} 数据
     * 否则，则抛出指定异常消息的 {@link BusinessException} 异常
     *
     * @param message 指定的异常消息
     * @return 验证成功的数据
     */
    public T successfulData(String message) {
        return successfulData(this.code, message);
    }

    /**
     * 获取验证成功的数据，会优先判断是否响应成功，如果{@code success == true}，返回 {@link #data} 数据
     * 否则，则抛出指定异常编码、指定异常消息的 {@link BusinessException} 异常
     *
     * @param code    指定的异常编码
     * @param message 指定的异常消息
     * @return 验证成功的数据
     */
    public T successfulData(int code, String message) {
        return successfulData(new BusinessException(code, message));
    }

    /**
     * 获取验证成功的数据，会优先判断是否响应成功，如果{@code success == true}，返回 {@link #data} 数据
     * 否则，则抛出指定{@link HttpCode}异常消息的 {@link BusinessException} 异常
     *
     * @param httpCode 指定的异常编码
     * @return 验证成功的数据
     */
    public T successfulData(@NonNull HttpCode httpCode) {
        return successfulData(new BusinessException(httpCode.getCode(), httpCode.getMessage()));
    }

    /**
     * 获取验证成功的数据，会优先判断是否响应成功，如果{@code success == true}，返回 {@link #data} 数据
     * 否则，则抛出指定的 {@link UncheckedException} 异常
     *
     * @param ex 指定的异常
     * @return 验证成功的数据
     */
    public T successfulData(@NonNull UncheckedException ex) {
        if (success) {
            return data;
        }
        throw ex;
    }

    ///////////////////////////// 操作成功 ///////////////////////////////////////////////////////////////////

    /**
     * 操作成功
     * <p>
     * {"status": true, "code": 200, "message": "操作成功"}
     *
     * @param <T> 泛型参数
     * @return Result
     */
    public static <T> Result<T> ok() {
        return new Result<>(HttpCode.OK);
    }

    /**
     * 操作成功
     * <p>
     * {"status": true, "code": 200, "message": "操作成功", "data": {返回数据}}
     *
     * @param data 返回数据
     * @param <T>  泛型参数
     * @return Result<T>
     */
    public static <T> Result<T> ok(T data) {
        return new Result<>(HttpCode.OK, data);
    }

    /**
     * 操作成功
     * <p>
     * {"status": true, "code": 200, "message": "操作成功", "data": {返回数据}}
     *
     * @param data 返回数据
     * @param <T>  泛型参数
     * @return Result<T>
     */
    public static <T> Result<T> ok(T data, int code) {
        return new Result<>(true, code, HttpCode.OK.getMessage(), data);
    }

    /**
     * 操作成功
     * <p>
     * {"status": true, "code": 200, "message": "返回消息", "data": {返回数据}}
     *
     * @param data    返回数据
     * @param message 返回消息
     * @param <T>     泛型参数
     * @return Result<T>
     */
    public static <T> Result<T> ok(T data, String message) {
        return new Result<>(HttpCode.OK, message, data);
    }

    /**
     * 操作成功
     * <p>
     * {"status": true, "code": 200, "message": "返回消息", "data": {返回数据}}
     *
     * @param data    返回数据
     * @param code    状态码
     * @param message 返回消息
     * @param <T>     泛型参数
     * @return Result<T>
     */
    public static <T> Result<T> ok(T data, int code, String message) {
        return new Result<>(true, code, message, data);
    }

    ///////////////////////////// 请求失败 ///////////////////////////////////////////////////////////////////

    /**
     * 请求失败
     * <p>
     * {"status": false, "code": 400, "message": "请求失败"}
     *
     * @param <T> 泛型参数
     * @return Result
     */
    public static <T> Result<T> fail() {
        return new Result<>(HttpCode.FAIL);
    }

    /**
     * 请求失败
     * <p>
     * {"status": false, "code": 400, "message": "返回消息"}
     *
     * @param message 返回消息
     * @param <T>     泛型参数
     * @return Result
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(HttpCode.FAIL, message);
    }

    /**
     * 请求失败
     * <p>
     * {"status": false, "code": 状态码, "message": "返回消息"}
     *
     * @param code    状态码
     * @param message 返回消息
     * @param <T>     泛型参数
     * @return Result
     */
    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(false, code, message, null);
    }

    /**
     * 请求失败
     * <p>
     * {"status": false, "code": httpCode编号, "message": "httpCode消息"}
     *
     * @param httpCode httpCode状态码
     * @param <T>      泛型参数
     * @return Result
     */
    public static <T> Result<T> fail(HttpCode httpCode) {
        return new Result<>(false, httpCode.getCode(), httpCode.getMessage(), null);
    }

    /**
     * 请求失败
     * <p>
     * {"status": false, "code": httpCode编号, "message": "返回消息"}
     *
     * @param httpCode httpCode状态码
     * @param message  返回消息
     * @param <T>      泛型参数
     * @return Result
     */
    public static <T> Result<T> fail(HttpCode httpCode, String message) {
        return new Result<>(false, httpCode.getCode(), message, null);
    }

    /**
     * 请求失败
     * <p>
     * {"status": false, "code": httpCode编号, "message": "返回消息"}
     *
     * @param throwable 异常信息
     * @param <T>       泛型参数
     * @return Result
     */
    public static <T> Result<T> fail(Throwable throwable) {
        return fail(ExceptionUtil.getExceptionCode(throwable), ExceptionUtil.getMessage(throwable));
    }

}
