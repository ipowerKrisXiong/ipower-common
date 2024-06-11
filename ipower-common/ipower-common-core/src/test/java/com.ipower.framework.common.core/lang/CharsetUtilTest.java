package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.constant.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * do something in here.
 *
 * @author diablo
 */
@Slf4j
class CharsetUtilTest {

    @Test
    void charset() {
        Charset charset = CharsetUtil.charset(StringPool.ISO_8859_1);
        Assertions.assertEquals(StandardCharsets.ISO_8859_1, charset);
        Charset charset2 = CharsetUtil.charset(StringPool.GBK);
        Assertions.assertEquals(CharsetUtil.CHARSET_GBK, charset2);
        // 断言异常
        Assertions.assertThrows(UnsupportedCharsetException.class, () -> CharsetUtil.charset("abc"));
    }

    @Test
    void convert() {
        String source = "测试";
        Assertions.assertEquals("娴嬭瘯", CharsetUtil.convert(source, StringPool.UTF_8, StringPool.GBK));
        Assertions.assertEquals("æµ\u008Bè¯\u0095", CharsetUtil.convert(source, StringPool.UTF_8, StringPool.ISO_8859_1));
    }

    @Test
    void testConvert() {
        String source = "测试";
        Assertions.assertEquals("娴嬭瘯", CharsetUtil.convert(source, StandardCharsets.UTF_8, CharsetUtil.CHARSET_GBK));
        Assertions.assertEquals("æµ\u008Bè¯\u0095", CharsetUtil.convert(source, StandardCharsets.UTF_8, StandardCharsets.ISO_8859_1));
        Assertions.assertEquals("测试", CharsetUtil.convert(source, StandardCharsets.UTF_8, null));
    }

    @Test
    void defaultCharsetName() {
        log.warn("-------------->> defaultCharsetName={}", CharsetUtil.defaultCharsetName());
        Assertions.assertEquals(Charset.defaultCharset().name(), CharsetUtil.defaultCharsetName());
    }

    @Test
    void defaultCharset() {
        log.warn("-------------->> defaultCharset={}", CharsetUtil.defaultCharset());
        Assertions.assertEquals(Charset.defaultCharset(), CharsetUtil.defaultCharset());
    }
}