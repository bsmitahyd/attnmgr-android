package com.ext.attendance.modules.sidemenu.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ext.attendance.R;
import com.ext.attendance.base.BaseFragment;
import com.ext.attendance.base.CommonNavigator;
import com.ext.attendance.modules.home.view.HomeActivity;
import com.ext.attendance.modules.login.view.LoginBaseActivity;
import com.ext.attendance.modules.sidemenu.models.ChangePwdResponseModel;
import com.ext.attendance.modules.sidemenu.viewmodels.ChangePasswordViewModel;
import com.ext.attendance.prefs.Session;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;

public class ChangePasswordFragment extends BaseFragment implements View.OnClickListener, CommonNavigator {

    private Unbinder unbinder;
    private HomeActivity activity;
    private Session session;
    private ChangePasswordViewModel mChangePasswordViewModel;

    @BindView(R.id.currentPassEditText)
    TextInputEditText currentPassTextInputEditText;
    @BindView(R.id.newPassEditText)
    TextInputEditText newPassTextInputEditText;
    @BindView(R.id.newConfPassEditText)
    TextInputEditText newConfPassTextInputEditText;
    @BindView(R.id.buttonChangePassword)
    Button changePassButton;
    @BindView(R.id.pbChangePassword)
    ProgressBar progressBar;

    @Override
    protected int layoutRes() {
        return R.layout.change_password_fragment;
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
        changePassButton.setOnClickListener(this);

        mChangePasswordViewModel = ViewModelProviders.of(activity).get(ChangePasswordViewModel.class);
        mChangePasswordViewModel.setNavigator(this);
        mChangePasswordViewModel.getChangePwdResponseModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ChangePwdResponseModel>() {
            @Override
            public void onChanged(ChangePwdResponseModel changePwdResponseModel) {
                if (changePwdResponseModel != null && changePwdResponseModel.getStatus() == 200) {
                    Toast.makeText(activity, "Password Changed Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(activity, LoginBaseActivity.class));
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonChangePassword:

                break;
        }
    }

    @Override
    public void handleError(Throwable e) {
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            Toast.makeText(activity, getErrorMessage(responseBody), Toast.LENGTH_LONG).show();
        } else if (e instanceof SocketTimeoutException) {
            Toast.makeText(activity, "Request Time out.", Toast.LENGTH_LONG).show();
        } else if (e instanceof IOException) {
            Toast.makeText(activity, "Network Error.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void loadProgressBar(boolean showProgress) {
        if (progressBar != null) {
            progressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mChangePasswordViewModel != null) {
            mChangePasswordViewModel.clearViewModelData();
        }
    }

}
