package com.ext.attendance.apputils;

import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class AppUtils {


    public static ArrayList autocomplete(String input) {

        final String LOG_TAG = "GooglePlacesAuto";
        final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
        final String TYPE_AUTOCOMPLETE = "/autocomplete";
        final String OUT_JSON = "/json";
        final String API_KEY = "AIzaSyCPTj53m0lLG7Cfb0oJgMPnxEViCDhWIb0";

        ArrayList resultList = null;
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            /*sb.append("&components=country:us");
            sb.append("&components=locality:Issaquah");*/
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Timber.e(e, "Error Places API URL");
            return resultList;
        } catch (IOException e) {
            Timber.e(e, "Error Places API");
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Timber.e(e, "Cannot process JSON results");
        }
        return resultList;
    }


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
        cal.setTimeInMillis(dateData);
        Date date = cal.getTime();
        DateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault()); //If you need time just put specific format for time like 'HH:mm:ss'
        dateStr = formatter.format(date);
        LogUtils.i(dateStr);
        return dateStr;
    }


}
