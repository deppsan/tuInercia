package com.tuinercia.inercia.interfaces;

/**
 * Created by ricar on 12/01/2018.
 */

public interface InerciaApiCreateBookingListener {
    void onCreateBookingSuccess(String responseMessage, int reservation);
    void onCreateBookingError(String errorMessage, String errorType);
    void onErrorServer(int statusCode);
}
