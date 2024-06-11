package com.ipower.framework.common.core.lang;

import java.util.UUID;

/**
 * UUID工具类。
 *
 * @author kris
 * @since 1.0.0
 */
public final class UUIDUtil {

    /**
     * 私有化构造函数，禁止实例化该类
     */
    private UUIDUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    public static final int SHORT_LENGTH = 8;

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * 获取短UUID，8位
     *
     * @return String
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public static String genShortUUID() {
        StringBuilder shortBuffer = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < SHORT_LENGTH; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    /**
     * 获取32位UUID
     *
     * @return String
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public static String gen32UUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
