package com.tuinercia.inercia.interfaces;

import com.tuinercia.inercia.DTO.Zone;

/**
 * Created by ricar on 03/11/2017.
 */

public interface InerciaApiGetZonesListener {
    void onZonesReceived(Zone[] zones);
    void onErrorServer(int statusCode);
    void onZonesNoReceived(String errorMessage);
}
