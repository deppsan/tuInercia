package com.tuinercia.inercia.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class AvisoFaltaDeCuentaPro extends DialogFragment {
    public final static String FRAGMENT_TAG = "AvisoFaltaDeCuentaPro";
    AvisoFaltaDeCuentaProListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("¡Oups!")
                .setMessage("Aún no tienes tu membresía inercia, adquierela hoy en tu sección 'Mi Cuenta'")
                .setPositiveButton("FITCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onpushGoProButton();
                    }
                })
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }
    public interface AvisoFaltaDeCuentaProListener {
        void onpushGoProButton();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AvisoFaltaDeCuentaProListener) {
            listener = (AvisoFaltaDeCuentaProListener) context;
        } else {
            throw new IllegalArgumentException(context.toString() + "debe de implementar en onAttach");
        }
    }
}
