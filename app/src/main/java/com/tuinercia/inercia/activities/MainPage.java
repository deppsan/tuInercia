package com.tuinercia.inercia.activities;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.AgendaFragment;
import com.tuinercia.inercia.fragments.PagosWebViewFragment;
import com.tuinercia.inercia.fragments.ReservacionClasesFragment;
import com.tuinercia.inercia.fragments.ReservacionGeolocalizacionFragment;

/**
 * Created by ricar on 22/09/2017.
 */

public class MainPage extends AppCompatActivity implements ReservacionClasesFragment.ReservacionClasesListener
                                                        , ReservacionGeolocalizacionFragment.ReservacionGeolocalizacionListener
                                                        , NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    View navHeader;
    Menu menu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigationDrawer);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        menu = navigationView.getMenu();
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.WHITE));

        navHeader = navigationView.getHeaderView(0);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        addFragment(R.id.frame_content_main,new ReservacionClasesFragment(), ReservacionClasesFragment.FRAGMENT_TAG);
    }


    public void replaceFragment(@IdRes int containerViewId,
                                @NonNull Fragment fragment,
                                @NonNull String fragmentTag,
                                @Nullable String backStackStateName) {
        getSupportFragmentManager()
                .popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
//                .addToBackStack(backStackStateName)
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

    @Override
    public void onClaseSeleccionada(String fragmentTAG) {
        replaceFragment(R.id.frame_content_main,new ReservacionGeolocalizacionFragment(), ReservacionGeolocalizacionFragment.FRAGMENT_TAG,fragmentTAG);
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
            /*case R.id.btnCategorias:
                Toast.makeText(this, "Aqui debera ir una pantalla de agenda =P", Toast.LENGTH_SHORT).show();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClickMarkerInfoWindow(Marker marker) {
        Intent i = new Intent(this,EstudioAgenda.class);
        startActivity(i);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment    = null;
        String previousTAG = getSupportFragmentManager().findFragmentById(R.id.frame_content_main).getTag()
                            , newTag = "";

        switch (item.getItemId()){
            case R.id.nav_home:
                fragment = new ReservacionClasesFragment();
                newTag = ReservacionClasesFragment.FRAGMENT_TAG;
                break;
            case R.id.nav_pagos:
                fragment = new PagosWebViewFragment();
                newTag = PagosWebViewFragment.FRAGMENT_TAG;
                break;
            case R.id.nav_mis_clases:
                fragment = new AgendaFragment();
                newTag = AgendaFragment.FRAGMENT_TAG;
                break;
            /*case R.id.nav_salir:

                break;*/
        }

        if (!newTag.equals(previousTAG)){
            replaceFragment(R.id.frame_content_main,fragment,newTag,previousTAG);
        }
        drawerLayout.closeDrawers();

        return false;
    }
}
