package com.example.attendanceacademia;

import com.example.attendanceacademia.Models.attendModel;
import com.example.attendanceacademia.Models.facultyModel;
import com.example.attendanceacademia.Models.leaveModel;
import com.example.attendanceacademia.Models.meetingModel;
import com.example.attendanceacademia.Models.proxyModel;
import com.example.attendanceacademia.Models.sltModel;
import com.example.attendanceacademia.Models.studentModel;
import com.example.attendanceacademia.Models.timetableModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface dashMyapi {

  // final static String BASE_URL = "http://10.0.2.2/AAMS/public/practice/";
    //final static String BASE_URL = "http://192.168.0.106/AAMS/public/practice/";//Ankit
      final static String BASE_URL = "http://192.168.0.105/AAMS/public/practice/";//Alok
    //final static String BASE_URL = "http://192.168.43.57/AAMS/public/practice/";//Sahil

    @GET("leaveDash.php")
    Call<List<leaveModel>> leaveModel();

    @GET("studentDash.php")
    Call<List<studentModel>> studentModel();

    @GET("attendDash.php")
    Call<List<attendModel>> attendModel();

    @GET("facultyDash.php")
    Call<List<facultyModel>> facultyModel();

    @GET("sltDash.php")
    Call<List<sltModel>> sltModel();

    @GET("meetingDash.php")
    Call<List<meetingModel>> meetingModel();

    @GET("timetableDash.php")
    Call<List<timetableModel>> timetableModel();

    @GET("proxyDash.php")
    Call<List<proxyModel>> proxyModel();
}
