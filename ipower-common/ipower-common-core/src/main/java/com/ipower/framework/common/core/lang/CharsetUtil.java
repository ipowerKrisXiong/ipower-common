package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.constant.StringPool;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.ipower.framework.common.core.lang.ObjectUtil.*;

/**
 * 针对字符集封装的工具类
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">hutool</a>
 *
 * @author kris
 * @since 1.0.0
 */
public class CharsetUtil {

    /**
     * 私有化构造函数，禁止实例化该类
     */
    private CharsetUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * GBK
     */
    public static final Charset CHARSET_GBK = Charset.forName(StringPool.GBK);

    /**
     * 转换为Charset对象
     *
     * @param charsetName 字符集，为空则返回默认字符集
     * @return Charset
     */
    public static Charset charset(String charsetName) {
        return isEmpty(charsetName) ? defaultCharset() : Charset.forName(charsetName);
    }

    /**
     * 转换字符串的字符集编码
     *
     * @param source      字符串
     * @param srcCharset  源字符集
     * @param destCharset 目标字符集，默认UTF-8
     * @return 转换后的字符集
     */
    public static String convert(String source, String srcCharset, String destCharset) {
        return convert(source, charset(srcCharset), charset(emptyToDefault(destCharset, StringPool.UTF_8)));
    }

    /**
     * 转换字符串的字符集编码<br>
     * 当以错误的编码读取为字符串时，打印字符串将出现乱码。<br>
     * 此方法用于纠正因读取使用编码错误导致的乱码问题。<br>
     * 例如，在Servlet请求中客户端用GBK编码了请求参数，我们使用UTF-8读取到的是乱码，此时，使用此方法即可还原原编码的内容
     * <pre>
     * 客户端 -》 GBK编码 -》 Servlet容器 -》 UTF-8解码 -》 乱码
     * 乱码 -》 UTF-8编码 -》 GBK解码 -》 正确内容
     * </pre>
     *
     * @param source      字符串
     * @param srcCharset  源字符集
     * @param destCharset 目标字符集，默认UTF-8
     * @return 转换后的字符集
     */
    public static String convert(String source, Charset srcCharset, Charset destCharset) {
        Charset orig = nullToDefault(srcCharset, defaultCharset());
        Charset dest = nullToDefault(destCharset, StandardCharsets.UTF_8);
        return isEmpty(source) || orig.equals(dest) ? source : new String(source.getBytes(orig), dest);
    }

    /**
     * 系统默认字符集编码
     *
     * @return 系统字符集编码
     */
    public static String defaultCharsetName() {
        return defaultCharset().name();
    }

    /**
     * 系统默认字符集编码
     *
     * @return 系统字符集编码
     */
    public static Charset defaultCharset() {
        return Charset.defaultCharset();
    }
}
