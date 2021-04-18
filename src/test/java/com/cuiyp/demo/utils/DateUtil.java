/**
 * @(#)DateUtil 2018/12/10 13:40 writAssemble Copyright 2018
 * Thuisoft, Inc. All rights reserved. THUNISOFT PROPRIETARY/CONFIDENTIAL. Use
 * is subject to license terms.
 */
package com.cuiyp.demo.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DateUtil.
 * 类职责描述：Date工具类.
 *
 * @author wangning on 2018/12/10 13:40.
 * @version 2.0.0.
 * @since jdk 1.8.
 */
public final class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    private static final String DATE_CN = "yyyy年MM月dd日";

    private static String dateString = "yyyy-MM-dd";
    private static String timeString = "HH:mm:ss";
    private static String dateTimeString = "yyyy-MM-dd HH:mm:ss";
    private static String dateTimeStringCn = "yyyy年MM月dd日 HH:mm:ss";
    private static String dateTimeHMStringCn = "yyyy年MM月dd日 HH时mm分";
    private static String dateDirString = "yyyy/MM/dd";

    private DateUtil() { throw new IllegalStateException("DateUtil class"); }

    /**
     * 获得当前日期
     *
     * @return a {@link java.util.Date} object.
     */
    public static Date curDate() {
        return new Date();
    }

    /**
     * 获得当前日期字符串: yyyy-MM-dd
     *
     * @return a {@link java.lang.String} object.
     */
    public static String curDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(dateString);
        return sdf.format(new Date());
    }

    /**
     * 根据毫秒数timeMills 转化为 HH:mm:ss
     * @param timeMills 毫秒数
     */
    public static String formatTimeMillsToTimeString(String timeMills) {
        if(NumberUtils.isNumber(timeMills)){
            Date date = new Date(Long.valueOf(timeMills));
            return DateFormatUtils.format(date, timeString);
        }else{
            return timeMills;
        }
    }

    /**
     * 根据毫秒数timeMills 转化为 yyyy年MM月dd日
     * @param timeMills 毫秒数
     */
    public static String formatTimeMillsToString(String timeMills) {
        if(NumberUtils.isNumber(timeMills)){
            Date date = new Date(Long.valueOf(timeMills));
            return DateFormatUtils.format(date, DATE_CN);
        }else{
            return timeMills;
        }
    }
    /**
     * 根据毫秒数timeMills 转化为 yyyy年MM月dd日 HH:mm:ss
     * @param timeMills 毫秒数
     */
    public static String formatTimeMillsToDateTimeString(String timeMills) {
        if(NumberUtils.isNumber(timeMills)){
            Date date = new Date(Long.valueOf(timeMills));
            return DateFormatUtils.format(date, dateTimeStringCn);
        }else{
            return timeMills;
        }
    }
    /**
     * 根据毫秒数timeMills 转化为 yyyy年MM月dd日 HH时mm分
     * @param timeMills 毫秒数
     */
    public static String formatTimeMillsTodateTimeHMString(String timeMills) {
        if(NumberUtils.isNumber(timeMills)){
            Date date = new Date(Long.valueOf(timeMills));
            return DateFormatUtils.format(date, dateTimeHMStringCn);
        }else{
            return timeMills;
        }
    }

    /**
     * 获得当前日期字符串：yyyy-MM-dd HH:mm:ss
     *
     * @return a {@link java.lang.String} object.
     */
    public static String curDateTimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(dateTimeString);
        return sdf.format(new Date());
    }

    /**
     * 格式化日期
     *
     * @param date a {@link java.util.Date} object.
     * @return a {@link java.lang.String} object.
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateString);
        return sdf.format(date);
    }

    /**
     * 格式化日期
     *
     * @param date a {@link java.util.Date} object.
     * @return a {@link java.lang.String} object.
     */
    public static String formatDateCN(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_CN);
        return sdf.format(date);
    }

    /**
     * 格式化时间
     *
     * @param date a {@link java.util.Date} object.
     * @return a {@link java.lang.String} object.
     */
    public static String formatTime(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(timeString);
        return sdf.format(date);
    }

    /**
     * 格式化日期时间
     *
     * @param date a {@link java.util.Date} object.
     * @return a {@link java.lang.String} object.
     */
    public static String formatDateTime(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateTimeString);
        return sdf.format(date);
    }

    /**
     * 字符串转换为日期
     *
     * @param dateStr a {@link java.lang.String} object.
     * @return a {@link java.util.Date} object.
     */
    public static Date parseDate(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateString);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * <p>parseDateTime.</p>
     *
     * @param datetimeStr a {@link java.lang.String} object.
     * @return a {@link java.util.Date} object.
     */
    public static Date parseDateTime(String datetimeStr) {
        if (StringUtils.isEmpty(datetimeStr)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateTimeString);
            return sdf.parse(datetimeStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * <p>formatDateDir.</p>
     *
     * @param date a {@link java.util.Date} object.
     * @return a {@link java.lang.String} object.
     */
    public static String formatDateDir(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateDirString);
        return sdf.format(date);
    }

    /**
     * 根据key解析数据类型
     *
     * @param key a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     * @return a {@link java.lang.Object} object.
     */
    public static Object format(String key,String value){
        if (StringUtils.isBlank(value)) {
            return value;
        }

        Object result = value;
        if (key.startsWith("N")) {
            try{
                result = Integer.valueOf(value);
            }catch(Exception e){
                logger.error("字符串转换integer失败", e);
            }
        }else if (key.startsWith("D")) {
            Date tmp = formatDate(value);
            if (tmp!=null) {
                result = tmp;
            }
        }
        return result;
    }

    /**
     * 格式化日期
     *
     * @param value a {@link java.lang.String} object.
     * @return a {@link java.util.Date} object.
     */
    @SuppressWarnings("serial")
    public static Date formatDate(String value){
        if(value == null){
            return null;
        }

        List<String> formats = new ArrayList<>();
        formats.add(dateTimeString);
        formats.add(dateString);
        formats.add("yyyy年M月d日");
        formats.add(DATE_CN);
        formats.add("yyyyMMdd");
        for(String formatText : formats){
            try {
                SimpleDateFormat format = new SimpleDateFormat(formatText);
                return format.parse(value);
            }catch(Exception e2){
                logger.error("转化类型错误：{}", e2.getMessage());
            }
        }
        return null;
    }
}

    
