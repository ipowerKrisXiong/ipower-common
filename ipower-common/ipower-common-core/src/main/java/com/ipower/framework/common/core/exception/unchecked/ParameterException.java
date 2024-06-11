package com.ipower.framework.common.core.exception.unchecked;

import com.ipower.framework.common.core.http.HttpCode;

import java.io.Serial;

/**
 * Class description goes here.
 *
 * @author kris
 */
public class ParameterException extends ValidateException {

    @Serial
    private static final long serialVersionUID = 2574576984307187551L;

    private static final HttpCode HTTP_CODE = HttpCode.PARAMETER_VERIFICATION_FAILED;

    public ParameterException() {
        super(HTTP_CODE.getCode(), HTTP_CODE.getMessage());
    }

    public ParameterException(String message) {
        super(HTTP_CODE.getCode(), message);
    }

    public ParameterException(String message, Throwable throwable) {
        super(HTTP_CODE.getCode(), message, throwable);
    }

    public ParameterException(Throwable throwable) {
        super(HTTP_CODE.getCode(), HTTP_CODE.getMessage(), throwable);
    }

    public ParameterException(Integer code) {
        super(code, HTTP_CODE.getMessage());
    }

    public ParameterException(Integer code, String message) {
        super(code, message);
    }

    public ParameterException(Integer code, Throwable throwable) {
        super(code, HTTP_CODE.getMessage(), throwable);
    }

    public ParameterException(Integer code, String message, Throwable throwable) {
        super(code, message, throwable);
    }
}
