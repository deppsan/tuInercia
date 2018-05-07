package com.tuinercia.inercia.implementation;


import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.PagosFormularioAltaFragment;
import com.tuinercia.inercia.fragments.dialogs.ErrorServerDialog;
import com.tuinercia.inercia.fragments.dialogs.GeneralDialogFragment;
import com.tuinercia.inercia.interfaces.InerciaApiCreatePaymentListener;

/**
 * Created by ricar on 05/04/2018.
 */

public class InerciaApiCreatePaymentListenerImpl implements InerciaApiCreatePaymentListener {

    PagosFormularioAltaFragment pagosFormularioAltaFragment;

    public InerciaApiCreatePaymentListenerImpl(PagosFormularioAltaFragment pagosFormularioAltaFragment) {
        this.pagosFormularioAltaFragment = pagosFormularioAltaFragment;
    }

    @Override
    public void onCreatePaymentError(String errorMessage) {
        GeneralDialogFragment.getInstance(errorMessage,"Aceptar",null)
                .show(pagosFormularioAltaFragment.getFragmentManager(),null);
    }

    @Override
    public void onErrorServer(int statusCode) {
        DialogFragment dialog = new ErrorServerDialog();
        dialog.setCancelable(false);
        dialog.show(pagosFormularioAltaFragment.getFragmentManager(),null);
    }

    @Override
    public void onCreatePaymentSuccess(String message) {
        pagosFormularioAltaFragment.getListener().OnCreatePaymentSuccess();
    }
}
