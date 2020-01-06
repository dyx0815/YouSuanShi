package com.dan.yousuanshi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final SimpleDateFormat SHOW_TIME_YEAR = new SimpleDateFormat("yyyy年MM月dd日");
    public static final SimpleDateFormat SHOW_TIME_MOTH = new SimpleDateFormat("MM月dd日");
    public static final SimpleDateFormat SHOW_TIME = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DATE_STRING = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static int diffMinute(String smdate, String bdate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(StringToDate(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(StringToDate(bdate));
        long time2 = cal.getTimeInMillis();
        long minutes = (time2 - time1) / (1000 * 60);//2者的分钟数值
        int min = (int) (minutes % 1440); //求分钟
        return min;
    }

    /**
     * im显示时间
     *
     * @param endDate
     * @param nowDate
     * @return
     */
    public static String showTime(Date endDate, Date nowDate) {
        Calendar now = Calendar.getInstance();
        now.setTime(nowDate);
        Calendar lastTime = Calendar.getInstance();
        lastTime.setTime(endDate);
        if (lastTime.get(Calendar.DAY_OF_YEAR) - now.get(Calendar.DAY_OF_YEAR) == -1) {
            return "昨天";
        } else if (lastTime.get(Calendar.DAY_OF_YEAR) - now.get(Calendar.DAY_OF_YEAR) == -2) {
            return "前天";
        } else {
            if (lastTime.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                if (lastTime.get(Calendar.WEEK_OF_YEAR) == now.get(Calendar.WEEK_OF_YEAR)) {
                    if (lastTime.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
                        return SHOW_TIME.format(endDate);
                    } else {
                        String week = "";
                        switch (lastTime.get(Calendar.DAY_OF_WEEK)) {
                            case 1:
                                week = "周日";
                                break;
                            case 2:
                                week = "周一";
                                break;
                            case 3:
                                week = "周二";
                                break;
                            case 4:
                                week = "周三";
                                break;
                            case 5:
                                week = "周四";
                                break;
                            case 6:
                                week = "周五";
                                break;
                            case 7:
                                week = "周六";
                                break;
                        }
                        return week;
                    }
                } else {
                    return SHOW_TIME_MOTH.format(endDate);
                }
            } else {
                return SHOW_TIME_YEAR.format(endDate);
            }
        }
    }


    public static String showTime2(Date endDate, Date nowDate) {
        Calendar now = Calendar.getInstance();
        now.setTime(nowDate);
        Calendar lastTime = Calendar.getInstance();
        lastTime.setTime(endDate);
        if (lastTime.get(Calendar.DAY_OF_YEAR) - now.get(Calendar.DAY_OF_YEAR) == -1) {
            return "昨天"+SHOW_TIME.format(endDate);
        } else if (lastTime.get(Calendar.DAY_OF_YEAR) - now.get(Calendar.DAY_OF_YEAR) == -2) {
            return "前天"+SHOW_TIME.format(endDate);
        } else {
            if (lastTime.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                if (lastTime.get(Calendar.WEEK_OF_YEAR) == now.get(Calendar.WEEK_OF_YEAR)) {
                    if (lastTime.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
                        return SHOW_TIME.format(endDate);
                    } else {
                        String week = "";
                        switch (lastTime.get(Calendar.DAY_OF_WEEK)) {
                            case 1:
                                week = "周日";
                                break;
                            case 2:
                                week = "周一";
                                break;
                            case 3:
                                week = "周二";
                                break;
                            case 4:
                                week = "周三";
                                break;
                            case 5:
                                week = "周四";
                                break;
                            case 6:
                                week = "周五";
                                break;
                            case 7:
                                week = "周六";
                                break;
                        }
                        return week+SHOW_TIME.format(endDate);
                    }
                } else {
                    return SHOW_TIME_MOTH.format(endDate)+SHOW_TIME.format(endDate);
                }
            } else {
                return SHOW_TIME_YEAR.format(endDate)+SHOW_TIME.format(endDate);
            }
        }
    }

    public static String dateToString(Date date){
        return DATE_STRING.format(date);
    }

    public static Date StringToDate(String date){
        Date date1 = null;
        try {
             date1 = DATE_STRING.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static String longToString(Long date){
        Date date1 = new Date(date);
        return DATE_STRING.format(date1);
    }
}
