package com.ext.attendance.modules.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ext.attendance.R;
import com.ext.attendance.apputils.AppKeysInterface;
import com.ext.attendance.apputils.AppUtils;
import com.ext.attendance.base.BaseFragment;
import com.ext.attendance.base.CommonNavigator;
import com.ext.attendance.modules.home.adapter.EmployeeAttendanceRecyclerViewAdapter;
import com.ext.attendance.modules.home.models.CurrentMonthAttendanceResponseModel;
import com.ext.attendance.modules.home.models.EmployeeCheckInOutResponseModel;
import com.ext.attendance.modules.home.viewmodel.AttendanceViewModel;
import com.ext.attendance.prefs.Session;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;


public class HomeFragment extends BaseFragment implements View.OnClickListener, CommonNavigator {
    private HomeActivity activity;
    private Session session;
    private Unbinder unbinder;
    private AttendanceViewModel mAttendanceViewModel;
    private EmployeeAttendanceRecyclerViewAdapter mEmployeeAttendanceRecyclerViewAdapter;
    private List<CurrentMonthAttendanceResponseModel.Data> mCurrentMonthDataList;

    @BindView(R.id.buttonCheckIn)
    Button checkInButton;
    @BindView(R.id.buttonCheckOut)
    Button checkOutButton;
    @BindView(R.id.tvAttMonth)
    TextView attMonthTextView;
    @BindView(R.id.tvCurrentDate)
    TextView currentDateTextView;
    @BindView(R.id.tvCheckInTime)
    TextView checkInTimeTextView;
    @BindView(R.id.tvCheckOutTime)
    TextView checkOutTimeTextView;
    @BindView(R.id.recyclerViewAtt)
    RecyclerView recyclerViewAtt;
    @BindView(R.id.api_progress_bar)
    ProgressBar progressBar;

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
        checkInButton.setOnClickListener(this);
        checkOutButton.setOnClickListener(this);

        recyclerViewAtt.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewAtt.setHasFixedSize(true);


        mCurrentMonthDataList = new ArrayList<>();
        mEmployeeAttendanceRecyclerViewAdapter = new EmployeeAttendanceRecyclerViewAdapter(activity, mCurrentMonthDataList);
        recyclerViewAtt.setAdapter(mEmployeeAttendanceRecyclerViewAdapter);

        mAttendanceViewModel = ViewModelProviders.of(this).get(AttendanceViewModel.class);
        mAttendanceViewModel.setNavigator(this);
        mAttendanceViewModel.getCurrentMonthAttendance();
        mAttendanceViewModel.getCurrentMonthAttendanceResponseModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<CurrentMonthAttendanceResponseModel>() {
            @Override
            public void onChanged(CurrentMonthAttendanceResponseModel currentMonthAttendanceResponseModel) {
                if (currentMonthAttendanceResponseModel != null && currentMonthAttendanceResponseModel.getStatus() == 200) {
                    setViewData(currentMonthAttendanceResponseModel);
                }

            }
        });
        mAttendanceViewModel.getEmployeeCheckInOutResponseModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<EmployeeCheckInOutResponseModel>() {
            @Override
            public void onChanged(EmployeeCheckInOutResponseModel employeeCheckInOutResponseModel) {
                if (employeeCheckInOutResponseModel != null && employeeCheckInOutResponseModel.getStatus() == 200) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Write whatever to want to do after delay specified (1 sec)
                            mAttendanceViewModel.getCurrentMonthAttendance();
                        }
                    }, 500);
                }

            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void setViewData(CurrentMonthAttendanceResponseModel currentMonthAttendanceResponseModel) {
        mCurrentMonthDataList = new ArrayList<>();
        attMonthTextView.setText("Attendance For Month of " + AppUtils.dateIntoMonth(currentMonthAttendanceResponseModel.getCurrentdate()) + " " +
                AppUtils.dateIntoYear(currentMonthAttendanceResponseModel.getCurrentdate()));
        currentDateTextView.setText(currentMonthAttendanceResponseModel.getCurrentdate());

        if (currentMonthAttendanceResponseModel.getData().size() > 0) {
            if (currentMonthAttendanceResponseModel.getData().get(0).getDate().
                    equalsIgnoreCase(currentMonthAttendanceResponseModel.getCurrentdate())) {
                if (currentMonthAttendanceResponseModel.getData().get(0).getCheckinTime() != 0) {
                    checkInButton.setEnabled(false);
                    checkInButton.setClickable(false);
                    checkInButton.setAlpha(0.5f);
                    checkInButton.setTextColor(activity.getResources().getColor(R.color.grey_40));

                    checkInTimeTextView.setVisibility(View.VISIBLE);
                    checkInTimeTextView.setText(String.valueOf(AppUtils.getFormattedTime(currentMonthAttendanceResponseModel.getData().get(0).getCheckinTime()).toUpperCase()));
                }
                if (currentMonthAttendanceResponseModel.getData().get(0).getCheckoutTime() != 0) {
                    checkOutButton.setEnabled(false);
                    checkOutButton.setClickable(false);
                    checkOutButton.setAlpha(0.5f);
                    checkOutButton.setTextColor(activity.getResources().getColor(R.color.grey_40));
                    checkOutTimeTextView.setVisibility(View.VISIBLE);
                    checkOutTimeTextView.setText(String.valueOf(AppUtils.getFormattedTime(currentMonthAttendanceResponseModel.getData().get(0).getCheckoutTime()).toUpperCase()));
                }

                if (currentMonthAttendanceResponseModel.getData().size() > 1) {
                    mCurrentMonthDataList = new ArrayList<>();
                    for (int i = 1; i < currentMonthAttendanceResponseModel.getData().size(); i++) {
                        mCurrentMonthDataList.add(currentMonthAttendanceResponseModel.getData().get(i));
                    }
                    mEmployeeAttendanceRecyclerViewAdapter = new EmployeeAttendanceRecyclerViewAdapter(activity, mCurrentMonthDataList);
                    recyclerViewAtt.setAdapter(mEmployeeAttendanceRecyclerViewAdapter);
                }
            } else {

                mCurrentMonthDataList.addAll(currentMonthAttendanceResponseModel.getData());
                mEmployeeAttendanceRecyclerViewAdapter = new EmployeeAttendanceRecyclerViewAdapter(activity, mCurrentMonthDataList);
                recyclerViewAtt.setAdapter(mEmployeeAttendanceRecyclerViewAdapter);
            }

        }

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
                Toast.makeText(activity, "LatLng:" + activity.latitude + " " + activity.longitude, Toast.LENGTH_SHORT).show();
                if (mAttendanceViewModel.getJsonObjectMutableLiveData() != null) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(AppKeysInterface.CHECKINLAT, activity.latitude);
                    jsonObject.addProperty(AppKeysInterface.CHECKINLNG, activity.longitude);
                    jsonObject.addProperty(AppKeysInterface.ADDRESS_CHECHIN, activity.address);
                    jsonObject.addProperty(AppKeysInterface.EMPLOYEE_ID, session.getEmployeeId());

                    Timber.d("CheckIn_DATA:%s", new Gson().toJson(jsonObject));
                    mAttendanceViewModel.getJsonObjectMutableLiveData().setValue(jsonObject);
                    mAttendanceViewModel.checkInCheckout();
                }
                break;
            case R.id.buttonCheckOut:
                Toast.makeText(activity, "LatLng:" + activity.latitude + " " + activity.longitude, Toast.LENGTH_SHORT).show();
                if (mAttendanceViewModel.getJsonObjectMutableLiveData() != null) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(AppKeysInterface.CHECKOUTLAT, activity.latitude);
                    jsonObject.addProperty(AppKeysInterface.CHECKOUTLNG, activity.longitude);
                    jsonObject.addProperty(AppKeysInterface.ADDRESS_CHECHOUT, activity.address);
                    jsonObject.addProperty(AppKeysInterface.EMPLOYEE_ID, session.getEmployeeId());

                    Timber.d("CheckOut_DATA:%s", new Gson().toJson(jsonObject));
                    mAttendanceViewModel.getJsonObjectMutableLiveData().setValue(jsonObject);
                    mAttendanceViewModel.checkInCheckout();
                }
                break;

            default:
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (activity.toolbar != null) {
            activity.toolbar.setTitle(R.string.attendance);
            activity.toolbarTitle.setText(AppUtils.capitalizeFirstLetter(activity.getResources().getString(R.string.dashboard)));
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
    public void handleError(Throwable throwable) {

    }

    @Override
    public void loadProgressBar(boolean showProgress) {
        if (progressBar != null) {
            progressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        }

    }


}
