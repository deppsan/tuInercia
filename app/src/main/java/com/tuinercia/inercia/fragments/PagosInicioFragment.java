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
import com.tuinercia.inercia.network.InerciaApiClient;
import com.tuinercia.inercia.utils.UtilsSharedPreference;

/**
 * Created by ricar on 27/03/2018.
 */

public class PagosInicioFragment extends Fragment implements View.OnClickListener{

    public static final String FRAGMENT_TAG = "PagosInicioFragment";
    private final static int TITLE = 3;

    LinearLayout view_account_free, view_account_payment;
    Button btn_mejorar_plan;

    User user;

    InerciaApiGetCurrentMembershipListenerImpl inerciaApiGetCurrentMembershipListener;
    PagosInicioListener pagosInicioListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v   = inflater.inflate(R.layout.fragment_pagos_inicio,container,false);

        view_account_free = (LinearLayout) v.findViewById(R.id.view_account_free);
        view_account_payment = (LinearLayout) v.findViewById(R.id.view_account_payment);
        btn_mejorar_plan = (Button) v.findViewById(R.id.btn_mejorar_plan);

        user = UtilsSharedPreference.getInstance(getContext()).getUser();

        inerciaApiGetCurrentMembershipListener = new InerciaApiGetCurrentMembershipListenerImpl(this);

        if(UtilsSharedPreference.getInstance(getContext()).get_type_account()){
            view_account_payment.setVisibility(View.VISIBLE);
            view_account_free.setVisibility(View.GONE);

            InerciaApiClient.getInstance(getContext()).getCurrentMemberShip(user.getEmail(), user.getPassword_digest(), inerciaApiGetCurrentMembershipListener);
        }

        ChangeTitleImpl.getInstance().changeTitleByCurrentFragment(TITLE);



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

    public interface PagosInicioListener{
        void onClickMejorarPlan();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_mejorar_plan:
                pagosInicioListener.onClickMejorarPlan();
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
