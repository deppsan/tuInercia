package com.tuinercia.inercia.interfaces;

/**
 * Created by ricar on 17/01/2018.
 */

public interface InerciaApiCancelBookingListener {
    void onCancelBookingSuccess(String responseMessage);
    void onCancelBookingFail(String errorMessage);
    void onErrorServer(int statusCode);
}
