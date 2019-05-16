package com.tuinercia.inercia.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.implementation.InerciaApiRegistroTokenFirebaseImpl;
import com.tuinercia.inercia.network.InerciaApiClient;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        Context mContext = getApplicationContext();
        User user = UtilsSharedPreference.getInstance(mContext).getUser();

        if (user != null){
            String firebaseToken  = FirebaseInstanceId.getInstance().getToken();
            InerciaApiRegistroTokenFirebaseImpl inerciaApiRegistroTokenFirebase = new InerciaApiRegistroTokenFirebaseImpl();

            Log.d("FIREBASE_TOKEN", firebaseToken);

            InerciaApiClient.getInstance(mContext).registroTokenFirebase(user.getId(),firebaseToken,inerciaApiRegistroTokenFirebase);
        }
    }
}
