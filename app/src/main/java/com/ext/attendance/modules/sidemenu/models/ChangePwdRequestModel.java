package com.ext.attendance.modules.sidemenu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePwdRequestModel {
    @Expose
    @SerializedName("newpassword")
    private String newpassword;
    @Expose
    @SerializedName("currentpass")
    private String currentpass;
    @Expose
    @SerializedName("employeeId")
    private String employeeid;


    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getCurrentpass() {
        return currentpass;
    }

    public void setCurrentpass(String currentpass) {
        this.currentpass = currentpass;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }
}
