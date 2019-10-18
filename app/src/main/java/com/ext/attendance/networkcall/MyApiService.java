package com.ext.attendance.networkcall;


import com.ext.attendance.base.GeneralResponse;
import com.ext.attendance.modules.home.models.CurrentMonthAttendanceResponseModel;
import com.ext.attendance.modules.home.models.EmployeeCheckInOutResponseModel;
import com.ext.attendance.modules.login.models.LoginResponseModel;
import com.ext.attendance.modules.sidemenu.models.ChangePwdResponseModel;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Query;

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

    // employeeAttendanceCheckInOutApiCall
    public abstract Observable<EmployeeCheckInOutResponseModel> employeeAttendanceCheckInOutApiCall(@Body JsonObject jsonObject);

    //getCurrentMonthAttendanceList
    public abstract  Observable<CurrentMonthAttendanceResponseModel> getCurrentMonthAttendanceListByEmpId(@Query("employeeId") String employeeId);

    //changePasswordApiCall

    public abstract Observable<ChangePwdResponseModel> changePasswordApiCall(@Body JsonObject jsonObject);

}