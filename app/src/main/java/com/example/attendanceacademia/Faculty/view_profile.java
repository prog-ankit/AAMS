package com.example.attendanceacademia.Faculty;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Models.studentModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class view_profile extends AppCompatActivity {

    SearchView searchStudent;
    ListView studentsList;
    fac_view_attendance.Student_Array_Adapter student_array_adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        searchStudent = findViewById(R.id.searchStudent);
        studentsList = findViewById(R.id.allStudents);


        searchStudent.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // student_array_adapter.getFilter().filter(newText);
                if (TextUtils.isEmpty(newText)){
                    student_array_adapter.filter("");
                    studentsList.clearTextFilter();
                }
                else{
                    student_array_adapter.filter(newText);
                }
                return false;
            }
        });
        displayData();
    }

    private void displayData() {
        dashMyapi studentmyapi =  Connection.retrofit.create(dashMyapi.class);
        Call<List<studentModel>> studentcall = studentmyapi.studentModel();
        studentcall.enqueue(new Callback<List<studentModel>>() {
            @Override
            public void onResponse(Call<List<studentModel>> call, Response<List<studentModel>> response) {
                List<studentModel> data = response.body();
                showData(data);
            }

            @Override
            public void onFailure(Call<List<studentModel>> call, Throwable t) {

            }
        });

    }

    private void showData(List<studentModel> filter) {
        //Initialize adapter
        student_array_adapter = new fac_view_attendance.Student_Array_Adapter(filter, view_profile.this, showProfile.class);
        studentsList.setAdapter(student_array_adapter);
    }
}