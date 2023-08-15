package com.example.attendanceacademia.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;
import com.example.attendanceacademia.Models.facultyModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;
import static com.example.attendanceacademia.Faculty.faculty_dash.redirectActvity;

public class edit_proxy extends AppCompatActivity {

    TextView date,from_time,to_time,room_no,desc;
    String tempdate,tempfromtime,Sfaculty,temptotime,Sto_faculty,Sdate,Sfrom_time,Sto_time,Sroom_no,Sdesc,Sid,url;
    Spinner sfaculty;
    Intent intent;
    Calendar c = Calendar.getInstance();
    private int mYear, mMonth, mDay, mHour, mMinute;
    List<String> facultyNames = new ArrayList<String>();
    int fac_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_proxy);

        url = BASE_URL + "updateProxy.php";

        date = findViewById(R.id.date_proxy);
        from_time = findViewById(R.id.from_time);
        to_time = findViewById(R.id.to_time);
        room_no = findViewById(R.id.room_no);
        desc = findViewById(R.id.descProxy);
        sfaculty = findViewById(R.id.faculty);

        intent = getIntent();
        Sto_faculty = intent.getStringExtra("to_faculty");
        Sdate = intent.getStringExtra("date");
        Sfrom_time = intent.getStringExtra("from_time");
        Sto_time = intent.getStringExtra("to_time");
        Sroom_no = intent.getStringExtra("room_no");
        Sdesc = intent.getStringExtra("description");
        Sid = String.valueOf(intent.getStringExtra("id"));
        //Toast.makeText(getApplicationContext(),Sid,Toast.LENGTH_LONG).show();

        fac_id = Integer.parseInt(faculty_dash.faculty_id);
        /*Sto_faculty = sfaculty.getSelectedItemPosition();
        for(int i = 0; i < 5; i++){
            if(Sto_faculty.equals(sfaculty.getSelectedItem())){
                sfaculty.setSelection(1);
            }
        }*/

        date.setText(Sdate);
        from_time.setText(Sfrom_time);
        to_time.setText(Sto_time);
        room_no.setText(Sroom_no);
        desc.setText(Sdesc);

        tempdate = date.getText().toString();
        tempfromtime = from_time.getText().toString();
        temptotime = to_time.getText().toString();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfromDateDialog();
            }
        });

        from_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfromTimeDialog();
            }
        });

        to_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opentoTimeDialog();
            }
        });
        fetchAllfaculties();

    }

    //Fecthing Faculty Names for Spinner
    private void fetchAllfaculties() {
        dashMyapi fecthallFacultiesapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<facultyModel>> fetchFacultycall = fecthallFacultiesapi.facultyModel();
        fetchFacultycall.enqueue(new Callback<List<facultyModel>>() {
            @Override
            public void onResponse(Call<List<facultyModel>> call, retrofit2.Response<List<facultyModel>> response) {
                List<facultyModel> data = response.body();
                List<String> tempfaculties = new ArrayList<String>();
                tempfaculties.add("--Select Faculty--");
                for (int i=0;i<data.size();i++){
                    int temp = Integer.parseInt(data.get(i).getRole());
                    int id = Integer.parseInt(data.get(i).getFaculty_id());
                    if(temp!=1 && id!=fac_id){
                        tempfaculties.add(data.get(i).getFaculty_name());
                    }
                }
                //Toast.makeText(getApplicationContext(),tempfaculties.get(0),Toast.LENGTH_LONG).show();
                sendData(tempfaculties);
            }
            @Override
            public void onFailure(Call<List<facultyModel>> call, Throwable t) {
            }
        });
    }
    //Send data
    private void sendData(List<String> myfaculties) {
        for (int i=0;i<myfaculties.size();i++){
            facultyNames.add(myfaculties.get(i));
        }

        // spin.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, facultyNames);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sfaculty.setAdapter(ad);
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
                        //Sdate.append(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        tempdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    private void openfromTimeDialog() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        //Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        from_time.setText(hourOfDay + ":" + minute);
                        tempfromtime = hourOfDay + ":" + minute;

                        // Stime.append(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
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
                        to_time.setText(hourOfDay + ":" + minute);
                        temptotime = hourOfDay + ":" + minute;

                        // Stime.append(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void updateProxy(View view) {
        Sfaculty = (String) sfaculty.getSelectedItem();
        Sroom_no = room_no.getText().toString();
        Sdesc = desc.getText().toString();
        int flag = 0;

        if (Sfaculty.equals("--Select Faculty--")){
            flag = 1;
        }
        if (Sdesc.isEmpty()){
            flag = 1;
        }
        if (tempdate.isEmpty()){
            flag = 1;
        }
        if (tempfromtime.isEmpty()){
            flag = 1;
        }
        if (temptotime.isEmpty()){
            flag = 1;
        }
        if (Sroom_no.isEmpty()){
            flag = 1;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date datef = formatter.parse(date.getText().toString());
            Date current = formatter.parse(formatter.format(c.getTime()));
            //Toast.makeText(getApplicationContext(),current+"\n"+datef,Toast.LENGTH_LONG).show();
            if(datef.compareTo(current)<0){
                flag=1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(flag==0){
            modifyProxy();
        }else{
            Toast.makeText(getApplicationContext(),"Invalid data",Toast.LENGTH_LONG).show();
        }
    }

    private void modifyProxy() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Update Success")){
                    Toast.makeText(getApplicationContext(),"Details Updated!!!",Toast.LENGTH_LONG).show();
                   finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(

            ) throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", Sid);
                params.put("to_faculty", Sfaculty);
                params.put("date_proxy", tempdate);
                params.put("from_time", tempfromtime);
                params.put("to_time", temptotime);
                params.put("room", Sroom_no);
                params.put("desc_proxy", Sdesc);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}