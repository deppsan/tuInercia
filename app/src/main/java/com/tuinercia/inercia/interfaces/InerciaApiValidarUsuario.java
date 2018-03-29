package com.tuinercia.inercia.interfaces;

import com.tuinercia.inercia.DTO.User;

/**
 * Created by ricar on 24/10/2017.
 */

public interface InerciaApiValidarUsuario {
    void onUsuarioCorrecto(User user);
    void onUsuarioIncorrecto(String errorMessage);
    void onErrorServer();
}
