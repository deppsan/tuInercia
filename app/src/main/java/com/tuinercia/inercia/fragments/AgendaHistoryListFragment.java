package com.tuinercia.inercia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tuinercia.inercia.DTO.History;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.adapter.HistoryRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

public class AgendaHistoryListFragment extends Fragment {

    RecyclerView rcy_mis_clases_history;

    List<History> trainings;

    HistoryRecycleAdapter historyRecycleAdapter;

    public static final String FRAGMENT_TAG = "AgendaHistoryListFragment";
    public static final String HISTORIAL_TAG = "historial";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mis_clases_history_list, container, false);

        History[] history = (History[]) getArguments().getSerializable(HISTORIAL_TAG);

        trainings = new ArrayList<>();

        for (History h : history){
            trainings.add(h);
        }

        rcy_mis_clases_history = (RecyclerView) v.findViewById(R.id.rcy_mis_clases_history);
        historyRecycleAdapter = new HistoryRecycleAdapter(trainings, getContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        rcy_mis_clases_history.setLayoutManager(layoutManager);
        rcy_mis_clases_history.setItemAnimator(new DefaultItemAnimator());
        rcy_mis_clases_history.setAdapter(historyRecycleAdapter);

        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        mFirebaseAnalytics.setCurrentScreen(getActivity(),FRAGMENT_TAG, null);

        return v;
    }

    public static AgendaHistoryListFragment newInstance(History[] historial){
        AgendaHistoryListFragment agendaHistoryListFragment = new AgendaHistoryListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(HISTORIAL_TAG, historial);

        agendaHistoryListFragment.setArguments(bundle);

        return agendaHistoryListFragment;
    }
}
