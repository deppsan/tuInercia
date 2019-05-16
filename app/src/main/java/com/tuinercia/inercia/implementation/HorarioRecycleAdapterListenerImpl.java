package com.tuinercia.inercia.implementation;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tuinercia.inercia.DTO.Schedule;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.activities.EstudioAgenda;
import com.tuinercia.inercia.fragments.dialogs.ConfirmarReservarScheduleDialog;
import com.tuinercia.inercia.fragments.dialogs.InformacionClasesDialog;
import com.tuinercia.inercia.interfaces.HorarioRecycleAdapterListener;

/**
 * Created by ricar on 19/02/2018.
 */

public class HorarioRecycleAdapterListenerImpl implements HorarioRecycleAdapterListener {
    EstudioAgenda estudioAgenda;

    public HorarioRecycleAdapterListenerImpl(EstudioAgenda estudioAgenda) {
        this.estudioAgenda = estudioAgenda;    }

    @Override
    public void onClickButtonListener(View v, int position) {
        final Schedule s = estudioAgenda.getHorarios().get(position);
        Button btn = (Button) v.findViewById(R.id.btn_reservar);
        estudioAgenda.setBtn_seleccion(btn);

        if(btn.getText().toString().equalsIgnoreCase(estudioAgenda.getString(R.string.btn_agendar))){
            /*ConfirmarReservarScheduleDialog dialog = ConfirmarReservarScheduleDialog.newInstance(s.getId());
            dialog.show(estudioAgenda.getSupportFragmentManager(),"");*/

            final Dialog dialog = new Dialog(estudioAgenda);
            dialog.setContentView(R.layout.custom_dialog_clase_descripcion);

            TextView txt_dialog_instructor = (TextView) dialog.findViewById(R.id.txt_dialog_instructor);
            TextView txt_dialog_clase = (TextView) dialog.findViewById(R.id.txt_dialog_clase);

            Button btn_aceptar_confirmacion = (Button) dialog.findViewById(R.id.btn_aceptar_confirmacion);
            Button btn_cancelar_confirmacion= (Button) dialog.findViewById(R.id.btn_cancelar_confirmacion);


            txt_dialog_clase.setText(s.getName());
            txt_dialog_instructor.setText(s.getInst_name());

            btn_cancelar_confirmacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            btn_aceptar_confirmacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    estudioAgenda.realizarReservacion(s.getId());
                    dialog.dismiss();
                }
            });

            dialog.show();

            s.setSelected(true);
        }else if(btn.getText().toString().equalsIgnoreCase(estudioAgenda.getString(R.string.btn_cancelar))){
            estudioAgenda.cancelarReservacion(btn.getTag().toString());
        }
    }

    @Override
    public void onClickInformationListener(View v, int position) {
        Schedule s = estudioAgenda.getHorarios().get(position);

        InformacionClasesDialog infoDialog = InformacionClasesDialog.newInstance(s.getName(),s.getDescription());
        infoDialog.show(estudioAgenda.getSupportFragmentManager(),infoDialog.FRAGMENT_TAG);

    }
}
