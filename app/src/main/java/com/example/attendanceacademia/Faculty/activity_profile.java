package com.example.attendanceacademia.Faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;

import com.example.attendanceacademia.R;

import static com.example.attendanceacademia.Faculty.faculty_dash.redirectActvity;

public class activity_profile extends AppCompatActivity {

    CardView cardeditprofile, cardviewprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        cardeditprofile = findViewById(R.id.editprofile);
        cardviewprofile = findViewById(R.id.viewprofile);

        cardviewlisteners();

    }

    private void cardviewlisteners() {

        cardeditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(activity_profile.this, fac_edit_profile.class);
            }
        });

        cardviewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(activity_profile.this, view_profile.class);
            }
        });

    }

}