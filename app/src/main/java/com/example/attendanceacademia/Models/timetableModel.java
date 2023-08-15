package com.example.attendanceacademia.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class timetableModel {
    @SerializedName("timetable_id")
    @Expose
    private int timetable_id;

    @SerializedName("day_timetable")
    @Expose
    private int day_timetable;

    @SerializedName("start_time")
    @Expose
    private String start_time;

    @SerializedName("end_time")
    @Expose
    private String end_time;

    @SerializedName("type_lect")
    @Expose
    private String type_lect;

    @SerializedName("room_lectlab_no")
    @Expose
    private String room_lectlab_no;

    @SerializedName("subject_name")
    @Expose
    private String subject_name;

    @SerializedName("faculty_name")
    @Expose
    private String faculty_name;

    @SerializedName("div_table")
    @Expose
    private String div_table;

    @SerializedName("batch_table")
    @Expose
    private String batch_table;



    public timetableModel(int timetable_id, int day_timetable, String start_time, String end_time, String type_lect, String room_lectlab_no, String subject_name, String faculty_name, String div_table, String batch_table) {
        this.timetable_id = timetable_id;
        this.day_timetable = day_timetable;
        this.start_time = start_time;
        this.end_time = end_time;
        this.type_lect = type_lect;
        this.room_lectlab_no = room_lectlab_no;
        this.subject_name = subject_name;
        this.faculty_name = faculty_name;
        this.div_table = div_table;
        this.batch_table = batch_table;

    }

    public int getTimetable_id() {
        return timetable_id;
    }

    public int getDay_timetable() {
        return day_timetable;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getType_lect() {
        return type_lect;
    }

    public String getRoom_lectlab_no() {
        return room_lectlab_no;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public String getDiv_table() {
        return div_table;
    }

    public String getBatch_table() {
        return batch_table;
    }

}
