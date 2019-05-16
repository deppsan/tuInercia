package com.tuinercia.inercia.implementation;

import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuinercia.inercia.DTO.Membership;
import com.tuinercia.inercia.DTO.MembershipProgress;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.PagosInicioFragment;
import com.tuinercia.inercia.fragments.dialogs.ErrorServerDialog;
import com.tuinercia.inercia.fragments.dialogs.GeneralDialogFragment;
import com.tuinercia.inercia.interfaces.InerciaApiGetCurrentMembershipListener;
import com.tuinercia.inercia.utils.TypeFaceCustom;

import org.w3c.dom.Text;

/**
 * Created by ricar on 28/03/2018.
 */

public class InerciaApiGetCurrentMembershipListenerImpl implements InerciaApiGetCurrentMembershipListener {

    PagosInicioFragment pagosInicioFragment;

    public InerciaApiGetCurrentMembershipListenerImpl(PagosInicioFragment pagosInicioFragment) {
        this.pagosInicioFragment = pagosInicioFragment;
    }

    @Override
    public void onErrorServer(int statusCode) {
        DialogFragment dialog = new ErrorServerDialog();
        dialog.setCancelable(false);
        dialog.show(pagosInicioFragment.getFragmentManager(),null);
    }

    @Override
    public void onGetCurrentMembershipSuccess(Membership membership, MembershipProgress membershipProgress, boolean showProgress) {
        if (showProgress){
            LinearLayout view = pagosInicioFragment.getView_account_payment();

            TextView text_nombre_plan = (TextView) view.findViewById(R.id.text_nombre_plan);
            TextView text_costo_plan = (TextView) view.findViewById(R.id.text_costo_plan);
            TextView text_clases_restantes = (TextView) view.findViewById(R.id.text_clases_restantes);
            TextView text_plan_caducidad = (TextView) view.findViewById(R.id.text_plan_caducidad);
            TextView text_clases_restantes_head = (TextView) view.findViewById(R.id.text_clases_restantes_head);
            TextView text_clases_totales_head = (TextView) view.findViewById(R.id.text_clases_totales_head);

            text_nombre_plan.setText(membership.getName());
            text_nombre_plan.setTypeface(TypeFaceCustom.getInstance(pagosInicioFragment.getContext()).UBUNTU_TYPE_FACE);
            text_clases_restantes.setText(" " + membershipProgress.getPeriod_total() + " ");
            text_clases_restantes.setTypeface(TypeFaceCustom.getInstance(pagosInicioFragment.getContext()).UBUNTU_TYPE_FACE);
            text_costo_plan.setText(" " + Double.toString(membership.getAmount()));
            text_costo_plan.setTypeface(TypeFaceCustom.getInstance(pagosInicioFragment.getContext()).UBUNTU_TYPE_FACE);
            text_plan_caducidad.setText(" " + membershipProgress.getPeriod_ends());
            text_plan_caducidad.setTypeface(TypeFaceCustom.getInstance(pagosInicioFragment.getContext()).UBUNTU_TYPE_FACE);
            text_clases_restantes_head.setText(Integer.toString(membershipProgress.getPeriod_current()));
            text_clases_totales_head.setTypeface(TypeFaceCustom.getInstance(pagosInicioFragment.getContext()).UBUNTU_TYPE_FACE);
            text_clases_totales_head.setText(Integer.toString(membershipProgress.getPeriod_total()));
            text_clases_restantes_head.setTypeface(TypeFaceCustom.getInstance(pagosInicioFragment.getContext()).UBUNTU_TYPE_FACE);

            pagosInicioFragment.getView_account_free().setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
        }else{
            pagosInicioFragment.getView_account_free().setVisibility(View.GONE);
            pagosInicioFragment.getView_account_payment_in_progress().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGetCurrentMembershipError(String errorMessage) {
        /*GeneralDialogFragment.getInstance(errorMessage,"Aceptar",null)
                .show(pagosInicioFragment.getFragmentManager(),null);*/
    }
}
