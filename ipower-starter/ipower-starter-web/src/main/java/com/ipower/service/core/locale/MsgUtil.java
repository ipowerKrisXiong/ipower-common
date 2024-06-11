package com.ipower.service.core.locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 本地化文件查询静态工具
 */
public class MsgUtil {

    private static MessageSource messageSource;

    public static void inti(MessageSource messageSource) {
        MsgUtil.messageSource = messageSource;
    }

    /**
     * 获取单个国际化翻译值 en_US
     */
    public static String get(String msgKey) {
        try {
            return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return msgKey;
        }
    }

}
