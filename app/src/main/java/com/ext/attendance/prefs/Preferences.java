package com.ext.attendance.prefs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Objects;

public class Preferences {

    private final String ACTIVITY_FIRST_TIME = "ACTIVITY_FIRST_TIME";
    private final String USER_ID = "USER_ID";
    private final String LOCATION_DATA = "LOCATION_DATA";
    private final String CURRENT_LOCATION_DATA = "CURRENT_LOCATION_DATA";
    private final String IS_SKIP = "IS_SKIP";


    /**
     * Saves User Id
     */
    public void saveIsSkip(Context context, Boolean value) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences sp = context.getSharedPreferences(IS_SKIP, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isSkip", value);

        // Commit the edits!
        editor.apply();
    }

    public Boolean getIsSkip(Context context) {
        SharedPreferences customerName = context.getSharedPreferences(IS_SKIP, 0);
        return customerName.getBoolean("isSkip", false);
    }


    /**
     * Saves the activity open state
     */
    public void saveActivityOpenState(Context context) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences openState = context.getSharedPreferences(ACTIVITY_FIRST_TIME, 0);
        SharedPreferences.Editor editor = openState.edit();
        editor.putBoolean("state", true);

        // Commit the edits!
        editor.apply();
    }

    public boolean getActivityOpenState(Context context) {
        SharedPreferences openState = context.getSharedPreferences(ACTIVITY_FIRST_TIME, 0);
        return openState.getBoolean("state", false);
    }

    /**
     * Saves User Id
     */
    public void saveUserId(Context context, int value, String email, String phoneNumber,
                           String userName) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences sp = context.getSharedPreferences(USER_ID, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("userId", value);
        editor.putString("userName", userName);
        editor.putString("email", email);
        editor.putString("phoneNumber", phoneNumber);

        // Commit the edits!
        editor.apply();
    }

    public int getUserId(Context context) {
        SharedPreferences customerName = context.getSharedPreferences(USER_ID, 0);
        return customerName.getInt("userId", 0);
    }

    public String getUserPhoneNumber(Context context) {
        SharedPreferences customerName = context.getSharedPreferences(USER_ID, 0);
        return customerName.getString("phoneNumber", "");
    }

    public String getUserEmail(Context context) {
        SharedPreferences customerName = context.getSharedPreferences(USER_ID, 0);
        return customerName.getString("email", "");
    }

    public String getUserName(Context context) {
        SharedPreferences customerName = context.getSharedPreferences(USER_ID, 0);
        return customerName.getString("userName", "");
    }

    /**
     * Current Location
     */
    public void saveLocationData(Context context, double latitude, double longitude) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences locationData = context.getSharedPreferences(LOCATION_DATA, 0);
        SharedPreferences.Editor editor = locationData.edit();
        editor.putString("latitude", String.valueOf(latitude));
        editor.putString("longitude", String.valueOf(longitude));

        // Commit the edits!
        editor.apply();
    }

    public ArrayList<Double> getLocationData(Context context) {
        SharedPreferences locationData = context.getSharedPreferences(LOCATION_DATA, 0);

        ArrayList<Double> location = new ArrayList<>();
        if (!locationData.getString("latitude", "").isEmpty()) {
            location.add(Double.valueOf(Objects.requireNonNull(locationData.getString("latitude", ""))));
            location.add(Double.valueOf(Objects.requireNonNull(locationData.getString("longitude", ""))));
            return location;
        }
        return location;


    }

    /**
     * Current Location
     */
    public void saveCurrentLocationData(Context context, double lat, double lng) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences currentLocationData = context.getSharedPreferences(CURRENT_LOCATION_DATA, 0);
        SharedPreferences.Editor cEditor = currentLocationData.edit();
        cEditor.putString("lat", String.valueOf(lat));
        cEditor.putString("lng", String.valueOf(lng));

        // Commit the edits!
        cEditor.apply();
    }

    public ArrayList<Double> getCurrentLocationData(Context context) {
        SharedPreferences currentLocationData = context.getSharedPreferences(CURRENT_LOCATION_DATA, 0);

        ArrayList<Double> currentLocation = new ArrayList<>();
        currentLocation.add(Double.valueOf(currentLocationData.getString("lat", "")));
        currentLocation.add(Double.valueOf(currentLocationData.getString("lng", "")));

        return currentLocation;
    }

}
