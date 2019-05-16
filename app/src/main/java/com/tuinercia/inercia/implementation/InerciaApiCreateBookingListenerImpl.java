package com.tuinercia.inercia.implementation;

import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.tuinercia.inercia.R;
import com.tuinercia.inercia.activities.EstudioAgenda;
import com.tuinercia.inercia.fragments.dialogs.AvisoFaltaDeCuentaPro;
import com.tuinercia.inercia.fragments.dialogs.ErrorServerDialog;
import com.tuinercia.inercia.fragments.dialogs.GeneralDialogFragment;
import com.tuinercia.inercia.interfaces.InerciaApiCreateBookingListener;

/**
 * Created by ricar on 19/02/2018.
 */

public class InerciaApiCreateBookingListenerImpl implements InerciaApiCreateBookingListener {

    EstudioAgenda estudioAgenda;

    public InerciaApiCreateBookingListenerImpl(EstudioAgenda estudioAgenda) {
        this.estudioAgenda = estudioAgenda;
    }

    @Override
    public void onCreateBookingSuccess(String responseMessage, int reservation) {
        GeneralDialogFragment.getInstance(responseMessage,"Aceptar",null)
                             .show(estudioAgenda.getSupportFragmentManager(),null);
        estudioAgenda.getBtn_seleccion().setText(R.string.btn_cancelar);
        estudioAgenda.getBtn_seleccion().setTag(reservation);
        estudioAgenda.setBtn_seleccion(null);
    }

    @Override
    public void onCreateBookingError(String errorMessage, String errorType) {

        switch (errorType){
            case "1":
                AvisoFaltaDeCuentaPro dialog = new AvisoFaltaDeCuentaPro();
                dialog.show(estudioAgenda.getSupportFragmentManager(),AvisoFaltaDeCuentaPro.FRAGMENT_TAG);
                break;
            default:
                GeneralDialogFragment.getInstance(errorMessage,"Aceptar",null)
                        .show(estudioAgenda.getSupportFragmentManager(),null);
                break;
        }
    }

    @Override
    public void onErrorServer(int statusCode) {
        DialogFragment dialog = new ErrorServerDialog();
        dialog.setCancelable(false);
        dialog.show(estudioAgenda.getSupportFragmentManager(),null);
    }
}
