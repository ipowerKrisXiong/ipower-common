package com.ipower.framework.common.core.date;

import com.ipower.framework.common.core.date.pattern.DateFormatPattern;
import com.ipower.framework.common.core.date.pattern.DatePattern;
import com.ipower.framework.common.core.session.Users;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DateUtil Tester.
 *
 * @author huangad@coracle.com
 */
public class DateUtilTest {

    /**
     * Method: toString(Long timestamp)
     * 测试Long timestamp转为String格式
     */
    @Test
    public void testToStringTimestamp() {
        System.out.println(Users.id());
        System.out.println(Users.name());
        System.out.println(Users.userName());
        System.out.println(Users.roleIds());
        System.out.println(Users.roleCodes());
        System.out.println(Users.deptIds());
        System.out.println(Users.tenantId());
        Users.print();
        Long longTimeTamp = 1000000000000L;
        assertEquals("2001-09-09", DateUtil.toString(longTimeTamp));

    }

    /**
     * 将timestamp时间类型转为字符串类型,并按照pattern匹配的格式进行转换
     * Method: toString(Long timestamp, String pattern)
     */
    @Test
    public void testToStringForTimestampPattern() {
        String strTimeTamp = "yyyy-MM-dd HH:mm:ss";
        Long longTimeTamp = 3000000000000L;
        assertEquals("2065-01-24 13:20:00", DateUtil.toString(longTimeTamp, strTimeTamp));
    }

    /**
     * 将Date类型按yyyy-MM-dd格式转为字符串类型
     * Method: toString(Date date)
     */
    @Test
    public void testToStringDate() {
        Date date = new Date();
        assertEquals(DateTime.now().toString("yyyy-MM-dd"), DateUtil.toString(date));
    }

    /**
     * 传入date和格式返回Date类型
     * Method: toString(Date date, String pattern)
     */
    @Test
    public void testToStringForDatePattern() {

        String pattern = "yyyy-MM-dd HH:mm:ss";
        Date date = new Date(3000000000000L);
        assertEquals("2065-01-24 13:20:00", DateUtil.toString(date, pattern));
    }

    /**
     * 传入timestamp转成Date类型
     * Method: toDate(Long timestamp)
     */
    @Test
    public void testToDateTimestamp() {

        Long longTimeTamp = 2222222222222L;
        Date date = DateUtil.toDate(longTimeTamp);
        assert date != null;
        assertEquals("Sat Jun 02 11:57:02 CST 2040", date.toString());
    }

    /**
     * 传入时间str转成Date类型
     * Method: toDate(String str)
     */
    @Test
    public void testToDateStr() {

        String dateStr = "1997-01-27 12:00:00";
        Date date = DateUtil.toDate(dateStr);

        assertEquals("Mon Jan 27 12:00:00 CST 1997", date.toString());
    }

    /**
     * 传入str转成pattern格式的Date类型对象
     * Method: toDate(String str, String pattern)
     */
    @Test
    public void testToDateForStrPattern() {
        String dateStr = "1997-01-27";
        String pattern = "yy-MM-dd";
        Date date = DateUtil.toDate(dateStr, pattern);

        assert date != null;
        assertEquals("Mon Jan 27 00:00:00 CST 1997", date.toString());
    }

    /**
     * 传入timestamp转为Date并且为这一天的开始时间（最小值）
     * Method: toDateWithMinimum(Long timestamp)
     */
    @Test
    public void testToDateWithMinimumTimestamp() {

        Long longMinimumTimestamp = 2222222222222L;
        Date date = DateUtil.toDateWithMinimum(longMinimumTimestamp);
        assertEquals("Sat Jun 02 00:00:00 CST 2040", date.toString());
    }

    /**
     * 传入字符串为Date并且为这一天的开始时间（最小值）
     * Method: toDateWithMinimum(String str)
     */
    @Test
    public void testToDateWithMinimumStr() {
        String str = "2016-11-11";
        Date date = DateUtil.toDateWithMinimum(str);
        assertEquals("Fri Nov 11 00:00:00 CST 2016", date.toString());

    }

    /**
     * 传入字符串和格式转为Date并且为这一天的开始时间（最小值）
     * Method: toDateWithMinimum(String str, String pattern)
     */
    @Test
    public void testToDateWithMinimumForStrPattern() {
        String str = "2016-11-11";
        String pattern = "yy-MM-dd";
        Date date = DateUtil.toDateWithMinimum(str, pattern);
        assertEquals("Fri Nov 11 00:00:00 CST 2016", date.toString());
    }

    /**
     * 传入date转成这一天的开始时间（最小值）并返回
     * Method: toDateWithMinimum(Date date)
     */
    @Test
    public void testToDateWithMinimumDate() {
        Date date = new Date(200000000L);
        Date date1 = DateUtil.toDateWithMinimum(date);
        assert date1 != null;
        assertEquals("Sat Jan 03 00:00:00 CST 1970", date1.toString());
    }

    /**
     * 传入timestamp转为Date并且为这一天的结束时间（最大值）
     * Method: toDateWithMaximum(Long timestamp)
     */
    @Test
    public void testToDateWithMaximumTimestamp() {
        Long longMinimumTimestamp = 2222222222222L;
        Date date = DateUtil.toDateWithMaximum(longMinimumTimestamp);
        assertEquals("Sat Jun 02 23:59:59 CST 2040", date.toString());
    }

    /**
     * 传入字符串为Date并且为这一天的结束时间（最大值）
     * Method: toDateWithMaximum(String str)
     */
    @Test
    public void testToDateWithMaximumStr() {
        String str = "2016-11-11";
        Date date = DateUtil.toDateWithMaximum(str);
        assertEquals("Fri Nov 11 23:59:59 CST 2016", date.toString());
    }

    /**
     * 传入字符串和格式转为Date并且为这一天的结束时间（最大值）
     * Method: toDateWithMaximum(String str, String pattern)
     */
    @Test
    public void testToDateWithMaximumForStrPattern() {
        String str = "2016-11-11";
        String pattern = "yy-MM-dd";
        Date date = DateUtil.toDateWithMaximum(str, pattern);
        assertEquals("Fri Nov 11 23:59:59 CST 2016", date.toString());
    }

    /**
     * 传入date转成这一天的结束（最大值）并返回
     * Method: toDateWithMaximum(Date date)
     */
    @Test
    public void testToDateWithMaximumDate() {
        Date date = new Date(200000000L);
        Date date1 = DateUtil.toDateWithMaximum(date);
        assert date1 != null;
        assertEquals("Sat Jan 03 23:59:59 CST 1970", date1.toString());
    }

    /**
     * 判断date时间是否在现在之前
     * Method: beforeNow(Date date)
     */
    @Test
    public void testBeforeNow() {
        Date date = new Date(222222222222222L);
        assertFalse(DateUtil.beforeNow(date));
    }

    /**
     * 判断date时间是否在现在之后
     * Method: afterNow(Date date)
     */
    @Test
    public void testAfterNow() {
        Date date = new Date(222222222222222L);
        assertTrue(DateUtil.afterNow(date));
    }

    /**
     * 判断时间one是否在时间two之前
     * Method: before(Date one, Date two)
     */
    @Test
    public void testBefore() {
        Date date1 = new Date(12312313L);
        Date date2 = new Date(111111111111111L);
        assertTrue(DateUtil.before(date1, date2));
    }

    /**
     * 判断时间one是否在时间two之后
     * Method: after(Date one, Date two)
     */
    @Test
    public void testAfter() {
        Date date1 = new Date(12312313L);
        Date date2 = new Date(111111111111111L);
        assertTrue(DateUtil.after(date2, date1));
    }

    /**
     * Method: getFormatPattern(String value)
     */
    @Test
    public void testGetFormatPattern() {
        String value = "2019-12-12 12:12:12";
        DateFormatPattern formatPattern = DateUtil.getFormatPattern(value);
        assert formatPattern != null;
        assertEquals("COMMON_DATE_TIME", formatPattern.toString());
    }

    /**
     * Method: getFormatPattern(String value)
     */
    @Test
    public void testsecondToTime() {
        System.out.println(DateUtil.secondToTime(1000L));
        assertEquals("0小时16分40秒", DateUtil.secondToTime(1000L));
        System.out.println(DateUtil.secondToTime(1000000000000000L));
    }


}
