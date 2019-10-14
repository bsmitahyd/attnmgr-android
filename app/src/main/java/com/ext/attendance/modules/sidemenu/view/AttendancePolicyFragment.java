package com.ext.attendance.modules.sidemenu.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ext.attendance.R;
import com.ext.attendance.base.BaseFragment;
import com.ext.attendance.modules.home.view.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AttendancePolicyFragment extends BaseFragment {

    @BindView(R.id.about_us_textView)
    TextView aboutUsTextView;

    private HomeActivity activity;
    private Unbinder unbinder;

    @Override
    protected int layoutRes() {
        return R.layout.about_us_fragment;
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
        initViews();
    }

    private void initViews() {
        aboutUsTextView.setText(activity.getResources().getString(R.string.about_us));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;

    }
}
