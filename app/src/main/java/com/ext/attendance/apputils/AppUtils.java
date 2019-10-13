package com.ext.attendance.apputils;

import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class AppUtils {

    public static long convertDateToTimeStamp(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date d;
        long time = 0;
        try {
            d = sdf.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            time = c.getTimeInMillis();
            System.out.println("TIMESTAMP_T:" + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }


    public static long convertTimeToTimeStamp(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        Date d;
        long time = 0;
        try {
            d = sdf.parse(timeStr);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            time = c.getTimeInMillis();
            System.out.println("TIMESTAMP_T:" + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    // Get Edit Text Value and return in String
    public static String getStringEditTextValue(EditText edittext) {
        if (edittext != null && edittext.length() > 0) {
            return edittext.getText().toString().trim();
        }
        return "";
    }

    // Get TextView Value and return in String
    public static String getStringTextViewValue(TextView textView) {
        if (textView != null && textView.length() > 0) {
            return textView.getText().toString().trim();
        }
        return "";
    }

    public static long getCurrentTimeStamp() {
        LogUtils.i(String.valueOf(System.currentTimeMillis()));
        return System.currentTimeMillis();
    }


    public static long dateIntoTimeStamp(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm aa", Locale.getDefault());
        Date d = null;
        long time = 0;
        try {
            d = sdf.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            time = c.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static long newDateIntoTimeStamp(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM dd yyyy hh:mm aa", Locale.getDefault());
        Date d = null;
        long time = 0;
        try {
            d = sdf.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            time = c.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getCurrentDate() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        System.out.println(formatter.format(cal.getTime())); //2016/11/16 12:08:43
        return formatter.format(cal.getTime());
    }

    public static String getCurrentTimeUsingCalendar() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        System.out.println("Current time of the day using Calendar - 24 hour format: " + dateFormat.format(date));
        return dateFormat.format(date);
    }


    public static String getFormattedTime(long dateData) {
        String dateStr = "";
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateData*1000);
        Date date = cal.getTime();
        DateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.getDefault()); //If you need time just put specific format for time like 'HH:mm:ss'
        dateStr = formatter.format(date);
        LogUtils.i(dateStr);
        return dateStr;
    }

    public static String dateIntoMonth(String date) {
        Date date1 = null;

        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        DateFormat targetFormat = new SimpleDateFormat("MMM",Locale.getDefault());
        String formattedDate = "";
        try{
            date1 = originalFormat.parse(date);
            formattedDate = targetFormat.format(date1);  // 20120821
        }catch (Exception e){
            Timber.e("%s", e.getMessage());
        }

        return formattedDate;
    }
    public static String dateIntoYear(String date) {
        Date date1 = null;

        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        DateFormat targetFormat = new SimpleDateFormat("yyyy",Locale.getDefault());
        String formattedDate = "";
        try{
            date1 = originalFormat.parse(date);
            formattedDate = targetFormat.format(date1);  // 20120821
        }catch (Exception e){
            Timber.e("%s", e.getMessage());
        }

        return formattedDate;
    }



}
