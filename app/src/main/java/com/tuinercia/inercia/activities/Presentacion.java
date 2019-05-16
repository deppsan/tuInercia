package com.tuinercia.inercia.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.crashlytics.android.Crashlytics;
import com.tuinercia.inercia.R;

import java.util.Timer;
import java.util.TimerTask;

import io.fabric.sdk.android.Fabric;

/**
 * Created by ricar on 19/09/2017.
 */

public class Presentacion extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_presentacion);

        final Handler h = new Handler();
        final Context mContext = this;

        TimerTask doAsynchronustTask = new TimerTask() {
            @Override
            public void run() {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent  = new Intent(mContext,LoginMain.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(doAsynchronustTask,1000);
    }
}
