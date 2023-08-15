package com.example.attendanceacademia.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceacademia.Models.timetableModel;
import com.example.attendanceacademia.R;

import java.util.List;

public class timetableAdapter extends RecyclerView.Adapter<timetableAdapter.ViewHolder> {

    Context context;
    List<timetableModel> timetableModelList;


    public timetableAdapter(Context context, List<timetableModel> timetableModelList){
        this.context = context;
        this.timetableModelList = timetableModelList;
    }

    @NonNull
    @Override
    public timetableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.timetableview,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull timetableAdapter.ViewHolder holder, int position) {
            timetableModel timetableModel = timetableModelList.get(position);//0 row
            holder.time_tv.setText(timetableModel.getStart_time()+" to "+timetableModel.getEnd_time());
            holder.facsub_tv.setText(timetableModel.getFaculty_name()+"-"+timetableModel.getSubject_name());
            if(timetableModel.getBatch_table()!=null){
                holder.roombat_tv.setText("  "+timetableModel.getRoom_lectlab_no()+"/"+timetableModel.getBatch_table());
            }else{
                holder.roombat_tv.setText("  "+timetableModel.getRoom_lectlab_no()+"/"+timetableModel.getDiv_table());
            }
    }


    @Override
    public int getItemCount() {
        return timetableModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time_tv,facsub_tv,roombat_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time_tv = itemView.findViewById(R.id.time_tv);
            facsub_tv = itemView.findViewById(R.id.facsub_tv);
            roombat_tv = itemView.findViewById(R.id.roombat_tv);
        }
    }
}
