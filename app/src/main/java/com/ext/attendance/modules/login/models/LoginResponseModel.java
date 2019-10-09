package com.ext.attendance.modules.login.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {


    @Expose
    @SerializedName("data")
    private Data data;
    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("message")
    private String message;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
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
