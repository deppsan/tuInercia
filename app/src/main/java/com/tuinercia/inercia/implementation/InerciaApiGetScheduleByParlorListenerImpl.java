package com.tuinercia.inercia.implementation;

import android.support.v4.app.DialogFragment;

import com.tuinercia.inercia.DTO.Reservation;
import com.tuinercia.inercia.DTO.Schedule;
import com.tuinercia.inercia.activities.EstudioAgenda;
import com.tuinercia.inercia.fragments.dialogs.ErrorServerDialog;
import com.tuinercia.inercia.fragments.dialogs.GeneralDialogFragment;
import com.tuinercia.inercia.interfaces.InerciaApiGetScheduleByParlorListener;

import java.util.ArrayList;

/**
 * Created by ricar on 02/02/2018.
 */

public class InerciaApiGetScheduleByParlorListenerImpl implements InerciaApiGetScheduleByParlorListener {
    EstudioAgenda estudioAgenda;

    public InerciaApiGetScheduleByParlorListenerImpl(EstudioAgenda estudioAgenda) {
        this.estudioAgenda = estudioAgenda;
    }

    @Override
    public void onErrorServer(int statusCode) {
        DialogFragment dialog = new ErrorServerDialog();
        dialog.setCancelable(false);
        dialog.show(estudioAgenda.getSupportFragmentManager(),null);
    }

    @Override
    public void onGetScheduleByParlor(ArrayList<Schedule[]> schedules, ArrayList<Reservation[]> reservations) {

        for (Schedule[] arr_s : schedules){
            for(Schedule s : arr_s){
                s.setSelected(false);
            }
        }
        estudioAgenda.setSchedules(schedules);
        if(schedules.size() > 0){
            estudioAgenda.chargeScheduleList(schedules.get(0));
        }
        estudioAgenda.setReservaciones_array(reservations);
        if (reservations.size() > 0){
            estudioAgenda.chargeReservationList(reservations.get(0));
        }
    }

    @Override
    public void onFailGetScheduleByParlor(String errorMessage) {
        GeneralDialogFragment.getInstance(errorMessage,"Aceptar",null)
                .show(estudioAgenda.getSupportFragmentManager(),null);
    }
}
