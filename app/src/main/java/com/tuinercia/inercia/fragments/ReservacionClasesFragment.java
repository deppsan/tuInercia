package com.tuinercia.inercia.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.tuinercia.inercia.R;
import com.tuinercia.inercia.implementation.ChangeTitleImpl;
import com.tuinercia.inercia.implementation.InerciaApiGetDiciplinasListenerImpl;
import com.tuinercia.inercia.network.InerciaApiClient;

import java.util.ArrayList;

/**
 * Created by ricar on 22/09/2017.
 */

public class ReservacionClasesFragment extends Fragment implements AdapterView.OnItemClickListener{

    public GridView grid_clases;
    ReservacionClasesListener listener;
    InerciaApiGetDiciplinasListenerImpl inerciaApiGetDiciplinasListener;

    public final static String FRAGMENT_TAG = "ReservacionClasesFragment";
    private final static int TITLE = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reservacion_clases,container,false);

        grid_clases = (GridView) v.findViewById(R.id.grid_clases);
        ArrayList<Integer> i = new ArrayList<>();

        inerciaApiGetDiciplinasListener = new InerciaApiGetDiciplinasListenerImpl(this);
        InerciaApiClient.getInstance(getContext()).getDiciplinas(inerciaApiGetDiciplinasListener);

        grid_clases.setOnItemClickListener(this);

        ChangeTitleImpl.getInstance().changeTitleByCurrentFragment(TITLE);

        return v;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listener.onClaseSeleccionada(this.FRAGMENT_TAG);
    }

    public interface ReservacionClasesListener{
        void onClaseSeleccionada(String fragmentTAG);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReservacionClasesListener) {
            listener = (ReservacionClasesListener) context;
        } else {
            throw new IllegalArgumentException(context.toString() + "debe de implementar en onAttach");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
