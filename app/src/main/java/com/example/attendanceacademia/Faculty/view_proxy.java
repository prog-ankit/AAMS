package com.example.attendanceacademia.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.attendanceacademia.R;

public class view_proxy extends AppCompatActivity {

    TextView from_faculty,to_faculty,date,from_time,to_time,room_no,desc;
    String Sfrom_faculty,Sto_faculty,Sdate,Sfrom_time,Sto_time,Sroom_no,Sdesc;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_proxy);

        from_faculty = findViewById(R.id.from_faculty);
        to_faculty = findViewById(R.id.to_faculty);
        date = findViewById(R.id.on_date);
        from_time = findViewById(R.id.from_time);
        to_time = findViewById(R.id.to_time);
        room_no = findViewById(R.id.room_no);
        desc = findViewById(R.id.description);

        intent = getIntent();
        Sfrom_faculty = intent.getStringExtra("from_faculty");
        Sto_faculty = intent.getStringExtra("to_faculty");
        Sdate = intent.getStringExtra("date");
        Sfrom_time = intent.getStringExtra("from_time");
        Sto_time = intent.getStringExtra("to_time");
        Sroom_no = intent.getStringExtra("room_no");
        Sdesc = intent.getStringExtra("description");

        from_faculty.setText(Sfrom_faculty);
        to_faculty.setText(Sto_faculty);
        date.setText(Sdate);
        from_time.setText(Sfrom_time);
        to_time.setText(Sto_time);
        room_no.setText(Sroom_no);
        desc.setText(Sdesc);
    }
}