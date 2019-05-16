package com.tuinercia.inercia.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tuinercia.inercia.DTO.Schedule;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.dialogs.InformacionClasesDialog;
import com.tuinercia.inercia.interfaces.HorarioRecycleAdapterListener;
import com.tuinercia.inercia.utils.TypeFaceCustom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ricar on 03/10/2017.
 */

public class HorariosRecycleAdapter extends RecyclerView.Adapter<HorariosRecycleAdapter.MyViewHolder>{

    List<Schedule> horarios;
    Context mContext;
    public HorarioRecycleAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView text_horarios;
        public Button btn_reservar;

        public MyViewHolder(View view) {
            super(view);
            text_horarios = (TextView) view.findViewById(R.id.text_horarios);
            btn_reservar = (Button) view.findViewById(R.id.btn_reservar);
            btn_reservar.setTypeface(TypeFaceCustom.getInstance(btn_reservar.getContext()).UBUNTU_BOLD_TYPE_FACE);
            btn_reservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickButtonListener(v,getAdapterPosition());
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickInformationListener(view,getAdapterPosition());
                }
            });
        }
    }


    public HorariosRecycleAdapter(List<Schedule> horarios, Context mContext, HorarioRecycleAdapterListener listener){
        this.horarios = horarios;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.object_estudio_horarios_listener, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Schedule horario = horarios.get(position);
        final String time = horario.getTime();

        if(horario.isSelected()){
            holder.btn_reservar.setText("Cancelar");
        }else{
            holder.btn_reservar.setText("Agendar");
        }


        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateObj = sdf.parse(time);

            holder.text_horarios.setText(new SimpleDateFormat("K:mm a").format(dateObj) + " - " + horario.getName());
        } catch (final ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return horarios.size();
    }
}
