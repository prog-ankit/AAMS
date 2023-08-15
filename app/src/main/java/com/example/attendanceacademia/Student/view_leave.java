package com.example.attendanceacademia.Student;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceacademia.R;

public class view_leave extends AppCompatActivity {

    Intent intent;
    TextView from,to,reason,counselor,description,Tstatus;
    String From,To,res,faculty,des,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_leave);
        from = (TextView) findViewById(R.id.txt_from_date);
        to = (TextView) findViewById(R.id.txt_to_date);
        reason = (TextView) findViewById(R.id.txt_reason_leave);
        counselor = (TextView) findViewById(R.id.txt_counselor_name);
        description = (TextView) findViewById(R.id.txt_Description_leave);
        Tstatus = (TextView) findViewById(R.id.status);

        intent = getIntent();
        From = intent.getStringExtra("start");
        To =   intent.getStringExtra("end");
        res =  intent.getStringExtra("reason");
        faculty = intent.getStringExtra("counselor");
        des =  intent.getStringExtra("description");
        status = intent.getStringExtra("status");

        from.setText(From);
        to.setText(To);
        counselor.setText(faculty);
        reason.setText(res);
        description.setText(des);

//        Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_LONG).show();
       if(status.equals("sanction") || status.equals("decline")){
            Tstatus.setText(status);
        }
        else{
            Tstatus.setText("Pending");
        }

    }
}