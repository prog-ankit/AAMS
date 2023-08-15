/*
Our work on 18 March 2021- view_provisional_detain, mark_attendance partially finished

Our work on 19 March 2021- mark_attendance completely finish(target) mark_attendance not finished

Our work on 20 March 2021- mark_attendance finished, manage Leave Finished

Our work on 21 March 2021- edit meeting,Slt Finished

Our work on 22 March 2021- delete meeting, delete Finished , view attendance partially finished

Work on 2 April 2021- editProfile something partially done!!!(need to work),HOD turned into card

7 April 2021- need to convert Done: set_slt,
                              Done: set_meeting,
                              Done: edit_slt,
                              Done: edit_proxy layout to Linear problem
13 April 2021- need to work on view_provisional_detain(complete)- solved

21 April- need to work on fac_show_attend xml some issue, check fac_view_attend,manage_leave,slt_desc,view_timetable,view_profile,view_provisional_detain once
*/
package com.example.attendanceacademia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.attendanceacademia.Faculty.faculty_dash;
import com.example.attendanceacademia.HOD.hod_dash;
import com.example.attendanceacademia.Student.std_dash;

import java.util.*;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;

public class MainActivity extends AppCompatActivity {



    EditText input_user,input_pwd;
    String url,username=null,password=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = BASE_URL+"login.php";
        input_user = findViewById(R.id.username);
        input_pwd = findViewById(R.id.password);


    }

    @Override
    protected void onStart() {
        super.onStart();
        checkSession();
    }

    public void login(View view) {
        username = input_user.getText().toString();
        password = input_pwd.getText().toString();
        Intent intent = new Intent(this, std_dash.class);
        intent.putExtra("username", username);
        if(username.isEmpty() || password.isEmpty()){
            if(username.isEmpty()){
                input_user.setError("Please Enter Username!!");
            }
            if (password.isEmpty()){

                input_pwd.setError("Please Enter Password!!");
            }

        }
        else{
            validate();
        }

    }
    private void checkSession() {
        //check if user is logged in
        //if user is logged in --> move to mainActivity

        SessionManager sessionManagement = new SessionManager(MainActivity.this);
        String userID = sessionManagement.getSession(MainActivity.this);

        if(!userID.equals("cancel")){
            //user id logged in and so move to mainActivity
            if(userID.length() == 12){
                alreadyMoved(std_dash.class,userID);
            }else if(userID.equals("9999")){
                alreadyMoved(hod_dash.class,userID);
            }else{
                alreadyMoved(faculty_dash.class,userID);
            }

        }
        //do nothing

    }


    private void alreadyMoved(Class aclass,String UserID) {
        Intent intent = new Intent(MainActivity.this, aclass);
        intent.putExtra("username",UserID);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void moveToMainActivity(Class aclass) {
        Intent intent = new Intent(MainActivity.this, aclass);
        intent.putExtra("username",username);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void validate() {
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            if(response.equals("Login Success")){
                User user = new User(username,password);
                SessionManager sessionManagement = new SessionManager(MainActivity.this);
                sessionManagement.saveSession(user);

                Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_LONG).show();
            /*    StyleableToast.makeText(this, "Hello World!", R.style.exampleToast).show();*/
                if(username.length()==12){
                    moveToMainActivity(std_dash.class);
                }
                else{
                    if(username.equals("9999")){

                        moveToMainActivity(hod_dash.class);
                    }else{

                        moveToMainActivity(faculty_dash.class);
                    }
                }
            }
            else{
                Toast.makeText(getApplicationContext(),"Invalid User",Toast.LENGTH_LONG).show();

            }
        }, error -> Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("id",username);
                param.put("password",password);
                return param;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}