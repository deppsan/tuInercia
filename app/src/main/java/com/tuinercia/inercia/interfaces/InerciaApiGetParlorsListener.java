package com.tuinercia.inercia.interfaces;

import com.tuinercia.inercia.DTO.Parlor;

import java.util.ArrayList;

/**
 * Created by ricar on 02/11/2017.
 */

public interface InerciaApiGetParlorsListener {
    void onGetParlorsSuccess(ArrayList<Parlor> parlors);
    void onFailChargeParlors(String errorMessage);
    void onErrorServer(int statusCode);
}
