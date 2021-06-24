package com.igordutrasanches.radioreggae.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.igordutrasanches.radioreggae.MainActivity;

public class Data {
    public static String PREFERENCE_NAME = "Igor_Sanches";
    private static final String PREFERENCE_FILE_NAME = "Picadas_db";
    private static SharedPreferences mSharedPreferences;

    private static SharedPreferences getmSharedPreferencesEditor(Context context){
        if(mSharedPreferences ==null){
            mSharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);

        }
        return mSharedPreferences;
    }

    public static void setPositionPlayList(int value, Context context) {
        SharedPreferences.Editor editor = getmSharedPreferencesEditor(context).edit();
        editor.putInt("PositionList", value);
        editor.commit();
    }

    public static int getPositionPlayList(Context context){
        return getmSharedPreferencesEditor(context).getInt("PositionList", 0);
    }

    public static int getSorting(Context context){
        return getmSharedPreferencesEditor(context).getInt("Sorting", 0);
    }

    public static void setSorting(int value, Context context) {
        SharedPreferences.Editor editor = getmSharedPreferencesEditor(context).edit();
        editor.putInt("Sorting", value);
        editor.commit();
    }

    public static float getVolume(Context context){
        return getmSharedPreferencesEditor(context).getFloat("volume", 0.8f);
    }

    public static float getVolumeOld(Context context){
        return getmSharedPreferencesEditor(context).getFloat("volumeOld", 0.8f);
    }

    public static void setVolumeOld(float volume, Context context) {
        SharedPreferences.Editor editor = getmSharedPreferencesEditor(context).edit();
        editor.putFloat("volumeOld", volume);
        editor.commit();
    }


    public static void setVolume(float volume, Context context) {
        SharedPreferences.Editor editor = getmSharedPreferencesEditor(context).edit();
        editor.putFloat("volume", volume);
        editor.commit();
    }

    public static void putFavorito(String key, boolean isFavorito, Context context) {
        SharedPreferences.Editor editor = getmSharedPreferencesEditor(context).edit();
        editor.putBoolean(key, isFavorito);
        editor.commit();
    }


    public static boolean isFavorito(String url, Context context) {
        return getmSharedPreferencesEditor(context).getBoolean(url, false);
    }

    public static int getBackground(Context context) {
        return getmSharedPreferencesEditor(context).getInt("background", 1);
    }


    public static void setBackground(int value, Context context) {
        SharedPreferences.Editor editor = getmSharedPreferencesEditor(context).edit();
        editor.putInt("background", value);
        editor.commit();
    }
}
