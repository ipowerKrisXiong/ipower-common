package com.ipower.service.core.reqformat;

import java.math.BigDecimal;

/**
 * Bigdecimal 序列化转换工具
 */
public class BigDecimalTransUtil {

    public static String serializer(BigDecimal value)

    {
        return  value.stripTrailingZeros().toPlainString();
    }



    public static BigDecimal deserializer(String value)

    {
//        if(value.endsWith("0")){
//           value = value.replaceAll("0+$", "");
//        }
        return  new BigDecimal(value);
    }

}
