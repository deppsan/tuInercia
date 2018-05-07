package com.tuinercia.inercia.implementation;

import com.tuinercia.inercia.fragments.AgendaActualFragment;
import com.tuinercia.inercia.interfaces.DialgoFragmentCancelFromAgenda;
import com.tuinercia.inercia.network.InerciaApiClient;

/**
 * Created by ricar on 12/03/2018.
 */

public class DialgoFragmentCancelFromAgendaPasadaListenerImpl implements DialgoFragmentCancelFromAgenda {

    AgendaActualFragment agendaActualFragment;

    public DialgoFragmentCancelFromAgendaPasadaListenerImpl(AgendaActualFragment agendaActualFragment) {
        this.agendaActualFragment = agendaActualFragment;
    }

    @Override
    public void onCancelarConfirmacionCancelacionReservacion() {

    }

    @Override
    public void onConfirmaCanccelacionReservacion(int id) {
        InerciaApiClient.getInstance(agendaActualFragment.getmContext()).cancelBooking(Integer.toString(agendaActualFragment.getUser().getId()),Integer.toString(id),agendaActualFragment.getInerciaApiCancelBookingListenerAgenda(),agendaActualFragment.getLoadingViewManager());
    }
}
