package com.tuinercia.inercia.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.implementation.ChangeTitleImpl;

/**
 * Created by ricar on 17/10/2017.
 */

public class AgendaFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;

    public static final String FRAGMENT_TAG  = "AgendaFragment";
    private static final String NOMBRE_PANTALLA = "Historial";
    private static final int TITLE = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mis_clases,container,false);

        tabLayout = (TabLayout) v.findViewById(R.id.tablayout_agenda_pasada);
        viewPager = (ViewPager) v.findViewById(R.id.view_pager_agenda_pasada);

        AgendaPageAdapter adapter = new AgendaPageAdapter(getFragmentManager() ,getContext());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ChangeTitleImpl.getInstance().changeTitleByCurrentFragment(TITLE);

        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        mFirebaseAnalytics.setCurrentScreen(getActivity(),FRAGMENT_TAG, null);


        return v;
    }

    public static class AgendaPageAdapter extends FragmentStatePagerAdapter {

        private static int NUM_ITEMS = 2;
        private int[] titles = {
                R.string.tab_title_agendas,
                R.string.tab_title_pasadas
        };
        Context mcontext;


        public AgendaPageAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.mcontext = context;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0:
                    return new AgendaActualFragment();
                case 1:
                    return new AgendaHistoryFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String t = mcontext.getResources().getString(titles[position]);
            return t;
        }
    }
}
