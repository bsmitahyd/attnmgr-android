package com.ext.attendance.apputils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.ext.attendance.R;


public class AndroidPermissions {

    public static final int REQUEST_LOCATION = 5;
    public static final int REQUEST_CAMERA = 2;
    public static final int REQUEST_READ_SD_CARD = 3;
    public static final int REQUEST_CALL = 4;
    /**
     * Id to identify a SMS permission request.
     */
    public static final int REQUEST_READ_SMS = 5;
    private static String[] PERMISSIONS_CALL = {Manifest.permission.CALL_PHONE};

    private static String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA};

    private static String[] permissionsCameraGallery = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    // private static String[] permissionsPrimary = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS};

    private static String[] permissionsPrimary = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    public static AndroidPermissions instance;

    public static AndroidPermissions getInstance() {
        if (instance == null) {
            instance = new AndroidPermissions();
        }
        return instance;
    }

    public boolean checkCallPermission(Context thisActivity) {
        if (ActivityCompat.checkSelfPermission(thisActivity, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(thisActivity,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkReadSmsPermission(Context thisActivity) {
        if (ActivityCompat.checkSelfPermission(thisActivity, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(thisActivity,
                Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    public void displayCallPermissionAlert(Activity thisActivity) {
        // No explanation needed, we can request the permission.
        ActivityCompat.requestPermissions(thisActivity, PERMISSIONS_CALL, REQUEST_CALL);
    }


    public boolean checkLocationPermission(Context thisActivity) {
        if (ActivityCompat.checkSelfPermission(thisActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(thisActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    //method checks for SD card read, write  permission granted OR not.
    public boolean checkSDCardReadPermission(Context thisActivity) {
        if (ActivityCompat.checkSelfPermission(thisActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(thisActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }

    }

    //method checks for camera permission granted OR not.
    public boolean checkCameraPermission(Context thisActivity) {
        if (ActivityCompat.checkSelfPermission(thisActivity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }

    }

    public void displayCameraPermissionAlert(Activity thisActivity) {
        // No explanation needed, we can request the permission.
        ActivityCompat.requestPermissions(thisActivity, PERMISSIONS_CAMERA, REQUEST_CAMERA);
    }


    public void displaySDCardReadPermissionAlert(Activity thisActivity) {
        // No explanation needed, we can request the permission.
        ActivityCompat.requestPermissions(thisActivity, permissionsCameraGallery, REQUEST_READ_SD_CARD);
    }

    public void displayCameraAndGalleryPermissionAlert(Activity activity) {
        // No explanation needed, we can request the permission.
        ActivityCompat.requestPermissions(activity, permissionsCameraGallery, REQUEST_CAMERA);
    }

    public void displayLocationCallContactPermissionAlert(Activity activity) {
        // No explanation needed, we can request the permission.
        ActivityCompat.requestPermissions(activity, permissionsPrimary, REQUEST_LOCATION);
    }

    public void displayAlert(final Activity context, final int position) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        if (position == REQUEST_LOCATION) {
            builder1.setTitle(context.getResources().getString(R.string.location));
            builder1.setMessage(context.getResources().getString(R.string.location_desc));
        } else if (position == REQUEST_CAMERA) {
            builder1.setTitle(context.getResources().getString(R.string.camera));
            builder1.setMessage(context.getResources().getString(R.string.camera_desc));
        } else if (position == REQUEST_READ_SD_CARD) {
            builder1.setTitle(context.getResources().getString(R.string.sdcard));
            builder1.setMessage(context.getResources().getString(R.string.sdcard_des));
        } else if (position == REQUEST_CALL) {
            builder1.setTitle(context.getResources().getString(R.string.call_title));
            builder1.setMessage(context.getResources().getString(R.string.call_des));
        }
        builder1.setCancelable(true);

        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if (position == REQUEST_CALL) {
                            displayCallPermissionAlert(context);
                        }
                    }
                });

        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert1 = builder1.create();
        alert1.show();
    }

}
