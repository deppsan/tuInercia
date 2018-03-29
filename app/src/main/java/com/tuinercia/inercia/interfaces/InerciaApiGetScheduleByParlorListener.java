package com.tuinercia.inercia.interfaces;

import com.tuinercia.inercia.DTO.Reservation;
import com.tuinercia.inercia.DTO.Schedule;

import java.util.ArrayList;

/**
 * Created by ricar on 14/12/2017.
 */

public interface InerciaApiGetScheduleByParlorListener {
    void onErrorServer(int statusCode);
    void onGetScheduleByParlor(ArrayList<Schedule[]> schedules,ArrayList<Reservation[]> reservations);
    void onFailGetScheduleByParlor(String errorMessage);
}
