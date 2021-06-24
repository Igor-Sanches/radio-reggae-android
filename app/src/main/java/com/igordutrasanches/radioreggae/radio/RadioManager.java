package com.igordutrasanches.radioreggae.radio;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

public class RadioManager {
    private static RadioManager instance = null;

    private static RadioService service;

    private Context context;
    public boolean isNuloPlayer(){
        return service.exoPlayer == null;
    }

    private boolean avaible = false;

    public boolean isAvaible(){
        return avaible;
    }

    private boolean serviceBound;

    public RadioManager(Context context) {
        this.context = context;
        serviceBound = false;
    }

    public static RadioManager with(Context context) {
        if (instance == null)
            instance = new RadioManager(context);


        return instance;
    }

    public static RadioService getService(){
        return service;
    }

    public void pause(){service.pause();}

    public void stop(){service.stop();}

    public void playOrPause(String streamUrl){
        service.playOrPause(streamUrl);
    }

    public String getUri(){
        return service.getUri();
    }

    public boolean isPlaying() {
        return service.isPlaying();
    }

    public void bind() {
        Intent intent = new Intent(context, RadioService.class);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        if(service != null)
            EventBus.getDefault().post(service.getStatus());

        avaible = true;
    }

    public void unbind() {
        context.unbindService(serviceConnection);
        avaible = true;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName arg0, IBinder binder) {
            service = ((RadioService.LocalBinder) binder).getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            serviceBound = false;
        }
    };

    public void play(String urlStreaming) {
        service.play(urlStreaming);
    }
}
