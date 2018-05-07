package com.tuinercia.inercia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuinercia.inercia.DTO.Schedule;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.utils.TypeFaceCustom;

import java.util.List;

public class HistoryScheduleRecycleAdapter extends RecyclerView.Adapter<HistoryScheduleRecycleAdapter.MyViewHolder> {

    List<Schedule> schedules;
    Context mContext;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.object_schedules_history, parent, false);

        return new MyViewHolder(itemView);
    }

    public HistoryScheduleRecycleAdapter(List<Schedule> schedules, Context mContext) {
        this.schedules = schedules;
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Schedule schedule = schedules.get(position);

        holder.text_object_schedule_time.setText(schedule.getTime());
        holder.text_object_schedule_instructor.setText(schedule.getInst_name());
        holder.text_object_schedule_date.setText(schedule.getDate());
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView text_object_schedule_time, text_object_schedule_date, text_object_schedule_instructor;


        public MyViewHolder(View itemView) {
            super(itemView);

            text_object_schedule_date = (TextView) itemView.findViewById(R.id.text_object_schedule_date);
            text_object_schedule_instructor = (TextView) itemView.findViewById(R.id.text_object_schedule_instructor);
            text_object_schedule_time = (TextView) itemView.findViewById(R.id.text_object_schedule_time);

            text_object_schedule_date.setTypeface(TypeFaceCustom.getInstance(text_object_schedule_date.getContext()).UBUNTU_BOLD_TYPE_FACE);
            text_object_schedule_instructor.setTypeface(TypeFaceCustom.getInstance(text_object_schedule_instructor.getContext()).UBUNTU_BOLD_TYPE_FACE);
            text_object_schedule_time.setTypeface(TypeFaceCustom.getInstance(text_object_schedule_time.getContext()).UBUNTU_BOLD_TYPE_FACE);

        }
    }
}
