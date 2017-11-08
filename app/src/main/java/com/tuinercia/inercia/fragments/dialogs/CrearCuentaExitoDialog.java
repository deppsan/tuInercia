package com.tuinercia.inercia.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.tuinercia.inercia.R;

/**
 * Created by ricar on 07/11/2017.
 */

public class CrearCuentaExitoDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder  = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton(R.string.btn_login_check, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setTitle("")
                .setMessage(R.string.dialog_meesage_crear_cuenta);

        return builder.create();
    }
}
