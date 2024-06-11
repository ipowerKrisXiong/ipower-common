package com.ipower.framework.common.core.exception.unchecked;

import java.io.Serial;

/**
 * 验证时异常，这里主要是用来验证数据时产生的异常
 *
 * @author kris
 */
public class ValidateException extends UncheckedException {

    @Serial
    private static final long serialVersionUID = -1572640165745482068L;

    public ValidateException() {
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException(Integer code) {
        super(code);
    }

    public ValidateException(Integer code, String message) {
        super(code, message);
    }

    public ValidateException(Integer code, Throwable throwable) {
        super(code, throwable);
    }

    public ValidateException(Integer code, String message, Throwable throwable) {
        super(code, message, throwable);
    }
}
