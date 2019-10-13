package com.ext.attendance;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ext.attendance.base.BaseActivity;
import com.ext.attendance.modules.home.view.HomeActivity;
import com.ext.attendance.modules.login.view.LoginBaseActivity;
import com.ext.attendance.prefs.Session;


public class SplashActivity extends BaseActivity {

private Session session;
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    Thread splashTread;

    @Override
    protected int layoutRes() {
        return R.layout.splash_activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAnimations();
        session=new Session(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }

                    if (session.getEmployeeId().length() > 0) {
                        launchActivityAndFinish(HomeActivity.class, null);
                    } else {
                        launchActivityAndFinish(LoginBaseActivity.class, null);
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashActivity.this.finish();
                }

            }
        };
        splashTread.start();
    }
}
