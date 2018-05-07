package com.tuinercia.inercia.activities;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.CrearCuentaFragment;
import com.tuinercia.inercia.fragments.CrearCuentaWizard2Fragment;
import com.tuinercia.inercia.fragments.LoginFragment;
import com.tuinercia.inercia.fragments.LoginOptionFragment;
import com.tuinercia.inercia.utils.UtilsSharedPreference;

public class LoginMain extends AppCompatActivity implements LoginOptionFragment.LoginOptionListener
                                                            , LoginFragment.LoginListener
                                                            , CrearCuentaFragment.CrearCuentaListener
                                                            , CrearCuentaWizard2Fragment.CrearCuentaListener2{

    FrameLayout frame_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        frame_content = (FrameLayout) findViewById(R.id.frame_content);


        setContentView(R.layout.activity_login_main);

        if(UtilsSharedPreference.getInstance(this).isLoggedIn()){
            goToMainPage();
        }else{
            addFragment(R.id.frame_content, new LoginOptionFragment(),LoginOptionFragment.FRAGMENT_TAG);
        }
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

    public void replaceFragment(@IdRes int containerViewId,
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
    public void backReplaceFragment(@IdRes int containerViewId,
                                         @NonNull Fragment fragment,
                                         @NonNull String fragmentTag,
                                         @Nullable String backStackStateName) {
        /*getSupportFragmentManager()
                .popBackStack();*/
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    @Override
    public void OnClickBtnLogin(String FragmentAnterior) {
        replaceFragment(R.id.frame_content, LoginFragment.getInstance(null, null, false),LoginFragment.FRAGMENT_TAG,FragmentAnterior);
    }

    @Override
    public void OnClickBtnSignUp(String FragmentAnterior) {
        replaceFragment(R.id.frame_content,new CrearCuentaFragment(),CrearCuentaFragment.FRAGMENT_TAG,FragmentAnterior);
    }

    @Override
    public void onClickButtonLogin() {
        goToMainPage();
    }

    private void goToMainPage(){
        Intent intent = new Intent(this,MainPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToNextStep(String email, String password, String FragmentAnterior) {
        Bundle args = new Bundle();
        CrearCuentaWizard2Fragment crearCuentaWizard2Fragment = new CrearCuentaWizard2Fragment();

        args.putString(CrearCuentaWizard2Fragment.EMAIL_FRAGMENT_PARAM, email);
        args.putString(CrearCuentaWizard2Fragment.PASSWORD_FRAGMENT_PARAM, password);

        crearCuentaWizard2Fragment.setArguments(args);

        backReplaceFragment(R.id.frame_content, crearCuentaWizard2Fragment,crearCuentaWizard2Fragment.FRAGMENT_TAG,FragmentAnterior);
    }


    @Override
    public void onCrearCuenta(String FragmentAnterior, String user, String password) {
        replaceFragment(R.id.frame_content, LoginFragment.getInstance(user, password, true),LoginFragment.FRAGMENT_TAG,FragmentAnterior);
    }


}


