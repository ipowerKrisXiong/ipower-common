package com.ipower.framework.common.core.text;

import com.github.stuxuhai.jpinyin.ChineseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description goes here.
 *
 * @author kris
 */
public final class ChineseUtil {

    private ChineseUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }


    public static String toTraditional(String str) {
        return ChineseHelper.convertToTraditionalChinese(str);
    }

    public static String toSimplified(String str) {
        return ChineseHelper.convertToSimplifiedChinese(str);
    }

    public static boolean isTraditional(char c) {
        return ChineseHelper.isTraditionalChinese(c);
    }

    public static List<String> findTraditional(String str) {
        List<String> list = new ArrayList<>();
        for (char ch : str.toCharArray()) {
            if (isTraditional(ch)) {
                String s = Character.toString(ch);
                if (list.contains(s)) {
                    continue;
                }
                list.add(Character.toString(ch));
            }
        }
        return list;
    }

    public static boolean isChinese(char c) {
        return ChineseHelper.isChinese(c);
    }

    public static boolean hasChinese(String str) {
        return ChineseHelper.containsChinese(str);
    }

    public static List<String> findChinese(String str) {
        List<String> list = new ArrayList<>();
        for (char ch : str.toCharArray()) {
            if (isChinese(ch)) {
                String s = Character.toString(ch);
                if (list.contains(s)) {
                    continue;
                }
                list.add(Character.toString(ch));
            }
        }
        return list;
    }

    public static void main(String[] args) {
//        System.out.println(toTraditional("义气行事"));
//        System.out.println(toSimplified("義氣"));
//        System.out.println(isTraditional('義'));
//        System.out.println(findTraditional("義氣行事"));
//        System.out.println(isChinese('A'));
//        System.out.println(isChinese('我'));
//        System.out.println(hasChinese("this is 義气"));
//        System.out.println(findChinese("this is 義气"));
    }
}
