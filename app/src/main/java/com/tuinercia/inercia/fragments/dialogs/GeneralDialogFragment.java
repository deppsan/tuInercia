package com.tuinercia.inercia.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

/**
 * Created by ricar on 25/01/2018.
 */

public class GeneralDialogFragment extends DialogFragment {

    private static final String MESSAGE_TEXT = "message";
    private static final String NEGATIVE_BUTTON = "nButton";
    private static final String POSITIVE_BUTTON = "pButton";


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String message = getArguments().getString(MESSAGE_TEXT);
        String nButton = getArguments().getString(NEGATIVE_BUTTON);
        String pButton = getArguments().getString(POSITIVE_BUTTON);

        builder.setPositiveButton(pButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setMessage(message);

        if (nButton != null && !nButton.isEmpty()){
            builder.setNegativeButton(nButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }

        return builder.create();
    }

    public static GeneralDialogFragment getInstance(String message, String positiveButton, @Nullable String nevativeButton){
        GeneralDialogFragment dialog = new GeneralDialogFragment();
        Bundle args  = new Bundle();
        args.putString(MESSAGE_TEXT, message);
        args.putString(POSITIVE_BUTTON, positiveButton);
        args.putString(NEGATIVE_BUTTON, nevativeButton);

        dialog.setArguments(args);

        return dialog;
    }
}
