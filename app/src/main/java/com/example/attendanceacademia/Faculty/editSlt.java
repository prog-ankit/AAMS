package com.example.attendanceacademia.Faculty;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendanceacademia.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;

public class editSlt extends AppCompatActivity {

    RadioGroup groupSlt;
    RadioButton buttonSlt,semiRadio,testRadio;
    EditText date, time, topic, lectname, desc;
    String Stype, Sdate, Stime, Stopic, Slectname, Sdesc, Sid;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String tempdate,temptime, url;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_slt);

        url = BASE_URL + "updateSlt.php";

        groupSlt = findViewById(R.id.groupSlt);
        semiRadio = findViewById(R.id.seminarSlt);
        testRadio = findViewById(R.id.testSlt);
        date = findViewById(R.id.dateSlt);
        time = findViewById(R.id.timeSlt);
        topic = findViewById(R.id.topicSlt);
        lectname = findViewById(R.id.lecturernameSlt);
        desc = findViewById(R.id.descSlt);

        intent = getIntent();
        Stype = intent.getStringExtra("type");
        Sdate = intent.getStringExtra("date");
        Stime = intent.getStringExtra("time");
        Stopic = intent.getStringExtra("topic");
        Slectname = intent.getStringExtra("faculty");
        Sdesc = intent.getStringExtra("description");
        Sid = intent.getStringExtra("id");

        //Toast.makeText(getApplicationContext(), S)

        date.setText(Sdate);
        time.setText(Stime);
        topic.setText(Stopic);
        lectname.setText(Slectname);
        desc.setText(Sdesc);

        tempdate = date.getText().toString();
        temptime = time.getText().toString();

        if (Stype.equals("Seminar")) {
            semiRadio.setChecked(true);
        } else {
            testRadio.setChecked(true);
        }

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

    public void updateSlt(View view) {
        int selectedId = groupSlt.getCheckedRadioButtonId();
        buttonSlt = (RadioButton) findViewById(selectedId);
        Stopic = topic.getText().toString();
        Slectname = lectname.getText().toString();
        Sdesc = desc.getText().toString();
        Stype = buttonSlt.getText().toString();

        int flag=0;

        if(Stype.equals("--Select Seminar/Test--")){
            flag=1;
        }
        if(Sdate.isEmpty()){
            flag=1;
        }
        if(Stime.isEmpty()){
            flag=1;
        }
        if(Stopic.isEmpty()){
            flag=1;
        }
        if(Slectname.isEmpty()){
            flag=1;
        }
        if(Sdesc.isEmpty()){
            flag=1;
        }

        if(flag==0){
            modifySlt();
        }else {
            Toast.makeText(getApplicationContext(),"Invalid data",Toast.LENGTH_LONG).show();
        }

    }

    private void modifySlt() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Update Success")){
                  /*  Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    desc.setText(response.toString());*/
                       Toast.makeText(getApplicationContext(),Stype+" Details Updated!!!",Toast.LENGTH_LONG).show();
                      finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

                    //      Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                desc.setText(error.toString());
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("type", Stype);
                params.put("date", tempdate);
                params.put("time", temptime);
                params.put("topic", Stopic);
                params.put("name", Slectname);
                params.put("desc", Sdesc);
                params.put("id", Sid);
                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}