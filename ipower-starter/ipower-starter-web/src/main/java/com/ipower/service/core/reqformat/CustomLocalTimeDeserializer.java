package com.ipower.service.core.reqformat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomLocalTimeDeserializer extends JsonDeserializer<LocalTime> {

    private DateTimeFormatter strFormatter;
    private TimeFormatTypeEnum timeFormatType;

    public CustomLocalTimeDeserializer() {
    }

    public CustomLocalTimeDeserializer(TimeFormatTypeEnum timeFormatType,DateTimeFormatter strFormatter)
    {
        super();
        this.strFormatter = strFormatter;
        this.timeFormatType = timeFormatType;
    }

    @Override
    public LocalTime deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {

        return CustomLocalDateTimeTransUtil.strToLocalTime(timeFormatType,parser.getText(),strFormatter);
    }

}
