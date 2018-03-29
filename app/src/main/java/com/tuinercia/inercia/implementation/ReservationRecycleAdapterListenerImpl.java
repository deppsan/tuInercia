package com.tuinercia.inercia.implementation;

import android.view.View;
import android.widget.Button;

import com.tuinercia.inercia.DTO.Reservation;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.activities.EstudioAgenda;
import com.tuinercia.inercia.fragments.dialogs.ConfirmarReservarScheduleDialog;
import com.tuinercia.inercia.interfaces.ReservationRecycleAdapterListener;

/**
 * Created by ricar on 27/02/2018.
 */

public class ReservationRecycleAdapterListenerImpl implements ReservationRecycleAdapterListener {

    EstudioAgenda estudioAgenda;

    public ReservationRecycleAdapterListenerImpl(EstudioAgenda estudioAgenda) {
        this.estudioAgenda = estudioAgenda;
    }

    @Override
    public void onClickButtonListener(View v, int position) {
        Reservation r = estudioAgenda.getReservaciones().get(position);
        Button btn = (Button) v.findViewById(R.id.btn_reservar);
        estudioAgenda.setBtn_seleccion(btn);
        if (btn.getText().toString().equalsIgnoreCase(estudioAgenda.getString(R.string.btn_cancelar))){
            estudioAgenda.cancelarReservacion(Integer.toString(r.getReservation_id()));
        }else if(btn.getText().toString().equalsIgnoreCase(estudioAgenda.getString(R.string.btn_agendar))){
            ConfirmarReservarScheduleDialog dialog = ConfirmarReservarScheduleDialog.newInstance(r.getId());
            dialog.show(estudioAgenda.getSupportFragmentManager(),"");
        }else if (btn.getText().toString().equalsIgnoreCase(estudioAgenda.getString(R.string.btn_check_in))){

        }
    }
}
