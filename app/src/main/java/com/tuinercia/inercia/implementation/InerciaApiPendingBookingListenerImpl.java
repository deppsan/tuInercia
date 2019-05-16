package com.tuinercia.inercia.implementation;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuinercia.inercia.DTO.Parlor;
import com.tuinercia.inercia.DTO.ReservationTrue;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.adapter.CheckInAdapter;
import com.tuinercia.inercia.fragments.AgendaActualFragment;
import com.tuinercia.inercia.fragments.dialogs.ErrorServerDialog;
import com.tuinercia.inercia.fragments.dialogs.GeneralDialogFragment;
import com.tuinercia.inercia.interfaces.InerciaApiPendingBookingListener;
import com.tuinercia.inercia.utils.TypeFaceCustom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ricar on 06/03/2018.
 */

public class InerciaApiPendingBookingListenerImpl implements InerciaApiPendingBookingListener {

    AgendaActualFragment agendaActualFragment;

    public InerciaApiPendingBookingListenerImpl(AgendaActualFragment agendaActualFragment) {
        this.agendaActualFragment = agendaActualFragment;
    }

    @Override
    public void onGetPenndingSuccess(ReservationTrue[] reservations) {
        ArrayList<ReservationTrue> data = new ArrayList<>();

        for (ReservationTrue r : reservations){
            data.add(r);
        }

        agendaActualFragment.getList_agenda().setAdapter(new CheckInAdapter(R.layout.object_clases_agendadas, agendaActualFragment.getmContext(),data) {
            @Override
            public void onEntradaSet(View v, HolderView mHolder) {
                mHolder.button = (Button) v.findViewById(R.id.button_check_in);
                mHolder.clase = (TextView) v.findViewById(R.id.text_clase);
                mHolder.fecha = (TextView) v.findViewById(R.id.text_fecha);
                mHolder.hora = (TextView) v.findViewById(R.id.text_hora);
                mHolder.estudio = (TextView) v.findViewById(R.id.text_estudio);
                mHolder.entrador = (TextView) v.findViewById(R.id.text_entrenador);
//                mHolder.mis_clases_orientame = (ImageView) v.findViewById(R.id.mis_clases_orientame);
            }

            @Override
            public void onEntrada(Object objects, HolderView mHolder, int position) {
                ReservationTrue reservation = (ReservationTrue) objects;

                Locale loc = new Locale("es","MX");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", loc);
                Calendar cal = Calendar.getInstance();
                try {
                    Date date = formatter.parse(reservation.getFecha());
                    cal.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                formatter = new SimpleDateFormat("E d MMMM",loc);
                mHolder.clase.setText(reservation.getClase());
                mHolder.clase.setTypeface(TypeFaceCustom.getInstance( agendaActualFragment.getmContext()).UBUNTU_TYPE_FACE);
                mHolder.fecha.setText(formatter.format(cal.getTime()) + " ");
                mHolder.fecha.setTypeface(TypeFaceCustom.getInstance( agendaActualFragment.getmContext()).UBUNTU_TYPE_FACE);
                mHolder.hora.setText(reservation.getHora());
                mHolder.hora.setTypeface(TypeFaceCustom.getInstance( agendaActualFragment.getmContext()).UBUNTU_TYPE_FACE);
                mHolder.estudio.setText(reservation.getSucursal() + " ");
                mHolder.estudio.setTypeface(TypeFaceCustom.getInstance( agendaActualFragment.getmContext()).UBUNTU_TYPE_FACE);
                mHolder.entrador.setText(reservation.getInstructor());
                mHolder.entrador.setTypeface(TypeFaceCustom.getInstance( agendaActualFragment.getmContext()).UBUNTU_TYPE_FACE);

                if (!reservation.getNo_cancel()){
                    mHolder.button.setText(R.string.btn_cancelar);

                }else{
                    if(!reservation.getChecking()){
                        mHolder.button.setText(R.string.btn_check_in);
                    }else{
                        mHolder.button.setText("Clave : " + reservation.getConfirmacion());
                        mHolder.button.setEnabled(false);
                        mHolder.button.setTextColor(ContextCompat.getColor(agendaActualFragment.getmContext(),R.color.colorPrimary));
                    }
                }
                mHolder.button.setTypeface(TypeFaceCustom.getInstance( agendaActualFragment.getmContext()).UBUNTU_BOLD_TYPE_FACE);
                mHolder.button.setTag(reservation.getId());
                mHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button btn = (Button) v;
                        if (btn.getText().toString().equalsIgnoreCase(agendaActualFragment.getString(R.string.btn_check_in))){
                            agendaActualFragment.CheckInReservacion(v.getTag().toString());
                        }else if (btn.getText().toString().equalsIgnoreCase(agendaActualFragment.getString(R.string.btn_cancelar))){
                            agendaActualFragment.cancelarReservacion(v.getTag().toString());
                        }
                        agendaActualFragment.setButton_selected(btn);
                    }
                });
            }
        });
    }

    @Override
    public void onGetPendingError(String errorMessage) {
        GeneralDialogFragment.getInstance(errorMessage,"Aceptar",null)
                .show(agendaActualFragment.getFragmentManager(),null);
    }

    @Override
    public void onErrorServer(int statusCode) {
        DialogFragment dialog = new ErrorServerDialog();
        dialog.setCancelable(false);
        dialog.show(agendaActualFragment.getFragmentManager(),null);
    }

    /*private void orientameButtonAction(Parlor parlor){
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + parlor.getCoord_x() + "," + parlor.getCoord_y());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(agendaActualFragment.getContext().getPackageManager()) != null){
            agendaActualFragment.getContext().startActivity(mapIntent);
        }
    }*/
}
