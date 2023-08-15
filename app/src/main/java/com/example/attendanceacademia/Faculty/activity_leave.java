package com.example.attendanceacademia.Faculty;


import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;
import com.example.attendanceacademia.Models.facultyModel;
import com.example.attendanceacademia.Models.leaveModel;

import java.util.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.attendanceacademia.Faculty.faculty_dash.*;

public class activity_leave extends AppCompatActivity {

    ListView showLeaves;
    String faculty_name;
    Manage_Leave_Adapter manage_leave_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);


        showLeaves = findViewById(R.id.showLeaves);

        fetchFacultyName();
        showData();
    }

    public void fetchFacultyName() {
        dashMyapi fetchNameapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<facultyModel>> fetchNamecall = fetchNameapi.facultyModel();
        fetchNamecall.enqueue(new Callback<List<facultyModel>>() {
            @Override
            public void onResponse(Call<List<facultyModel>> call, Response<List<facultyModel>> response) {
                List<facultyModel> data = response.body();
                for (int i=0;i<data.size();i++){
                    if(data.get(i).getFaculty_id().equals(faculty_id)){
                        faculty_name = data.get(i).getFaculty_name();
                    }
                }
                //   Toast.makeText(getApplicationContext(),faculty_name,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<facultyModel>> call, Throwable t) {
            }
        });
    }


    private void showData() {

        List<leaveModel> filters = new ArrayList<leaveModel>();
        dashMyapi manageLeaveapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<leaveModel>> manageLeavecall = manageLeaveapi.leaveModel();
        manageLeavecall.enqueue(new Callback<List<leaveModel>>() {
            @Override
            public void onResponse(Call<List<leaveModel>> call, Response<List<leaveModel>> response) {
                List<leaveModel> data = response.body();

                for (int i=0;i<data.size();i++){
                    if (data.get(i).getLeave_faculty().equals(faculty_name) && data.get(i).getStatus() == null){
                        filters.add(data.get(i));
                    }
                }
                sendData(filters);
            }

            @Override
            public void onFailure(Call<List<leaveModel>> call, Throwable t) {

            }
        });


    }

    private void sendData(List<leaveModel> filters) {

        manage_leave_adapter = new Manage_Leave_Adapter(filters, this, manage_leave.class);
        showLeaves.setAdapter(manage_leave_adapter);

        showLeaves.setOnItemClickListener((parent, view, position, id) -> {
            Intent leave = new Intent(activity_leave.this,manage_leave.class);
            leave.putExtra("id",filters.get(position).getLeave_id());
            leave.putExtra("enrollment",filters.get(position).getLenrollment());

            leave.putExtra("start", filters.get(position).getLeave_start_date());
            leave.putExtra("end",filters.get(position).getLeave_end_date());
            leave.putExtra("reason", filters.get(position).getReason());
            leave.putExtra("counselor",filters.get(position).getLeave_faculty());
            leave.putExtra("description", filters.get(position).getLeave_desc());
            leave.putExtra("status",filters.get(position).getStatus());
            startActivity(leave);
            finish();
        });
    }

    public static class Manage_Leave_Adapter extends BaseAdapter {

        private final List<leaveModel> leaveModels;
        private final List<leaveModel> leaveModelListFiltered;
        private final Context context;
        private final LayoutInflater inflater;
        private final Class toClass;

        public Manage_Leave_Adapter(List<leaveModel> leaveModels, Context context, Class toClass) {
            this.leaveModels = leaveModels;
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            leaveModelListFiltered = new ArrayList<leaveModel>();
            leaveModelListFiltered.addAll(leaveModels);
            this.toClass = toClass;
        }

        @Override
        public int getCount() {
            return leaveModels.size();
        }

        @Override
        public Object getItem(int position) {
            return leaveModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder{
            TextView enrollment, reason;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.student_attendance_items,null);
                holder.enrollment = convertView.findViewById(R.id.enrollment);
                holder.reason = convertView.findViewById(R.id.name);

                convertView.setTag(
                        holder
                );
            }
            else{
                holder = (ViewHolder) convertView.getTag();

            }
            if (position % 2 == 1) convertView.setBackgroundColor(context.getResources().getColor(R.color.gray));
            else convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.enrollment.setText(leaveModels.get(position).getLenrollment());
            holder.reason.setText(leaveModels.get(position).getReason());
            return convertView;
        }
    }

}