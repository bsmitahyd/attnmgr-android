package com.ext.attendance.push;

import androidx.annotation.NonNull;

import com.ext.attendance.MyApplication;
import com.ext.attendance.prefs.Session;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import timber.log.Timber;

public class AttnMgrFireBaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Timber.e(s);
        Session session = new Session(MyApplication.getInstance().getApplicationContext());
        session.setDeviceToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage != null) {
            Timber.d("remoteMessage:%s", remoteMessage.getData());

        }
    }
}
