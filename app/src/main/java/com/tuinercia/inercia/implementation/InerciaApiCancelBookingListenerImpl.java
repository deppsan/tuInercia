package com.tuinercia.inercia.implementation;

import android.widget.Toast;

import com.tuinercia.inercia.R;
import com.tuinercia.inercia.activities.EstudioAgenda;
import com.tuinercia.inercia.fragments.dialogs.GeneralDialogFragment;
import com.tuinercia.inercia.interfaces.InerciaApiCancelBookingListener;

/**
 * Created by ricar on 27/02/2018.
 */

public class InerciaApiCancelBookingListenerImpl implements InerciaApiCancelBookingListener {

    EstudioAgenda estudioAgenda;

    public InerciaApiCancelBookingListenerImpl(EstudioAgenda estudioAgenda) {
        this.estudioAgenda = estudioAgenda;
    }

    @Override
    public void onCancelBookingSuccess(String responseMessage) {
        GeneralDialogFragment.getInstance(responseMessage,"Aceptar",null)
                .show(estudioAgenda.getSupportFragmentManager(),null);
        estudioAgenda.getBtn_seleccion().setText(R.string.btn_agendar);
//        estudioAgenda.getBtn_seleccion().setTag(reservation);
        estudioAgenda.setBtn_seleccion(null);
    }

    @Override
    public void onCancelBookingFail(String errorMessage) {
        Toast.makeText(estudioAgenda, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorServer(int statusCode) {
        Toast.makeText(estudioAgenda, Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
    }
}
