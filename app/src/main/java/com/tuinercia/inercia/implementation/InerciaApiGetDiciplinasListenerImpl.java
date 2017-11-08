package com.tuinercia.inercia.implementation;

import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tuinercia.inercia.DTO.Disciplines;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.adapter.ReservacionClasesAdapter;
import com.tuinercia.inercia.fragments.ReservacionClasesFragment;
import com.tuinercia.inercia.fragments.dialogs.ErrorConexionDialog;
import com.tuinercia.inercia.fragments.dialogs.ErrorServerDialog;
import com.tuinercia.inercia.interfaces.InerciaApiGetDiciplinasListener;
import com.tuinercia.inercia.utils.TypeFaceCustom;

import java.util.ArrayList;

/**
 * Created by ricar on 07/11/2017.
 */

public class InerciaApiGetDiciplinasListenerImpl implements InerciaApiGetDiciplinasListener {

    ReservacionClasesFragment reservacionClasesFragment;

    public InerciaApiGetDiciplinasListenerImpl(ReservacionClasesFragment reservacionClasesFragment){
        this.reservacionClasesFragment = reservacionClasesFragment;
    }

    @Override
    public void chargeDiciplinas(Disciplines[] disciplines) {
        ArrayList<Disciplines> data = new ArrayList<>();
        data.add(new Disciplines("0",reservacionClasesFragment.getContext().getString(R.string.title_all),Integer.toString(R.drawable.exercise),"","",""));
        for (Disciplines d : disciplines){
            data.add(d);
        }
        reservacionClasesFragment.grid_clases.setAdapter(new ReservacionClasesAdapter(R.layout.object_grid_reservacion_clases,data,reservacionClasesFragment.getContext()) {
            @Override
            public void onEntradaSet(View v, ReservacionClasesAdapter.ViewHolder mHolder) {
                mHolder.imagenClase = (ImageView) v.findViewById(R.id.img_clase_grid);
                mHolder.nombreClase = (TextView) v.findViewById(R.id.txt_clase_grid);
                mHolder.nombreClase.setTypeface(TypeFaceCustom.getInstance(reservacionClasesFragment.getContext()).UBUNTU_BOLD_TYPE_FACE);
            }
            @Override
            public void onEntrada(Object clases, ReservacionClasesAdapter.ViewHolder mHolder, int position) {
                Disciplines discipline = (Disciplines) clases;
                mHolder.nombreClase.setText(discipline.getName());
                Picasso.with(reservacionClasesFragment.getContext())
                        .load(discipline.getPic_file_name())
                        .placeholder(R.drawable.exercise)
                        .into(mHolder.imagenClase);
            }
        });
    }

    @Override
    public void failChargeDiciplines() {
        DialogFragment dialog = new ErrorConexionDialog();
        dialog.show(reservacionClasesFragment.getFragmentManager(),reservacionClasesFragment.FRAGMENT_TAG);
    }

    @Override
    public void onErrorServer(int statusCode) {
        DialogFragment dialog = new ErrorServerDialog();
        dialog.show(reservacionClasesFragment.getFragmentManager(),reservacionClasesFragment.FRAGMENT_TAG);
    }
}
