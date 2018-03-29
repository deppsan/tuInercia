package com.tuinercia.inercia.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.tuinercia.inercia.R;

/**
 * Created by ricar on 26/03/2018.
 */

public class InformacionCaducidadDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.custom_dialog_caducidad_exp);
        }else{
            View view = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog_caducidad_exp,null);
            builder.setView(view);
        }

        return builder.show();
    }
}
