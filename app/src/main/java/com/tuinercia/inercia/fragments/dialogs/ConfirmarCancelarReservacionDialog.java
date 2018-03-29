package com.tuinercia.inercia.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.tuinercia.inercia.R;
import com.tuinercia.inercia.interfaces.DialgoFragmentCancelFromAgenda;

/**
 * Created by ricar on 27/02/2018.
 */

public class ConfirmarCancelarReservacionDialog extends DialogFragment{
    private final static String RESERVATION_ARGS = "id_reservation";
    private DialgoFragmentCancelFromAgenda listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final int id = getArguments().getInt(RESERVATION_ARGS);

        builder.setMessage(R.string.dialog_confirmar_cancelacion_message)
                .setTitle(R.string.dialog_confirmar_cancelacion_title)
                .setPositiveButton(R.string.dialog_confirmar_reservacion_button_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onConfirmaCanccelacionReservacion(id);
                    }
                })
                .setNegativeButton(R.string.dialog_confirmar_reservacion_button_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onCancelarConfirmacionCancelacionReservacion();
                    }
                });

        return builder.create();
    }

    public static ConfirmarCancelarReservacionDialog newInstance(int idReservasion, DialgoFragmentCancelFromAgenda listener ){
        ConfirmarCancelarReservacionDialog dialog = new ConfirmarCancelarReservacionDialog();
        dialog.setListener(listener);
        Bundle args = new Bundle();
        args.putInt(RESERVATION_ARGS,idReservasion);
        dialog.setArguments(args);
        return dialog;
    }

    public DialgoFragmentCancelFromAgenda getListener() {
        return listener;
    }

    public void setListener(DialgoFragmentCancelFromAgenda listener) {
        this.listener = listener;
    }
}
