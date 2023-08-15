package com.example.attendanceacademia.Faculty;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.MainActivity;
import com.example.attendanceacademia.Models.facultyModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.SessionManager;
import com.example.attendanceacademia.dashMyapi;
import com.example.attendanceacademia.Student.select_view_timetable;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;

public class faculty_dash extends AppCompatActivity {

    //Layout Varibales
    TextView name, id;
    CircleImageView fac_profile;
    CardView cardattend,cardleave,cardmeeting,cardslt,cardproxy,cardtimetable,cardprofile,cardlogout;

    Intent intent;//intent for receiving id
    public static String faculty_id;//id for later uses

    Connection obj;
    public static SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dash);


        intent = getIntent();
        faculty_id = intent.getStringExtra("username");

        //Assigning Layout Variables
        name = (TextView) findViewById(R.id.facultyname);
        id = (TextView) findViewById(R.id.facultyid);
        fac_profile = (CircleImageView) findViewById(R.id.facultyprofile);
        //Assigning CardViews
        cardattend =findViewById(R.id.attendance);
        cardleave =findViewById(R.id.leave);
        cardmeeting =findViewById(R.id.meeting);
        cardproxy =findViewById(R.id.proxy);
        cardslt =findViewById(R.id.seminartest);
        cardtimetable =findViewById(R.id.timetable);
        cardprofile = findViewById(R.id.profile);
        cardlogout = findViewById(R.id.logout);

        cardviewlisteners();



        sessionManager = new SessionManager(getApplicationContext());

        name.setText("");
        id.setText("");


        displayData();
    }

    private void cardviewlisteners() {
        cardprofile.setOnClickListener(v -> redirectActvity(faculty_dash.this, activity_profile.class));

        cardlogout.setOnClickListener(v -> logout(faculty_dash.this));

        cardattend.setOnClickListener(v -> redirectActvity(faculty_dash.this, activity_attendance.class));

        cardleave.setOnClickListener(v -> redirectActvity(faculty_dash.this, activity_leave.class));

        cardproxy.setOnClickListener(v -> redirectActvity(faculty_dash.this, activity_proxy.class));

        cardtimetable.setOnClickListener(v -> redirectActvity(faculty_dash.this, select_view_timetable.class));

        cardslt.setOnClickListener(v -> redirectActvity(faculty_dash.this,faculty_slt.class));

        cardmeeting.setOnClickListener(v -> redirectActvity(faculty_dash.this, activity_meeting.class));


    }
    private void displayData() {

        dashMyapi fac_api = Connection.retrofit.create(dashMyapi.class);
        Call<List<facultyModel>> fac_call = fac_api.facultyModel();
        fac_call.enqueue(new Callback<List<facultyModel>>() {
            @Override
            public void onResponse(Call<List<facultyModel>> call, Response<List<facultyModel>> response) {
                List<facultyModel> data = response.body();
                for(int i = 0; i < data.size(); i++){
                    String temp = data.get(i).getFaculty_id();
                    if(temp.equals(faculty_id)){

                        String prof = data.get(i).getFaculty_profile();

                        name.setText(data.get(i).getFaculty_name());
                        id.setText(temp);

                        Glide.with(faculty_dash.this)
                                .load(BASE_URL+prof)
                                .into(fac_profile);
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