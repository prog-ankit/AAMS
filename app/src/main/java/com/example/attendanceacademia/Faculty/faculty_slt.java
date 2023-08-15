package com.example.attendanceacademia.Faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;

import com.example.attendanceacademia.R;

import static com.example.attendanceacademia.Faculty.faculty_dash.*;

public class faculty_slt extends AppCompatActivity {

    CardView cardarrangeslt,cardupdateslt,cardviewslt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_slt);

        //Assigning CardViews
        cardarrangeslt = findViewById(R.id.arrangeslt);
        cardupdateslt = findViewById(R.id.updateslt);
        cardviewslt = findViewById(R.id.viewslt);
        cardviewlisteners();

    }

    private void cardviewlisteners() {
        cardarrangeslt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(faculty_slt.this, set_slt.class);
            }
        });

        cardupdateslt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(faculty_slt.this, edit_slt_list.class);
            }
        });

        cardviewslt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(faculty_slt.this, view_slt.class);
            }
        });
    }
}