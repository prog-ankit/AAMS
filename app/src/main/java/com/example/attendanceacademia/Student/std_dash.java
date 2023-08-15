package com.example.attendanceacademia.Student;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.bumptech.glide.Glide;
import com.example.attendanceacademia.MainActivity;
import com.example.attendanceacademia.Models.studentModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.SessionManager;
import com.example.attendanceacademia.dashMyapi;
import com.example.attendanceacademia.Faculty.view_slt;

import java.util.*;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;

public class std_dash extends AppCompatActivity {

    CircleImageView profile;

    Intent intent;
    public static String enrollment;
    CardView std_cardattend,std_cardleave,std_cardslt,std_cardtimetable,std_cardprofile,std_cardlogout;
    TextView txt_username, txt_userid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_dash);

        intent = getIntent();
        enrollment = intent.getStringExtra("username");

        txt_username = (TextView)findViewById(R.id.txt_stdusername);
        txt_userid = (TextView)findViewById(R.id.txt_stduserid);
        profile= (CircleImageView) findViewById(R.id.stdprofile);
        std_cardattend =findViewById(R.id.stdattendance);
        std_cardleave =findViewById(R.id.stdleave);
        std_cardslt =findViewById(R.id.stdseminartest);
        std_cardtimetable =findViewById(R.id.stdtimetable);
        std_cardprofile = findViewById(R.id.stdmainprofile);
        std_cardlogout = findViewById(R.id.stdlogout);

        DisplayData();
        cardviewlisteners();


    }

    private void DisplayData() {

        /*    url = "http://10.0.2.2/AAMS/public/practice/";*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dashMyapi api = retrofit.create(dashMyapi.class);
        Call<List<studentModel>> call = api.studentModel();
        call.enqueue(new Callback<List<studentModel>>() {
            @Override
            public void onResponse(Call<List<studentModel>> call, Response<List<studentModel>> response) {
                List<studentModel> data = response.body();
                for(int i = 0; i<data.size();i++){
                    String temp = data.get(i).getEnrollment();
                    if(temp.equals(enrollment)){

                        String prof = data.get(i).getStd_profile();
                        txt_username.append(data.get(i).getStd_name());
                        txt_userid.append(temp);

                        Glide.with(std_dash.this)
                                .load(BASE_URL+prof)
                                .into(profile);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<studentModel>> call, Throwable t) {

            }
        });
    }





    private void cardviewlisteners() {


        std_cardprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(std_dash.this,editProfile.class);
            }
        });

        std_cardlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(std_dash.this);
            }
        });
        std_cardattend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                redirectActvity(std_dash.this, student_attendance.class);
            }
        });

        std_cardleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                redirectActvity(std_dash.this, student_leave.class);
            }
        });

        std_cardtimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActvity(std_dash.this, student_timetable_proxy.class);
            }
        });

        std_cardslt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                redirectActvity(std_dash.this, view_slt.class);
            }
        });
    }



    //Static Methods for Code Understandability
    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you want to Log Out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*activity.finishActivity(0);
                System.exit(0);*/
                SessionManager sessionManagement = new SessionManager(activity);
                sessionManagement.removeSession();
                specialredirectActvity(activity, MainActivity.class);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static void specialredirectActvity(Activity activity, Class aclass) {
        Intent intent = new Intent(activity, aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }
    public static void redirectActvity(Activity activity, Class aclass) {
        Intent intent = new Intent(activity,aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }
    //Static Method Ends
}