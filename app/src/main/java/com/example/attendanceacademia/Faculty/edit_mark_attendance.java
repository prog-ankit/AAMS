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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.Models.attendModel;
import com.example.attendanceacademia.dashMyapi;
import com.example.attendanceacademia.Models.facultyModel;
import com.example.attendanceacademia.Models.studentModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;

public class edit_mark_attendance extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {


    private static String url;
    Intent intent;
    CheckBox selectall;
    ProgressDialog progressBar;
    int len, typell;
    int count=0;
    ListView attend_list;
    TextView total;
    List<String> attend_array = new ArrayList<String>();
    ArrayAdapter<String> attend_array_adapter;
    List<String> ids = new ArrayList<String>();
    private String type, subject, divbatch,date, divbat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mark_attendance);

        //Initializing Varibales

        url = BASE_URL+"updateAttend.php";
        selectall = findViewById(R.id.edit_checkall);
        total = findViewById(R.id.edit_showtotal);

        //Referencing ListView
        attend_list = findViewById(R.id.edit_attend_list);

        //Getting Data from previous Activity
        intent = getIntent();
        type = intent.getStringExtra("type");
        subject = intent.getStringExtra("subject");
        divbatch = intent.getStringExtra("divbatch");
        date = intent.getStringExtra("date");
        len = divbatch.length();
        if(type.equals("Lab")){
            typell=1;
        }else {
            typell=0;
        }


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
  /*  @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
*/
    private void showData() {
        dashMyapi attendmyapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<studentModel>> attendcall = attendmyapi.studentModel();
        attendcall.enqueue(new Callback<List<studentModel>>() {
            @Override
            public void onResponse(Call<List<studentModel>> call, Response<List<studentModel>> response) {
                List<studentModel> data = response.body();
                List<studentModel> studentsList = new ArrayList<studentModel>();

                for(int i = 0; i < data.size(); i++){
                    if(len == 1) {
                        divbat = data.get(i).getDiv_std();
                    }else{
                        divbat = data.get(i).getBatch_std();
                    }

                    if(divbat.equals(divbatch)){
                        studentsList.add(data.get(i));
                        //fetchdata(data.get(i).getEnrollment());
                    }
                }
                fetchdata(studentsList);
            }


            @Override
            public void onFailure(Call<List<studentModel>> call, Throwable t) {

            }
        });

    }

    private void fetchdata(List<studentModel> studentsList) {

        for (int i=0;i<studentsList.size();i++){
            attend_array.add(studentsList.get(i).getEnrollment());
        }



        attend_array_adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice,attend_array);
        attend_list.setAdapter(attend_array_adapter);

        selectall.setChecked(attend_array.size() == count);

        dashMyapi updateApi = Connection.retrofit.create(dashMyapi.class);
        Call<List<attendModel>> updateApicall = updateApi.attendModel();
        updateApicall.enqueue(new Callback<List<attendModel>>() {
            @Override
            public void onResponse(Call<List<attendModel>> call, Response<List<attendModel>> response) {
                List<attendModel> data = response.body();

                int divf=0,batchf=0;
                for(int i = 0; i < data.size(); i++) {
                    String changedate = data.get(i).getDate_attend();

                    if (data.get(i).getBatch_attend() == null) {
                        if (changedate.equals(date)) {
                            if (data.get(i).getSubject_name().equals(subject)) {
                                if (data.get(i).getDiv_attend().equals(divbatch)) {
                                    if (data.get(i).getAttend() == 1) {
                                        attend_list.setItemChecked(divf, true);
                                        count++;
                                    }
                                    ids.add(data.get(i).getAttend_id());
                                    divf++;
                                }
                            }
                        }
                    } else {
                        if (data.get(i).getBatch_attend().equals(divbatch)) {
                            if (data.get(i).getAttend() == 1) {
                                attend_list.setItemChecked(batchf, true);
                                count++;
                            }
                            ids.add(data.get(i).getAttend_id());
                            batchf++;
                        }
                    }
                }
                total.setText("Total: "+count);
            }


            @Override
            public void onFailure(Call<List<attendModel>> call, Throwable t) {

            }
        });
    }

    public void updateattendence(View view) {
        progressBar = new ProgressDialog(edit_mark_attendance.this);
        progressBar.setMessage("Updating Attendance...Please Wait!!");
        progressBar.show();

        List<Integer> attends = new ArrayList<Integer>();
        for (int j = 0; j < attend_list.getCount(); j++){

            if(attend_list.isItemChecked(j)){
                count++;
                attends.add(1);
            }else{
                count--;
                attends.add(0);
            }
        }

        sendData(ids,attends);
    }

    private void sendData(List<String> ids, List<Integer> attends) {
        // Toast.makeText(getApplicationContext(),"Inside sendData!!",Toast.LENGTH_LONG).show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.dismiss();
                if (response.contains("Success"))
                    Toast.makeText(getApplicationContext(),"Attendance Updated!!!",Toast.LENGTH_LONG).show();
                else
                Toast.makeText(getApplicationContext(),"Cannot Update Attendance!!!",Toast.LENGTH_LONG).show();


                finish();

            }
        }, error -> Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                String sids = new Gson().toJson(ids);
                String sattends = new Gson().toJson(attends);
                params.put("ids",sids);
                params.put("attend",sattends);
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