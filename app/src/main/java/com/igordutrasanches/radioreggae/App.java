package com.igordutrasanches.radioreggae;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.google.firebase.FirebaseApp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class App extends Application {

    private static DisplayImageOptions displayImageOptions;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        context = getApplicationContext();
        displayImageOptions = new DisplayImageOptions.Builder().imageScaleType(ImageScaleType.NONE_SAFE).resetViewBeforeLoading(true).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();

    }

    public static DisplayImageOptions getAppDisplayOptions() {
        return displayImageOptions;
    }

    public static Context getAppContext() {
        return context;
    }
}
