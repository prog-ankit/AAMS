package com.example.attendanceacademia.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Models.timetableModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class view_timetable extends AppCompatActivity {

    RecyclerView recyclerView;
    int day;
    String Sdays, Sdivision;
    List<timetableModel> filters = new ArrayList<timetableModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_timetable);

        Sdivision = select_view_timetable.selectdivision.Sdivision;
        Sdays = select_view_timetable.selectdays.Sdays;

        if(Sdays.equals("Monday")) day = 1;
        if(Sdays.equals("Tuesday")) day = 2;
        if(Sdays.equals("Wednesday")) day = 3;
        if(Sdays.equals("Thursday")) day = 4;
        if(Sdays.equals("Friday")) day = 5;


        recyclerView = findViewById(R.id.timetableRecycler);
        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sendData();
    }

    private void sendData() {
        dashMyapi timetableapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<timetableModel>> timetablecall = timetableapi.timetableModel();

        timetablecall.enqueue(new Callback<List<timetableModel>>() {
            @Override
            public void onResponse(Call<List<timetableModel>> call, Response<List<timetableModel>> response) {
                List<timetableModel> data = response.body();
                for (int i=0;i<data.size();i++) {
                    if (data.get(i).getDiv_table().equals(Sdivision) && data.get(i).getDay_timetable() == day) {
                        filters.add(data.get(i));
                    }
                }
                timetableAdapter timetableAdapter = new timetableAdapter(view_timetable.this,filters);
                recyclerView.setAdapter(timetableAdapter);
            }

            @Override
            public void onFailure(Call<List<timetableModel>> call, Throwable t) {

            }
        });
    }
}