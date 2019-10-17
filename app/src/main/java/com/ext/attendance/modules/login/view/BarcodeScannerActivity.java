package com.ext.attendance.modules.login.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.ext.attendance.apputils.AndroidPermissions;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import timber.log.Timber;

public class BarcodeScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        //Here checking Camera permission for Android OS version 6.0 and above
        checkGalleryPermission();
    }

    public void checkGalleryPermission() {

        if (Build.VERSION.SDK_INT >= 23) {

            if (!(AndroidPermissions.getInstance().checkCameraPermission(this))) {
                AndroidPermissions.getInstance().displayCameraAndGalleryPermissionAlert(this);

            } else {
                QrScanner();
            }
        } else {
            QrScanner();
        }
    }

    public void QrScanner() {

        if (mScannerView != null) {
            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();         // Start camera
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScannerView != null)
            mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Timber.e("RESULT:%s",rawResult.getText()); // Prints scan results
        Timber.e(rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
        Intent resultIntent = new Intent();
        resultIntent.putExtra("barcode", rawResult.getText());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AndroidPermissions.REQUEST_CAMERA) {
            Timber.i("Received response for Camera permission request.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                QrScanner();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
