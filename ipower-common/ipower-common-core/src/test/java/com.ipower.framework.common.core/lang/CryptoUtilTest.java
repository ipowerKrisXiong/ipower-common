package com.ipower.framework.common.core.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * do something in here.
 *
 * @author diablo
 */
class CryptoUtilTest {

    @Test
    void encryption() {
        String text = "123456";
        //noinspection SpellCheckingInspection
        Assertions.assertEquals("eQ2173bnzxs=", CryptoUtil.encryption(text));
    }

    @Test
    void testEncryption() {
        //noinspection SpellCheckingInspection
        String text = "eQ2173bnzxs=";
        Assertions.assertEquals("123456", CryptoUtil.decryption(text));
    }

    @Test
    void decryption() {
        String text = "123456";
        Assertions.assertEquals("ED5wLgc3Mnw=", CryptoUtil.encryption(text, "12345678"));
    }

    @Test
    void testDecryption() {
        String text = "ED5wLgc3Mnw=";
        Assertions.assertEquals("123456", CryptoUtil.decryption(text, "12345678"));
    }
}