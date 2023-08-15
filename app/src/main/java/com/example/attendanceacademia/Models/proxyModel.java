package com.example.attendanceacademia.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.Date;


public class proxyModel {
    @SerializedName("proxy_id")
    @Expose
    private int proxy_id;

    @SerializedName("from_faculty")
    @Expose
    private int from_faculty;

    @SerializedName("to_faculty")
    @Expose
    private String to_faculty;

    @SerializedName("date_proxy")
    @Expose
    private String date_proxy;

    @SerializedName("from_time")
    @Expose
    private String from_time;

    @SerializedName("to_time")
    @Expose
    private String to_time;

    @SerializedName("room_no")
    @Expose
    private String room_no;

    @SerializedName("desc_proxy")
    @Expose
    private String desc_proxy;

    public proxyModel(int proxy_id, int from_faculty, String to_faculty, String date_proxy, String from_time, String to_time, String room_no, String desc_proxy) {
        this.proxy_id = proxy_id;
        this.from_faculty = from_faculty;
        this.to_faculty = to_faculty;
        this.date_proxy = date_proxy;
        this.from_time = from_time;
        this.to_time = to_time;
        this.room_no = room_no;
        this.desc_proxy = desc_proxy;
    }

    public int getProxy_id() {
        return proxy_id;
    }

    public int getFrom_faculty() {
        return from_faculty;
    }

    public String getTo_faculty() {
        return to_faculty;
    }

    public String getDate_proxy() {
        return date_proxy;
    }

    public String getFrom_time() {
        return from_time;
    }

    public String getTo_time() {
        return to_time;
    }

    public String getRoom_no() {
        return room_no;
    }

    public String getDesc_proxy() {
        return desc_proxy;
    }
}
