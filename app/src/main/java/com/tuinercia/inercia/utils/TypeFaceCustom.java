package com.tuinercia.inercia.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by ricar on 17/10/2017.
 */

public class TypeFaceCustom {

    public static Typeface UBUNTU_BOLD_TYPE_FACE;
    public static Typeface UBUNTU_TYPE_FACE;

    private static TypeFaceCustom instance = null;
    Context mContext;

    public static TypeFaceCustom getInstance(Context mContext){

        if (instance == null){
            instance = new TypeFaceCustom(mContext);
        }

        return instance;
    }

    private TypeFaceCustom(Context mContext){
        UBUNTU_BOLD_TYPE_FACE = Typeface.createFromAsset(mContext.getAssets(),"fonts/Ubuntu-Medium.ttf");
        UBUNTU_TYPE_FACE = Typeface.createFromAsset(mContext.getAssets(),"fonts/Ubuntu-Regular.ttf");
    }
}
