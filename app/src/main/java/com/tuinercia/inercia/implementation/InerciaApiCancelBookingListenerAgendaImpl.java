package com.tuinercia.inercia.implementation;

import android.widget.Toast;

import com.tuinercia.inercia.fragments.AgendaActualFragment;
import com.tuinercia.inercia.fragments.dialogs.GeneralDialogFragment;
import com.tuinercia.inercia.interfaces.InerciaApiCancelBookingListener;
import com.tuinercia.inercia.network.InerciaApiClient;

/**
 * Created by ricar on 07/03/2018.
 */

public class InerciaApiCancelBookingListenerAgendaImpl implements InerciaApiCancelBookingListener {

    AgendaActualFragment agendaActualFragment;

    public InerciaApiCancelBookingListenerAgendaImpl(AgendaActualFragment agendaActualFragment) {
        this.agendaActualFragment = agendaActualFragment;
    }

    @Override
    public void onCancelBookingSuccess(String responseMessage) {
        GeneralDialogFragment.getInstance(responseMessage,"Aceptar",null)
                             .show(agendaActualFragment.getFragmentManager(),null);
        InerciaApiClient.getInstance(agendaActualFragment.getmContext()).pendingBookin(Integer.toString(agendaActualFragment.getUser().getId()), agendaActualFragment.getInerciaApiPendingBookingListener());
    }

    @Override
    public void onCancelBookingFail(String errorMessage) {
        Toast.makeText(agendaActualFragment.getmContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorServer(int statusCode) {
        Toast.makeText(agendaActualFragment.getmContext(), Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
    }
}
