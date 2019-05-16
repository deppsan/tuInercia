package com.tuinercia.inercia.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.dialogs.ErrorConexionDialog;
import com.tuinercia.inercia.fragments.dialogs.ErrorServerDialog;
import com.tuinercia.inercia.implementation.InerciaApiValidarUsuarioImpl;
import com.tuinercia.inercia.interfaces.InerciaApiValidarUsuario;
import com.tuinercia.inercia.network.InerciaApiClient;
import com.tuinercia.inercia.utils.TypeFaceCustom;
import com.tuinercia.inercia.utils.UtilsSharedPreference;

/**
 * Created by ricar on 18/09/2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener{

    public static final String FRAGMENT_TAG = "LoginFragment";
    private static final String USER_PARAM = "userParam";
    private static final String WIHT_DATA_PARAM = "whitData";
    private static final String PASSWORD_PARAM = "passwordParam";
    LoginListener listener;

    EditText text_contraseña_login,text_email_login;
    LinearLayout view_group_login, view_group_progress_bar;
    Button btn_login;

    InerciaApiValidarUsuarioImpl inerciaApiValidarUsuario;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login_option_on,container,false);

        text_contraseña_login = (EditText) v.findViewById(R.id.text_contraseña_login);
        text_email_login = (EditText) v.findViewById(R.id.text_email_login);

        btn_login = (Button) v.findViewById(R.id.btn_login);

        view_group_login = (LinearLayout) v.findViewById(R.id.view_group_login);
        view_group_progress_bar = (LinearLayout) v.findViewById(R.id.view_group_progress_bar);

        text_contraseña_login.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);
        text_email_login.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);

        btn_login.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);
        btn_login.setOnClickListener(this);

        inerciaApiValidarUsuario = new InerciaApiValidarUsuarioImpl(this);
        
        if (getArguments().getBoolean(WIHT_DATA_PARAM)){
            InerciaApiClient.getInstance(getContext())
                    .validarUsuario( getArguments().getString(USER_PARAM)
                            , getArguments().getString(PASSWORD_PARAM)
                            ,inerciaApiValidarUsuario);
        }


        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        mFirebaseAnalytics.setCurrentScreen(getActivity(),FRAGMENT_TAG, null);

        return v;
    }

    public static LoginFragment getInstance(@Nullable String user,@Nullable String password, boolean withData){
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        if (withData){
            args.putString(USER_PARAM, user);
            args.putString(PASSWORD_PARAM, password);
        }
        args.putBoolean(WIHT_DATA_PARAM, withData);
        fragment.setArguments(args);
        
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                String user = text_email_login.getText().toString();
                String pass = text_contraseña_login.getText().toString();

                if (user.trim().length() > 0 && pass.trim().length() >0){
                    view_group_login.setClickable(false);
                    view_group_progress_bar.setVisibility(View.VISIBLE);
                    InerciaApiClient.getInstance(getContext())
                                    .validarUsuario( text_email_login.getText().toString()
                                            ,text_contraseña_login.getText().toString()
                                            ,inerciaApiValidarUsuario);
                }else{
                    messageWrongLogin();
                }

                break;
        }
    }

    public void messageWrongLogin(){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle(R.string.dialog_title_login_error);
        alertDialog.setMessage(getContext().getString(R.string.dialog_message_login_error));
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginListener) {
            listener = (LoginListener) context;
        } else {
            throw new IllegalArgumentException(context.toString() + "debe de implementar en onAttach");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface LoginListener{
        void onClickButtonLogin();
    }

    public LoginListener getListener() {
        return listener;
    }

    public LinearLayout getView_group_login() {
        return view_group_login;
    }

    public LinearLayout getView_group_progress_bar() {
        return view_group_progress_bar;
    }
}
