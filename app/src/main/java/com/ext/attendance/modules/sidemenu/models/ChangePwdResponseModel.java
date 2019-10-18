package com.ext.attendance.modules.sidemenu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePwdResponseModel {
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("statusCode")
    private int statusCode;
    @Expose
    @SerializedName("status")
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
