package com.tuinercia.inercia.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.tuinercia.inercia.DTO.Disciplines;
import com.tuinercia.inercia.DTO.Parlor;
import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.AgendaFragment;
import com.tuinercia.inercia.fragments.PagosFormularioAltaFragment;
import com.tuinercia.inercia.fragments.PagosInicioFragment;
import com.tuinercia.inercia.fragments.ReservacionClasesFragment;
import com.tuinercia.inercia.fragments.ReservacionGeolocalizacionFragment;
import com.tuinercia.inercia.fragments.dialogs.GeneralDialogFragment;
import com.tuinercia.inercia.implementation.ChangeTitleImpl;
import com.tuinercia.inercia.implementation.InerciaApiRegistroTokenFirebaseImpl;
import com.tuinercia.inercia.network.InerciaApiClient;
import com.tuinercia.inercia.utils.UtilsSharedPreference;

import static java.net.Proxy.Type.HTTP;

/**
 * Created by ricar on 22/09/2017.
 */

public class MainPage extends AppCompatActivity implements ReservacionClasesFragment.ReservacionClasesListener
                                                        , ReservacionGeolocalizacionFragment.ReservacionGeolocalizacionListener
                                                        , NavigationView.OnNavigationItemSelectedListener
                                                        , PagosInicioFragment.PagosInicioListener
                                                        , PagosFormularioAltaFragment.PagosFormularioAltaFragmentListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    View navHeader;
    Menu menu;

    static final String INTENT_EXTRA_HEADER = "parlor";
    static final String INTENT_EXTRA_HEADER_DISCIPLINE = "discipline";
    public static final String  INTENT_EXTRA_HEADER_GO_TO_MI_CUENTA = "GOTOmicuenta";
    private static final String CONTACT_SOS = "8131193404";
    private static final String CONTACT_EMAIL_SOS = "sos@tuinercia.com";

    private String[] array_titles;
    ChangeTitleImpl changeTitle;
    String firebaseToken;
    boolean messagePayment = true;

    User user;

    InerciaApiRegistroTokenFirebaseImpl inerciaApiRegistroTokenFirebase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        UtilsSharedPreference.getInstance(getApplicationContext()).checkLogin();


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigationDrawer);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        user = UtilsSharedPreference.getInstance(this).getUser();

        menu = navigationView.getMenu();
        array_titles = getResources().getStringArray(R.array.array_titles);
        changeTitle = ChangeTitleImpl.getInstance(this);


        navHeader = navigationView.getHeaderView(0);
        TextView text_name =  (TextView) navHeader.findViewById(R.id.name);
        TextView text_mail = (TextView) navHeader.findViewById(R.id.mail);

        text_name.setText(user.getName());
        text_mail.setText(user.getEmail());

        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.WHITE));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.title_main,R.string.title_main);

        actionBarDrawerToggle.syncState();

        changeTitle.changeTitleByCurrentFragment(0);

        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.WHITE));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);


        boolean goToMiCuenta = getIntent().getBooleanExtra(INTENT_EXTRA_HEADER_GO_TO_MI_CUENTA,false);

        if(goToMiCuenta){
            addFragment(R.id.frame_content_main,new PagosInicioFragment(), PagosInicioFragment.FRAGMENT_TAG);
        }else{
            if(!UtilsSharedPreference.getInstance(this).get_type_account() && messagePayment){
                GeneralDialogFragment.getInstance("Accesa a todas las clases que quieras con tu membresía tuinercia. Ve a \"Mi Cuenta\" para adquirirla.","Aceptar",null)
                        .show(getSupportFragmentManager(),null);
                messagePayment = false;
            }
            addFragment(R.id.frame_content_main,new ReservacionClasesFragment(), ReservacionClasesFragment.FRAGMENT_TAG);
        }

        firebaseToken = FirebaseInstanceId.getInstance().getToken();
        inerciaApiRegistroTokenFirebase = new InerciaApiRegistroTokenFirebaseImpl();
        InerciaApiClient.getInstance(this).registroTokenFirebase(user.getId(),firebaseToken,inerciaApiRegistroTokenFirebase);
    }


    public void replaceFragment(@IdRes int containerViewId,
                                @NonNull Fragment fragment,
                                @NonNull String fragmentTag,
                                @Nullable String backStackStateName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    public void replacePopFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) {
        getSupportFragmentManager()
                .popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    public void addFragment(@IdRes int containerViewId,
                            @NonNull Fragment fragment,
                            @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }
    private void emtyBackStack(){
        while (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void onClaseSeleccionada(String fragmentTAG, Disciplines disciplineSelected) {
        ReservacionGeolocalizacionFragment fragment = new ReservacionGeolocalizacionFragment();

        Bundle args = new Bundle();
        args.putString(fragment.DISCIPLINE_ARGS, InerciaApiClient.gson.toJson(disciplineSelected));

        fragment.setArguments(args);
        replaceFragment(R.id.frame_content_main, fragment, fragment.FRAGMENT_TAG, fragmentTAG);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.temp_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btn_sos:


                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.custom_dialog_sos);

                Button btn_sos_whatsapp, btn_sos_message, btn_sos_mail;

                btn_sos_whatsapp = dialog.findViewById(R.id.btn_sos_whatsapp);
                btn_sos_message = dialog.findViewById(R.id.btn_sos_message);
                btn_sos_mail = dialog.findViewById(R.id.btn_sos_mail);

                btn_sos_whatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PackageManager packageManager = getPackageManager();
                        String url = "https://api.whatsapp.com/send?phone=" + CONTACT_SOS ;
                        Intent waIntent = new Intent();
                        try{
                            waIntent.setType("text/plain");
                            String text= "text";

                            packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_META_DATA);
                            waIntent.setPackage("com.whatsapp");

                            waIntent.putExtra(Intent.EXTRA_TEXT,text);
                            waIntent.setData(Uri.parse(url));
                            startActivity(Intent.createChooser(waIntent,"Shared with"));
                        }catch (PackageManager.NameNotFoundException e){

                            url = "https://play.google.com/store/apps/details?id=com.whatsapp";
                            waIntent.setData(Uri.parse(url));
                            startActivity(waIntent);

                        }

                        dialog.dismiss();
                    }
                });
                btn_sos_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms",CONTACT_SOS ,null));
//                        intent.putExtra("sms_body","Hello dear.....");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                btn_sos_mail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String[] adress = {CONTACT_EMAIL_SOS};

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/html");
                        intent.putExtra(Intent.EXTRA_EMAIL, adress);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "SOS - Inercia");

                        startActivity(Intent.createChooser(intent, "Send Email"));

                        dialog.dismiss();
                    }
                });

                dialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClickMarkerInfoWindow(Parlor parlor, String discipline) {
        Intent i = new Intent(this,EstudioAgenda.class);
        String json_parlor = InerciaApiClient.getInstance(this).gson.toJson(parlor,Parlor.class);
        i.putExtra(INTENT_EXTRA_HEADER, json_parlor);
        i.putExtra(INTENT_EXTRA_HEADER_DISCIPLINE, discipline);
        startActivity(i);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment    = null;
        boolean mainPageBack = false;
        String previousTAG = getSupportFragmentManager().findFragmentById(R.id.frame_content_main).getTag()
                           , newTag = "";

        switch (item.getItemId()){
            case R.id.nav_home:
                fragment = new ReservacionClasesFragment();
                newTag = ReservacionClasesFragment.FRAGMENT_TAG;
                mainPageBack = true;
                break;
            case R.id.nav_pagos:
                fragment = new PagosInicioFragment();
                newTag = PagosFormularioAltaFragment.FRAGMENT_TAG;
                break;
            case R.id.nav_mis_clases:
                fragment = new AgendaFragment();
                newTag = AgendaFragment.FRAGMENT_TAG;
                break;
        }

        if (!newTag.equals(previousTAG)){
//            if (mainPageBack){
//                emtyBackStack();
//                addFragment(R.id.frame_content_main,fragment,newTag);
//            }else{
                replacePopFragment(R.id.frame_content_main,fragment,newTag,previousTAG);
//            }

        }
        drawerLayout.closeDrawers();

        return false;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public String[] getArray_titles() {
        return array_titles;
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsSharedPreference.getInstance(getApplicationContext()).checkLogin();
    }

    @Override
    public void onClickMejorarPlan() {
        String previousTag = getSupportFragmentManager().findFragmentById(R.id.frame_content_main).getTag();
        replaceFragment(R.id.frame_content_main, PagosFormularioAltaFragment.getInstance() , PagosFormularioAltaFragment.FRAGMENT_TAG,previousTag);
    }

    @Override
    public void OnCreatePaymentSuccess() {
        emtyBackStack();
        addFragment(R.id.frame_content_main, new PagosInicioFragment(), PagosInicioFragment.FRAGMENT_TAG);
    }
}
