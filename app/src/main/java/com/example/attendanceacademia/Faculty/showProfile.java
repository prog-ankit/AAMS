package com.example.attendanceacademia.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Models.attendModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;

public class showProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    EditText prenrollment, prname, prcontact, prsem, prdivision, prper;
    Spinner spinner;
    CircleImageView prprofile;
    String enrollment, name, contact, division, sem,profile;
    List<Integer> attends = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
        prenrollment = findViewById(R.id.profileenrollment);
        prname = findViewById(R.id.profilestudent);
        prcontact = findViewById(R.id.profilecontact);
        prsem = findViewById(R.id.profilesem);
        prdivision = findViewById(R.id.profiledivision);
        prper = findViewById(R.id.profileattendanceratio);
        prprofile = findViewById(R.id.profilepic);
        spinner = findViewById(R.id.profilespinner);


        Intent intent = getIntent();
        enrollment = intent.getStringExtra("enrollment");
        name = intent.getStringExtra("name");
        contact = intent.getStringExtra("contact");
        sem = intent.getStringExtra("semester");
        division = intent.getStringExtra("division");
        profile = intent.getStringExtra("profilepic");

        prenrollment.setText(enrollment);
        prname.setText(name);
        prcontact.setText(contact);
        prsem.setText(sem);
        prdivision.setText(division);

        Glide.with(showProfile.this)
                .load(BASE_URL+profile)
                .into(prprofile);

        spinner.setOnItemSelectedListener(this);

    }


    private void setEditTextAttendance(String subject) {

        dashMyapi attendprofileapi =  Connection.retrofit.create(dashMyapi.class);
        Call<List<attendModel>> attendprofileapicall = attendprofileapi.attendModel();
        attendprofileapicall.enqueue(new Callback<List<attendModel>>() {
            @Override
            public void onResponse(Call<List<attendModel>> call, Response<List<attendModel>> response) {
                List<attendModel> data = response.body();

                for (int i=0;i<data.size();i++){
                    if(data.get(i).getSubject_name().equals(subject) && data.get(i).getEnrollment().equals(enrollment)){
                        attends.add(data.get(i).getAttend());
                    }
                }
                setAttendanceData(subject);
            }

            @Override
            public void onFailure(Call<List<attendModel>> call, Throwable t) {

            }
        });

    }

    private void setAttendanceData(String subject) {
        int count=0;
        for (int i=0;i<attends.size();i++){
            if (attends.get(i) == 1){
                count++;
            }
        }
        if(subject.equals("--Select Subject--") || attends.size()==0 ){
            prper.setText("N/A");
        }
        else {
            int percentage = (count*100)/attends.size();

            prper.setText(String.valueOf(percentage)+"%");
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String subject = parent.getItemAtPosition(position).toString();
        attends.clear();
        setEditTextAttendance(subject);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}