package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.collection.ArrayUtil;
import com.ipower.framework.common.core.collection.CollectionUtil;
import com.ipower.framework.common.core.constant.StringPool;
import com.ipower.framework.common.core.stream.Streams;

import java.util.Collection;
import java.util.Iterator;

/**
 * 字符串工具验证器，提供字符串的blank和empty等检查<br>
 * <ul>
 *     <li>empty定义：{@code null} or 空字符串：{@code ""}</li>
 *     <li>blank定义：{@code null} or 空字符串：{@code ""} or 空格、全角空格、制表符、换行符，等不可见字符</li>
 * </ul>
 *
 * @author kris
 */
public class CharSequenceValidator {

    /**
     * 私有化构造函数，禁止实例化该类
     */
    protected CharSequenceValidator() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 索引位置未被找到的常量值 -1
     */
    public static final int INDEX_NOT_FOUND = -1;

    //region >>> validate empty

    /**
     * <p>判断字符串是否为空，空字符串的定义如下：</p>
     * <ol>
     *     <li>{@code null}</li>
     *     <li>空字符串：{@code ""}</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.isEmpty(null)     // true}</li>
     *     <li>{@code StringUtil.isEmpty("")       // true}</li>
     *     <li>{@code StringUtil.isEmpty(" ")      // false}</li>
     *     <li>{@code StringUtil.isEmpty(" \t\n")  // false}</li>
     *     <li>{@code StringUtil.isEmpty("abc")    // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isBlank(CharSequence)} 的区别是：该方法不校验空白字符。</p>
     * <p>建议：</p>
     * <ul>
     *     <li>该方法建议用于工具类或任何可以预期的方法参数的校验中。</li>
     *     <li>需要同时校验多个字符串时，建议采用 {@link #hasEmpty(CharSequence...)} 或 {@link #isAllEmpty(CharSequence...)}</li>
     * </ul>
     *
     * @param param 被检测的字符串
     * @return 是否为空
     */
    public static boolean isEmpty(final CharSequence param) {
        return param == null || param.isEmpty();
    }

    /**
     * <p>判断字符串是否不为空，不为空字符串的定义如下： </p>
     * <ol>
     *     <li>不为 {@code null}</li>
     *     <li>不为空字符串：{@code ""}</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.isNotEmpty(null)     // false}</li>
     *     <li>{@code StringUtil.isNotEmpty("")       // false}</li>
     *     <li>{@code StringUtil.isNotEmpty(" ")      // true}</li>
     *     <li>{@code StringUtil.isNotEmpty(" \t\n")  // true}</li>
     *     <li>{@code StringUtil.isNotEmpty("abc")    // true}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isNotBlank(CharSequence)} 的区别是：该方法不校验空白字符。</p>
     * <p>建议：该方法建议用于工具类或任何可以预期的方法参数的校验中。</p>
     * <br/>
     *
     * @param param 被检测的字符串
     * @return 是否不为空
     */
    public static boolean isNotEmpty(final CharSequence param) {
        return !isEmpty(param);
    }

    /**
     * <p>判断字符串数组中的元素，是否全部为空。</p>
     * <p>如果指定的字符串数组的长度为 0，或者所有元素都是空字符串，则返回 true。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.isAllEmpty()                  // true}</li>
     *     <li>{@code StringUtil.isAllEmpty("", null)          // true}</li>
     *     <li>{@code StringUtil.isAllEmpty("123", "")         // false}</li>
     *     <li>{@code StringUtil.isAllEmpty("123", "abc")      // false}</li>
     *     <li>{@code StringUtil.isAllEmpty(" ", "\t", "\n")   // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #hasEmpty(CharSequence...)} 的区别在于：</p>
     * <ul>
     *     <li>{@link #hasEmpty(CharSequence...)}   等价于 {@code isEmpty(...) || isEmpty(...) || ...}</li>
     *     <li>isAllEmpty(CharSequence...)          等价于 {@code isEmpty(...) && isEmpty(...) && ...}</li>
     * </ul>
     *
     * @param params 被检测的字符串数组
     * @return 所有字符串是否全部为空
     */
    public static boolean isAllEmpty(final CharSequence... params) {
        return Streams.of(params).allMatch(CharSequenceUtil::isEmpty);
    }

    /**
     * <p>判断字符串集合中的元素，是否全部为空。</p>
     * <p>如果指定的字符串集合的长度为{@code 0}，或者所有元素都是空字符串，则返回{@code true}。</p>
     *
     * @param params 被检测的字符串集合
     * @return 所有字符串是否全部为空
     */
    public static boolean isAllEmpty(final Collection<? extends CharSequence> params) {
        return Streams.of(params).allMatch(CharSequenceUtil::isEmpty);
    }

    /**
     * <p>判断字符串迭代器中的元素，是否全部为空。</p>
     * <p>如果指定的字符串迭代器为{@code null}，或者所有元素都是空字符串，则返回{@code true}。</p>
     *
     * @param params 被检测的字符串迭代器
     * @return 所有字符串是否全部为空
     */
    public static boolean isAllEmpty(final Iterable<? extends CharSequence> params) {
        return Streams.of(params).allMatch(CharSequenceUtil::isEmpty);
    }

    /**
     * <p>判断字符串迭代器中的元素，是否全部为空。</p>
     * <p>如果指定的字符串迭代器为{@code null}，或者所有元素都是空字符串，则返回{@code true}。</p>
     *
     * @param params 被检测的字符串迭代器
     * @return 所有字符串是否全部为空
     */
    public static boolean isAllEmpty(final Iterator<? extends CharSequence> params) {
        return Streams.of(params).allMatch(CharSequenceUtil::isEmpty);
    }

    /**
     * <p>判断字符串数组中的元素，是否全部不为空。</p>
     * <p>如果指定的字符串数组的长度不为 0，或者所有元素都不是空字符串，则返回 true。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.isAllNotEmpty()                  // false}</li>
     *     <li>{@code StringUtil.isAllNotEmpty("", null)          // false}</li>
     *     <li>{@code StringUtil.isAllNotEmpty("123", "")         // false}</li>
     *     <li>{@code StringUtil.isAllNotEmpty("123", "abc")      // true}</li>
     *     <li>{@code StringUtil.isAllNotEmpty(" ", "\t", "\n")   // true}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isAllEmpty(CharSequence...)} 的区别在于：</p>
     * <ul>
     *     <li>{@link #isAllEmpty(CharSequence...)}    等价于 {@code isEmpty(...) && isEmpty(...) && ...}</li>
     *     <li>isAllNotEmpty(CharSequence...)          等价于 {@code !isEmpty(...) && !isEmpty(...) && ...}</li>
     * </ul>
     *
     * @param params 被检测的字符串数组
     * @return 所有字符串是否全部不为空
     */
    public static boolean isAllNotEmpty(final CharSequence... params) {
        return !hasEmpty(params);
    }

    /**
     * <p>判断字符串集合中的元素，是否全部不为空。</p>
     * <p>如果指定的字符串集合的长度不为 0，或者所有元素都不是空字符串，则返回 true。</p>
     *
     * @param params 被检测的字符串集合
     * @return 所有字符串是否全部不为空
     */
    public static boolean isAllNotEmpty(final Collection<? extends CharSequence> params) {
        return !hasEmpty(params);
    }

    /**
     * <p>判断字符串迭代器中的元素，是否全部不为空。</p>
     * <p>如果指定的字符串迭代器不为{@code null}，或者所有元素都不是空字符串，则返回{@code true}。</p>
     *
     * @param params 被检测的字符串迭代器
     * @return 所有字符串是否全部不为空
     */
    public static boolean isAllNotEmpty(final Iterable<? extends CharSequence> params) {
        return !hasEmpty(params);
    }

    /**
     * <p>判断字符串迭代器中的元素，是否全部不为空。</p>
     * <p>如果指定的字符串迭代器不为{@code null}，或者所有元素都不是空字符串，则返回{@code true}。</p>
     *
     * @param params 被检测的字符串迭代器
     * @return 所有字符串是否全部不为空
     */
    public static boolean isAllNotEmpty(final Iterator<? extends CharSequence> params) {
        return !hasEmpty(params);
    }

    /**
     * <p>判断字符串数组中的元素，是否包含空的元素。</p>
     * <p>如果指定的字符串数组的长度为 0，或者其中的任意一个元素是空字符串，则返回 true。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.hasEmpty()                  // true}</li>
     *     <li>{@code StringUtil.hasEmpty(null)              // true}</li>
     *     <li>{@code StringUtil.hasEmpty("", null)          // true}</li>
     *     <li>{@code StringUtil.hasEmpty("123", "")         // true}</li>
     *     <li>{@code StringUtil.hasEmpty("123", "abc")      // false}</li>
     *     <li>{@code StringUtil.hasEmpty(" ", "\t", "\n")   // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isAllEmpty(CharSequence...)} 的区别在于：</p>
     * <ul>
     *     <li>hasEmpty(CharSequence...)            等价于 {@code isEmpty(...) || isEmpty(...) || ...}</li>
     *     <li>{@link #isAllEmpty(CharSequence...)} 等价于 {@code isEmpty(...) && isEmpty(...) && ...}</li>
     * </ul>
     *
     * @param params 被检测的字符串数组
     * @return 是否包含空字符串
     */
    public static boolean hasEmpty(final CharSequence... params) {
        return ArrayUtil.isEmpty(params) || Streams.of(params).anyMatch(CharSequenceUtil::isEmpty);
    }

    /**
     * <p>判断字符串集合中的元素，是否包含空的元素。</p>
     * <p>如果指定的字符串集合的长度为 0，或者其中的任意一个元素是空字符串，则返回 true。</p>
     *
     * @param params 被检测的字符串集合
     * @return 是否包含空字符串
     */
    public static boolean hasEmpty(final Collection<? extends CharSequence> params) {
        return CollectionUtil.isEmpty(params) || Streams.of(params).anyMatch(CharSequenceUtil::isEmpty);
    }

    /**
     * <p>判断字符串迭代器中的元素，是否包含空的元素。</p>
     * <p>如果指定的字符串迭代器为{@code null}，或者其中的任意一个元素是空字符串，则返回{@code true}。</p>
     *
     * @param params 被检测的字符串迭代器
     * @return 是否包含空字符串
     */
    public static boolean hasEmpty(final Iterable<? extends CharSequence> params) {
        return CollectionUtil.isEmpty(params) || Streams.of(params).anyMatch(CharSequenceUtil::isEmpty);
    }

    /**
     * <p>判断字符串迭代器中的元素，是否包含空的元素。</p>
     * <p>如果指定的字符串迭代器为{@code null}，或者其中的任意一个元素是空字符串，则返回{@code true}。</p>
     *
     * @param params 被检测的字符串迭代器
     * @return 是否包含空字符串
     */
    public static boolean hasEmpty(final Iterator<? extends CharSequence> params) {
        return CollectionUtil.isEmpty(params) || Streams.of(params).anyMatch(CharSequenceUtil::isEmpty);
    }

    /**
     * <p>判断字符串是否为空、是否"null"字符串、是否"undefined"字符串。</p>
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.isEmptyOrUndefined(null)              // true}</li>
     *     <li>{@code StringUtil.isEmptyOrUndefined("")                // true}</li>
     *     <li>{@code StringUtil.isEmptyOrUndefined(" ")               // false}</li>
     *     <li>{@code StringUtil.isEmptyOrUndefined("\t\n")            // false}</li>
     *     <li>{@code StringUtil.isEmptyOrUndefined("null")            // true}</li>
     *     <li>{@code StringUtil.isEmptyOrUndefined("undefined")       // true}</li>
     *     <li>{@code StringUtil.isEmptyOrUndefined("abc")             // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isEmpty(CharSequence)} 的区别在于，还会判断入参是否为字符串形式的"null"或"undefined"</p>
     * <br>
     *
     * @param param 被检查的字符串
     * @return 字符串是否为空、"null"、"undefined"
     */
    public static boolean isEmptyOrUndefined(final CharSequence param) {
        return isEmpty(param) || isNullOrUndefinedString(param);
    }

    //endregion

    //region >>> validate blank

    /**
     * <p>判断字符串是否为空白，空白字符串的定义如下：</p>
     * <ol>
     *     <li>{@code null}</li>
     *     <li>空字符串：{@code ""}</li>
     *     <li>空格、全角空格、制表符、换行符，等不可见字符</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.isBlank(null)     // true}</li>
     *     <li>{@code StringUtil.isBlank("")       // true}</li>
     *     <li>{@code StringUtil.isBlank(" ")      // true}</li>
     *     <li>{@code StringUtil.isBlank(" \t\n")  // true}</li>
     *     <li>{@code StringUtil.isBlank("abc")    // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isEmpty(CharSequence)} 的区别是：
     * 该方法会校验空白字符，且性能相对于 {@link #isEmpty(CharSequence)} 略慢。</p>
     *
     * <p>建议：</p>
     * <ul>
     *     <li>该方法建议仅对于客户端（或第三方接口）传入的参数使用该方法。</li>
     *     <li>需要同时校验多个字符串时，建议采用 {@link #hasBlank(CharSequence...)} 或 {@link #isAllBlank(CharSequence...)}</li>
     * </ul>
     *
     * @param param 被检测的字符串
     * @return 若为空白，则返回 true
     * @see #isEmpty(CharSequence)
     */
    public static boolean isBlank(final CharSequence param) {
        // 优先判断是否为空
        if (isEmpty(param)) {
            return true;
        }
        // 循环每一个字符，只要有一个非空字符，就返回false
        for (int i = 0; i < param.length(); i++) {
            if (!CharUtil.isBlank(param.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>判断字符串是否不为空白，不为空白字符串的定义如下： </p>
     * <ol>
     *     <li>不为 {@code null}</li>
     *     <li>不为空字符串：{@code ""}</li>
     *     <li>不为空格、全角空格、制表符、换行符，等不可见字符</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.isNotBlank(null)     // false}</li>
     *     <li>{@code StringUtil.isNotBlank("")       // false}</li>
     *     <li>{@code StringUtil.isNotBlank(" ")      // false}</li>
     *     <li>{@code StringUtil.isNotBlank(" \t\n")  // false}</li>
     *     <li>{@code StringUtil.isNotBlank("abc")    // true}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isNotEmpty(CharSequence)} 的区别是：
     * 该方法会校验空白字符，且性能相对于 {@link #isNotEmpty(CharSequence)} 略慢。</p>
     * <p>建议：仅对于客户端（或第三方接口）传入的参数使用该方法。</p>
     *
     * @param param 被检测的字符串
     * @return 是否为非空
     * @see #isBlank(CharSequence)
     */
    public static boolean isNotBlank(final CharSequence param) {
        return !isBlank(param);
    }

    /**
     * <p>指定字符串数组中的元素，是否全部为空白。</p>
     * <p>如果指定的字符串数组的长度为 0，或者所有元素都是空白字符串，则返回 true。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.isAllBlank()                  // true}</li>
     *     <li>{@code StringUtil.isAllBlank("", null, " ")     // true}</li>
     *     <li>{@code StringUtil.isAllBlank("123", " ")        // false}</li>
     *     <li>{@code StringUtil.isAllBlank("123", "abc")      // false}</li>
     *     <li>{@code StringUtil.isAllBlank(" ", "\t", "\n")   // true}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #hasBlank(CharSequence...)} 的区别在于：</p>
     * <ul>
     *     <li>{@link #hasBlank(CharSequence...)}   等价于 {@code isBlank(...) || isBlank(...) || ...}</li>
     *     <li>isAllBlank(CharSequence...)          等价于 {@code isBlank(...) && isBlank(...) && ...}</li>
     * </ul>
     *
     * @param params 被检测的字符串数组
     * @return 所有字符串是否全部为空白
     */
    public static boolean isAllBlank(final CharSequence... params) {
        return Streams.of(params).allMatch(CharSequenceUtil::isBlank);
    }

    /**
     * <p>指定字符串集合中的元素，是否全部为空白。</p>
     * <p>如果指定的字符串集合的长度为 0，或者所有元素都是空白字符串，则返回 true。</p>
     *
     * @param params 被检测的字符集合
     * @return 所有字符串是否全部为空白
     */
    public static boolean isAllBlank(final Collection<? extends CharSequence> params) {
        return Streams.of(params).allMatch(CharSequenceUtil::isBlank);
    }

    /**
     * <p>指定字符串迭代器的元素，是否全部为空白。</p>
     * <p>如果指定的字符串迭代器为{@code null}，或者所有元素都是空白字符串，则返回{@code ture}。</p>
     *
     * @param params 被检测的字符迭代器
     * @return 所有字符串是否全部为空白
     */
    public static boolean isAllBlank(final Iterable<? extends CharSequence> params) {
        return Streams.of(params).allMatch(CharSequenceUtil::isBlank);
    }

    /**
     * <p>指定字符串迭代器的元素，是否全部为空白。</p>
     * <p>如果指定的字符串迭代器为{@code null}，或者所有元素都是空白字符串，则返回{@code ture}。</p>
     *
     * @param params 被检测的字符迭代器
     * @return 所有字符串是否全部为空白
     */
    public static boolean isAllBlank(final Iterator<? extends CharSequence> params) {
        return Streams.of(params).allMatch(CharSequenceUtil::isBlank);
    }

    /**
     * <p>判断字符串数组中的元素，是否全部不为空白。</p>
     * <p>如果指定的字符串数组的长度不为 0，或者所有元素都不是空白字符串，则返回 true。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.isAllNotBlank()                  // false}</li>
     *     <li>{@code StringUtil.isAllNotBlank("", null, " ")     // false}</li>
     *     <li>{@code StringUtil.isAllNotBlank("123", "")         // false}</li>
     *     <li>{@code StringUtil.isAllNotBlank("123", "abc")      // true}</li>
     *     <li>{@code StringUtil.isAllNotBlank(" ", "\t", "\n")   // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isAllEmpty(CharSequence...)} 的区别在于：</p>
     * <ul>
     *     <li>{@link #isAllEmpty(CharSequence...)}    等价于 {@code isEmpty(...) && isEmpty(...) && ...}</li>
     *     <li>isAllNotEmpty(CharSequence...)          等价于 {@code !isEmpty(...) && !isEmpty(...) && ...}</li>
     * </ul>
     *
     * @param params 被检测的字符串数组
     * @return 所有字符串是否全部不为空白
     */
    public static boolean isAllNotBlank(final CharSequence... params) {
        return !hasBlank(params);
    }

    /**
     * <p>判断字符串集合中的元素，是否全部不为空白。</p>
     * <p>如果指定的字符串集合的长度不为 0，或者所有元素都不是空白字符串，则返回 true。</p>
     *
     * @param params 被检测的字符串集合
     * @return 所有字符串是否全部不为空白
     */
    public static boolean isAllNotBlank(final Collection<? extends CharSequence> params) {
        return !hasBlank(params);
    }

    /**
     * <p>判断字符串迭代器中的元素，是否全部不为空白。</p>
     * <p>如果指定的字符串迭代器不为{@code null}，或者所有元素都不是空白字符串，则返回{@code true}。</p>
     *
     * @param params 被检测的字符串迭代器
     * @return 所有字符串是否全部不为空白
     */
    public static boolean isAllNotBlank(final Iterable<? extends CharSequence> params) {
        return !hasBlank(params);
    }

    /**
     * <p>判断字符串迭代器中的元素，是否全部不为空白。</p>
     * <p>如果指定的字符串迭代器不为{@code null}，或者所有元素都不是空白字符串，则返回{@code true}。</p>
     *
     * @param params 被检测的字符串迭代器
     * @return 所有字符串是否全部不为空白
     */
    public static boolean isAllNotBlank(final Iterator<? extends CharSequence> params) {
        return !hasBlank(params);
    }

    /**
     * <p>指定字符串数组中，是否包含空白字符串。</p>
     * <p>如果指定的字符串数组的长度为 0，或者其中的任意一个元素是空白字符串，则返回 true。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.hasBlank()                  // true}</li>
     *     <li>{@code StringUtil.hasBlank(null)              // true}</li>
     *     <li>{@code StringUtil.hasBlank("", null, " ")     // true}</li>
     *     <li>{@code StringUtil.hasBlank("123", " ")        // true}</li>
     *     <li>{@code StringUtil.hasBlank("123", "abc")      // false}</li>
     *     <li>{@code StringUtil.hasBlank(" ", "\t", "\n")   // true}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isAllBlank(CharSequence...)} 的区别在于：</p>
     * <ul>
     *     <li>hasBlank(CharSequence...)  等价于 {@code isBlank(...) || isBlank(...) || ...}</li>
     *     <li>{@link #isAllBlank(CharSequence...)} 等价于 {@code isBlank(...) && isBlank(...) && ...}</li>
     * </ul>
     *
     * @param params 被检测的字符串数组
     * @return 是否包含空白字符串
     */
    public static boolean hasBlank(final CharSequence... params) {
        return ArrayUtil.isEmpty(params) || Streams.of(params).anyMatch(CharSequenceUtil::isBlank);
    }

    /**
     * <p>指定字符串集合中，是否包含空白字符串。</p>
     * <p>如果指定的字符串集合的长度为 0，或者其中的任意一个元素是空白字符串，则返回 true。</p>
     *
     * @param params 被检测的字符串集合
     * @return 是否包含空白字符串
     */
    public static boolean hasBlank(final Collection<? extends CharSequence> params) {
        return CollectionUtil.isEmpty(params) || Streams.of(params).anyMatch(CharSequenceUtil::isBlank);
    }

    /**
     * <p>指定字符串迭代器中，是否包含空白字符串。</p>
     * <p>如果指定的字符串迭代器为{@code null}，或者其中的任意一个元素是空白字符串，则返回{@code true}。</p>
     *
     * @param params 被检测的字符串迭代器
     * @return 是否包含空白字符串
     */
    public static boolean hasBlank(final Iterable<? extends CharSequence> params) {
        return CollectionUtil.isEmpty(params) || Streams.of(params).anyMatch(CharSequenceUtil::isBlank);
    }

    /**
     * <p>指定字符串迭代器中，是否包含空白字符串。</p>
     * <p>如果指定的字符串迭代器为{@code null}，或者其中的任意一个元素是空白字符串，则返回{@code true}。</p>
     *
     * @param params 被检测的字符串迭代器
     * @return 是否包含空白字符串
     */
    public static boolean hasBlank(final Iterator<? extends CharSequence> params) {
        return CollectionUtil.isEmpty(params) || Streams.of(params).anyMatch(CharSequenceUtil::isBlank);
    }

    /**
     * <p>判断字符串是否为空白、是否"null"字符串、是否"undefined"字符串。</p>
     * <p>例：</p>
     * <ul>
     *     <li>{@code StringUtil.isBlankOrUndefined(null)              // true}</li>
     *     <li>{@code StringUtil.isBlankOrUndefined("")                // true}</li>
     *     <li>{@code StringUtil.isBlankOrUndefined(" ")               // true}</li>
     *     <li>{@code StringUtil.isBlankOrUndefined("\t\n")            // true}</li>
     *     <li>{@code StringUtil.isBlankOrUndefined("null")            // true}</li>
     *     <li>{@code StringUtil.isBlankOrUndefined("undefined")       // true}</li>
     *     <li>{@code StringUtil.isBlankOrUndefined("abc")             // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isBlank(CharSequence)} 的区别在于，还会判断入参是否为字符串形式的"null"或"undefined"</p>
     * <br>
     *
     * @param param 被检查的字符串
     * @return 字符串是否为空白、"null"、"undefined"
     */
    public static boolean isBlankOrUndefined(final CharSequence param) {
        return isBlank(param) || isNullOrUndefinedString(param);
    }

    //endregion

    //region >>> private method

    /**
     * <p>判断是否为字符“null”或者为字符“undefined”。</p>
     * <br/>
     * <p>注意：此方法为私有方法，参数不允许为null，运行时不会做空指针检查。</p>
     * <br/>
     *
     * @param param 被检测的字符串
     * @return 是否为“null”或者“undefined”
     */
    private static boolean isNullOrUndefinedString(final CharSequence param) {
        final String string = param.toString().trim();
        return StringPool.NULL.equals(string) || StringPool.UNDEFINED.equals(string);
    }
}
