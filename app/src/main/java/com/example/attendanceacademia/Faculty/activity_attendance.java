package com.example.attendanceacademia.Faculty;


import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.example.attendanceacademia.R;

import static com.example.attendanceacademia.Faculty.faculty_dash.*;

public class activity_attendance extends AppCompatActivity {

    CardView cardmarkattend,cardviewattend,cardupdateattend,cardviewprovisionaldetain;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        //Assigning CardViews
        cardmarkattend =findViewById(R.id.markattendance);
        cardviewattend =findViewById(R.id.viewattendance);
        cardupdateattend =findViewById(R.id.updateattendance);
        cardviewprovisionaldetain =findViewById(R.id.viewprovisionaldetain);
        cardviewlisteners();

    }

    private void cardviewlisteners() {
        cardmarkattend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(activity_attendance.this, select_attendance.class);
            }
        });

        cardupdateattend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(activity_attendance.this, edit_select_attendance.class);
            }
        });

        cardviewattend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(activity_attendance.this, fac_view_attendance.class);
            }
        });

        cardviewprovisionaldetain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(activity_attendance.this, view_provisional_detain.class);
            }
        });
    }
}


