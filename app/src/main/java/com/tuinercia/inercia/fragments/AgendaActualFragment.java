package com.tuinercia.inercia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tuinercia.inercia.R;
import com.tuinercia.inercia.adapter.CheckInAdapter;
import com.tuinercia.inercia.utils.TypeFaceCustom;

import java.util.ArrayList;

/**
 * Created by ricar on 17/10/2017.
 */

public class AgendaActualFragment extends Fragment {

    ListView list_agenda;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_view_general,container,false);

        ArrayList<objectAlgo> data = new ArrayList<>();
        data.add(new objectAlgo("JUE 5 DIC", "6:00 PM","MAD COMPLEX","ESCALADA","JOSE LOPEZ",true));
        data.add(new objectAlgo("VIE 6 DIC", "6:00 PM","VY YOGA","ASHTANGA YOGA RITMO ZEN","ADRIANA",false));

        list_agenda = (ListView) v.findViewById(R.id.list_general);

        list_agenda.setAdapter(new CheckInAdapter(R.layout.object_clases_agendadas,getContext(),data) {
            @Override
            public void onEntradaSet(View v, HolderView mHolder) {
                mHolder.button = (Button) v.findViewById(R.id.button_check_in);
                mHolder.clase = (TextView) v.findViewById(R.id.text_clase);
                mHolder.fecha = (TextView) v.findViewById(R.id.text_fecha);
                mHolder.hora = (TextView) v.findViewById(R.id.text_hora);
                mHolder.estudio = (TextView) v.findViewById(R.id.text_estudio);
                mHolder.entrador = (TextView) v.findViewById(R.id.text_entrenador);
            }

            @Override
            public void onEntrada(Object objects, HolderView mHolder, int position) {
                objectAlgo o = (objectAlgo) objects;
                mHolder.clase.setText(o.clase);
                mHolder.clase.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);
                mHolder.fecha.setText(o.fecha + " ");
                mHolder.fecha.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);
                mHolder.hora.setText(o.hora);
                mHolder.hora.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);
                mHolder.estudio.setText(o.estudio + " ");
                mHolder.estudio.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);
                mHolder.entrador.setText(o.entrenador);
                mHolder.entrador.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);

                mHolder.button.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_BOLD_TYPE_FACE);
            }
        });


        return v;
    }

    class objectAlgo{
        public String fecha,hora,estudio,clase,entrenador;
        public boolean isAgendado;

        public objectAlgo(String fecha, String hora, String estudio, String clase, String entrenador, boolean isAgendado) {
            this.fecha = fecha;
            this.hora = hora;
            this.estudio = estudio;
            this.clase = clase;
            this.entrenador = entrenador;
            this.isAgendado = isAgendado;
        }
    }
}
