package com.tuinercia.inercia.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.implementation.ChangeTitleImpl;
import com.tuinercia.inercia.implementation.InerciaApiGetCurrentMembershipListenerImpl;
import com.tuinercia.inercia.implementation.LoadingViewManagerImpl;
import com.tuinercia.inercia.interfaces.LoadingViewManager;
import com.tuinercia.inercia.network.InerciaApiClient;
import com.tuinercia.inercia.utils.UtilsSharedPreference;

/**
 * Created by ricar on 27/03/2018.
 */

public class PagosInicioFragment extends Fragment implements View.OnClickListener{

    public static final String FRAGMENT_TAG = "PagosInicioFragment";
    private final static int TITLE = 3;

    LinearLayout view_account_free, view_account_payment, view_account_payment_in_progress;
    Button btn_mejorar_plan, btn_cerrar_sesion;
    View view;

    User user;

    InerciaApiGetCurrentMembershipListenerImpl inerciaApiGetCurrentMembershipListener;
    PagosInicioListener pagosInicioListener;
    LoadingViewManagerImpl loadingViewManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v   = inflater.inflate(R.layout.fragment_pagos_inicio,container,false);

        view_account_free = (LinearLayout) v.findViewById(R.id.view_account_free);
        view_account_payment = (LinearLayout) v.findViewById(R.id.view_account_payment);
        view_account_payment_in_progress = (LinearLayout) v.findViewById(R.id.view_account_payment_in_progress);
        btn_mejorar_plan = (Button) v.findViewById(R.id.btn_mejorar_plan);
        btn_cerrar_sesion = (Button) v.findViewById(R.id.btn_cerrar_sesion);
        view = v.findViewById(R.id.loading_view);
        user = UtilsSharedPreference.getInstance(getContext()).getUser();

        inerciaApiGetCurrentMembershipListener = new InerciaApiGetCurrentMembershipListenerImpl(this);
        loadingViewManager = new LoadingViewManagerImpl(view);

        if(UtilsSharedPreference.getInstance(getContext()).get_type_account()){

            InerciaApiClient.getInstance(getContext()).getCurrentMemberShip(user.getEmail(), user.getPassword_digest(), inerciaApiGetCurrentMembershipListener,loadingViewManager);
        }

        ChangeTitleImpl.getInstance().changeTitleByCurrentFragment(TITLE);
        btn_mejorar_plan.setOnClickListener(this);
        btn_cerrar_sesion.setOnClickListener(this);

        return v;
    }

    public LinearLayout getView_account_free() {
        return view_account_free;
    }
    public void setView_account_free(LinearLayout view_account_free) {
        this.view_account_free = view_account_free;
    }

    public LinearLayout getView_account_payment() {
        return view_account_payment;
    }
    public void setView_account_payment(LinearLayout view_account_payment) {
        this.view_account_payment = view_account_payment;
    }

    public LinearLayout getView_account_payment_in_progress() {
        return view_account_payment_in_progress;
    }
    public void setView_account_payment_in_progress(LinearLayout view_account_payment_in_progress) {
        this.view_account_payment_in_progress = view_account_payment_in_progress;
    }

    public interface PagosInicioListener{
        void onClickMejorarPlan();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_mejorar_plan:
                pagosInicioListener.onClickMejorarPlan();
                break;
            case R.id.btn_cerrar_sesion:
                UtilsSharedPreference.getInstance(getContext()).logOutUser();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PagosInicioListener) {
            pagosInicioListener = (PagosInicioListener) context;
        } else {
            throw new IllegalArgumentException(context.toString() + "debe de implementar en onAttach");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        pagosInicioListener = null;
    }
}
