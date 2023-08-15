package com.example.attendanceacademia.Faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendanceacademia.R;

import java.util.HashMap;
import java.util.Map;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;

public class manage_leave extends AppCompatActivity{

    int flag=0;
    Intent intent;
    TextView from,to,reason,counselor,description,Tstatus,enrollment;
    String From,To,res,faculty,des,status,url,id,enroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_leave);


        url=BASE_URL+"updateleaveStatus.php";

        from = (TextView) findViewById(R.id.manage_from_date);
        to = (TextView) findViewById(R.id.manage_to_date);
        reason = (TextView) findViewById(R.id.manage_reason);
        counselor = (TextView) findViewById(R.id.manage_facultyName);
        description = (TextView) findViewById(R.id.manage_desc);
        Tstatus = (TextView) findViewById(R.id.manage_status);
        enrollment = findViewById(R.id.txt_enroll);

        intent = getIntent();
        id = intent.getStringExtra("id");
        enroll = intent.getStringExtra("enrollment");
        From = intent.getStringExtra("start");
        To =   intent.getStringExtra("end");
        res =  intent.getStringExtra("reason");
        faculty = intent.getStringExtra("counselor");
        des =  intent.getStringExtra("description");
        status = intent.getStringExtra("status");

        enrollment.setText(enroll);
        from.setText(From);
        to.setText(To);
        counselor.setText(faculty);
        reason.setText(res);
        description.setText(des);

        if (status != null){
            if(status.equals("sanction") || status.equals("decline")){
                Tstatus.setText(status);
            }
            else{
                Tstatus.setText("Pending");
            }
        }
        else{
            Tstatus.setText("Pending");
        }



        Tstatus.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(manage_leave.this);
            builder.setTitle("Manage Leave");
            builder.setMessage("Do you want to Sanction or Decline this Leave?");
            builder.setPositiveButton("Sanction", (dialog, which) -> {
                Tstatus.setText("sanction");
                flag=1;
                updateleaveStatus(flag);
            });
            builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Tstatus.setText("decline");
                    flag=0;
                    updateleaveStatus(flag);
                }
            });
            builder.show();
            return true;
        });


    }

    private void updateleaveStatus(int flag) {

        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {

            Toast.makeText(manage_leave.this,"Leave Managed!!",Toast.LENGTH_SHORT).show();

        }, error -> Toast.makeText(manage_leave.this,error.toString(),Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                if (flag==1){
                    param.put("id",id);
                    param.put("status","sanction");
                }
                else{
                    param.put("id",id);
                    param.put("status","decline");
                }

                return param;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }


}