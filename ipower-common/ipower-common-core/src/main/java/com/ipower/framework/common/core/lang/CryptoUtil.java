package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.exception.ExceptionUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;

import static com.ipower.framework.common.core.lang.Md5Util.BASE64_DECODER;
import static com.ipower.framework.common.core.lang.Md5Util.BASE64_ENCODER;
import static com.ipower.framework.common.core.lang.ObjectUtil.isEmpty;

/**
 * 加密解密工具.
 *
 * @author kris
 */
@Slf4j
public class CryptoUtil {

    /**
     * 私有化构造函数，禁止实例化该类
     */
    private CryptoUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * DES算法
     */
    public static final String DES_ALGORITHM = "DES";

    /**
     * 默认的公钥
     */
    private static final String DEFAULT_PUBLIC_KEY = "3c3cae90e552445fb978a240579702f1";

    /**
     * 秘钥对象
     */
    private static final SecretKey DEFAULT_SECRET_KEY = getSecretKey();

    /**
     * 加密
     *
     * @param text 需要加密的参数
     * @return String 加密后的字符串
     */
    public static String encryption(String text) {
        return encryption(text, null);
    }

    /**
     * 加密
     *
     * @param text      需要加密的参数
     * @param publicKey 公钥
     * @return String 加密后的字符串
     */
    public static String encryption(String text, String publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(publicKey));
            return new String(BASE64_ENCODER.encode(cipher.doFinal(Validate.notEmpty(text).getBytes())),
                    StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("加密[" + text + "]失败：" + e.getMessage(), e);
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * 解密
     *
     * @param text 需要解密的密文
     * @return String 解密后的字符串
     */
    public static String decryption(String text) {
        return decryption(text, null);
    }

    /**
     * 解密
     *
     * @param text      需要解密的密文
     * @param publicKey 公钥
     * @return String 解密后的字符串
     */
    public static String decryption(String text, String publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(publicKey));
            return new String(cipher.doFinal(BASE64_DECODER.decode(Validate.notEmpty(text).getBytes())),
                    StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("解密[" + text + "]失败：" + e.getMessage(), e);
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    /**
     * 获取秘钥对象
     *
     * @return SecretKey
     */
    private static SecretKey getSecretKey() {
        return getSecretKey(DEFAULT_PUBLIC_KEY);
    }

    /**
     * 获取秘钥对象
     *
     * @return SecretKey
     */
    @SneakyThrows
    private static SecretKey getSecretKey(String publicKey) {
        if (isEmpty(publicKey)) {
            return DEFAULT_SECRET_KEY;
        }
        SecretKeyFactory des = SecretKeyFactory.getInstance(DES_ALGORITHM);
        return des.generateSecret(new DESKeySpec(publicKey.getBytes()));
    }
}
