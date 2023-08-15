package com.example.attendanceacademia.Faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;
import com.example.attendanceacademia.Models.facultyModel;
import com.example.attendanceacademia.Models.proxyModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;

public class edit_proxy_list extends AppCompatActivity {

    ListView myproxylistView;
    List<String> myshowArray = new ArrayList<String>();
    proxy_list.Proxy_Array_Adapter myshowArrayadapter;
    List<proxyModel> filter = new ArrayList<proxyModel>();
    String fac_id,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_proxy_list);

        url = BASE_URL + "deleteProxy.php";
        myproxylistView = findViewById(R.id.your_proxy_list);
        fac_id = faculty_dash.faculty_id;
        fetchFacultyNames();


        myproxylistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_proxy_list.this);
                builder.setTitle("Delete Proxy");
                builder.setIcon(R.drawable.ic_proxy);
                builder.setMessage("Are you sure you want to delete Proxy?");

                builder.setPositiveButton(Html.fromHtml("<font color='#FF7F27'>Yes</font>"), (dialog, which) -> {
                    String id1 = String.valueOf(filter.get(position).getProxy_id());

                    StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());
                    }, error -> Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show()){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> param = new HashMap<>();
                            param.put("id", id1);
                            return param;
                        }

                    };
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);
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
                buttonPositive.setTextColor(ContextCompat.getColor(edit_proxy_list.this, R.color.blue));
                Button buttonNegative = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNegative.setTextColor(ContextCompat.getColor(edit_proxy_list.this, R.color.blue));


                return true;
            }
        });
    }

    private void fetchFacultyNames(){

        dashMyapi myfacultyapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<facultyModel>> myfacultyapicall = myfacultyapi.facultyModel();
        myfacultyapicall.enqueue(new Callback<List<facultyModel>>() {
            @Override
            public void onResponse(Call<List<facultyModel>> call, Response<List<facultyModel>> response) {
                List<facultyModel> data = response.body();
                displayData(data);
            }

            @Override
            public void onFailure(Call<List<facultyModel>> call, Throwable t) {

            }
        });
    }
    private void displayData(List<facultyModel> faculties) {

        dashMyapi myproxyapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<proxyModel>> myproxycall = myproxyapi.proxyModel();
        myproxycall.enqueue(new Callback<List<proxyModel>>() {
            @Override
            public void onResponse(Call<List<proxyModel>> call, Response<List<proxyModel>> response) {
                List<proxyModel> data = response.body();
                List<String> facultyNames = new ArrayList<String>();

                for (int i =0;i<data.size();i++){
                    String id = String.valueOf(data.get(i).getFrom_faculty());
                    if(fac_id.equals(id)){
                        filter.add(data.get(i));
                        for (int c = 0;c<faculties.size();c++){
                            if (id.equals(faculties.get(c).getFaculty_id())){
                                facultyNames.add(faculties.get(c).getFaculty_name());
                            }
                        }
                    }
                }
                showList(filter,facultyNames);
            }

            @Override
            public void onFailure(Call<List<proxyModel>> call, Throwable t) {

            }
        });
    }

    private void showList(List<proxyModel> data, List<String> facultyNames) {


/*
        for (int i=0;i<facultyNames.size();i++){
            myshowArray.add("From: " + facultyNames.get(i) + "  to: " +
                    data.get(i).getTo_faculty() + "\nOn: " +
                    data.get(i).getDate_proxy() + "  At:" +
                    data.get(i).getFrom_time());

        }
*/

        myshowArrayadapter = new proxy_list.Proxy_Array_Adapter(data,facultyNames,edit_proxy_list.this);
        myproxylistView.setAdapter(myshowArrayadapter);

        myproxylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent proxy = new Intent(edit_proxy_list.this,edit_proxy.class);
                proxy.putExtra("to_faculty",data.get(position).getTo_faculty());
                proxy.putExtra("date",data.get(position).getDate_proxy());
                proxy.putExtra("from_time",data.get(position).getFrom_time());
                proxy.putExtra("to_time",data.get(position).getTo_time());
                proxy.putExtra("room_no",data.get(position).getRoom_no());
                proxy.putExtra("description",data.get(position).getDesc_proxy());
                proxy.putExtra("id",String.valueOf(data.get(position).getProxy_id()));
                startActivity(proxy);
            }
        });
    }
}
