package com.people.loveme.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {

    public final static long MS_OF_DAY = 24 * 3600 * 1000;
    public static final int MINUTE = 60 * 1000;
    public final static String DATE_FORMAT = "yyyy-MM-dd";
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_TIME_FORMAT2 = "yyyy/MM/dd HH:mm:ss";
    public final static String DATE_DAY_FORMAT = "yyyy-MM-dd HH:mm";
    public final static String DATE_FORMAT2 = "yyyy/MM/dd";

    private final static String[] weekCN = {
            "يەكشەنبە", "دۈشەنبە", "سەيشەنبە", "چارشەنبە", "پەيشەنبە", "جۈمە", "شەنبە",
    };

    public static String chineseWeekDayToday() {
        return chineseWeek(dayOfWeekToday());
    }

    public static String chineseWeekDay(String dateStr) {
        return chineseWeek(dayOfWeek(dateStr));
    }

    private static String chineseWeek(int dayOfWeek) {
        if (dayOfWeek < 0) {
            dayOfWeek = 0;
        } else if (dayOfWeek >= weekCN.length) {
            dayOfWeek = weekCN.length - 1;
        }
        return weekCN[dayOfWeek];
    }

    /**
     * @return [0, 6]:[周日,周六]
     */
    private static int dayOfWeekToday() {
        return dayOfWeek(new Date());
    }

    public static Date String2Data(long time){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(time);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    private static int dayOfWeek(String dateStr) {
        return dayOfWeek(dateFormString(dateStr));
    }

    private static int dayOfWeek(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static int countPassDay(final String date) {
        return countPassDay(dateFormString(date), Integer.MAX_VALUE);
    }

    public static int countFutureDay(final Date date, final int max){
        if ( date.getTime() - new Date().getTime() > max * MS_OF_DAY || date.getTime() - new Date().getTime() < 0) {
            return max;
        }
        final Calendar c1 = Calendar.getInstance();
        c1.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c1.setTime(new Date());
        int year1 = c1.get(Calendar.YEAR);
        int day1 = c1.get(Calendar.DAY_OF_YEAR);

        final Calendar c2 = Calendar.getInstance();
        c2.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c2.setTime(date);
        int year2 = c2.get(Calendar.YEAR);
        int day2 = c2.get(Calendar.DAY_OF_YEAR);

        int pass = 0;
        if (year1 == year2) {
            pass = day2 - day1;
        } else {
            pass = 365 - day2 + day1;
        }
        int result = pass >= 0 ? pass : 0;
        return result;
    }

    public static int countPassDay(final Date date, final int max) {
        if (new Date().getTime() - date.getTime() > max * MS_OF_DAY) {
            return max;
        }
        final Calendar c1 = Calendar.getInstance();
        c1.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c1.setTime(new Date());
        int year1 = c1.get(Calendar.YEAR);
        int day1 = c1.get(Calendar.DAY_OF_YEAR);

        final Calendar c2 = Calendar.getInstance();
        c2.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c2.setTime(date);
        int year2 = c2.get(Calendar.YEAR);
        int day2 = c2.get(Calendar.DAY_OF_YEAR);

        int pass = 0;
        if (year1 == year2) {
            pass = day1 - day2;
        } else {
            pass = 365 - day2 + day1;
        }
        return pass >= 0 ? pass : 0;
    }

    public static int dayOfMonth(final String date) {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c.setTime(dateFormString(date));
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static int monthOfYear(final String date) {
        return monthOfYear(dateFormString(date));
    }

    public static int monthOfYear(final Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c.setTime(date);
        return 1 + c.get(Calendar.MONTH);
    }

    public static String friendlyFurtureTime(final String date) {
        return friendlyFurtureTime(dateFormString(date));
    }

    public static String friendlyPassTime(final Date date) {
        int time = TimeUtil.countPassDay(date, 3);
        switch (time) {
            case 0:
                return formatDate(date, "HH:mm");
            case 1:
                return "تۈنۈگۈن " + formatDate(date, "HH:mm");
            case 2:
                return "ئۈلۈشكۈن " + formatDate(date, "HH:mm");
            default:
                return formatDate(date, "yyyyيىلMMئاينىڭddكۈنى HH:mm");
        }
    }

    public static String friendlyFurtureTime(final Date date) {
        switch (TimeUtil.countFutureDay(date, 3)) {
            case 0:
                return formatDate(date, "HH:mm");
            case 1:
                return "ئەتە " + formatDate(date, "HH:mm");
            case 2:
                return "ئۆگۈن " + formatDate(date, "HH:mm");
            default:
                return formatDate(date, "yyyyيىلMMئاينىڭddكۈنى HH:mm");
        }
    }

    @SuppressWarnings("deprecation")
    public static String recentTime(long passed) {
        if (passed < 60 * 1000) {
            return "ھازىرلا";
        }
        passed /= 60 * 1000;
        if (passed < 60) {
            return passed + "مىنۇت ";
        }
        passed /= 60;
        if (passed < 24) {
            return passed + "سائەت بۇرۇن";
        }
        passed /= 24;
        if (passed < 30) {
            return passed + "كۈن بۇرۇن";
        }
        passed /= 30;
        if (passed < 12) {
            return passed + "ئاي بۇرۇن";
        }
        return passed / 12 + "يىل بۇرۇن";
    }

    /**
     * 将秒转换成时分秒
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "0:0";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


    public static String recentTime(final String date) {
        return recentTime(System.currentTimeMillis() - msOfTime(date));
    }


    public static boolean isToday(String date) {
        return null != date && date.startsWith(getDateString(DATE_FORMAT));
    }

    public static int countWeekFrom(String startDate) {
        final Calendar c1 = Calendar.getInstance();
        c1.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c1.setTime(new Date());
        int year1 = c1.get(Calendar.YEAR);
        int week1 = c1.get(Calendar.WEEK_OF_YEAR);

        final Calendar c2 = Calendar.getInstance();
        c2.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c2.setTime(dateFormString(startDate));
        int year2 = c2.get(Calendar.YEAR);
        int week2 = c2.get(Calendar.WEEK_OF_YEAR);

        int pass = 0;
        if (year1 == year2) {
            pass = week1 - week2;
        } else {
            pass = 52 - week2 + week1;
        }
        return pass >= 0 ? pass : 0;
    }

    public static String getCurrentTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        String date = sDateFormat.format(new Date());
        return date;
    }

    public static String getDateString() {
        return getDateString(DATE_TIME_FORMAT);
    }

    public static String getCurDayDateString() {
        return getDateString(DATE_FORMAT);
    }

    public static String getDateString(final String format) {
        return formatDate(new Date(), format);
    }

    public static String formatTime(final long t) {
        return formatTime(t, DATE_TIME_FORMAT);
    }

    public static String formatTime(final long t, final String format) {
        if (t < 0) {
            return null;
        } else {
            return new SimpleDateFormat(format)
                    .format(new Date(t));
        }
    }

    public static String formatDate(final Date date) {
        return formatDate(date, DATE_TIME_FORMAT);
    }

    public static String formatDate(final Date date, final String format) {
        if (null == date) {
            return "";
        } else {
            return new SimpleDateFormat(format).format(date);
        }
    }

    public static Date dateFormString(final String timeStr, final String format) {
        if (null != timeStr) {
            try {
                return new SimpleDateFormat(format).parse(timeStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Date();
    }

    public static Date dateFormString(final String timeStr) {
        if (null != timeStr) {
            String format = DATE_FORMAT;
            if (timeStr.contains(" ") && timeStr.contains(":")) {
                format = DATE_TIME_FORMAT;
            }
            return dateFormString(timeStr, format);
        }
        return new Date();
    }

    public static long msOfTime(final String time) {
        try {
            String format = DATE_FORMAT;
            if (time.contains(" ") && time.contains(":")) {
                format = DATE_TIME_FORMAT;
            }
            return new SimpleDateFormat(format).parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int parseDaysFrom(final String time) {
        return (int) ((System.currentTimeMillis() - msOfTime(time)) / (24 * 3600 * 1000));
    }

    public static String convertDate(final String dateStr, final String fromFormat, final String toFormat) {
        if (TextUtils.isEmpty(dateStr) || TextUtils.isEmpty(fromFormat) || TextUtils.isEmpty(toFormat)) {
            return "";
        }
        Date date = null;
        try {
            date = new SimpleDateFormat(fromFormat).parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatDate(date, toFormat);
    }

    public static String dayAfter(int addDay) {
        try {
            Calendar cd = Calendar.getInstance();
            cd.add(Calendar.DAY_OF_MONTH, addDay);//增加一天
            return formatDate(cd.getTime(), "MM/dd");
        } catch (Exception e) {
            return null;
        }
    }

    public static String getMd(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return formatDate(sdf.parse(dateStr), "MM月dd日");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    final static int S_IN_MINUTE = 60;
    final static int M_IN_HOUR = 60;
    final static int H_IN_DAY = 24;
    final static int D_IN_MONTH = 30;
    final static int Mth_IN_YEAR = 12;
    final static int S_IN_HOUR = M_IN_HOUR * S_IN_MINUTE;
    final static int S_IN_DAY = H_IN_DAY * S_IN_HOUR;
    final static int S_IN_MONTH = D_IN_MONTH * S_IN_DAY;
    final static int S_IN_YEAR = Mth_IN_YEAR * S_IN_MONTH;

    public static String friendlyCostTime(final int second) {
        if (second < S_IN_MINUTE) {
            return String.format(Locale.getDefault(), "%d秒", second);
        }
        if (second < 2 * S_IN_MINUTE) {
            return String.format(Locale.getDefault(), "%d分钟%d秒",
                    second / S_IN_MINUTE, second % S_IN_MINUTE);
        }
        if (second < S_IN_HOUR) {
            return String.format(Locale.getDefault(), "%d分钟",
                    second / S_IN_MINUTE);
        }
        if (second < S_IN_DAY) {
            return String.format(Locale.getDefault(), "%d小时%d分钟",
                    second / S_IN_HOUR, (second / S_IN_MINUTE) % M_IN_HOUR);
        }
        if (second < S_IN_MONTH) {
            return String.format(Locale.getDefault(), "%d天%d小时",
                    second / S_IN_DAY, (second / S_IN_HOUR) % H_IN_DAY);
        }
        if (second < S_IN_YEAR) {
            return String.format(Locale.getDefault(), "%d个月%d天",
                    second / S_IN_DAY, (second / S_IN_HOUR) % H_IN_DAY);
        }
        return String.format(Locale.getDefault(), "%.1f年", ((float) second) / S_IN_YEAR);
    }

}
