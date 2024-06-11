package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.constant.StringPool;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.ipower.framework.common.core.lang.ObjectUtil.emptyToDefault;


/**
 * NumberUtil Tester.
 *
 * @author huangad@coracle.com
 */
public class NumberUtilTest {

    /**
     * Method: add(float v1, float v2)
     */
    @Test
    public void testAddForV1V2() {
        //float+float
        float a = 1.2f;
        float b = 2.2f;
        Assertions.assertEquals(3.4d, NumberUtil.add(a, b));
        //float+double
        double c = 3.3d;
        Assertions.assertEquals(4.5d, NumberUtil.add(a, c));
        //double+float
        Assertions.assertEquals(4.5d, NumberUtil.add(c, a));
        //double+double
        double d = 4.4d;
        Assertions.assertEquals(7.7d, NumberUtil.add(c, d));

        //Double+Double
        Double e = 5.5d;
        Double f = 5.5d;
        Double g = null;
        Assertions.assertEquals(5.5d, NumberUtil.add(e, null));
        //noinspection ConstantValue
        Assertions.assertEquals(0.0d, NumberUtil.add(g, null));
        Assertions.assertEquals(11.0d, NumberUtil.add(e, f));

        //Number+Number
        Number number1 = 1.21;
        Number number2 = 2.21;
        Number number3 = null;
        BigDecimal bigDecimal1 = new BigDecimal("3.42");
        BigDecimal bigDecimal2 = new BigDecimal("1.21");
        BigDecimal bigDecimal3 = new BigDecimal("0");

        Assertions.assertEquals(bigDecimal1, NumberUtil.add(number1, number2));
        Assertions.assertEquals(bigDecimal2, NumberUtil.add(number1, null));
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal3, NumberUtil.add(number3, null));
    }

    /**
     * Method: add(Number... values)
     */
    @Test
    public void testAddValues() {
        //Number...Values
        Number number1 = 1.21;
        Number number2 = 2.21;
        Number number3 = null;
        Number number4 = null;
        Number number5 = 3.14;
        BigDecimal bigDecimal2 = new BigDecimal("1.21");
        BigDecimal bigDecimal3 = new BigDecimal("0");
        BigDecimal bigDecimal4 = new BigDecimal("6.56");
        Assertions.assertEquals(bigDecimal4, NumberUtil.add(number1, number2, number5));

        Number number6 = null;
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal3, NumberUtil.add(number3, number4, number6));
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal2, NumberUtil.add(number1, number3, number4));

        //String...Values
        String s1 = null;
        String s2 = null;
        String s3 = null;
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal3, NumberUtil.add(s1, s2, s3));

        String s4 = "3.14";
        String s5 = "3.14";
        String s6 = "3.14";
        BigDecimal bigDecimal7 = new BigDecimal("9.42");
        Assertions.assertEquals(bigDecimal7, NumberUtil.add(s4, s5, s6));

        BigDecimal bigDecimal8 = new BigDecimal("3.14");
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal8, NumberUtil.add(s1, s2, s4));

        //BigDecimal...Values
        BigDecimal bigDecimal9 = new BigDecimal("1");
        BigDecimal bigDecimal10 = new BigDecimal("13.56");
        Assertions.assertEquals(bigDecimal10, NumberUtil.add(bigDecimal7, bigDecimal8, bigDecimal9));

        BigDecimal bigDecimal11 = null;
        BigDecimal bigDecimal12 = null;
        BigDecimal bigDecimal13 = null;
        BigDecimal bigDecimal14 = new BigDecimal("0");
        BigDecimal bigDecimal15 = new BigDecimal("1");

        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal14, NumberUtil.add(bigDecimal11, bigDecimal12, bigDecimal13));
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal15, NumberUtil.add(bigDecimal11, bigDecimal12, bigDecimal9));

    }

    /**
     * Method: sub(float v1, float v2)
     */
    @Test
    public void testSubForV1V2() {
        //float-float
        float a = 1.2f;
        float b = 2.2f;
        Assertions.assertEquals(1.0d, NumberUtil.sub(b, a));

        //float-double
        double c = 3.3d;
        Assertions.assertEquals(-2.1d, NumberUtil.sub(a, c));

        //double-float
        Assertions.assertEquals(2.1d, NumberUtil.sub(c, a));

        //double-double
        double d = 4.4d;
        Assertions.assertEquals(-1.1d, NumberUtil.sub(c, d));

        //Double-Double
        Double e = 5.5d;
        Double f = 5.5d;
        Double g = null;
        Double h = null;
        //noinspection ConstantValue
        Assertions.assertEquals(5.5d, NumberUtil.sub(e, g));
        //noinspection ConstantValue
        Assertions.assertEquals(0.0d, NumberUtil.sub(g, h));
        Assertions.assertEquals(0.0d, NumberUtil.sub(e, f));

        //Number-Number
        Number number1 = 1.21;
        Number number2 = 2.21;
        Number number3 = null;
        Number number4 = null;
        BigDecimal bigDecimal1 = new BigDecimal("1.00");
        BigDecimal multiply = bigDecimal1.multiply(new BigDecimal("-1"));
        BigDecimal bigDecimal2 = new BigDecimal("1.21");
        BigDecimal bigDecimal3 = new BigDecimal("0");

        Assertions.assertEquals(multiply, NumberUtil.sub(number1, number2));
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal2, NumberUtil.sub(number1, number3));
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal3, NumberUtil.sub(number3, number4));
    }

    /**
     * Method: sub(Number... values)
     */
    @Test
    public void testSubValues() {
        //Number...Values
        Number number1 = 1.00;
        Number number2 = 2.00;
        Number number3 = null;
        Number number4 = null;
        Number number5 = 3.14;
        BigDecimal bigDecimal2 = new BigDecimal("1.0");
        BigDecimal bigDecimal3 = new BigDecimal("0");
        BigDecimal bigDecimal4 = new BigDecimal("0.14");
        Assertions.assertEquals(bigDecimal4, NumberUtil.sub(number5, number2, number1));

        Number number6 = null;
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal3, NumberUtil.sub(number3, number4, number6));
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal2, NumberUtil.sub(number1, number3, number4));

        //String...Values
        String s1 = null;
        String s2 = null;
        String s3 = null;
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal3, NumberUtil.sub(s1, s2, s3));

        String s4 = "5.20";
        String s5 = "2.10";
        String s6 = "1.10";
        BigDecimal bigDecimal7 = new BigDecimal("2.00");
        Assertions.assertEquals(bigDecimal7, NumberUtil.sub(s4, s5, s6));

        BigDecimal bigDecimal8 = new BigDecimal("0.00");
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal8, NumberUtil.sub(s4, s2, s4));

        //BigDecimal...Values
        BigDecimal bigDecimal9 = new BigDecimal("1.00");
        Assertions.assertEquals(bigDecimal9, NumberUtil.sub(bigDecimal7, bigDecimal9, bigDecimal8));

        BigDecimal bigDecimal11 = null;
        BigDecimal bigDecimal12 = null;
        BigDecimal bigDecimal13 = null;
        BigDecimal bigDecimal14 = new BigDecimal("0");
        BigDecimal bigDecimal15 = new BigDecimal("1.00");

        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal14, NumberUtil.sub(bigDecimal11, bigDecimal12, bigDecimal13));
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal15, NumberUtil.sub(bigDecimal9, bigDecimal11, bigDecimal12));

    }

    /**
     * Method: mul(float v1, float v2)
     */
    @Test
    public void testMulForV1V2() {
        //float float
        float a = 1.2f;
        float b = 2.2f;
        Assertions.assertEquals(2.64d, NumberUtil.mul(a, b));

        //float+double
        double c = 3.3d;
        Assertions.assertEquals(3.96d, NumberUtil.mul(a, c));

        //double+float
        Assertions.assertEquals(3.96d, NumberUtil.mul(c, a));

        //double+double
        double d = 4.4d;
        Assertions.assertEquals(14.52, NumberUtil.mul(c, d));

        //Double+Double
        Double e = 5.5d;
        Double f = 5.5d;
        Double g = null;

        Assertions.assertEquals(0.0d, NumberUtil.mul(e, null));
        //noinspection ConstantValue
        Assertions.assertEquals(0.0d, NumberUtil.mul(g, null));
        Assertions.assertEquals(30.25, NumberUtil.mul(e, f));

        //Number+Number
        Number number1 = 1.21;
        Number number2 = 2.21;
        Number number3 = null;
        Number number4 = null;
        BigDecimal bigDecimal1 = new BigDecimal("2.6741");
        BigDecimal bigDecimal2 = new BigDecimal("0.00");
        BigDecimal bigDecimal3 = new BigDecimal("0");

        Assertions.assertEquals(bigDecimal1, NumberUtil.mul(number1, number2));
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal2, NumberUtil.mul(number1, number3));
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal3, NumberUtil.mul(number3, number4));
    }

    /**
     * Method: mul(Number... values)
     */
    @Test
    public void testMulValues() {
        //Number...Values
        Number number1 = 1.21;
        Number number2 = 2.21;
        Number number3 = null;
        Number number4 = null;
        Number number5 = 3.14;
        BigDecimal bigDecimal2 = new BigDecimal("0.00");
        BigDecimal bigDecimal3 = new BigDecimal("0");
        BigDecimal bigDecimal4 = new BigDecimal("8.396674");
        Assertions.assertEquals(bigDecimal4, NumberUtil.mul(number1, number2, number5));

        Number number6 = null;
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal3, NumberUtil.mul(number3, number4, number6));
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal2, NumberUtil.mul(number1, number3, number4));

        //String...Values
        String s1 = null;
        String s2 = null;
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal3, NumberUtil.mul(s1, null, null));

        String s4 = "3.14";
        String s5 = "3.14";
        String s6 = "3.14";
        BigDecimal bigDecimal7 = new BigDecimal("30.959144");
        Assertions.assertEquals(bigDecimal7, NumberUtil.mul(s4, s5, s6));

        BigDecimal bigDecimal8 = new BigDecimal("0.00");
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal8, NumberUtil.mul(s1, s2, s4));

        //BigDecimal...Values
        BigDecimal bigDecimal9 = new BigDecimal("1");
        BigDecimal bigDecimal10 = new BigDecimal("0.00");
        BigDecimal bigDecimal0 = new BigDecimal("2");
        Assertions.assertEquals(bigDecimal10, NumberUtil.mul(bigDecimal0, bigDecimal8, bigDecimal9));

        BigDecimal bigDecimal11 = null;
        BigDecimal bigDecimal12 = null;
        BigDecimal bigDecimal13 = null;
        BigDecimal bigDecimal14 = new BigDecimal("0");

        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal14, NumberUtil.mul(bigDecimal11, bigDecimal12, bigDecimal13));
        //noinspection ConstantValue
        Assertions.assertEquals(bigDecimal14, NumberUtil.mul(bigDecimal11, bigDecimal12, bigDecimal9));
    }

    /**
     * Method: div(float v1, float v2)
     */
    @Test
    public void testDivForV1V2() {
        //float+float
        float a = 1.2f;
        float b = 2.2f;
        Assertions.assertEquals(0.5454545455d, NumberUtil.div(a, b), 0.001);
        //float+double
        double c = 3.3d;
        Assertions.assertEquals(0.3636363636d, NumberUtil.div(a, c), 0.001);
        //double+float
        Assertions.assertEquals(2.75d, NumberUtil.div(c, a), 0.001);
        //double+double
        double d = 4.4d;
        Assertions.assertEquals(0.75d, NumberUtil.div(c, d), 0.001);

        //Double+Double
        Double e = 5.5d;
        Double f = 5.5d;
        Double g = 0d;
        Assertions.assertEquals(1.00d, NumberUtil.div(e, f), 0.01);
        Assertions.assertEquals(0.0d, NumberUtil.div(g, e), 0.01);

        //Number+Number
        Number number1 = 1.21;
        Number number2 = 2.21;
        BigDecimal bigDecimal1 = new BigDecimal("0.5475113122");
        Assertions.assertEquals(bigDecimal1, NumberUtil.div(number1, number2));

        //String+String
        String s4 = "3.14";
        String s5 = "3.14";
        BigDecimal bigDecimal7 = new BigDecimal("1.0000000000");
        Assertions.assertEquals(bigDecimal7, NumberUtil.div(s4, s5));
    }

    /**
     * Method: div(float v1, float v2, int scale)
     */
    @Test
    public void testDivForV1V2Scale() {
        //float+float
        float a = 1.21f;
        float b = 2.2f;
        Assertions.assertEquals(0.55d, NumberUtil.div(a, b, 2), 0.001);
        //float+double
        double c = 3.3d;
        Assertions.assertEquals(0.37d, NumberUtil.div(a, c, 2), 0.001);
        //double+float
        Assertions.assertEquals(2.7d, NumberUtil.div(c, a, 1), 0.001);
        //double+double
        double d = 4.4d;
        Assertions.assertEquals(0.75d, NumberUtil.div(c, d, 3), 0.001);

        //Double+Double
        Double e = 5.5d;
        Double f = 5.5d;
        Double g = 0d;
        Assertions.assertEquals(1.00d, NumberUtil.div(e, f, 2), 0.01);
        Assertions.assertEquals(0.0d, NumberUtil.div(g, e, 2), 0.01);

        //Number+Number
        Number number1 = 1.21;
        Number number2 = 2.21;
        Number number3 = 0;
        Number number4 = 1;
        BigDecimal bigDecimal1 = new BigDecimal("0.55");
        BigDecimal bigDecimal3 = new BigDecimal("0.00");
        Assertions.assertEquals(bigDecimal1, NumberUtil.div(number1, number2, 2));
        Assertions.assertEquals(bigDecimal3, NumberUtil.div(number3, number4, 2));

        //String+String
        String s4 = "3.14";
        String s5 = "3.19";
        BigDecimal bigDecimal7 = new BigDecimal("0.98");
        Assertions.assertEquals(bigDecimal7, NumberUtil.div(s4, s5, 2));
    }

    /**
     * Method: div(float v1, float v2, int scale, RoundingMode roundingMode)
     */
    @Test
    public void testDivForV1V2ScaleRoundingMode() {
        //float+float
        float a = 1.20f;
        float b = 2.2f;
        Assertions.assertEquals(0.54d, NumberUtil.div(a, b, 2, RoundingMode.FLOOR), 0.001);
        //float+double
        double c = 3.3d;
        Assertions.assertEquals(0.36d, NumberUtil.div(a, c, 2, RoundingMode.FLOOR), 0.001);
        //double+float
        Assertions.assertEquals(2.7d, NumberUtil.div(c, a, 1, RoundingMode.FLOOR), 0.001);
        //double+double
        double d = 4.4d;
        Assertions.assertEquals(0.75d, NumberUtil.div(c, d, 3, RoundingMode.FLOOR), 0.001);

        //Double+Double
        Double e = 5.5d;
        Double f = 5.5d;
        Double g = 0d;
        Assertions.assertEquals(1.00d, NumberUtil.div(e, f, 2, RoundingMode.FLOOR), 0.01);
        Assertions.assertEquals(0.0d, NumberUtil.div(g, e, 2, RoundingMode.FLOOR), 0.01);

        //Number+Number
        Number number1 = 1.21;
        Number number2 = 2.21;
        Number number3 = 0;
        Number number4 = 1;
        BigDecimal bigDecimal1 = new BigDecimal("0.54");
        BigDecimal bigDecimal3 = new BigDecimal("0.00");
        Assertions.assertEquals(bigDecimal1, NumberUtil.div(number1, number2, 2, RoundingMode.FLOOR));
        Assertions.assertEquals(bigDecimal3, NumberUtil.div(number3, number4, 2, RoundingMode.FLOOR));

        //String+String
        String s4 = "3.14";
        String s5 = "3.19";
        BigDecimal bigDecimal7 = new BigDecimal("0.98");
        Assertions.assertEquals(bigDecimal7, NumberUtil.div(s4, s5, 2, RoundingMode.FLOOR));

    }

    /**
     * Method: round(double v, int scale)
     */
    @Test
    public void testRoundForVScale() {
        double a = 1.555d;
        BigDecimal bigDecimal = new BigDecimal("1.56");
        Assertions.assertEquals(bigDecimal, NumberUtil.round(a, 2));
    }

    /**
     * Method: roundStr(double v, int scale)
     */
    @Test
    public void testRoundStrForVScale() {
        double a = 1.14d;
        String b = "1.1";
        Assertions.assertEquals(b, NumberUtil.roundStr(a, 1));
    }

    /**
     * Method: round(String numberStr, int scale)
     */
    @Test
    public void testRoundForNumberStrScale() {
        String b = "1.234";
        BigDecimal bigDecimal1 = new BigDecimal("1.23");
        Assertions.assertEquals(bigDecimal1, NumberUtil.round(b, 2));
    }

    /**
     * Method: round(BigDecimal number, int scale)
     */
    @Test
    public void testRoundForNumberScale() {
        BigDecimal bigDecimal2 = new BigDecimal("1.565");
        BigDecimal bigDecimal3 = new BigDecimal("1.57");
        Assertions.assertEquals(bigDecimal3, NumberUtil.round(bigDecimal2, 2));
    }

    /**
     * Method: roundStr(String numberStr, int scale)
     */
    @Test
    public void testRoundStrForNumberStrScale() {
        String a = "1.268";
        String b = "1.27";
        Assertions.assertEquals(b, NumberUtil.roundStr(a, 2));
    }

    /**
     * Method: round(double v, int scale, RoundingMode roundingMode)
     */
    @Test
    public void testRoundForVScaleRoundingMode() {
        double a = 1.15d;
        String b = "1.1";
        Assertions.assertEquals(b, NumberUtil.roundStr(a, 1, RoundingMode.FLOOR));
    }

    /**
     * Method: roundStr(double v, int scale, RoundingMode roundingMode)
     */
    @Test
    public void testRoundStrForVScaleRoundingMode() {
        double v = 1.599;
        Assertions.assertEquals("1.59", NumberUtil.roundStr(v, 2, RoundingMode.DOWN));
    }

    /**
     * Method: round(String numberStr, int scale, RoundingMode roundingMode)
     */
    @Test
    public void testRoundForNumberStrScaleRoundingMode() {
        String numberStr = "2.8898";
        BigDecimal bigDecimal = new BigDecimal("2.889");
        Assertions.assertEquals(bigDecimal, NumberUtil.round(numberStr, 3, RoundingMode.DOWN));
    }

    /**
     * Method: round(BigDecimal number, int scale, RoundingMode roundingMode)
     */
    @Test
    public void testRoundForNumberScaleRoundingMode() {
        BigDecimal bigDecimal = new BigDecimal("1.255");
        BigDecimal bigDecimal1 = new BigDecimal("1.26");
        Assertions.assertEquals(bigDecimal1, NumberUtil.round(bigDecimal, 2, RoundingMode.UP));
    }

    /**
     * Method: roundStr(String numberStr, int scale, RoundingMode roundingMode)
     */
    @Test
    public void testRoundStrForNumberStrScaleRoundingMode() {
        String a = "1.872";
        Assertions.assertEquals("1.88", NumberUtil.roundStr(a, 2, RoundingMode.UP));
    }

    /**
     * Method: roundHalfEven(Number number, int scale)
     */
    @Test
    public void testRoundHalfEvenForNumberScale() {
        Number number = 1.245;
        BigDecimal bigDecimal = new BigDecimal("1.24");
        Assertions.assertEquals(bigDecimal, NumberUtil.roundHalfEven(number, 2));
    }

    /**
     * Method: roundHalfEven(BigDecimal value, int scale)
     */
    @Test
    public void testRoundHalfEvenForValueScale() {
        Number number = 1.245;
        BigDecimal bigDecimal = new BigDecimal("1.24");
        Assertions.assertEquals(bigDecimal, NumberUtil.roundHalfEven(number, 2));
    }

    /**
     * Method: roundDown(Number number, int scale)
     */
    @Test
    public void testRoundDownForNumberScale() {
        Number number = 2.456;
        BigDecimal bigDecimal = new BigDecimal("2.45");
        Assertions.assertEquals(bigDecimal, NumberUtil.roundDown(number, 2));
    }

    /**
     * Method: roundDown(BigDecimal value, int scale)
     */
    @Test
    public void testRoundDownForValueScale() {
        BigDecimal bigDecimal = new BigDecimal("1.223");
        BigDecimal bigDecimal1 = new BigDecimal("1.22");
        Assertions.assertEquals(bigDecimal1, NumberUtil.roundDown(bigDecimal, 2));
    }

    /**
     * Method: decimalFormat(String pattern, double value)
     */
    @Test
    public void testDecimalFormatForPatternValue() {
        double a = 12.121d;
        Assertions.assertEquals("12", NumberUtil.decimalFormat("0", a));

        long b = (long) 5.433;
        Assertions.assertEquals("5.00", NumberUtil.decimalFormat("0.00", b));
    }

    /**
     * Method: decimalFormatMoney(double value)
     */
    @Test
    public void testDecimalFormatMoney() {
        double a = 12321111111d;
        Assertions.assertEquals("12,321,111,111.00", NumberUtil.decimalFormatMoney(a));
    }

    /**
     * Method: formatPercent(double number, int scale)
     */
    @Test
    public void testFormatPercent() {
        double a = 0.55559d;
        Assertions.assertEquals("55.56%", NumberUtil.formatPercent(a, 2));
    }

    /**
     * Method: isNumber(String str)
     */
    @Test
    public void testIsNumber() {
        String a = "12E-3";
        String b = "-12.22d";
        String c = "-4";
        Assertions.assertTrue(NumberUtil.isNumber(a));
        Assertions.assertTrue(NumberUtil.isNumber(b));
        Assertions.assertTrue(NumberUtil.isNumber(c));
    }

    /**
     * Method: isValidIfNumber(Object obj)
     */
    @Test
    public void testIsValidIfNumber() {
        Double a = 112d;
        BigDecimal c = new BigDecimal("22");
        Number d = 44;
        Assertions.assertTrue(NumberUtil.isValidIfNumber(a));
        Assertions.assertTrue(NumberUtil.isValidIfNumber(null));
        Assertions.assertTrue(NumberUtil.isValidIfNumber(c));
        Assertions.assertTrue(NumberUtil.isValidIfNumber(d));
    }

    /**
     * Method: isInteger(String s)
     */
    @Test
    public void testIsInteger() {
        String a = "5.1";
        String b = "2";

        Assertions.assertFalse(NumberUtil.isInteger(a));
        Assertions.assertTrue(NumberUtil.isInteger(b));
    }

    /**
     * Method: isLong(String s)
     */
    @Test
    public void testIsLong() {
        String a = "5.1";
        long b = 123;
        String c = String.valueOf(b);

        Assertions.assertFalse(NumberUtil.isLong(a));
        Assertions.assertTrue(NumberUtil.isLong(c));
    }

    /**
     * Method: isDouble(String s)
     */
    @Test
    public void testIsDouble() {
        String a = "5";
        double b = 123.2d;
        String c = String.valueOf(b);

        Assertions.assertFalse(NumberUtil.isDouble(a));
        Assertions.assertTrue(NumberUtil.isDouble(c));
    }

    /**
     * Method: isPrimes(int n)
     */
    @Test
    public void testIsPrimes() {
        int a = 2;
        int b = 4;

        Assertions.assertTrue(NumberUtil.isPrimes(a));
        Assertions.assertFalse(NumberUtil.isPrimes(b));
    }

    /**
     * Method: generateRandomNumber(int begin, int end, int size)
     */
    @Test
    public void testGenerateRandomNumber() {
        int[] arr = NumberUtil.generateRandomNumber(0, 9, 9);
        int x = arr[0];
        for (int i = 1; i < 9; i++) {
            Assertions.assertTrue(x != arr[i]);
        }
    }

    /**
     * Method: generateBySet(int begin, int end, int size)
     */
    @Test
    public void testGenerateBySet() {
        Integer[] arr = NumberUtil.generateBySet(0, 9, 9);
        int x = arr[0];
        for (int i = 1; i < 9; i++) {
            Assertions.assertTrue(x != arr[i]);
        }
    }

    /**
     * Method: range(int stop)
     */
    @Test
    public void testRangeStop() {
        int[] arr = NumberUtil.range(8);
        int x = arr[0];

        for (int i = 1; i <= 8; i++) {
            Assertions.assertEquals(x, arr[i] - 1);
            x = arr[i];
        }
    }

    /**
     * Method: range(int start, int stop)
     */
    @Test
    public void testRangeForStartStop() {
        int[] arr = NumberUtil.range(2, 9);
        int x = arr[0];

        for (int i = 1; i <= 7; i++) {
            Assertions.assertEquals(x, arr[i] - 1);
            x = arr[i];
        }
    }

    /**
     * Method: range(int start, int stop, int step)
     */
    @Test
    public void testRangeForStartStopStep() {
        int[] arr = NumberUtil.range(2, 6, 2);
        int x = arr[0];

        for (int i = 1; i <= 2; i++) {
            Assertions.assertEquals(x, arr[i] - 2);
            x = arr[i];
        }
    }

    /**
     * Method: appendRange(int start, int stop, Collection<Integer> values)
     */
    @Test
    public void testAppendRangeForStartStopValues() {
        List<Integer> collection = new ArrayList<>();
        collection.add(2);
        collection.add(5);
        List<Integer> testCollection = (List<Integer>) NumberUtil.appendRange(3, 9, collection);

        Integer x = testCollection.get(2);
        for (int i = 2; i < testCollection.size(); i++) {
            Assertions.assertEquals(x, testCollection.get(i));
            x++;
        }
    }

    /**
     * Method: appendRange(int start, int stop, int step, Collection<Integer> values)
     */
    @Test
    public void testAppendRangeForStartStopStepValues() {
        List<Integer> collection = new ArrayList<>();
        collection.add(2);
        collection.add(5);
        List<Integer> testCollection = (List<Integer>) NumberUtil.appendRange(3, 9, 2, collection);

        Integer x = testCollection.get(2);
        for (int i = 2; i < testCollection.size(); i++) {
            Assertions.assertEquals(x, testCollection.get(i));
            x = x + 2;
        }
    }

    /**
     * Method: factorial(long start, long end)
     */
    @Test
    public void testFactorialForStartEnd() {
        Assertions.assertEquals(60, NumberUtil.factorial(5, 2));
    }

    /**
     * Method: factorial(long n)
     */
    @Test
    public void testFactorialN() {
        Assertions.assertEquals(120, NumberUtil.factorial(5));
    }

    /**
     * Method: sqrt(long x)
     */
    @Test
    public void testSqrt() {
        Assertions.assertEquals(2, NumberUtil.sqrt(4));
        Assertions.assertEquals(0, NumberUtil.sqrt(-1));
    }

    /**
     * Method: processMultiple(int selectNum, int minNum)
     */
    @Test
    public void testProcessMultiple() {
        int a = 7;
        int b = 5;
        Assertions.assertEquals(21, NumberUtil.processMultiple(a, b));
    }

    /**
     * Method: divisor(int m, int n)
     */
    @Test
    public void testDivisor() {
        int a = 18;
        int b = 81;
        Assertions.assertEquals(9, NumberUtil.divisor(a, b));
    }

    /**
     * Method: multiple(int m, int n)
     */
    @Test
    public void testMultiple() {
        int a = 7;
        int b = 5;
        Assertions.assertEquals(35, NumberUtil.multiple(a, b));
    }

    /**
     * Method: getBinaryStr(Number number)
     */
    @Test
    public void testGetBinaryStr() {
        Number a = 53;
        Assertions.assertEquals("110101", NumberUtil.getBinaryStr(a));
    }

    /**
     * Method: binaryToInt(String binaryStr)
     */
    @Test
    public void testBinaryToInt() {
        String a = "11010";
        Assertions.assertEquals(26, NumberUtil.binaryToInt(a));
    }

    /**
     * Method: binaryToLong(String binaryStr)
     */
    @Test
    public void testBinaryToLong() {
        String a = "11011";
        Assertions.assertEquals(27, NumberUtil.binaryToLong(a));
    }

    /**
     * Method: compare(char x, char y)
     */
    @Test
    public void testCompareForXY() {
        char a = '9';
        char b = '8';
        Assertions.assertEquals(1, NumberUtil.compare(a, b));

        double c = 1.1d;
        double d = 3.2d;
        Assertions.assertEquals(-1, NumberUtil.compare(c, d));

        short e = 1;
        short f = 1;
        Assertions.assertEquals(0, NumberUtil.compare(e, f));

        long g = 18;
        long h = 19;
        Assertions.assertEquals(-1, NumberUtil.compare(g, h));

        int j = 55;
        int k = 43;
        Assertions.assertEquals(1, NumberUtil.compare(j, k));

        byte l = 127;
        byte m = 126;
        Assertions.assertEquals(1, NumberUtil.compare(l, m));
    }

    /**
     * Method: isGreater(BigDecimal bigNum1, BigDecimal bigNum2)
     */
    @Test
    public void testIsGreater() {
        BigDecimal bigDecimal1 = new BigDecimal("212.212");
        BigDecimal bigDecimal2 = new BigDecimal("123.11");
        Assertions.assertTrue(NumberUtil.isGreater(bigDecimal1, bigDecimal2));
    }


    /**
     * Method: isGreaterOrEqual(BigDecimal bigNum1, BigDecimal bigNum2)
     */
    @Test
    public void testIsGreaterOrEqual() {
        BigDecimal bigDecimal1 = new BigDecimal("212.212");
        BigDecimal bigDecimal2 = new BigDecimal("212.212");
        Assertions.assertTrue(NumberUtil.isGreaterOrEqual(bigDecimal1, bigDecimal2));
    }

    /**
     * Method: isLess(BigDecimal bigNum1, BigDecimal bigNum2)
     */
    @Test
    public void testIsLess() {
        BigDecimal bigDecimal1 = new BigDecimal("2121.212");
        BigDecimal bigDecimal2 = new BigDecimal("212.212");
        Assertions.assertFalse(NumberUtil.isLess(bigDecimal1, bigDecimal2));
    }

    /**
     * Method: isLessOrEqual(BigDecimal bigNum1, BigDecimal bigNum2)
     */
    @Test
    public void testIsLessOrEqual() {
        BigDecimal bigDecimal1 = new BigDecimal("212.212");
        BigDecimal bigDecimal2 = new BigDecimal("212.212");
        Assertions.assertTrue(NumberUtil.isLessOrEqual(bigDecimal1, bigDecimal2));
    }

    /**
     * Method: equals(BigDecimal bigNum1, BigDecimal bigNum2)
     */
    @Test
    public void testEquals() {
        BigDecimal bigDecimal1 = new BigDecimal("2121.00");
        BigDecimal bigDecimal2 = new BigDecimal("2121");
        Assertions.assertTrue(NumberUtil.equals(bigDecimal1, bigDecimal2));
    }

    /**
     * Method: toString(Number number, String defaultValue)
     */
    @Test
    public void testToStringForNumberDefaultValue() {
        Number number = 55.00;
        String defaultValue = "0";
        String s = "55";
        Assertions.assertEquals(s, NumberUtil.toString(number, defaultValue));
    }

    /**
     * Method: toString(Number number)
     */
    @Test
    public void testToStringNumber() {
        Number number = 55.00;
        String s = "55";
        Assertions.assertEquals(s, NumberUtil.toString(number));
    }

    /**
     * Method: toBigDecimal(Number number)
     */
    @Test
    public void testToBigDecimalNumber() {
        Number a = 23.10;
        Assertions.assertEquals(new BigDecimal("23.1"), NumberUtil.toBigDecimal(a));

        String b = "33.20";
        Assertions.assertEquals(new BigDecimal("33.20"), NumberUtil.toBigDecimal(b));
    }

    /**
     * Method: count(int total, int part)
     */
    @Test
    public void testCount() {
        int total = 100, part = 25;
        Assertions.assertEquals(4, NumberUtil.count(total, part));
    }

    /**
     * Method: null2Zero(BigDecimal decimal)
     */
    @Test
    public void testNull2Zero() {
        Assertions.assertEquals(new BigDecimal("0"), NumberUtil.nullToZero(null));
    }

    /**
     * Method: zero2One(int value)
     */
    @Test
    public void testZero2One() {
        int value = 0;
        int value1 = 1;
        int value2 = -1;

        Assertions.assertEquals(1, NumberUtil.zero2One(value));
        Assertions.assertEquals(1, NumberUtil.zero2One(value1));
        Assertions.assertEquals(-1, NumberUtil.zero2One(value2));
    }

    /**
     * Method: newBigInteger(String str)
     */
    @Test
    public void testNewBigInteger() {
        String s1 = "5";
        String s2 = "";

        Assertions.assertEquals(new BigInteger("5"), NumberUtil.newBigInteger(s1));
        Assertions.assertNull(NumberUtil.newBigInteger(s2));
    }

    /**
     * Method: isBeside(long number1, long number2)
     */
    @Test
    public void testIsBesideForNumber1Number2() {
        long a = 1, b = 2, c = 3;
        Assertions.assertTrue(NumberUtil.isBeside(a, b));
        Assertions.assertFalse(NumberUtil.isBeside(a, c));

        int d = 1, e = 2, f = 3;
        Assertions.assertTrue(NumberUtil.isBeside(d, e));
        Assertions.assertFalse(NumberUtil.isBeside(d, f));
    }

    /**
     * Method: partValue(int total, int partCount)
     */
    @Test
    public void testPartValueForTotalPartCount() {
        int total = 80;
        int part = 40;
        Assertions.assertEquals(2, NumberUtil.partValue(total, part));

        int part2 = 30;
        Assertions.assertEquals(3, NumberUtil.partValue(total, part2));
    }

    /**
     * Method: partValue(int total, int partCount, boolean isPlusOneWhenHasRem)
     */
    @Test
    public void testPartValueForTotalPartCountIsPlusOneWhenHasRem() {
        int total = 80;
        int part = 40;
        Assertions.assertEquals(2, NumberUtil.partValue(total, part, true));

        int part2 = 30;
        Assertions.assertEquals(3, NumberUtil.partValue(total, part2, true));
        Assertions.assertEquals(2, NumberUtil.partValue(total, part2, false));
    }

    /**
     * Method: pow(Number number, int n)
     */
    @Test
    public void testPowForNumberN() {
        Number number = 6;
        int n = 3;
        Assertions.assertEquals(new BigDecimal("216"), NumberUtil.pow(number, n));

        BigDecimal bigDecimal = new BigDecimal("6");
        Assertions.assertEquals(new BigDecimal("216"), NumberUtil.pow(bigDecimal, n));
    }

    /**
     * Method: parseInt(String number)
     */
    @Test
    public void testParseInt() {
        String s1 = "0x16";
        String s2 = "21";
        String s3 = "1011";
        String s4 = "";
        String s5 = "3.14";

        Assertions.assertEquals(22, NumberUtil.parseInt(s1));
        Assertions.assertEquals(21, NumberUtil.parseInt(s2));
        Assertions.assertEquals(1011, NumberUtil.parseInt(s3));
        Assertions.assertEquals(0, NumberUtil.parseInt(s4));
        Assertions.assertEquals(3, NumberUtil.parseInt(s5));
    }

    /**
     * Method: parseLong(String number)
     */
    @Test
    public void testParseLong() {
        String s1 = "0x16";
        String s2 = "21";
        String s3 = "1011";
        String s4 = "";
        String s5 = "3.14";

        long a = 22L;
        long b = 21L;
        long c = 1011L;
        long d = 0L;
        long e = 3L;
        Assertions.assertEquals(a, NumberUtil.parseLong(s1));
        Assertions.assertEquals(b, NumberUtil.parseLong(s2));
        Assertions.assertEquals(c, NumberUtil.parseLong(s3));
        Assertions.assertEquals(d, NumberUtil.parseLong(s4));
        Assertions.assertEquals(e, NumberUtil.parseLong(s5));
    }

    /**
     * Method: parseNumber(String numberStr)
     */
    @Test
    public void testParseNumber() {
        String s = "22l";
        Number number = 22L;
        Assertions.assertEquals(number, NumberUtil.parseNumber(s));
    }

    /**
     * Method: toBytes(int value)
     */
    @Test
    public void testToBytes() {
        int a = 1000;
        byte[] arr = new byte[]{0, 0, 3, -24};

        Assertions.assertArrayEquals(arr, NumberUtil.toBytes(a));
    }

    /**
     * Method: toInt(byte[] bytes)
     */
    @Test
    public void testToInt() {
        int a = 1000;
        byte[] arr = new byte[]{0, 0, 3, -24};

        Assertions.assertEquals(a, NumberUtil.toInt(arr));
    }


    /**
     * Method: toUnsignedByteArray(BigInteger value)
     */
    @Test
    public void testToUnsignedByteArrayValue() {
        BigInteger a = new BigInteger("12345678");
        byte[] arr = new byte[]{-68, 97, 78};

        Assertions.assertArrayEquals(arr, NumberUtil.toUnsignedByteArray(a));
    }

    /**
     * Method: toUnsignedByteArray(int length, BigInteger value)
     */
    @Test
    public void testToUnsignedByteArrayForLengthValue() {
        BigInteger a = new BigInteger("12345678");
        int length = 6;
        byte[] arr = new byte[]{0, 0, 0, -68, 97, 78};
        byte[] bytes = NumberUtil.toUnsignedByteArray(length, a);

        Assertions.assertArrayEquals(arr, bytes);
    }

    /**
     * Method: fromUnsignedByteArray(byte[] buf)
     */
    @Test
    public void testFromUnsignedByteArrayBuf() {
        byte[] bytes = new byte[]{0, 0, 0, -68, 97, 78};
        BigInteger a = new BigInteger("12345678");
        Assertions.assertEquals(a, NumberUtil.fromUnsignedByteArray(bytes));
    }

    /**
     * Method: fromUnsignedByteArray(byte[] buf, int off, int length)
     */
    @Test
    public void testFromUnsignedByteArrayForBufOffLength() {
        byte[] arr = new byte[]{-68, 97, 78};
        int off = 1;
        int length = 2;

        BigInteger a = new BigInteger("24910");
        Assertions.assertEquals(a, NumberUtil.fromUnsignedByteArray(arr, off, length));
    }

    @Test
    public void test() {
        String path = "1.2.3";
        System.out.println(StringUtil.split(path, StringPool.BACK_SLASH_DOT));
        Assertions.assertEquals(3, path.lastIndexOf(StringPool.DOT));
        Assertions.assertEquals(-1, path.lastIndexOf(StringPool.BACK_SLASH_DOT));

        String s5 = "3.14";
        System.out.println(NumberUtil.parseLong(s5));
        System.out.println("DICT".concat(StringPool.COLON));
        System.out.println("CONF".concat(StringPool.COLON).concat("KEY"));

        String redisKey = "DICT";
        String grouping = "DEV";
        String root = "TYPE";
        System.out.println(redisKey.concat(StringPool.COLON).concat(emptyToDefault(grouping, "DEFAULT")).concat(StringPool.COLON).concat(emptyToDefault(root, StringPool.HASH)));
    }

}
