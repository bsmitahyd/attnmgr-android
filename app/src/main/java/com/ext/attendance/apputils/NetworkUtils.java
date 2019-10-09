package com.ext.attendance.apputils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    public static boolean isNetworkConnected(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm != null ? cm.getActiveNetworkInfo() : null;
            return netInfo != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}