package com.tuinercia.inercia.network.conection;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ricar on 06/12/2016.
 */

public class conexionHTTP{

    private final GsonBuilder builder = new GsonBuilder();
    private final Gson gson = builder.create();
    private static conexionHTTP instance = null;
    private RequestQueue queue;


    public conexionHTTP getInstance(){
        if (instance == null){
            instance = new conexionHTTP();
        }
        return instance;
    }


    public conexionHTTP() { }

    /*public void getJsonResponse (Context context, String url, final VolleyCallback callback){
        queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse != null){
                            callback.onError(error.networkResponse.statusCode);
                        }else {
                            callback.onError(500);
                        }
                    }
        });
        queue.add(stringRequest);
    }

    public void putJsonResponse (Context context, String url, final HashMap<String, String> params, final VolleyCallback callback){
        queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse != null){
                            callback.onError(error.networkResponse.statusCode);
                        }else {
                            callback.onError(500);
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        queue.add(stringRequest);
    }*/

    public void postJsonResponse(Context context, String url, final HashMap<String,String> params, final VolleyCallback callback){
        queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse != null){
                            callback.onError(error.networkResponse.statusCode);
                        }else {
                            callback.onError(500);
                        }
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public interface VolleyCallback{
        void onSuccess(JSONObject response);
        void onError(int statusCode);
    }
}
