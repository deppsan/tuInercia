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

public class ErrorServerDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder  = new AlertDialog.Builder(getActivity());

        builder.setNegativeButton(R.string.dialog_button_error_conection, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setTitle(R.string.dialog_title_error_server)
                .setMessage(R.string.dialog_message_error_server);

        return builder.create();
    }
}
