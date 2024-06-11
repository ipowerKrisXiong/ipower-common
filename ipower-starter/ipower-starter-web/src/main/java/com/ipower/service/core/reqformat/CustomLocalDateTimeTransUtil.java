package com.ipower.service.core.reqformat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * 工具类，用于localDateTime，localtime，localdate的 根据当前Locale变量进行不同时区的转换
 *
 * req "2022-01-01 10:00:00"-> controller中localDateTime="2022-01-01 10:00:00" ->mysql存储的时候要靠链接中配置的会话时区&serverTimezone=Asia/Shanghai配置和本地时间同一个时区才能保证保存正确(也就是说jvm的时区和这个要配置成一样)
 * 数据库默认时区设置是System，跟随系统时区。和上面的会话时区要统一
 * 数据库的Date、Time、DateTime类型不支持时区转换
 * TimeStamp列的值从会话时区转换为UTC以进行存储，并从UTC转换为会话时区以进行检索。
 * 可以理解为它存储时区，而DateTime不存储时区。
 * 且从DateTime和TimeStamp的表示范围上，我们就能看出来DateTime有点字符串的意思，而TimeStamp则不是。
 * timestamp在mysql5.6.2以下是只能支持到2038年，以上可以支持到1千年后
 * 以上参考：https://blog.csdn.net/m0_38072683/article/details/105011313
 *
 * 正常请求日期字符串使用YYYY-MM-DD HH:mm:ss
 * 如果是国际化请求，为了表达时区，最好用ISO 8601格式(当前版本还未支持，后续再扩展)，也就是YYYY-MM-DDThh:mm:ss[.mmm]TZD 中间的T分割，后面的TZD表达时区
 * Z是0时区，或+hh:mm或-hh:mm，+或-表示时区距离UTC(世界标准时间)时区多远。例如 2012-03-29T10:05:45Z 2012-03-29T10:05:45-06:00 2012-03-29T10:05:45+08:00
 *
 * 国家化时间转换的最终效果为:
 *
 * n个不同时区来的日期字符串 -> jvm系统默认时区下的localDateTime -> 和jvm同一时区下的mysql存储
 *
 * LocalDateTime的理解，LocalDateTime是一个本地时间(可以想象承人平时生活中读时的字符串)，但是这个类里面没有时区信息。
 * 所以当他要转换为一个确定时刻的时候，需要添加ZoneOffset信息，知道这个时间在哪个时区后，才能把这个时间放到一个时间线上，才能得到确切得时刻Instant(保存了从UTC原时到当前时刻的时间线计数),从而得到时间戳
 * 比如2022-02-01 11:00:00，放到ZoneOffset=+8.则代表中国的2022-02-01 11:00:00，放到0，则代表UTC的2022-02-01 11:00:00
 * 所以当获取LocalDateTime的时间戳的时候，需要先把他放到一个时区(一个确定的时刻)才能得到时间戳，前提是你要知道这个localDateTime当前所处的环境是在哪个时区偏移上
 * LocalDateTime.now 以当前默认时区信息获取一个本地时间出来，比如now=2022-01-01 10:00:00
 * now.toEpochSecond(ZoneOffset.UTC); 把这个时间放到UTC上,也就是UTC的2022-01-01 10:00:00的时间，再去获取时间戳(相对1970xxx原时区的偏移long值)
 * now.toEpochSecond(ZoneOffset.of("+8")); 把这个时间放到+8区上,也就是+8的2022-01-01 10:00:00的时间戳，再去获取时间戳(相对1970xxx原时区的偏移long值)
 * 所以当LocalDateTime转时间戳的时候一定要知道当前语境下的时区，再去获取时间戳，不然可能得到错误的结果，毕竟localDateTime本身是去除了时区信息的
 *  错误的例子：假如localdatetime=2022-01-01 10：00：00
 *  LocalDateTime newLocalDate = ZonedDateTime.of(dateTime,UTCZoneId).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
 *  这句话的意思是把2022-01-01 10：00：00 作为一个UTC时间，然后转为了当前系统默认时区(假设+8)，再变为localDatetime，这个时候localDatetime保存的日历(字符串)其实变为了2022-01-01 18：00：00
 *  这个时候你要取时间戳，newLocalDate.toEpochSecond(ZoneOffset.UTC)得到得值是错误的，这个得到的是UTC的2022-01-01 18：00：00的时间戳，快了8小时
 *  正确的取法应该是newLocalDate.toEpochSecond(ZoneOffset.systemDefault()),所以你应该知道本地时间你是在哪个语境中，然后配合相应的时区信息去获取一个真实时刻。
 *
 *  而时间戳是指一个确定时区了的确定时刻和UTC原时点之间得差值，把一个时间戳转换为一个时间，需要时区信息，等于把这个差值加回来变成一个timeline时刻，再放到后一个具体时区上显示。差值是不变的，但是不同时区，展示的日历文字不一样。
 *
 *  可以参考，LocalDateTime类注释，或者https://qa.1r1g.com/sf/ask/3248344291/
 *
 * LocalDateTime对象本身不存储时区信息-它只有日期和时间相关的字段:日,月,年; 小时,分钟,秒和纳秒.但该now方法使用时区或偏移量来获取这些字段的正确值.
 * 这是因为对"今天是星期几？"这一问题的回答.和"现在几点？" 并不像我们想象的那么简单.
 * 通常认为答案就像查看我们的日历/手机/任何内容并查看当前日期/时间一样简单.但技术上正确的答案是:"这取决于".
 * 这基本上取决于你所处的位置.此时,世界上每个地方都有自己的当地日期和时间.例如,在7月5 日,2017年:虽然它是在圣保罗14H(或14:00),这是下午6点在伦敦和下午5点在UTC,但在东京那是凌晨2点,第二天(7月6 日).
 * 世界上每个地区都有特定的规则来确定他们在历史中的当地时间,当然它会影响他们的当地日期.将国家/城市/地区映射到这些规则的概念是一个时区.
 * 这就是该now方法需要时区的原因.该ZoneId对象加载所有时区数据以检查该区域中的当前日期和时间,并相应地调整日/月/年/小时/分钟/秒/纳秒值.不接收参数(LocalDateTime.now())的版本将使用系统的默认时区,因此API总是使用一些时区.
 * 时区(或偏移量,例如ZoneOffset.UTC)用于获取日,月,年,小时,分钟,秒和纳秒的正确值,然后 - LocalDateTime对于不保留区域的任何其他类的情况 - 丢弃.
 * 因此,这个概念可能与您的想法略有不同.如果我做:
 *
 * // ZoneOffset.UTC is equivalent to ZoneId.of("UTC")
 * LocalDateTime date = LocalDateTime.now(ZoneOffset.UTC);
 * 这段代码的作用是:"以UTC表示当前日期和时间,只获取日期和时间字段,丢弃时区/偏移信息".
 *
 * 当我运行此代码时,UTC中的当前日期/时间是2017-09-25T12:15:43.570Z,因此LocalDateTime其值等于2017-09-25T12:15:43.570(没有任何时区信息,只有日期和时间字段).如果我now()不带参数调用,它将使用JVM默认时区(在我的情况下,它是America/Sao_Paulo),值将是2017-09-25T09:15:43.570.
 * 因此,使用a LocalDateTime可以获取值,但是您无法知道这些值来自哪个时区,因为它不会保留此信息.
 * 如果您需要UTC日期,则必须使用其他类,以保留此信息:
 *
 * Instant.now() - 这将始终获得当前的UTC时刻
 * OffsetDateTime.now(ZoneOffset.UTC)- 使用此功能,您可以查询日期和时间字段(例如getDayOfMonth()或getHour())
 * ZonedDateTime.now(ZoneOffset.UTC)- 对于UTC,它与之相同OffsetDateTime,但如果您使用不同的时区,则会处理所有时区特定数据,例如夏令时更改.
 * 要检查此类对象是否为UTC,一种方法是使用该getZone()方法:
 *
 * ZonedDateTime z = ZonedDateTime.now(ZoneOffset.UTC);
 * System.out.println(z.getZone().equals(ZoneOffset.UTC)); // true
 * 但是,如果你使用等价物ZoneId.of("UTC"),equals方法返回false.所以你也可以检查是否z.getZone().getId()等于Z或UTC.有OffsetDateTime,它是类似的:
 *
 * OffsetDateTime odt = OffsetDateTime.now(ZoneOffset.UTC);
 * System.out.println(odt.getOffset().equals(ZoneOffset.UTC)); // true
 * 随着Instant你不需要检查,因为它总是在UTC.
 * 您可以在Oracle的日期/时间教程中检查所有可用类型.
 * 二者ZonedDateTime并OffsetDateTime可以转换到一个LocalDateTime使用toLocalDateTime()方法:
 *
 * // dt will have the current date and time in UTC
 * LocalDateTime dt = ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
 *
 * // or
 * LocalDateTime dt = OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime();
 * 这样,dt变量将具有与UTC中当前日期/时间对应的所有日期和时间字段(日/月/年,小时/分钟/秒/纳秒).但它不会保留任何时区/偏移信息,因此LocalDateTime对象本身无法知道这些值来自哪个时区.
 */
public class CustomLocalDateTimeTransUtil {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static final DateTimeFormatter YEARMONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");


    /**
     *  通过原时区，指定时区，变换本地时间的日历显示
     *  LocalDateTime,LocalDate,LocalTime是不含时区信息的，所以你可以理解为一个无时区时间。
     *  所以要转其它时区，就要先知道原来这个localDatetime是哪个时区的，用zoneId构造ZonedDateTime,再转指定的zoneId
     * @param localDateTime
     * @param originZoneId
     * @param targetZoneId
     * @return
     */
    public static LocalDateTime convertLocalDateTime(LocalDateTime localDateTime, ZoneId originZoneId, ZoneId targetZoneId)

    {
        return  ZonedDateTime.of(localDateTime,originZoneId).withZoneSameInstant(targetZoneId).toLocalDateTime();
    }

    /**
     * 从String转化为LocalDateTime,String可以是时间戳，如果不是时间戳，则按strFormatter来进行转化
     * @param timeStr
     * @return
     * @throws IOException
     */
    public static LocalDateTime timeStampOrStrToLocalDateTime(TimeFormatTypeEnum timeFormatType, String timeStr,DateTimeFormatter strFormatter) {
        if(timeStr==null) return null;
        LocalDateTime res=null;
        //系统时间戳sec转为系统localDateTime
        if(timeFormatType==TimeFormatTypeEnum.TIMESTAMP_SEC && StringUtils.isNumeric(timeStr)){
            LocalDateTime systemLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.valueOf(timeStr)),ZoneId.systemDefault());
            return systemLocalDateTime;
        //系统时间戳milli转为系统localDateTime
        }else if(timeFormatType==TimeFormatTypeEnum.TIMESTAMP_MILLI && StringUtils.isNumeric(timeStr)){
            LocalDateTime systemLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(timeStr)),ZoneId.systemDefault());
            return systemLocalDateTime;
        //时间字符串yyyy-MM-dd HH:mm:ss转为系统localDateTime
        }else if(timeFormatType==TimeFormatTypeEnum.TIMESTR && strFormatter!=null){
            TimeZone localeTimeZone = LocaleContextHolder.getTimeZone();
            //如果有本地信息，把当前传的字符串先按local时区进行转localDateTime，再把时区变为系统时区
            //如果supportTimeZoneTrans配置false,这儿取出来会是默认时区
            if(localeTimeZone != null && !localeTimeZone.getID().equals(TimeZone.getDefault().getID())){
                //传来一个2001-10-10 20:00:00,先转localDateTime，然后认为该时间是用户时区，转为系统时区
                res = convertLocalDateTime(LocalDateTime.parse(timeStr, strFormatter), localeTimeZone.toZoneId(),
                        ZoneId.systemDefault());
            }else{
                //直接按当前时区用
                res = LocalDateTime.parse(timeStr, strFormatter);
            }
        }
        return res;
    }


    public static  String localDateTimeToStrOrTimeStamp(TimeFormatTypeEnum timeFormatType,LocalDateTime value,DateTimeFormatter strFormatter) {
        if(value==null) return null;
        String res=null;
        //序列化为时间戳秒
        if(timeFormatType==TimeFormatTypeEnum.TIMESTAMP_SEC){
            res = String.valueOf(value.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());

        //序列化为时间戳毫秒
        }else if(timeFormatType==TimeFormatTypeEnum.TIMESTAMP_MILLI){
            res = String.valueOf(value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        //格式化为字符串
        }else if(timeFormatType==TimeFormatTypeEnum.TIMESTR && strFormatter!=null){
            //如果有local时区信息，则把当前服务器系统所在时区转换为目标时区 e.g. zoneId=Europe/Paris
            //如果supportTimeZoneTrans配置false,这儿取出来会是默认时区
            TimeZone localeTimeZone = LocaleContextHolder.getTimeZone();
            //有本地信息，从系统时间往本地时间转化，再格式化字符串
            if(localeTimeZone != null && !localeTimeZone.getID().equals(TimeZone.getDefault().getID())){
                //把当前系统时区转用户时区，再转jsonStr
                res = convertLocalDateTime(value, ZoneId.systemDefault(),localeTimeZone.toZoneId())
                        .format(strFormatter);

            }else{
                res = value.format(strFormatter);
            }
        }
        return res;
    }

    /**
     * localdate 如果传入字符串只有年月日，进行时区转换的时候默认是当天0点，这样转系统时区后年月日会发生变化，你再拿去做查询的话，可能精度就跟之前不一样了
     * 所以建议在国际化场景中，所有的接收都用localDateTime，不要用localeDate和localeTime，主要是时间精度问题
     * 所以目前转换只支持字符串，不支持时间戳，字符串形式直接按当前本地时区做转换
     * */
    public static LocalDate timeStampOrStrTolocalDate(TimeFormatTypeEnum timeFormatType, String timeStr,DateTimeFormatter strFormatter) {
        if(timeStr==null) return null;
        LocalDate res=null;
//        //系统时间戳sec转为系统localDateTime
//        if(timeFormatType==TimeFormatTypeEnum.TIMESTAMP_UTC_SEC){
//            if(StringUtils.isNumeric(timeStr)){
//                LocalDateTime systemLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.valueOf(timeStr)),ZoneId.systemDefault());
//                return systemLocalDateTime.toLocalDate();
//            }
//        //系统时间戳milli转为系统localDateTime
//        }else if(timeFormatType==TimeFormatTypeEnum.TIMESTAMP_UTC_MILLI){
//            if(StringUtils.isNumeric(timeStr)){
//                LocalDateTime systemLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(timeStr)),ZoneId.systemDefault());
//                res =  systemLocalDateTime.toLocalDate();
//            }
        //时间字符串yyyy-MM-dd HH:mm:ss转为系统localDateTime
//        }else if(timeFormatType==TimeFormatTypeEnum.TIMESTR && strFormatter!=null) {
            TimeZone localeTimeZone = LocaleContextHolder.getTimeZone();
            //如果有本地信息，把当前传的字符串先按local时区进行转localDateTime，再把时区变为系统时区
            //TODO 这儿会有歧义，比如请求日本时间2023-03-07 会被转换为北京时间2023-03-06，如果把这个作为查询范围，实际上用户是想查的时间不是北京时间2023-03-06
//            if (localeTimeZone != null) {
//                res = convertLocalDateTime(LocalDate.parse(timeStr, strFormatter).atStartOfDay(), localeTimeZone.toZoneId(),
//                        ZoneId.systemDefault()).toLocalDate();
//            } else {
                res =  LocalDate.parse(timeStr, strFormatter);
//            }
//        }

        //因为精度问题,localDate不做转换,直接按原字符串返序列化为本地对象
        return res;
    }


    public static String localDateToStrOrTimeStamp(TimeFormatTypeEnum timeFormatType,LocalDate value,DateTimeFormatter strFormatter) {
        if(value==null) return null;
        String res=null;
//        //序列化为UTC时间戳秒
//        if(timeFormatType==TimeFormatTypeEnum.TIMESTAMP_UTC_SEC){
//            res = String.valueOf(value.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
//        //序列化为UTC时间戳毫秒
//        }else if(timeFormatType==TimeFormatTypeEnum.TIMESTAMP_UTC_MILLI){
//            res = String.valueOf(value.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
//        //格式化为字符串
//        }else if(timeFormatType==TimeFormatTypeEnum.TIMESTR && strFormatter!=null){
//            //如果有local时区信息，则把当前服务器系统所在时区转换为目标时区 e.g. zoneId=Europe/Paris
//            TimeZone localeTimeZone = LocaleContextHolder.getTimeZone();
//            //有本地信息，从系统时间往本地时间转化，再格式化字符串
//            if(localeTimeZone != null){
//                //时间按时区转换后,进行formatter字符串格式化
//                res = convertLocalDateTime(value.atStartOfDay(), ZoneId.systemDefault(),localeTimeZone.toZoneId()).toLocalDate()
//                        .format(strFormatter);
//            //没有本地信息，采用默认系统时区
//            }else{
                //因为精度问题,localDate不做转换,直接按原字符串返序列化为本地对象
                res = value.format(strFormatter);
//            }
//        }
        return res;
    }


    public static LocalTime strToLocalTime(TimeFormatTypeEnum timeFormatType,String timeStr,DateTimeFormatter strFormatter) {
        if(timeStr==null) return null;
        LocalTime res= null;

//        //系统时间戳sec转为系统localDateTime
//        if(timeFormatType==TimeFormatTypeEnum.TIMESTAMP_UTC_SEC){
//            if(StringUtils.isNumeric(timeStr)){
//                LocalDateTime systemLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.valueOf(timeStr)),ZoneId.systemDefault());
//                return systemLocalDateTime.toLocalTime();
//            }
//            //系统时间戳milli转为系统localDateTime
//        }else if(timeFormatType==TimeFormatTypeEnum.TIMESTAMP_UTC_MILLI){
//            if(StringUtils.isNumeric(timeStr)){
//                LocalDateTime systemLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(timeStr)),ZoneId.systemDefault());
//                res =  systemLocalDateTime.toLocalTime();
//            }
//        //时间字符串HH:mm:ss转为系统localTime
//        }else if(timeFormatType==TimeFormatTypeEnum.TIMESTR && strFormatter!=null){
//            TimeZone localeTimeZone = LocaleContextHolder.getTimeZone();
//            //如果有本地信息，把当前传的字符串先按local时区进行转localDateTime，再把时区变为系统时区
//            if(localeTimeZone != null){
//                res = convertLocalDateTime(LocalTime.parse(timeStr, strFormatter).atDate(LocalDate.now(localeTimeZone.toZoneId()))
//                        ,localeTimeZone.toZoneId()
//                        ,ZoneId.systemDefault())
//                        .toLocalTime();
//            }else{
                //如果timeStr传入格式不对,让她自己报错
                res = LocalTime.parse(timeStr, strFormatter);
//            }
//        }
        return res;
    }


    /**
     * LocalTime转换为时间戳默认按当天年月日来转
     * @param value
     * @param strFormatter
     * @return
     */
    public static String localTimeToStr(TimeFormatTypeEnum timeFormatType, LocalTime value, DateTimeFormatter strFormatter) {
        if(value==null) return null;
        String res = null;

        //系统时间戳sec转为系统localTime
//        if (timeFormatType == TimeFormatTypeEnum.TIMESTAMP_UTC_SEC) {
//            res = String.valueOf(value.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
//            //系统时间戳milli转为系统localDateTime
//        }else if (timeFormatType == TimeFormatTypeEnum.TIMESTAMP_UTC_MILLI) {
//            res = String.valueOf(value.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
//        }else if(timeFormatType == TimeFormatTypeEnum.TIMESTR && strFormatter!=null){
//            //如果有local时区信息，则把当前服务器系统所在时区转换为目标时区 e.g. zoneId=Europe/Paris
//            TimeZone localeTimeZone = LocaleContextHolder.getTimeZone();
//            //有本地信息，从系统时间往本地时间转化，再格式化字符串
//            if (localeTimeZone != null) {
//                res = convertLocalDateTime(value.atDate(LocalDate.now()), ZoneId.systemDefault(), localeTimeZone.toZoneId()).format(strFormatter);
//                //没有本地信息，采用默认系统时区
//            } else {
                //因为精度问题,localDate不做转换,直接按原字符串返序列化为本地对象
                res = value.format(strFormatter);
//            }
//        }
        return res;
    }

}
