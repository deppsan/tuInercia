package com.tuinercia.inercia.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tuinercia.inercia.DTO.Membership;
import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.dialogs.GeneralDialogFragment;
import com.tuinercia.inercia.fragments.dialogs.InformacionCVVDialog;
import com.tuinercia.inercia.fragments.dialogs.InformacionCaducidadDialog;
import com.tuinercia.inercia.implementation.InerciaApiCreatePaymentListenerImpl;
import com.tuinercia.inercia.implementation.InerciaApiGetPlanesInerciaListenerImpl;
import com.tuinercia.inercia.implementation.LoadingViewManagerImpl;
import com.tuinercia.inercia.network.InerciaApiClient;
import com.tuinercia.inercia.utils.UtilsSharedPreference;

import org.json.JSONObject;

import io.conekta.conektasdk.Card;
import io.conekta.conektasdk.Conekta;
import io.conekta.conektasdk.Token;
import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

/**
 * Created by ricar on 05/10/2017.
 */

public class PagosFormularioAltaFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    public static final String FRAGMENT_TAG = "PagosFormularioAltaFragment";

    private static final int LENGHT_CARD_NUMBER = 16;
    private static final int CERO_NUMBER = 0;
    private static final int LENGHT_CVC = 3;
    private static final int LENGHT_VTO_MONTH = 2;
    private static final int LENGHT_VTO_YEAR = 4;
    private static final String ERROR_CAMPO_NECESARIO = "Campo requerido";
    private static final String ERROR_NUMERO_TARJETA = "El numero de tarjeta consta de 16 numeros";

    private static final String REGEX_SPACE = "\\s+";

    private static final String ERROR_RESULT_NAME_CONEKTA = "error";
    private static final String CONEKTA_PUBLIC_KEY = "key_DsUNHQxSdYi81c6oxByvAbg";
    private static final String CONEKTA_VERSION = "2.0.0";
    private static final String OBJECT_RESULT_NAME_CONEKTA = "object";
    private static final String ID_RESULT_NAME_CONEKTA = "id";
    private static final String MESSAGE_TO_PURCHASER = "message_to_purchaser";

    private static PagosFormularioAltaFragment instance;

    Spinner spinner;
    ImageView info_cvc, info_caducidad;
    Activity activity;
    Button button_alta_plan_pago;
    EditText txt_titular_tarjeta, txt_numero_tarjeta, txt_cvc, txt_expiracion_mes, txt_expiracion_año;
    View view;

    User user;
    InerciaApiGetPlanesInerciaListenerImpl inerciaApiGetPlanesInerciaListener;
    InerciaApiCreatePaymentListenerImpl inerciaApiCreatePaymentListener;
    PagosFormularioAltaFragmentListener listener;
    LoadingViewManagerImpl loadingViewManager;

    Membership[] memberships;

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
        view = v.findViewById(R.id.loading_view);

        activity = getActivity();

        inerciaApiGetPlanesInerciaListener = new InerciaApiGetPlanesInerciaListenerImpl(this);
        inerciaApiCreatePaymentListener = new InerciaApiCreatePaymentListenerImpl(this);
        loadingViewManager = new LoadingViewManagerImpl(view);

        user = UtilsSharedPreference.getInstance(getContext()).getUser();

        InerciaApiClient.getInstance(getContext()).getPlanesInercia(user.getEmail(), user.getPassword_digest(), inerciaApiGetPlanesInerciaListener, loadingViewManager);

        info_cvc.setOnClickListener(this);
        info_caducidad.setOnClickListener(this);
        button_alta_plan_pago.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        MaskImpl mask  = MaskImpl.createTerminated(PredefinedSlots.CARD_NUMBER_STANDART);
        FormatWatcher watcher = new MaskFormatWatcher(mask);
        watcher.installOn(txt_numero_tarjeta);

        return v;
    }

    public static PagosFormularioAltaFragment getInstance(){
        if (instance == null){
            instance = new PagosFormularioAltaFragment();
        }
        return instance;
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
                if (validateform()){
                    String cardNumber, titularName, cvc, vto_month, vto_year;

                    Conekta.setPublicKey(CONEKTA_PUBLIC_KEY); //Set public key
                    Conekta.setApiVersion(CONEKTA_VERSION); //Set api version (optional)
                    Conekta.collectDevice(activity); //Collect device

                    cardNumber = txt_numero_tarjeta.getText().toString();
                    cardNumber = cardNumber.trim().replaceAll(REGEX_SPACE,"");
                    titularName = txt_titular_tarjeta.getText().toString();
                    cvc = txt_cvc.getText().toString();
                    vto_year = txt_expiracion_año.getText().toString();
                    vto_month = txt_expiracion_mes.getText().toString();
                    final Membership membership = memberships[spinner.getSelectedItemPosition() - 1];

                    Card card  = new Card(titularName, cardNumber, cvc, vto_month, vto_year);
                    Token token = new Token(activity);

                    token.onCreateTokenListener(new Token.CreateToken() {
                        @Override
                        public void onCreateTokenReady(JSONObject data) {
                            try{
                                if (!data.getString(OBJECT_RESULT_NAME_CONEKTA).trim().equalsIgnoreCase(ERROR_RESULT_NAME_CONEKTA)){
                                    InerciaApiClient.getInstance(getContext()).createPayment(data.getString(ID_RESULT_NAME_CONEKTA), Integer.toString(user.getId()), membership.getId() , inerciaApiCreatePaymentListener, loadingViewManager);
                                } else {
                                    GeneralDialogFragment.getInstance(data.getString(MESSAGE_TO_PURCHASER),"Aceptar",null)
                                            .show(getActivity().getSupportFragmentManager(),null);
                                }
                            }catch (Exception e){
                                Toast.makeText(activity, "e : " + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    token.create(card);
                }
                break;
        }
    }
    public Spinner getSpinner() {
        return spinner;
    }
    public void setSpinner(Spinner spinner) {
        this.spinner = spinner;
    }


    public Membership[] getMemberships() {
        return memberships;
    }
    public void setMemberships(Membership[] memberships) {
        this.memberships = memberships;
    }

    public PagosFormularioAltaFragmentListener getListener() {
        return listener;
    }

    public void setListener(PagosFormularioAltaFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean validateform(){
        String cardNumber, titularName, cvc, vto_month, vto_year;
        boolean validation = true;

        cardNumber = txt_numero_tarjeta.getText().toString();
        cardNumber = cardNumber.trim().replaceAll("\\s+","");
        titularName = txt_titular_tarjeta.getText().toString();
        cvc = txt_cvc.getText().toString();
        vto_year = txt_expiracion_año.getText().toString();
        vto_month = txt_expiracion_mes.getText().toString();
        int select = spinner.getSelectedItemPosition();

        if ( cardNumber.length() < LENGHT_CARD_NUMBER ){
            validation = false;
            txt_numero_tarjeta.setError(ERROR_NUMERO_TARJETA);
            if (cardNumber.length() == CERO_NUMBER)
                txt_numero_tarjeta.setError(ERROR_CAMPO_NECESARIO);
        }
        if ( titularName.trim().length() == CERO_NUMBER ){
            validation = false;
            txt_titular_tarjeta.setError(ERROR_CAMPO_NECESARIO);
        }
        if ( cvc.trim().length() != LENGHT_CVC ){
            validation = false;
            txt_cvc.setError(ERROR_CAMPO_NECESARIO);
        }
        if ( vto_month.trim().length() != LENGHT_VTO_MONTH ){
            validation = false;
            txt_expiracion_mes.setError(ERROR_CAMPO_NECESARIO);
        }
        if ( vto_year.trim().length() != LENGHT_VTO_YEAR ){
            validation = false;
            txt_expiracion_año.setError(ERROR_CAMPO_NECESARIO);
        }
        if ( select == 0 ){
            validation = false;
        }

        return validation;
    }

    public interface PagosFormularioAltaFragmentListener{
            void OnCreatePaymentSuccess();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PagosFormularioAltaFragmentListener) {
            listener = (PagosFormularioAltaFragmentListener) context;
        } else {
            throw new IllegalArgumentException(context.toString() + "debe de implementar en onAttach");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
