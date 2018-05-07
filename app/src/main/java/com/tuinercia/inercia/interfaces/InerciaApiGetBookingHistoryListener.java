package com.tuinercia.inercia.interfaces;

import com.tuinercia.inercia.DTO.History;
import com.tuinercia.inercia.DTO.Reservation;

import java.util.HashMap;

/**
 * Created by ricar on 12/01/2018.
 */

public interface InerciaApiGetBookingHistoryListener {
    void onGetBookingHistorySuccess(History[] actual_history, History[] prev_history, History[] ant_prev_history);
    void onGetBookingHistoryError(String errorMessage);
    void onErrorServer(int statusCode);
}
