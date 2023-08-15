package com.example.attendanceacademia.Faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Models.attendModel;
import com.example.attendanceacademia.Models.facultyModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.attendanceacademia.Faculty.faculty_dash.logout;
import static com.example.attendanceacademia.Faculty.faculty_dash.redirectActvity;



public class select_attendance extends AppCompatActivity{

    Spinner spintype, spinsubj, spindivbat;
    DrawerLayout drawerLayout;
    List<String> div_batch = new ArrayList<String>();
    String typell,subject, divbatch;
    public static select_attendance ob;
    int flag = 0;
    Calendar c = Calendar.getInstance();
    String current;
    String fac_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_attendance);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        current = dateFormat.format(c.getTime());
        //Toast.makeText(getApplicationContext(),current, Toast.LENGTH_SHORT).show();

        fac_id = faculty_dash.faculty_id;
        spintype = (Spinner) findViewById(R.id.type);
        spinsubj = (Spinner) findViewById(R.id.subject);
        spindivbat = (Spinner) findViewById(R.id.div_batch);

        ob= new select_attendance();


        spintype.setOnItemSelectedListener(new type(spindivbat));
        spinsubj.setOnItemSelectedListener(new subject());
        spindivbat.setOnItemSelectedListener(new division_batch());
    }

    public void attend(View view) {
        String obtypell = ob.typell;
        String obsubject = ob.subject;
        String obdivbatch = ob.divbatch;

        if(flag == 0) {
            if (obtypell.equals("--Select Type--") || obsubject.equals("--Select Subject--") || obdivbatch.equals("--Select Division/Batch--")) {
                Toast.makeText(getApplicationContext(), "Select data", Toast.LENGTH_LONG).show();
                recreate();
            } else {
                //Toast.makeText(getApplicationContext(),ob.typell + " " + ob.subject + " " + ob.divbatch,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), mark_attendance.class);
                intent.putExtra("type", obtypell);
                intent.putExtra("subject", obsubject);
                intent.putExtra("divbatch", obdivbatch);
                startActivity(intent);//
                finish();//still not, same in update attendance???
            }
        }
    }


    //type class starts
    class type extends select_attendance implements AdapterView.OnItemSelectedListener {
        Spinner spindivbat;
        public type(Spinner spindivbat) {
            this.spindivbat = spindivbat;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String type = parent.getItemAtPosition(position).toString();
            /*Toast.makeText(view.getContext(),type,Toast.LENGTH_SHORT).show();*/

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
            ArrayAdapter div_batchadapter = new ArrayAdapter(select_attendance.this, android.R.layout.simple_spinner_item, div_batch);
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
    class subject extends select_attendance  implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ob.subject = parent.getItemAtPosition(position).toString();}

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    //subject class ends
    //division_batch class starts
    class division_batch extends select_attendance  implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ob.divbatch = parent.getItemAtPosition(position).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
    }
}