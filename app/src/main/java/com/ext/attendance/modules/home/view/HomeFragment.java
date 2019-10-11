package com.ext.attendance.modules.home.view;

import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ext.attendance.R;
import com.ext.attendance.apputils.AppUtils;
import com.ext.attendance.base.BaseFragment;
import com.ext.attendance.modules.home.adapter.EmployeeAttendanceRecyclerViewAdapter;
import com.ext.attendance.modules.home.models.CurrentMonthAttendanceResponseModel;
import com.ext.attendance.modules.home.models.EmployeeCheckInOutResponseModel;
import com.ext.attendance.modules.home.viewmodel.EmployeeAttendanceListViewModel;
import com.ext.attendance.prefs.Session;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private HomeActivity activity;
    private Session session;
    private Unbinder unbinder;
    private EmployeeAttendanceListViewModel mEmployeeAttendanceListViewModel;
    private EmployeeAttendanceRecyclerViewAdapter mEmployeeAttendanceRecyclerViewAdapter;
    private List<CurrentMonthAttendanceResponseModel.Data> mCurrentMonthDataList;

    @BindView(R.id.buttonCheckIn)
    Button checkInButton;
    @BindView(R.id.buttonCheckOut)
    Button checkOutButton;
    @BindView(R.id.buttonSubmit)
    Button submitButton;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int layoutRes() {
        return R.layout.home_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutRes(), container, false);
        unbinder = ButterKnife.bind(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        checkInButton.setOnClickListener(this);
        checkOutButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        mCurrentMonthDataList = new ArrayList<>();
        mEmployeeAttendanceRecyclerViewAdapter = new EmployeeAttendanceRecyclerViewAdapter(activity, mCurrentMonthDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mEmployeeAttendanceRecyclerViewAdapter);

        mEmployeeAttendanceListViewModel = ViewModelProviders.of(this).get(EmployeeAttendanceListViewModel.class);
        mEmployeeAttendanceListViewModel.getEmployeeAttendanceListViewModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<EmployeeCheckInOutResponseModel>() {
            @Override
            public void onChanged(EmployeeCheckInOutResponseModel employeeCheckInOutResponseModel) {
                if (employeeCheckInOutResponseModel != null && employeeCheckInOutResponseModel.getStatus() == 200) {
                    Toast.makeText(activity, "CheckInOut Success", Toast.LENGTH_LONG).show();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCheckIn:

                break;
            case R.id.buttonCheckOut:

                break;
            case R.id.buttonSubmit:
//                if (mEmployeeAttendanceListViewModel.getJsonObjectMutableLiveData().getValue() != null) {
//                    JsonObject jsonObject = new JsonObject();
//                    jsonObject.addProperty(AppKeysInterface.CHECKINLAT, "");
//                    jsonObject.addProperty(AppKeysInterface.CHECKINLNG, "");
//                    jsonObject.addProperty(AppKeysInterface.CHECKOUTLAT, "");
//                    jsonObject.addProperty(AppKeysInterface.CHECKOUTLNG, "");
//                    jsonObject.addProperty(AppKeysInterface.ADDRESS_CHECHIN, "");
//                    jsonObject.addProperty(AppKeysInterface.ADDRESS_CHECHOUT, "");
//                    jsonObject.addProperty(AppKeysInterface.EMPLOYEE_ID, "");
//
//                    mEmployeeAttendanceListViewModel.getJsonObjectMutableLiveData().setValue(jsonObject);
//                    mEmployeeAttendanceListViewModel.checkInCheckout();
//                } else {
//                    Toast.makeText(activity, "", Toast.LENGTH_LONG).show();
//                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (activity.toolbar != null) {
            activity.toolbar.setTitle("");
            activity.toolbarTitle.setText(AppUtils.capitalizeFirstLetter(activity.getResources().getString(R.string.dashboard)));
        }
        if (mEmployeeAttendanceListViewModel != null) {
            mEmployeeAttendanceListViewModel.clearViewModelData();
        }
    }
}
