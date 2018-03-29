package com.tuinercia.inercia.implementation;

import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.Toast;

import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.AgendaActualFragment;
import com.tuinercia.inercia.fragments.dialogs.GeneralDialogFragment;
import com.tuinercia.inercia.interfaces.InerciaApiCheckInBookingListener;

/**
 * Created by ricar on 15/03/2018.
 */

public class InerciaApiCheckInBookingListenerAgendaImpl implements InerciaApiCheckInBookingListener {

    AgendaActualFragment agendaActualFragment;

    public InerciaApiCheckInBookingListenerAgendaImpl(AgendaActualFragment agendaActualFragment) {
        this.agendaActualFragment = agendaActualFragment;
    }

    @Override
    public void onCheckInBookingSuccess(String responseMessage, String code) {

        GeneralDialogFragment.getInstance(responseMessage,"Aceptar",null)
                             .show(agendaActualFragment.getFragmentManager(),null);
        Button btn = agendaActualFragment.getButton_selected();

        btn.setEnabled(false);
        btn.setText(code);
        btn.setTextColor(ContextCompat.getColor(agendaActualFragment.getmContext(),R.color.colorPrimary));

        agendaActualFragment.setButton_selected(null);
    }

    @Override
    public void onCheckInBookingFail(String errorMessage) {
        GeneralDialogFragment.getInstance(errorMessage,"Aceptar",null)
                             .show(agendaActualFragment.getFragmentManager(),null);
    }

    @Override
    public void onErrorServer(int statusCode) {
        Toast.makeText(agendaActualFragment.getmContext(), Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
    }
}
