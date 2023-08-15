package com.example.attendanceacademia.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class studentModel {

    @SerializedName("enrollment")
    @Expose
    private String enrollment;

    @SerializedName("std_name")
    @Expose
    private String std_name;

    @SerializedName("std_pwd")
    @Expose
    private String std_pwd;

    @SerializedName("overall_attend_avg")
    @Expose
    private String overall_attend_avg;

    @SerializedName("contact_std")
    @Expose
    private String contact_std;

    @SerializedName("semester")
    @Expose
    private String semester;

    @SerializedName("div_std")
    @Expose
    private String div_std;

    @SerializedName("batch_std")
    @Expose
    private String batch_std;

    @SerializedName("std_profile")
    @Expose
    private String std_profile;


    @SerializedName("email_std")
    @Expose
    private String email_std;

    public studentModel(String enrollment, String std_name, String std_pwd, String overall_attend_avg, String contact_std, String semester, String div_std, String batch_std, String std_profile, String email_std) {
        this.enrollment = enrollment;
        this.std_name = std_name;
        this.std_pwd = std_pwd;
        this.overall_attend_avg = overall_attend_avg;
        this.contact_std = contact_std;
        this.semester = semester;
        this.div_std = div_std;
        this.batch_std = batch_std;
        this.std_profile = std_profile;
        this.email_std = email_std;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public String getStd_name() {
        return std_name;
    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
    }

    public String getStd_pwd() {
        return std_pwd;
    }

    public void setStd_pwd(String std_pwd) {
        this.std_pwd = std_pwd;
    }

    public String getOverall_attend_avg() {
        return overall_attend_avg;
    }

    public void setOverall_attend_avg(String overall_attend_avg) {
        this.overall_attend_avg = overall_attend_avg;
    }

    public String getContact_std() {
        return contact_std;
    }

    public void setContact_std(String contact_std) {
        this.contact_std = contact_std;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDiv_std() {
        return div_std;
    }

    public void setDiv_std(String div_std) {
        this.div_std = div_std;
    }

    public String getBatch_std() {
        return batch_std;
    }

    public void setBatch_std(String batch_std) {
        this.batch_std = batch_std;
    }

    public String getStd_profile() {
        return std_profile;
    }

    public void setStd_profile(String std_profile) {
        this.std_profile = std_profile;
    }

    public String getEmail_std() {
        return email_std;
    }

    public void setEmail_std(String email_std) {
        this.email_std = email_std;
    }
}
