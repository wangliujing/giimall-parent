package com.giimall.common.util;

import com.giimall.common.constant.DateFormatConstant;
import com.giimall.common.exception.CommonException;
import com.giimall.common.util.spring.HttpServletUtil;
import com.giimall.common.util.spring.SpringContextHelper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 日期工具类
 *
 * @author wangLiuJing
 * Created on 2020/1/16
 */
public class DateUtil extends DateUtils {

    public static final String NOW_DATE = "NOW_DATE";

    /**
     * 获取默认时区
     *
     * @return the defaultTimeZone (type String) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/24
     */
    public static String getDefaultTimeZone() {
        return TimeZone.getDefault().toZoneId().getId();
    }


    /**
     * 将java.util.Date转化成Timestamp
     *
     * @param date of type Date
     * @return Date
     * @author zhanghao
     * Created on 2020/6/9
     */
    public static Timestamp parseDateToTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }


    /**
     * 日期对象转换
     *
     * @param date of type Date
     * @return Date
     * @author wangLiuJing
     * Created on 2021/11/1
     */
    public static java.sql.Date toSqlDate(Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

    /**
     * 日期对象转换
     *
     * @param date of type Date
     * @return Time
     * @author wangLiuJing
     * Created on 2021/11/1
     */
    public static java.sql.Time toSqlTime(Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Time(date.getTime());
    }

    /**
     * 日期对象转换
     *
     * @param date of type Date
     * @return Timestamp
     * @author wangLiuJing
     * Created on 2021/11/1
     */
    public static Timestamp toSqlTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    /**
     * 判断两个时间是否是同一天
     *
     * @param date      of type Date
     * @param otherDate of type Date
     * @return boolean
     * @author wangLiuJing
     * Created on 2020/5/23
     */
    public static boolean isTheSameDay(Date date, Date otherDate) {
        if (parseDateToStringCustom(date, DateFormatConstant.DATE)
                .equals(parseDateToStringCustom(otherDate, DateFormatConstant.DATE))) {
            return true;
        }
        return false;
    }


    /**
     * 日期转换字符串
     *
     * @param date of type Date
     * @return String
     * @author wangLiuJing
     * Created on 2020/1/16
     */
    public static String parseDateToNormalString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormatConstant.NORMAL);
        return simpleDateFormat.format(date);
    }


    /**
     * 日期转换字符串
     *
     * @param originalString       of type String
     * @param originalStringFormat of type String
     * @return String
     * @author zhangyujie
     * Created on 2020/1/16
     */
    @SneakyThrows
    public static String parseStringToCustomString(String originalString, String originalStringFormat,
                                                   String targetStringFormat) {
        Date time = new SimpleDateFormat(originalStringFormat).parse(originalString);
        return new SimpleDateFormat(targetStringFormat).format(time);
    }


    public static String parseDateToDateString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormatConstant.DATE);
        return simpleDateFormat.format(date);
    }

    /**
     * 将日期格式化为字符串形式精确到毫秒
     *
     * @param date
     * @return
     */
    public static String parseDateToTimeString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormatConstant.TIME);
        return simpleDateFormat.format(date);
    }


    /**
     * 将日期格式化为字符串形式
     *
     * @param date   日期
     * @param format 自定义格式
     * @return
     */
    public static String parseDateToStringCustom(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 将日期格式化为字符串形式
     *
     * @param date   日期
     * @param format 自定义格式
     * @param timeZone 时区
     * @return
     */
    public static String parseDateToStringCustom(Date date, String format, TimeZone timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(date);
    }

    /**
     * 字符串转日期
     *
     * @param time   of type String
     * @param format of type String
     * @param timeZone 时区
     * @return Date
     * @author wangLiuJing
     * Created on 2020/1/16
     */
    @SneakyThrows
    public static Date parseStringToDateCustom(String time, String format, TimeZone timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.parse(time);
    }

    /**
     * 字符串转日期
     *
     * @param time   of type String
     * @param format of type String
     * @return Date
     * @author wangLiuJing
     * Created on 2020/1/16
     */
    @SneakyThrows
    public static Date parseStringToDateCustom(String time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(time);
    }

    /**
     * 获取时间格式
     *
     * @param time of type String
     * @return String
     * @author wangLiuJing
     * Created on 2020/5/10
     */
    public static String getTimeFormat(String time) {
        if (StringUtil.isBlank(time)) {
            return null;
        }

        if (time.matches(DateFormatConstant.NORMAL_REX)) {
            return DateFormatConstant.NORMAL;
        }

        if (time.matches(DateFormatConstant.NORMAL_WITH_MILLISECONDS_REX)) {
            return DateFormatConstant.NORMAL_WITH_MILLISECONDS;
        }

        if (time.matches(DateFormatConstant.ISO_NORMAL_REX)) {
            return DateFormatConstant.ISO_NORMAL;
        }

        if (time.matches(DateFormatConstant.ISO_NORMAL_WITH_MILLISECONDS_REX)) {
            return DateFormatConstant.ISO_NORMAL_WITH_MILLISECONDS;
        }

        if (time.matches(DateFormatConstant.DATE_REX)) {
            return DateFormatConstant.DATE;
        }

        if (time.matches(DateFormatConstant.TIME_REX)) {
            return DateFormatConstant.TIME;
        }

        if (time.matches(DateFormatConstant.TIME_WITH_MILLISECONDS_REX)) {
            return DateFormatConstant.TIME_WITH_MILLISECONDS;
        }
        throw new CommonException("时间格式有误： " + time);
    }

    /**
     * 格式化字符串为时间类型，该方法会自动判断字符串的时间格式
     *
     * @param time of type String
     * @return Date
     * @author wangLiuJing
     * Created on 2020/5/10
     */
    public static Date autoParseStringToDate(String time) {
        if (StringUtil.isBlank(time)) {
            return null;
        }

        if (time.matches(DateFormatConstant.NORMAL_REX)) {
            return parseStringToDateCustom(time, DateFormatConstant.NORMAL);
        }

        if (time.matches(DateFormatConstant.NORMAL_WITH_MILLISECONDS_REX)) {
            return parseStringToDateCustom(time, DateFormatConstant.NORMAL_WITH_MILLISECONDS);
        }

        if (time.matches(DateFormatConstant.ISO_NORMAL_REX)) {
            return parseStringToDateCustom(time, DateFormatConstant.ISO_NORMAL);
        }

        if (time.matches(DateFormatConstant.ISO_NORMAL_WITH_MILLISECONDS_REX)) {
            return parseStringToDateCustom(time, DateFormatConstant.ISO_NORMAL_WITH_MILLISECONDS);
        }


        if (time.matches(DateFormatConstant.DATE_REX)) {
            return parseStringToDateCustom(time, DateFormatConstant.DATE);
        }

        if (time.matches(DateFormatConstant.TIME_REX)) {
            return parseStringToDateCustom(time, DateFormatConstant.TIME);
        }

        if (time.matches(DateFormatConstant.TIME_WITH_MILLISECONDS_REX)) {
            return parseStringToDateCustom(time, DateFormatConstant.TIME_WITH_MILLISECONDS);
        }
        throw new CommonException("时间格式有误： " + time);
    }

    /**
     * desc:得到当前是一个月的那一天
     *
     * @return Feb 18, 2009
     */
    public static int getNowDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * desc:取得当前小时的整数，24小时制
     *
     * @return Feb 18, 2009
     */
    public static int getNowHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * desc:得到当前时间的分钟数
     *
     * @return Feb 18, 2009
     */
    public static int getNowMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);

    }

    /**
     * desc:得到当前的月份
     *
     * @return Feb 18, 2009
     */
    public static int getNowMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * desc:得到当前的年份
     *
     * @return Feb 18, 2009
     */
    public static int getNowYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }


    /**
     * desc:得到当前的秒数
     *
     * @return Feb 18, 2009
     */
    public static int getNowSeconds() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    /**
     * 从1开始{"1","2","3","4","5","6","7"}
     * 获取目标时间的星期
     *
     * @param date
     * @return
     */
    public static int getWeekDay(Date date) {
        Calendar instance = Calendar.getInstance();
        if (date != null) {
            instance.setTime(date);
        }
        int dayOfweek = instance.get(Calendar.DAY_OF_WEEK);
        if (dayOfweek == 1) {
            return 7;
        } else {
            return dayOfweek - 1;
        }
    }

    /**
     * 获取目标时间的星期几，返回格式星期一
     *
     * @param date
     * @return format 是否返回指定格式 true为是
     */
    public static String getStringWeekDay(Date date, boolean format) {
        // 从1开始{"1","2","3","4","5","6","7"}
        int weekDay = getWeekDay(date);
        if (!format) {
            return StringUtil.valueOf(weekDay);
        }
        String[] weeks = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        return weeks[weekDay];
    }

    /**
     * desc:得到当前是星期几
     *
     * @return Feb 18, 2009
     */
    public static int getNowWeek() {
        int temp = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (temp == 1) {
            return 7;
        } else {
            return temp - 1;
        }
    }

    /**
     * 返回指定日期的前几天
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }


    /**
     * 毫秒转为x天x小时x分x秒
     *
     * @param mss
     * @return
     */
    public static String formatTimeMillis(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        StringBuffer stf = new StringBuffer();
        if (days >= 1L) {
            stf.append(String.valueOf(days)).append("天");
        }
        if (hours >= 1L) {
            stf.append(String.valueOf(hours)).append("小时");
        }
        if (minutes >= 1L) {
            stf.append(String.valueOf(minutes)).append("分");
        }
        if (seconds >= 1L) {
            stf.append(String.valueOf(seconds)).append("秒");
        }
        return stf.toString();

    }

    /**
     * 返回指定日期的后几年
     *
     * @param d
     * @param year
     * @return
     */
    public static Date getYearAfter(Date d, int year) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.YEAR, now.get(Calendar.YEAR) + year);
        return now.getTime();
    }

    /**
     * 返回指定日期的后几天
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 返回指定日期的后几月
     *
     * @param d
     * @param month
     * @return
     */
    public static Date getMonthAfter(Date d, int month) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);
        return now.getTime();
    }


    /**
     * 返回指定日期的后几分
     *
     * @param d
     * @param minute
     * @return
     */
    public static Date getMinAfter(Date d, int minute) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + minute);
        return now.getTime();
    }

    /**
     * 返回指定日期的后几秒
     *
     * @param d
     * @param second
     * @return
     */
    public static Date getSecAfter(Date d, int second) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.SECOND, now.get(Calendar.SECOND) + second);
        return now.getTime();
    }

    /**
     * 返回指定日期的前几秒
     *
     * @param d
     * @param second
     * @return
     */
    public static Date getSecBefore(Date d, int second) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.SECOND, now.get(Calendar.SECOND) - second);
        return now.getTime();
    }

    /**
     * 返回指定日期的前几分
     *
     * @param d
     * @param minute
     * @return
     */
    public static Date getMinBefore(Date d, int minute) {
        if (d == null) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) - minute);
        return now.getTime();
    }

    /**
     * 返回当前日期(e.g. 2018-12-05)
     *
     * @return
     */
    public static Date getDayOfToday() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    /**
     * 返回当前月份的第一天(e.g. 2018-12-01)
     *
     * @return
     */
    public static Date getFirstDayOfCurrMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        calendar.clear();
        calendar.set(year, month, 1);
        return calendar.getTime();
    }

    /**
     * 返回当前月份的第一天(e.g. 2018-12-01)
     *
     * @return
     * @author zhangyujie
     */
    public static Date getFirstDayOfCurrMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        calendar.clear();
        calendar.set(year, month, 1);
        return calendar.getTime();
    }


    /**
     * 返回当前年份的第一天(e.g. 2018-01-01)
     *
     * @return
     */
    public static Date getFirstDayOfCurrYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(year, 0, 1);
        return calendar.getTime();
    }

    /**
     * 根据时间返回年份的第一天(e.g. 2018-01-01)
     *
     * @return
     * @author zhangyujie
     */
    public static Date getFirstDayOfCurrYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(year, 0, 1);
        return calendar.getTime();
    }

    /**
     * 指定日期加减指定天时分秒
     *
     * @param date
     * @param format Calendar.DATE
     * @param count
     * @return
     */
    public static Date getCustomTime(Date date, int format, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(format, calendar.get(format) + count);
        return calendar.getTime();
    }

    /**
     * 获取当天的开始时间
     *
     * @return the dayBegin (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取指定日期当天的开始时间
     *
     * @return the dayBegin (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getDayBegin(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当天的结束时间
     *
     * @return the dayEnd (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 获取当天的结束时间
     *
     * @return the dayEnd (type Date) of this DateUtil object.
     * @author zhangyujie
     * Created on 2021/2/22
     */
    public static Date getDayEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 获取昨天的开始时间
     *
     * @return the beginDayOfYesterday (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getBeginDayOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取昨天的结束时间
     *
     * @return the endDayOfYesterDay (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getEndDayOfYesterDay() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取明天的开始时间
     *
     * @return the beginDayOfTomorrow (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getBeginDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    /**
     * 获取明天的结束时间
     *
     * @return the endDayOfTomorrow (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getEndDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取明天的结束时间
     *
     * @return the endDayOfTomorrow (type Date) of this DateUtil object.
     * @author zhangyujie
     * Created on 2021/2/22
     */
    public static Date getEndDayOfTomorrow(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd(date));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取本周的开始时间
     *
     * @return the beginDayOfWeek (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    /**
     * 根据时间获取本周的开始时间
     *
     * @return the beginDayOfWeek (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getBeginDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }


    /**
     * 获取本周的结束时间
     *
     * @return the endDayOfWeek (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    /**
     * 获取本周的结束时间
     *
     * @return the endDayOfWeek (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getEndDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek(date));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }


    /**
     * 获取本月的开始时间
     *
     * @return the beginDayOfMonth (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYears(), getNowMonths() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    /**
     * 获取本月的结束时间
     *
     * @return the endDayOfMonth (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYears(), getNowMonths() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYears(), getNowMonths() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    /**
     * 获取本月的结束时间
     *
     * @return the endDayOfMonth (type Date) of this DateUtil object.
     * @author zhangyujie
     * Created on 2021/2/22
     */
    public static Date getEndDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.setTime(getFirstDayOfCurrMonth(calendar.getTime()));
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 1);
        return calendar.getTime();
    }

    /**
     * 获取近一个月开始时间，过去则为负数
     *
     * @param i of type int
     * @return Date
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getBeginDayOfLastMonth(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, i);
        return calendar.getTime();
    }

    /**
     * 获取本年的开始时间
     *
     * @return the beginDayOfYear (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYears());
        // cal.set
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayStartTime(cal.getTime());
    }

    /**
     * 获取本年的结束时间
     *
     * @return the endDayOfYear (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYears());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    /**
     * 获取本年的结束时间
     *
     * @return the endDayOfYear (type Date) of this DateUtil object.
     * @author zhangyujie
     * Created on 2021/2/22
     */
    public static Date getEndDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    /**
     * 获取近几年的开始时间，过去则为负数
     *
     * @param i of type int
     * @return Date
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getEndDayOfLastYear(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, i);
        return calendar.getTime();
    }

    /**
     * 获取某个日期的开始时间
     *
     * @param d of type Date
     * @return Timestamp
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取某个日期的结束时间
     *
     * @param d of type Date
     * @return Timestamp
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取今年是哪一年
     *
     * @return the nowYears (type Integer) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static int getNowYears() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    /**
     * 获取本月是哪一月
     *
     * @return the nowMonths (type int) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static int getNowMonths() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    /**
     * 两个日期相减得到的天数
     *
     * @param beginDate of type Date
     * @param endDate   of type Date
     * @return int
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static int getDiffDays(Date beginDate, Date endDate) {

        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }

        long diff = (endDate.getTime() - beginDate.getTime())
                / (1000 * 60 * 60 * 24);

        int days = new Long(diff).intValue();

        return days;
    }

    /**
     * 两个日期相减得到的毫秒数
     *
     * @param beginDate of type Date
     * @param endDate   of type Date
     * @return long
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static long dateDiff(Date beginDate, Date endDate) {
        long date1ms = beginDate.getTime();
        long date2ms = endDate.getTime();
        return date2ms - date1ms;
    }

    /**
     * 获取两个日期中的最大日期
     *
     * @param beginDate of type Date
     * @param endDate   of type Date
     * @return Date
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date max(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return beginDate;
        }
        return endDate;
    }

    /**
     * 获取两个日期中的最小日期
     *
     * @param beginDate of type Date
     * @param endDate   of type Date
     * @return Date
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date min(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return endDate;
        }
        return beginDate;
    }

    /**
     * 返回某月该季度的第一个月
     *
     * @param date of type Date
     * @return Date
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getFirstSeasonDate(Date date) {
        final int[] SEASON = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = SEASON[cal.get(Calendar.MONTH)];
        cal.set(Calendar.MONTH, sean * 3 - 3);
        return cal.getTime();
    }

    /**
     * 返回某月该季度的第一个月
     *
     * @param date of type Date
     * @return Date
     * @author zhangyujie
     * Created on 2021/4/25
     */
    public static Date getFirstSeasonDayDate(Date date) {
        final int[] SEASON = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = SEASON[cal.get(Calendar.MONTH)];
        cal.set(Calendar.MONTH, sean * 3 - 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 返回某月该季度的最后一天
     *
     * @param date of type Date
     * @return Date
     * @author zhangyujie
     * Created on 2021/4/25
     */
    public static Date getEndDayOfQuarterly(Date date) {
        final int[] SEASON = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = SEASON[cal.get(Calendar.MONTH)];
        cal.set(Calendar.MONTH, sean * 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return getCustomTime(getFirstSeasonDayDate(cal.getTime()), Calendar.SECOND, -1);
    }


    /**
     * 返回某个日期下几天的日期
     *
     * @param date of type Date
     * @param i    of type int
     * @return Date
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getNextDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
        return cal.getTime();
    }

    /**
     * 返回某个日期前几天的日期
     *
     * @param date of type Date
     * @param i    of type int
     * @return Date
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static Date getFrontDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
        return cal.getTime();
    }

    /**
     * 获取某年某月到某年某月按天的切片日期集合（间隔天数的日期集合）
     *
     * @param beginYear  of type int
     * @param beginMonth of type int
     * @param endYear    of type int
     * @param endMonth   of type int
     * @param k          of type int
     * @return List<List < Date>>
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static List<List<Date>> getTimeList(int beginYear, int beginMonth, int endYear,
                                               int endMonth, int k) {
        List<List<Date>> list = new ArrayList<List<Date>>();
        if (beginYear == endYear) {
            for (int j = beginMonth; j <= endMonth; j++) {
                list.add(getTimeList(beginYear, j, k));

            }
        } else {
            {
                for (int j = beginMonth; j < 12; j++) {
                    list.add(getTimeList(beginYear, j, k));
                }

                for (int i = beginYear + 1; i < endYear; i++) {
                    for (int j = 0; j < 12; j++) {
                        list.add(getTimeList(i, j, k));
                    }
                }
                for (int j = 0; j <= endMonth; j++) {
                    list.add(getTimeList(endYear, j, k));
                }
            }
        }
        return list;
    }

    /**
     * 获取某年某月按天切片日期集合（某个月间隔多少天的日期集合）
     *
     * @param beginYear  of type int
     * @param beginMonth of type int
     * @param k          of type int
     * @return List<Date>
     * @author wangLiuJing
     * Created on 2021/2/22
     */
    public static List<Date> getTimeList(int beginYear, int beginMonth, int k) {
        List<Date> list = new ArrayList<Date>();
        Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
        int max = begincal.getActualMaximum(Calendar.DATE);
        for (int i = 1; i < max; i = i + k) {
            list.add(begincal.getTime());
            begincal.add(Calendar.DATE, k);
        }
        begincal = new GregorianCalendar(beginYear, beginMonth, max);
        list.add(begincal.getTime());
        return list;
    }


    /**
     * 改方法从request获取时间，request获取不到则创建时间放入renquest域
     *
     * @return the nowDate (type Date) of this DateUtil object.
     * @author wangLiuJing
     * Created on 2019/11/21
     */
    public static Date getNowDate() {
        HttpServletRequest httpServletRequest = HttpServletUtil.getHttpServletRequest();
        if (httpServletRequest == null) {
            return new Date();
        }
        Object attribute = httpServletRequest.getAttribute(NOW_DATE);
        if (attribute == null) {
            Date date = new Date();
            httpServletRequest.setAttribute(NOW_DATE, date);
            return date;
        }
        return (Date) attribute;
    }

    /**
     * 获取当前时间到指定时间的天数
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        if (date == null) {
            return -1;
        }
        Date nowDate = new Date();
        return (int) ((nowDate.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 返回用户输入的月份的第一天(e.g. 2019-04-23)
     *
     * @return
     */

    public static Date getFirstDayDateOfMonth(Date date, int num) {
        final Calendar cal = Calendar.getInstance();
        date = DateUtil.getMonthAfter(date, num);
        cal.setTime(date);
        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }


    /**
     * 根据身份证计算年龄
     *
     * @param idCard
     * @return
     */
    public static Integer idCardToAge(String idCard) {
        if (StringUtil.isBlank(idCard)) {
            return null;
        }
        String yyyyStr = idCard.substring(6, 10);
        SimpleDateFormat df = new SimpleDateFormat(DateFormatConstant.YEAR);
        int age = Integer.parseInt(df.format(new Date())) - Integer.parseInt(yyyyStr);
        // 是否已过了生日
        String mmddStr = idCard.substring(10, 14);
        SimpleDateFormat df2 = new SimpleDateFormat("MMdd");
        int u2 = Integer.parseInt(df2.format(new Date())) - Integer.parseInt(mmddStr);
        if (u2 < 0) {
            age = age - 1;
        }
        return age;
    }

    /**
     * 判断一个Date时间是否在某个Date时间范围内
     *
     * @param nowTime   of type Date
     * @param beginTime of type Date
     * @param endTime   of type Date
     * @return boolean
     * @author zhangyujie
     * Created on 2021/12/9
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else if (nowTime.compareTo(beginTime) == 0 || nowTime.compareTo(endTime) == 0) {
            return true;
        } else {
            return false;
        }
    }
}
