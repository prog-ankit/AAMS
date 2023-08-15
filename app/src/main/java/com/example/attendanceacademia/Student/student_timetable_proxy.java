package com.example.attendanceacademia.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;

import com.example.attendanceacademia.Faculty.proxy_list;
import com.example.attendanceacademia.R;

import static com.example.attendanceacademia.Faculty.faculty_dash.redirectActvity;

public class student_timetable_proxy extends AppCompatActivity {

    CardView cardtimetable, cardproxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timetable_proxy);

        cardtimetable = findViewById(R.id.stdviewtimetable);
        cardproxy = findViewById(R.id.stdviewproxy);

        cardviewlisteners();
    }

    private void cardviewlisteners() {
        cardtimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(student_timetable_proxy.this, select_view_timetable.class);
            }
        });
        cardproxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(student_timetable_proxy.this, proxy_list.class);

            }
        });
    }
}