package com.earl.auth;

import java.util.Calendar;
import java.util.Date;

public class DateTest {

    public static void main(String[] args) {
//        Date date = getDateStartByInterval(new Date(), "week");
//        Date endDate = getDateEndByInterval(new Date(), "week");
//        System.out.println(date);
//        System.out.println(endDate);

        getDateBegin();
    }

    private static Date getDateStartByInterval(Date date, String interval){
        Calendar calendar = Calendar.getInstance();
        switch (interval){
            case "day":
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                System.out.println("1");
                return calendar.getTime();
            case "week":
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.add(Calendar.DAY_OF_MONTH, -7);
                return calendar.getTime();
            case "month":
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.add(Calendar.DAY_OF_MONTH, -30);
                return calendar.getTime();
            default:
                System.out.println("default");
                return date;
        }
    }

    private static Date getDateEndByInterval(Date date, String interval){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTime();
    }

    public static Date getDateBegin(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        System.out.println(calendar.getTime());

        return calendar.getTime();
    }

}
