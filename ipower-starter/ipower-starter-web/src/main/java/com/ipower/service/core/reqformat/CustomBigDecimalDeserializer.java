package com.ipower.service.core.reqformat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 自定义bigdecimal解析器
 *
 *  * 局部使用方式
 *  * 可以通过字段注解指定
 *  * //在vo类字段中对字段加上@JsonDeserialize注解
 *  *   @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
 *  *   private BigDecimal exchangeUsdtPrice; //兑换时的价格
 */
public class CustomBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return BigDecimalTransUtil.deserializer(parser.getText());
    }

}
