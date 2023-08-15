package com.example.attendanceacademia.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class meetingModel {

    @SerializedName("meet_id")
    @Expose
    private String meet_id;

    @SerializedName("faculty_id")
    @Expose
    private String faculty_id;

    @SerializedName("topic_meet")
    @Expose
    private String topic_meet;

    @SerializedName("room_no")
    @Expose
    private String room_no;

    @SerializedName("date_meet")
    @Expose
    private String date_meet;

    @SerializedName("desc_meet")
    @Expose
    private String desc_meet;

    @SerializedName("time_meet")
    @Expose
    private String time_meet;

    public meetingModel(String meet_id, String faculty_id, String topic_meet, String room_no, String date_meet, String desc_meet, String time_meet) {
        this.meet_id = meet_id;
        this.faculty_id = faculty_id;
        this.topic_meet = topic_meet;
        this.room_no = room_no;
        this.date_meet = date_meet;
        this.desc_meet = desc_meet;
        this.time_meet = time_meet;
    }

    public String getMeet_id() {
        return meet_id;
    }

    public void setMeet_id(String meet_id) {
        this.meet_id = meet_id;
    }

    public String getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(String faculty_id) {
        this.faculty_id = faculty_id;
    }

    public String getTopic_meet() {
        return topic_meet;
    }

    public void setTopic_meet(String topic_meet) {
        this.topic_meet = topic_meet;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getDate_meet() {
        return date_meet;
    }

    public void setDate_meet(String date_meet) {
        this.date_meet = date_meet;
    }

    public String getDesc_meet() {
        return desc_meet;
    }

    public void setDesc_meet(String desc_meet) {
        this.desc_meet = desc_meet;
    }

    public String getTime_meet() {
        return time_meet;
    }

    public void setTime_meet(String time_meet) {
        this.time_meet = time_meet;
    }
}
