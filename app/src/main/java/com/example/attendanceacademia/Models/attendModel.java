package com.example.attendanceacademia.Models;

import com.google.gson.annotations.*;

public class attendModel {

    @SerializedName("attend_id")
    @Expose
    private String attend_id;

    @SerializedName("enrollment")
    @Expose
    private String enrollment;

    @SerializedName("div_attend")
    @Expose
    private String div_attend;

    @SerializedName("batch_attend")
    @Expose
    private String batch_attend;

    @SerializedName("attend")
    @Expose
    private int attend;

    @SerializedName("faculty_id")
    @Expose
    private String faculty_id;

    @SerializedName("faculty_name")
    @Expose
    private String faculty_name;

    @SerializedName("subject_name")
    @Expose
    private String subject_name;

    @SerializedName("type_lect")
    @Expose
    private String type_lect;

    @SerializedName("date_attend")
    @Expose
    private String date_attend;

    public attendModel(String attend_id, String enrollment, String div_attend, String batch_attend, int attend, String faculty_id, String faculty_name, String subject_name, String type_lect, String date_attend) {
        this.attend_id = attend_id;
        this.enrollment = enrollment;
        this.div_attend = div_attend;
        this.batch_attend = batch_attend;
        this.attend = attend;
        this.faculty_id = faculty_id;
        this.faculty_name = faculty_name;
        this.subject_name = subject_name;
        this.type_lect = type_lect;
        this.date_attend = date_attend;
    }

    public String getAttend_id() {
        return attend_id;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public String getDiv_attend() {
        return div_attend;
    }

    public String getBatch_attend() {
        return batch_attend;
    }

    public int getAttend() {
        return attend;
    }

    public String getFaculty_id() {
        return faculty_id;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public String getType_lect() {
        return type_lect;
    }

    public String getDate_attend() {
        return date_attend;
    }
}