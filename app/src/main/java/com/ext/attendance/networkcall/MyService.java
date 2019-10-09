package com.ext.attendance.networkcall;

import com.ext.attendance.base.GeneralResponse;
import com.ext.attendance.modules.login.models.LoginResponseModel;
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

    //TODO Attendance Checkin - Checkout
   // @POST("attendance/checkInCheckout")
   // Observable<AttendanceCheckInOutResponseModel> attendanceCheckInOutApiCall(@Body JsonObject jsonObject);

    // TODO Attendance list for current month
   // @GET("attendance/getCurrentMonthAttendance")
  //  Observable<AttendanceCurrentMonthResponseModel> attendanceCurrentMListApiCall(@Query("employeeId") int employeeId);
}