package com.ipower.service.core.reqformat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * jsonStr->obj
 * LocalDateTime反序列化 yyyy-MM-dd HH:mm:ss 字符串 -> localDateTime 或 时间戳123123192 -》localDateTime
 */
public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime>{


        private DateTimeFormatter strFormatter;
        private TimeFormatTypeEnum timeFormatType;

        public CustomLocalDateTimeDeserializer() {
        }

        public CustomLocalDateTimeDeserializer(TimeFormatTypeEnum timeFormatType,DateTimeFormatter strFormatter)
        {
            super();
            this.timeFormatType=timeFormatType;
            this.strFormatter = strFormatter;
        }

        @Override
        public LocalDateTime deserialize(JsonParser parser, DeserializationContext context)
                throws IOException {
            return CustomLocalDateTimeTransUtil.timeStampOrStrToLocalDateTime(timeFormatType, parser.getText(),strFormatter);
        }


}
