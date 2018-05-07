package com.tuinercia.inercia.implementation;

import android.view.View;

import com.tuinercia.inercia.interfaces.LoadingViewManager;

/**
 * Created by ricar on 05/04/2018.
 */

public class LoadingViewManagerImpl implements LoadingViewManager {

    View view;

    public LoadingViewManagerImpl(View view) {
        this.view = view;
    }

    @Override
    public void showLoadingView() {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        view.setVisibility(View.GONE);
    }
}
