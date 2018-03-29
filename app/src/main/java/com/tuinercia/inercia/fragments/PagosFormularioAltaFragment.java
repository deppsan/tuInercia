package com.tuinercia.inercia.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.dialogs.InformacionCVVDialog;
import com.tuinercia.inercia.fragments.dialogs.InformacionCaducidadDialog;

import io.conekta.conektasdk.Conekta;

/**
 * Created by ricar on 05/10/2017.
 */

public class PagosFormularioAltaFragment extends Fragment implements View.OnClickListener{

    private final String URL = "http://www.google.com";
    public static final String FRAGMENT_TAG = "PagosFormularioAltaFragment";

    Spinner spinner;
    ImageView info_cvc, info_caducidad;
    Activity activity;
    Button button_alta_plan_pago;
    EditText txt_titular_tarjeta, txt_numero_tarjeta, txt_cvc, txt_expiracion_mes, txt_expiracion_año;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_pagos_formulario,container,false);

        spinner = (Spinner) v.findViewById(R.id.spinner_planes_pagos);
        info_cvc = (ImageView) v.findViewById(R.id.image_help_icon_cvc);
        info_caducidad = (ImageView) v.findViewById(R.id.image_vto_help);
        button_alta_plan_pago = (Button) v.findViewById(R.id.button_alta_plan_pago);

        txt_expiracion_año = (EditText) v.findViewById(R.id.txt_expiracion_año);
        txt_titular_tarjeta = (EditText) v.findViewById(R.id.txt_titular_tarjeta);
        txt_numero_tarjeta = (EditText) v.findViewById(R.id.txt_numero_tarjeta);
        txt_cvc = (EditText) v.findViewById(R.id.txt_cvc);
        txt_expiracion_mes = (EditText) v.findViewById(R.id.txt_expiracion_mes);

        activity = getActivity();

        String[] plants = new String[]{
                "Black birch",
                "Bolean birch",
                "Canoe birch",
                "Cherry birch",
                "European weeping birch"
        };

        Conekta.setPublicKey("key_DsUNHQxSdYi81c6oxByvAbg"); //Set public key
        Conekta.setApiVersion("0.2.0"); //Set api version (optional)
        Conekta.collectDevice(activity); //Collect device

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.object_spinner_planes,plants);
        spinner.setAdapter(adapter);

        info_cvc.setOnClickListener(this);
        info_caducidad.setOnClickListener(this);
        button_alta_plan_pago.setOnClickListener(this);

        return v;

    }

    @Override
    public void onClick(View v) {

        DialogFragment dialog;
        switch(v.getId()){
            case R.id.image_help_icon_cvc:
                dialog = new InformacionCVVDialog();
                dialog.show(getFragmentManager(),"");
                break;
            case R.id.image_vto_help:
                dialog = new InformacionCaducidadDialog();
                dialog.show(getFragmentManager(),"");
                break;
            case R.id.button_alta_plan_pago:

                break;
        }
    }
}
