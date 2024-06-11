package com.ipower.framework.common.core.exception.unchecked;

import com.ipower.framework.common.core.http.HttpCode;

import java.io.Serial;

/**
 * 业务功能异常.
 *
 * @author kris
 */
public class BusinessException extends UncheckedException {

    @Serial
    private static final long serialVersionUID = -340611234487220247L;

    private static final HttpCode HTTP_CODE = HttpCode.BUSINESS_HANDLER_FAILED;

    public BusinessException() {
        super(HTTP_CODE.getCode(), HTTP_CODE.getMessage());
    }

    public BusinessException(String message) {
        super(HTTP_CODE.getCode(), message);
    }

    public BusinessException(String message, Throwable throwable) {
        super(HTTP_CODE.getCode(), message, throwable);
    }

    public BusinessException(Throwable throwable) {
        super(HTTP_CODE.getCode(), HTTP_CODE.getMessage(), throwable);
    }

    public BusinessException(Integer code) {
        super(code, HTTP_CODE.getMessage());
    }

    public BusinessException(Integer code, String message) {
        super(code, message);
    }

    public BusinessException(Integer code, Throwable throwable) {
        super(code, HTTP_CODE.getMessage(), throwable);
    }

    public BusinessException(Integer code, String message, Throwable throwable) {
        super(code, message, throwable);
    }
}
