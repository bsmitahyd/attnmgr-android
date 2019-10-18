package com.ext.attendance.modules.sidemenu.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ext.attendance.R;
import com.ext.attendance.base.BaseFragment;
import com.ext.attendance.base.CommonNavigator;
import com.ext.attendance.modules.home.view.HomeActivity;
import com.ext.attendance.prefs.Session;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;

public class ProfileFragment extends BaseFragment implements View.OnClickListener, CommonNavigator {

    private HomeActivity activity;
    private Session session;
    private Unbinder unbinder;


    @BindView(R.id.imageViewEmp)
    ImageView profileImageView;
    @BindView(R.id.etName)
    TextInputEditText nameTextInputEditText;
    @BindView(R.id.etMName)
    TextInputEditText mNameTextInputEditText;
    @BindView(R.id.etLName)
    TextInputEditText lNameTextInputEditText;
    @BindView(R.id.etEmail)
    TextInputEditText emailTextInputEditText;
    @BindView(R.id.etContact)
    TextInputEditText contactTextInputEditText;

    @BindView(R.id.etDepartment)
    TextInputEditText deptTextInputEditText;
    @BindView(R.id.etDesignation)
    TextInputEditText designationTextInputEditText;
    @BindView(R.id.etDOJ)
    TextInputEditText dOJTextInputEditText;
    @BindView(R.id.etReportingTo)
    TextInputEditText reportingToTextInputEditText;
    @BindView(R.id.etCompany)
    TextInputEditText companyTextInputEditText;
    @BindView(R.id.etBranch)
    TextInputEditText branchTextInputEditText;

    @BindView(R.id.buttonUpdateProfile)
    Button updateButton;
    @BindView(R.id.profile_progress)
    ProgressBar profileProgressBar;

    @Override
    protected int layoutRes() {
        return R.layout.employee_profile_fragment;
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

        updateButton.setOnClickListener(this);
        profileImageView.setOnClickListener(this);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonUpdateProfile:
                break;
            case R.id.imageViewEmp:
                break;
            default:
                break;
        }
    }

    @Override
    public void handleError(Throwable e) {
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            showToast(activity, getErrorMessage(responseBody));
        } else if (e instanceof SocketTimeoutException) {
            showToast(activity, "Request Time out.");
        } else if (e instanceof IOException) {
            showToast(activity, "Network Error.");
        } else {
            showToast(activity, e.getMessage());
        }
    }

    @Override
    public void loadProgressBar(boolean showProgress) {
        if (profileProgressBar != null) {
            profileProgressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
