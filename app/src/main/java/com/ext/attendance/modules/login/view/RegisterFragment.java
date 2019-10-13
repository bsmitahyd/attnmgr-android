package com.ext.attendance.modules.login.view;

import android.annotation.SuppressLint;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
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
    private Session session;

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
    @BindView(R.id.etDateOfBirth)
    TextInputEditText dobTextInputEditText;
    @BindView(R.id.etDesignation)
    TextInputEditText designationTextInputEditText;
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
        session = new Session(activity);
        registerButton.setOnClickListener(this);
        dobTextInputEditText.setOnClickListener(this);

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
                @SuppressLint("HardwareIds")
                String androidId = Settings.Secure.getString(activity.getContentResolver(),
                        Settings.Secure.ANDROID_ID);


                if (mRegisterViewModel.inputValidation(Objects.requireNonNull(NameTextInputEditText.getText()).toString(),
                        lNameTextInputEditText.getText().toString(), emailTextInputEditText.getText().toString(),
                        contactTextInputEditText.getText().toString())) {

                    if (mRegisterViewModel.getJsonObjectMutableLiveData().getValue() != null) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty(AppKeysInterface.FIRST_NAME, NameTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.MIDDLE_NAME, mNameTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.LAST_NAME, lNameTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.EMAIL, emailTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.USERNAME, usernameTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.CONTACT, contactTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.EMERGENCY_CONTACT, emergencyContactTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.PAN_NO, panNumberTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.DOB, dobTextInputEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.DEVICE_ID, androidId);
                        jsonObject.addProperty(AppKeysInterface.DEVICE_TOKEN, session.getDeviceToken());//device token used for push notificstion
                        jsonObject.addProperty(AppKeysInterface.DEVICE_TYPE, AppKeysInterface.ANDROID);

                        Timber.d("Register_DATA:%s", new Gson().toJson(jsonObject));
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

            default:
                break;
        }
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        dobTextInputEditText.setText(sdf.format(myCalendar.getTime()));
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