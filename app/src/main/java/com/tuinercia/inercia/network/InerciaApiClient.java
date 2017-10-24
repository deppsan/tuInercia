package com.tuinercia.inercia.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tuinercia.inercia.interfaces.InerciaApiValidarUsuario;
import com.tuinercia.inercia.network.conection.conexionHTTP;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ricar on 24/10/2017.
 */

public class InerciaApiClient {
    private static final String BASE_URL = "https://inercia-ivanaguilar.c9users.io/";
    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    private static InerciaApiClient instance;

    public static synchronized InerciaApiClient getInstance(){
        if (instance == null){
            instance = new InerciaApiClient();
        }
        return instance;
    }

    public void validarUsuario(String correo, String contraseña, Context mContext, final InerciaApiValidarUsuario listener){
        HashMap<String,String> params = new HashMap<>();

        params.put("email",correo);
        params.put("password",contraseña);

        new conexionHTTP().getInstance().postJsonResponse(mContext,BASE_URL + "validate_user/", params,
                new conexionHTTP.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        listener.onUsuarioCorrecto();
                    }

                    @Override
                    public void onError(int statusCode) {
                        listener.onErrorServer();
                    }
                });
    }
}


