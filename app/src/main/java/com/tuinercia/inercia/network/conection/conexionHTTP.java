package com.tuinercia.inercia.network.conection;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tuinercia.inercia.interfaces.LoadingViewManager;

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

    public void postJsonResponse(Context context, String url, final HashMap<String,String> params, final VolleyCallback callback, final LoadingViewManager loadingViewManager){
        loadingViewManager.showLoadingView();
        queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                        loadingViewManager.hideLoadingView();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse != null){
                    callback.onError(error.networkResponse.statusCode);
                }else {
                    callback.onError(500);
                }
                loadingViewManager.hideLoadingView();
            }
        });
        jsonObjectRequest.setRetryPolicy( new DefaultRetryPolicy(0,-1,0));
        queue.add(jsonObjectRequest);
    }

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
        jsonObjectRequest.setRetryPolicy( new DefaultRetryPolicy(0,-1,0));
        queue.add(jsonObjectRequest);
    }

    public interface VolleyCallback{
        void onSuccess(JSONObject response);
        void onError(int statusCode);
    }
}
