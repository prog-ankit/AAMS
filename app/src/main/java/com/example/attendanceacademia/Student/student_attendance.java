package com.example.attendanceacademia.Student;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.example.attendanceacademia.R;

import static com.example.attendanceacademia.Student.std_dash.*;

public class student_attendance extends AppCompatActivity {

    CardView cardviewattend, cardviewdetain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);

        cardviewdetain = findViewById(R.id.btn_view_detain);
        cardviewattend = findViewById(R.id.btn_view_attend);
        cardviewlisteners();
    }

    private void cardviewlisteners() {
        cardviewattend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(student_attendance.this, view_attendance.class);
            }
        });

        cardviewdetain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(student_attendance.this, view_detain.class);
            }
        });
    }

}