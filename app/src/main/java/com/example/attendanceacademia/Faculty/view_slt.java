package com.example.attendanceacademia.Faculty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendanceacademia.Connection;
import com.example.attendanceacademia.Models.meetingModel;
import com.example.attendanceacademia.Models.sltModel;
import com.example.attendanceacademia.R;
import com.example.attendanceacademia.dashMyapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class view_slt extends AppCompatActivity
{

    ListView slt_view;
    List<String> slt_array = new ArrayList<String>();
    Slt_List_Adapater slt_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slt);

        slt_view = findViewById(R.id.slt_list);
        displayData();
    }

    private void displayData() {

        //call sltModel
        dashMyapi sltapi = Connection.retrofit.create(dashMyapi.class);
        Call<List<sltModel>> sltcall = sltapi.sltModel();
        sltcall.enqueue(new Callback<List<sltModel>>() {
            @Override
            public void onResponse(Call<List<sltModel>> call, Response<List<sltModel>> response) {
                List<sltModel> data = response.body();

                    showslt(data);

            }

            @Override
            public void onFailure(Call<List<sltModel>> call, Throwable t) {

            }
        });
    }

    public void showslt(List<sltModel> data){

        slt_adapter = new Slt_List_Adapater(data,view_slt.this);
       /* slt_array.add(data1.get(i).getType_seminar_lect_test() + ":  " +
                data1.get(i).getTopic_test_lect() + "\nOn: " +
                data1.get(i).getDate_test_lect() + "  At: " +
                data1.get(i).getTime_test_lect());

        //Initialize adapter
        slt_adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, slt_array){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = ((TextView) view.findViewById(android.R.id.text1));
                textView.setMinHeight(250); // Min Height

                // textView.setHeight(200); // Height
                if (position%2==1) view.setBackgroundColor(getResources().getColor(R.color.gray));
                else view.setBackgroundColor(getResources().getColor(R.color.white));
                return view;
            }
        };
*/
        slt_view.setAdapter(slt_adapter);

        slt_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent slt = new Intent(view_slt.this, slt_desc.class);

                slt.putExtra("date",data.get(position).getDate_test_lect());
                slt.putExtra("time",data.get(position).getTime_test_lect());
                slt.putExtra("topic",data.get(position).getTopic_test_lect());
                slt.putExtra("faculty",data.get(position).getLecturer_name());
                slt.putExtra("description",data.get(position).getDesc_lecture());

                startActivity(slt);

            }
        });
    }
    public static class Slt_List_Adapater extends BaseAdapter {

        private final List<sltModel> sltarray;
        private final List<sltModel> slt_arrayFiltered;
        private final Context context;
        private final LayoutInflater inflater;

        public Slt_List_Adapater(List<sltModel> sltarray, Context context) {
            this.sltarray = sltarray;
            slt_arrayFiltered = new ArrayList<sltModel>();
            slt_arrayFiltered.addAll(sltarray);
            this.context = context;
            this.inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return sltarray.size();
        }

        @Override
        public Object getItem(int position) {
            return sltarray.get(position);
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


            holder.title.setText(sltarray.get(position).getType_seminar_lect_test()+" : "+sltarray.get(position).getTopic_test_lect());
            holder.time.setText(sltarray.get(position).getDate_test_lect() + " At " + sltarray.get(position).getTime_test_lect());
            if (position % 2 == 1)
                convertView.setBackgroundColor(context.getResources().getColor(R.color.gray));
            else convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
            return convertView;
        }
    }

}
