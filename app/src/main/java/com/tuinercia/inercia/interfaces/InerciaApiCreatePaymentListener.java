package com.tuinercia.inercia.interfaces;

/**
 * Created by ricar on 04/04/2018.
 */

public interface InerciaApiCreatePaymentListener {
    void onCreatePaymentError(String errorMessage);
    void onErrorServer(int statusCode);
    void onCreatePaymentSuccess(String message);
}
