package com.example.attendanceacademia.Student;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Models.facultyModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;


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

public class apply_leave extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    EditText tfrom,tto,tdesc;
    Spinner sfacultyName,sreason;
    Button applyleavebtn;
    String desc,facultyName,enrollment,reason;
    String url;
    StringBuffer fromdate, todate;
    private int mYear, mMonth, mDay;
    Calendar c = Calendar.getInstance();
    List<String> facultyNames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);

        fromdate = new StringBuffer();
        todate = new StringBuffer();

        url = BASE_URL+"apply_leave.php";

        //EditText
        tfrom = (EditText) findViewById(R.id.from_date);
        tto = (EditText) findViewById(R.id.to_date);
        tdesc = (EditText) findViewById(R.id.desc);
        tfrom.setText("");
        tto.setText("");

        //Spinners
        sfacultyName = (Spinner) findViewById(R.id.appliedfaculty);
        sreason = (Spinner) findViewById(R.id.reason);

        //Button
        applyleavebtn = findViewById(R.id.applyleavebtn);
        fetchAllfaculties();

        //addingSpinnerdata();
        sfacultyName.setOnItemSelectedListener(this);
        applyleavebtn.setOnClickListener(this);
        tfrom.setOnClickListener(v -> openfromDateDialog());

        tto.setOnClickListener(v -> opentoDateDialog());

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
                    if(temp!=1){
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
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, facultyNames);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sfacultyName.setAdapter(ad);
       // Toast.makeText(getApplicationContext(),facultyNames.get(0),Toast.LENGTH_LONG).show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(getApplicationContext(),String.valueOf(facultyNames.size()),Toast.LENGTH_LONG).show();
        facultyName = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    //DateTimePicker Starts
    private void openfromDateDialog() {

        // Get Current Date
        fromdate.setLength(0);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        fromdate.append(dayOfMonth).append("-").append(monthOfYear + 1).append("-").append(year);
                        tfrom.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void opentoDateDialog() {
        // Get Current Date
        todate.setLength(0);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        todate.append(dayOfMonth).append("-").append(monthOfYear + 1).append("-").append(year);
                        tto.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    //DateTimePicker Ends

    //Database logic starts

    @Override
    public void onClick(View v) {
        reason = (String) sreason.getSelectedItem();
        enrollment = std_dash.enrollment;
        desc = tdesc.getText().toString().trim();
        int flag=0;
        if (facultyName.equals("--Select Faculty--")){
            flag=1;
        }
        if (reason.equals("--Select Reason--")){
            flag=1;
        }
        if (fromdate.toString().isEmpty()){
            tfrom.setError("Please Select a Date!!!");
            flag=1;
        }
        if (todate.toString().isEmpty()){
            tto.setError("Please Select a Date!!!");
            flag=1;
        }
        if (desc.isEmpty()){
            tdesc.setError("Please Enter Description!!!");
            flag=1;
        }

        if(flag==0){
           feedLeave();
        }else{
            Toast.makeText(getApplicationContext(),"Invalid data",Toast.LENGTH_LONG).show();
        }
    }
    public void feedLeave() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Insert Success")){
                    Toast.makeText(getApplicationContext(),"Leave Applied",Toast.LENGTH_LONG).show();
                    finish();

                }
                else{
                    Toast.makeText(getApplicationContext(),"Leave Cannot be Applied",Toast.LENGTH_LONG).show();

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
                params.put("username",enrollment);
                params.put("fname",facultyName);
                params.put("reason",reason);
                params.put("from", String.valueOf(fromdate));
                params.put("to", String.valueOf(todate));
                params.put("desc",desc);
                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }//Database Logic Ends




}