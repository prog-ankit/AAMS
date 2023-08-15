package com.example.attendanceacademia.Faculty;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.MainActivity;
import com.example.attendanceacademia.Models.facultyModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;
import com.example.attendanceacademia.forgot_password;
import com.example.attendanceacademia.HOD.hod_dash;
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

import static com.example.attendanceacademia.dashMyapi.BASE_URL;
import static com.example.attendanceacademia.Student.std_dash.redirectActvity;

public class fac_edit_profile extends AppCompatActivity {

    //Layout Varibales
    EditText name,contact, email;
    Button updatebutton;

    String url,faculty_id,temp_facid,temp_hodid;
    String prof;
    ProgressDialog progressBar;
    int flag=0;
    Bitmap bitmap;
    String encodeImageString;
    private static final int PICK_IMAGE = 1;
  //  Uri imageUri;
    CircleImageView showImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_edit_profile);
        url = BASE_URL+"updateFaculty.php";

        //Initializing Layout Varibales
        name = (EditText) findViewById(R.id.fac_username);
        contact = (EditText) findViewById(R.id.fac_contact);
        email = (EditText) findViewById(R.id.fac_email);
        updatebutton = findViewById(R.id.updatebutton);
        showImage = (CircleImageView) findViewById(R.id.facuserprof);

        temp_facid = faculty_dash.faculty_id;
        temp_hodid = hod_dash.hod_id;
        if (temp_facid==null){
            faculty_id = temp_hodid;
        }
        else{
            faculty_id = temp_facid;
        }

        editProfile();
       // Toast.makeText(getApplicationContext(), prof, Toast.LENGTH_SHORT).show();

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
               // Toast.makeText(getApplicationContext(), bitmap.toString(), Toast.LENGTH_SHORT).show();
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
        flag=1;
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }
    private void editProfile() {
        /*    url = "http://10.0.2.2/AAMS/public/practice/";*/
      /*  Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/

        dashMyapi fac_edit = Connection.retrofit.create(dashMyapi.class);
        Call<List<facultyModel>> fac_call = fac_edit.facultyModel();
        fac_call.enqueue(new Callback<List<facultyModel>>() {
            @Override
            public void onResponse(Call<List<facultyModel>> call, Response<List<facultyModel>> response) {

                List<facultyModel> data = response.body();
                for(int i = 0; i < data.size(); i++){
                    String temp = data.get(i).getFaculty_id();
                    if(temp.equals(faculty_id)){
                        String fac_name = data.get(i).getFaculty_name();
                        String fac_pass = data.get(i).getFaculty_pwd();
                        String fac_contact = data.get(i).getContact_faculty();
                        String fac_email = data.get(i).getEmail_faculty();
                        prof = data.get(i).getFaculty_profile();
                        encodeImageString = data.get(i).getFaculty_profile();
                        name.setText(fac_name);

                        contact.setText(fac_contact);
                        email.setText(fac_email);
                        Glide.with(fac_edit_profile.this)
                                .load(BASE_URL+prof)
                                .into(showImage);
                        name.setFocusable(false);

                        contact.setFocusable(false);
                        email.setFocusable(false);

                    }
                }

            }

            @Override
            public void onFailure(Call<List<facultyModel>> call, Throwable t) {

            }
        });
    }

    public void updateFaculty(View view) {



        progressBar = new ProgressDialog(fac_edit_profile.this);

        if(updatebutton.getText().equals("Edit Profile")){
            progressBar.setMessage("Loading..Please Wait!!");
            progressBar.show();

            name.setFocusableInTouchMode(true);

            contact.setFocusableInTouchMode(true);
            email.setFocusableInTouchMode(true);
            showImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dexter.withActivity(fac_edit_profile.this)
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
            updatebutton.setText("Update Profile");
            progressBar.dismiss();
        }
        else{

            progressBar.setMessage("Updating Details...Please Wait!!");
            progressBar.show();

            String updatefacName,updatefacPassword,updatefacContact,updatefacEmail;
            updatefacName = String.valueOf(name.getText());
            updatefacContact = String.valueOf(contact.getText());
         //   updatefacPassword = String.valueOf(password.getText());
            updatefacEmail = String.valueOf(email.getText());

           // encodeImageString = String.valueOf(showImage.getTag());
            progressBar.dismiss();
          // Toast.makeText(getApplicationContext(), encodeImageString, Toast.LENGTH_SHORT).show();
            updateCheck(updatefacName,updatefacContact,updatefacEmail);
        }



    }

    private void updateCheck(String updatefacName, String updatefacContact, String updatefacEmail) {
        if(flag==0) encodeImageString = "no";

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().equals("Update Success")){
                    Toast.makeText(getApplicationContext(),"Update Success",Toast.LENGTH_LONG).show();
                    updatebutton.setText("Edit Profile");
                    editProfile();
                    redirectActvity(fac_edit_profile.this, MainActivity.class);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Update Failed",Toast.LENGTH_LONG).show();
                }
                progressBar.dismiss();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.dismiss();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("name", updatefacName);
                param.put("contact", updatefacContact);
                param.put("email", updatefacEmail);
                param.put("fac_id", faculty_id);
                param.put("flag", String.valueOf(flag));
                param.put("profileImage",encodeImageString);
                return param;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    public void forgotpassword(View view) {
        redirectActvity(fac_edit_profile.this, forgot_password.class);
    }
}