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
import com.example.attendanceacademia.Models.sltModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;

public class edit_slt_list extends AppCompatActivity
//implements AdapterView.OnItemSelectedListener
{

    ListView slt_view;
    List<String> slt_array = new ArrayList<String>();
    TextView test;
    view_slt.Slt_List_Adapater slt_adapter;
    String faculty_id,url;
    Call<List<sltModel>> sltcall;
    List<sltModel> filters = new ArrayList<sltModel>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_slt_list);


        url = BASE_URL + "deleteSlt.php";
        slt_view = (ListView) findViewById(R.id.slt_list);
        faculty_id = faculty_dash.faculty_id;

        displayData();

        test = (TextView) findViewById(R.id.text);

        slt_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //   android.app.AlertDialog.Builder builder = new AlertDialog.Builder(edit_slt_list.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_slt_list.this);

                builder.setTitle("Delete Seminar/Test");
                builder.setIcon(R.drawable.ic_lecture);
                builder.setMessage("Are you sure you want to delete Seminar/Test?");
                //     builder.setMessage(Html.fromHtml("<font color='#FF7F27'>This is a test</font>"));
                builder.setPositiveButton(Html.fromHtml("<font color='#FF7F27'>Yes</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = filters.get(position).getId();
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
                buttonPositive.setTextColor(ContextCompat.getColor(edit_slt_list.this, R.color.blue));
                Button buttonNegative = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNegative.setTextColor(ContextCompat.getColor(edit_slt_list.this, R.color.blue));


                return true;
            }
        });
    }




    private void displayData() {
        //call sltModel
        dashMyapi sltapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<sltModel>> sltcall = sltapi.sltModel();
        sltcall.enqueue(new Callback<List<sltModel>>() {
            @Override
            public void onResponse(Call<List<sltModel>> call, Response<List<sltModel>> response) {
                List<sltModel> data = response.body();

                for(int i = 0; i < data.size(); i++){
                    if (data.get(i).getFaculty_id().equals(faculty_id)){
                        filters.add(data.get(i));
                    }
                }
                showslt(filters);
            }

            @Override
            public void onFailure(Call<List<sltModel>> call, Throwable t) {

            }
        });
    }

    public void showslt(List<sltModel> filters){

        //Initialize adapter
      /*  slt_adapter = new view_slt.Slt_List_Adapater(filters,edit_slt_list.this){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = ((TextView) view.findViewById(android.R.id.text1));
                textView.setMinHeight(250); // Min Height

                // textView.setHeight(200); // Height
                if (position%2==1) view.setBackgroundColor(getResources().getColor(R.color.gray));
                else view.setBackgroundColor(getResources().getColor(R.color.white));
                return view;
            }
        };*/
        slt_adapter = new view_slt.Slt_List_Adapater(filters,edit_slt_list.this);
        slt_view.setAdapter(slt_adapter);

        slt_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent slt = new Intent(edit_slt_list.this, editSlt.class);
             /*   Toast.makeText(getApplicationContext(),String.valueOf( filters.get(position).getId()),Toast.LENGTH_LONG).show();*/
                slt.putExtra("type",filters.get(position).getType_seminar_lect_test());
                slt.putExtra("date",filters.get(position).getDate_test_lect());
                slt.putExtra("time",filters.get(position).getTime_test_lect());
                slt.putExtra("topic",filters.get(position).getTopic_test_lect());
                slt.putExtra("faculty",filters.get(position).getLecturer_name());
                slt.putExtra("description",filters.get(position).getDesc_lecture());
                slt.putExtra("id", filters.get(position).getId());
                startActivity(slt);
            }
        });
    }


}
