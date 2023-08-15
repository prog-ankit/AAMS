package com.example.attendanceacademia.HOD;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Faculty.activity_profile;
import com.example.attendanceacademia.Faculty.fac_list_meeting;
import com.example.attendanceacademia.MainActivity;
import com.example.attendanceacademia.Models.facultyModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.*;
import com.example.attendanceacademia.Student.*;
import com.example.attendanceacademia.Faculty.*;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;

public class hod_dash extends AppCompatActivity {

    TextView name, id;
    CircleImageView hodProfile;
    Intent intent;
    public static String hod_id;
    CardView cardhodslt,cardhodprovsion,cardhodmeeting,cardhodtimetable,cardhodproxy,cardlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_dash);

        intent = getIntent();
        hod_id = intent.getStringExtra("username");

        name =  findViewById(R.id.hodname);
        id =  findViewById(R.id.hodid);

        hodProfile =  findViewById(R.id.hodprofile);


        cardhodslt = findViewById(R.id.myhodslt);
        cardhodprovsion = findViewById(R.id.myhodprovisionlist);
        cardhodmeeting = findViewById(R.id.myhodmeeting);
        cardhodtimetable = findViewById(R.id.myhodtimetable);
        cardhodproxy = findViewById(R.id.myhodproxies);
        cardlogout = findViewById(R.id.logout);

        cardviewlisteners();

        displayData();
    }


    private void cardviewlisteners() {

        hodProfile.setOnClickListener(v -> redirectActvity(hod_dash.this, activity_profile.class));
        cardhodslt.setOnClickListener(v -> redirectActvity(hod_dash.this, view_slt.class));
        cardhodprovsion.setOnClickListener(v -> redirectActvity(hod_dash.this, view_provisional_detain.class));

        cardhodmeeting.setOnClickListener(v -> redirectActvity(hod_dash.this, fac_list_meeting.class));

        cardhodproxy.setOnClickListener(v -> redirectActvity(hod_dash.this,proxy_list.class));

        cardhodtimetable.setOnClickListener(v -> redirectActvity(hod_dash.this, select_view_timetable.class));

        cardlogout.setOnClickListener(v -> logout(hod_dash.this));
    }

    private void displayData() {

        dashMyapi hod_api = Connection.retrofit.create(dashMyapi.class);
        Call<List<facultyModel>> hod_call = hod_api.facultyModel();
        hod_call.enqueue(new Callback<List<facultyModel>>() {
            @Override
            public void onResponse(Call<List<facultyModel>> call, Response<List<facultyModel>> response) {
                List<facultyModel> data = response.body();
                for(int i = 0; i < data.size(); i++){
                    String temp = data.get(i).getFaculty_id();
                    if(temp.equals(hod_id)){

                        String prof = data.get(i).getFaculty_profile();

                        name.setText(data.get(i).getFaculty_name());
                        id.setText(temp);
                        //        email.setText(data.get(i).getEmail_faculty());
                        Glide.with(hod_dash.this)
                                .load(BASE_URL+prof)
                                .into(hodProfile);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<facultyModel>> call, Throwable t) {

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