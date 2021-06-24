package com.igordutrasanches.radioreggae.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.igordutrasanches.radioreggae.MainActivity;
import com.igordutrasanches.radioreggae.R;
import com.igordutrasanches.radioreggae.data.Data;
import com.igordutrasanches.radioreggae.model.Radio;
import com.igordutrasanches.radioreggae.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

public class Tools {

    public static void nextBackground(Activity activity, boolean isChaned){
        CoordinatorLayout bg = activity.findViewById(R.id.backgroundRadio);
        Window bar = activity.getWindow();

        int position = Data.getBackground(activity);
        if(isChaned){
            switch (position){
            case 0:
                position = 1;
                break;
            case 1:
                position = 2;
                break;
            case 2:
                position = 0;
                break;
        }
        Data.setBackground(position, activity);
        }
        if(position == 0){
            bg.setBackgroundResource(R.drawable.background_2);
            bar.setStatusBarColor(activity.getResources().getColor(R.color.colorStatuBar2));
            bar.setNavigationBarColor(activity.getResources().getColor(R.color.colorNavBar2));
        }else if(position==2){
            bg.setBackgroundResource(R.drawable.background_3);
            bar.setStatusBarColor(activity.getResources().getColor(R.color.colorStatuBar3));
            bar.setNavigationBarColor(activity.getResources().getColor(R.color.colorStatuBar3));
        }else{
            bg.setBackgroundResource(R.drawable.background_1);
            bar.setStatusBarColor(activity.getResources().getColor(R.color.colorStatuBar1));
            bar.setNavigationBarColor(activity.getResources().getColor(R.color.colorStatuBar1));

        }
    }

    public static float toFloatVolume(int volume){
        String string = "";
        if(volume==10){
            string = "1.0";
        }else{
                string = "0." + volume;
        }

        float v = Float.valueOf(string);

        return v;
    }

    public static int toIntVolume(Context context){
        String string = "";
        float volume = Data.getVolume(context);
        if(volume==1.0f){
            string = "10";
        }else{
            string = String.valueOf(volume).replace("0.", "");
        }

        return Integer.valueOf(string);
    }

    public static List<Radio> getRadios() {
        List<Radio> lista = new ArrayList<>();
        lista.add(new Radio("https://s34.maxcast.com.br:8130/live?1609861057225","Rádio Reggae10","O melhor do reggae você ouve aqui!", "https://img.radios.com.br/radio/xl/radio33913_1536081863.png"));
        lista.add(new Radio("http://hts04.kshost.com.br:9694/;?1609860911150","Expresso do Reggae","A melhor do reggae", "https://img.radios.com.br/radio/xl/radio73353_1530734633.jpg"));
        lista.add(new Radio("http://198.50.248.220:8024/live?1609861232431","Rádio Point do Reggae","Levando você até o coração da Jamaica!", "https://img.radios.com.br/radio/xl/radio71527_1535031536.png"));
        lista.add(new Radio("http://streaming29.hstbr.net:8034/live?1609860815335","Rádio Roots Reggae Rasta","O melhor do reggae você ouve aqui!", "https://img.radios.com.br/radio/xl/radio27729_1439401691.jpg"));

        return lista;
    }
}
