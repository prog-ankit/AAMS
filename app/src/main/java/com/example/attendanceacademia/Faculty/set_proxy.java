package com.example.attendanceacademia.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendanceacademia.Models.facultyModel;
import com.example.attendanceacademia.*;
import com.example.attendanceacademia.R;

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

public class set_proxy extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText date,fromtime,totime,desc,room;
    String tempdate,tempfromtime,temptotime,Sfaculty,Sdesc,Sroom,fac_id,url;
    Spinner sfaculty;
    private int mYear, mMonth, mDay, mHour, mMinute;
    int test_fac_id;

    List<String> facultyNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_proxy);

        url = BASE_URL+"set_proxy.php";
        date = findViewById(R.id.on_date);
        fromtime = findViewById(R.id.from_time);
        totime = findViewById(R.id.to_time);
        sfaculty = findViewById(R.id.spinfaculty);
        desc = findViewById(R.id.description);
        room = findViewById(R.id.roomnum);

        fac_id = faculty_dash.faculty_id;
        test_fac_id = Integer.parseInt(faculty_dash.faculty_id);
        fetchAllfaculties();

        sfaculty.setOnItemSelectedListener(this);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfromDateDialog();
            }
        });

        fromtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfromTimeDialog();
            }
        });

        totime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opentoTimeDialog();
            }
        });
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
                    if(temp!=1  && id!=test_fac_id){
                        tempfaculties.add(data.get(i).getFaculty_name());
                    }
                }
                //  Toast.makeText(getApplicationContext(),tempfaculties.get(0),Toast.LENGTH_LONG).show();
                sendData(tempfaculties);

            }
            @Override
            public void onFailure(Call<List<facultyModel>> call, Throwable t) {
            }
        });
    }
    //Send data
    private void sendData(List<String> myfaculties) {
        facultyNames.addAll(myfaculties);

       // spin.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, facultyNames);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sfaculty.setAdapter(ad);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Sfaculty = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        fromtime.setText(hourOfDay + ":" + minute);
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
                        totime.setText(hourOfDay + ":" + minute);
                        temptotime = hourOfDay + ":" + minute;
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void submit(View view) {
        Sdesc = desc.getText().toString();
        Sroom = room.getText().toString();
        int flag=0;
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
        if (Sroom.isEmpty()){
            flag = 1;
        }

        if(flag==0){
            feedProxy();
        }else{
            Toast.makeText(getApplicationContext(),"Invalid data",Toast.LENGTH_LONG).show();
        }

    }

    private void feedProxy() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Insert Success")) {
                    Toast.makeText(getApplicationContext(),"Proxy Arranged",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                }
            }
        }, error -> Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("from_faculty", fac_id);
                params.put("to_faculty", Sfaculty);
                params.put("date_proxy", tempdate);
                params.put("from_time", tempfromtime);
                params.put("to_time", temptotime);
                params.put("room", Sroom);
                params.put("desc_proxy", Sdesc);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


}