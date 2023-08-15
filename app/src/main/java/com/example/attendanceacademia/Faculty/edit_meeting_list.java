package com.example.attendanceacademia.Faculty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;
import com.example.attendanceacademia.Models.meetingModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;

public class edit_meeting_list extends AppCompatActivity {

    ListView meet_view;
    List<String> meet_array = new ArrayList<String>();
    TextView test;
    String faculty_id;
    List<meetingModel> filters = new ArrayList<meetingModel>();
    fac_list_meeting.Meeting_List_Adapater meet_adapter;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meeting_list);

        //Initializing Variables!!
        url = BASE_URL+"deletemeet.php";
        meet_view = (ListView) findViewById(R.id.meet_list);
        faculty_id = faculty_dash.faculty_id;
        displayData();
        test = (TextView) findViewById(R.id.text);
        meet_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //   android.app.AlertDialog.Builder builder = new AlertDialog.Builder(edit_slt_list.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_meeting_list.this);

                builder.setTitle("Delete Meeting");
                builder.setIcon(R.drawable.ic_lecture);
                builder.setMessage("Are you sure you want to delete Meeting?");
                //     builder.setMessage(Html.fromHtml("<font color='#FF7F27'>This is a test</font>"));
                builder.setPositiveButton(Html.fromHtml("<font color='#FF7F27'>Yes</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = filters.get(position).getMeet_id();
                        //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
                        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                                /*Intent intent = new Intent(edit_slt_list.this,faculty_slt.class);
                                startActivity(intent);*/
                                finish();
                                startActivity(getIntent());
                            }
                        }, new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> param = new HashMap<>();
                                param.put("id",id);
                                return param;
                            }

                        };
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(request);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                android.app.AlertDialog alert = builder.create();
                alert.show();
                Button buttonPositive = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setTextColor(ContextCompat.getColor(edit_meeting_list.this, R.color.blue));
                Button buttonNegative = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNegative.setTextColor(ContextCompat.getColor(edit_meeting_list.this, R.color.blue));


                return true;
            }
        });
    }


    private void displayData() {

        //call meetingModel

        dashMyapi meetingapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<meetingModel>> meetingcall = meetingapi.meetingModel();
        meetingcall.enqueue(new Callback<List<meetingModel>>() {
            @Override
            public void onResponse(Call<List<meetingModel>> call, Response<List<meetingModel>> response) {
                List<meetingModel> data = response.body();

                for(int i = 0; i < data.size(); i++){
                    if(faculty_id.equals(data.get(i).getFaculty_id())){
                        filters.add(data.get(i));
                    }
                }
                showmeet(filters);
            }

            @Override
            public void onFailure(Call<List<meetingModel>> call, Throwable t) {

            }
        });
    }

    public void showmeet(List<meetingModel> filters){
        meet_adapter = new fac_list_meeting.Meeting_List_Adapater(filters,edit_meeting_list.this);
        meet_view.setAdapter(meet_adapter);
        meet_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent meet = new Intent(edit_meeting_list.this,editMeeting.class);
                meet.putExtra("date",filters.get(position).getDate_meet());
                meet.putExtra("time",filters.get(position).getTime_meet());
                meet.putExtra("topic",filters.get(position).getTopic_meet());
                meet.putExtra("room",filters.get(position).getRoom_no());
                meet.putExtra("description",filters.get(position).getDesc_meet());
                meet.putExtra("id",filters.get(position).getMeet_id());
                startActivity(meet);
                finish();
            }
        });
    }


}
