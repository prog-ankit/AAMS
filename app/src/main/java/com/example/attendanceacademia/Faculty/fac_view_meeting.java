package com.example.attendanceacademia.Faculty;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.attendanceacademia.R;

public class fac_view_meeting extends AppCompatActivity {

    TextView date,time,topic,room,description;
    String Sdate,Stime,Stopic,Sroom,Sdescription;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_view_meeting);
        date = findViewById(R.id.txt_date_hint);
        time = findViewById(R.id.txt_time_hint);
        topic = findViewById(R.id.txt_topic_hint);
        room = findViewById(R.id.txt_room_hint);
        description = findViewById(R.id.txt_description_hint);

        intent = getIntent();
        Sdate = intent.getStringExtra("date");
        Stime = intent.getStringExtra("time");
        Stopic = intent.getStringExtra("topic");
        Sroom = intent.getStringExtra("room");
        Sdescription = intent.getStringExtra("description");

        date.setText(Sdate);
        time.setText(Stime);
        topic.setText(Stopic);
        room.setText(Sroom);
        description.setText(Sdescription);
    }
}