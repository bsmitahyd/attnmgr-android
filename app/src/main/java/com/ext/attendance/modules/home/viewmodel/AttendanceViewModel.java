package com.ext.attendance.modules.home.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.ext.attendance.MyApplication;
import com.ext.attendance.base.BaseViewModel;
import com.ext.attendance.base.CommonNavigator;
import com.ext.attendance.modules.home.models.CurrentMonthAttendanceResponseModel;
import com.ext.attendance.modules.home.models.EmployeeCheckInOutResponseModel;
import com.ext.attendance.networkcall.MyApiService;
import com.ext.attendance.prefs.Session;
import com.google.gson.JsonObject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class AttendanceViewModel extends BaseViewModel<CommonNavigator> {
    private Disposable mDisposable = null;
    private MutableLiveData<EmployeeCheckInOutResponseModel> employeeCheckInOutResponseModelMutableLiveData;
    private MutableLiveData<JsonObject> jsonObjectMutableLiveData;
    private MutableLiveData<CurrentMonthAttendanceResponseModel> currentMonthAttendanceResponseModelMutableLiveData;

    public AttendanceViewModel() {
        employeeCheckInOutResponseModelMutableLiveData = new MutableLiveData<>();
        jsonObjectMutableLiveData = new MutableLiveData<>();
        currentMonthAttendanceResponseModelMutableLiveData = new MutableLiveData<>();
        loadEmployeeAttendanceListData();
    }

    private void loadEmployeeAttendanceListData() {
        currentMonthAttendanceResponseModelMutableLiveData.setValue(new CurrentMonthAttendanceResponseModel());
        employeeCheckInOutResponseModelMutableLiveData.setValue(new EmployeeCheckInOutResponseModel());
        jsonObjectMutableLiveData.setValue(new JsonObject());
    }

    public void clearViewModelData() {
        currentMonthAttendanceResponseModelMutableLiveData.setValue(null);
        employeeCheckInOutResponseModelMutableLiveData.setValue(null);
        jsonObjectMutableLiveData.setValue(null);
    }

    public MutableLiveData<CurrentMonthAttendanceResponseModel> getCurrentMonthAttendanceResponseModelMutableLiveData() {
        return currentMonthAttendanceResponseModelMutableLiveData;
    }

    public MutableLiveData<EmployeeCheckInOutResponseModel> getEmployeeCheckInOutResponseModelMutableLiveData() {
        return employeeCheckInOutResponseModelMutableLiveData;
    }

    public MutableLiveData<JsonObject> getJsonObjectMutableLiveData() {
        return jsonObjectMutableLiveData;
    }

    public void checkInCheckout() {
        getNavigator().loadProgressBar(true);
        mDisposable = MyApiService.getService().employeeAttendanceCheckInOutApiCall(jsonObjectMutableLiveData.getValue()).
                subscribe(new Consumer<EmployeeCheckInOutResponseModel>() {
                    @Override
                    public void accept(EmployeeCheckInOutResponseModel employeeCheckInOutResponseModel) throws Exception {
                        getNavigator().loadProgressBar(false);
                        if (employeeCheckInOutResponseModel.getStatus() == 200) {
                            mDisposable.dispose();
                            employeeCheckInOutResponseModelMutableLiveData.setValue(employeeCheckInOutResponseModel);
                        } else {
                            getNavigator().loadProgressBar(false);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getNavigator().loadProgressBar(false);
                        mDisposable.dispose();
                    }
                });
    }

    public void getCurrentMonthAttendance() {

        getNavigator().loadProgressBar(true);
        Session session = new Session(MyApplication.getInstance().getApplicationContext());
        mDisposable = MyApiService.getService().getCurrentMonthAttendanceListByEmpId(session.getEmployeeId())
                .subscribe(new Consumer<CurrentMonthAttendanceResponseModel>() {
                    @Override
                    public void accept(CurrentMonthAttendanceResponseModel currentMonthAttendanceResponseModel) throws Exception {
                        getNavigator().loadProgressBar(false);
                        if (currentMonthAttendanceResponseModel.getStatus() == 200) {
                            currentMonthAttendanceResponseModelMutableLiveData.setValue(currentMonthAttendanceResponseModel);
                            mDisposable.dispose();
                        } else {
                            getNavigator().loadProgressBar(false);
                            mDisposable.dispose();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getNavigator().loadProgressBar(false);
                        getNavigator().handleError(throwable);
                    }
                });
    }
}
