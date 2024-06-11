package com.ipower.framework.common.core.constant;

/**
 * do something in here.
 *
 * @author diablo
 * @since 2.0.0
 */
@SuppressWarnings("unused")
public class CharPool {

    /**
     * 私有化构造函数，禁止实例化该类
     */
    private CharPool() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 换行符
     */
    public static final char LF = '\n';

    /**
     * 回车符
     */
    public static final char CR = '\r';

    /**
     * 空字符
     */
    public static final char NUL = '\0';

    /**
     * 空格
     */
    public static final char SPACE = ' ';

    /**
     * 制表符
     */
    public static final char TAB = '	';

    /**
     * 点
     */
    public static final char DOT = '.';

    /**
     * 正斜杠
     */
    public static final char SLASH = '/';

    /**
     * 反斜杠
     */
    public static final char BACKSLASH = '\\';

    /**
     * 下划线
     */
    public static final char UNDERLINE = '_';

    /**
     * 破折号
     */
    public static final char DASHED = '-';

    /**
     * 英文逗号
     */
    public static final char COMMA = ',';

    /**
     * 大括号开始
     */
    public static final char DELIM_START = '{';

    /**
     * 大括号结束
     */
    public static final char DELIM_END = '}';

    /**
     * 中括号开始
     */
    public static final char BRACKET_START = '[';

    /**
     * 中括号结束
     */
    public static final char BRACKET_END = ']';

    /**
     * 冒号
     */
    public static final char COLON = ':';

    /**
     * 双引号
     */
    public static final char DOUBLE_QUOTES = '"';

    /**
     * 单引号
     */
    public static final char SINGLE_QUOTE = '\'';

    /**
     * and 符号
     */
    public static final char AMP = '&';

    /**
     * at 符号
     */
    public static final char AT = '@';

    /**
     * 十六进制数字数组常量
     */
    public static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

}
