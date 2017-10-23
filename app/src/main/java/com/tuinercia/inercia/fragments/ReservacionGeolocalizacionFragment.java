package com.tuinercia.inercia.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.utils.TypeFaceCustom;

/**
 * Created by ricar on 25/09/2017.
 */

public class ReservacionGeolocalizacionFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    MapView mMap;
    GoogleMap gMap;
    Spinner spn_Zonas;
    View custom_info_view;
    ImageButton image_button_my_location;

    FusedLocationProviderClient mFusedLocationClient;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    LatLng mLatLng;

    boolean permisosNoOtorgados = false;
    int currentSpinnerOption = 0;
    int permissionCheck;
    ReservacionGeolocalizacionListener listener;

    public static final String FRAGMENT_TAG = "ReservacionGeolocalizacionFragment";
    private static final int MY_PERMISSIONS_REQUEST_GET_LOCATION = 0;

    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_agendar_geolocalizacion, container, false);

        permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        mContext = getContext();

        image_button_my_location = (ImageButton) v.findViewById(R.id.image_button_my_location);
        spn_Zonas                = (Spinner) v.findViewById(R.id.spn_Zonas);
        mMap                     = (MapView) v.findViewById(R.id.map);

        custom_info_view = getLayoutInflater(savedInstanceState).inflate(R.layout.object_maps_info_window,mMap);

        mMap.onCreate(savedInstanceState);
        mMap.onResume();


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMap.getMapAsync(this);
        image_button_my_location.setOnClickListener(this);
        image_button_my_location.setVisibility(View.INVISIBLE);
        spn_Zonas.setOnItemSelectedListener(this);

        return v;
    }

    LocationCallback mLocationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()){
                mLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,15));
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMap.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMap.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMap.onLowMemory();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CameraPosition cameraPosition;
        /*En esta seccion se remplazara con los datos recividos por la Api de Inercia*/
        switch (position) {
            case 0:
                if (mLatLng != null){
                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,15));
                }else if (permisosNoOtorgados){
                    Toast.makeText(getContext(), "No has otorgado permisos de localización, favor de revisar en administrador de Aplicaciones de tu Célular.", Toast.LENGTH_LONG).show();
                    spn_Zonas.setSelection(currentSpinnerOption);
                }
                break;
            case 1:
                cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(25.747162, -100.3903067)).zoom(15).build();
                gMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
                currentSpinnerOption = 1;
                break;
            case 2:
                cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(25.6527492, -100.2994418)).zoom(15).build();
                gMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
                currentSpinnerOption = 2;
                break;
            case 3:
                cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(25.638729, -100.3166602)).zoom(15).build();
                gMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
                currentSpinnerOption = 3;
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_GET_LOCATION:
                for (int i = 0 ; i < permissions.length ; i++){
                    String permission  = permissions[i];
                    int grantResult = grantResults[i];

                    if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)){
                        if (grantResult == PackageManager.PERMISSION_GRANTED){
                            try {
                                if (mGoogleApiClient != null){
                                    buildGoogleApiClient();
                                }
                                gMap.setMyLocationEnabled(true);
                            } catch (SecurityException e) {
                                e.printStackTrace();
                            }
                        }else{
                            permisosNoOtorgados = true;
                            spn_Zonas.setSelection(1);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(25.747162, -100.3903067)).title("Estudio");
        // Changing marker icon
        marker.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getContext().getResources(),R.drawable.tag_map)
        ));
        // adding marker
        gMap.addMarker(marker);
        gMap.setOnInfoWindowClickListener(this);
        gMap.setInfoWindowAdapter(new MapsAdapterCustom());
        gMap.setOnMarkerClickListener(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                try {
                    gMap.setMyLocationEnabled(true);
                    gMap.getUiSettings().setMyLocationButtonEnabled(false);
                    image_button_my_location.setVisibility(View.VISIBLE);

                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_GET_LOCATION);
            }
        }else{
            buildGoogleApiClient();
            try{
                gMap.setMyLocationEnabled(true);
                gMap.getUiSettings().setMyLocationButtonEnabled(false);
                image_button_my_location.setVisibility(View.VISIBLE);

            }catch (SecurityException e){
                e.printStackTrace();
            }
        }
    }

    private synchronized void  buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(600000);
        mLocationRequest.setFastestInterval(600000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        listener.onClickMarkerInfoWindow(marker);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng latLng = marker.getPosition();
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude + .003, latLng.longitude),15));
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_button_my_location:
                if (mLatLng != null){
                    LocationManager locationManager =
                            (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                    Criteria criteria = new Criteria();
                    String provider = locationManager.getBestProvider(criteria, false);
                    Location location;
                    try{
                        location = locationManager.getLastKnownLocation(provider);
                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new
                                LatLng(location.getLatitude(),
                                location.getLongitude()), 15));
                    }catch (SecurityException e){
                        e.printStackTrace();
                    }

                }else if (permisosNoOtorgados){
                    Toast.makeText(getContext(), "No has otorgado permisos de localización, favor de revisar en administrador de Aplicaciones de tu Célular.", Toast.LENGTH_LONG).show();
                    spn_Zonas.setSelection(currentSpinnerOption);
                }
                break;
        }
    }


    public interface ReservacionGeolocalizacionListener{
        void onClickMarkerInfoWindow(Marker marker);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReservacionGeolocalizacionListener) {
            listener = (ReservacionGeolocalizacionListener) context;
        } else {
            throw new IllegalArgumentException(context.toString() + "debe de implementar en onAttach");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private class MapsAdapterCustom implements GoogleMap.InfoWindowAdapter{

        @Override
        public View getInfoWindow(Marker marker) {



            Button btn = (Button) custom_info_view.findViewById(R.id.button_ver_clase_info);
            btn.setTypeface(TypeFaceCustom.getInstance(getContext()).UBUNTU_TYPE_FACE);



            return custom_info_view;
        }

        @Override
        public View getInfoContents(Marker marker) {
            if (marker != null && marker.isInfoWindowShown()){
                marker.hideInfoWindow();
            }
            return null;
        }
    }
}
