package com.ext.attendance.modules.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ext.attendance.R;
import com.ext.attendance.apputils.AppKeysInterface;
import com.ext.attendance.base.BaseFragment;
import com.ext.attendance.modules.home.view.HomeActivity;
import com.ext.attendance.modules.login.models.LoginResponseModel;
import com.ext.attendance.modules.login.viewmodels.LoginViewModel;
import com.ext.attendance.prefs.Session;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class LoginFragment extends BaseFragment implements View.OnClickListener, LoginNavigator {

    private LoginBaseActivity activity;
    private Unbinder unbinder;

    @BindView(R.id.btnLogin)
    Button loginButton;
    @BindView(R.id.btnRegister)
    Button registerButton;

    @BindView(R.id.etEmployeeId)
    TextInputEditText employeeIdEditText;
    @BindView(R.id.etPassword)
    TextInputEditText passwordEditText;

    @BindView(R.id.progressBarLogin)
    ProgressBar progressBarLogin;


    private LoginViewModel mLoginViewModel;
    private Session session;

    @Override
    protected int layoutRes() {
        return R.layout.login_fragment;
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

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mLoginViewModel.setNavigator(this);

        mLoginViewModel.getLoginResponseModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<LoginResponseModel>() {
            @Override
            public void onChanged(LoginResponseModel loginResponseModel) {
                if (loginResponseModel != null && loginResponseModel.getStatus() == 200) {
                    Toast.makeText(activity, "login Success", Toast.LENGTH_LONG).show();
                    session.setId(loginResponseModel.getData().getId());
                    session.setEmployeeId(loginResponseModel.getData().getEmployeeid());
                    session.setUsername(loginResponseModel.getData().getUsername());
                    session.setFname(loginResponseModel.getData().getName());
                    session.setLname(loginResponseModel.getData().getLastname());
                    session.setEmail(loginResponseModel.getData().getEmail());
                    session.setContact(loginResponseModel.getData().getContact());
                    //TODO starting new activity and finishing login activity
                    startActivity(new Intent(activity, HomeActivity.class));
                    activity.finish();
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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLogin:

                String androidId = Settings.Secure.getString(getContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);

                if (mLoginViewModel.inputValidation(employeeIdEditText.getText().toString(), passwordEditText.getText().toString(), androidId)) {
                    if (mLoginViewModel.jsonObjectMutableLiveData().getValue() != null) {
                        JsonObject jsonObject = new JsonObject();

                        jsonObject.addProperty(AppKeysInterface.EMPLOYEE_ID, employeeIdEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.PASSWORD, passwordEditText.getText().toString());
                        jsonObject.addProperty(AppKeysInterface.DEVICE_ID, "c1e9293645243213");

                        Timber.d("%s%s", AppKeysInterface.EMPLOYEE_ID, employeeIdEditText.getText().toString());
                        Timber.d("%s%s", AppKeysInterface.PASSWORD, passwordEditText.getText().toString());
                        Timber.d("%s%s", AppKeysInterface.DEVICE_ID, androidId);
                        mLoginViewModel.jsonObjectMutableLiveData().setValue(jsonObject);
                        mLoginViewModel.loginEmployee();
                    }
                } else {
                    Toast.makeText(activity, "All Fields are required!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnRegister:
                Bundle bundle = new Bundle();
                RegisterFragment registerFragment = new RegisterFragment();
                registerFragment.setArguments(bundle);
                activity.addFragment(registerFragment, true);
                break;
            default:
                break;
        }
    }

    private void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
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

    @Override
    public void login() {

    }

    @Override
    public void loadProgressBar(boolean showProgress) {
        if (progressBarLogin != null) {
            progressBarLogin.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (activity.toolbar != null) {
            activity.toolbarTitle.setText(R.string.login);
            activity.toolbar.setTitle("");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
