package com.example.attendanceacademia.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class facultyModel {

    @SerializedName("faculty_id")
    @Expose
    private String faculty_id;

    @SerializedName("faculty_name")
    @Expose
    private String faculty_name;

    @SerializedName("faculty_pwd")
    @Expose
    private String faculty_pwd;

    @SerializedName("subject_name")
    @Expose
    private String subject_name;

    @SerializedName("contact_faculty")
    @Expose
    private String contact_faculty;

    @SerializedName("email_faculty")
    @Expose
    private String email_faculty;

    @SerializedName("faculty_profile")
    @Expose
    private String faculty_profile;

    @SerializedName("role")
    @Expose
    private String role;

    public facultyModel(String faculty_id, String faculty_name, String faculty_pwd, String subject_name, String contact_faculty, String email_faculty, String faculty_profile, String role) {
        this.faculty_id = faculty_id;
        this.faculty_name = faculty_name;
        this.faculty_pwd = faculty_pwd;
        this.subject_name = subject_name;
        this.contact_faculty = contact_faculty;
        this.email_faculty = email_faculty;
        this.faculty_profile = faculty_profile;
        this.role = role;
    }

    public String getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(String faculty_id) {
        this.faculty_id = faculty_id;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public String getFaculty_pwd() {
        return faculty_pwd;
    }

    public void setFaculty_pwd(String faculty_pwd) {
        this.faculty_pwd = faculty_pwd;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getContact_faculty() {
        return contact_faculty;
    }

    public void setContact_faculty(String contact_faculty) {
        this.contact_faculty = contact_faculty;
    }

    public String getEmail_faculty() {
        return email_faculty;
    }

    public void setEmail_faculty(String email_faculty) {
        this.email_faculty = email_faculty;
    }

    public String getFaculty_profile() {
        return faculty_profile;
    }

    public void setFaculty_profile(String faculty_profile) {
        this.faculty_profile = faculty_profile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
