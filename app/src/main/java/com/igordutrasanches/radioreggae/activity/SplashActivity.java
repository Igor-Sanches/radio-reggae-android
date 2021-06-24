package com.igordutrasanches.radioreggae.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.igordutrasanches.radioreggae.MainActivity;
import com.igordutrasanches.radioreggae.R;
import com.igordutrasanches.radioreggae.data.Data;
import com.igordutrasanches.radioreggae.model.Radio;
import com.igordutrasanches.radioreggae.navigation.Navigation;
import com.igordutrasanches.radioreggae.tools.Tools;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try{
                     Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        Radio r = Tools.getRadios().get(Data.getPositionPlayList(SplashActivity.this));
                        if(r != null){
                            intent.putExtra("radio", r);
                            startActivity(intent);
                            finish();
                        }

                }catch (Exception x){
                    new AlertDialog.Builder(SplashActivity.this).setMessage(x.getMessage()).setPositiveButton("OK", null).show();
                }

             }
        }, 2000);
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(timer!=null){
            timer.cancel();
            timer = null;
        }
        super.onPause();
    }
}