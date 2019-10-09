package com.ext.attendance.modules.login.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {


    @Expose
    @SerializedName("data")
    private Data data;
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("status")
    private int status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Data {
        @Expose
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("idproofurl")
        private String idproofurl;
        @Expose
        @SerializedName("mobile")
        private String mobile;
        @Expose
        @SerializedName("pandit_id")
        private int pandit_id = 0;

        @Expose
        @SerializedName("yajman_id")
        private int yajman_id = 0;

        @Expose
        @SerializedName("fname")
        private String fname;

        @Expose
        @SerializedName("lname")
        private String lname;

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public int getYajman_id() {
            return yajman_id;
        }

        public void setYajman_id(int yajman_id) {
            this.yajman_id = yajman_id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getIdproofurl() {
            return idproofurl;
        }

        public void setIdproofurl(String idproofurl) {
            this.idproofurl = idproofurl;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getPandit_id() {
            return pandit_id;
        }

        public void setPandit_id(int pandit_id) {
            this.pandit_id = pandit_id;
        }
    }
}
