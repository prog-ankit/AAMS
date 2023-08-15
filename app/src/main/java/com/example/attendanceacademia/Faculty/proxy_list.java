package com.example.attendanceacademia.Faculty;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Models.facultyModel;
import com.example.attendanceacademia.Models.proxyModel;
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

public class proxy_list extends AppCompatActivity {

    ListView proxylistView;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat formatter;
    Date current;
    Proxy_Array_Adapter proxy_array_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_list);

        proxylistView = findViewById(R.id.proxy_list);
        formatter = new SimpleDateFormat("dd-MM-yyyy");

        try {
            current = formatter.parse(formatter.format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fetchFacultyNames();
    }

    private void fetchFacultyNames(){

        dashMyapi myfacultyapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<facultyModel>> myfacultyapicall = myfacultyapi.facultyModel();
        myfacultyapicall.enqueue(new Callback<List<facultyModel>>() {
            @Override
            public void onResponse(Call<List<facultyModel>> call, Response<List<facultyModel>> response) {
                List<facultyModel> data = response.body();
                displayData(data);
            }

            @Override
            public void onFailure(Call<List<facultyModel>> call, Throwable t) {

            }
        });
    }
    private void displayData(List<facultyModel> faculties) {

        dashMyapi myproxyapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<proxyModel>> myproxycall = myproxyapi.proxyModel();
        myproxycall.enqueue(new Callback<List<proxyModel>>() {
            @Override
            public void onResponse(Call<List<proxyModel>> call, Response<List<proxyModel>> response) {
                List<proxyModel> data = response.body();

                List<String> facultyNames = new ArrayList<String>();
                for (int i = 0; i< (data != null ? data.size() : 0); i++){
                    String id = String.valueOf(data.get(i).getFrom_faculty());
                    for (int c = 0;c<faculties.size();c++){
                            if (id.equals(faculties.get(c).getFaculty_id())){
                                facultyNames.add(faculties.get(c).getFaculty_name());
                            }
                    }


                }
               showList(data,facultyNames);
            }

            @Override
            public void onFailure(Call<List<proxyModel>> call, Throwable t) {

            }
        });
    }

    private void showList(List<proxyModel> data, List<String> facultyNames) {

        proxy_array_adapter = new Proxy_Array_Adapter(data, facultyNames,proxy_list.this);
        proxylistView.setAdapter(proxy_array_adapter);

        proxylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent proxy = new Intent(proxy_list.this,view_proxy.class);
                proxy.putExtra("from_faculty",facultyNames.get(position));
                proxy.putExtra("to_faculty",data.get(position).getTo_faculty());
                proxy.putExtra("date",data.get(position).getDate_proxy());
                proxy.putExtra("from_time",data.get(position).getFrom_time());
                proxy.putExtra("to_time",data.get(position).getTo_time());
                proxy.putExtra("room_no",data.get(position).getRoom_no());
                proxy.putExtra("description",data.get(position).getDesc_proxy());
                startActivity(proxy);
            }
        });
    }


    public static class Proxy_Array_Adapter extends BaseAdapter{

        private final List<proxyModel> proxyModels;

        private final Context context;
        private final LayoutInflater inflater;
        private final List<String> facultyNames;

        public Proxy_Array_Adapter(List<proxyModel> proxyModels,List<String> facultyNames, Context context) {
            this.proxyModels = proxyModels;
            this.facultyNames = facultyNames;
            this.context = context;
            this.inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return proxyModels.size();
        }

        @Override
        public Object getItem(int position) {
            return proxyModels.get(position);
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
            holder.enrollment.setText("From: " + facultyNames.get(position)  + "  To: " + proxyModels.get(position).getTo_faculty());
            holder.name.setText("On: " +
                    proxyModels.get(position).getDate_proxy() + "  At:" +
                    proxyModels.get(position).getFrom_time());
            if (position % 2 == 1) convertView.setBackgroundColor(context.getResources().getColor(R.color.gray));
            else convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
            return convertView;
        }
    }
}
