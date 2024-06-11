package com.ipower.service.core.reqformat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * localDateTime json序列化器，转时间戳或者字符串 yyyy-MM-dd HH:mm:ss
 */
public class CustomLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    private DateTimeFormatter strFormatter;
    private TimeFormatTypeEnum timeFormatType;


    //按格式化构造
    public CustomLocalDateTimeSerializer(TimeFormatTypeEnum timeFormatType,DateTimeFormatter strFormatter)
    {
        super();
        this.timeFormatType = timeFormatType;
        this.strFormatter = strFormatter;
    }

    //obj-》jsonStr
    @Override
    public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider provider)
            throws IOException
    {
        String res =  CustomLocalDateTimeTransUtil.localDateTimeToStrOrTimeStamp(timeFormatType, value, strFormatter);
        generator.writeString(res);
    }

}
