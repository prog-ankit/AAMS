package com.example.attendanceacademia.Student;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.attendanceacademia.R;

import static com.example.attendanceacademia.Student.std_dash.redirectActvity;

public class student_leave extends AppCompatActivity {

    CardView cardapplyleave, cardviewleave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_leave);

        cardapplyleave = findViewById(R.id.btn_applyleave);
        cardviewleave = findViewById(R.id.btn_view_leave);
        cardviewlisteners();
    }

    private void cardviewlisteners() {
        cardapplyleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(student_leave.this, apply_leave.class);
            }
        });

        cardviewleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(student_leave.this, leave_list.class);
            }
        });
    }
}