package com.tuinercia.inercia.interfaces;

import com.tuinercia.inercia.DTO.Disciplines;

import java.util.HashMap;

/**
 * Created by ricar on 12/01/2018.
 */

public interface InerciaApiGetDisciplinesByIdListener {
    void OnGetDisciplinesByIdSuccefull(HashMap<String,Disciplines> disciplines);
    void onGetDisciplinesByIdError(String errorMessage);
    void onErrorServer(int statusCode);
}
