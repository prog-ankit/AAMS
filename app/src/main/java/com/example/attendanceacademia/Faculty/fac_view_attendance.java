package com.example.attendanceacademia.Faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import android.content.*;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;

import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Models.studentModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;

import java.util.*;
import retrofit2.*;

public class fac_view_attendance extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner division;
    String div;
    ListView student_list;
    SearchView searchEnrollment;
    List<String> student_array = new ArrayList<String>();
    Student_Array_Adapter student_array_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_view_attendance);

        division = findViewById(R.id.division);
        student_list = findViewById(R.id.students_list);
        searchEnrollment = findViewById(R.id.search_bar);
        division.setOnItemSelectedListener(this);

        searchEnrollment.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               // student_array_adapter.getFilter().filter(newText);
                if (TextUtils.isEmpty(newText)){
                    student_array_adapter.filter("");
                    student_list.clearTextFilter();
                }
                else{
                    student_array_adapter.filter(newText);
                }
                return false;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        student_array.clear();
        div = division.getItemAtPosition(position).toString();
        displayData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void displayData() {
        dashMyapi studentmyapi =  Connection.retrofit.create(dashMyapi.class);
        Call<List<studentModel>> studentcall = studentmyapi.studentModel();
        studentcall.enqueue(new Callback<List<studentModel>>() {
            @Override
            public void onResponse(Call<List<studentModel>> call, Response<List<studentModel>> response) {
                List<studentModel> data = response.body();
                List<studentModel> filter = new ArrayList<studentModel>();
                for(int i = 0 ; i <data.size(); i++){
                    if(div.equals(data.get(i).getDiv_std())){
                        filter.add(data.get(i));
                    }
                }
                showData(filter);
            }

            @Override
            public void onFailure(Call<List<studentModel>> call, Throwable t) {

            }
        });

    }

    private void showData(List<studentModel> filter) {
        //Initialize adapter
        student_array_adapter = new Student_Array_Adapter(filter, this, fac_show_attend.class);
        student_list.setAdapter(student_array_adapter);
    }

    public static class Student_Array_Adapter extends BaseAdapter{
        private final List<studentModel> studentModells;
        private final List<studentModel> studentModelListFiltered;
        private final Context context;
        private final LayoutInflater inflater;
        private final Class toClass;

        public Student_Array_Adapter(List<studentModel> studentModells, Context context, Class fac_show_attendClass) {
            this.studentModells = studentModells;
            this.context = context;
            inflater = LayoutInflater.from(context);
            studentModelListFiltered = new ArrayList<studentModel>();
            studentModelListFiltered.addAll(studentModells);
            toClass = fac_show_attendClass;
        }
        @Override
        public int getCount() {
            return studentModells.size();
        }
        @Override
        public Object getItem(int position) {
            return studentModells.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        public class ViewHolder{
            TextView enrollment, name;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;


            if(convertView == null){
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.student_attendance_items,null);
                holder.enrollment = convertView.findViewById(R.id.enrollment);
                holder.name = convertView.findViewById(R.id.name);

                convertView.setTag(
                        holder
                );
            }
            else holder = (ViewHolder) convertView.getTag();


            if (position % 2 == 1) convertView.setBackgroundColor(context.getResources().getColor(R.color.gray));
            else convertView.setBackgroundColor(context.getResources().getColor(R.color.white));

            holder.enrollment.setText(studentModells.get(position).getEnrollment());
            holder.name.setText(studentModells.get(position).getStd_name());
            convertView.setOnClickListener(v -> {
                Intent sendStudent = new Intent(context,toClass);
                sendStudent.putExtra("enrollment", studentModells.get(position).getEnrollment());
                sendStudent.putExtra("name", studentModells.get(position).getStd_name());
                sendStudent.putExtra("semester",studentModells.get(position).getSemester());
                sendStudent.putExtra("division",studentModells.get(position).getDiv_std());
                sendStudent.putExtra("contact",studentModells.get(position).getContact_std());
                sendStudent.putExtra("profilepic",studentModells.get(position).getStd_profile());
                context.startActivity(sendStudent);
            });

            return convertView;
        }

        public void filter(String chartext){

            studentModells.clear();
            if (chartext.length()==0){
                studentModells.addAll(studentModelListFiltered);
            }
            else{
                for (studentModel student : studentModelListFiltered){
                    if (student.getStd_name().contains(chartext) || student.getEnrollment().contains(chartext)){
                        studentModells.add(student);
                    }
                }
            }
            notifyDataSetChanged();
        }
   }
}