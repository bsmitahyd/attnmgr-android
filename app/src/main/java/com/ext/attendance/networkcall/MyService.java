package com.ext.attendance.networkcall;

import com.ext.attendance.base.GeneralResponse;
import com.ext.attendance.modules.home.models.CurrentMonthAttendanceResponseModel;
import com.ext.attendance.modules.home.models.EmployeeCheckInOutResponseModel;
import com.ext.attendance.modules.login.models.LoginResponseModel;
import com.ext.attendance.modules.sidemenu.models.ChangePwdResponseModel;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyService {

    //TODO Create Employee
    @POST("employee/createEmployee")
    Observable<GeneralResponse> employeeRegisterApiCall(@Body JsonObject jsonObject);

    //TODO Employee Login
    @POST("employee/login")
    Observable<LoginResponseModel> employeeLoginApiCall(@Body JsonObject jsonObject);

    // TODO Attendance Checkin - Checkout
    @POST("attendance/checkInCheckout")
    Observable<EmployeeCheckInOutResponseModel> employeeAttendanceCheckInOutApiCall(@Body JsonObject jsonObject);

    // TODO Attendance list for current month
    @GET("attendance/getCurrentMonthAttendance")
    Observable<CurrentMonthAttendanceResponseModel> getCurrentMonthAttendanceData(@Query("employeeId") String employeeId);

    //TODO Employee Login
    @POST("employee/changePassword")
    Observable<ChangePwdResponseModel> changePasswordApiCall(@Body JsonObject jsonObject);

}