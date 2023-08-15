package com.example.attendanceacademia.Faculty;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.attendanceacademia.R;

public class slt_desc extends AppCompatActivity {

    Intent intent;
    TextView date,time,topic,faculty,description;
    String Sdate,Stime,Stopic,Sfaculty,Sdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slt_desc);

        //Reference
        date = (TextView) findViewById(R.id.txt_date_hint);
        time = (TextView) findViewById(R.id.txt_time_hint);
        topic = (TextView) findViewById(R.id.txt_topic_hint);
        faculty = (TextView) findViewById(R.id.txt_faculty_hint);
        description = (TextView) findViewById(R.id.txt_Description_hint);

        intent = getIntent();
        Sdate = intent.getStringExtra("date");
        Stime = intent.getStringExtra("time");
        Stopic = intent.getStringExtra("topic");
        Sfaculty = intent.getStringExtra("faculty");
        Sdescription = intent.getStringExtra("description");


        date.setText(Sdate);
        time.setText(Stime);
        topic.setText(Stopic);
        faculty.setText(Sfaculty);
        description.setText(Sdescription);
    }
}