package com.tuinercia.inercia.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class InformacionClasesDialog extends DialogFragment {
    public final static String FRAGMENT_TAG = "InformacionClasesDialog";
    final static String CLASE_NAME_ARGS = "claseNameArgs";
    final static String CLASE_DESCRIPTION_ARGS = "claseDescriptionArgs";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();

        builder.setTitle("Clase: " + args.getString(CLASE_NAME_ARGS))
        .setMessage(args.getString(CLASE_DESCRIPTION_ARGS))
        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });



        return builder.create();
    }

    public static InformacionClasesDialog newInstance(String nombre, String descripcion){
        InformacionClasesDialog dialog = new InformacionClasesDialog();
        Bundle args = new Bundle();
        args.putString(CLASE_NAME_ARGS,nombre);
        args.putString(CLASE_DESCRIPTION_ARGS,descripcion);
        dialog.setArguments(args);
        return dialog;
    }
}
