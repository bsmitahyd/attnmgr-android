package com.ext.attendance.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralResponse {
    @SerializedName("statusCode")
    @Expose
    int statusCode = 0;

    @SerializedName("message")
    @Expose
    String message = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("status")
    @Expose
    String status = "";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
