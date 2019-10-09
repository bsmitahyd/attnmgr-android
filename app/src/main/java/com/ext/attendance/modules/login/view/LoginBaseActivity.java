package com.ext.attendance.modules.login.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.ext.attendance.R;
import com.ext.attendance.base.BaseActivity;

import timber.log.Timber;

public class LoginBaseActivity extends BaseActivity {

    public Toolbar toolbar;
    public TextView toolbarTitle;


    @Override
    protected int layoutRes() {
        return R.layout.login_base_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         initViews(savedInstanceState);
    }

    private void initViews(Bundle savedInstanceState){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(getString(R.string.login));

        if (savedInstanceState == null) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                addFragment(new LoginFragment(), false);
            }
        }
    }

    public void addFragment(Fragment fragment, boolean addToBackStack) {

        if (addToBackStack)
            getSupportFragmentManager().beginTransaction().replace(R.id.login_container, fragment).addToBackStack(null).commit();
        else
            getSupportFragmentManager().beginTransaction().add(R.id.login_container, fragment).commit();

    }

    public void removeAllBackStack() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            getSupportFragmentManager().popBackStack();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.d("LoginBaseActivity: onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
