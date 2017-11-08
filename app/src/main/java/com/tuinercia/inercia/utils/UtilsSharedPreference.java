package com.tuinercia.inercia.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.activities.LoginMain;
import com.tuinercia.inercia.activities.MainPage;
import com.tuinercia.inercia.network.InerciaApiClient;

/**
 * Created by ricar on 25/10/2017.
 */

public class UtilsSharedPreference {

    private static final String PREFS_NAME = "com.tuinercia.sharedPreferance";

    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String USER_EMAIL = "UserEmail";
    private static final String USER_ID = "UserId";

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static UtilsSharedPreference instance;

    private Context mContext;

    private UtilsSharedPreference(Context context){
        this.mContext = context;
        preferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor  = preferences.edit();
    }

    public static synchronized UtilsSharedPreference getInstance(Context context){
        if (instance == null){
            instance = new UtilsSharedPreference(context);
        }
        return instance;
    }

    public void setUserJsonString(User user){
        String jsonString = InerciaApiClient.gson.toJson(user);

        editor.putBoolean(IS_LOGGED_IN,true);
        editor.putString(USER_EMAIL,user.getEmail());
        editor.putInt(USER_ID,user.getId());
        editor.putString("user",jsonString);

        editor.commit();
    }

    public void checkLogin(){
        if (!this.isLoggedIn()){
            Intent i = new Intent(mContext, LoginMain.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }
    }

    public boolean isLoggedIn(){
        return preferences.getBoolean(IS_LOGGED_IN,false);
    }

    public User getUser(){
        User user = null;
        String jsonString = preferences.getString("user","error");
        if (!jsonString.equals("error")){
            user = InerciaApiClient.gson.fromJson(jsonString,User.class);
        }

        return user;
    }

    public void logOutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(mContext, LoginMain.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }
}
