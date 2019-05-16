package com.tuinercia.inercia.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.tuinercia.inercia.R;

/**
 * Created by ricar on 22/02/2018.
 */

public class ConfirmarReservarScheduleDialog extends DialogFragment {
    private final static String SCHEDULE_ARGS = "id_schedule";

    ConfirmarReservarScheduleDialogListener listener;

   /* @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final int id = getArguments().getInt(SCHEDULE_ARGS);

        builder.setTitle(R.string.dialog_title_confirmar_reservacion)
                .setNegativeButton(R.string.dialog_confirmar_reservacion_button_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onCancelarConfirmacion();
                    }
                })
                .setPositiveButton(R.string.dialog_confirmar_reservacion_button_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onAceptarConfirmacion(id);
                    }
                })
                .setMessage(R.string.dialog_message_confirmar_reservacion);

        return builder.create();
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final int id = getArguments().getInt(SCHEDULE_ARGS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.custom_dialog_caducidad_exp);
        }else{
            View view = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog_caducidad_exp,null);
            builder.setView(view);
        }

        return builder.show();
    }

    public static ConfirmarReservarScheduleDialog newInstance(int idSchedule){

        ConfirmarReservarScheduleDialog dialog = new ConfirmarReservarScheduleDialog();
        Bundle args = new Bundle();
        args.putInt(SCHEDULE_ARGS,idSchedule);
        dialog.setArguments(args);
        return dialog;
    }


    public interface ConfirmarReservarScheduleDialogListener{
        void onCancelarConfirmacion();
        void onAceptarConfirmacion(int idSchedule);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ConfirmarReservarScheduleDialogListener) {
            listener = (ConfirmarReservarScheduleDialogListener) context;
        } else {
            throw new IllegalArgumentException(context.toString() + "debe de implementar en onAttach");
        }
    }
}
