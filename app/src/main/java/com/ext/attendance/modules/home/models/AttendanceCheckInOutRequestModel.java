package com.ext.attendance.modules.home.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class AttendanceCheckInOutRequestModel {

    @Expose
    @SerializedName("address_chechout")
    private String addressChechout;
    @Expose
    @SerializedName("checkoutlng")
    private double checkoutlng;
    @Expose
    @SerializedName("checkoutlat")
    private double checkoutlat;
    @Expose
    @SerializedName("employeeId")
    private String employeeid;
    @Expose
    @SerializedName("address_chechin")
    private String addressChechin;
    @Expose
    @SerializedName("checkinlng")
    private double checkinlng;
    @Expose
    @SerializedName("checkinlat")
    private double checkinlat;

    public String getAddressChechout() {
        return addressChechout;
    }

    public void setAddressChechout(String addressChechout) {
        this.addressChechout = addressChechout;
    }

    public double getCheckoutlng() {
        return checkoutlng;
    }

    public void setCheckoutlng(double checkoutlng) {
        this.checkoutlng = checkoutlng;
    }

    public double getCheckoutlat() {
        return checkoutlat;
    }

    public void setCheckoutlat(double checkoutlat) {
        this.checkoutlat = checkoutlat;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getAddressChechin() {
        return addressChechin;
    }

    public void setAddressChechin(String addressChechin) {
        this.addressChechin = addressChechin;
    }

    public double getCheckinlng() {
        return checkinlng;
    }

    public void setCheckinlng(double checkinlng) {
        this.checkinlng = checkinlng;
    }

    public double getCheckinlat() {
        return checkinlat;
    }

    public void setCheckinlat(double checkinlat) {
        this.checkinlat = checkinlat;
    }
}
