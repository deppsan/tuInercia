package com.tuinercia.inercia.interfaces;

/**
 * Created by ricar on 24/10/2017.
 */

public interface InerciaApiValidarUsuario {
    void onUsuarioCorrecto();
    void onUsuarioIncorrecto();
    void onErrorServer();
}
