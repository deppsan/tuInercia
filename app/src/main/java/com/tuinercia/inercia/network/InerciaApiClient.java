package com.tuinercia.inercia.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tuinercia.inercia.DTO.Disciplines;
import com.tuinercia.inercia.DTO.Parlor;
import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.DTO.Zone;
import com.tuinercia.inercia.interfaces.InerciaApiGetDiciplinasListener;
import com.tuinercia.inercia.interfaces.InerciaApiGetParlorsListener;
import com.tuinercia.inercia.interfaces.InerciaApiGetZonesListener;
import com.tuinercia.inercia.interfaces.InerciaApiValidarUsuario;
import com.tuinercia.inercia.network.conection.conexionHTTP;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by ricar on 24/10/2017.
 */

public class InerciaApiClient {
    private static final String BASE_URL = "https://inercia-stg.herokuapp.com/inercia_apis/";
    private static final GsonBuilder builder = new GsonBuilder();

    private static final String EMAIL_HEAD_PARAM = "email";
    private static final String PASSWORD_HEAD_PARAM = "password";
    private static final String DISCIPLINE_HEAD_PARAM = "discipline";

    private static final String DICIPLINES_URL = "req_disciplines/";
    private static final String VALIDATE_USER_URL = "validate_user/";
    private static final String PARLORS_URL = "req_parlors_by_discipline/";
    private static final String ZONES_URL = "req_zones/";

    private static final String DISCIPLINE_RESULT_NAME = "disciplines" ;
    private static final String PARLOR_RESULT_NAME = "parlors" ;
    private static final String RESPONSE_RESULT_NAME = "response" ;
    private static final String USER_RESULT_NAME = "user" ;
    private static final String ZONE_RESULT_NAME = "zones" ;


    public static final Gson gson = builder.create();


    private static InerciaApiClient instance;
    private Context mContext;

    public static synchronized InerciaApiClient getInstance(Context context){
        if (instance == null){
            instance = new InerciaApiClient(context);
        }
        return instance;
    }

    private InerciaApiClient(Context mContext){
        this.mContext = mContext;
    }

    public void validarUsuario(String correo, String contraseña, final InerciaApiValidarUsuario listener){
        HashMap<String,String> params = new HashMap<>();

        params.put(EMAIL_HEAD_PARAM,correo);
        params.put(PASSWORD_HEAD_PARAM,contraseña);

        new conexionHTTP().getInstance().postJsonResponse(mContext,BASE_URL + VALIDATE_USER_URL, params,
            new conexionHTTP.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        if (response.getBoolean(RESPONSE_RESULT_NAME) == true){
                            User us = gson.fromJson(response.getString(USER_RESULT_NAME),User.class);
                            listener.onUsuarioCorrecto(us);
                        }else{
                            listener.onUsuarioIncorrecto();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onErrorServer();
                    }
                }
                @Override
                public void onError(int statusCode) {
                    listener.onErrorServer();
                }
            }
        );
    }

    public void getDiciplinas(final InerciaApiGetDiciplinasListener listener){
        HashMap<String,String> params = new HashMap<>();

        new conexionHTTP().getInstance().postJsonResponse(mContext,BASE_URL + DICIPLINES_URL, params,
                new conexionHTTP.VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            if (response.getString(DISCIPLINE_RESULT_NAME) != null){
                                Disciplines[] disciplines = gson.fromJson(response.getString(DISCIPLINE_RESULT_NAME),Disciplines[].class);
                                listener.chargeDiciplinas(disciplines);
                            }else{
                                listener.failChargeDiciplines();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.failChargeDiciplines();
                        }
                    }
                    @Override
                    public void onError(int statusCode) {
                        listener.onErrorServer(statusCode);
                    }
                }
        );
    }

    public void getParlorsByDicipline(String discipline, final InerciaApiGetParlorsListener listener){
        HashMap<String,String> params = new HashMap<>();
        params.put(DISCIPLINE_HEAD_PARAM,discipline);
        new conexionHTTP().getInstance().postJsonResponse(mContext,BASE_URL + PARLORS_URL ,params, new conexionHTTP.VolleyCallback(){
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if(response.getString(PARLOR_RESULT_NAME) != null){
                        ArrayList<Parlor> arrParlors = new ArrayList<>();
                        Parlor[] parlors = gson.fromJson(response.getString(PARLOR_RESULT_NAME),Parlor[].class);
                        for (Parlor p : parlors){
                                arrParlors.add(p);
                        }
                        listener.onGetParlorsSuccess(arrParlors);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onFailChargeParlors();
                }
            }

            @Override
            public void onError(int statusCode) {
                listener.onErrorServer(statusCode);
            }
        });
    }

    public void getAllZones(final InerciaApiGetZonesListener listener){
        HashMap<String,String> params = new HashMap<>();
        new conexionHTTP().getInstance().postJsonResponse(mContext, BASE_URL + ZONES_URL, params, new conexionHTTP.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getString(ZONE_RESULT_NAME)!= null){
                        Zone[] zones = gson.fromJson(response.getString(ZONE_RESULT_NAME),Zone[].class);
                        listener.onZonesReceived(zones);
                    }else{
                        listener.onZonesNoReceived();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    listener.onZonesNoReceived();
                }
            }

            @Override
            public void onError(int statusCode) {
                listener.onErrorServer(statusCode);
            }
        });
    }
}


