package com.example.attendanceacademia.Faculty;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Models.attendModel;
import com.example.attendanceacademia.Models.studentModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class edit_select_attendance extends AppCompatActivity {


    EditText editdate;
    Spinner spintype, spinsubj, spindivbat;
    String typell,subject, divbatch, fac_id;
    List<String> div_batch = new ArrayList<String>();
    public static edit_select_attendance ob;
    private int mYear, mMonth, mDay;
    String tempdate,formattedDate;
    int flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_select_attendance);


        spintype = (Spinner) findViewById(R.id.edit_type);
        spinsubj = (Spinner) findViewById(R.id.edit_subject);
        spindivbat = (Spinner) findViewById(R.id.edit_div_batch);
        editdate = (EditText) findViewById(R.id.edit_attend_date);

        //Fetching Current System Date
        Date currentdate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
        formattedDate = df.format(currentdate);

        ob = new edit_select_attendance();

        editdate.setOnClickListener(v -> openfromDateDialog());

        spintype.setOnItemSelectedListener(new type(spindivbat));
        spinsubj.setOnItemSelectedListener(new subject());
        spindivbat.setOnItemSelectedListener(new division_batch());
        fac_id = faculty_dash.faculty_id;
    }

    public void update_attend(View view) {

        String obtypell = ob.typell;
        String obsubject = ob.subject;
        String obdivbatch = ob.divbatch;
        int len = obdivbatch.length();

        //Toast.makeText(getApplicationContext(), obdivbatch+"\n"+len, Toast.LENGTH_SHORT).show();

        dashMyapi attendmyapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<attendModel>> attendcall = attendmyapi.attendModel();
        attendcall.enqueue(new Callback<List<attendModel>>() {
            @Override
            public void onResponse(Call<List<attendModel>> call, Response<List<attendModel>> response) {
                List<attendModel> data = response.body();
                for(int i = 0; i < data.size(); i++){
                    if(fac_id.equals(data.get(i).getFaculty_id())){
                        if( tempdate.equals(data.get(i).getDate_attend()) &&
                            obsubject.equals(data.get(i).getSubject_name()) &&
                            obsubject.equals(data.get(i).getSubject_name())){
                            if(len == 2){
                                if(obdivbatch.equals(data.get(i).getBatch_attend())){
                                    flag = 1;
                                    break;
                                }
                            }else if(obdivbatch.equals(data.get(i).getDiv_attend())){
                                flag = 1;
                                break;
                            }
                        }
                    }
                }
                if(flag == 1){
                    if (obtypell.equals("--Select Type--") || obsubject.equals("--Select Subject--") || obdivbatch.equals("--Select Division/Batch--"))
                        Toast.makeText(getApplicationContext(), "Select data", Toast.LENGTH_LONG).show();
                    else {
                        Intent intent = new Intent(getApplicationContext(), edit_mark_attendance.class);
                        intent.putExtra("type", obtypell);
                        intent.putExtra("subject", obsubject);
                        intent.putExtra("divbatch", obdivbatch);
                        intent.putExtra("date", tempdate);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<attendModel>> call, Throwable t) {

            }
        });
    }

    //type class starts
    class type extends edit_select_attendance implements AdapterView.OnItemSelectedListener {
        Spinner spindivbat;
        public type(Spinner spindivbat) {
            this.spindivbat = spindivbat;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String type = parent.getItemAtPosition(position).toString();
            if (type.equals("--Select Type--")){
                div_batch.clear();
                div_batch.add("--Select Division/Batch--");// = new String[]{"--Select Division/Batch--"};
            }
            if(type.equals("Lecture")){
                div_batch.clear();
                div_batch.add("A");
                div_batch.add("B");
                div_batch.add("C");

            }else if(type.equals("Lab")){
                div_batch.clear();
                div_batch.add("A1");
                div_batch.add("A2");
                div_batch.add("A3");
                div_batch.add("A4");


                div_batch.add("B1");
                div_batch.add("B2");
                div_batch.add("B3");
                div_batch.add("B4");

                div_batch.add("C1");
                div_batch.add("C2");
                div_batch.add("C3");
                div_batch.add("C4");
            }
            ArrayAdapter div_batchadapter = new ArrayAdapter(edit_select_attendance.this, android.R.layout.simple_spinner_item, div_batch);
            div_batchadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spindivbat.setAdapter(div_batchadapter);
            ob.typell = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    //type class ends

    //subject class starts
    static class subject extends edit_select_attendance  implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ob.subject = parent.getItemAtPosition(position).toString();}

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    //subject class ends
    //division_batch class starts
    static class division_batch extends edit_select_attendance  implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ob.divbatch = parent.getItemAtPosition(position).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
    }

    private void openfromDateDialog() {// Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {

                    // Sdate.append(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    int month = monthOfYear + 1;
                    String formattedMonth = "" + monthOfYear;
                    String formattedDayOfMonth = "" + dayOfMonth;

                    if(month < 10){

                        formattedMonth = "0" + month;
                    }
                    if(dayOfMonth < 10){

                        formattedDayOfMonth = "0" + dayOfMonth;
                    }
                    editdate.setText(formattedDayOfMonth + "-" + formattedMonth + "-" + year);
                    tempdate =  year + "-" + formattedMonth + "-" + formattedDayOfMonth;

                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}