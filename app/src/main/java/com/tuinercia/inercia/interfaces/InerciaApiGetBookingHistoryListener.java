package com.tuinercia.inercia.interfaces;

import com.tuinercia.inercia.DTO.Reservation;

import java.util.HashMap;

/**
 * Created by ricar on 12/01/2018.
 */

public interface InerciaApiGetBookingHistoryListener {
    void onGetBookingHistorySuccess(HashMap<String,Reservation> resevations);
    void onGetBookingHistoryError(String errorMessage);
    void onErrorServer(int statusCode);
}
