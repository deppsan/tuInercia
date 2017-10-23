package com.tuinercia.inercia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tuinercia.inercia.R;
import com.tuinercia.inercia.utils.TypeFaceCustom;

/**
 * Created by ricar on 20/09/2017.
 */

public class CrearCuentaFragment extends Fragment {

    EditText text_email_crear,text_confirmar_email_crear,text_contraseña_crear,text_contraseña_confirmar_crear;
    Button button_crear;

    public static final String FRAGMENT_TAG = "CrearCuentaFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crear_cuenta,container,false);
        
        text_confirmar_email_crear = (EditText) v.findViewById(R.id.text_confirmar_email_crear);
        text_contraseña_confirmar_crear = (EditText) v.findViewById(R.id.text_contraseña_confirmar_crear);
        text_contraseña_crear = (EditText) v.findViewById(R.id.text_contraseña_crear);
        text_email_crear = (EditText) v.findViewById(R.id.text_email_crear);

        button_crear = (Button) v.findViewById(R.id.button_crear);

        text_contraseña_confirmar_crear.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);
        text_confirmar_email_crear.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);
        text_email_crear.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);
        text_contraseña_crear.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);


        button_crear.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);


        return v;
    }
}
