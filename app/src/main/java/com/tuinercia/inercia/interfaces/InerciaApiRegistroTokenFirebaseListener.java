package com.tuinercia.inercia.interfaces;

public interface InerciaApiRegistroTokenFirebaseListener{
    public void onErrorServer(int statusCode);
    public void onRegistroError(String errorMessage);
    public void onRegistroExitoso();
}
