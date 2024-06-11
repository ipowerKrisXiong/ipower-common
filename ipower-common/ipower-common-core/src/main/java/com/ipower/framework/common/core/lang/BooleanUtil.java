package com.ipower.framework.common.core.lang;

import java.util.stream.Stream;

import static com.ipower.framework.common.core.lang.ObjectUtil.isEmpty;
import static com.ipower.framework.common.core.lang.ObjectUtil.isNull;


/**
 * Boolean类型相关工具类
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">hutool</a>
 *
 * @author kris
 * @since 1.0.0
 */
public class BooleanUtil {

    /**
     * 私有化构造函数，禁止实例化该类
     */
    private BooleanUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 表示为真的字符串
     */
    private static final String[] TRUE_ARRAY = {"true", "yes", "y", "t", "ok", "1", "on", "是", "对", "真"};

    /////////////////////////////// 公用方法 /////////////////////////////////////////////////////////////////////////

    /**
     * 给定类是否为Boolean或者boolean
     *
     * @param clazz 类
     * @return 是否为Boolean或者boolean
     */
    public static boolean isBoolean(Class<?> clazz) {
        return clazz == Boolean.class || clazz == boolean.class;
    }

    /**
     * 检查 {@code Boolean} 值是否为 {@code true}
     *
     * <pre>
     *   BooleanUtil.isTrue(Boolean.TRUE)  = true
     *   BooleanUtil.isTrue(Boolean.FALSE) = false
     *   BooleanUtil.isTrue(null)          = false
     * </pre>
     *
     * @param value 被检查的Boolean值
     * @return 当值为true且非null时返回{@code true}
     */
    public static boolean isTrue(Boolean value) {
        return ObjectUtil.equals(value, Boolean.TRUE);
    }

    /**
     * 检查 {@code String} 值是否为 {@code true}
     *
     * <pre>
     *   BooleanUtil.isTrue("yes")  = true
     *   BooleanUtil.isTrue("否") = false
     *   BooleanUtil.isTrue(null)          = false
     * </pre>
     *
     * @param value 被检查的字符串值
     * @return 当值为true且非null时返回{@code true}
     */
    public static boolean isTrue(String value) {
        return toBoolean(value);
    }

    /**
     * 取相反值
     *
     * @param value Boolean值
     * @return 相反的Boolean值
     */
    public static boolean negate(boolean value) {
        return !value;
    }

    /**
     * 取相反值
     *
     * @param value Boolean值
     * @return 相反的Boolean值
     */
    public static Boolean negate(Boolean value) {
        return isNull(value) ? null : negate(value.booleanValue());
    }

    /**
     * 转换字符串为boolean值
     *
     * @param value 字符串
     * @return boolean值
     */
    public static boolean toBoolean(String value) {
        return !isEmpty(value) && Stream.of(TRUE_ARRAY).anyMatch(it -> it.equalsIgnoreCase(value));
    }

    /**
     * 转换字符串为boolean值
     *
     * @param value 字符串
     * @return boolean值
     */
    public static boolean toBoolean(Object value) {
        return toBoolean(StringUtil.toString(value));
    }

    /**
     * boolean值转为int
     *
     * @param value Boolean值
     * @return int值
     */
    public static int toInt(boolean value) {
        return value ? 1 : 0;
    }

    /**
     * boolean值转为Integer
     *
     * @param value Boolean值
     * @return Integer值
     */
    public static Integer toInteger(boolean value) {
        return toInt(value);
    }

    /**
     * boolean值转为char
     *
     * @param value Boolean值
     * @return char值
     */
    public static char toChar(boolean value) {
        return (char) toInt(value);
    }

    /**
     * boolean值转为Character
     *
     * @param value Boolean值
     * @return Character值
     */
    public static Character toCharacter(boolean value) {
        return toChar(value);
    }

    /**
     * boolean值转为byte
     *
     * @param value Boolean值
     * @return byte值
     */
    public static byte toByte(boolean value) {
        return (byte) toInt(value);
    }

    /**
     * boolean值转为Byte
     *
     * @param value Boolean值
     * @return Byte值
     */
    public static Byte toByteObj(boolean value) {
        return toByte(value);
    }

    /**
     * boolean值转为long
     *
     * @param value Boolean值
     * @return long值
     */
    public static long toLong(boolean value) {
        return toInt(value);
    }

    /**
     * boolean值转为Long
     *
     * @param value Boolean值
     * @return Long值
     */
    public static Long toLongObj(boolean value) {
        return toLong(value);
    }

    /**
     * boolean值转为short
     *
     * @param value Boolean值
     * @return short值
     */
    public static short toShort(boolean value) {
        return (short) toInt(value);
    }

    /**
     * boolean值转为Short
     *
     * @param value Boolean值
     * @return Short值
     */
    public static Short toShortObj(boolean value) {
        return toShort(value);
    }

    /**
     * boolean值转为float
     *
     * @param value Boolean值
     * @return float值
     */
    public static float toFloat(boolean value) {
        return toInt(value);
    }

    /**
     * boolean值转为Float
     *
     * @param value Boolean值
     * @return float值
     */
    public static Float toFloatObj(boolean value) {
        return toFloat(value);
    }

    /**
     * boolean值转为double
     *
     * @param value Boolean值
     * @return double值
     */
    public static double toDouble(boolean value) {
        return toInt(value);
    }

    /**
     * boolean值转为double
     *
     * @param value Boolean值
     * @return double值
     */
    public static Double toDoubleObj(boolean value) {
        return toDouble(value);
    }

    /**
     * 将boolean转换为字符串 {@code 'true'} 或者 {@code 'false'}.
     *
     * <pre>
     *   BooleanUtil.toStringTrueFalse(true)   = "true"
     *   BooleanUtil.toStringTrueFalse(false)  = "false"
     * </pre>
     *
     * @param value Boolean值
     * @return {@code 'true'}, {@code 'false'}
     */
    public static String toTrueOrFalse(boolean value) {
        return toString(value, "true", "false");
    }

    /**
     * 将boolean转换为字符串 {@code 'true'} 或者 {@code 'false'}.
     *
     * <pre>
     *   BooleanUtil.toStringTrueFalse(true)   = "true"
     *   BooleanUtil.toStringTrueFalse(false)  = "false"
     * </pre>
     *
     * @param value Boolean值
     * @return {@code 'true'}, {@code 'false'}
     */
    public static String toTrueOrFalse(Boolean value) {
        return toString(value, "true", "false");
    }

    /**
     * 将boolean转换为字符串 {@code 'on'} 或者 {@code 'off'}.
     *
     * <pre>
     *   BooleanUtil.toStringOnOff(true)   = "on"
     *   BooleanUtil.toStringOnOff(false)  = "off"
     * </pre>
     *
     * @param value Boolean值
     * @return {@code 'on'}, {@code 'off'}
     */
    public static String toOnOrOff(boolean value) {
        return toString(value, "on", "off");
    }

    /**
     * 将boolean转换为字符串 {@code 'on'} 或者 {@code 'off'}.
     *
     * <pre>
     *   BooleanUtil.toStringOnOff(true)   = "on"
     *   BooleanUtil.toStringOnOff(false)  = "off"
     * </pre>
     *
     * @param value Boolean值
     * @return {@code 'on'}, {@code 'off'}
     */
    public static String toOnOrOff(Boolean value) {
        return toString(value, "on", "off");
    }

    /**
     * 将boolean转换为字符串 {@code 'yes'} 或者 {@code 'no'}.
     *
     * <pre>
     *   BooleanUtil.toStringYesNo(true)   = "yes"
     *   BooleanUtil.toStringYesNo(false)  = "no"
     * </pre>
     *
     * @param value Boolean值
     * @return {@code 'yes'}, {@code 'no'}
     */
    public static String toYesOrNo(boolean value) {
        return toString(value, "yes", "no");
    }

    /**
     * 将boolean转换为字符串 {@code 'yes'} 或者 {@code 'no'}.
     *
     * <pre>
     *   BooleanUtil.toStringYesNo(true)   = "yes"
     *   BooleanUtil.toStringYesNo(false)  = "no"
     * </pre>
     *
     * @param value Boolean值
     * @return {@code 'yes'}, {@code 'no'}
     */
    public static String toYesOrNo(Boolean value) {
        return toString(value, "yes", "no");
    }

    /**
     * 将boolean转换为字符串
     *
     * <pre>
     *   BooleanUtil.toString(true, "true", "false")   = "true"
     *   BooleanUtil.toString(false, "true", "false")  = "false"
     * </pre>
     *
     * @param value      Boolean值
     * @param trueValue  当值为 {@code true}时返回此字符串, 可能为 {@code null}
     * @param falseValue 当值为 {@code false}时返回此字符串, 可能为 {@code null}
     * @return 结果值
     */
    public static String toString(boolean value, String trueValue, String falseValue) {
        return value ? trueValue : falseValue;
    }

    /**
     * 将boolean转换为字符串
     *
     * <pre>
     *   BooleanUtil.toString(true, "true", "false")   = "true"
     *   BooleanUtil.toString(false, "true", "false")  = "false"
     * </pre>
     *
     * @param value      Boolean值
     * @param trueValue  当值为 {@code true}时返回此字符串, 可能为 {@code null}
     * @param falseValue 当值为 {@code false}时返回此字符串, 可能为 {@code null}
     * @return 结果值
     */
    public static String toString(Boolean value, String trueValue, String falseValue) {
        return isTrue(value) ? trueValue : falseValue;
    }
}
