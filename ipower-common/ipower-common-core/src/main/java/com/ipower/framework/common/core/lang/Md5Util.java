package com.ipower.framework.common.core.lang;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * md5工具类。
 *
 * @author kris
 * @since 1.1.0
 */
@Slf4j
public final class Md5Util {

    /**
     * DES算法
     */
    public static final String MD5_ALGORITHM = "MD5";

    /**
     * 编码器
     */
    public static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();

    /**
     * 解码器
     */
    public static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

    /**
     * 私有化构造函数，禁止实例化该类
     */
    private Md5Util() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 标准字符串MD5加密
     *
     * @param plaintext 明文字符串
     * @return MD5加密后的32位字符
     */
    public static String encrypt(String plaintext) {
        Validate.notEmpty(plaintext);
        try {
            //创建一个MD5算法对象，并获得MD5字节数组,16*8=128位
            byte[] hash = MessageDigest.getInstance(MD5_ALGORITHM).digest(plaintext.getBytes(StandardCharsets.UTF_8));
            //转换为十六进制字符串
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5 encryption is not supported", e);
        }
        return "";
    }

    /**
     * base64编码
     *
     * @param text 需要编码的文本
     * @return String base64编码之后的文本
     */
    public static String base64Encode(String text) {
        return BASE64_ENCODER.encodeToString(Validate.notEmpty(text).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * base64解码
     *
     * @param text 需要解码的文本
     * @return String 解码之后的文本
     */
    public static String base64Decode(String text) {
        return new String(BASE64_DECODER.decode(Validate.notEmpty(text)), StandardCharsets.UTF_8);
    }
}
