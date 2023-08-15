package com.example.attendanceacademia.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class sltModel {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("type_seminar_lect_test")
    @Expose
    private String type_seminar_lect_test;

    @SerializedName("date_test_lect")
    @Expose
    private String date_test_lect;

    @SerializedName("topic_test_lect")
    @Expose
    private String topic_test_lect;

    @SerializedName("lecturer_name")
    @Expose
    private String lecturer_name;

    @SerializedName("faculty_id")
    @Expose
    private String faculty_id;

    @SerializedName("time_test_lect")
    @Expose
    private String time_test_lect;

    @SerializedName("desc_lecture")
    @Expose
    private String desc_lecture;

    public sltModel(String id, String type_seminar_lect_test, String date_test_lect, String topic_test_lect, String lecturer_name, String faculty_id, String time_test_lect, String desc_lecture) {
        this.id = id;
        this.type_seminar_lect_test = type_seminar_lect_test;
        this.date_test_lect = date_test_lect;
        this.topic_test_lect = topic_test_lect;
        this.lecturer_name = lecturer_name;
        this.faculty_id = faculty_id;
        this.time_test_lect = time_test_lect;
        this.desc_lecture = desc_lecture;
    }

    public String getId() {
        return id;
    }

    public String getType_seminar_lect_test() {
        return type_seminar_lect_test;
    }

    public String getDate_test_lect() {
        return date_test_lect;
    }

    public String getTopic_test_lect() {
        return topic_test_lect;
    }

    public String getLecturer_name() {
        return lecturer_name;
    }

    public String getFaculty_id() {
        return faculty_id;
    }

    public String getTime_test_lect() {
        return time_test_lect;
    }

    public String getDesc_lecture() {
        return desc_lecture;
    }
}
