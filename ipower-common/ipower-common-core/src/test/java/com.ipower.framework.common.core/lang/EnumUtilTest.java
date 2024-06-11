package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.text.pattern.PinyinPattern;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * EnumUtil Tester.
 *
 * @author diablo
 */
public class EnumUtilTest {

    /**
     * Method: getEnum(@Nonnull Class<E> clazz, String name)
     */
    @Test
    public void testGetEnumForClazzName() {
        Assertions.assertEquals(PinyinPattern.NONE_TONE, EnumUtil.getEnum(PinyinPattern.class, "NONE_TONE"));
        //忽略大小写测试
        Assertions.assertEquals(PinyinPattern.NONE_TONE, EnumUtil.getEnum(PinyinPattern.class, "none_tone"));
        Assertions.assertEquals(PinyinPattern.NONE_TONE, EnumUtil.getEnum(PinyinPattern.class, "NONE_tone"));
        //测试不存在的
        Assertions.assertNull(EnumUtil.getEnum(PinyinPattern.class, "NONE_TONE_1"));
        Assertions.assertNull(EnumUtil.getEnum(PinyinPattern.class, null));
        //测试异常的
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> EnumUtil.getEnum(null, "NONE_TONE"));
    }

    /**
     * Method: getEnum(@Nonnull Class<E> clazz, int ordinal)
     */
    @Test
    public void testGetEnumForClazzOrdinal() {
        Assertions.assertEquals(PinyinPattern.NONE_TONE, EnumUtil.getEnum(PinyinPattern.class, 0));
        Assertions.assertEquals(PinyinPattern.TONE_MARK, EnumUtil.getEnum(PinyinPattern.class, 2));
        //测试不存在的
        Assertions.assertNull(EnumUtil.getEnum(PinyinPattern.class, 10));
        //测试异常的
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> EnumUtil.getEnum(null, 2));
    }

    /**
     * Method: getEnum(@Nonnull Class<E> clazz, @Nonnull String property, Object value)
     */
    @Test
    public void testGetEnumForClazzPropertyValue() {
        Assertions.assertEquals(PinyinPattern.NONE_TONE, EnumUtil.getEnum(PinyinPattern.class, "description", "不带音调"));
        Assertions.assertEquals(PinyinPattern.TONE_MARK, EnumUtil.getEnum(PinyinPattern.class, "code", 3));
        //测试定制值不存在的
        Assertions.assertNull(EnumUtil.getEnum(PinyinPattern.class, "description", "test"));
        //测试定制属性不存在的
        Assertions.assertNull(EnumUtil.getEnum(PinyinPattern.class, "test", "ok"));
        Assertions.assertNull(EnumUtil.getEnum(PinyinPattern.class, "test", null));
    }

    /**
     * Method: getEnum(@Nonnull Class<E> clazz, @Nonnull String property, Object value)
     */
    @Test
    public void testGetEnumForClazzPropertyValueDefault() {
        Assertions.assertEquals(PinyinPattern.NONE_TONE_FU, EnumUtil.getEnum(PinyinPattern.class, "description", "不带音调，且转换后首字母大写", PinyinPattern.NONE_TONE));
        Assertions.assertEquals(PinyinPattern.TONE_MARK, EnumUtil.getEnum(PinyinPattern.class, "code", 3, PinyinPattern.NONE_TONE));
        //测试定制值不存在的
        Assertions.assertEquals(PinyinPattern.NONE_TONE, EnumUtil.getEnum(PinyinPattern.class, "description", "test", PinyinPattern.NONE_TONE));
        //测试定制属性不存在的
        Assertions.assertEquals(PinyinPattern.NONE_TONE, EnumUtil.getEnum(PinyinPattern.class, "test", "ok", PinyinPattern.NONE_TONE));
        Assertions.assertEquals(PinyinPattern.NONE_TONE, EnumUtil.getEnum(PinyinPattern.class, "test", null, PinyinPattern.NONE_TONE));
    }

}
