package com.tuinercia.inercia.interfaces;

import com.tuinercia.inercia.DTO.ReservationTrue;

/**
 * Created by ricar on 06/03/2018.
 */

public interface InerciaApiPendingBookingListener {
    void onGetPenndingSuccess(ReservationTrue[] reservations);
    void onGetPendingError(String errorMessage);
    void onErrorServer(int statusCode);
}
