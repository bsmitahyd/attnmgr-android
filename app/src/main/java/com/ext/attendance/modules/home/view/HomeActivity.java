package com.ext.attendance.modules.home.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.ext.attendance.R;
import com.ext.attendance.apputils.AppUtils;
import com.ext.attendance.base.BaseActivity;
import com.ext.attendance.modules.home.viewmodel.EmployeeAttendanceListViewModel;
import com.ext.attendance.modules.login.view.LoginBaseActivity;
import com.ext.attendance.modules.sidemenu.view.AboutUsFragment;
import com.ext.attendance.modules.sidemenu.view.AttendanceFragment;
import com.ext.attendance.modules.sidemenu.view.AttendancePolicyFragment;
import com.ext.attendance.modules.sidemenu.view.ChangePasswordFragment;
import com.ext.attendance.modules.sidemenu.view.ProfileFragment;
import com.ext.attendance.prefs.Session;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    public Toolbar toolbar;
    private NavigationView nvDrawer;
    private View navHeader;
    public TextView toolbarTitle;
    private EmployeeAttendanceListViewModel employeeAttendanceListViewModel;

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    private Session session;
    private TextView employeeIdTextView;

    @Override
    protected int layoutRes() {
        return R.layout.home_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
    }

    public void addFragment(Fragment fragment, boolean addToBackStack) {

        if (addToBackStack)
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container, fragment, "currentFragment").addToBackStack(null).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container, fragment, "currentFragment").commit();

    }

    private void initView(Bundle savedInstanceState) {
        session = new Session(this);

        employeeAttendanceListViewModel = ViewModelProviders.of(this).get(EmployeeAttendanceListViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbarTitle = findViewById(R.id.toolbarTitle);

        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);

        // Loading profile image
        ImageView profileImage = navHeader.findViewById(R.id.profileImage);

        //Loading backgrounf image
        ImageView navBackground = navHeader.findViewById(R.id.img_header_bg);

        //username
        employeeIdTextView = navHeader.findViewById(R.id.tvEmployeeId);
        employeeIdTextView.setText(String.valueOf("EmployeeId" + session.getEmployeeId()));


        //Select Home by default
        navigationView.setCheckedItem(R.id.nav_attendance_fragment);
        Fragment fragment = new HomeFragment();

        if (savedInstanceState == null) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0)
                addFragment(fragment, false);
        }
    }

    public void removeAllBackStack() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Fragment fragment;
        clearBackStack();
        if (id == R.id.nav_profile_fragment) {
            fragment = new ProfileFragment();
            toolbarTitle.setText(AppUtils.capitalizeFirstLetter(getString(R.string.profile)));
            addFragment(fragment, false);
        } else if (id == R.id.nav_attendance_fragment) {
            fragment = new AttendanceFragment();
            toolbarTitle.setText(AppUtils.capitalizeFirstLetter(getString(R.string.attendance)));
            addFragment(fragment, false);
        } else if (id == R.id.nav_attendance_policy_fragment) {
            fragment = new AttendancePolicyFragment();
            toolbarTitle.setText(AppUtils.capitalizeFirstLetter(getString(R.string.attendance_policy)));
            addFragment(fragment, false);
        } else if (id == R.id.nav_aboutus_fragment) {
            fragment = new AboutUsFragment();
            toolbarTitle.setText(AppUtils.capitalizeFirstLetter(getString(R.string.about_us)));
            addFragment(fragment, false);
        } else if (id == R.id.nav_change_password_fragment) {
            fragment = new ChangePasswordFragment();
            toolbarTitle.setText(AppUtils.capitalizeFirstLetter(getString(R.string.change_password)));
            addFragment(fragment, false);
        } else if (id == R.id.nav_logout) {
            showLogoutConfirmationDialog();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            } else {
                if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                    super.onBackPressed();
                } else {
                    Toast.makeText(getBaseContext(), "Press once again to exit!",
                            Toast.LENGTH_SHORT).show();
                }
                back_pressed = System.currentTimeMillis();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    //Logout confirmation dialog fragment
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder logoutBuilder = new AlertDialog.Builder(this);
        logoutBuilder.setTitle("Confirmation Alert")
                .setCancelable(true)
                .setMessage("Are you sure, do you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Session session = new Session(HomeActivity.this);
                        session.setUserId(0);
                        session.setAuthId("");

                        dialog.cancel();
                        startActivity(new Intent(HomeActivity.this, LoginBaseActivity.class));
                        finish();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = logoutBuilder.create();
        alertDialog.show();

        Button okButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button cancelButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        okButton.setTextColor(getResources().getColor(R.color.black));
        cancelButton.setTextColor(getResources().getColor(R.color.black));
    }

    public void resetAndStartHome() {
        // serviceListViewModel.clearViewModelData();
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            addFragment(new HomeFragment(), false);
    }

    public void refreshProfileHeaders() {
        employeeIdTextView.setText(String.valueOf("EmployeeId" + session.getEmployeeId()));
    }

    private void clearBackStack() {
        if (employeeAttendanceListViewModel != null) {
            employeeAttendanceListViewModel.clearViewModelData();
        }
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }
}
