package com.tuinercia.inercia.implementation;

import android.support.v7.widget.Toolbar;

import com.tuinercia.inercia.activities.MainPage;
import com.tuinercia.inercia.interfaces.ChangeTitle;

/**
 * Created by ricar on 24/10/2017.
 */

public class ChangeTitleImpl implements ChangeTitle {

    MainPage mainPage;
    static ChangeTitleImpl changeTitle;

    private ChangeTitleImpl(MainPage mainPage) {
        this.mainPage = mainPage;
    }

    @Override
    public void changeTitleByCurrentFragment(int position) {
        mainPage.getToolbar().setTitle(mainPage.getArray_titles()[position]);
    }

    public static ChangeTitleImpl getInstance(MainPage mainPage){
        if (changeTitle == null){
            changeTitle = new ChangeTitleImpl(mainPage);
        }
        return changeTitle;
    }
    public static ChangeTitleImpl getInstance(){
        return changeTitle;
    }
}
