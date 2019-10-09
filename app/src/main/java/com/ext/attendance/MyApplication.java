package com.ext.attendance;

import android.app.Application;
import android.content.Context;

import com.ext.attendance.apputils.LogUtils;

public class MyApplication extends Application {
    public static final String TAG = MyApplication.class.getSimpleName();

    private static MyApplication _instance;
    private static Context mContext;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        mContext = getApplicationContext();
        //Timber.plant(new Timber.DebugTree());
        LogUtils.init();

    }

    public static Context getContext() {
        return mContext;
    }

    public static MyApplication getInstance() {
        return _instance;
    }
}
