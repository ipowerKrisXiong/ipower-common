package com.ipower.framework.common.core.exception;

import com.ipower.framework.common.core.exception.unchecked.UncheckedException;
import com.ipower.framework.common.core.lang.ObjectUtil;
import com.ipower.framework.common.core.lang.StringUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

import static com.ipower.framework.common.core.lang.ObjectUtil.isEmpty;
import static com.ipower.framework.common.core.lang.ObjectUtil.isNull;

/**
 * 异常工具类
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @author kris
 * @since 1.0.0
 */
public final class ExceptionUtil {

    public static final int DEFAULT_EXCEPTION_CODE = 400;

    private static final String NULL = "null";

    private static final String NULL_POINTER = "null pointer";

    /**
     * 私有化构造函数，禁止实例化该类
     */
    private ExceptionUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 获取异常编号
     *
     * @param throwable 异常数据对象
     * @return int 异常编码
     */
    public static int getExceptionCode(final Throwable throwable) {
        if (throwable instanceof UncheckedException) {
            return ObjectUtil.nullToDefault(((UncheckedException) throwable).getCode(), DEFAULT_EXCEPTION_CODE);
        }
        return DEFAULT_EXCEPTION_CODE;
    }

    /**
     * 获得完整消息，包括异常名、异常消息
     *
     * @param e 异常
     * @return 完整消息
     */
    public static String getMessage(Throwable e) {
        return isNull(e) ? NULL : StringUtil.format("{}: {}", e.getClass().getSimpleName(), e.getMessage());
    }

    /**
     * 获得简洁消息，只包含异常消息
     *
     * @param e 异常
     * @return 简洁消息
     */
    public static String getSimpleMessage(Throwable e) {
        return isNull(e) ? NULL : isEmpty(e.getMessage()) ? NULL_POINTER : e.getMessage();
    }

    /**
     * 将异常包装为本地UncheckedException异常
     *
     * @param throwable 异常
     * @return 运行时异常
     */
    public static UncheckedException wrapUnchecked(Throwable throwable) {
        return (throwable instanceof UncheckedException) ? (UncheckedException) throwable : new UncheckedException(400, throwable.getMessage());
    }

    /**
     * 使用运行时异常包装编译异常
     *
     * @param throwable 异常
     * @return 运行时异常
     */
    public static RuntimeException wrapRuntime(Throwable throwable) {
        return (throwable instanceof RuntimeException) ? (RuntimeException) throwable : new RuntimeException(throwable);
    }

    /**
     * 获取当前栈信息
     *
     * @return 当前栈信息
     */
    public static StackTraceElement[] getStackElements() {
        return Thread.currentThread().getStackTrace();
    }

    /**
     * 获取指定层的堆栈信息
     *
     * @return 指定层的堆栈信息
     */
    public static StackTraceElement getStackElement(int i) {
        return getStackElements()[i];
    }

    /**
     * 获取入口堆栈信息
     *
     * @return 入口堆栈信息
     */
    public static StackTraceElement getRootStackElement() {
        final StackTraceElement[] stackElements = getStackElements();
        return stackElements[stackElements.length - 1];
    }

    /**
     * 获取异常栈信息
     */
    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }
}
