package com.example.attendanceacademia.Faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendanceacademia.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;

public class set_meeting extends AppCompatActivity {

    EditText date,time,topic,room,desc;
    String Stopic,Sroom,Sdesc,Stype,url,fac_id,tempdate,temptime;
    StringBuffer Sdate, Stime;
    Calendar c = Calendar.getInstance();

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_meeting);

        date = findViewById(R.id.hint_date);
        time = findViewById(R.id.hint_time);
        topic = findViewById(R.id.hint_topic);
        room = findViewById(R.id.hint_room);
        desc = findViewById(R.id.hint_desc);
        url=BASE_URL+"set_meet.php";

        fac_id = faculty_dash.faculty_id;
        date.setText("");
        time.setText("");


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfromDateDialog();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opentoTimeDialog();
            }
        });
    }

    private void opentoTimeDialog() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        time.setText(hourOfDay + ":" + minute);
                        temptime = hourOfDay + ":" + minute;

                        // Stime.append(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void openfromDateDialog() {// Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        // Sdate.append(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        tempdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void submit(View view) {

     /*   url = "http://10.0.2.2/AAMS/public/practice/set_meet.php";*/

        Stopic = topic.getText().toString();
        Sroom = room.getText().toString();
        Sdesc = desc.getText().toString();
        int flag = 0;

        if(tempdate.isEmpty()){
            flag = 1;
        }
        if(Stopic.isEmpty()){
            flag = 1;
        }
        if(Sroom.isEmpty()){
            flag = 1;
        }
        if(Sdesc.isEmpty()){
            flag = 1;
        }

        if(flag==0){
            feedMeet();
        }else{
            Toast.makeText(getApplicationContext(),"Invalid data",Toast.LENGTH_LONG).show();
        }
    }

    private void feedMeet() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Insert Success")){
                    Toast.makeText(getApplicationContext(),"Meeting Set",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("date", tempdate);
                params.put("time", temptime);
                params.put("topic", Stopic);
                params.put("room", Sroom);
                params.put("desc", Sdesc);
                params.put("id", fac_id);
                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}