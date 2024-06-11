package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.collection.ArrayUtil;
import com.ipower.framework.common.core.collection.Lists;
import com.ipower.framework.common.core.constant.StringPool;
import com.ipower.framework.common.core.stream.Streams;
import com.ipower.framework.common.core.text.StringFormatter;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.ipower.framework.common.core.constant.StringPool.EMPTY;
import static com.ipower.framework.common.core.lang.ObjectUtil.isNotNull;
import static com.ipower.framework.common.core.lang.ObjectUtil.isNull;

/**
 * 字符串处理的工具类。
 *
 * @author kris
 * @since 1.0.0
 */
public final class StringUtil extends CharSequenceUtil {

    /**
     * 私有化构造函数，禁止实例化该类
     */
    private StringUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 字符串常量系列
     */
    public static final String IS = "is";
    public static final String GET = "get";
    public static final String SET = "set";
    public static final String EMPTY_JSON = StringPool.LEFT_BRACE.concat(StringPool.RIGHT_BRACE);


    /**
     * <p>检测字符串如果是{@code null}，会返回空字符串，否则将返回字符串本身。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.nullToEmpty(null)       // ""}</li>
     *     <li>{@code StringUtil.nullToDefault("")       // ""}</li>
     *     <li>{@code StringUtil.nullToDefault(" ")      // " "}</li>
     *     <li>{@code StringUtil.nullToDefault("abc")    // "abc"}</li>
     * </ul>
     *
     * <p>建议：改方法适用于在程序中需要使用获取到的字符串时，省去非空判断来规避空指针异常</p>
     * <br/>
     *
     * @param param 被检测的字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String nullToEmpty(final String param) {
        return nullToDefault(param, EMPTY);
    }

    /**
     * <p>检测字符串如果是{@code null}，会返回指定的默认字符串，否则将返回字符串本身。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.nullToDefault(null, "defaultString")     // "defaultString"}</li>
     *     <li>{@code StringUtil.nullToDefault("", "defaultString")       // ""}</li>
     *     <li>{@code StringUtil.nullToDefault(" ", "defaultString")      // " "}</li>
     *     <li>{@code StringUtil.nullToDefault("\t\n", "defaultString")    // "\t\n"}</li>
     *     <li>{@code StringUtil.nullToDefault("abc", "defaultString")    // "abc"}</li>
     * </ul>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>该方法只检测字符串是否为{@code null}。</li>
     *     <li>若还需要检测是否为空字符串，建议采用 {@link #emptyToDefault(String, String)}</li>
     *     <li>若还需要检测是否为空白字符串，建议采用 {@link #blankToDefault(String, String)}</li>
     * </ul>
     *
     * @param param         被检测的字符串
     * @param defaultString 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String nullToDefault(final String param, final String defaultString) {
        return param == null ? defaultString : param;
    }

    /**
     * <p>检测字符串如果是{@code null}或是空，会返回指定的默认字符串，否则将返回字符串本身。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.emptyToDefault(null, "defaultString")     // "defaultString"}</li>
     *     <li>{@code StringUtil.emptyToDefault("", "defaultString")       // "defaultString"}</li>
     *     <li>{@code StringUtil.emptyToDefault(" ", "defaultString")      // " "}</li>
     *     <li>{@code StringUtil.emptyToDefault("\t\n", "defaultString")   // "\t\n"}</li>
     *     <li>{@code StringUtil.emptyToDefault("abc", "defaultString")    // "abc"}</li>
     * </ul>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>该方法只检测字符串是否为{@code null}或是空字符串。</li>
     *     <li>若还需要检测是否为空白字符串，建议采用 {@link #blankToDefault(String, String)}</li>
     *     <li>若只需要检测字符串是否为{@code null}，请使用 {@link #nullToDefault(String, String)}</li>
     * </ul>
     *
     * @param param         被检测的字符串
     * @param defaultString 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String emptyToDefault(final String param, final String defaultString) {
        return isEmpty(param) ? defaultString : param;
    }

    /**
     * <p>检测字符串如果是{@code null}或是空白，会返回指定的默认字符串，否则将返回字符串本身。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.blankToDefault(null, "defaultString")     // "defaultString"}</li>
     *     <li>{@code StringUtil.blankToDefault("", "defaultString")       // "defaultString"}</li>
     *     <li>{@code StringUtil.blankToDefault(" ", "defaultString")      // "defaultString"}</li>
     *     <li>{@code StringUtil.blankToDefault("\t\n", "defaultString")   // "defaultString"}</li>
     *     <li>{@code StringUtil.blankToDefault("abc", "defaultString")    // "abc"}</li>
     * </ul>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>该方法会检测字符串是否为{@code null}，是否为空白字符串。</li>
     *     <li>若只需要检测字符串是否为{@code null}，请使用 {@link #nullToDefault(String, String)}</li>
     *     <li>若只需要检测字符串是否为{@code null}、否为空字符串，请使用 {@link #emptyToDefault(String, String)}</li>
     * </ul>
     *
     * @param param         被检测的字符串
     * @param defaultString 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String blankToDefault(final String param, final String defaultString) {
        return isBlank(param) ? defaultString : param;
    }

    //------------------------------- String equal ---------------------------------------------

    /**
     * <p>比较两个字符串是否一致（大小写敏感）。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.equal(null, null)       // true}</li>
     *     <li>{@code StringUtil.equal("", "")           // true}</li>
     *     <li>{@code StringUtil.equal("abc", null)      // false}</li>
     *     <li>{@code StringUtil.equal(null, "abc")      // false}</li>
     *     <li>{@code StringUtil.equal("", "abc")        // false}</li>
     *     <li>{@code StringUtil.equal("abc", "")        // false}</li>
     *     <li>{@code StringUtil.equal("abc", "abc")     // true}</li>
     *     <li>{@code StringUtil.equal("abc", "ABC")     // false}</li>
     * </ul>
     *
     * <p>注意：如果俩个字符串都是{@code null}，则返回{@code true}</p>
     * <br/>
     *
     * @param param   要比较的字符串
     * @param another 另一个要比较的字符串
     * @return 两个字符串是否相同
     */
    public static boolean equals(final String param, final String another) {
        //noinspection StringEquality,EqualsReplaceableByObjectsCall
        return param == another || (param != null && param.equals(another));
    }

    /**
     * <p>比较两个字符串是否一致，会忽略大小写。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.equalIgnoreCase(null, null)       // true}</li>
     *     <li>{@code StringUtil.equalIgnoreCase("", "")           // true}</li>
     *     <li>{@code StringUtil.equalIgnoreCase("abc", null)      // false}</li>
     *     <li>{@code StringUtil.equalIgnoreCase(null, "abc")      // false}</li>
     *     <li>{@code StringUtil.equalIgnoreCase("", "abc")        // false}</li>
     *     <li>{@code StringUtil.equalIgnoreCase("abc", "")        // false}</li>
     *     <li>{@code StringUtil.equalIgnoreCase("abc", "abc")     // true}</li>
     *     <li>{@code StringUtil.equalIgnoreCase("abc", "ABC")     // false}</li>
     * </ul>
     *
     * <p>注意：如果俩个字符串都是{@code null}，则返回{@code true}</p>
     * <br/>
     *
     * @param param   要比较的字符串1
     * @param another 要比较的字符串2
     * @return 两个字符串是否相同
     */
    public static boolean equalsIgnoreCase(final String param, final String another) {
        //noinspection StringEquality
        return param == another || (param != null && param.equalsIgnoreCase(another));
    }

    /**
     * <p>给定字符串是否与提供的中任一字符串相同（大小写敏感），相同则返回{@code true}，没有相同的返回{@code false}</p>
     * <p>如果参与比对的字符串列表为空，返回{@code false}</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.equalAny("", "")                            // true}</li>
     *     <li>{@code StringUtil.equalAny("abc", null)                       // false}</li>
     *     <li>{@code StringUtil.equalAny(null, "abc")                       // false}</li>
     *     <li>{@code StringUtil.equalAny("", "abc", "admin", "test")        // false}</li>
     *     <li>{@code StringUtil.equalAny("", "abc", "admin", "test", "")    // true}</li>
     *     <li>{@code StringUtil.equalAny("abc", "abc", "admin", "test")     // true}</li>
     *     <li>{@code StringUtil.equalAny("abc", "ABC", "admin", "test")     // false}</li>
     *     <li>{@code StringUtil.equalAny("abc", "ABC", "admin", "test", "") // false}</li>
     * </ul>
     *
     * <p>注意：如果俩个字符串都是{@code null}，则返回{@code true}</p>
     * <br/>
     *
     * @param param  需要检查的字符串
     * @param others 需要参与比对的字符串列表
     * @return 是否有相同的字符串
     */
    public static boolean equalsAny(final String param, final String... others) {
        return Streams.of(others).anyMatch(it -> equals(it, param));
    }

    /**
     * <p>给定字符串是否与提供的中任一字符串相同，会忽略大小写，相同则返回{@code true}，没有相同的返回{@code false}</p>
     * <p>如果参与比对的字符串列表为空，返回{@code false}</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.equalAnyIgnoreCase("", "")                            // true}</li>
     *     <li>{@code StringUtil.equalAnyIgnoreCase("abc", null)                       // false}</li>
     *     <li>{@code StringUtil.equalAnyIgnoreCase(null, "abc")                       // false}</li>
     *     <li>{@code StringUtil.equalAnyIgnoreCase("", "abc", "admin", "test")        // false}</li>
     *     <li>{@code StringUtil.equalAnyIgnoreCase("", "abc", "admin", "test", "")    // true}</li>
     *     <li>{@code StringUtil.equalAnyIgnoreCase("abc", "abc", "admin", "test")     // true}</li>
     *     <li>{@code StringUtil.equalAnyIgnoreCase("abc", "ABC", "admin", "test")     // true}</li>
     *     <li>{@code StringUtil.equalAnyIgnoreCase("abc", "ABC", "admin", "test")     // true}</li>
     *     <li>{@code StringUtil.equalAnyIgnoreCase("abc", "ABC", "admin", "test", "") // true}</li>
     * </ul>
     *
     * <p>注意：如果俩个字符串都是{@code null}，则返回{@code true}</p>
     * <br/>
     *
     * @param param  需要检查的字符串
     * @param others 需要参与比对的字符串列表
     * @return 是否有相同的字符串
     */
    public static boolean equalsAnyIgnoreCase(final String param, final String... others) {
        return Streams.of(others).anyMatch(it -> equalsIgnoreCase(it, param));
    }

    //------------------------------- CharSequence trim ---------------------------------------------

    /**
     * <p>去除字符串头尾部的空白，如果字符串是空白，会返回空字符串。<p/>
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.trim(null)                  // ""}</li>
     *     <li>{@code StringUtil.trim("")                    // ""}</li>
     *     <li>{@code StringUtil.trim("     ")               // ""}</li>
     *     <li>{@code StringUtil.trim(" abc ")               // "abc"}</li>
     *     <li>{@code StringUtil.trim("     abc     ")       // "abc"}</li>
     *     <li>{@code StringUtil.trim("\t\n abc \t\n")       // "abc"}</li>
     *     <li>{@code StringUtil.trim("abc")                 // "abc"}</li>
     * </ul>
     * <p>注意：和{@link String#trim()}不同，此方法使用{@link CharUtil#isBlank(char)} 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。</p>
     *
     * @param param 要处理的字符串
     * @return 去除头尾部空白的字符串
     */
    public static String trim(final String param) {
        return isEmpty(param) ? EMPTY : trim(param, 0);
    }

    /**
     * 字符串数组去空格
     *
     * @param params 字符串参数数组
     * @return 去空格以后的字符串集合
     */
    public static String[] trim(final String... params) {
        return Streams.of(params).map(StringUtil::trim).toArray(String[]::new);
    }

    /**
     * 字符串集合去空格
     *
     * @param params 字符串参数集合
     * @return 去空格以后的字符串集合
     */
    public static List<String> trim(final Collection<String> params) {
        return Streams.of(params).map(StringUtil::trim).toList();
    }

    /**
     * 字符串数组去空格，并过滤掉空字符串
     *
     * @param params 字符串参数数组
     * @return 去空格以后的字符串集合
     */
    public static String[] trimIgnoreEmpty(final String... params) {
        return Streams.of(params).map(StringUtil::trim).filter(CharSequenceUtil::isNotEmpty).toArray(String[]::new);
    }

    /**
     * 字符串集合去空格，并过滤掉空字符串
     *
     * @param params 字符串参数集合
     * @return 去空格以后的字符串集合
     */
    public static List<String> trimIgnoreEmpty(final Collection<String> params) {
        return Streams.of(params).map(StringUtil::trim).filter(CharSequenceUtil::isNotEmpty).toList();
    }

    /**
     * <p>去除字符串头部的空白，如果字符串是空白，会返回空字符串。<p/>
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.trim(null)                  // ""}</li>
     *     <li>{@code StringUtil.trim("")                    // ""}</li>
     *     <li>{@code StringUtil.trim("     ")               // ""}</li>
     *     <li>{@code StringUtil.trim(" abc ")               // "abc "}</li>
     *     <li>{@code StringUtil.trim("     abc     ")       // "abc     "}</li>
     *     <li>{@code StringUtil.trim("\t\n abc \t\n")       // "abc \t\n"}</li>
     *     <li>{@code StringUtil.trim("abc")                 // "abc"}</li>
     * </ul>
     * <p>注意：和{@link String#trim()}不同，此方法使用{@link CharUtil#isBlank(char)} 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。</p>
     *
     * @param param 要处理的字符串
     * @return 去除头部空白的字符串
     */
    public static String trimBefore(final String param) {
        return isEmpty(param) ? EMPTY : trim(param, -1);
    }

    /**
     * <p>去除字符串尾部的空白，如果字符串是空白，会返回空字符串。<p/>
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.trim(null)                  // ""}</li>
     *     <li>{@code StringUtil.trim("")                    // ""}</li>
     *     <li>{@code StringUtil.trim("     ")               // ""}</li>
     *     <li>{@code StringUtil.trim(" abc ")               // " abc"}</li>
     *     <li>{@code StringUtil.trim("     abc     ")       // "     abc"}</li>
     *     <li>{@code StringUtil.trim("\t\n abc \t\n")       // "\t\n abc"}</li>
     *     <li>{@code StringUtil.trim("abc")                 // "abc"}</li>
     * </ul>
     * <p>注意：和{@link String#trim()}不同，此方法使用{@link CharUtil#isBlank(char)} 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。</p>
     *
     * @param param 要处理的字符串
     * @return 去除尾部空白的字符串
     */
    public static String trimAfter(final String param) {
        return isEmpty(param) ? EMPTY : trim(param, 1);
    }

    //------------------------------- String count ---------------------------------------------

    /**
     * <p>统计指定参数中包含指定字符串的数量，参数为 {@code null} 或者 "" 返回 {@code 0}。<p/>
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.count(null, "*")                   // 0}</li>
     *     <li>{@code StringUtil.count("", "*")                     // 0}</li>
     *     <li>{@code StringUtil.count("alibaba", null)             // 0}</li>
     *     <li>{@code StringUtil.count("alibaba", "")               // 0}</li>
     *     <li>{@code StringUtil.count("alibaba", "a")              // 3}</li>
     *     <li>{@code StringUtil.count("alibaba", "A")              // 0}</li>
     *     <li>{@code StringUtil.count("alibaba", "ba")             // 2}</li>
     *     <li>{@code StringUtil.count("alibaba", "BA")             // 0}</li>
     *     <li>{@code StringUtil.count("alibaba", "alibaba")        // 1}</li>
     *     <li>{@code StringUtil.count("alibaba", "_alibaba")       // 0}</li>
     * </ul>
     *
     * <p>注意：该方法用于精准匹配，不忽略大小写。</p>
     * <br/>
     *
     * @param param  被查找的字符串
     * @param search 被统计的字符串
     * @return 查找到的个数
     */
    public static int count(final String param, final String search) {
        if (hasEmpty(param, search) || search.length() > param.length()) {
            return 0;
        }
        int count = 0, index = 0;
        while ((index = param.indexOf(search, index)) > -1) {
            count++;
            index += search.length();
        }
        return count;
    }

    //------------------------------- String startWith ---------------------------------------------

    /**
     * <p>判断是否以指定字符串开头，前缀的大小写敏感</p>
     * <p>如果被检测的字符串和前缀字符串中任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.startWith(null, null)                   // false}</li>
     *     <li>{@code StringUtil.startWith(null, "")                     // false}</li>
     *     <li>{@code StringUtil.startWith("", null)                     // false}</li>
     *     <li>{@code StringUtil.startWith("", "")                       // true}</li>
     *     <li>{@code StringUtil.startWith("alibaba", null)              // false}</li>
     *     <li>{@code StringUtil.startWith("alibaba", "")                // true}</li>
     *     <li>{@code StringUtil.startWith("alibaba", "a")               // true}</li>
     *     <li>{@code StringUtil.startWith("alibaba", " a")              // false}</li>
     *     <li>{@code StringUtil.startWith("alibaba", "A")               // false}</li>
     *     <li>{@code StringUtil.startWith("alibaba", "ali")             // true}</li>
     *     <li>{@code StringUtil.startWith("alibaba", "AL")              // false}</li>
     *     <li>{@code StringUtil.startWith("alibaba", "alibaba")         // true}</li>
     *     <li>{@code StringUtil.startWith("alibaba", "Alibaba")         // false}</li>
     * </ul>
     *
     * @param param  被检测字符串
     * @param prefix 前缀字符串
     * @return 是否以指定字符串开头
     */
    public static boolean startWith(final String param, final String prefix) {
        return startWith(param, prefix, false);
    }

    /**
     * <p>判断是否以指定字符串开头，会忽略前缀的大小写</p>
     * <p>如果被检测的字符串和前缀字符串中任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.startWithIgnoreCase(null, null)                   // false}</li>
     *     <li>{@code StringUtil.startWithIgnoreCase(null, "")                     // false}</li>
     *     <li>{@code StringUtil.startWithIgnoreCase("", null)                     // false}</li>
     *     <li>{@code StringUtil.startWithIgnoreCase("", "")                       // true}</li>
     *     <li>{@code StringUtil.startWithIgnoreCase("alibaba", null)              // false}</li>
     *     <li>{@code StringUtil.startWithIgnoreCase("alibaba", "")                // true}</li>
     *     <li>{@code StringUtil.startWithIgnoreCase("alibaba", "a")               // true}</li>
     *     <li>{@code StringUtil.startWithIgnoreCase("alibaba", " a")              // false}</li>
     *     <li>{@code StringUtil.startWithIgnoreCase("alibaba", "A")               // true}</li>
     *     <li>{@code StringUtil.startWithIgnoreCase("alibaba", "ali")             // true}</li>
     *     <li>{@code StringUtil.startWithIgnoreCase("alibaba", "AL")              // true}</li>
     *     <li>{@code StringUtil.startWithIgnoreCase("alibaba", "alibaba")         // true}</li>
     *     <li>{@code StringUtil.startWithIgnoreCase("alibaba", "Alibaba")         // true}</li>
     * </ul>
     *
     * @param param  被检测字符串
     * @param prefix 前缀字符串
     * @return 是否以指定字符串开头
     */
    public static boolean startWithIgnoreCase(final String param, final String prefix) {
        return startWith(param, prefix, true);
    }

    /**
     * <p>判断参数字符串是否以指定的前缀列表中任意一个开头，前缀的大小写敏感</p>
     * <p>如果被检测的字符串和前缀字符串列表任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.startWithAny(null, null)                               // false}</li>
     *     <li>{@code StringUtil.startWithAny(null, "")                                 // false}</li>
     *     <li>{@code StringUtil.startWithAny("", null)                                 // false}</li>
     *     <li>{@code StringUtil.startWithAny("", "")                                   // true}</li>
     *     <li>{@code StringUtil.startWithAny("alibaba", null)                          // false}</li>
     *     <li>{@code StringUtil.startWithAny("alibaba", "", "b")                       // true}</li>
     *     <li>{@code StringUtil.startWithAny("alibaba", "a", "b")                      // true}</li>
     *     <li>{@code StringUtil.startWithAny("alibaba", "a ", "b")                     // false}</li>
     *     <li>{@code StringUtil.startWithAny("alibaba", " a", "A")                     // false}</li>
     *     <li>{@code StringUtil.startWithAny("alibaba", "A", "AL")                     // false}</li>
     *     <li>{@code StringUtil.startWithAny("alibaba", "A", "AL", "ali")              // true}</li>
     *     <li>{@code StringUtil.startWithAny("alibaba", "_alibaba", "Alibaba")         // false}</li>
     * </ul>
     *
     * @param param    被检测字符串
     * @param prefixes 前缀字符串列表
     * @return 是否以指定的前缀列表中任意一个开头
     */
    public static boolean startWithAny(final String param, final String... prefixes) {
        return Streams.of(prefixes).anyMatch(it -> startWith(param, it));
    }

    /**
     * <p>判断参数字符串是否以指定的前缀列表中任意一个开头，会忽略前缀的大小写</p>
     * <p>如果被检测的字符串和前缀字符串列表任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.startWithAnyIgnoreCase(null, null)                               // false}</li>
     *     <li>{@code StringUtil.startWithAnyIgnoreCase(null, "")                                 // false}</li>
     *     <li>{@code StringUtil.startWithAnyIgnoreCase("", null)                                 // false}</li>
     *     <li>{@code StringUtil.startWithAnyIgnoreCase("", "")                                   // true}</li>
     *     <li>{@code StringUtil.startWithAnyIgnoreCase("alibaba", null)                          // false}</li>
     *     <li>{@code StringUtil.startWithAnyIgnoreCase("alibaba", "", "b")                       // true}</li>
     *     <li>{@code StringUtil.startWithAnyIgnoreCase("alibaba", "a", "b")                      // true}</li>
     *     <li>{@code StringUtil.startWithAnyIgnoreCase("alibaba", "a ", "b")                     // false}</li>
     *     <li>{@code StringUtil.startWithAnyIgnoreCase("alibaba", " a", "A")                     // true}</li>
     *     <li>{@code StringUtil.startWithAnyIgnoreCase("alibaba", "A", "AL")                     // true}</li>
     *     <li>{@code StringUtil.startWithAnyIgnoreCase("alibaba", "A", "AL", "ali")              // true}</li>
     *     <li>{@code StringUtil.startWithAnyIgnoreCase("alibaba", "_alibaba", "Alibaba")         // true}</li>
     * </ul>
     *
     * @param param    被检测字符串
     * @param prefixes 前缀字符串列表
     * @return 是否以指定的前缀列表中任意一个开头
     */
    public static boolean startWithAnyIgnoreCase(final String param, final String... prefixes) {
        return Streams.of(prefixes).anyMatch(it -> startWithIgnoreCase(param, it));
    }

    //------------------------------- String endWith ---------------------------------------------

    /**
     * <p>判断是否以指定字符串结尾，后缀的大小写敏感</p>
     * <p>如果被检测的字符串和后缀字符串中任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.endWith(null, null)                   // false}</li>
     *     <li>{@code StringUtil.endWith(null, "")                     // false}</li>
     *     <li>{@code StringUtil.endWith("", null)                     // false}</li>
     *     <li>{@code StringUtil.endWith("", "")                       // true}</li>
     *     <li>{@code StringUtil.endWith("alibaba", null)              // false}</li>
     *     <li>{@code StringUtil.endWith("alibaba", "")                // true}</li>
     *     <li>{@code StringUtil.endWith("alibaba", "a")               // true}</li>
     *     <li>{@code StringUtil.endWith("alibaba", " a")              // false}</li>
     *     <li>{@code StringUtil.endWith("alibaba", "A")               // false}</li>
     *     <li>{@code StringUtil.endWith("alibaba", "aba")             // true}</li>
     *     <li>{@code StringUtil.endWith("alibaba", "BA")              // false}</li>
     *     <li>{@code StringUtil.endWith("alibaba", "alibaba")         // true}</li>
     *     <li>{@code StringUtil.endWith("alibaba", "Alibaba")         // false}</li>
     * </ul>
     *
     * @param param  被检测字符串
     * @param suffix 后缀字符串
     * @return 是否以指定字符串结尾
     */
    public static boolean endWith(final String param, final String suffix) {
        return endWith(param, suffix, false);
    }

    /**
     * <p>判断是否以指定字符串结尾，会忽略后缀的大小写</p>
     * <p>如果被检测的字符串和后缀字符串中任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.endWithIgnoreCase(null, null)                   // false}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase(null, "")                     // false}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("", null)                     // false}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("", "")                       // true}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", null)              // false}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", "")                // true}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", "a")               // true}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", " a")              // false}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", "A")               // true}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", "aba")             // true}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", "BA")              // true}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", "alibaba")         // true}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", "Alibaba")         // true}</li>
     * </ul>
     *
     * @param param  被检测字符串
     * @param suffix 后缀字符串
     * @return 是否以指定字符串结尾
     */
    public static boolean endWithIgnoreCase(final String param, final String suffix) {
        return endWith(param, suffix, true);
    }

    /**
     * <p>判断参数字符串是否以指定的后缀列表中任意一个开头，后缀的大小写敏感</p>
     * <p>如果被检测的字符串和后缀字符串列表任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.endWithAny(null, null)                               // false}</li>
     *     <li>{@code StringUtil.endWithAny(null, "")                                 // false}</li>
     *     <li>{@code StringUtil.endWithAny("", null)                                 // false}</li>
     *     <li>{@code StringUtil.endWithAny("", "")                                   // true}</li>
     *     <li>{@code StringUtil.endWithAny("alibaba", null)                          // false}</li>
     *     <li>{@code StringUtil.endWithAny("alibaba", "", "b")                       // true}</li>
     *     <li>{@code StringUtil.endWithAny("alibaba", "a", "b")                      // true}</li>
     *     <li>{@code StringUtil.endWithAny("alibaba", "a ", "b")                     // false}</li>
     *     <li>{@code StringUtil.endWithAny("alibaba", " a", "A")                     // false}</li>
     *     <li>{@code StringUtil.endWithAny("alibaba", "A", "BA")                     // false}</li>
     *     <li>{@code StringUtil.endWithAny("alibaba", "A", "BA", "aba")              // true}</li>
     *     <li>{@code StringUtil.endWithAny("alibaba", "_alibaba", "Alibaba")         // false}</li>
     * </ul>
     *
     * @param param    被检测字符串
     * @param suffixes 后缀字符串列表
     * @return 是否以指定的后缀列表中任意一个结尾
     */
    public static boolean endWithAny(final String param, final String... suffixes) {
        return Streams.of(suffixes).anyMatch(it -> endWith(param, it));
    }

    /**
     * <p>判断参数字符串是否以指定的后缀列表中任意一个开头，会忽略后缀的大小写</p>
     * <p>如果被检测的字符串和后缀字符串列表任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.endWithIgnoreCase(null, null)                               // false}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase(null, "")                                 // false}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("", null)                                 // false}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("", "")                                   // true}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", null)                          // false}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", "", "b")                       // true}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", "a", "b")                      // true}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", "a ", "b")                     // false}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", " a", "A")                     // true}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", "A", "BA")                     // true}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", "A", "BA", "aba")              // true}</li>
     *     <li>{@code StringUtil.endWithIgnoreCase("alibaba", "_alibaba", "Alibaba")         // true}</li>
     * </ul>
     *
     * @param param    被检测字符串
     * @param suffixes 后缀字符串列表
     * @return 是否以指定的后缀列表中任意一个结尾
     */
    public static boolean endWithAnyIgnoreCase(final String param, final String... suffixes) {
        return Streams.of(suffixes).anyMatch(it -> endWithIgnoreCase(param, it));
    }

    //------------------------------- String contains ---------------------------------------------

    /**
     * <p>判断字符串中是否包含了指定的字符串</p>
     * <p>如果被检测的字符串和被查找的字符串任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.contains(null, null)                           // false}</li>
     *     <li>{@code StringUtil.contains("", "")                               // true}</li>
     *     <li>{@code StringUtil.contains("", null)                             // false}</li>
     *     <li>{@code StringUtil.contains(null, "")                             // false}</li>
     *     <li>{@code StringUtil.contains(null, "a")                            // false}</li>
     *     <li>{@code StringUtil.contains("alibaba", null)                      // false}</li>
     *     <li>{@code StringUtil.contains("alibaba", "a")                       // true}</li>
     *     <li>{@code StringUtil.contains("alibaba", "A")                       // false}</li>
     *     <li>{@code StringUtil.contains("alibaba", "ali")                     // true}</li>
     *     <li>{@code StringUtil.contains("alibaba", "ALI")                     // false}</li>
     *     <li>{@code StringUtil.contains("alibaba", "c")                       // false}</li>
     *     <li>{@code StringUtil.contains("alibaba", "bc")                      // false}</li>
     * </ul>
     *
     * <p>注意：和{@link String#contains(CharSequence)}不同，此方法可以规避空指针异常，在使用时不需要判断参数是否为{@code null}</p>
     *
     * @param param  被检测字符串
     * @param search 被查找的字符串
     * @return 是否包含了指定的字符串
     */
    public static boolean contains(final String param, final String search) {
        return param != null && search != null && param.contains(search);
    }

    /**
     * <p>判断字符串中是否包含了指定的字符串，会忽略大小写</p>
     * <p>如果被检测的字符串和被查找的字符串任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.containsIgnoreCase(null, null)                           // false}</li>
     *     <li>{@code StringUtil.containsIgnoreCase("", "")                               // true}</li>
     *     <li>{@code StringUtil.containsIgnoreCase(null, "")                             // false}</li>
     *     <li>{@code StringUtil.containsIgnoreCase("", null)                             // false}</li>
     *     <li>{@code StringUtil.containsIgnoreCase(null, "a")                            // false}</li>
     *     <li>{@code StringUtil.containsIgnoreCase("alibaba", null)                      // false}</li>
     *     <li>{@code StringUtil.containsIgnoreCase("alibaba", "a")                       // true}</li>
     *     <li>{@code StringUtil.containsIgnoreCase("alibaba", "A")                       // true}</li>
     *     <li>{@code StringUtil.containsIgnoreCase("alibaba", "ali")                     // true}</li>
     *     <li>{@code StringUtil.containsIgnoreCase("alibaba", "ALI")                     // true}</li>
     *     <li>{@code StringUtil.containsIgnoreCase("alibaba", "c")                       // false}</li>
     *     <li>{@code StringUtil.containsIgnoreCase("alibaba", "bc")                      // false}</li>
     * </ul>
     *
     * @param param  被检测字符串
     * @param search 被查找的字符串
     * @return 是否包含了指定的字符串
     */
    public static boolean containsIgnoreCase(final String param, final String search) {
        return param != null && search != null && param.toLowerCase(Locale.ROOT)
                .contains(search.toLowerCase(Locale.ROOT));
    }

    /**
     * <p>判断字符串中是否包含了指定的字符串数组中任意一个字符串</p>
     * <p>如果被检测的字符串和被查找的字符串数组任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.containsAny(null, null)                             // false}</li>
     *     <li>{@code StringUtil.containsAny("", "")                                 // true}</li>
     *     <li>{@code StringUtil.containsAny("", null)                               // false}</li>
     *     <li>{@code StringUtil.containsAny(null, "")                               // false}</li>
     *     <li>{@code StringUtil.containsAny(null, "a")                              // false}</li>
     *     <li>{@code StringUtil.containsAny("alibaba", null)                        // false}</li>
     *     <li>{@code StringUtil.containsAny("alibaba", "c" ,"d" ,"e")               // false}</li>
     *     <li>{@code StringUtil.containsAny("alibaba", "a", "b", "li")              // true}</li>
     *     <li>{@code StringUtil.containsAny("alibaba", "c" ,"de" ,"li")             // true}</li>
     *     <li>{@code StringUtil.containsAny("alibaba", "A", "B", "LI")              // false}</li>
     *     <li>{@code StringUtil.containsAny("alibaba", "C", "DE", "LO")             // false}</li>
     * </ul>
     *
     * @param param    被检测字符串
     * @param searches 被检查的字符串数组
     * @return 是否包含任意一个字符串
     */
    public static boolean containsAny(final String param, final String... searches) {
        return Streams.of(searches).anyMatch(it -> contains(param, it));
    }

    /**
     * <p>判断字符串中是否包含了指定的字符串数组中任意一个字符串，会忽略大小写</p>
     * <p>如果被检测的字符串和被查找的字符串数组任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.containsAnyIgnoreCase(null, null)                             // false}</li>
     *     <li>{@code StringUtil.containsAnyIgnoreCase("", "")                                 // true}</li>
     *     <li>{@code StringUtil.containsAnyIgnoreCase("", null)                               // false}</li>
     *     <li>{@code StringUtil.containsAnyIgnoreCase(null, "")                               // false}</li>
     *     <li>{@code StringUtil.containsAnyIgnoreCase(null, "a")                              // false}</li>
     *     <li>{@code StringUtil.containsAnyIgnoreCase("alibaba", null)                        // false}</li>
     *     <li>{@code StringUtil.containsAnyIgnoreCase("alibaba", "c" ,"d" ,"e")               // false}</li>
     *     <li>{@code StringUtil.containsAnyIgnoreCase("alibaba", "a", "b", "li")              // true}</li>
     *     <li>{@code StringUtil.containsAnyIgnoreCase("alibaba", "c" ,"de" ,"li")             // true}</li>
     *     <li>{@code StringUtil.containsAnyIgnoreCase("alibaba", "A", "B", "LI")              // true}</li>
     *     <li>{@code StringUtil.containsAnyIgnoreCase("alibaba", "C", "DE", "LO")             // false}</li>
     * </ul>
     *
     * @param param    被检测字符串
     * @param searches 被检查的字符串数组
     * @return 是否包含任意一个字符串
     */
    public static boolean containsAnyIgnoreCase(final String param, final String... searches) {
        return Streams.of(searches).anyMatch(it -> containsIgnoreCase(param, it));
    }

    /**
     * <p>判断字符串中是否包含了指定的字符串数组中全部的字符串</p>
     * <p>如果被检测的字符串和被查找的字符串数组任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.containsAll(null, null)                             // false}</li>
     *     <li>{@code StringUtil.containsAll("", "")                                 // true}</li>
     *     <li>{@code StringUtil.containsAll("", null)                               // false}</li>
     *     <li>{@code StringUtil.containsAll(null, "")                               // false}</li>
     *     <li>{@code StringUtil.containsAll(null, "a")                              // false}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", null)                        // false}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", "c", "d", "e")               // false}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", "a" ,"b" ,"li")              // true}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", "c" ,"de" ,"li")             // false}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", 'A','B','LI')                // false}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", "C" ,"DE" ,"LO")             // false}</li>
     * </ul>
     *
     * @param param    被检测字符串
     * @param searches 被检查的字符串数组
     * @return 是否包含全部的字符串
     */
    public static boolean containsAll(final String param, final String... searches) {
        return ArrayUtil.isNotEmpty(searches) && Streams.of(searches).allMatch(it -> contains(param, it));
    }

    /**
     * <p>判断字符串中是否包含了指定的字符串数组中全部的字符串，会忽略大小写</p>
     * <p>如果被检测的字符串和被查找的字符串数组任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.containsAll(null, null)                             // false}</li>
     *     <li>{@code StringUtil.containsAll("", "")                                 // true}</li>
     *     <li>{@code StringUtil.containsAll("", null)                               // false}</li>
     *     <li>{@code StringUtil.containsAll(null, "")                               // false}</li>
     *     <li>{@code StringUtil.containsAll(null, "a")                              // false}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", null)                        // false}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", "c", "d", "e")               // false}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", "a" ,"b" ,"li")              // true}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", "c" ,"de" ,"li")             // false}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", 'A','B','LI')                // true}</li>
     *     <li>{@code StringUtil.containsAll("alibaba", "C" ,"DE" ,"LO")             // false}</li>
     * </ul>
     *
     * @param param    被检测字符串
     * @param searches 被检查的字符串数组
     * @return 是否包含全部的字符串
     */
    public static boolean containsAllIgnoreCase(final String param, final String... searches) {
        return ArrayUtil.isNotEmpty(searches) && Streams.of(searches).allMatch(it -> containsIgnoreCase(param, it));
    }

    //------------------------------- String indexOf ---------------------------------------------

    /**
     * <p>在字符串中查找指定字符串的索引位置。</p>
     * <p>如果被检测的字符串或被查找的字符串为 {@code null} 或者 ""，则返回 {@code -1}</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.indexOf(null, null)                    // -1}</li>
     *     <li>{@code StringUtil.indexOf(null, "")                      // -1}</li>
     *     <li>{@code StringUtil.indexOf("", null)                      // -1}</li>
     *     <li>{@code StringUtil.indexOf("", "")                        // -1}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", "")                 // -1}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", "a")                // 0}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", "ab")               // 4}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", "ba")               // 3}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", "ca")               // -1}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", "AB")               // -1}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", "BA")               // -1}</li>
     * </ul>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>此方法主要用规避空指针异常的问题，使用时无需判断参数是否为空。</li>
     *     <li>{@link String#indexOf(String)}比较，被查找的字符串参数为{@code ""}时， String提供的原生方法返回{@code 0}，而本方法返回{@code -1}</li>
     * </ul>
     *
     * @param param  被检测字符串
     * @param search 被查找的字符串
     * @return 字符串的位置，查询不到返回 {@code -1}
     */
    public static int indexOf(final String param, final String search) {
        return (isEmpty(param) || isEmpty(search)) ? INDEX_NOT_FOUND : param.indexOf(search);
    }

    /**
     * <p>在字符串中查找指定字符串的索引位置，会忽略大小写</p>
     * <p>如果被检测的字符串或被查找的字符串为 {@code null} 或者 ""，则返回 {@code -1}</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.indexOf(null, null)                    // -1}</li>
     *     <li>{@code StringUtil.indexOf(null, "")                      // -1}</li>
     *     <li>{@code StringUtil.indexOf("", null)                      // -1}</li>
     *     <li>{@code StringUtil.indexOf("", "")                        // -1}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", "")                 // -1}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", "a")                // 0}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", "ab")               // 4}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", "ba")               // 3}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", "ca")               // -1}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", "AB")               // -1}</li>
     *     <li>{@code StringUtil.indexOf("alibaba", "BA")               // -1}</li>
     * </ul>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>此方法主要用规避空指针异常的问题，使用时无需判断参数是否为空。</li>
     *     <li>{@link String#indexOf(String)}比较，被查找的字符串参数为{@code ""}时， String提供的原生方法返回{@code 0}，而本方法返回{@code -1}</li>
     * </ul>
     *
     * @param param  被检测字符串
     * @param search 被查找的字符串
     * @return 字符串的位置，查询不到返回 {@code -1}
     */
    public static int indexOfIgnoreCase(final String param, final String search) {
        return (isEmpty(param) || isEmpty(search)) ? INDEX_NOT_FOUND : param.toLowerCase(Locale.ROOT)
                .indexOf(search.toLowerCase(Locale.ROOT));
    }

    /**
     * <p>在字符串中查找指定字符串的最后一个索引位置。</p>
     * <p>如果被检测的字符串或被查找的字符串为 {@code null} 或者 ""，则返回 {@code -1}</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.lastIndexOf(null, null)                    // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf(null, "")                      // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf("", null)                      // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf("", "")                        // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", "")                 // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", "a")                // 6}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", "ab")               // 5}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", "ba")               // 4}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", "ca")               // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", "AB")               // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", "BA")               // -1}</li>
     * </ul>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>此方法主要用规避空指针异常的问题，使用时无需判断参数是否为空。</li>
     *     <li>{@link String#lastIndexOf(String)}比较，被查找的字符串参数为{@code ""}时， String提供的原生方法返回{@code param.length() + 1}，而本方法返回{@code -1}</li>
     * </ul>
     *
     * @param param  被检测字符串
     * @param search 被查找的字符串
     * @return 字符串的最后一个位置，查询不到返回 {@code -1}
     */
    public static int lastIndexOf(final String param, final String search) {
        return (isEmpty(param) || isEmpty(search)) ? INDEX_NOT_FOUND : param.lastIndexOf(search);
    }

    /**
     * <p>在字符串中查找指定字符串的最后一个索引位置，会忽略大小写</p>
     * <p>如果被检测的字符串或被查找的字符串为 {@code null} 或者 ""，则返回 {@code -1}</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.lastIndexOf(null, null)                    // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf(null, "")                      // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf("", null)                      // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf("", "")                        // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", "")                 // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", "a")                // 6}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", "ab")               // 5}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", "ba")               // 4}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", "ca")               // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", "AB")               // -1}</li>
     *     <li>{@code StringUtil.lastIndexOf("alibaba", "BA")               // -1}</li>
     * </ul>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>此方法主要用规避空指针异常的问题，使用时无需判断参数是否为空。</li>
     *     <li>{@link String#lastIndexOf(String)}比较，被查找的字符串参数为{@code ""}时， String提供的原生方法返回{@code param.length() + 1}，而本方法返回{@code -1}</li>
     * </ul>
     *
     * @param param  被检测字符串
     * @param search 被查找的字符串
     * @return 字符串的最后一个位置，查询不到返回 {@code -1}
     */
    public static int lastIndexOfIgnoreCase(final String param, final String search) {
        return (isEmpty(param) || isEmpty(search)) ? INDEX_NOT_FOUND : param.toLowerCase(Locale.ROOT)
                .lastIndexOf(search.toLowerCase(Locale.ROOT));
    }

    //------------------------------- String substring ---------------------------------------------

    /**
     * <p>根据开始位置截取字符串，规则：</p>
     * <ol>
     *     <li>被截取的字符串如果为 {@code null} 或者 ""，会直接返回空字符串 ""</li>
     *     <li>开始位置 {@code >= }字符串长度，也直接返回空字符串 ""</li>
     *     <li>开始位置{@code < 0}，会默认从 {@code 0 }开始截取</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.substring(null, 1)                    // ""}</li>
     *     <li>{@code StringUtil.substring("", 1)                      // ""}</li>
     *     <li>{@code StringUtil.substring("admin", -2)                // "admin"}</li>
     *     <li>{@code StringUtil.substring("admin", 0)                 // "admin"}</li>
     *     <li>{@code StringUtil.substring("admin", 3)                 // "in"}</li>
     *     <li>{@code StringUtil.substring("admin", 6)                 // ""}</li>
     * </ul>
     *
     * @param param 被截取的字符串
     * @param start 开始索引
     * @return 截取后的字符串
     */
    public static String substring(final String param, final int start) {
        return (isEmpty(param) || start >= param.length()) ? EMPTY : param.substring(Math.max(start, 0));
    }

    /**
     * <p>根据开始位置、结束位置截取字符串，规则：</p>
     * <ol>
     *     <li>被截取的字符串如果为 {@code null} 或者 ""，会直接返回空字符串 ""</li>
     *     <li>开始位置 {@code >= }结束，也直接返回空字符串 ""</li>
     *     <li>开始位置 {@code < 0}，会默认从 {@code 0 }开始截取</li>
     *     <li>结束位置 {@code > }字符串长度时，会默认结束位置为字符串的长度</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.substring(null, 1, 1)                    // ""}</li>
     *     <li>{@code StringUtil.substring("", 1, 1)                      // ""}</li>
     *     <li>{@code StringUtil.substring("admin", -2, 2)                // "ad"}</li>
     *     <li>{@code StringUtil.substring("admin", 0, 2)                 // "ad"}</li>
     *     <li>{@code StringUtil.substring("admin", 3, 6)                 // "in"}</li>
     *     <li>{@code StringUtil.substring("admin", 5, 6)                 // ""}</li>
     * </ul>
     *
     * @param param 被截取的字符串
     * @param start 开始索引
     * @param end   结束索引
     * @return 截取后的字符串
     */
    public static String substring(final String param, int start, int end) {
        return (isEmpty(param) || start >= end) ? EMPTY : param.substring(Math.max(start, 0), Math.min(end, param.length()));
    }

    /**
     * <p>根据分隔符截取字符串，结果保留分隔符之后的值，规则：</p>
     * <ol>
     *     <li>被截取的字符串如果为 {@code null} 或者 ""，会直接返回空字符串 ""</li>
     *     <li>分割标记如果为 {@code null} 或者 ""，或者不在被截取的字符串中，返回被截取的字符串</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.substringAfter(null, ":")                            // ""}</li>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", "")                // "redis:key:admin"}</li>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", ":")               // "key:admin"}</li>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", "$")               // "redis:key:admin"}</li>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", "key")             // ":admin"}</li>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", "key:")            // "admin"}</li>
     * </ul>
     *
     * @param param     被截取的字符串
     * @param separator 分割标记
     * @return 分隔符之后的值
     */
    public static String substringAfter(final String param, final String separator) {
        return substringAfter(param, separator, false);
    }

    /**
     * <p>根据分隔符截取字符串，结果保留分隔符之后的值，规则：</p>
     * <ol>
     *     <li>被截取的字符串如果为 {@code null} 或者 ""，会直接返回空字符串 ""</li>
     *     <li>分割标记如果为 {@code null} 或者 ""，或者不在被截取的字符串中，返回被截取的字符串</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", ":", false)               // "key:admin"}</li>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", ":", true)                // "admin"}</li>
     * </ul>
     *
     * @param param     被截取的字符串
     * @param separator 分割标记
     * @param isLast    是否查找最后一个分隔字符串，如果多次出现分隔字符串，值为 {@code true} 选取最后一个）
     * @return 分隔符之后的值
     */
    public static String substringAfter(final String param, final String separator, boolean isLast) {
        //优先查找分割符的位置
        final int index = isLast ? lastIndexOf(param, separator) : indexOf(param, separator);
        return index == INDEX_NOT_FOUND ? nullToEmpty(param) : param.substring(index + separator.length());
    }

    /**
     * <p>根据分隔符截取字符串，结果保留分隔符之后的值，会忽略大小写，规则：</p>
     * <ol>
     *     <li>被截取的字符串如果为 {@code null} 或者 ""，会直接返回空字符串 ""</li>
     *     <li>分割标记如果为 {@code null} 或者 ""，或者不在被截取的字符串中，返回被截取的字符串</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", ":")                         // "key:admin"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "key")             // ":admin"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "key:")            // "admin"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "KEY")             // ":admin"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "KEY:")            // "admin"}</li>
     * </ul>
     *
     * @param param     被截取的字符串
     * @param separator 分割标记
     * @return 分隔符之后的值
     */
    public static String substringAfterIgnoreCase(final String param, final String separator) {
        return substringAfterIgnoreCase(param, separator, false);
    }

    /**
     * <p>根据分隔符截取字符串，结果保留分隔符之后的值，会忽略大小写，规则：</p>
     * <ol>
     *     <li>被截取的字符串如果为 {@code null} 或者 ""，会直接返回空字符串 ""</li>
     *     <li>分割标记如果为 {@code null} 或者 ""，或者不在被截取的字符串中，返回被截取的字符串</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", ":", false)                  // "key:admin"}</li>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", ":", true)                   // "admin"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "key", false)      // ":admin"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "key:", true)      // "admin"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "KEY", false)      // ":admin"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "KEY:", true)      // "admin"}</li>
     * </ul>
     *
     * @param param     被截取的字符串
     * @param separator 分割标记
     * @param isLast    是否查找最后一个分隔字符串，如果多次出现分隔字符串，值为 {@code true} 选取最后一个）
     * @return 分隔符之后的值
     */
    public static String substringAfterIgnoreCase(final String param, final String separator, boolean isLast) {
        //优先查找分割符的位置
        final int index = isLast ? lastIndexOfIgnoreCase(param, separator) : indexOfIgnoreCase(param, separator);
        return index == INDEX_NOT_FOUND ? nullToEmpty(param) : param.substring(index + separator.length());
    }

    /**
     * <p>根据分隔符截取字符串，结果保留分隔符之前的值，规则：</p>
     * <ol>
     *     <li>被截取的字符串如果为 {@code null} 或者 ""，会直接返回空字符串 ""</li>
     *     <li>分割标记如果为 {@code null} 或者 ""，或者不在被截取的字符串中，返回被截取的字符串</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.substringAfter(null, ":")                            // ""}</li>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", "")                // "redis:key:admin"}</li>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", ":")               // "redis"}</li>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", "$")               // "redis:key:admin"}</li>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", "key")             // "redis"}</li>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", "key:")            // "redis"}</li>
     * </ul>
     *
     * @param param     被截取的字符串
     * @param separator 分割标记
     * @return 分隔符之前的值
     */
    public static String substringBefore(final String param, final String separator) {
        return substringBefore(param, separator, false);
    }

    /**
     * <p>根据分隔符截取字符串，结果保留分隔符之前的值，规则：</p>
     * <ol>
     *     <li>被截取的字符串如果为 {@code null} 或者 ""，会直接返回空字符串 ""</li>
     *     <li>分割标记如果为 {@code null} 或者 ""，或者不在被截取的字符串中，返回被截取的字符串</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", ":", false)               // "redis"}</li>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", ":", true)                // "redis:key"}</li>
     * </ul>
     *
     * @param param     被截取的字符串
     * @param separator 分割标记
     * @param isLast    是否查找最后一个分隔字符串，如果多次出现分隔字符串，值为 {@code true} 选取最后一个）
     * @return 分隔符之前的值
     */
    public static String substringBefore(final String param, final String separator, boolean isLast) {
        //优先查找分割符的位置
        final int index = isLast ? lastIndexOf(param, separator) : indexOf(param, separator);
        return index == INDEX_NOT_FOUND ? nullToEmpty(param) : param.substring(0, index);
    }

    /**
     * <p>根据分隔符截取字符串，结果保留分隔符之前的值，会忽略大小写，规则：</p>
     * <ol>
     *     <li>被截取的字符串如果为 {@code null} 或者 ""，会直接返回空字符串 ""</li>
     *     <li>分割标记如果为 {@code null} 或者 ""，或者不在被截取的字符串中，返回被截取的字符串</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", ":")                         // "redis"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "key")             // "redis:"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "key:")            // "redis:"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "KEY")             // "redis:"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "KEY:")            // "redis:"}</li>
     * </ul>
     *
     * @param param     被截取的字符串
     * @param separator 分割标记
     * @return 分隔符之前的值
     */
    public static String substringBeforeIgnoreCase(final String param, final String separator) {
        return substringBeforeIgnoreCase(param, separator, false);
    }

    /**
     * <p>根据分隔符截取字符串，结果保留分隔符之前的值，会忽略大小写，规则：</p>
     * <ol>
     *     <li>被截取的字符串如果为 {@code null} 或者 ""，会直接返回空字符串 ""</li>
     *     <li>分割标记如果为 {@code null} 或者 ""，或者不在被截取的字符串中，返回被截取的字符串</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", ":", false)                  // "redis"}</li>
     *     <li>{@code StringUtil.substringAfter("redis:key:admin", ":", true)                   // "redis:key"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "key", false)      // "redis:"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "key:", true)      // "redis:"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "KEY", false)      // "redis:"}</li>
     *     <li>{@code StringUtil.substringAfterIgnoreCase("redis:key:admin", "KEY:", true)      // "redis:"}</li>
     * </ul>
     *
     * @param param     被截取的字符串
     * @param separator 分割标记
     * @param isLast    是否查找最后一个分隔字符串，如果多次出现分隔字符串，值为 {@code true} 选取最后一个）
     * @return 分隔符之前的值
     */
    public static String substringBeforeIgnoreCase(final String param, final String separator, boolean isLast) {
        //优先查找分割符的位置
        final int index = isLast ? lastIndexOfIgnoreCase(param, separator) : indexOfIgnoreCase(param, separator);
        return index == INDEX_NOT_FOUND ? nullToEmpty(param) : param.substring(0, index);
    }

    /**
     * <p>根据分隔符截取字符串，结果保留前后分隔符之间的值，规则：</p>
     * <ol>
     *     <li>被截取的字符串如果为 {@code null} 或者 ""，会直接返回空字符串 ""</li>
     *     <li>前后分割标记如果为 {@code null} 或者 ""，或者不在被截取的字符串中，返回被截取的字符串</li>
     *     <li>前分割符如果出现多次，取第一次出现的位置</li>
     *     <li>后分割符如果出现多次，取最后一次出现的位置</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.substringBetween(null, ":", ":")                            // ""}</li>
     *     <li>{@code StringUtil.substringBetween("redis:key:admin", null, ":")              // ""}</li>
     *     <li>{@code StringUtil.substringBetween("redis:key:admin", ":", ":")               // "key"}</li>
     *     <li>{@code StringUtil.substringBetween("redis:key:admin", "i", "i")               // "s:key:adm"}</li>
     *     <li>{@code StringUtil.substringBetween("redis:key:admin", "I", "I")               // ""}</li>
     *     <li>{@code StringUtil.substringBetween("redis:key:admin", "redis", "admin")       // ":key:"}</li>
     *     <li>{@code StringUtil.substringBetween("redis:key:admin", "REDIS", "ADMIN")       // ""}</li>
     * </ul>
     *
     * @param param  被截取的字符串
     * @param prefix 截取开始位置的分割标记，如果出现多次，取第一次出现的位置
     * @param suffix 截取结束位置的分割标记，如果出现多次，取最后一次出现的位置
     * @return 分隔符之间的值
     */
    public static String substringBetween(final String param, final String prefix, String suffix) {
        // 找到前缀的位置
        int index = indexOf(param, prefix);
        if (index > INDEX_NOT_FOUND) {
            // 定义开始位置
            int start = index + prefix.length();
            // 找到结束位置
            int end = lastIndexOf(param, suffix);
            if (end > start) {
                return param.substring(start, end);
            }
        }
        return EMPTY;
    }

    /**
     * <p>根据分隔符截取字符串，结果保留前后分隔符之间的值，会忽略大小写，规则：</p>
     * <ol>
     *     <li>被截取的字符串如果为 {@code null} 或者 ""，会直接返回空字符串 ""</li>
     *     <li>前后分割标记如果为 {@code null} 或者 ""，或者不在被截取的字符串中，返回被截取的字符串</li>
     *     <li>前分割符如果出现多次，取第一次出现的位置</li>
     *     <li>后分割符如果出现多次，取最后一次出现的位置</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.substringBetween(null, ":", ":")                            // ""}</li>
     *     <li>{@code StringUtil.substringBetween("redis:key:admin", null, ":")              // ""}</li>
     *     <li>{@code StringUtil.substringBetween("redis:key:admin", ":", ":")               // "key"}</li>
     *     <li>{@code StringUtil.substringBetween("redis:key:admin", "i", "i")               // "s:key:adm"}</li>
     *     <li>{@code StringUtil.substringBetween("redis:key:admin", "I", "I")               // "s:key:adm"}</li>
     *     <li>{@code StringUtil.substringBetween("redis:key:admin", "redis", "admin")       // ":key:"}</li>
     *     <li>{@code StringUtil.substringBetween("redis:key:admin", "REDIS", "ADMIN")       // ":key:"}</li>
     * </ul>
     *
     * @param param  被截取的字符串
     * @param prefix 截取开始位置的分割标记，如果出现多次，取第一次出现的位置
     * @param suffix 截取结束位置的分割标记，如果出现多次，取最后一次出现的位置
     * @return 分隔符之间的值
     */
    public static String substringBetweenIgnoreCase(final String param, final String prefix, String suffix) {
        // 找到前缀的位置
        int index = indexOfIgnoreCase(param, prefix);
        if (index > INDEX_NOT_FOUND) {
            // 定义开始位置
            int start = index + prefix.length();
            // 找到结束位置
            int end = lastIndexOfIgnoreCase(param, suffix);
            if (end > start) {
                return param.substring(start, end);
            }
        }
        return EMPTY;
    }

    //------------------------------- String replace ---------------------------------------------

    /**
     * <p>替换字符串中指定的字符串，会替换所有匹配到的字符串</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param       被检测字符串
     * @param search      待替换的字符串
     * @param replacement 被替换的字符串
     * @return 替换之后的字符串
     */
    public static String replace(final String param, final String search, final String replacement) {
        return isEmpty(param) ? EMPTY : isEmpty(search) ? param : param.replace(search, nullToEmpty(replacement));
    }

    /**
     * <p>替换字符串中指定的字符串，会替换所有匹配到的字符串</p>
     * <p>在查找替换字符串时，会忽略大小写</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param       被检测字符串
     * @param search      待替换的字符串
     * @param replacement 被替换的字符串
     * @return 替换之后的字符串
     */
    public static String replaceIgnoreCase(final String param, final String search, final String replacement) {
        // 如果查找替换字符串与替换字符串一致，直接返回参数字符串
        if (equals(search, replacement)) {
            return nullToEmpty(param);
        }
        // 优先执行一次替换
        String result = replaceFirstIgnoreCase(param, search, replacement);
        // 判断替换后的字符串是否还存在查找的字符串
        while (indexOfIgnoreCase(result, search) > INDEX_NOT_FOUND) {
            result = replaceFirstIgnoreCase(result, search, replacement);
        }
        return result;
    }

    /**
     * <p>替换字符串中正则表达式匹配的字符串，会替换所有匹配到的字符串</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param       被检测字符串
     * @param regex       正则表达式
     * @param replacement 被替换的字符串
     * @return 替换之后的字符串
     */
    public static String replaceRegex(final String param, final String regex, final String replacement) {
        return isEmpty(param) ? EMPTY : isEmpty(regex) ? param : param.replaceAll(regex, nullToEmpty(replacement));
    }

    /**
     * <p>替换字符串中第一个指定的字符串</p>
     * <p>待替换的字符如果出现多次，只会替换第一次出现的那个字符</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param       被检测字符串
     * @param search      待替换的字符串
     * @param replacement 被替换的字符串
     * @return 替换之后的字符串
     */
    public static String replaceFirst(final String param, final String search, final String replacement) {
        return replace(param, indexOf(param, search), length(search), replacement);
    }

    /**
     * <p>替换字符串中第一个指定的字符串</p>
     * <p>待替换的字符如果出现多次，只会替换第一次出现的那个字符</p>
     * <p>在查找替换字符串时，会忽略大小写</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param       被检测字符串
     * @param search      待替换的字符串
     * @param replacement 被替换的字符串
     * @return 替换之后的字符串
     */
    public static String replaceFirstIgnoreCase(final String param, final String search, final String replacement) {
        return replace(param, indexOfIgnoreCase(param, search), length(search), replacement);
    }

    /**
     * <p>替换字符串中正则表达式匹配的字符串，只替换第一个符合匹配条件的字符串</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param       被检测字符串
     * @param regex       正则表达式
     * @param replacement 被替换的字符串
     * @return 替换之后的字符串
     */
    public static String replaceFirstRegex(final String param, final String regex, final String replacement) {
        return isEmpty(param) ? EMPTY : isEmpty(regex) ? param : param.replaceFirst(regex, nullToEmpty(replacement));
    }

    /**
     * <p>替换字符串中最后一个指定的字符串</p>
     * <p>待替换的字符如果出现多次，只会替换最后一次出现的那个字符</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param       被检测字符串
     * @param search      待替换的字符串
     * @param replacement 被替换的字符串
     * @return 替换之后的字符串
     */
    public static String replaceLast(final String param, final String search, final String replacement) {
        return replace(param, lastIndexOf(param, search), length(search), replacement);
    }

    /**
     * <p>替换字符串中最后一个指定的字符串</p>
     * <p>待替换的字符如果出现多次，只会替换最后一次出现的那个字符</p>
     * <p>在查找替换字符串时，会忽略大小写</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param       被检测字符串
     * @param search      待替换的字符串
     * @param replacement 被替换的字符串
     * @return 替换之后的字符串
     */
    public static String replaceLastIgnoreCase(final String param, final String search, final String replacement) {
        return replace(param, lastIndexOfIgnoreCase(param, search), length(search), replacement);
    }

    //------------------------------- String remove ---------------------------------------------

    /**
     * <p>从字符串中删除指定的字符串，会删除所有匹配的字符串</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param  被检测字符串
     * @param search 待删除的字符串
     * @return 删除指定字符串后的字符串
     */
    public static String remove(final String param, final String search) {
        return replace(param, search, null);
    }

    /**
     * <p>从字符串中删除指定字符串数组中的所有值，会删除所有匹配的字符串</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param    被检测字符串
     * @param searches 待删除的字符串数组
     * @return 删除指定字符串数组中所有值的字符串
     */
    public static String remove(final String param, final String... searches) {
        String result = nullToEmpty(param);
        if (!result.isEmpty() && searches != null) {
            for (String search : searches) {
                if (isNotEmpty(search)) {
                    result = result.replace(search, EMPTY);
                }
            }
        }
        return result;
    }

    /**
     * <p>从字符串中删除指定的字符串，会删除所有匹配的字符串，并忽略大小写</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param  被检测字符串
     * @param search 待删除的字符串
     * @return 删除指定字符串后的字符串
     */
    public static String removeIgnoreCase(final String param, final String search) {
        return replaceIgnoreCase(param, search, null);
    }

    /**
     * <p>从字符串中删除指定字符串数组中的所有值，会删除所有匹配的字符串，并忽略大小写</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param    被检测字符串
     * @param searches 待删除的字符串数组
     * @return 删除指定字符串数组中所有值的字符串
     */
    public static String removeIgnoreCase(final String param, final String... searches) {
        String result = nullToEmpty(param);
        if (!result.isEmpty() && searches != null) {
            for (String search : searches) {
                result = removeIgnoreCase(result, search);
            }
        }
        return result;
    }

    /**
     * <p>删除字符串中第一个指定的字符串</p>
     * <p>待删除的字符如果出现多次，只会删除第一次出现的那个字符串</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param  被检测字符串
     * @param search 待删除的字符串
     * @return 删除指定字符串后的字符串
     */
    public static String removeFirst(final String param, final String search) {
        return replaceFirst(param, search, null);
    }

    /**
     * <p>删除字符串中第一个指定的字符串，会忽略大小写</p>
     * <p>待删除的字符如果出现多次，只会删除第一次出现的那个字符串</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param  被检测字符串
     * @param search 待删除的字符串
     * @return 删除指定字符串后的字符串
     */
    public static String removeFirstIgnoreCase(final String param, final String search) {
        return replaceFirstIgnoreCase(param, search, null);
    }

    /**
     * <p>删除字符串中最后一个指定的字符串</p>
     * <p>待删除的字符如果出现多次，只会删除最后一次出现的那个字符串</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param  被检测字符串
     * @param search 待删除的字符串
     * @return 删除指定字符串后的字符串
     */
    public static String removeLast(final String param, final String search) {
        return replaceLast(param, search, null);
    }

    /**
     * <p>删除字符串中最后一个指定的字符串，会忽略大小写</p>
     * <p>待删除的字符如果出现多次，只会删除最后一次出现的那个字符串</p>
     * <p>如果被检测的字符串值为 {@code null} 或 ""，将会返回空字符串</p>
     *
     * @param param  被检测字符串
     * @param search 待删除的字符串
     * @return 删除指定字符串后的字符串
     */
    public static String removeLastIgnoreCase(final String param, final String search) {
        return replaceLastIgnoreCase(param, search, null);
    }

    //---------------------------------String upper lower case -------------------------------------------

    /**
     * 字符串转大写
     *
     * @param param 字符串参数
     * @return 转大写后的字符串
     */
    public static String upperCase(final String param) {
        return isEmpty(param) ? EMPTY : param.toUpperCase(Locale.ROOT);
    }

    /**
     * 字符串集合转大写
     *
     * @param params 字符串参数集合
     * @return 转大写后的字符串集合
     */
    public static List<String> upperCase(final Collection<String> params) {
        return Streams.of(params).map(StringUtil::upperCase).toList();
    }

    /**
     * 字符串数组转大写
     *
     * @param params 字符串参数数组
     * @return 转大写后的字符串数组
     */
    public static String[] upperCase(final String... params) {
        return Streams.of(params).map(StringUtil::upperCase).toArray(String[]::new);
    }

    /**
     * 首字母大写
     *
     * @param param 字符串参数
     * @return 首字母转大写后的字符串
     */
    public static String upperFirst(final String param) {
        return isEmpty(param) ? EMPTY : Character.toUpperCase(param.charAt(0)) + param.substring(1);
    }

    /**
     * 字符串集合首字母大写
     *
     * @param params 字符串参数集合
     * @return 首字母转大写后的字符串集合
     */
    public static List<String> upperFirst(final Collection<String> params) {
        return Streams.of(params).map(StringUtil::upperFirst).toList();
    }

    /**
     * 字符串数组首字母大写
     *
     * @param params 字符串参数数组
     * @return 首字母转大写后的字符串数组
     */
    public static String[] upperFirst(final String... params) {
        return Streams.of(params).map(StringUtil::upperFirst).toArray(String[]::new);
    }

    /**
     * 字符串转小写
     *
     * @param param 字符串参数
     * @return 转小写后的字符串
     */
    public static String lowerCase(final String param) {
        return isEmpty(param) ? EMPTY : param.toLowerCase(Locale.ROOT);
    }

    /**
     * 字符串转小写
     *
     * @param params 字符串参数
     * @return 转小写后的字符串
     */
    public static List<String> lowerCase(final Collection<String> params) {
        return Streams.of(params).map(StringUtil::lowerCase).toList();
    }

    /**
     * 字符串转小写
     *
     * @param params 字符串参数
     * @return 转小写后的字符串
     */
    public static String[] lowerCase(final String... params) {
        return Streams.of(params).map(StringUtil::lowerCase).toArray(String[]::new);
    }

    /**
     * 首字母小写
     *
     * @param param 字符串参数
     * @return 首字母转小写后的字符串
     */
    public static String lowerFirst(final String param) {
        return isEmpty(param) ? EMPTY : Character.toLowerCase(param.charAt(0)) + param.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param params 字符串参数
     * @return 首字母转小写后的字符串
     */
    public static List<String> lowerFirst(final Collection<String> params) {
        return Streams.of(params).map(StringUtil::lowerFirst).toList();
    }

    /**
     * 首字母小写
     *
     * @param params 字符串参数
     * @return 首字母转小写后的字符串
     */
    public static String[] lowerFirst(final String... params) {
        return Streams.of(params).map(StringUtil::lowerFirst).toArray(String[]::new);
    }

    //-------------------------------String convert camel ---------------------------------------------

    /**
     * 驼峰格式字符串集合转下划线格式字符串集合。
     *
     * @param params 需要转换的字符串集合
     * @return 转换后的字符串集合
     */
    public static List<String> camelToUnderline(final Collection<String> params) {
        return Streams.of(params).map(CharSequenceUtil::camelToUnderline).toList();
    }

    /**
     * 下划线格式字符串集合转驼峰格式字符串集合。
     *
     * @param params 需要转换的字符串集合
     * @return 转换后的字符串集合
     */
    public static List<String> underlineToCamel(final Collection<String> params) {
        return Streams.of(params).map(CharSequenceUtil::underlineToCamel).toList();
    }

    //--------------------------------- string split -------------------------------------------

    /**
     * <p>根据正则表达式，分割字符串，并返回分割之后的字符串集合</p>
     * <p>默认会对分割后的字符串清除左右空白字符</p>
     * <p>分割后的集合，会过滤掉空白字符串</p>
     *
     * @param param 需要分割的字符串
     * @param regex 正则表达式
     * @return 分割后的字符串集合
     */
    public static List<String> split(final String param, final String regex) {
        return split(param, regex, true, true);
    }

    /**
     * <p>根据正则表达式，分割字符串，并返回分割之后的字符串集合</p>
     *
     * @param param       需要分割的字符串
     * @param regex       正则表达式
     * @param isTrim      标记是否对分割后的字符串清除左右空白字符
     * @param ignoreBlank 标记返回的集合中是否过滤空白字符串
     * @return 分割后的字符串集合
     */
    public static List<String> split(final String param, final String regex, boolean isTrim, boolean ignoreBlank) {
        return split(param, regex, s -> s, isTrim, ignoreBlank);
    }

    /**
     * <p>根据正则表达式，分割字符串，并返回分割之后的数据集合</p>
     * <p>默认会对分割后的字符串清除左右空白字符</p>
     * <p>分割后的集合，会过滤掉空白字符串</p>
     *
     * @param param    需要分割的字符串
     * @param regex    正则表达式
     * @param function 分割后的字符串元素转换函数
     * @param <R>      分割后元素类型
     * @return 分割后的字符串集合
     */
    public static <R> List<R> split(final String param, final String regex, Function<String, R> function) {
        return split(param, regex, function, true, true);
    }

    /**
     * <p>根据正则表达式，分割字符串，并返回分割之后的数据集合</p>
     *
     * @param param       需要分割的字符串
     * @param regex       正则表达式
     * @param function    分割后的字符串元素转换函数
     * @param isTrim      标记是否对分割后的字符串清除左右空白字符
     * @param ignoreBlank 标记返回的集合中是否过滤空白字符串
     * @param <R>         分割后元素类型
     * @return 分割后的字符串集合
     */
    public static <R> List<R> split(final String param, final String regex, Function<String, R> function
            , boolean isTrim, boolean ignoreBlank) {
        //1. 转换函数不能为空
        Validate.notNull(function, "function must not null !");
        //2. 如果参数为空，直接返回空集合
        if (isEmpty(param)) {
            return Lists.arrayList();
        }
        //3. 分割字符串后，获取到数组
        final String[] array = isEmpty(regex) ? new String[]{param} : param.split(regex);
        //4. 将数组转为可操作流
        Stream<String> stream = Arrays.stream(array);
        //5. 判断是否去左右空白字符
        if (isTrim) {
            stream = stream.map(StringUtil::trim);
        }
        //6. 判断是否过来空白字符串元素
        if (ignoreBlank) {
            stream = stream.filter(StringUtil::isNotBlank);
        }
        //7. 调用转换函数
        return stream.map(function).toList();
    }

    /**
     * <p>安全的分割字符串分割，并转换成Integer类型</p>
     * <p>会对分割后的字符串清除左右空白字符</p>
     * <p>分割后的集合，会过滤掉空白字符串</p>
     *
     * @param param 需要分割的字符串
     * @param regex 正则表达式
     * @return 分割后的Integer类型集合
     */
    public static List<Integer> splitToInteger(final String param, final String regex) {
        return split(param, regex, Integer::valueOf);
    }

    /**
     * <p>安全的分割字符串分割，并转换成Long类型</p>
     * <p>会对分割后的字符串清除左右空白字符</p>
     * <p>分割后的集合，会过滤掉空白字符串</p>
     *
     * @param param 需要分割的字符串
     * @param regex 正则表达式
     * @return 分割后的Long类型集合
     */
    public static List<Long> splitToLong(final String param, final String regex) {
        return split(param, regex, Long::valueOf);
    }

    /**
     * <p>安全的分割字符串分割，并转换成BigDecimal类型</p>
     * <p>会对分割后的字符串清除左右空白字符</p>
     * <p>分割后的集合，会过滤掉空白字符串</p>
     *
     * @param param 需要分割的字符串
     * @param regex 正则表达式
     * @return 分割后的BigDecimal类型集合
     */
    public static List<BigDecimal> splitToBigDecimal(final String param, final String regex) {
        return split(param, regex, BigDecimal::new);
    }

    //--------------------------------- string concat join -------------------------------------------

    /**
     * <p>将数组元素连接成一个字符串，会将数组中为null的元素转换位字符的"null"</p>
     *
     * @param params 需要连接的数组参数
     * @return 连接之后的字符串
     */
    public static String concat(final Object... params) {
        return join(EMPTY, params);
    }

    /**
     * <p>将集合元素连接成一个字符串，会将集合中为null的元素转换位字符的"null"</p>
     *
     * @param params 需要连接的集合参数
     * @return 包装后的字符串
     */
    public static <T> String concat(final Collection<T> params) {
        return join(EMPTY, params);
    }

    /**
     * <p>使用指定的连接符，将数组元素连接成一个字符串，请参考{@link ArrayUtil#join(Object[], CharSequence)}</p>
     *
     * @param delimiter 连接符
     * @param params    需要连接的数组参数
     * @return 连接之后的字符串
     */
    public static String join(final String delimiter, final Object... params) {
        return ArrayUtil.join(params, delimiter);
    }

    /**
     * <p>使用指定的连接符，将集合元素连接成一个字符串，会将集合中为null的元素转换位字符的"null"</p>
     * <p>如果连接符为 {@code null }，会将其转换为 ""</p>
     *
     * @param delimiter 连接符
     * @param params    需要连接的集合参数
     * @return 连接之后的字符串
     */
    public static <T> String join(final String delimiter, final Collection<T> params) {
        StringJoiner joiner = new StringJoiner(nullToEmpty(delimiter));
        Streams.of(params).map(it -> toString(it, false)).forEach(joiner::add);
        return joiner.toString();
    }

    /**
     * 包装指定字符串
     *
     * @param str    被包装的字符串
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 包装后的字符串
     */
    public static String wrap(String str, String prefix, String suffix) {
        return nullToEmpty(prefix).concat(nullToEmpty(str)).concat(nullToEmpty(suffix));
    }

    /**
     * 将标准的getter或setter方法名称转换为对应的属性名称
     *
     * @param methodName java标准getter或setter方法名称
     * @return 生成对应的property名称
     */
    public static String propertyName(final String methodName) {
        return isEmpty(methodName) ? EMPTY : isGetterOrSetter(methodName) ? lowerFirst(methodName
                .substring(
                        methodName.startsWith(IS)
                                ? 2 : 3))
                : methodName;
    }

    /**
     * 根据属性名称获取对应的getter方法名
     *
     * @param property 属性名称
     * @return 生成property对应的getter名称
     */
    public static String getterName(final String property) {
        return isEmpty(property) ? EMPTY : GET + upperFirst(property);
    }

    /**
     * 根据属性名称和java类型，获取对应的getter方法名
     * boolean类型 生成以is开头，Boolean类型 生成以get开头
     *
     * @param property     属性名称
     * @param propertyType 属性类型
     * @return 生成property对应的getter名称
     */
    public static String getterName(final String property, final Class<?> propertyType) {
        return isEmpty(property) ? EMPTY : ((isNotNull(propertyType) && boolean.class
                .isAssignableFrom(propertyType)) ? IS : GET) + upperFirst(property);
    }

    /**
     * 根据属性名称获取对应的setter方法名称
     *
     * @param property 属性名称
     * @return 生成property对应的getter名称
     */
    public static String setterName(final String property) {
        return isEmpty(property) ? EMPTY : SET + upperFirst(property);
    }

    /**
     * 判断是否标准getter或setter方法
     *
     * @param methodName 方法名称
     * @return 是否标准属性方法
     */
    public static boolean isGetterOrSetter(String methodName) {
        return isNotEmpty(methodName) && (methodName.startsWith(GET) || methodName.startsWith(SET) || methodName.startsWith(IS));
    }

    /**
     * 判断是否数字，负数返回false
     *
     * @param param 需要判断的字符串参数
     * @return 是否数字
     */
    public static boolean isNumeric(CharSequence param) {
        if (isEmpty(param)) {
            return false;
        }
        for (int i = 0; i < param.length(); ++i) {
            if (!Character.isDigit(param.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 反转字符串<br>
     * 例如：abcd =》dcba
     *
     * @param str 被反转的字符串
     * @return 反转后的字符串
     */
    public static String reverse(String str) {
        return isEmpty(str) ? EMPTY : new StringBuilder(str).reverse().toString();
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") =》 this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") =》 this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(final CharSequence template, final Object... params) {
        return isEmpty(template) ? EMPTY : StringFormatter.format(template.toString(), params);
    }

    /**
     * 将Object对象转换为字符串，为空的情况下输出为""
     *
     * @param obj 需要转换的对象
     * @return 转换后的字符串
     */
    public static String toString(final Object obj) {
        return toString(obj, true);
    }

    /**
     * 将Object对象转换为字符串，为空的情况下输出为""
     *
     * @param obj           需要转换的对象
     * @param isNullToEmpty 对象为空时，是否输出为""
     * @return 转换后的字符串
     */
    public static String toString(final Object obj, boolean isNullToEmpty) {
        return isNull(obj) ? isNullToEmpty ? EMPTY : StringPool.NULL : obj.toString();
    }

    /**
     * 将对象转为字符串<br>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组 2、对象数组会调用Arrays.toString方法
     *
     * @param obj         对象
     * @param charsetName 字符集
     * @return 字符串
     */
    public static String toString(final Object obj, final String charsetName) {
        return toString(obj, Charset.forName(charsetName));
    }

    /**
     * 将对象转为字符串<br>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组 2、对象数组会调用Arrays.toString方法
     *
     * @param obj     对象
     * @param charset 字符集
     * @return 字符串
     */
    public static String toString(Object obj, Charset charset) {
        if (null == obj) {
            return EMPTY;
        }

        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return toString((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            return toString((Byte[]) obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return toString((ByteBuffer) obj, charset);
        } else if (ArrayUtil.isArray(obj)) {
            return ArrayUtil.toString((Object[]) obj);
        }
        return obj.toString();
    }

    /**
     * 将byte数组转为字符串
     *
     * @param bytes   byte数组
     * @param charset 字符集
     * @return 字符串
     */
    public static String toString(byte[] bytes, String charset) {
        return toString(bytes, CharsetUtil.charset(charset));
    }

    /**
     * 解码字节码
     *
     * @param bytes   字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 解码后的字符串
     */
    public static String toString(byte[] bytes, Charset charset) {
        return isNull(bytes) ? EMPTY : isNull(charset) ? new String(bytes) : new String(bytes, charset);
    }

    /**
     * 将Byte数组转为字符串
     *
     * @param bytes   byte数组
     * @param charset 字符集
     * @return 字符串
     */
    public static String toString(Byte[] bytes, String charset) {
        return toString(bytes, CharsetUtil.charset(charset));
    }

    /**
     * 解码字节码
     *
     * @param bytes   字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 解码后的字符串
     */
    public static String toString(Byte[] bytes, Charset charset) {
        if (isNull(bytes)) {
            return EMPTY;
        }

        byte[] data = new byte[bytes.length];
        Byte dataByte;
        for (int i = 0; i < bytes.length; i++) {
            dataByte = bytes[i];
            data[i] = (null == dataByte) ? -1 : dataByte;
        }

        return toString(data, charset);
    }

    /**
     * 将编码的byteBuffer数据转换为字符串
     *
     * @param data    数据
     * @param charset 字符集，如果为空使用当前系统字符集
     * @return 字符串
     */
    public static String toString(ByteBuffer data, String charset) {
        return isNull(data) ? EMPTY : toString(data, CharsetUtil.charset(charset));
    }

    /**
     * 将编码的byteBuffer数据转换为字符串
     *
     * @param data    数据
     * @param charset 字符集，如果为空使用当前系统字符集
     * @return 字符串
     */
    public static String toString(ByteBuffer data, Charset charset) {
        if (isNull(charset)) {
            charset = Charset.defaultCharset();
        }
        return charset.decode(data).toString();
    }

    /**
     * 将对象转为字符串<br>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组 2、对象数组会调用Arrays.toString方法
     *
     * @param obj 对象
     * @return 字符串
     */
    public static String toUtf8String(Object obj) {
        return toString(obj, StandardCharsets.UTF_8);
    }

    ////////////////////////////////////////// 私有方法 //////////////////////////////////////////

    /**
     * <p>根据模式，去除字符串头尾部的空白字符。</p>
     * <br/>
     * <p>注意：此方法为私有方法，参数不允许为null，运行时不会做空指针检查。</p>
     * <br/>
     *
     * @param param 要处理的字符串
     * @param mode  {@code -1}表示trimStart，{@code 0}表示trim全部， {@code 1}表示trimEnd
     * @return 除去指定字符后的的字符串，如果原字串为{@code null}，则返回{@code null}
     */
    private static String trim(@NonNull String param, int mode) {
        // 定义开始位置、结束位置
        int start = 0, end = param.length();
        // 扫描字符串首部
        if (mode <= 0) {
            while ((start < end) && CharUtil.isBlank(param.charAt(start))) {
                start++;
            }
        }
        // 扫描字符串尾部
        if (mode >= 0) {
            while ((start < end) && CharUtil.isBlank(param.charAt(end - 1))) {
                end--;
            }
        }
        // 截取字符串
        return (start > 0 || end < param.length()) ? param.substring(start, end) : param;
    }

    /**
     * <p>判断是否以指定字符串开头，会根据参数 {@code ignoreCase} 来判断是否忽略大小写</p>
     * <p>如果被检测的字符串和前缀字符串中任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * @param param      被检测字符串
     * @param prefix     前缀字符串
     * @param ignoreCase 标记是否忽略大小写
     * @return 是否以指定字符串开头
     */
    private static boolean startWith(final String param, final String prefix, boolean ignoreCase) {
        return param != null && prefix != null && param.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
    }

    /**
     * <p>判断是否以指定字符串结尾，会根据参数 {@code ignoreCase} 来判断是否忽略大小写</p>
     * <p>如果被检测的字符串和后缀字符串中任意一个值为 {@code null}，将会返回 {@code false}<p>
     *
     * @param param  被检测字符串
     * @param suffix 后缀字符串
     * @return 是否以指定字符串结尾
     */
    private static boolean endWith(final String param, final String suffix, boolean ignoreCase) {
        if (param == null || suffix == null) {
            return false;
        }
        final int offset = Math.subtractExact(param.length(), suffix.length());
        return param.regionMatches(ignoreCase, offset, suffix, 0, suffix.length());
    }

    /**
     * <p>根据索引位置、字符长度，替换范围内的字符串</p>
     * <p>被检查字符如果为空，直接返回空字符串<p>
     * <p>索引位置为 {@code -1} 或是 长度{@code < 1 }，直接返回被检查字符串<p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>此方法为私有方法，参数index，length的值已在调用时做了范围的安全处理，所以这里不会做校验。</li>
     * </ul>
     *
     * @param param       被检测字符串
     * @param index       查找字符串的索引位置
     * @param length      查找字符的长度
     * @param replacement 被替换的字符串
     * @return 替换之后的字符串
     */
    private static String replace(final String param, final int index, final int length, final String replacement) {
        // 被检查字符如果为空，直接返回空字符串
        if (isEmpty(param)) {
            return EMPTY;
        }
        if (index == INDEX_NOT_FOUND || length < 1) {
            return param;
        }
        String result = EMPTY;
        if (index > 0) {
            result += param.substring(0, index);
        }
        result += nullToEmpty(replacement);
        result += param.substring(index + length);
        return result;
    }
}
