package com.ext.attendance.modules.home.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @Expose
    @SerializedName("checkoutlng")
    private double checkoutlng;
    @Expose
    @SerializedName("checkoutlat")
    private double checkoutlat;
    @Expose
    @SerializedName("checkout_time")
    private int checkoutTime;
    @Expose
    @SerializedName("address_chechout")
    private String addressChechout;
    @Expose
    @SerializedName("__v")
    private int V;
    @Expose
    @SerializedName("updatedAt")
    private String updatedat;
    @Expose
    @SerializedName("createdAt")
    private String createdat;
    @Expose
    @SerializedName("employeeId")
    private String employeeid;
    @Expose
    @SerializedName("checkin_time")
    private int checkinTime;
    @Expose
    @SerializedName("address_chechin")
    private String addressChechin;
    @Expose
    @SerializedName("checkinlng")
    private double checkinlng;
    @Expose
    @SerializedName("checkinlat")
    private double checkinlat;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("_id")
    private String Id;
}
