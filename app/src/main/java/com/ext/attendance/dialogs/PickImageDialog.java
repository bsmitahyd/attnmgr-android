package com.ext.attendance.dialogs;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.ext.attendance.BuildConfig;
import com.ext.attendance.R;
import com.ext.attendance.apputils.Constants;
import com.ext.attendance.interfaces.PickImageDialogInterface;
import com.ext.attendance.modules.home.view.HomeActivity;

import com.ext.attendance.modules.login.view.LoginBaseActivity;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import static com.ext.attendance.MyApplication.getContext;


/**
 * Helper class to select image
 * Source: http://stackoverflow.com/questions/9414955/trigger-mediascanner-on-specific-path-folder
 * -how-to
 * Source: http://www.grokkingandroid.com/adding-files-to-androids-media-library-using-the-
 * mediascanner/
 */
public class PickImageDialog {

    //Holds activity
    private LoginBaseActivity mActivity;

    //Interface
    public PickImageDialogInterface delegate;

    public PickImageDialog(LoginBaseActivity activity) {
        this.mActivity = activity;
    }

    public void showDialog() {

        String str[] = new String[]{"Take Photo", "From Gallery"};
        new AlertDialog.Builder(mActivity)
                .setTitle(R.string.app_name)
                .setItems(str,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    createImageFile(false);

                                    //Source - https://developer.android.com/training/camera/
                                    // photobasics.html#TaskGallery
                                    Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (picIntent.resolveActivity(mActivity.getPackageManager())
                                            != null) {

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                                            picIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                            Uri contentUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", file);
                                            picIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                                            delegate.handleIntent(picIntent,
                                                    Constants.REQUEST_CODE_SELECT_IMAGE);
                                        } else {
                                            delegate.holdRecordingFile(photoUri, file, null, 0);
                                            picIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                            delegate.handleIntent(picIntent,
                                                    Constants.REQUEST_CODE_SELECT_IMAGE);
                                        }


                                    }
                                } else {
                                    // Create intent to Open Image applications like Gallery,
                                    // Google Photos
                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                            MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                    galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                    // Start the Intent
                                    delegate.handleIntent(galleryIntent,
                                            Constants.REQUEST_CODE_SELECT_IMAGE_GALLERY);

                                }
                            }
                        }).show();
    }

    private File file;
    private Uri photoUri = null;

    private Uri createImageFile(boolean isGaleeryPic) {
        try {
            // Find the SD Card path
            File filepath = Environment.getExternalStorageDirectory();

            // Create a new folder in SD Card
            File dir = new File(filepath.getAbsolutePath());

            File dataFolder;
            if (isGaleeryPic) {
                File androidFolder = new File(dir, "DCIM");
                if (!androidFolder.exists()) {
                    androidFolder.mkdir();
                }
                dataFolder = new File(androidFolder, "Camera");
                if (!dataFolder.exists()) {
                    dataFolder.mkdir();
                }
            } else {
                File androidFolder = new File(dir, "Android");
                if (!androidFolder.exists()) {
                    androidFolder.mkdir();
                }
                File dFolder = new File(androidFolder, "data");
                if (!dFolder.exists()) {
                    dFolder.mkdir();
                }
                dataFolder = new File(dFolder, BuildConfig.APPLICATION_ID);
                if (!dataFolder.exists()) {
                    dataFolder.mkdir();
                }
            }

            Calendar cal = Calendar.getInstance();
            long millis = (cal.get(Calendar.HOUR_OF_DAY) * 24 * 60 * 60)
                    + (cal.get(Calendar.MINUTE) * 60 * 60) + (cal.get(Calendar.SECOND) * 60);
            String date = cal.get(Calendar.YEAR) + "" + cal.get(Calendar.MONTH) + ""
                    + cal.get(Calendar.DATE) + "_" + millis;

            // Create a name for the saved image
            file = new File(dataFolder, date + ".jpg");
            if (!file.exists()) {
                file.createNewFile();
            }

            photoUri = Uri.fromFile(file);

        } catch (IOException e) {
            //Do Nothing
        }

        return photoUri;
    }

    public void resetFiles(Uri savUri, File recFile) {
        photoUri = savUri;
        file = recFile;
    }

    public void onActivityResult(int requestCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_CODE_SELECT_IMAGE: //Camera
                delegate.holdRecordingFile(photoUri, file, null, 1);
                cropImage(photoUri, photoUri);
                //delegate.displayPickedImage(file);

                break;

            case Constants.REQUEST_CODE_SELECT_IMAGE_GALLERY: //Gallery
                if (data != null) {
                    createImageFile(true);
                    delegate.holdRecordingFile(photoUri, file, data.getData(), 1);
                    cropImage(data.getData(), photoUri);
                    // mActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data.getData()));
                    //delegate.displayPickedImage(file);
                }

                break;

            case UCrop.REQUEST_CROP:
                updateImageVisibilityInGallery();
                delegate.displayPickedImage(file);
                break;

            case UCrop.RESULT_ERROR:
                onResultCancelled();
                break;

            default:
                break;

        }

    }

    public boolean onResultCancelled() {
        return file != null && file.delete();
    }


    private void updateImageVisibilityInGallery() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // only for kitkat and newer versions
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            if (photoUri != null) {
                intent.setData(photoUri);
            }
            mActivity.sendBroadcast(intent);

        } else {
            mActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));

        }
    }

    public void cropImage(Uri sourceUri, Uri destinationUri) {
        UCrop.of(sourceUri, destinationUri)
                .withAspectRatio(16, 16)
                .start(mActivity, UCrop.REQUEST_CROP);

    }


}
