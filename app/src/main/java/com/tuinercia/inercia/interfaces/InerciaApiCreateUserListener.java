package com.tuinercia.inercia.interfaces;

import com.tuinercia.inercia.DTO.User;

/**
 * Created by ricar on 13/12/2017.
 */

public interface InerciaApiCreateUserListener {
    void onCreateUserSuccefull(User user);
    void onCreateUserError(String errorMessage);
    void onErrorServer(int statusCode);
}
