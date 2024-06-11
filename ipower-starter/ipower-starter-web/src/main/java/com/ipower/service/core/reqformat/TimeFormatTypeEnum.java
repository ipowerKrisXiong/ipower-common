package com.ipower.service.core.reqformat;

import java.util.HashMap;
import java.util.Map;

/**
 * 时间格式化类型枚举
 */
public enum TimeFormatTypeEnum {

    TIMESTR("Timestr", "格式化为日期字符串"),
    TIMESTAMP_SEC("TimestampSec", "格式化为时间戳秒"),
    TIMESTAMP_MILLI("TimestampMilli", "格式化为时间戳毫秒");

    private final String code;
    private final String name;

    TimeFormatTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final Map<String, TimeFormatTypeEnum> DATA_MAP = new HashMap<>(2);

    static {
        for (TimeFormatTypeEnum value : values()) {
            DATA_MAP.put(value.getCode(), value);
        }
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static TimeFormatTypeEnum valueOfCode(String code) {
        return DATA_MAP.getOrDefault(code,null);
    }
}
