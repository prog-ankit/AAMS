package com.example.attendanceacademia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.attendanceacademia.Faculty.faculty_dash;
import com.example.attendanceacademia.HOD.hod_dash;
import com.example.attendanceacademia.Student.std_dash;

import java.util.HashMap;
import java.util.Map;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;
import static com.example.attendanceacademia.Faculty.faculty_dash.specialredirectActvity;

public class forgot_password extends AppCompatActivity {

    EditText username, newpassword, confirmpassword;
    String enrollment,faculty_id,hod_id,mainuser;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        enrollment = std_dash.enrollment;
        faculty_id = faculty_dash.faculty_id;
        hod_id = hod_dash.hod_id;

        url = BASE_URL+"updatePassword.php";
        username = findViewById(R.id.userid);
        newpassword = findViewById(R.id.newpassword);
        confirmpassword = findViewById(R.id.confirmpassword);

        if(enrollment == null){
            if(faculty_id == null){
                mainuser = hod_id;
            }
            else{
                mainuser = faculty_id;
            }
        }
        else{
            mainuser = enrollment;
        }
        username.setText(mainuser);

        newpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0 || s.length()<10) newpassword.setError("Password Must be 10 characters long!!");

                if(!newpassword.getText().toString().contains("@") && !newpassword.getText().toString().contains("_"))
                    newpassword.setError("Password must contain single @ or _ !!");
               }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(newpassword.getText().length()==0) newpassword.setError("Enter a Password");
                if(!s.toString().equals(newpassword.getText().toString())) confirmpassword.setError(newpassword.getText().toString()+" : "+s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}

        });


    }

    public void updatepassword(View view) {

        String strnewpassword = newpassword.getText().toString();
        String strconfirm = confirmpassword.getText().toString();
       if (!strnewpassword.equals(strconfirm)){
           Toast.makeText(this, "Both Passwords Must Match!!", Toast.LENGTH_SHORT).show();
       }
       else{
            showAlert(strnewpassword);
       }

    }

    private void showAlert(String strnewpassword) {
        AlertDialog.Builder builder = new AlertDialog.Builder(forgot_password.this);
        builder.setTitle("Confirm Password");
        builder.setMessage("Are you sure you want to confirm password?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              changePassword(strnewpassword);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void changePassword(String strnewpassword) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Update Success")){
                    Toast.makeText(forgot_password.this, response.toString(), Toast.LENGTH_SHORT).show();
                    SessionManager sessionManagement = new SessionManager(forgot_password.this);
                    sessionManagement.removeSession();
                    enrollment = null;
                    hod_id = null;
                    faculty_id = null;
                    specialredirectActvity(forgot_password.this,MainActivity.class);
                }
                else{
                    Toast.makeText(forgot_password.this, response.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("username",mainuser);
                params.put("password",strnewpassword);
                return params;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}