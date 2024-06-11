package com.ipower.framework.common.core.exception.unchecked;

import lombok.Getter;

import java.io.Serial;

/**
 * 包装了unchecked类型的异常，为其增加一个code的错误编码，方便后续统一异常处理
 *
 * @author kris
 */
@Getter
public class UncheckedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8197213782182549017L;

    private Integer code;

    public UncheckedException() {
        super();
    }

    public UncheckedException(String message) {
        super(message);
    }

    public UncheckedException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public UncheckedException(Throwable cause) {
        super(cause);
    }

    public UncheckedException(Integer code) {
        this.code = code;
    }

    public UncheckedException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public UncheckedException(Integer code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public UncheckedException(Integer code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

}
