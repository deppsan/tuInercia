package com.tuinercia.inercia.interfaces;

import com.tuinercia.inercia.DTO.Disciplines;

import java.util.ArrayList;

/**
 * Created by ricar on 26/10/2017.
 */

public interface InerciaApiGetDiciplinasListener {
    void chargeDiciplinas(Disciplines[] disciplines);
    void failChargeDiciplines(String errorMessage);
    void onErrorServer(int statusCode);
}
