package com.ext.attendance.networkcall;

import com.ext.attendance.base.GeneralResponse;
import com.ext.attendance.modules.login.models.LoginResponseModel;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;


public class CloudDataService extends MyApiService {

    //Holds service instance
    private MyService service;

    // Constructor
    CloudDataService() {
        service = new RetrofitHelper().getMyService();
    }


    @Override
    public Observable<GeneralResponse> employeeRegisterApiCall(JsonObject jsonObject) {
        return service.employeeRegisterApiCall(jsonObject)
                .subscribeOn(Schedulers.io()) // “work” on io thread
                .observeOn(AndroidSchedulers.mainThread()); // “listen” on UIThread;
    }

    @Override
    public Observable<LoginResponseModel> employeeLoginApiCall(JsonObject jsonObject) {
        return service.employeeLoginApiCall(jsonObject)
                .subscribeOn(Schedulers.io()) // “work” on io thread
                .observeOn(AndroidSchedulers.mainThread()); // “listen” on UIThread;
    }
}