package com.ext.attendance.modules.login.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ext.attendance.R;
import com.ext.attendance.base.BaseFragment;
import com.ext.attendance.modules.login.models.LoginResponseModel;
import com.ext.attendance.modules.login.viewmodels.LoginViewModel;
import com.ext.attendance.prefs.Session;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

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

    private LoginViewModel mLoginViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.login_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Session session;

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
        mLoginViewModel.getNavigator();

        mLoginViewModel.getLoginResponseModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<LoginResponseModel>() {
            @Override
            public void onChanged(LoginResponseModel loginResponseModel) {
                if (loginResponseModel != null && loginResponseModel.getStatus() == 200) {
                    Toast.makeText(activity, "login Success", Toast.LENGTH_LONG).show();
                    activity.onBackPressed();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLogin:
                if (mLoginViewModel.inputValidation(employeeIdEditText.getText().toString(), passwordEditText.getText().toString())) {
                    if (mLoginViewModel.getGeneralResponseMutableLiveData().getValue() != null) {
                       // mLoginViewModel.getGeneralResponseMutableLiveData()
                    }
                }
                break;
            case R.id.btnRegister:
                break;
            default:
                break;
        }
    }
}
