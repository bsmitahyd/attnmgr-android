package com.ext.attendance.modules.home.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.ext.attendance.base.BaseViewModel;
import com.ext.attendance.modules.home.models.CurrentMonthAttendanceResponseModel;
import com.ext.attendance.modules.home.view.CurrentMonthAttendanceNavigator;

import io.reactivex.disposables.Disposable;

public class CurrentMonthAttendanceViewModel extends BaseViewModel<CurrentMonthAttendanceNavigator> {
    private Disposable mDisposable = null;
    private MutableLiveData<CurrentMonthAttendanceResponseModel> currentMonthAttendanceResponseModelMutableLiveData;

    public CurrentMonthAttendanceViewModel(){
        currentMonthAttendanceResponseModelMutableLiveData=new MutableLiveData<>();
    }
}
