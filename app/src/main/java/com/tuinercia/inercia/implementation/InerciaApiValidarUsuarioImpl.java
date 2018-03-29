package com.tuinercia.inercia.implementation;

import android.support.v4.app.DialogFragment;
import android.view.View;

import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.fragments.LoginFragment;
import com.tuinercia.inercia.fragments.dialogs.ErrorServerDialog;
import com.tuinercia.inercia.interfaces.InerciaApiValidarUsuario;
import com.tuinercia.inercia.utils.UtilsSharedPreference;

/**
 * Created by ricar on 08/11/2017.
 */

public class InerciaApiValidarUsuarioImpl implements InerciaApiValidarUsuario {
    LoginFragment loginFragment;

    public InerciaApiValidarUsuarioImpl(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    @Override
    public void onUsuarioCorrecto(User user) {
        UtilsSharedPreference.getInstance(loginFragment.getContext()).setUserJsonString(user);

        loginFragment.getView_group_login().setClickable(true);
        loginFragment.getView_group_progress_bar().setVisibility(View.GONE);

        loginFragment.getListener().onClickButtonLogin();

    }

    @Override
    public void onUsuarioIncorrecto(String errorMessage) {
        loginFragment.getView_group_login().setClickable(true);
        loginFragment.getView_group_progress_bar().setVisibility(View.GONE);
        loginFragment.messageWrongLogin();
    }

    @Override
    public void onErrorServer() {
        loginFragment.getView_group_login().setClickable(true);
        loginFragment.getView_group_progress_bar().setVisibility(View.GONE);
        DialogFragment dialog = new ErrorServerDialog();
        dialog.setCancelable(false);
        dialog.show(loginFragment.getActivity().getSupportFragmentManager(),loginFragment.FRAGMENT_TAG);
    }
}
