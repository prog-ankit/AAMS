package com.example.attendanceacademia.Student;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.MainActivity;
import com.example.attendanceacademia.Models.studentModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;
import com.example.attendanceacademia.forgot_password;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.attendanceacademia.Student.std_dash.redirectActvity;
import static com.example.attendanceacademia.dashMyapi.BASE_URL;

public class editProfile extends AppCompatActivity {

    EditText username,contact,email;
    CircleImageView showImage;
    Button updatebutton;
    String url,enrollment;
    ProgressDialog progressBar;
    Bitmap bitmap;
    int flag=0;
    String encodeImageString;
    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        url = BASE_URL+"updateStudent.php";

        username = (EditText) findViewById(R.id.username);

        contact = (EditText) findViewById(R.id.contact);
        email = (EditText) findViewById(R.id.email);
        showImage = (CircleImageView) findViewById(R.id.stduserprof);
        updatebutton = findViewById(R.id.stdupdatebutton);

        enrollment = std_dash.enrollment;

        showImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage.setFocusable(true);
            }
        });
        displayData();
    }

    private void displayData() {
        dashMyapi edit =  Connection.retrofit.create(dashMyapi.class);
        Call<List<studentModel>> call = edit.studentModel();
        call.enqueue(new Callback<List<studentModel>>() {
            @Override
            public void onResponse(Call<List<studentModel>> call, Response<List<studentModel>> response) {
                List<studentModel> data = response.body();
                for(int i = 0; i<data.size();i++){
                    String temp = data.get(i).getEnrollment();
                    if(temp.equals(enrollment)){
                        String name = data.get(i).getStd_name();

                        String mail = data.get(i).getEmail_std();
                        String phn = data.get(i).getContact_std();
                        String prof = data.get(i).getStd_profile();
                        username.setText(name);

                        email.setText(mail);
                        contact.setText(phn);
                        Glide.with(editProfile.this)
                                .load(BASE_URL+prof)
                                .into(showImage);


                        username.setFocusable(false);

                        contact.setFocusable(false);
                        email.setFocusable(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<studentModel>> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            Uri filepath=data.getData();
            try
            {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                showImage.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString= Base64.encodeToString(bytesofimage, Base64.DEFAULT);
        flag=1;
    }


    public void update(View view) {

        progressBar = new ProgressDialog(editProfile.this);
        if(updatebutton.getText().equals("Edit Profile")){
            progressBar.setMessage("Showing Profile!!");
            progressBar.show();


         //   displayData();
            username.setFocusableInTouchMode(true);

            contact.setFocusableInTouchMode(true);
            email.setFocusableInTouchMode(true);

            showImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dexter.withActivity(editProfile.this)
                            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    Intent showgallery = new Intent(Intent.ACTION_PICK);
                                    showgallery.setType("image/*");
                                    startActivityForResult(Intent.createChooser(showgallery,"Browse Image"),1);
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            }).check();
                 /*   Intent showgallery = new Intent();
                    showgallery.setType("image/*");
                    showgallery.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(showgallery,"Select Picture"),PICK_IMAGE);*/
                }
            });
        //    showImage.setFocusable(true);
          /*  if (showImage.isFocusable()){
                Toast.makeText(getApplicationContext(),"Image Clicked",Toast.LENGTH_SHORT).show();
            }*/
            updatebutton.setText("Update Profile");
            progressBar.dismiss();
        }
        else{

            progressBar.setMessage("Updating Details...Please Wait!!");
            progressBar.show();

            String updatefacName,updatefacContact,updatefacEmail;
            updatefacName = String.valueOf(username.getText());
            updatefacContact = String.valueOf(contact.getText());

            updatefacEmail = String.valueOf(email.getText());

            updateCheck(updatefacName,updatefacContact,updatefacEmail);
        }

    }

    private void updateCheck(String updateName, String updateContact, String updateEmail) {
        if(flag==0) encodeImageString = "no";
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                if(response.equals("Update Success")){
                    Toast.makeText(getApplicationContext(),"Update Success",Toast.LENGTH_LONG).show();
                    updatebutton.setText("Edit Profile");
                    displayData();
                    redirectActvity(editProfile.this, MainActivity.class);
                }
                else{
                    Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    username.setText(response);
                }
                progressBar.dismiss();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("name",updateName);

                param.put("contact",updateContact);
                param.put("email",updateEmail);
                param.put("enrollment",enrollment);
                param.put("flag",String.valueOf(flag));
                param.put("profileImage",encodeImageString);
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
    
        public void forgotpassword(View view) {
          redirectActvity(editProfile.this, forgot_password.class);
        }
}