//Our work on 18 March 2021

package com.example.attendanceacademia.Faculty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Models.attendModel;
import com.example.attendanceacademia.Models.studentModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class view_provisional_detain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Spinner subject;
    private ListView listView;
    SearchView searchView;
    List<String> detain_array = new ArrayList<String>();
    Provisional_Detain_List_Adapater provisional_detain_list_adapater;
    List<String> studentEnrollment = new ArrayList<String>();
    List<Integer> attendCollection = new ArrayList<Integer>();
    int total=0,count=0,flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_provisional_detain);

        searchView = findViewById(R.id.searchdetain);
        subject = findViewById(R.id.select_subject);
        listView = findViewById(R.id.students);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)  {
                return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    provisional_detain_list_adapater.filter("");
                    listView.clearTextFilter();
                }
                else{
                    provisional_detain_list_adapater.filter(newText);
                }
                return false;
            }
        });
        subject.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        studentEnrollment.clear();
        attendCollection.clear();
        detain_array.clear();
        fetchStudents(parent,position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    private void fetchData(List<String> studentEnrollment,AdapterView<?> parent, int position) {
        String subject = parent.getItemAtPosition(position).toString();
        if (subject.equals("--Select Subject--")){
            studentEnrollment.clear();
            attendCollection.clear();
            detain_array.clear();
            if (flag==1){
                provisional_detain_list_adapater.notifyDataSetChanged();
                flag=0;
            }


        }
        else{
            flag=1;
            for (int i = 0; i < studentEnrollment.size(); i++){
                showData(studentEnrollment.get(i),subject);
            }
        }


    }
    //Showing List of student and subject
    private void showData(String enrollment, String subject) {//186230307001,CMTS
        dashMyapi detainapi = Connection.retrofit.create(dashMyapi.class);
        Call <List<attendModel>> detaincall = detainapi.attendModel();
        detaincall.enqueue(new Callback<List<attendModel>>() {
            @Override
            public void onResponse(Call<List<attendModel>> call, Response<List<attendModel>> response) {
                List<attendModel> data = response.body();
                int count_a = 0, flag = 0;
                //186230307001
                for (int j = 0; j < data.size(); j++) {//(data.size=10)-1=(j<=9)
                    String temp_enroll = data.get(j).getEnrollment();
                    String temp_subj = data.get(j).getSubject_name();
                    if (temp_enroll.equals(enrollment) && temp_subj.equals(subject)) {
                        flag++;//3
                        attendCollection.add(count_a,data.get(j).getAttend());//0=001-CMTS-1, 1=001-CMTS-0
                        count_a++;
                    }
                }
                calculate(enrollment,attendCollection,subject);
            }

            @Override
            public void onFailure(Call<List<attendModel>> call, Throwable t) {

            }
        });

    }

    private void calculate(String enrollment,List<Integer> attendCollection,String subject) {

        float per;
        for (int i=0;i<attendCollection.size();i++){
            total++;
            if(attendCollection.get(i) == 1){
                count++;
            }
        }

        if(total!=0){
            if (subject.equals("--Select Subject--")){
                detain_array.clear();
            }
            else{

                per = (count*100)/total;
                if(per == 0){
                    detain_array.add(enrollment + ": Nil");
                }else if(per > 0 && per < 50){
                    detain_array.add(enrollment + ": Less");
                }
            }


            //Initialize adapter
            provisional_detain_list_adapater = new Provisional_Detain_List_Adapater(detain_array, view_provisional_detain.this);
            listView.setAdapter(provisional_detain_list_adapater);

            //detain_array_adapter.clear();
            count=total=0;
            attendCollection.clear();

        }
        else{
            //detain_array.clear();
        }
    }



    //Fetching Student's Enrollment Numbers
    private void fetchStudents(AdapterView<?> parent, int position) {
        //Call student model
        dashMyapi studentapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<studentModel>> stdcall = studentapi.studentModel();
        stdcall.enqueue(new Callback<List<studentModel>>() {
            @Override
            public void onResponse(Call<List<studentModel>> call, Response<List<studentModel>> response) {
                List<studentModel> data = response.body();
                for (int i = 0; i < data.size(); i++){
                    studentEnrollment.add(i,data.get(i).getEnrollment());
                }
                fetchData(studentEnrollment,parent,position);

            }

            @Override
            public void onFailure(Call<List<studentModel>> call, Throwable t) {

            }
        });
    }




    public static class Provisional_Detain_List_Adapater extends BaseAdapter {

        private final List<String> detain_array;
        private final List<String> detainModelListFiltered;
        private final Context context;
        private final LayoutInflater inflater;


        public Provisional_Detain_List_Adapater(List<String> detain_array, Context context) {
            this.detain_array = detain_array;
            detainModelListFiltered = new ArrayList<String>();
            detainModelListFiltered.addAll(detain_array);
            this.context = context;
            this.inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return detain_array.size();
        }

        @Override
        public Object getItem(int position) {
            return detain_array.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public static class ViewHolder{
            TextView enrollment, name;
        }
        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           ViewHolder holder;


            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.student_attendance_items, null);
                holder.enrollment = convertView.findViewById(R.id.enrollment);
                holder.name = convertView.findViewById(R.id.name);

                convertView.setTag(
                        holder
                );
            } else {
                holder = (ViewHolder) convertView.getTag();

            }
            if (position % 2 == 1)
                convertView.setBackgroundColor(context.getResources().getColor(R.color.gray));
            else convertView.setBackgroundColor(context.getResources().getColor(R.color.white));


            holder.enrollment.setText(detain_array.get(position));
            holder.name.setText("");
            if (position % 2 == 1) convertView.setBackgroundColor(context.getResources().getColor(R.color.gray));
            else convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
            return convertView;



        }
        public void filter(String chartext){

            detain_array.clear();
            if (chartext.length()==0){
                detain_array.addAll(detainModelListFiltered);
            }
            else{
                for (String enrollment : detainModelListFiltered){
                    if (enrollment.contains(chartext)){
                        detain_array.add(enrollment);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
}