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
import com.tuinercia.inercia.implementation.LoadingViewManagerImpl;
import com.tuinercia.inercia.network.InerciaApiClient;
import com.tuinercia.inercia.utils.ExpValidator;
import com.tuinercia.inercia.utils.TypeFaceCustom;
import com.tuinercia.inercia.utils.UtilsSharedPreference;

/**
 * Created by ricar on 20/09/2017.
 */

public class CrearCuentaFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener{

    EditText text_email_crear,text_confirmar_email_crear,text_contraseña_crear,text_contraseña_confirmar_crear;
    Button button_crear;

    public static final String FRAGMENT_TAG = "CrearCuentaFragment";
    private CrearCuentaListener listener;

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

        text_email_crear.setOnFocusChangeListener(this);
        text_confirmar_email_crear.setOnFocusChangeListener(this);
        text_contraseña_confirmar_crear.setOnFocusChangeListener(this);

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
                    listener.goToNextStep(email,contraseña,FRAGMENT_TAG);
                }

                break;
        }
    }

    private boolean validarConfirmaciones(String email, String emailConfirm, String contraseña, String contraseñaConfirm){
        boolean validation = true;

        if (!ExpValidator.getInstance().validateEmail(email)){
            validation = false;
            text_email_crear.setError(getString(R.string.label_error_correo_formato_valido));
        }

        if (!email.equalsIgnoreCase(emailConfirm) && validation){
            validation = false;
            text_email_crear.setError(getString(R.string.label_error_email_no_coincide));
            text_confirmar_email_crear.setError(getString(R.string.label_error_email_no_coincide));
        }

        if (!contraseña.equalsIgnoreCase(contraseñaConfirm)  && validation ){
            validation = false;
            text_contraseña_confirmar_crear.setError(getString(R.string.label_error_password_no_coincide));
            text_contraseña_crear.setError(getString(R.string.label_error_password_no_coincide));
        }

        return validation;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (!b){
            switch (view.getId()){
                case R.id.text_email_crear:
                    String email = text_email_crear.getText().toString();
                    if (!ExpValidator.getInstance().validateEmail(email) && email.trim().length() > 0){
                        text_email_crear.setError(getString(R.string.label_error_correo_formato_valido));
                    }
                    break;
                case R.id.text_confirmar_email_crear:
                    if (!text_email_crear.getText().toString().equalsIgnoreCase(text_confirmar_email_crear.getText().toString())){
//                        text_email_crear.setError(getString(R.string.label_error_email_no_coincide));
                        text_confirmar_email_crear.setError(getString(R.string.label_error_email_no_coincide));
                    }
                    break;
                case R.id.text_contraseña_confirmar_crear:
                    if (!text_contraseña_crear.getText().toString().equalsIgnoreCase(text_contraseña_confirmar_crear.getText().toString())){
                        text_contraseña_confirmar_crear.setError(getString(R.string.label_error_password_no_coincide));
                        text_contraseña_crear.setError(getString(R.string.label_error_password_no_coincide));
                    }
                    break;
            }
        }
    }


    public interface CrearCuentaListener{
        void goToNextStep(String email, String password, String fragmentAnterior);
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
