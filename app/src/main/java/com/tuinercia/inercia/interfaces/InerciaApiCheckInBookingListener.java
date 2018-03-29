package com.tuinercia.inercia.interfaces;

/**
 * Created by ricar on 17/01/2018.
 */

public interface InerciaApiCheckInBookingListener {
    void onCheckInBookingSuccess(String responseMessage, String code);
    void onCheckInBookingFail(String errorMessage);
    void onErrorServer(int statusCode);
}
