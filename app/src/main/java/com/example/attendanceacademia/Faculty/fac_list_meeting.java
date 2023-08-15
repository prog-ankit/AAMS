package com.example.attendanceacademia.Faculty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Models.meetingModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fac_list_meeting extends AppCompatActivity
{

    ListView meet_view;
    List<String> meet_array = new ArrayList<String>();
    TextView test;
    Meeting_List_Adapater meet_adapter;
    Call<List<meetingModel>> meetcall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_meeting);
        meet_view = (ListView) findViewById(R.id.meet_list);
        displayData();
    }


    private void displayData() {

        //call meetingModel
        dashMyapi meetingapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<meetingModel>> meetingcall = meetingapi.meetingModel();
        meetingcall.enqueue(new Callback<List<meetingModel>>() {
            @Override
            public void onResponse(Call<List<meetingModel>> call, Response<List<meetingModel>> response) {
                List<meetingModel> data = response.body();
                showmeet(data);
            }

            @Override
            public void onFailure(Call<List<meetingModel>> call, Throwable t) {

            }
        });
    }

    public void showmeet(List<meetingModel> data) {

        meet_adapter = new Meeting_List_Adapater(data, fac_list_meeting.this);
        meet_view.setAdapter(meet_adapter);

        meet_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent meet = new Intent(fac_list_meeting.this, fac_view_meeting.class);
                meet.putExtra("date", data.get(position).getDate_meet());
                meet.putExtra("time", data.get(position).getTime_meet());
                meet.putExtra("topic", data.get(position).getTopic_meet());
                meet.putExtra("room", data.get(position).getRoom_no());
                meet.putExtra("description", data.get(position).getDesc_meet());
                startActivity(meet);

            }
        });
    }

    public static class Meeting_List_Adapater extends BaseAdapter {

        private final List<meetingModel> meeting_array;
        private final List<meetingModel> meeting_arrayFiltered;
        private final Context context;
        private final LayoutInflater inflater;

        public Meeting_List_Adapater(List<meetingModel> meeting_array, Context context) {
            this.meeting_array = meeting_array;
            meeting_arrayFiltered = new ArrayList<meetingModel>();
            meeting_arrayFiltered.addAll(meeting_array);
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return meeting_array.size();
        }

        @Override
        public Object getItem(int position) {
            return meeting_array.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder {
            TextView title, time;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;


            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.student_attendance_items, null);
                holder.title = convertView.findViewById(R.id.enrollment);
                holder.time = convertView.findViewById(R.id.name);

                convertView.setTag(
                        holder
                );
            } else {
                holder = (ViewHolder) convertView.getTag();

            }
            if (position % 2 == 1)
                convertView.setBackgroundColor(context.getResources().getColor(R.color.gray));
            else convertView.setBackgroundColor(context.getResources().getColor(R.color.white));


            holder.title.setText(meeting_array.get(position).getTopic_meet());
            holder.time.setText(meeting_array.get(position).getDate_meet() + " At " + meeting_array.get(position).getTime_meet());
            if (position % 2 == 1)
                convertView.setBackgroundColor(context.getResources().getColor(R.color.gray));
            else convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
            return convertView;
        }
    }
}


