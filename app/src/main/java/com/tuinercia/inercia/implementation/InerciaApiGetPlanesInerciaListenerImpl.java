package com.tuinercia.inercia.implementation;

import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;

import com.tuinercia.inercia.DTO.Membership;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.PagosFormularioAltaFragment;
import com.tuinercia.inercia.fragments.dialogs.ErrorConexionDialog;
import com.tuinercia.inercia.fragments.dialogs.ErrorServerDialog;
import com.tuinercia.inercia.interfaces.InerciaApiGetPlanesInerciaListener;

import java.util.ArrayList;

/**
 * Created by ricar on 02/04/2018.
 */

public class InerciaApiGetPlanesInerciaListenerImpl implements InerciaApiGetPlanesInerciaListener {

    PagosFormularioAltaFragment pagosFormularioAltaFragment;

    public InerciaApiGetPlanesInerciaListenerImpl(PagosFormularioAltaFragment pagosFormularioAltaFragment) {
        this.pagosFormularioAltaFragment = pagosFormularioAltaFragment;
    }

    @Override
    public void onGetPlanesListError(String errorMessage) {
        DialogFragment dialog = new ErrorConexionDialog();
        dialog.show(pagosFormularioAltaFragment.getFragmentManager(),pagosFormularioAltaFragment.FRAGMENT_TAG);
    }

    @Override
    public void onErrorServer(int statusCode) {
        DialogFragment dialog = new ErrorServerDialog();
        dialog.show(pagosFormularioAltaFragment.getFragmentManager(),pagosFormularioAltaFragment.FRAGMENT_TAG);
    }

    @Override
    public void onGetPlanesListSuccess(Membership[] memberships) {
        ArrayList<String> planes = new ArrayList<>();
        planes.add(pagosFormularioAltaFragment.getActivity().getString(R.string.label_prompt_planes));

        for (Membership m : memberships){
            planes.add(m.getName() + " - $ " + m.getAmount() + " - " + m.getNum_classes() + " Clases");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(pagosFormularioAltaFragment.getContext(), R.layout.object_spinner_planes, planes);
        pagosFormularioAltaFragment.getSpinner().setAdapter(adapter);
        pagosFormularioAltaFragment.setMemberships(memberships);
    }
}
