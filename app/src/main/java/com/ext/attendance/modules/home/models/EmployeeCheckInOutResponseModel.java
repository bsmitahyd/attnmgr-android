package com.ext.attendance.modules.home.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeCheckInOutResponseModel {

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

    public static class Data {
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

        public int getV() {
            return V;
        }

        public void setV(int V) {
            this.V = V;
        }

        public String getUpdatedat() {
            return updatedat;
        }

        public void setUpdatedat(String updatedat) {
            this.updatedat = updatedat;
        }

        public String getCreatedat() {
            return createdat;
        }

        public void setCreatedat(String createdat) {
            this.createdat = createdat;
        }

        public String getEmployeeid() {
            return employeeid;
        }

        public void setEmployeeid(String employeeid) {
            this.employeeid = employeeid;
        }

        public int getCheckinTime() {
            return checkinTime;
        }

        public void setCheckinTime(int checkinTime) {
            this.checkinTime = checkinTime;
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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }
    }
}
