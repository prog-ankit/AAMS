package com.example.attendanceacademia.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.attendanceacademia.R;

public class select_view_timetable extends AppCompatActivity {

    Spinner days,div;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_view_timetable);

        days = (Spinner) findViewById(R.id.days);
        div = (Spinner) findViewById(R.id.ttdivision);


        days.setOnItemSelectedListener(new selectdays());
        div.setOnItemSelectedListener(new selectdivision());
    }

    public void view_data(View view) {
        //Toast.makeText(getApplicationContext(),selectdays.Sdays + "  " + selectdivision.Sdivision,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(select_view_timetable.this,view_timetable.class);
        startActivity(intent);
    }
    //to select data from spinner
    public static class  selectdays implements AdapterView.OnItemSelectedListener {

        static String Sdays;
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Sdays = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    public static class selectdivision implements AdapterView.OnItemSelectedListener {
        static String Sdivision;
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Sdivision = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}