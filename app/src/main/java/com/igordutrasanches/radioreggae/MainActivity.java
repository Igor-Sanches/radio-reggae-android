package com.igordutrasanches.radioreggae;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.igordutrasanches.radioreggae.data.Data;
import com.igordutrasanches.radioreggae.menu.MenuHamburger;
import com.igordutrasanches.radioreggae.model.Radio;
import com.igordutrasanches.radioreggae.navigation.Navigation;
import com.igordutrasanches.radioreggae.radio.PlaybackStatus;
import com.igordutrasanches.radioreggae.radio.RadioManager;
import com.igordutrasanches.radioreggae.tools.Tools;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView name, slogan;
    private Toolbar toolbar, menu_toolbar;
    private SeekBar seekBar;
    private Timer timer;
    private ImageView btn_volume;
    private long timer_count = 0;
    private CheckBox btn_favorito;
    private ProgressBar progressBar;
    private FloatingActionButton btnPlayPause, onPrevious, onNext;
    private RadioManager radioManager;
    public Radio radio;
    private List<Radio> lista;
    private static MainActivity instance;

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_main);
            Tools.nextBackground(this, false);
            instance = this;
            lista = Tools.getRadios();
            compomentfindViewById();
            radioManager = new RadioManager(this);
            boolean isAutoPlay = getIntent().getBooleanExtra("autoPlay", false);
            radio = (Radio)getIntent().getSerializableExtra("radio");

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNavigationView(true);
                }
            });
            menu_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNavigationView(false);
                }
            });

            seekBar.setProgress(Tools.toIntVolume(this));
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    onChangedVolume(i);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            if(isAutoPlay){
                if(RadioManager.getService() != null){
                    if(radioManager.getUri() != null) {
                        if(radio.getUrlStreaming()!=null) {
                            if (!radioManager.getUri().equals(radio.getUrlStreaming())) {
                                radioManager.playOrPause(radio.getUrlStreaming());
                            }
                        }
                    }
                }
            }

            if(radio != null){
                infoRadio();
            }

        }catch (Exception x){
            message(x.getMessage());
        }
    }

    private void compomentfindViewById() {
        onPrevious = findViewById(R.id.onPrevious);
        name = findViewById(R.id.titulo);
        slogan = findViewById(R.id.slogan);
        onNext = findViewById(R.id.onNext);
         progressBar = findViewById(R.id.exo_progress);
        btnPlayPause = findViewById(R.id.playerState);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        menu_toolbar = findViewById(R.id.toolbar_menu);
        btn_favorito = findViewById(R.id.btn_favorito);
        seekBar = findViewById(R.id.seekbar_volume);
        btn_volume = findViewById(R.id.btn_volume);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        radioManager.unbind();
        //if(timer!=null){
        //    timer.cancel();
        //    timer=null;
        //}
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //if(timer!=null){
          //timer.cancel();
           // timer=null;
        //}
    }

    @Override
    protected void onResume() {
        super.onResume();
        radioManager.bind();
        if(radio == null){
            return;
        }
        try{

            infoRadio();

        }catch (Exception x){

        }
        //if(timer==null){
          //  iniciarTimer();
        //}
    }

    private void iniciarTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Tools.nextBackground(MainActivity.this, true);
                        }catch (Exception x){
                            message(x.getMessage());
                        }
                    }
                });
            }
        };
        timer = new Timer("Timer");
        timer.scheduleAtFixedRate(timerTask, 40000, 40000);
    }

    @Subscribe
    public void onEvent(String status){
        switch (status){
            case PlaybackStatus.LOADING:
                btnPlayPause.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                break;
            case PlaybackStatus.PLAYING:
                progressBar.setVisibility(View.GONE);
                btnPlayPause.setVisibility(View.VISIBLE);
                Data.setPositionPlayList(getPosition(), this);
                 break;
            case PlaybackStatus.ERROR:
                progressBar.setVisibility(View.GONE);
                btnPlayPause.setVisibility(View.VISIBLE);
                if(isConnected()){
                    Toast.makeText(this, R.string.erro_no_stream, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, R.string.no_stream, Toast.LENGTH_SHORT).show();
                }
                break;

        }

        onNext.setEnabled(!status.equals(PlaybackStatus.LOADING));
        onPrevious.setEnabled(!status.equals(PlaybackStatus.LOADING));
        btnPlayPause.setImageResource(status.equals(PlaybackStatus.PLAYING)
                ? R.drawable.exo_controls_pause
                : R.drawable.exo_controls_play);
        if(RadioManager.getService() != null){
            if(radioManager.isPlaying()){
                if(!radioManager.getUri().equals(radio.getUrlStreaming())){
                    radioManager.pause();
                }
            }
        }
        infoRadio();
    }

    void message(String msg){
        new androidx.appcompat.app.AlertDialog.Builder(this).setMessage(msg).show();
    }

    public void onPlayPause(View view) {
        try{
            if(radioManager.isPlaying()){
                radioManager.pause();
            }else{
                radioManager.play(radio.getUrlStreaming());
            }

        }catch (Exception x){
            message(x.getMessage());
        }
    }

    private void onChangedVolume(int volume) {
        switch (volume) {
            case 0:
                btn_volume.setImageResource(R.drawable.ic_action_valume_off);
                break;
            case 1: case 2: case 3:
                btn_volume.setImageResource(R.drawable.ic_action_valume_down);
                break;
            case 4: case 5: case 6:
                btn_volume.setImageResource(R.drawable.ic_action_valume_medium);
                break;
            case 7: case 8: case 9: case 10:
                btn_volume.setImageResource(R.drawable.ic_action_valume_max);
                break;
        }
        Data.setVolume(Tools.toFloatVolume(volume), this);
        if(RadioManager.getService() != null){
            RadioManager.getService().onChanged(Data.getVolume(this));
        }
    }

    private void onNavigationView(boolean isOpen) {
        if(isOpen){
            drawerLayout.openDrawer(GravityCompat.START);
        }else{
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            onNavigationView(false);
            return;
        }
        if(System.currentTimeMillis() - timer_count > 2000){
            Toast.makeText(this, getString(R.string.click_exit), Toast.LENGTH_SHORT).show();
            timer_count = System.currentTimeMillis();
            return;
        }
        finishAffinity();
    }

    public void onMenuItem(View view) {
        try{
            if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                onNavigationView(false);
            }
            LinearLayout btn = (LinearLayout)view;
            String tag = btn.getTag().toString();
            if(tag.equals("key_changed_background")){
                Tools.nextBackground(this, true);
                return;
            }
            MenuHamburger.context(this).onClickItem(tag);
        }catch (Exception x){ }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            if(resultCode == 10){
                radio = (Radio)data.getSerializableExtra("radio");
                if(radio != null){
                    infoRadio();

                }
            }
        }
    }

    public void onVolumeClick(View view) {
        if(Tools.toIntVolume(this) > 0){
            Data.setVolumeOld(Tools.toFloatVolume(Tools.toIntVolume(this)), this);
            seekBar.setProgress(0);
            onChangedVolume(0);
            Data.setVolume(0, this);
        }else{
            Data.setVolume(Data.getVolumeOld(this), this);
            seekBar.setProgress(Tools.toIntVolume(this));
            onChangedVolume(seekBar.getProgress());
        }

    }

    private void infoRadio(){
         btn_favorito.setChecked(Data.isFavorito(radio.getUrlStreaming(), this));
            onNext.setEnabled(lista.size() > 1);
            onPrevious.setEnabled(lista.size() > 1);
            name.setText(radio.getName());
            slogan.setText(radio.getSloga());
    }

    public void onFavorito(View view) {
        Data.putFavorito(radio.getUrlStreaming(), btn_favorito.isChecked(), this);
        if(btn_favorito.isChecked()){
            Snackbar.make(view, getString(R.string.add_favorito), Snackbar.LENGTH_LONG).show();
        }else{
            Snackbar.make(view, getString(R.string.removed_favorito), Snackbar.LENGTH_LONG).show();
        }
    }

    public void onNext(View view) {
        if(position == -1){
            getPosition();
        }

        int max = lista.size();
        position += 1;
        if(position > max - 1){
            position = 0;
        }
        radio = lista.get(position);
        radioManager.playOrPause(radio.getUrlStreaming());
        btn_favorito.setChecked(Data.isFavorito(radio.getUrlStreaming(), this));
    }

    public void onPrevious(View view) {
        try{
            if(position == -1){
                getPosition();
            }

            int max = lista.size();
            position -= 1;
            if(position < 0){
                position = max - 1;
            }
            radio = lista.get(position);
            radioManager.playOrPause(radio.getUrlStreaming());
            btn_favorito.setChecked(Data.isFavorito(radio.getUrlStreaming(), this));
        }catch (Exception x){
            message(x.getMessage());
        }
    }

    private int position = -1;
    private int getPosition(){
        for(int i =0; i<lista.size();i++){
            Radio radio = lista.get(i);
            if(radio.getUrlStreaming().equals(this.radio.getUrlStreaming())){
                position = i;
                break;
            }
        }
        return position;
    }

    public boolean isConnected() {
        try{
            NetworkInfo info = ((ConnectivityManager) Objects.requireNonNull(getSystemService(Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo();
            if(info == null){
                return false;
            }
            return info.isConnected();
        }catch (Exception x){
            return false;
        }
    }

    int y=0;
    public void onShare(View view) {
        CoordinatorLayout backgroundRadio = findViewById(R.id.backgroundRadio);
        y+=1;
        if(y==1){
            backgroundRadio.setBackgroundResource(R.drawable.background_2);
        }else if(y==2){
            backgroundRadio.setBackgroundResource(R.drawable.background_3);
        }else {
            backgroundRadio.setBackgroundResource(R.drawable.background_1);
            y=0;
        }

    }


}