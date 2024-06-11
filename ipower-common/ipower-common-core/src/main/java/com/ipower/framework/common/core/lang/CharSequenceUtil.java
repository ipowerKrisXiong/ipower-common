package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.constant.CharPool;
import com.ipower.framework.common.core.stream.Streams;

import static com.ipower.framework.common.core.constant.StringPool.EMPTY;


/**
 * 字符串处理的工具类。
 *
 * @author kris
 * @since 3.0.0
 */
public class CharSequenceUtil extends CharSequenceValidator {

    /**
     * 私有化构造函数，禁止实例化该类
     */
    protected CharSequenceUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //------------------------------- CharSequence empty or blank ---------------------------------------------


    //------------------------------- CharSequence length ---------------------------------------------

    /**
     * <p>获取字符串的长度，如果为 {@code null} 返回 {@code 0}。<p/>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.length(null)                  // 0}</li>
     *     <li>{@code StringUtil.length("")                    // 0}</li>
     *     <li>{@code StringUtil.length("alibaba")             // 7}</li>
     * </ul>
     *
     * <p>注意：和{@link String#length()}不同，此方法可以规避空指针异常。</p>
     * <br/>
     *
     * @param param 要处理的字符串
     * @return 字符串的长度
     */
    public static int length(final CharSequence param) {
        return param == null ? 0 : param.length();
    }

    //------------------------------- CharSequence count ---------------------------------------------

    /**
     * <p>统计指定参数中包含指定字符的数量。<p/>
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.count(null, '*')                   // 0}</li>
     *     <li>{@code StringUtil.count("", '*')                     // 0}</li>
     *     <li>{@code StringUtil.count("alibaba", '\n')             // 0}</li>
     *     <li>{@code StringUtil.count("alibaba", 'a')              // 3}</li>
     *     <li>{@code StringUtil.count("alibaba", (char) 97))       // 3}</li>
     *     <li>{@code StringUtil.count("alibaba", 'b')              // 2}</li>
     *     <li>{@code StringUtil.count("alibaba", (char) 98)        // 2}</li>
     *     <li>{@code StringUtil.count("alibaba", 'A')              // 0}</li>
     *     <li>{@code StringUtil.count("alibaba", (char) 65)        // 0}</li>
     * </ul>
     *
     * <p>注意：该方法用于精准匹配，不忽略大小写。</p>
     * <br/>
     *
     * @param param  被查找的字符串
     * @param search 被统计的字符
     * @return 查找到的个数
     */
    public static int count(final CharSequence param, char search) {
        if (isEmpty(param)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < param.length(); i++) {
            if (param.charAt(i) == search) {
                count++;
            }
        }
        return count;
    }

    //------------------------------- CharSequence startWith ---------------------------------------------

    /**
     * <p>判断是否以指定字符开头</p>
     * <p>如果被检测的字符串为 {@code null} 或是 ""，将会返回 {@code false}</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.startWith("", 'a')                            // false}</li>
     *     <li>{@code StringUtil.startWith("alibaba", 'a')                     // true}</li>
     *     <li>{@code StringUtil.startWith("alibaba", (char) 97)               // true}</li>
     *     <li>{@code StringUtil.startWith("alibaba", 'b')                     // false}</li>
     *     <li>{@code StringUtil.startWith("alibaba", (char) 98)               // false}</li>
     *     <li>{@code StringUtil.startWith("alibaba", 'A')                     // false}</li>
     *     <li>{@code StringUtil.startWith("alibaba", (char) 65)               // false}</li>
     * </ul>
     *
     * @param param  被检测字符串
     * @param prefix 前缀字符
     * @return 是否以指定字符开头
     */
    public static boolean startWith(final CharSequence param, final char prefix) {
        return isNotEmpty(param) && param.charAt(0) == prefix;
    }

    //------------------------------- CharSequence endWith ---------------------------------------------

    /**
     * <p>判断是否以指定字符结尾</p>
     * <p>如果被检测的字符串为 {@code null} 或是 ""，将会返回 {@code false}</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.endWith("", 'a')                            // false}</li>
     *     <li>{@code StringUtil.endWith("alibaba", 'a')                     // true}</li>
     *     <li>{@code StringUtil.endWith("alibaba", (char) 97)               // true}</li>
     *     <li>{@code StringUtil.endWith("alibaba", 'b')                     // false}</li>
     *     <li>{@code StringUtil.endWith("alibaba", (char) 98)               // false}</li>
     *     <li>{@code StringUtil.endWith("alibaba", 'A')                     // false}</li>
     *     <li>{@code StringUtil.endWith("alibaba", (char) 65)               // false}</li>
     * </ul>
     *
     * @param param  被检测字符串
     * @param suffix 后缀字符
     * @return 是否以指定字符结尾
     */
    public static boolean endWith(final CharSequence param, final char suffix) {
        return isNotEmpty(param) && param.charAt(Math.subtractExact(param.length(), 1)) == suffix;
    }

    //------------------------------- CharSequence contains ---------------------------------------------

    /**
     * <p>判断字符串中是否包含了指定的字符</p>
     * <p>如果被检测的字符串值为 {@code null}，将会返回 {@code false}</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.contains(null, 'a')                            // false}</li>
     *     <li>{@code StringUtil.contains("", 'a')                              // false}</li>
     *     <li>{@code StringUtil.contains("alibaba", 'a')                       // true}</li>
     *     <li>{@code StringUtil.contains("alibaba", (char) 97)                 // true}</li>
     *     <li>{@code StringUtil.contains("alibaba", 'b')                       // true}</li>
     *     <li>{@code StringUtil.contains("alibaba", (char) 98)                 // true}</li>
     *     <li>{@code StringUtil.contains("alibaba", 'c')                       // false}</li>
     *     <li>{@code StringUtil.contains("alibaba", (char) 99)                 // false}</li>
     *     <li>{@code StringUtil.contains("alibaba", 'A')                       // false}</li>
     *     <li>{@code StringUtil.contains("alibaba", (char) 65)                 // false}</li>
     * </ul>
     *
     * @param param  被检测字符串
     * @param search 被查找的字符
     * @return 是否包含了指定的字符
     */
    public static boolean contains(final CharSequence param, final char search) {
        return param != null && param.toString().indexOf(search) > -1;
    }

    /**
     * <p>判断字符串中是否包含了指定的字符数组中任意一个字符</p>
     * <p>如果被检测的字符串和被查找的字符数组任意一个值为 {@code null}，将会返回 {@code false}</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.containsAny(null, null)                                             // false}</li>
     *     <li>{@code StringUtil.containsAny("", null)                                               // false}</li>
     *     <li>{@code StringUtil.containsAny(null, 'a')                                              // false}</li>
     *     <li>{@code StringUtil.containsAny("alibaba", 'c' ,'d' ,'e')                               // false}</li>
     *     <li>{@code StringUtil.containsAny("alibaba", 'a' ,'b' ,'c')                               // true}</li>
     *     <li>{@code StringUtil.containsAny("alibaba", (char) 97 ,(char) 98 ,(char) 99)             // true}</li>
     *     <li>{@code StringUtil.containsAny("alibaba", 'A','B','C')                                 // false}</li>
     *     <li>{@code StringUtil.containsAny("alibaba", (char) 65 ,(char) 66 ,(char) 67)             // false}</li>
     * </ul>
     *
     * @param param    被检测字符串
     * @param searches 被检查的字符数组
     * @return 是否包含任意一个字符
     */
    public static boolean containsAny(final CharSequence param, final char... searches) {
        if (param == null || searches == null) {
            return false;
        }
        final String str = param.toString();
        for (char search : searches) {
            if (str.indexOf(search) > -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>判断字符串中是否包含了指定的字符数组中全部的字符</p>
     * <p>如果被检测的字符串和被查找的字符数组任意一个值为 {@code null}，将会返回 {@code false}</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.containsAll(null, null)                                             // false}</li>
     *     <li>{@code StringUtil.containsAll("", null)                                               // false}</li>
     *     <li>{@code StringUtil.containsAll(null, 'a')                                              // false}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", 'c' ,'d' ,'e')                               // false}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", 'a' ,'b' ,'l')                               // true}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", (char) 97 ,(char) 98 ,(char) 108)            // true}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", 'A','B','C')                                 // false}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", (char) 65 ,(char) 66 ,(char) 67)             // false}</li>
     * </ul>
     *
     * @param param    被检测字符串
     * @param searches 被检查的字符数组
     * @return 是否包含全部的字符
     */
    public static boolean containsAll(final CharSequence param, final char... searches) {
        if (param == null || searches == null) {
            return false;
        }
        final String str = param.toString();
        for (char search : searches) {
            if (str.indexOf(search) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>判断字符串中是否包含了空白符（空白符包括空格、制表符、全角空格和不间断空格）</p>
     * <p>如果给定字符串为 {@code null} 或者 ""，则返回 {@code false}</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.containsBlank(null)                        // false}</li>
     *     <li>{@code StringUtil.containsBlank("")                          // false}</li>
     *     <li>{@code StringUtil.containsBlank(" ")                         // true}</li>
     *     <li>{@code StringUtil.containsBlank("alibaba")                   // false}</li>
     *     <li>{@code StringUtil.containsBlank("alibaba ")                  // true}</li>
     *     <li>{@code StringUtil.containsBlank("alibaba\n\t")               // true}</li>
     *     <li>{@code StringUtil.containsBlank("  alibaba")                 // true}</li>
     *     <li>{@code StringUtil.containsBlank("ali ba  ba")                // true}</li>
     * </ul>
     *
     * <p>注意：此方法使用{@link CharUtil#isBlank(char)} 来循环判断组成字符串的每一个字符是否空白字符。</p>
     *
     * @param param 被检测字符串
     * @return 是否包含空白符
     */
    public static boolean containsBlank(final CharSequence param) {
        if (isEmpty(param)) {
            return false;
        }
        for (int i = 0; i < param.length(); i++) {
            if (CharUtil.isBlank(param.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    //------------------------------- CharSequence indexOf ---------------------------------------------

    /**
     * <p>在字符串中查找指定字符的索引位置。</p>
     * <p>如果给定字符串为 {@code null} 或者 ""，则返回 {@code -1}</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.indexOf(null, 'a')                    // -1}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", 'a')               // 0}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", 'b')               // 3}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", 'c')               // -1}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", 'A')               // -1}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", 'B')               // -1}</li>
     * </ul>
     *
     * <p>注意：此方法主要用规避空指针异常的问题，使用时无需判断参数是否为空。</p>
     *
     * @param param  被检测字符串
     * @param search 被查找的字符
     * @return 字符的位置，查询不到返回 {@code -1}
     */
    public static int indexOf(final CharSequence param, final char search) {
        return isEmpty(param) ? INDEX_NOT_FOUND : param.toString().indexOf(search);
    }

    /**
     * <p>在字符串中查找指定字符的最后一个索引位置。</p>
     * <p>如果给定字符串为 {@code null} 或者 ""，则返回 {@code -1}</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.lastIndexOf(null, 'a')                    // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", 'a')               // 6}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", 'b')               // 5}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", 'c')               // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", 'A')               // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", 'B')               // -1}</li>
     * </ul>
     *
     * <p>注意：此方法主要用规避空指针异常的问题，使用时无需判断参数是否为空。</p>
     *
     * @param param  被检测字符串
     * @param search 被查找的字符
     * @return 字符的最后一个位置，查询不到返回 {@code -1}
     */
    public static int lastIndexOf(final CharSequence param, final char search) {
        return isEmpty(param) ? INDEX_NOT_FOUND : param.toString().lastIndexOf(search);
    }

    //------------------------------- CharSequence remove ---------------------------------------------

    /**
     * <p>从字符串中删除指定的字符，会删除所有匹配的字符</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param  被检测字符串
     * @param search 待删除的字符
     * @return 删除指定字符后的字符串
     */
    public static String remove(final CharSequence param, final char search) {
        return remove(param, new char[]{search});
    }

    /**
     * <p>从字符串中删除指定字符数组中的全部字符</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     * <p>如果被删除的字符数组为空，会返回被检测的字符串</p>
     *
     * @param param    被检测字符串
     * @param searches 待删除的字符数组
     * @return 删除指定字符后的字符串
     */
    public static String remove(final CharSequence param, final char... searches) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        // 待删除的字符数组为空，直接返回参数字符串
        if (searches == null || searches.length < 1) {
            return param.toString();
        }
        // 循环删除待删除的字符集
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < param.length(); i++) {
            char c = param.charAt(i);
            boolean exists = false;
            for (char search : searches) {
                if (c == search) {
                    exists = true;
                    break;
                }
            }
            // 待删除的字符不存在，添加到builder中
            if (!exists) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * <p>删除字符串的第一个字符</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param 被检测字符串
     * @return 删除指定字符后的字符串
     */
    public static String removeFirst(final CharSequence param) {
        return isEmpty(param) ? EMPTY : param.toString().substring(1);
    }

    /**
     * <p>删除字符串中第一个指定的字符</p>
     * <p>待删除的字符如果出现多次，只会删除第一次出现的那个字符</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param  被检测字符串
     * @param search 待删除的字符
     * @return 删除指定字符后的字符串
     */
    public static String removeFirst(final CharSequence param, final char search) {
        return removeIndex(param, indexOf(param, search));
    }

    /**
     * <p>删除字符串的最后一个字符</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param 被检测字符串
     * @return 删除指定字符后的字符串
     */
    public static String removeLast(final CharSequence param) {
        return isEmpty(param) ? EMPTY : param.toString().substring(0, param.length() - 1);
    }

    /**
     * <p>删除字符串中最后一个指定的字符</p>
     * <p>待删除的字符如果出现多次，只会删除最后一次出现的那个字符</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param  被检测字符串
     * @param search 待删除的字符
     * @return 删除指定字符后的字符串
     */
    public static String removeLast(final CharSequence param, final char search) {
        return removeIndex(param, lastIndexOf(param, search));
    }

    /**
     * <p>删除字符串中指定位置的字符</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     * <p>如果指定位置 {@code <0 } 或 {@code >= 被检测字符串的长度 }，直接返回被检测字符串</p>
     *
     * @param param 被检测字符串
     * @param index 待删除字符的位置
     * @return 删除指定位置字符后的字符串
     */
    public static String removeIndex(final CharSequence param, final int index) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        // 表示位置
        if (index < 0 || index >= param.length()) {
            return param.toString();
        }
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < param.length(); i++) {
            char c = param.charAt(i);
            if (i == index) {
                continue;
            }
            builder.append(c);
        }
        return builder.toString();
    }

    /**
     * <p>删除字符串中空白字符</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     * <p>注意：此方法使用{@link CharUtil#isBlank(char)} 来循环判断组成字符串的每一个字符是否空白字符。</p>
     *
     * @param param 被检测字符串
     * @return 删除空白字符后的字符串
     */
    public static String removeBlank(final CharSequence param) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        // 循环删除待删除的字符集
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < param.length(); i++) {
            char c = param.charAt(i);
            if (!CharUtil.isBlank(c)) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * <p>删除字符串中的换行符，换行符包括：</p>
     * <ol>
     *     <li>{@code \r}</li>
     *     <li>{@code \n}</li>
     * </ol>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param 被检测字符串
     * @return 删除换行符后的字符串
     */
    public static String removeLineBreaks(final CharSequence param) {
        return remove(param, CharPool.CR, CharPool.LF);
    }

    //------------------------------- CharSequence replace ---------------------------------------------

    /**
     * <p>替换字符串中指定的字符，会替换所有匹配到的字符</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param       被检测字符串
     * @param search      待替换的字符
     * @param replacement 被替换的字符
     * @return 替换之后的字符串
     */
    public static String replace(final CharSequence param, final char search, final char replacement) {
        return isEmpty(param) ? EMPTY : param.toString().replace(search, replacement);
    }

    /**
     * <p>替换字符串中第一个指定的字符</p>
     * <p>待替换的字符如果出现多次，只会替换第一次出现的那个字符</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param       被检测字符串
     * @param search      待替换的字符
     * @param replacement 被替换的字符
     * @return 替换之后的字符串
     */
    public static String replaceFirst(final CharSequence param, final char search, final char replacement) {
        return replaceIndex(param, indexOf(param, search), replacement);
    }

    /**
     * <p>替换字符串中最后一个指定的字符</p>
     * <p>待替换的字符如果出现多次，只会替换最后一次出现的那个字符</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param       被检测字符串
     * @param search      待替换的字符
     * @param replacement 被替换的字符
     * @return 替换之后的字符串
     */
    public static String replaceLast(final CharSequence param, final char search, final char replacement) {
        return replaceIndex(param, lastIndexOf(param, search), replacement);
    }

    /**
     * <p>替换字符串中指定位置的字符</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     * <p>如果指定位置 {@code <0 } 或 {@code >= 被检测字符串的长度 }，直接返回被检测字符串</p>
     *
     * @param param       被检测字符串
     * @param index       待替换字符的位置
     * @param replacement 被替换的字符
     * @return 替换指定位置字符之后的字符串
     */
    public static String replaceIndex(final CharSequence param, final int index, final char replacement) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        // 索引位置不合规
        if (index < 0 || index >= param.length()) {
            return param.toString();
        }
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < param.length(); i++) {
            char c = param.charAt(i);
            // 索引一致，替换字符
            if (i == index) {
                builder.append(replacement);
                continue;
            }
            builder.append(c);
        }
        return builder.toString();
    }

    //-------------------------------CharSequence convert camel ---------------------------------------------

    /**
     * 驼峰格式字符串转下划线格式字符串。
     *
     * @param param 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String camelToUnderline(final CharSequence param) {
        return camelToCharacter(param, CharPool.UNDERLINE);
    }

    /**
     * 驼峰格式字符串数组转下划线格式字符串数组。
     *
     * @param params 需要转换的字符串数组
     * @return 转换后的字符串数组
     */
    public static String[] camelToUnderline(final CharSequence... params) {
        return Streams.of(params).map(CharSequenceUtil::camelToUnderline).toArray(String[]::new);
    }

    /**
     * 驼峰格式字符串转自定义格式的字符串
     *
     * @param param 需要转换的字符串
     * @param ch    自定义格式字符
     * @return 转换后的字符串
     */
    public static String camelToCharacter(final CharSequence param, final char ch) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append(ch);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 下划线格式字符串转驼峰格式字符串
     *
     * @param param 需要转换的字符串
     * @return 驼峰格式字符串
     */
    public static String underlineToCamel(final CharSequence param) {
        return characterToCamel(param, CharPool.UNDERLINE);
    }

    /**
     * 下划线格式字符串数组转驼峰格式字符串数组。
     *
     * @param params 需要转换的字符串数组
     * @return 驼峰格式字符串数组
     */
    public static String[] underlineToCamel(final CharSequence... params) {
        return Streams.of(params).map(CharSequenceUtil::underlineToCamel).toArray(String[]::new);
    }

    /**
     * 自定义格式字符串转驼峰格式字符串
     *
     * @param ch    自定义格式字符
     * @param param 需要转换的字符串
     * @return 驼峰格式的字符串
     */
    public static String characterToCamel(final CharSequence param, final char ch) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        boolean upperCase = false;
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == ch) {
                upperCase = true;
                continue;
            }
            // 如果是首字母，需要转小写处理
            if (sb.isEmpty()) {
                sb.append(Character.toLowerCase(c));
            }
            // 如果需要转大写处理
            else if (upperCase) {
                sb.append(Character.toUpperCase(c));
            }
            // 其它情况，不处理
            else {
                sb.append(c);
            }
            // 重置转大写标记
            upperCase = false;
        }
        return sb.toString();
    }

}
