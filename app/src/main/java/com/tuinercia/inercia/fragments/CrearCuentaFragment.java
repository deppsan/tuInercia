package com.tuinercia.inercia.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tuinercia.inercia.R;
import com.tuinercia.inercia.implementation.InerciaApiCreateUserListenerImpl;
import com.tuinercia.inercia.network.InerciaApiClient;
import com.tuinercia.inercia.utils.TypeFaceCustom;

/**
 * Created by ricar on 20/09/2017.
 */

public class CrearCuentaFragment extends Fragment implements View.OnClickListener{

    EditText text_email_crear,text_confirmar_email_crear,text_contraseña_crear,text_contraseña_confirmar_crear;
    Button button_crear;

    public static final String FRAGMENT_TAG = "CrearCuentaFragment";
    private CrearCuentaListener listener;
    private InerciaApiCreateUserListenerImpl inerciaApiCreateUser;

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
        button_crear.setOnClickListener(this);

        inerciaApiCreateUser = new InerciaApiCreateUserListenerImpl(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_crear:
                String email = text_email_crear.getText().toString();
                String emailConfirmar = text_confirmar_email_crear.getText().toString();
                String contraseña = text_contraseña_crear.getText().toString();
                String contraseñaConfirmar = text_contraseña_confirmar_crear.getText().toString();

                if(validarConfirmaciones(email, emailConfirmar, contraseña, contraseñaConfirmar)){
                    InerciaApiClient.getInstance(getContext()).createUser(email,contraseña,inerciaApiCreateUser);
                }else{
                    Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean validarConfirmaciones(String email, String emailConfirm, String contraseña, String contraseñaConfirm){
        boolean validation = true;

        validation = (email.equalsIgnoreCase(emailConfirm)) ? validation : false;
        validation = (contraseña.equalsIgnoreCase(contraseñaConfirm)) ? validation : false;

        return validation;
    }

    public void messageSuccessCreation(){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("");
        alertDialog.setMessage(getContext().getString(R.string.dialog_meesage_crear_cuenta));
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onCrearCuenta(FRAGMENT_TAG);
            }
        });
        alertDialog.show();
    }

    public interface CrearCuentaListener{
        void onCrearCuenta(String thisFragmentTag);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CrearCuentaListener) {
            listener = (CrearCuentaListener) context;
        } else {
            throw new IllegalArgumentException(context.toString() + "debe de implementar en onAttach");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public EditText getText_email_crear() {
        return text_email_crear;
    }

    public EditText getText_confirmar_email_crear() {
        return text_confirmar_email_crear;
    }

    public EditText getText_contraseña_crear() {
        return text_contraseña_crear;
    }

    public EditText getText_contraseña_confirmar_crear() {
        return text_contraseña_confirmar_crear;
    }

    public CrearCuentaListener getListener() {
        return listener;
    }
}
