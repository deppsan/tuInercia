package com.tuinercia.inercia.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuinercia.inercia.DTO.History;
import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.implementation.InerciaApiGetBookingHistoryListenerImpl;
import com.tuinercia.inercia.implementation.LoadingViewManagerImpl;
import com.tuinercia.inercia.network.InerciaApiClient;
import com.tuinercia.inercia.utils.CustomViewPager;
import com.tuinercia.inercia.utils.UtilsSharedPreference;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

public class AgendaHistoryFragment extends Fragment {

    public static final String FRAGMENT_TAG = "AgendaHistoryFragment";

    public TabLayout tablayout_history;
    public CustomViewPager view_pager_history;
    View loadingView;


    LoadingViewManagerImpl loadingViewManager;
    InerciaApiGetBookingHistoryListenerImpl inerciaApiGetBookingHistoryListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mis_clases_history, container, false);


        tablayout_history = (TabLayout) v.findViewById(R.id.tablayout_history);
        view_pager_history = (CustomViewPager) v.findViewById(R.id.view_pager_history);
        loadingView = v.findViewById(R.id.loading_view);

        User user = UtilsSharedPreference.getInstance(getContext()).getUser();

        loadingViewManager = new LoadingViewManagerImpl(loadingView);
        inerciaApiGetBookingHistoryListener = new InerciaApiGetBookingHistoryListenerImpl(this);

        InerciaApiClient.getInstance(getActivity()).getBookingHistory(Integer.toString(user.getId()), inerciaApiGetBookingHistoryListener);

        return v;
    }


    public static class HistoryPageAdapter extends FragmentStatePagerAdapter{

        private static final int NUM_ITEMS = 3;
        private static ArrayList<String> titles  = new ArrayList<>();
        Context mcontext;
        ArrayList<History[]> historial = new ArrayList<>();

        public HistoryPageAdapter(FragmentManager fm, Context mContext, History[] actual_history, History[] prev_history, History[] ant_prev_history ) {
            super(fm);

            this.mcontext = mContext;
            Calendar cal = Calendar.getInstance();

            cal.add(Calendar.MONTH, -2);
            titles.add(getMonth(cal.get(Calendar.MONTH)));
            cal.add(Calendar.MONTH, +1);
            titles.add(getMonth(cal.get(Calendar.MONTH)));
            cal.add(Calendar.MONTH, +1);
            titles.add(getMonth(cal.get(Calendar.MONTH)));

            historial.add(ant_prev_history);
            historial.add(prev_history);
            historial.add(actual_history);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return AgendaHistoryListFragment.newInstance(historial.get(0));
                case 1:
                    return AgendaHistoryListFragment.newInstance(historial.get(1));
                case 2:
                    return AgendaHistoryListFragment.newInstance(historial.get(2));
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
        public String getMonth(int month) {
            return new DateFormatSymbols().getMonths()[month];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String t = titles.get(position);
            return t;
        }
    }
}
