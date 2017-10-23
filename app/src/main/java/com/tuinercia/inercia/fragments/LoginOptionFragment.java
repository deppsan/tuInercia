package com.tuinercia.inercia.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tuinercia.inercia.R;
import com.tuinercia.inercia.utils.TypeFaceCustom;

/**
 * Created by ricar on 18/09/2017.
 */

public class LoginOptionFragment extends Fragment implements View.OnClickListener{

    LoginOptionListener listener;

    Button btn_login, btn_sign_up;
    public static final String FRAGMENT_TAG = "LoginOptionFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login_option,container,false);

        btn_login = (Button) v.findViewById(R.id.btn_login);
        btn_sign_up = (Button) v.findViewById(R.id.btn_sign_up);

        btn_login.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);
        btn_sign_up.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);

        btn_sign_up.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                listener.OnClickBtnLogin(FRAGMENT_TAG);
                break;
            case R.id.btn_sign_up:
                listener.OnClickBtnSignUp(FRAGMENT_TAG);
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginOptionListener) {
            listener = (LoginOptionListener) context;
        } else {
            throw new IllegalArgumentException(context.toString() + "debe de implementar en onAttach");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface LoginOptionListener {
        void OnClickBtnLogin(String FragmentAnterior);
        void OnClickBtnSignUp(String FragmentAnterior);
    }
}
