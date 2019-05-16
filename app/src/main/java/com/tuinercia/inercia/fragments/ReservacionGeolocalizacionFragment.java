package com.tuinercia.inercia.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.tuinercia.inercia.DTO.Disciplines;
import com.tuinercia.inercia.DTO.Parlor;
import com.tuinercia.inercia.DTO.Zone;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.dialogs.GeneralDialogFragment;
import com.tuinercia.inercia.implementation.ChangeTitleImpl;
import com.tuinercia.inercia.implementation.InerciaApiGetParlorsListenerImpl;
import com.tuinercia.inercia.implementation.InerciaApiGetZonesListenerImpl;
import com.tuinercia.inercia.network.InerciaApiClient;
import com.tuinercia.inercia.utils.TypeFaceCustom;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;

/**
 * Created by ricar on 25/09/2017.
 */

public class ReservacionGeolocalizacionFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    MapView mMap;
    public GoogleMap gMap;
    public Spinner spn_Zonas;
    static View custom_info_view;
    ImageButton image_button_my_location;

    FusedLocationProviderClient mFusedLocationClient;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    LatLng mLatLng;

    public static ArrayList<Parlor> res_parlors;
    boolean permisosNoOtorgados = false;
    int permissionCheck;
    int currentSpinnerOption = 0;
    boolean firstEntry = true;
    Disciplines disciplineSelected;
    ReservacionGeolocalizacionListener listener;

    public static final String FRAGMENT_TAG = "ReservacionGeolocalizacionFragment";
    public static final String DISCIPLINE_ARGS = "discipline";
    private static final int MY_PERMISSIONS_REQUEST_GET_LOCATION = 0;
    private static final int TITLE = 1;

    static Context mContext;

    InerciaApiGetParlorsListenerImpl inerciaApiGetParlorsListener;
    InerciaApiGetZonesListenerImpl inerciaApiGetZonesListener;

    LocationManager locationManager;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_agendar_geolocalizacion, container, false);

        permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        mContext = getContext();

        image_button_my_location = (ImageButton) v.findViewById(R.id.image_button_my_location);
        spn_Zonas                = (Spinner) v.findViewById(R.id.spn_Zonas);
        mMap                     = (MapView) v.findViewById(R.id.map);

        custom_info_view = getLayoutInflater(savedInstanceState).inflate(R.layout.object_maps_info_window,null);

        disciplineSelected = InerciaApiClient.gson.fromJson(getArguments().getString(DISCIPLINE_ARGS),Disciplines.class);


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
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            image_button_my_location.setVisibility(View.VISIBLE);
        }

        spn_Zonas.setOnItemSelectedListener(this);

        inerciaApiGetParlorsListener = new InerciaApiGetParlorsListenerImpl(this);
        inerciaApiGetZonesListener = new InerciaApiGetZonesListenerImpl(this);

        InerciaApiClient.getInstance(getActivity().getBaseContext()).getAllZones(inerciaApiGetZonesListener);
        ChangeTitleImpl.getInstance().changeTitleByCurrentFragment(TITLE);

        Toast.makeText(mContext, mContext.getString(R.string.toast_geoocalizacion), Toast.LENGTH_LONG).show();


        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        mFirebaseAnalytics.setCurrentScreen(getActivity(),FRAGMENT_TAG, null);

        return v;
    }

    LocationCallback mLocationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()){
                mLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,12.0f));
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

        if(!firstEntry){
            Zone zone = (Zone) spn_Zonas.getSelectedItem();

            cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(Double.parseDouble(zone.getLat()),Double.parseDouble(zone.getLng())))
                    .zoom(13.0f)
                    .build();
            gMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            currentSpinnerOption = position;
        }else{
            firstEntry = false;
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
                                if (mGoogleApiClient == null){
                                    buildGoogleApiClient();
                                }
                                gMap.setMyLocationEnabled(true);
                                gMap.getUiSettings().setMyLocationButtonEnabled(false);

                                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                    image_button_my_location.setVisibility(View.VISIBLE);
                                }
                            } catch (SecurityException e) {
                                e.printStackTrace();
                            }
                        }else{
                            permisosNoOtorgados = true;
                            spn_Zonas.setSelection(0);
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

        InerciaApiClient.getInstance(mContext).getParlorsByDicipline(disciplineSelected.getName(),inerciaApiGetParlorsListener);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(25.6673117,-100.3473442),13.0f));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                try {
                    gMap.setMyLocationEnabled(true);
                    gMap.getUiSettings().setMyLocationButtonEnabled(false);
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        image_button_my_location.setVisibility(View.VISIBLE);
                    }

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
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    image_button_my_location.setVisibility(View.VISIBLE);
                }
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
        int i = Integer.parseInt(marker.getTitle());
        Parlor p  = res_parlors.get(i);

        listener.onClickMarkerInfoWindow(p,disciplineSelected.getId());
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng latLng = marker.getPosition();
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude + .003, latLng.longitude),12.0f));
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_button_my_location:
                if (mLatLng != null){
                    Criteria criteria = new Criteria();

                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        String provider = locationManager.getBestProvider(criteria, false);
                        Location location;

                        try{
                            location = locationManager.getLastKnownLocation(provider);
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
                                    LatLng(location.getLatitude(),
                                    location.getLongitude()), 15.0f));
                        }catch (SecurityException e){
                            e.printStackTrace();

                        }
                    }else{
                        GeneralDialogFragment.getInstance("Debes tener el servicio de GPS activado para usar esta opción. Favor de activarlo en configuraciones de tu celular.", getResources().getString(R.string.btn_login_check),null)
                        .show(this.getFragmentManager(),null);
                    }

                }else if (permisosNoOtorgados){
                    Toast.makeText(getContext(), "No has otorgado permisos de localización, favor de revisar en administrador de Aplicaciones de tu Célular.", Toast.LENGTH_LONG).show();
                    spn_Zonas.setSelection(currentSpinnerOption);
                }
                return;
        }
    }

    public interface ReservacionGeolocalizacionListener{
        void onClickMarkerInfoWindow(Parlor parlor, String discipline);
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

    public static class MapsAdapterCustom implements GoogleMap.InfoWindowAdapter{


        @Override
        public View getInfoWindow(Marker marker) {

            int i = Integer.parseInt(marker.getTitle());
            Parlor p  = res_parlors.get(i);

            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display d = wm.getDefaultDisplay();
            Point size = new Point();
            d.getSize(size);

            int mHeight = size.y;
            int actionBarHeight = 0;

            TypedValue tv = new TypedValue();
            if (mContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,mContext.getResources().getDisplayMetrics());
            }

            custom_info_view.setMinimumHeight(((mHeight/3))-(actionBarHeight/3));

            Button button_ver_clase_info;
            TextView text_info_title,text_info_description;
            ImageView img_info_studio;

            button_ver_clase_info = (Button) custom_info_view.findViewById(R.id.button_ver_clase_info);
            text_info_description = (TextView) custom_info_view.findViewById(R.id.text_info_description);
            text_info_title = (TextView) custom_info_view.findViewById(R.id.text_info_title);
            img_info_studio = (ImageView) custom_info_view.findViewById(R.id.img_info_studio);

            text_info_description.setText(p.getDescription());
            text_info_description.setMinimumHeight(actionBarHeight * 2);
            text_info_title.setText(p.getName());
            text_info_title.setMinimumHeight(actionBarHeight);

            Picasso mPicasso = Picasso.with(mContext);
            boolean no_first_time = false;

            try{
                no_first_time = (boolean) marker.getTag();
            }catch (Exception e){

            }

            if (no_first_time){
//                mPicasso.load(p.getPic1_url()).resize(250,((mHeight/3))-(actionBarHeight/3)).centerCrop().into(img_info_studio);
                mPicasso.load(p.getPic1_url()).resize(250,((mHeight/3))-(actionBarHeight/3)).centerInside().into(img_info_studio);
            }else{
                marker.setTag(true);
//                mPicasso.load(p.getPic1_url()).resize(250,((mHeight/3))-(actionBarHeight/3)).centerCrop().into(img_info_studio, new InfoWindowRefreser(marker));
                mPicasso.load(p.getPic1_url()).resize(250,((mHeight/3))-(actionBarHeight/3)).centerInside().into(img_info_studio, new InfoWindowRefreser(marker));
            }

            button_ver_clase_info.setTypeface(TypeFaceCustom.getInstance(mContext).UBUNTU_TYPE_FACE);

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

    private static class InfoWindowRefreser implements Callback{

        private Marker markerRefresher;

        public InfoWindowRefreser(Marker markerRefresher) {
            this.markerRefresher = markerRefresher;
        }

        @Override
        public void onSuccess() {
            markerRefresher.showInfoWindow();
        }

        @Override
        public void onError() {

        }
    }

}


