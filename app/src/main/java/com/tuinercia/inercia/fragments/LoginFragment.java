package com.tuinercia.inercia.fragments;

import android.content.Context;
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
 * Created by ricar on 18/09/2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener{

    public static final String FRAGMENT_TAG = "LoginFragment";
    LoginListener listener;

    EditText text_contraseña_login,text_email_login;
    Button btn_login;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login_option_on,container,false);

        text_contraseña_login = (EditText) v.findViewById(R.id.text_contraseña_login);
        text_email_login = (EditText) v.findViewById(R.id.text_email_login);

        btn_login = (Button) v.findViewById(R.id.btn_login);

        text_contraseña_login.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);
        text_email_login.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);

        btn_login.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);
        btn_login.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                listener.onClickButtonLogin();
                break;
        }
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
}
