package com.ext.attendance.modules.home.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract class AttendanceCurrentMonthResponseModel {

    @Expose
    @SerializedName("data")
    private List<Data> data;
    @Expose
    @SerializedName("currentDate")
    private String currentdate;
    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("message")
    private String message;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
