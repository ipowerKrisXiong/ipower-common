package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.constant.CharPool;

import static com.ipower.framework.common.core.lang.ObjectUtil.*;

/**
 * 字符工具类
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">hutool</a>
 *
 * @author kris
 * @since 1.0.0
 */
public class CharUtil {

    /**
     * 私有化构造函数，禁止实例化该类
     */
    private CharUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 字符数组常量
     */
    private static final String[] CHAR_STRING_ARRAY = new String[128];


    /*
      构造ASCII字符的缓存数据
     */
    static {
        for (char c = 0; c < CHAR_STRING_ARRAY.length; c++) {
            CHAR_STRING_ARRAY[c] = String.valueOf(c);
        }
    }

    //-----------------------------------------------------------------------

    /**
     * 是否为ASCII字符，ASCII字符位于0~127之间
     *
     * <pre>
     *   CharUtil.isAscii('a')  = true
     *   CharUtil.isAscii('A')  = true
     *   CharUtil.isAscii('3')  = true
     *   CharUtil.isAscii('-')  = true
     *   CharUtil.isAscii('\n') = true
     *   CharUtil.isAscii('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符处
     * @return true表示为ASCII字符，ASCII字符位于0~127之间
     */
    public static boolean isAscii(char ch) {
        return ch < 128;
    }

    /**
     * 给定对象对应的类是否为字符类，字符类包括：
     *
     * <pre>
     * Character.class
     * char.class
     * </pre>
     *
     * @param obj 被检查的对象
     * @return true表示为字符类
     */
    public static boolean isChar(Object obj) {
        return isNotNull(obj) && obj instanceof Character;
    }

    /**
     * <p>将字符串的第一个字符转换为字符，为空的情况下抛出空字符串上的异常.</p>
     *
     * <pre>
     *   CharUtils.toChar("A")  = 'A'
     *   CharUtils.toChar("BA") = 'B'
     *   CharUtils.toChar(null) throws IllegalArgumentException
     *   CharUtils.toChar("")   throws IllegalArgumentException
     * </pre>
     *
     * @param str 要转换的字符
     * @return 字符串第一个字母的字符值
     * @throws IllegalArgumentException 如果字符串为空
     */
    public static char toChar(final String str) {
        return Validate.notEmpty(str, "The String must not be empty").charAt(0);
    }

    /**
     * <p>将字符串的第一个字符转换为字符，为空的情况下使用默认值.</p>
     *
     * <pre>
     *   CharUtils.toChar(null, 'X') = 'X'
     *   CharUtils.toChar("", 'X')   = 'X'
     *   CharUtils.toChar("A", 'X')  = 'A'
     *   CharUtils.toChar("BA", 'X') = 'B'
     * </pre>
     *
     * @param str          要转换的字符
     * @param defaultValue 字符为空时使用的值
     * @return 字符串第一个字母的字符值，如果为空则为默认值
     */
    public static char toChar(final String str, final char defaultValue) {
        return isEmpty(str) ? defaultValue : str.charAt(0);
    }

    /**
     * <p>将字符转换为它表示的整数，如果字符不是数字，则引发异常.</p>
     * <p>此方法将字符“1”转换为int 1，依此类推.</p>
     *
     * <pre>
     *   CharUtils.toIntValue('3')  = 3
     *   CharUtils.toIntValue(null) throws IllegalArgumentException
     *   CharUtils.toIntValue('A')  throws IllegalArgumentException
     * </pre>
     *
     * @param ch 要转换的字符, 不能为空
     * @return 字符的int值
     * @throws IllegalArgumentException 如果字符不是ascii数字或为空
     */
    public static int toInt(final Character ch) {
        Validate.notNull(ch, "The character must not be null");
        Validate.isTrue(Character.isDigit(ch), "The character " + ch + " is not in the range '0' - '9'");
        return ch - 48;
    }

    /**
     * <p>将字符转换为它表示的整数，如果字符不是数字，使用默认值.</p>
     * <p>此方法将字符“1”转换为int 1，依此类推.</p>
     *
     * <pre>
     *   CharUtils.toIntValue(null, -1) = -1
     *   CharUtils.toIntValue('3', -1)  = 3
     *   CharUtils.toIntValue('A', -1)  = -1
     * </pre>
     *
     * @param ch           要转换的字符
     * @param defaultValue 如果字符不是数字，则使用的默认值
     * @return 字符的int值
     */
    public static int toInt(final Character ch, final int defaultValue) {
        return (isNull(ch) || !Character.isDigit(ch)) ? defaultValue : ch - 48;
    }

    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     */
    public static boolean isBlank(char c) {
        return isBlank((int) c);
    }

    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     */
    public static boolean isBlank(int c) {
        //noinspection UnnecessaryUnicodeEscape
        return Character.isWhitespace(c)
                || Character.isSpaceChar(c)
                || c == '\ufeff'
                || c == '\u202a'
                || c == '\u0000'
                // Hangul Filler
                || c == '\u3164'
                // Braille Pattern Blank
                || c == '\u2800'
                // MONGOLIAN VOWEL SEPARATOR
                || c == '\u180e';
    }

    /**
     * <p>将字符串转换为Unicode格式 '\\u0020'.</p>
     *
     * <p>此格式是Java源代码格式.</p>
     *
     * <pre>
     *   CharUtils.unicodeEscaped(null) = null
     *   CharUtils.unicodeEscaped(' ')  = "\\u0020"
     *   CharUtils.unicodeEscaped('A')  = "\\u0041"
     * </pre>
     *
     * @param ch 要转换的字符
     * @return 转义的Unicode字符串
     */
    public static String unicodeEscaped(final Character ch) {
        return isNull(ch) ? null : "\\u" +
                CharPool.HEX_DIGITS[(ch >> 12) & 15] +
                CharPool.HEX_DIGITS[(ch >> 8) & 15] +
                CharPool.HEX_DIGITS[(ch >> 4) & 15] +
                CharPool.HEX_DIGITS[(ch) & 15];
    }

    /**
     * <p>将字符转换为包含一个字符的字符串.</p>
     * <p>对于ascii 7位字符，它使用一个缓存，该缓存将返回每次都是相同的字符串对象.</p>
     *
     * <pre>
     *   CharUtils.toString(null) = null
     *   CharUtils.toString(' ')  = " "
     *   CharUtils.toString('A')  = "A"
     * </pre>
     *
     * @param ch 要转换的字符
     * @return 包含一个指定字符的字符串
     */
    public static String toString(final Character ch) {
        return isNull(ch) ? null : isAscii(ch) ? CHAR_STRING_ARRAY[ch] : String.valueOf(ch);
    }
}
