package com.ipower.framework.common.core.exception.unchecked;

import com.ipower.framework.common.core.http.HttpCode;

import java.io.Serial;

/**
 * Token相关异常
 *
 * @author hcs
 */
public class TokenException extends UncheckedException {

    public static final int TOKEN_EXPIRED = 40301;
    public static final int TOKEN_EMPTY = 40302;
    public static final int TOKEN_ERROR = 40303;

    @Serial
    private static final long serialVersionUID = -1572640165745482068L;

    private static final HttpCode HTTP_CODE = HttpCode.TOKEN_FORBIDDEN;

    public TokenException() {
        super(HTTP_CODE.getCode(), HTTP_CODE.getMessage());
    }

    public TokenException(String message) {
        super(HTTP_CODE.getCode(), message);
    }

    public TokenException(String message, Throwable throwable) {
        super(HTTP_CODE.getCode(), message, throwable);
    }

    public TokenException(Throwable throwable) {
        super(HTTP_CODE.getCode(), HTTP_CODE.getMessage(), throwable);
    }

    public TokenException(Integer code) {
        super(code, HTTP_CODE.getMessage());
    }

    public TokenException(Integer code, String message) {
        super(code, message);
    }

    public TokenException(Integer code, Throwable throwable) {
        super(code, HTTP_CODE.getMessage(), throwable);
    }

    public TokenException(Integer code, String message, Throwable throwable) {
        super(code, message, throwable);
    }
}
