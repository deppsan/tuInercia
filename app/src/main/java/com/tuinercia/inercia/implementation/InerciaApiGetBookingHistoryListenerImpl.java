package com.tuinercia.inercia.implementation;

import com.tuinercia.inercia.DTO.History;
import com.tuinercia.inercia.fragments.AgendaHistoryFragment;
import com.tuinercia.inercia.interfaces.InerciaApiGetBookingHistoryListener;


public class InerciaApiGetBookingHistoryListenerImpl implements InerciaApiGetBookingHistoryListener {

    AgendaHistoryFragment agendaHistoryFragment;


    public InerciaApiGetBookingHistoryListenerImpl(AgendaHistoryFragment agendaHistoryFragment) {
        this.agendaHistoryFragment = agendaHistoryFragment;
    }


    @Override
    public void onGetBookingHistorySuccess(History[] actual_history, History[] prev_history, History[] ant_prev_history) {
        AgendaHistoryFragment.HistoryPageAdapter historyPageAdapter = new AgendaHistoryFragment.HistoryPageAdapter(agendaHistoryFragment.getFragmentManager(), agendaHistoryFragment.getContext(),actual_history, prev_history, ant_prev_history);

        agendaHistoryFragment.view_pager_history.setAdapter(historyPageAdapter);
        agendaHistoryFragment.tablayout_history.setupWithViewPager(agendaHistoryFragment.view_pager_history);
        agendaHistoryFragment.view_pager_history.setCurrentItem(2);
    }

    @Override
    public void onGetBookingHistoryError(String errorMessage) {

    }

    @Override
    public void onErrorServer(int statusCode) {

    }
}
