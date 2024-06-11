package com.ipower.service.core.reqformat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private DateTimeFormatter strFormatter;
    private TimeFormatTypeEnum timeFormatType;

    public CustomLocalDateDeserializer() {
    }

    public CustomLocalDateDeserializer(TimeFormatTypeEnum timeFormatType,DateTimeFormatter strFormatter)
    {
        super();
        this.timeFormatType = timeFormatType;
        this.strFormatter=strFormatter;
    }

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {
        return CustomLocalDateTimeTransUtil.timeStampOrStrTolocalDate(timeFormatType, parser.getText(),strFormatter);
    }


}
