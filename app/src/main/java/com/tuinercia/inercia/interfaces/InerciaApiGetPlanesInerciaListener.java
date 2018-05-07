package com.tuinercia.inercia.interfaces;

import com.tuinercia.inercia.DTO.Membership;

/**
 * Created by ricar on 02/04/2018.
 */

public interface InerciaApiGetPlanesInerciaListener{
    void onGetPlanesListError(String errorMessage);
    void onErrorServer(int statusCode);
    void onGetPlanesListSuccess(Membership[] memberships);
}
