package com.ipower.service.core.reqformat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateSerializer extends JsonSerializer<LocalDate> {

    private DateTimeFormatter strFormatter;
    private TimeFormatTypeEnum timeFormatType;

    public CustomLocalDateSerializer() {
    }
    public CustomLocalDateSerializer(TimeFormatTypeEnum timeFormatType,DateTimeFormatter strFormatter)
    {
        super();
        this.strFormatter = strFormatter;
        this.timeFormatType = timeFormatType;
    }

    @Override
    public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider provider)
            throws IOException
    {
        String str = CustomLocalDateTimeTransUtil.localDateToStrOrTimeStamp(timeFormatType,value,strFormatter);
        generator.writeString(str);
    }
}
