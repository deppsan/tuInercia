package com.tuinercia.inercia.implementation;

import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tuinercia.inercia.DTO.Parlor;
import com.tuinercia.inercia.DTO.Zone;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.ReservacionGeolocalizacionFragment;
import com.tuinercia.inercia.fragments.dialogs.ErrorConexionDialog;
import com.tuinercia.inercia.fragments.dialogs.ErrorServerDialog;
import com.tuinercia.inercia.interfaces.InerciaApiGetParlorsListener;

import java.util.ArrayList;

/**
 * Created by ricar on 08/11/2017.
 */

public class InerciaApiGetParlorsListenerImpl implements InerciaApiGetParlorsListener {

    ReservacionGeolocalizacionFragment reservacionGeolocalizacionFragment;

    public InerciaApiGetParlorsListenerImpl(ReservacionGeolocalizacionFragment reservacionGeolocalizacionFragment) {
        this.reservacionGeolocalizacionFragment = reservacionGeolocalizacionFragment;
    }

    @Override
    public void onGetParlorsSuccess(ArrayList<Parlor> parlors) {
        reservacionGeolocalizacionFragment.res_parlors = parlors;
        int i = 0;
        for (Parlor p : reservacionGeolocalizacionFragment.res_parlors){

            // create marker
            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(Double.parseDouble(p.getCoord_y()),Double.parseDouble(p.getCoord_x()))).title(Integer.toString(i));
            // Changing marker icon
            marker.icon(BitmapDescriptorFactory.fromBitmap(
                    BitmapFactory.decodeResource(reservacionGeolocalizacionFragment.getContext().getResources(), R.drawable.tag_map)
            ));
            // adding marker
            reservacionGeolocalizacionFragment.gMap.addMarker(marker);

            i++;
        }
        reservacionGeolocalizacionFragment.gMap.setOnInfoWindowClickListener(reservacionGeolocalizacionFragment);
        reservacionGeolocalizacionFragment.gMap.setInfoWindowAdapter(new ReservacionGeolocalizacionFragment.MapsAdapterCustom());
        reservacionGeolocalizacionFragment.gMap.setOnMarkerClickListener(reservacionGeolocalizacionFragment);
    }

    @Override
    public void onFailChargeParlors() {
        DialogFragment dialog = new ErrorConexionDialog();
        dialog.show(reservacionGeolocalizacionFragment.getFragmentManager(),reservacionGeolocalizacionFragment.FRAGMENT_TAG);
    }

    @Override
    public void onErrorServer(int statusCode) {
        DialogFragment dialog = new ErrorServerDialog();
        dialog.show(reservacionGeolocalizacionFragment.getFragmentManager(),reservacionGeolocalizacionFragment.FRAGMENT_TAG);
    }
}
