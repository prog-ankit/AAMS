package com.example.attendanceacademia.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;


public class leaveModel {
    @SerializedName("leave_id")
    @Expose
    private String leave_id;

    @SerializedName("lenrollment")
    @Expose
    private String lenrollment;

    @SerializedName("leave_faculty")
    @Expose
    private String leave_faculty;

    @SerializedName("reason")
    @Expose
    private String reason;

    @SerializedName("leave_desc")
    @Expose
    private String leave_desc;

    @SerializedName("leave_start_date")
    @Expose
    private String leave_start_date;

    @SerializedName("leave_end_date")
    @Expose
    private String leave_end_date;

    @SerializedName("status")
    @Expose
    private String status;

    public leaveModel(String leave_id, String lenrollment, String leave_faculty, String reason, String leave_desc, String leave_start_date, String leave_end_date, String status) {
        this.leave_id = leave_id;
        this.lenrollment = lenrollment;
        this.leave_faculty = leave_faculty;
        this.reason = reason;
        this.leave_desc = leave_desc;
        this.leave_start_date = leave_start_date;
        this.leave_end_date = leave_end_date;
        this.status = status;
    }


    public String getLeave_id() {
        return leave_id;
    }

    public void setLeave_id(String leave_id) {
        this.leave_id = leave_id;
    }

    public String getLenrollment() {
        return lenrollment;
    }

    public void setLenrollment(String lenrollment) {
        this.lenrollment = lenrollment;
    }

    public String getLeave_faculty() {
        return leave_faculty;
    }

    public void setLeave_faculty(String leave_faculty) {
        this.leave_faculty = leave_faculty;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLeave_desc() {
        return leave_desc;
    }

    public void setLeave_desc(String leave_desc) {
        this.leave_desc = leave_desc;
    }

    public String getLeave_start_date() {
        return leave_start_date;
    }

    public void setLeave_start_date(String leave_start_date) {
        this.leave_start_date = leave_start_date;
    }

    public String getLeave_end_date() {
        return leave_end_date;
    }

    public void setLeave_end_date(String leave_end_date) {
        this.leave_end_date = leave_end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
