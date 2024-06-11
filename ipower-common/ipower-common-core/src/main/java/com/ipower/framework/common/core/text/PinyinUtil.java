package com.ipower.framework.common.core.text;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.ipower.framework.common.core.lang.StringUtil;
import com.ipower.framework.common.core.text.pattern.PinyinPattern;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.ipower.framework.common.core.lang.ObjectUtil.nullToDefault;


/**
 * Class description goes here.
 *
 * @author kris
 */
@Slf4j
public final class PinyinUtil {

    private PinyinUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    private static final String REPLACE = "の";

    public static String toPinyin(String str) {
        return toPinyin(str, null, null);
    }

    public static String toPinyin(String str, String delimiter) {
        return toPinyin(str, delimiter, null);
    }

    public static String toPinyin(String str, PinyinPattern pattern) {
        return toPinyin(str, null, pattern);
    }

    public static String toPinyin(String str, String delimiter, PinyinPattern pattern) {
        try {
            boolean toUpper = pattern != null && pattern.getCode() % 2 == 0;
            if (!toUpper) {
                return PinyinHelper.convertToPinyinString(str, nullToDefault(delimiter, ""), getFormat(pattern));
            }
            String[] temp = PinyinHelper.convertToPinyinString(str, REPLACE, getFormat(pattern)).split(REPLACE);
            for (int i = 0; i < temp.length; i++) {
                temp[i] = StringUtil.upperFirst(temp[i]);
            }
            return StringUtil.join(StringUtil.nullToDefault(delimiter, ""), (Object[]) temp);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    public static String toShortPinyin(String str) {
        try {
            return PinyinHelper.getShortPinyin(str);
        } catch (PinyinException e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * 是否多音字
     */
    public static boolean isPolyphony(char c) {
        return PinyinHelper.hasMultiPinyin(c);
    }

    public static List<String> findPolyphony(String str) {
        List<String> list = new ArrayList<>();
        for (char ch : str.toCharArray()) {
            if (isPolyphony(ch)) {
                String s = Character.toString(ch);
                if (list.contains(s)) {
                    continue;
                }
                list.add(Character.toString(ch));
            }
        }
        return list;
    }

    private static PinyinFormat getFormat(PinyinPattern pattern) {
        return pattern == null ? PinyinFormat.WITHOUT_TONE : PinyinFormat.valueOf(pattern.getFormat());
    }
}
