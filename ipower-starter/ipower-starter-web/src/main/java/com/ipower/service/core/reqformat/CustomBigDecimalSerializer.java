package com.ipower.service.core.reqformat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 自定义bigdecimal解析器
 *
 * 局部使用方式
 * 可以通过字段注解指定
 * //在vo类字段中对字段加上@JsonSerialize注解
 *   @JsonSerialize(using = CustomerBigDecimalSerialize.class)
 *   private BigDecimal exchangeUsdtPrice; //兑换时的价格
 */
public class CustomBigDecimalSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(Objects.nonNull(value)) {
            //返回到前端的数据为数字类型,前端接收有可能丢失精度
            //gen.writeNumber(value.stripTrailingZeros());
            //返回到前端的数据为字符串类型,去除后面的0,比如固定精度12.1200000去除后面得00000再变为字符串返回
            gen.writeString(BigDecimalTransUtil.serializer(value));
            //去除0后缀,如果想统一进行保留精度，也可以采用类似处理
        }else {//如果为null的话，就写null
            gen.writeNull();
        }
    }


}
