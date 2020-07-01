package com.example.version1.Util;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TimeUtil {

    /**
     * 格式：年－月－日 小时：分钟：秒
     */
    public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认formater yyyy-MM-dd HH:mm:ss
     */
//    public static final SimpleDateFormat TIMEFORMAT = new SimpleDateFormat(
//            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(FORMAT_ONE, Locale.CHINA);
        }
    };

    public static final String FORMAT_TWO = "yyyy-MM-dd";
    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(FORMAT_TWO, Locale.CHINA);
        }
    };

    public static final String FORMAT_THREE = "yyyy-MM-dd HH:mm";
    private final static ThreadLocal<SimpleDateFormat> dateFormater3 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(FORMAT_THREE, Locale.CHINA);
        }
    };
    private static final List<String>hours = new ArrayList<>();
    private static final List<String>minutes = new ArrayList<>();

    static{
        for(int i=8;i<22;i++){
            hours.add(String.valueOf(i));
        }
        for(int i=0;i<6;i++){
            for(int j=0;j<=9;j++){
                minutes.add(String.valueOf(i)+String.valueOf(j));
            }
        }
    }

    public static List<String>getHours(){
        return hours;
    }

    public static  List<String>getMinutes(){
        return  minutes;
    }



    /***
     * 计算两个时间差，返回的是的秒s
     *
     * @author qxian 2016-5-25下午23:50:06
     *
     * @return long
     * @param date1
     * @param date2
     * @return
     */
    public static long calDateDifferent(String date1, String date2) {

        long diff = 0;

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = dateFormater.get().parse(date1);
            d2 = dateFormater.get().parse(date2);

            // 毫秒ms
            diff = d2.getTime() - d1.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return diff / 1000;
    }

    public static String calFinalTime(Calendar cal,int minute){
        Date d = cal.getTime();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),22,0);
        long resTime = d.getTime()+minute*60*1000;
        long limiTime = cal.getTime().getTime();
        if(resTime<limiTime && d.getTime()>System.currentTimeMillis())
            return dateFormater3.get().format(resTime);
        else return "不合法的预约时段";
    }



    public static String getCurTimeStr() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater.get().format(cal.getTime());
        return curDate;
    }

    /**
     * 获取过去或者未来 任意天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public static ArrayList<String> getDataList(int intervals) {
//        ArrayList<String> pastDaysList = new ArrayList<>();
        ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i <intervals; i++) {
//            pastDaysList.add(getPastDate(i));
            fetureDaysList.add(getFetureDate(i));
        }
        return fetureDaysList;
    }

    /**
     * 获取过去第n天的日期
     *
     * @param n
     * @return
     */
    public static String getPastDate(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - n);
        Date today = calendar.getTime();
        return dateFormater2.get().format(today);
    }



    /**
     * 获取未来 第n天的日期
     * @param n
     * @return
     */
    public static String getFetureDate(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + n);
        Date today = calendar.getTime();
        return dateFormater2.get().format(today);
    }

}