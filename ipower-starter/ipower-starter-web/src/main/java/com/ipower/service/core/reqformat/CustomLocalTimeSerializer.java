package com.ipower.service.core.reqformat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomLocalTimeSerializer extends JsonSerializer<LocalTime> {

    private DateTimeFormatter strFormatter;
    private TimeFormatTypeEnum timeFormatType;

    public CustomLocalTimeSerializer() {
    }
    public CustomLocalTimeSerializer(TimeFormatTypeEnum timeFormatType,DateTimeFormatter strFormatter)
    {
        super();
        this.strFormatter = strFormatter;
        this.timeFormatType = timeFormatType;
    }

    @Override
    public void serialize(LocalTime value, JsonGenerator generator, SerializerProvider provider)
            throws IOException
    {
        String str = CustomLocalDateTimeTransUtil.localTimeToStr(timeFormatType,value,strFormatter);
        generator.writeString(str);
    }
}
