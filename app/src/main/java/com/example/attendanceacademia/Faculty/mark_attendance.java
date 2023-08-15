package com.example.attendanceacademia.Faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Models.facultyModel;
import com.example.attendanceacademia.Models.studentModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;

public class mark_attendance extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static String url;
    Intent intent;
    CheckBox selectall;
    ProgressDialog progressBar;
    private String type, subject, divbatch, divbat,faculty_name,faculty_id,formattedDate;
    int len;
    int count=0;
    ListView attend_list;
    TextView total;
    List<String> attend_array = new ArrayList<String>();
    ArrayAdapter<String> attend_array_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);



        //Initializing Varibales
        faculty_id = faculty_dash.faculty_id;
        url = BASE_URL+"setAttend.php";
        selectall = findViewById(R.id.checkall);
        total = findViewById(R.id.showtotal);

        //Referencing ListView
        attend_list = findViewById(R.id.attend_list);

        //Getting Data from previous Activity
        intent = getIntent();
        type = intent.getStringExtra("type");
        subject = intent.getStringExtra("subject");
        divbatch = intent.getStringExtra("divbatch");
        len = divbatch.length();

        //Fetching Current System Date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());
        formattedDate = df.format(c);



        fetchFacultyName();
        // Toast.makeText(getApplicationContext(),faculty_name,Toast.LENGTH_SHORT).show();
        showData();
        selectall.setOnClickListener(this);

        attend_list.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (selectall.isChecked()){
            for (int i=0;i<attend_list.getCount();i++){
                if(!attend_list.isItemChecked(i)){
                    attend_list.setItemChecked(i,true);
                    count++;

                }
            }
        }
        else{
            for (int i=0;i<attend_list.getCount();i++){
                if(attend_list.isItemChecked(i)){
                    attend_list.setItemChecked(i,false);
                    count--;
                }
            }
        }
        total.setText("Total: " + count);
    }
    public void fetchFacultyName() {

        dashMyapi fetchNameapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<facultyModel>> fetchNamecall = fetchNameapi.facultyModel();
        fetchNamecall.enqueue(new Callback<List<facultyModel>>() {

            @Override
            public void onResponse(Call<List<facultyModel>> call, Response<List<facultyModel>> response) {
                List<facultyModel> data = response.body();
                for (int i=0;i<data.size();i++){
                    if(data.get(i).getFaculty_id().equals(faculty_id)){
                        faculty_name = data.get(i).getFaculty_name();
                    }
                }
                //   Toast.makeText(getApplicationContext(),faculty_name,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<facultyModel>> call, Throwable t) {
            }
        });

    }

    private void showData() {
        dashMyapi attendmyapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<studentModel>> attendcall = attendmyapi.studentModel();
        attendcall.enqueue(new Callback<List<studentModel>>() {
            @Override
            public void onResponse(Call<List<studentModel>> call, Response<List<studentModel>> response) {
                List<studentModel> data = response.body();
                for(int i = 0; i < data.size(); i++){
                    if(len == 1) {
                        divbat = data.get(i).getDiv_std();
                    }else{
                        divbat = data.get(i).getBatch_std();
                    }
                    if(divbat.equals(divbatch)){
                        fetchdata(data.get(i).getEnrollment());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<studentModel>> call, Throwable t) {

            }
        });

    }

    private void fetchdata(String enrollment) {
        attend_array.add(enrollment);
        attend_array_adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice,attend_array);
        attend_list.setAdapter(attend_array_adapter);
    }

    public void markattendence(View view) {
        progressBar = new ProgressDialog(mark_attendance.this);
        progressBar.setMessage("Marking Attendance...Please Wait!!");
        progressBar.show();
        List<String> enrollments = new ArrayList<>();
        List<Integer> attends = new ArrayList<Integer>();
        for (int j = 0; j < attend_list.getCount(); j++){
            enrollments.add(attend_list.getItemAtPosition(j).toString());
            if(attend_list.isItemChecked(j)) attends.add(1);
            else attends.add(0);

        }
        sendData(enrollments,attends);
    }

    private void sendData(List<String> enrollments, List<Integer> attends) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.dismiss();
                if(response.equals("Success")){
                    Toast.makeText(getApplicationContext(),"Attendance Taken!!!"+" "+response.toString(),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Attendance Taken failed!!!"+" "+response.toString(),Toast.LENGTH_LONG).show();
                }
                finish();

            }
        }, error -> Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                String senrollment = new Gson().toJson(enrollments);
                String sattends = new Gson().toJson(attends);

                params.put("enrollment",senrollment);
                params.put("attend",sattends);
                params.put("type_lect",type);
                params.put("faculty_id",faculty_id);
                params.put("faculty_name",faculty_name);
                params.put("subject_name",subject);
                params.put("batch_attend",divbatch);
                params.put("date",formattedDate);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(attend_list.isItemChecked(position)) {
            count++;
        }else{
            count--;
        }
        selectall.setChecked(attend_array.size() == count);

        total.setText("Total: " + count);
    }

}