package com.ext.attendance.networkcall;


import com.ext.attendance.base.GeneralResponse;
import com.ext.attendance.modules.login.models.LoginResponseModel;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public abstract class MyApiService {

    private static MyApiService service;

    private static void setServiceType() {
        service = new CloudDataService();
    }

    public static MyApiService getService() {
        if (service == null) {
            setServiceType();
        }

        return service;
    }

    // employeeRegisterApiCall
    public abstract Observable<GeneralResponse> employeeRegisterApiCall(@Body JsonObject jsonObject);

    // employeeLoginApiCall
    public abstract Observable<LoginResponseModel> employeeLoginApiCall(@Body JsonObject jsonObject);

  }