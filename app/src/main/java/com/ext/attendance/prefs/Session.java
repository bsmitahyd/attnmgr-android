package com.ext.attendance.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "com.ext.attendance";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String USER_ID = "userId";
    private static final String AUTH_ID = "authId";
    private static final String ADMIN_ID = "adminId";
    private static final String USER_NAME = "username";
    private static final String EMAIL = "email";
    private static final String MOBILE = "mobile";
    private static final String DEVICE_ID = "deviceId";
    private static final String USER_TYPE = "userType";
    private static final String IS_ADDRESS_UPDATED = "address_updated";

    private static final String F_NAME = "fname";
    private static final String L_NAME = "lname";
    private static final String PROFILE_PIC_URL = "profile_pic";

    public Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public void setUserId(int userId) {
        editor.putInt(USER_ID, userId);
        editor.commit();
    }

    public int getUserId() {
        return pref.getInt(USER_ID, 0);
    }


    public void setAdminId(String userId) {
        editor.putString(ADMIN_ID, userId);
        editor.commit();
    }

    public String getAdminId() {
        return pref.getString(ADMIN_ID, "");
    }


    public void setAuthId(String authId) {
        editor.putString(AUTH_ID, authId);
        editor.commit();
    }

    public String getAuthId() {
        return pref.getString(AUTH_ID, "");
    }

    public void setUsername(String username) {
        editor.putString(USER_NAME, username);
        editor.commit();
    }

    public String getUsername() {
        return pref.getString(USER_NAME, "");
    }


    public void setEmail(String username) {
        editor.putString(EMAIL, username);
        editor.commit();
    }

    public String getEmail() {
        return pref.getString(EMAIL, "");
    }

    public void setMobile(String username) {
        editor.putString(MOBILE, username);
        editor.commit();
    }

    public String getMobile() {
        return pref.getString(MOBILE, "");
    }


    public void setDeviceId(String deviceId) {
        editor.putString(DEVICE_ID, deviceId);
        editor.commit();
    }

    public String getDeviceId() {
        return pref.getString(DEVICE_ID, "123");
    }

    public void setUserType(String userType) {
        editor.putString(USER_TYPE, userType);
        editor.commit();
    }

    public String getUserType() {
        return pref.getString(USER_TYPE, "");
    }

    public void setAddressUpdated(boolean userType) {
        editor.putBoolean(IS_ADDRESS_UPDATED, userType);
        editor.commit();
    }

    public boolean isAddressUpdated() {
        return pref.getBoolean(IS_ADDRESS_UPDATED, false);
    }

    public void setFname(String fname) {
        editor.putString(F_NAME, fname);
        editor.commit();
    }

    public String getFname() {
        return pref.getString(F_NAME, "");
    }

    public void setLname(String lname) {
        editor.putString(L_NAME, lname);
        editor.commit();
    }

    public String getLname() {
        return pref.getString(L_NAME, "");
    }


    public void setProfilePicUrl(String lname) {
        editor.putString(PROFILE_PIC_URL, lname);
        editor.commit();
    }

    public String getProfilePicUrl() {
        return pref.getString(PROFILE_PIC_URL, "");
    }

}