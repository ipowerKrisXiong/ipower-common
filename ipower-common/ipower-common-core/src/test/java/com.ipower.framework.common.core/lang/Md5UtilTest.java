package com.ipower.framework.common.core.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * do something in here.
 *
 * @author diablo
 */
class Md5UtilTest {

    @Test
    void encrypt() {
        String plaintext = "123456";
        Assertions.assertEquals("e10adc3949ba59abbe56e057f20f883e", Md5Util.encrypt(plaintext));
        String plaintext2 = ".42fnWvbV";
        Assertions.assertEquals("a268b945627948636f20e84653e9d66e", Md5Util.encrypt(plaintext2));
    }

    @Test
    void base64Encode() {
        String text = "123456";
        Assertions.assertEquals("MTIzNDU2", Md5Util.base64Encode(text));
        String text2 = ".42fnWvbV";
        Assertions.assertEquals("LjQyZm5XdmJW", Md5Util.base64Encode(text2));
    }

    @Test
    void base64Decode() {
        String text = "MTIzNDU2";
        Assertions.assertEquals("123456", Md5Util.base64Decode(text));
        String text2 = "LjQyZm5XdmJW";
        Assertions.assertEquals(".42fnWvbV", Md5Util.base64Decode(text2));
    }
}