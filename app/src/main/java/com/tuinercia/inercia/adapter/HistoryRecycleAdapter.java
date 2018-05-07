package com.tuinercia.inercia.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuinercia.inercia.DTO.History;
import com.tuinercia.inercia.DTO.Schedule;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.utils.TypeFaceCustom;

import java.util.ArrayList;
import java.util.List;

public class HistoryRecycleAdapter extends RecyclerView.Adapter<HistoryRecycleAdapter.MyViewHolder> {

    List<History> trainings;
    Context mContext;
    static int mExpandedPosition = -1;
    static int previousExpandedPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text_nombre_parlor,text_nombre_training;
        RecyclerView rcy_schedules_history;
        ImageView img_arrow_down;


        public MyViewHolder(View itemView) {
            super(itemView);
            text_nombre_parlor = (TextView) itemView.findViewById(R.id.text_nombre_parlor);
            text_nombre_training = (TextView) itemView.findViewById(R.id.text_nombre_training);
            rcy_schedules_history = (RecyclerView) itemView.findViewById(R.id.rcy_schedules_history);

            text_nombre_training.setTypeface(TypeFaceCustom.getInstance(text_nombre_training.getContext()).UBUNTU_BOLD_TYPE_FACE);
            text_nombre_parlor.setTypeface(TypeFaceCustom.getInstance(text_nombre_training.getContext()).UBUNTU_BOLD_TYPE_FACE);

            img_arrow_down = (ImageView) itemView.findViewById(R.id.img_arrow_down);
        }
    }

    public HistoryRecycleAdapter(List<History> trainings, Context mContext) {
        this.trainings = trainings;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.object_mis_clases_history, parent, false);
        return  new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final boolean isExpanded = position == mExpandedPosition;

        History training = trainings.get(position);

        holder.text_nombre_training.setText(training.getName());
        holder.text_nombre_parlor.setText(training.getSucursal());

        List<Schedule> schedules = new ArrayList<>();

        for (Schedule s : training.getSchedules()){
            schedules.add(s);
        }


        HistoryScheduleRecycleAdapter historyScheduleRecycleAdapter = new HistoryScheduleRecycleAdapter(schedules, mContext);

        final RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(0);
        rotateAnimation.setFillAfter(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext.getApplicationContext());
        holder.rcy_schedules_history.setLayoutManager(layoutManager);
        holder.rcy_schedules_history.setItemAnimator(new DefaultItemAnimator());
        holder.rcy_schedules_history.setAdapter(historyScheduleRecycleAdapter);

        holder.rcy_schedules_history.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);

        if(isExpanded)
            previousExpandedPosition = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;

                holder.img_arrow_down.setAnimation(rotateAnimation);

                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }
}
