package com.zhengqing.common.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 时间工具类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/18 0018 12:57
 */
@Slf4j
public class MyDateUtil {

    public static final String GMT = "GMT+8";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_START_FORMAT = "yyyy-MM-dd 00:00:00";
    public static final String DATE_TIME_END_FORMAT = "yyyy-MM-dd 23:59:59";
    public static final String MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String HOUR_FORMAT = "yyyy-MM-dd HH";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String MONTH_FORMAT = "yyyy-MM";
    public static final String YEAR_FORMAT = "yyyy";
    public static final String MINUTE_ONLY_FORMAT = "mm";
    public static final String HOUR_ONLY_FORMAT = "HH";
    public static final String MINUTE_JAVA_AUTHOR_FORMAT = "yyyy/MM/dd HH:mm";

    /**
     * 获取当前时间的字符串格式时间
     *
     * @return 字符串格式时间
     * @author zhengqingya
     * @date 2020/8/22 13:07
     */
    public static String nowStr() {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date());
    }

    /**
     * 获取当前时间字符串的指定格式时间
     *
     * @param format 时间格式
     * @return 字符串格式时间
     * @author zhengqingya
     * @date 2020/8/22 13:07
     */
    public static String nowStr(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * Date转Str
     *
     * @param date   时间
     * @param format 时间格式
     * @return 字符串时间类型
     * @author zhengqingya
     * @date 2020/8/22 13:07
     */
    public static String dateToStr(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * Date转Str {@link MyDateUtil.DATE_TIME_FORMAT}
     *
     * @param date 时间
     * @return 字符串时间类型
     * @author zhengqingya
     * @date 2020/8/22 13:07
     */
    public static String dateToStr(Date date) {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(date);
    }

    /**
     * Date 时间开始处理 (ex: Sat Sep 04 10:11:25 CST 2021 -> 2021-09-04 00:00:00)
     *
     * @param date 时间
     * @return 时间
     * @author zhengqingya
     * @date 2021/9/4 10:12
     */
    @SneakyThrows(Exception.class)
    public static Date dateToStartTime(Date date) {
        String dateTime = dateToStr(date, DATE_TIME_START_FORMAT);
        return strToDate(dateTime, DATE_TIME_FORMAT);
    }

    /**
     * Date 时间结束处理 (ex: Sat Sep 04 10:11:25 CST 2021 -> 2021-09-04 23:59:59)
     *
     * @param date 时间
     * @return 时间
     * @author zhengqingya
     * @date 2021/9/4 10:12
     */
    @SneakyThrows(Exception.class)
    public static Date dateToEndTime(Date date) {
        String dateTime = dateToStr(date, DATE_TIME_END_FORMAT);
        return strToDate(dateTime, DATE_TIME_FORMAT);
    }

    /**
     * Str(yyyy-MM-dd HH:mm:ss)转DateTime
     *
     * @param dateStr 字符串时间
     * @return Date时间类型
     * @author zhengqingya
     * @date 2021/8/20 9:41
     */
    @SneakyThrows(Exception.class)
    public static Date strToDateTime(String dateStr) {
        return new SimpleDateFormat(DATE_TIME_FORMAT).parse(dateStr);
    }

    /**
     * Str转Date
     *
     * @param dateStr 字符串时间
     * @param format  时间格式
     * @return Date时间类型
     * @author zhengqingya
     * @date 2020/8/22 13:07
     */
    @SneakyThrows(Exception.class)
    public static Date strToDate(String dateStr, String format) {
        return new SimpleDateFormat(format).parse(dateStr);
    }

    /**
     * Str转long(10位-秒级别)
     *
     * @param dateStr 字符串时间
     * @param format  时间格式
     * @return 秒
     * @author zhengqingya
     * @date 2020/8/22 13:07
     */
    @SneakyThrows(Exception.class)
    public static long strToLong(String dateStr, String format) {
        return new SimpleDateFormat(format).parse(dateStr).getTime() / 1000;
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 判断结束时间是否大于等于开始时间
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param format    时间格式
     * @return 判断结果
     * @author zhengqingya
     * @date 2020/8/22 13:21
     */
    public static boolean verifyTime(String startTime, String endTime, String format) {
        Date startDate = strToDate(startTime, format);
        Date endDate = strToDate(endTime, format);
        return endDate.getTime() >= startDate.getTime();
    }

    /**
     * 获取今日开始时间
     *
     * @return 今日开始时间
     * @author zhengqingya
     * @date 2021/8/18 18:58
     */
    public static Date todayStartTime() {
        LocalDateTime todayStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return Date.from(todayStartTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取今日开始时间
     *
     * @return 今日开始时间
     * @author zhengqingya
     * @date 2021/8/18 18:58
     */
    public static String todayStartTimeStr() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_TIME_START_FORMAT);
        LocalDateTime time = LocalDateTime.now();
        return df.format(time);
    }

    /**
     * 获取今日结束时间
     *
     * @return 今日结束时间
     * @author zhengqingya
     * @date 2021/8/18 18:58
     */
    public static Date todayEndTime() {
        LocalDateTime todayStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return Date.from(todayStartTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取今日结束时间
     *
     * @return 今日结束时间
     * @author zhengqingya
     * @date 2021/8/18 18:58
     */
    public static String todayEndTimeStr() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_TIME_END_FORMAT);
        LocalDateTime time = LocalDateTime.now();
        return df.format(time);
    }

    /**
     * 计算两时间分钟差
     *
     * @param startTimeStr 开始时间 ex:2020-09-09 10:00:10
     * @param endTimeStr   结束时间 ex:2020-09-09 10:30:10
     * @return 分钟差 ex:30
     * @author zhengqingya
     * @date 2021/9/9 10:21
     */
    @SneakyThrows(Exception.class)
    public static int diffMinute(String startTimeStr, String endTimeStr) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(MINUTE_FORMAT);
        long startTime = simpleFormat.parse(startTimeStr).getTime();
        long endTime = simpleFormat.parse(endTimeStr).getTime();
        return (int) ((endTime - startTime) / (1000 * 60));
    }

    /**
     * 在当前时间上加指定时间
     *
     * @param timeUnit 时间单位
     * @param time     时间
     * @return 结果
     * @author zhengqingya
     * @date 2021/10/18 16:57
     */
    @SneakyThrows(Exception.class)
    public static Date addTime(TimeUnit timeUnit, int time) {
        return addAndsubTime(timeUnit, +time);
    }

    /**
     * 在当前时间上减指定时间
     *
     * @param timeUnit 时间单位
     * @param time     时间
     * @return 结果
     * @author zhengqingya
     * @date 2021/10/18 16:57
     */
    @SneakyThrows(Exception.class)
    public static Date subTime(TimeUnit timeUnit, int time) {
        return addAndsubTime(timeUnit, -time);
    }

    /**
     * 在当前时间上 加或减 指定时间
     *
     * @param timeUnit 时间单位
     * @param time     时间
     * @return 结果
     * @author zhengqingya
     * @date 2021/10/18 16:57
     */
    @SneakyThrows(Exception.class)
    public static Date addAndsubTime(TimeUnit timeUnit, int time) {
        Calendar nowTime = Calendar.getInstance();
        switch (timeUnit) {
            case SECONDS:
                nowTime.add(Calendar.SECOND, time);
                break;
            case MINUTES:
                nowTime.add(Calendar.MINUTE, time);
                break;
            case HOURS:
                nowTime.add(Calendar.HOUR, time);
                break;
            case DAYS:
                nowTime.add(Calendar.DATE, time);
                break;
            default:
                throw new Exception("暂不支持该时间类型！");
        }
        return nowTime.getTime();
    }

    public static void main(String[] args) {
        Date nowDateTime = new Date();
        String dateStr = dateToStr(nowDateTime, DATE_TIME_FORMAT);
        System.out.println(nowStr(YEAR_FORMAT));
        boolean result = verifyTime("2020-08-18 00:00:00", "2020-08-17 23:59:59", DATE_TIME_FORMAT);
        System.out.println(result);

        Date todayStartTime = todayStartTime();
        Date todayEndTime = todayEndTime();

        String todayStartTimeStr = todayStartTimeStr();
        String todayEndTimeStr = todayEndTimeStr();

        Date date = strToDateTime("2020-08-18 00:00:10");

        Long aLong = strToLong("2020-08-18 00:00:10", DATE_TIME_FORMAT);

        Date dateTimeStartFormat = dateToStartTime(nowDateTime);
        Date dateTimeEndFormat = dateToEndTime(nowDateTime);

        int diffMinute = diffMinute("2020-09-09 10:00:10", "2020-09-09 10:30:10");

        log.info("nowTime:{} addTime: {}", nowStr(), dateToStr(addTime(TimeUnit.SECONDS, 20), DATE_TIME_FORMAT));
        log.info("nowTime:{} addTime: {}", nowStr(), dateToStr(addTime(TimeUnit.MINUTES, 10), DATE_TIME_FORMAT));
        log.info("nowTime:{} addTime: {}", nowStr(), dateToStr(addTime(TimeUnit.HOURS, 10), DATE_TIME_FORMAT));
        log.info("nowTime:{} addTime: {}", nowStr(), dateToStr(addTime(TimeUnit.DAYS, 10), DATE_TIME_FORMAT));

        log.info("--------------------------------");

        log.info("nowTime:{} subTime: {}", nowStr(), dateToStr(subTime(TimeUnit.SECONDS, 20), DATE_TIME_FORMAT));
        log.info("nowTime:{} subTime: {}", nowStr(), dateToStr(subTime(TimeUnit.MINUTES, 10), DATE_TIME_FORMAT));
        log.info("nowTime:{} subTime: {}", nowStr(), dateToStr(subTime(TimeUnit.HOURS, 10), DATE_TIME_FORMAT));
        log.info("nowTime:{} subTime: {}", nowStr(), dateToStr(subTime(TimeUnit.DAYS, 10), DATE_TIME_FORMAT));

        log.info("--------------------------------");
    }

}
