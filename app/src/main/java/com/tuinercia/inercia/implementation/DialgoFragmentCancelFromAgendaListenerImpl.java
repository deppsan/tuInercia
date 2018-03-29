package com.tuinercia.inercia.implementation;

import com.tuinercia.inercia.activities.EstudioAgenda;
import com.tuinercia.inercia.interfaces.DialgoFragmentCancelFromAgenda;
import com.tuinercia.inercia.network.InerciaApiClient;
import com.tuinercia.inercia.utils.UtilsSharedPreference;

/**
 * Created by ricar on 12/03/2018.
 */

public class DialgoFragmentCancelFromAgendaListenerImpl implements DialgoFragmentCancelFromAgenda {
    EstudioAgenda estudioAgenda;

    public DialgoFragmentCancelFromAgendaListenerImpl(EstudioAgenda estudioAgenda) {
        this.estudioAgenda = estudioAgenda;
    }

    @Override
    public void onCancelarConfirmacionCancelacionReservacion() {

    }

    @Override
    public void onConfirmaCanccelacionReservacion(int id) {
        InerciaApiClient.getInstance(estudioAgenda).cancelBooking(Integer.toString(estudioAgenda.getUser().getId()),Integer.toString(id),estudioAgenda.getInerciaApiCancelBookingListener());
    }
}
