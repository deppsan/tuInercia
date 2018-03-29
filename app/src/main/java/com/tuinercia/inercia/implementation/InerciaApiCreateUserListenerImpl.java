package com.tuinercia.inercia.implementation;

import android.support.v4.app.DialogFragment;

import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.CrearCuentaFragment;
import com.tuinercia.inercia.fragments.dialogs.ErrorServerDialog;
import com.tuinercia.inercia.fragments.dialogs.GeneralDialogFragment;
import com.tuinercia.inercia.interfaces.InerciaApiCreateUserListener;

/**
 * Created by ricar on 22/01/2018.
 */

public class InerciaApiCreateUserListenerImpl implements InerciaApiCreateUserListener {

    CrearCuentaFragment crearCuentaFragment;

    public InerciaApiCreateUserListenerImpl(CrearCuentaFragment crearCuentaFragment) {
        this.crearCuentaFragment = crearCuentaFragment;
    }

    @Override
    public void onCreateUserSuccefull(User user) {
        crearCuentaFragment.messageSuccessCreation();
    }

    @Override
    public void onCreateUserError(String errorMessage) {
        GeneralDialogFragment.getInstance(errorMessage, crearCuentaFragment.getContext().getString(R.string.btn_login_check),null)
                             .show(crearCuentaFragment.getFragmentManager(),crearCuentaFragment.FRAGMENT_TAG);
    }

    @Override
    public void onErrorServer(int statusCode) {
        DialogFragment dialog = new ErrorServerDialog();
        dialog.setCancelable(false);
        dialog.show(crearCuentaFragment.getFragmentManager(),crearCuentaFragment.FRAGMENT_TAG);

    }
}
