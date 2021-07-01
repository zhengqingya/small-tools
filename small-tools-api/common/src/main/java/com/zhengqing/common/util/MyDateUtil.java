package com.zhengqing.common.util;

import lombok.SneakyThrows;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 时间工具类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/8/18 0018 12:57
 */
public class MyDateUtil {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
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
     * @return: 字符串格式时间
     * @author zhengqingya
     * @date 2020/8/22 13:07
     */
    public static String nowStr() {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date());
    }

    /**
     * 获取当前时间字符串的指定格式时间
     *
     * @param format: 时间格式
     * @return: 字符串格式时间
     * @author zhengqingya
     * @date 2020/8/22 13:07
     */
    public static String nowStr(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * Date转Str
     *
     * @param date:   时间
     * @param format: 时间格式
     * @return: 字符串时间类型
     * @author zhengqingya
     * @date 2020/8/22 13:07
     */
    public static String dateToStr(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * Str转Date
     *
     * @param dateStr: 字符串时间
     * @param format:  时间格式
     * @return: Date时间类型
     * @author zhengqingya
     * @date 2020/8/22 13:07
     */
    @SneakyThrows
    public static Date strToDate(String dateStr, String format) {
        return new SimpleDateFormat(format).parse(dateStr);
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
     * @param startTime: 开始时间
     * @param endTime:   结束时间
     * @param format:    时间格式
     * @return: 判断结果
     * @author zhengqingya
     * @date 2020/8/22 13:21
     */
    public static boolean verifyTime(String startTime, String endTime, String format) {
        Date startDate = strToDate(startTime, format);
        Date endDate = strToDate(endTime, format);
        return endDate.getTime() >= startDate.getTime();
    }

    public static void main(String[] args) {
        String dateStr = dateToStr(new Date(), DATE_TIME_FORMAT);
        System.out.println(nowStr(YEAR_FORMAT));
        boolean result = verifyTime("2020-08-18 00:00:00", "2020-08-17 23:59:59", DATE_TIME_FORMAT);
        System.out.println(result);
    }

}
