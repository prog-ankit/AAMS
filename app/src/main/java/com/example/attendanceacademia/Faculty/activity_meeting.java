package com.example.attendanceacademia.Faculty;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.attendanceacademia.R;

import static com.example.attendanceacademia.Faculty.faculty_dash.redirectActvity;

public class activity_meeting extends AppCompatActivity {

    CardView cardarrangemeeting,cardupdatemeeting,cardviewmeeting;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        //Assigning CardViews
        cardarrangemeeting = findViewById(R.id.arrangemeeting);
        cardupdatemeeting = findViewById(R.id.updatemeeting);
        cardviewmeeting = findViewById(R.id.viewmeeting);
        cardviewlisteners();
    }

    private void cardviewlisteners() {
        cardarrangemeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(activity_meeting.this, set_meeting.class);
            }
        });

        cardupdatemeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(activity_meeting.this, edit_meeting_list.class);
            }
        });

        cardviewmeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(activity_meeting.this, fac_list_meeting.class);
            }
        });
    }
}
