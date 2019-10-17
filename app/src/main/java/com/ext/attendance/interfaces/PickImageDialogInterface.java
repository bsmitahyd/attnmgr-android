package com.ext.attendance.interfaces;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Interface between PickImageDialog and the calling classes
 */
public interface PickImageDialogInterface {

    void holdRecordingFile(Uri fileUri, File file, Uri uri, int crop);

    void handleIntent(Intent intent, int requestCode);

    void displayPickedImage(File file);

}
