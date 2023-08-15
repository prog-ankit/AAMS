package com.example.attendanceacademia.Student;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Models.attendModel;
import com.example.attendanceacademia.Models.facultyModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.Student.std_dash;
import com.example.attendanceacademia.dashMyapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class view_attendance extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner_subject;
    String enroll,facultyName,subject;
    TextView test,Sname,Fname,Sattendence;
    String name;
    int count=0, total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);


        spinner_subject = (Spinner) findViewById(R.id.subject);
        spinner_subject.setOnItemSelectedListener(this);

        Sname = (TextView) findViewById(R.id.subject_name);
        Fname = (TextView) findViewById(R.id.faculty_name);
        Sattendence = (TextView) findViewById(R.id.subject_attendance);


        enroll = std_dash.enrollment;
        Sname.setText("");
        Fname.setText("");
        Sattendence.setText("");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Sname.setText("");
        Fname.setText("");
        Sattendence.setText("");
        showdata(parent, position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //user defined method
    public void showdata(AdapterView<?> parent, int position){


        String spinnerSubject = parent.getItemAtPosition(position).toString();

        //Call facultyModel
        dashMyapi facultyapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<facultyModel>> facultycall = facultyapi.facultyModel();
        facultycall.enqueue(new Callback<List<facultyModel>>() {
            @Override
            public void onResponse(Call<List<facultyModel>> call, Response<List<facultyModel>> response) {
                List<facultyModel> data1 = response.body();
                for (int i =0 ;i<data1.size();i++){
                    String tempSub = data1.get(i).getSubject_name();
                    if(tempSub.equals(spinnerSubject)){
                        facultyName = data1.get(i).getFaculty_name();
                        Fname.setText(facultyName);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<facultyModel>> call, Throwable t) {

            }
        });

        //Call attendModel
        dashMyapi attendapi = Connection.retrofit.create(dashMyapi.class);
        Call <List<attendModel>> attendcall = attendapi.attendModel();
        attendcall.enqueue(new Callback<List<attendModel>>() {

            @Override
            public void onResponse(Call<List<attendModel>> call, Response<List<attendModel>> response) {
                List<attendModel> data2 = response.body();


                for(int j = 0; j < data2.size(); j++){
                    String temp = data2.get(j).getEnrollment();
                    if(temp.equals(enroll)){
                        String sub = data2.get(j).getSubject_name();
                        if(sub.equals(spinnerSubject)){
                            total++;
                            subject = sub;
                            int n = data2.get(j).getAttend();
                            while(n==1){
                                count++;
                                n=0;
                            }
                        }
                    }
                }
                String a = count+"/"+total;
                Sname.setText(subject);
                Sattendence.setText(a);
                count=0;
                total=0;
            }

            @Override
            public void onFailure(Call<List<attendModel>> call, Throwable t) {

            }
        });//Call Bracket

    }//userdefined bracket

}