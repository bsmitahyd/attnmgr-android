package com.ext.attendance.modules.login.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ext.attendance.R;
import com.ext.attendance.apputils.AppKeysInterface;
import com.ext.attendance.base.BaseFragment;
import com.ext.attendance.base.GeneralResponse;
import com.ext.attendance.modules.login.viewmodels.RegisterViewModel;
import com.ext.attendance.prefs.Session;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class RegisterFragment extends BaseFragment implements RegisterNavigator, View.OnClickListener {
    private LoginBaseActivity activity;
    private Unbinder unbinder;
    private RegisterViewModel mRegisterViewModel;
    final Calendar myCalendar = Calendar.getInstance();

    @BindView(R.id.buttonRegister)
    Button registerButton;
    @BindView(R.id.etName)
    TextInputEditText NameTextInputEditText;
    @BindView(R.id.etMName)
    TextInputEditText mNameTextInputEditText;
    @BindView(R.id.etLName)
    TextInputEditText lNameTextInputEditText;
    @BindView(R.id.etUserName)
    TextInputEditText usernameTextInputEditText;
    @BindView(R.id.etContact)
    TextInputEditText contactTextInputEditText;
    @BindView(R.id.etEmail)
    TextInputEditText emailTextInputEditText;
    @BindView(R.id.etEmergencyContact)
    TextInputEditText emergencyContactTextInputEditText;
    @BindView(R.id.etPanNumber)
    TextInputEditText panNumberTextInputEditText;
    @BindView(R.id.etDateOfJoining)
    TextInputEditText dojTextInputEditText;
    @BindView(R.id.etDateOfBirth)
    TextInputEditText dobTextInputEditText;
    @BindView(R.id.etDepartment)
    TextInputEditText departmentTextInputEditText;
    @BindView(R.id.etDesignation)
    TextInputEditText designationTextInputEditText;
    @BindView(R.id.etReportTo)
    TextInputEditText reportingToTextInputEditText;
    @BindView(R.id.pbRegister)
    ProgressBar pbRegister;

    @Override
    protected int layoutRes() {
        return R.layout.register_profile_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutRes(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        registerButton.setOnClickListener(this);
        dobTextInputEditText.setOnClickListener(this);
        dojTextInputEditText.setOnClickListener(this);

        activity.toolbar.setTitle("");
        activity.toolbarTitle.setText("Register");

        mRegisterViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        mRegisterViewModel.setNavigator(this);

        mRegisterViewModel.getGeneralResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<GeneralResponse>() {
            @Override
            public void onChanged(GeneralResponse generalResponse) {
                if (generalResponse != null && generalResponse.getStatus() == 200) {
                    Toast.makeText(activity, "Registered Success", Toast.LENGTH_LONG).show();
                    activity.onBackPressed();
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (LoginBaseActivity) context;
    }

    @Override
    public void handleError(Throwable e) {
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            showToast(getErrorMessage(responseBody));
        } else if (e instanceof SocketTimeoutException) {
            showToast("Request Time out.");
        } else if (e instanceof IOException) {
            showToast("Network Error.");
        } else {
            showToast(e.getMessage());
        }
    }

    private void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void register() {

    }

    @Override
    public void loadProgressBar(boolean showProgress) {
        if (pbRegister != null) {
            pbRegister.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonRegister:
                String androidId = Settings.Secure.getString(getContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);

                if (mRegisterViewModel.inputValidation(NameTextInputEditText.getText().toString(),
                        lNameTextInputEditText.getText().toString(), emailTextInputEditText.getText().toString(),
                        contactTextInputEditText.getText().toString(),reportingToTextInputEditText.getText().toString())) {
                    if (mRegisterViewModel.getJsonObjectMutableLiveData().getValue() != null) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty(AppKeysInterface.FIRST_NAME, NameTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.MIDDLE_NAME, mNameTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.LAST_NAME, lNameTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.EMAIL, emailTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.USERNAME,usernameTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.CONTACT, contactTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.EMERGENCY_CONTACT, emergencyContactTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.PAN_NO, panNumberTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.DOB, dobTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.DOJ, contactTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.DESIGNATION, contactTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.DEPARTMENT, contactTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.REPORTING_TO, reportingToTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.DEVICE_ID, androidId);


                        Timber.d("%s%s", AppKeysInterface.FIRST_NAME, NameTextInputEditText.getText().toString());
                        Timber.d("%s%s", AppKeysInterface.LAST_NAME, lNameTextInputEditText.getText().toString());
                        Timber.d("%s%s", AppKeysInterface.EMAIL, emailTextInputEditText.getText().toString());
                        Timber.d("%s%s", AppKeysInterface.CONTACT, contactTextInputEditText.getText().toString());

                        mRegisterViewModel.getJsonObjectMutableLiveData().setValue(jsonObject);

                        mRegisterViewModel.employeeRegister();
                    }
                } else {
                    Toast.makeText(activity, "Please Enter All Required Fields!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.etDateOfBirth:

                dateDialog();
                break;
            case R.id.etDateOfJoining:

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(activity,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dojTextInputEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                break;
            default:
                break;
        }
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        dobTextInputEditText.setText(sdf.format(myCalendar.getTime()));
        dojTextInputEditText.setText(sdf.format(myCalendar.getTime()));
    }

    public void dateDialog() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        DatePickerDialog picker = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dobTextInputEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}