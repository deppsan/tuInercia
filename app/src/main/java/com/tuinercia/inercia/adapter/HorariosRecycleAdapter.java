package com.tuinercia.inercia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tuinercia.inercia.R;
import com.tuinercia.inercia.model.Horario;
import com.tuinercia.inercia.utils.TypeFaceCustom;

import java.util.List;

/**
 * Created by ricar on 03/10/2017.
 */

public class HorariosRecycleAdapter extends RecyclerView.Adapter<HorariosRecycleAdapter.MyViewHolder> {

    List<Horario> horarios;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView text_horarios;
        public Button btn_reservar;

        public MyViewHolder(View view) {
            super(view);
            text_horarios = (TextView) view.findViewById(R.id.text_horarios);
            btn_reservar = (Button) view.findViewById(R.id.btn_reservar);
            btn_reservar.setTypeface(TypeFaceCustom.getInstance(btn_reservar.getContext()).UBUNTU_BOLD_TYPE_FACE);
        }
    }

    public HorariosRecycleAdapter(List<Horario> horarios){
        this.horarios = horarios;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.object_estudio_horarios_listener, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Horario horario = horarios.get(position);
        holder.text_horarios.setText(horario.getHorario_text());

    }

    @Override
    public int getItemCount() {
        return horarios.size();
    }
}
