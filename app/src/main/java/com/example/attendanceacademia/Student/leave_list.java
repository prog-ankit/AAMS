package com.example.attendanceacademia.Student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Faculty.fac_view_attendance;
import com.example.attendanceacademia.Models.leaveModel;
import com.example.attendanceacademia.Models.studentModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.attendanceacademia.dashMyapi.BASE_URL;

public class leave_list extends AppCompatActivity {

    //Initialize variable
    ListView leave_list;
    String enrollment,url;
    List<String> leave_array = new ArrayList<String>();
    Leave_Array_Adapter leave_array_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_list);

        //Assign variable
        enrollment = std_dash.enrollment;
        leave_list = findViewById(R.id.leave_list);
        url=BASE_URL+"leaveDash.php";
        fetchData();
    }


    private void fetchData() {
        dashMyapi myapi =  Connection.retrofit.create(dashMyapi.class);
        Call<List<leaveModel>> call = myapi.leaveModel();
        call.enqueue(new Callback<List<leaveModel>>() {
            @Override
            public void onResponse(Call<List<leaveModel>> call, Response<List<leaveModel>> response) {
                List<leaveModel> data = response.body();
                int count=0;

                List<leaveModel> filter = new ArrayList<leaveModel>();
                for(int i = 0; i < data.size(); i++) {
                    String temp = data.get(i).getLenrollment();
                    if(temp.equals(enrollment)){
                      //index= 0, value= 6 //index = 1,value=7 //2,8 //3,9
                        filter.add(count,data.get(i));//0, 6throw //1,7th row
                        count++;
                    }

                }
                list(filter);
            }

            @Override
            public void onFailure(Call<List<leaveModel>> call, Throwable t) {

            }
        });

    }

    public void list( List<leaveModel> filter) {
        //Add items in array list
        for (int j=0;j<filter.size();j++)//4
        {
         //   leave_array.add(filter.get(j).getReason()+ "\n" + filter.get(j).getLeave_start_date() + " to " + filter.get(j).getLeave_end_date());
            if((filter.get(j).getStatus())==null){
                filter.get(j).setStatus("Pending");
            }
        }
        //Initialize adapter
        leave_array_adapter = new Leave_Array_Adapter(filter, leave_list.this);
        leave_list.setAdapter(leave_array_adapter);
        leave_list.setOnItemClickListener((parent, view, position, id) -> {
            Intent leave = new Intent(leave_list.this, view_leave.class);
            leave.putExtra("start", filter.get(position).getLeave_start_date());
            leave.putExtra("end",filter.get(position).getLeave_end_date());
            leave.putExtra("reason", filter.get(position).getReason());
            leave.putExtra("counselor",filter.get(position).getLeave_faculty());
            leave.putExtra("description", filter.get(position).getLeave_desc());
            leave.putExtra("status",filter.get(position).getStatus());
            startActivity(leave);
        });
    }
    public static class Leave_Array_Adapter extends BaseAdapter {

        private final List<leaveModel> leaveModells;
        private final List<leaveModel> leaveModelListFiltered;
        private final Context context;
        private final LayoutInflater inflater;

        public Leave_Array_Adapter(List<leaveModel> leaveModells, Context context) {
            this.leaveModells = leaveModells;
            this.context = context;
            inflater = LayoutInflater.from(context);
            leaveModelListFiltered = new ArrayList<leaveModel>();
            leaveModelListFiltered.addAll(leaveModells);

        }


        @Override
        public int getCount() {
            return leaveModells.size();
        }

        @Override
        public Object getItem(int position) {
            return leaveModells.get(position);
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


            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.student_attendance_items, null);
                holder.enrollment = convertView.findViewById(R.id.enrollment);
                holder.name = convertView.findViewById(R.id.name);

                convertView.setTag(
                        holder
                );
            } else
                holder = (ViewHolder) convertView.getTag();


            if (position % 2 == 1)
                convertView.setBackgroundColor(context.getResources().getColor(R.color.gray));
            else convertView.setBackgroundColor(context.getResources().getColor(R.color.white));

        /*    if((leaveModells.get(position).getStatus())==null){
                leaveModells.get(position).setStatus("Pending");
            }*/
            holder.enrollment.setText(leaveModells.get(position).getReason());
            holder.name.setText(leaveModells.get(position).getLeave_start_date() + " to " + leaveModells.get(position).getLeave_end_date());
            return convertView;
        }

    }

}