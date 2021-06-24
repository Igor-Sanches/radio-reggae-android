package com.igordutrasanches.radioreggae.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.core.app.ActivityOptionsCompat;

public class Navigation {

    private Activity mActivity;
    private Context mContext;

    public Navigation(Context mContext){
        this.mContext = mContext;
    }

    public static Navigation context(Context mContext){
        return new Navigation(mContext);
    }

    public Navigation(Activity mActivity){
        this.mActivity = mActivity;
    }

    public static Navigation activity(Activity mActivity){
        return new Navigation(mActivity);
    }

    public void onNavigationWithAnimation(Class<?> classe, View view, String name){
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view, name);
        mActivity.startActivity(new Intent(mActivity, classe), activityOptionsCompat.toBundle());
    }

    public void onNavigation(Class<?> classe){
        if(mActivity == null){
            mContext.startActivity(new Intent(mContext, classe));
        }else{
            mActivity.startActivity(new Intent(mActivity, classe));
        }
    }

    public void onNavigationResult(Intent intent, int codeResult){
        try{
            mActivity.startActivityForResult(intent, codeResult);

        }catch (Exception x){

        }
    }

    public void onNavigationResult(Class<?> classe, int codeResult){
        try{
            mActivity.startActivityForResult(new Intent(mActivity, classe), codeResult);

        }catch (Exception x){

        }
    }

    public void onNavigation(Intent Intent){
        if(mActivity == null){
            mContext.startActivity(Intent);
        }else{
            mActivity.startActivity(Intent);
        }
    }

    public void onNavigationWithFinish(Class<?> classe){
        mActivity.startActivity(new Intent(mActivity, classe));
        mActivity.finish();
    }

}
