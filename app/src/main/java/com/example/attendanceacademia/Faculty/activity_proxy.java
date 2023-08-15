package com.example.attendanceacademia.Faculty;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.attendanceacademia.R;

import static com.example.attendanceacademia.Faculty.faculty_dash.redirectActvity;

public class activity_proxy extends AppCompatActivity {

    CardView cardarrangeproxy,cardupdateproxy,cardviewproxy;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);

        cardarrangeproxy = findViewById(R.id.btn_apply_proxy);
        cardupdateproxy = findViewById(R.id.btn_modify_proxy);
        cardviewproxy = findViewById(R.id.btn_view_proxy);
        cardviewlisteners();
    }

    private void cardviewlisteners() {
        cardarrangeproxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(activity_proxy.this, set_proxy.class);
            }
        });

        cardupdateproxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(activity_proxy.this, edit_proxy_list.class);
            }
        });

        cardviewproxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(activity_proxy.this, proxy_list.class);
            }
        });
    }
}
