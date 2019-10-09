package com.ext.attendance.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
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
    @SerializedName("companyId")
    private String companyid;
    @Expose
    @SerializedName("branchId")
    private String branchid;
    @Expose
    @SerializedName("employeeId")
    private String employeeid;
    @Expose
    @SerializedName("dob")
    private String dob;
    @Expose
    @SerializedName("reportingTo")
    private String reportingto;
    @Expose
    @SerializedName("designation")
    private String designation;
    @Expose
    @SerializedName("dateofjoining")
    private String dateofjoining;
    @Expose
    @SerializedName("department")
    private String department;
    @Expose
    @SerializedName("panNo")
    private String panno;
    @Expose
    @SerializedName("device_type")
    private String deviceType;
    @Expose
    @SerializedName("device_token")
    private String deviceToken;
    @Expose
    @SerializedName("device_id")
    private String deviceId;
    @Expose
    @SerializedName("emergency_contact")
    private String emergencyContact;
    @Expose
    @SerializedName("contact")
    private String contact;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("username")
    private String username;
    @Expose
    @SerializedName("lastname")
    private String lastname;
    @Expose
    @SerializedName("middlename")
    private String middlename;
    @Expose
    @SerializedName("name")
    private String name;
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

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getReportingto() {
        return reportingto;
    }

    public void setReportingto(String reportingto) {
        this.reportingto = reportingto;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDateofjoining() {
        return dateofjoining;
    }

    public void setDateofjoining(String dateofjoining) {
        this.dateofjoining = dateofjoining;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
}
