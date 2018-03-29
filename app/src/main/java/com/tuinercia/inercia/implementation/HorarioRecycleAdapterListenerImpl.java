package com.tuinercia.inercia.implementation;

import android.view.View;
import android.widget.Button;

import com.tuinercia.inercia.DTO.Schedule;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.activities.EstudioAgenda;
import com.tuinercia.inercia.fragments.dialogs.ConfirmarReservarScheduleDialog;
import com.tuinercia.inercia.interfaces.HorarioRecycleAdapterListener;

/**
 * Created by ricar on 19/02/2018.
 */

public class HorarioRecycleAdapterListenerImpl implements HorarioRecycleAdapterListener {
    EstudioAgenda estudioAgenda;

    public HorarioRecycleAdapterListenerImpl(EstudioAgenda estudioAgenda) {
        this.estudioAgenda = estudioAgenda;    }

    @Override
    public void onClickButtonListener(View v, int position) {
        Schedule s = estudioAgenda.getHorarios().get(position);
        Button btn = (Button) v.findViewById(R.id.btn_reservar);
        estudioAgenda.setBtn_seleccion(btn);

        if(btn.getText().toString().equalsIgnoreCase(estudioAgenda.getString(R.string.btn_agendar))){
            ConfirmarReservarScheduleDialog dialog = ConfirmarReservarScheduleDialog.newInstance(s.getId());
            dialog.show(estudioAgenda.getSupportFragmentManager(),"");
        }else if(btn.getText().toString().equalsIgnoreCase(estudioAgenda.getString(R.string.btn_cancelar))){
            estudioAgenda.cancelarReservacion(btn.getTag().toString());
        }
    }
}
