package com.example.attendanceacademia.Faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Faculty.faculty_dash;
import com.example.attendanceacademia.Models.attendModel;
import com.example.attendanceacademia.Models.facultyModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fac_show_attend extends AppCompatActivity {

    TextView viewenroll,viewname, viewsubject,showcount;

    ListView statusStudent;
    List<String> students_attend_array = new ArrayList<String>();
    ArrayAdapter<String> students_attend_array_adapter;
    String subject=null,mainsubject,faculty_id,stdenroll,stdname;
    Intent intent;
    int total=0,count=0;
    private List<Integer> attendviewCollection = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_show_attend);

        intent = getIntent();
        stdname = intent.getStringExtra("name");
        stdenroll = intent.getStringExtra("enrollment");

        faculty_id = faculty_dash.faculty_id;
        viewenroll = findViewById(R.id.view_enrollment);
        viewname = findViewById(R.id.view_student_name);
        viewsubject = findViewById(R.id.view_subjectName);
        showcount = findViewById(R.id.totalstatus);
        statusStudent = findViewById(R.id.studentdates);

        fetchSubject(viewsubject);

        viewenroll.setText(stdenroll);
        viewname.setText(stdname);

    }




    private void showabsentList(String stdenroll, String subject) {
       dashMyapi fetchattenddates = Connection.retrofit.create(dashMyapi.class);
       Call<List<attendModel>> fetchattenddatesCall = fetchattenddates.attendModel();

        fetchattenddatesCall.enqueue(new Callback<List<attendModel>>() {
            @Override
            public void onResponse(Call<List<attendModel>> call, Response<List<attendModel>> response) {
                List<attendModel> data = response.body();
                List<attendModel> studentsMention = new ArrayList<attendModel>();
                for (int i=0;i<data.size();i++){
                    if (data.get(i).getEnrollment().equals(stdenroll) && data.get(i).getSubject_name().equals(subject)){
                        studentsMention.add(data.get(i));
                    }
                }
                showDates(studentsMention);
            }

            @Override
            public void onFailure(Call<List<attendModel>> call, Throwable t) {

            }
        });

    }

    private void showDates(List<attendModel> studentsMention) {
        for (int i=0;i<studentsMention.size();i++){
            if (studentsMention.get(i).getAttend()==1) students_attend_array.add(studentsMention.get(i).getDate_attend()+" : Present");
            else students_attend_array.add(studentsMention.get(i).getDate_attend()+" : Absent");
        }
        //Initialize adapter
        students_attend_array_adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, students_attend_array){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = ((TextView) view.findViewById(android.R.id.text1));
                textView.setMinHeight(100); // Min Height
                return view;
            }

        };
        statusStudent.setAdapter(students_attend_array_adapter);
    }


    private void showTotalcount(String subject) {
        mainsubject = subject;
        dashMyapi detainapi = Connection.retrofit.create(dashMyapi.class);
        Call <List<attendModel>> detaincall = detainapi.attendModel();
        detaincall.enqueue(new Callback<List<attendModel>>() {
            @Override
            public void onResponse(Call<List<attendModel>> call, Response<List<attendModel>> response) {
                List<attendModel> data = response.body();

                for (int j = 0; j < data.size(); j++) {//(data.size=10)-1=(j<=9)
                    String temp_enroll = data.get(j).getEnrollment();
                    String temp_subj = data.get(j).getSubject_name();
                    if (temp_enroll.equals(stdenroll) && temp_subj.equals(subject))
                        attendviewCollection.add(data.get(j).getAttend());
                }
                calculate(attendviewCollection,subject);
            }

            @Override
            public void onFailure(Call<List<attendModel>> call, Throwable t) {

            }
        });

    }

    private void calculate(List<Integer> attendviewCollection, String subject) {

        for (int i=0;i<attendviewCollection.size();i++){
            total++;
            if(attendviewCollection.get(i) == 1){
                count++;
            }
        }
        String store = "Total Present "+ count+" out of "+total;
        showcount.setText(store);
        showabsentList(stdenroll,subject);
    }


    private void fetchSubject(TextView viewsubject) {
        dashMyapi fetchSubject = Connection.retrofit.create(dashMyapi.class);
        Call<List<facultyModel>> fetchSubjectCall = fetchSubject.facultyModel();
        fetchSubjectCall.enqueue(new Callback<List<facultyModel>>() {
            @Override
            public void onResponse(Call<List<facultyModel>> call, Response<List<facultyModel>> response) {
                List<facultyModel> data = response.body();
                for (int i = 0; i < data.size(); i++){
                    if (data.get(i).getFaculty_id().equals(faculty_id)){
                        subject =  data.get(i).getSubject_name();
                    }
                }
                viewsubject.setText(subject);
                showTotalcount(subject);
            }

            @Override
            public void onFailure(Call<List<facultyModel>> call, Throwable t) {
            }
        });
    }
}