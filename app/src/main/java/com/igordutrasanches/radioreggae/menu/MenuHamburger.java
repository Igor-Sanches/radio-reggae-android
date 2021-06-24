package com.igordutrasanches.radioreggae.menu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.igordutrasanches.radioreggae.R;
import com.igordutrasanches.radioreggae.navigation.Navigation;

public class MenuHamburger {

    private Context mContext;

    public MenuHamburger(Context mContext){
        this.mContext = mContext;
    }

    public static MenuHamburger context(Context mContext){
        return new MenuHamburger(mContext);
    }

    public void onClickItem(String tag){
        Intent intent = null;
        switch (tag){
            case "key_sobre":
                View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_sobre_app, null);
                AlertDialog dialog = new AlertDialog.Builder(mContext).create();
                TextView version_app = view.findViewById(R.id.version_app);
                String version = mContext.getString(R.string.procurando);
                try {
                    version = mContext.getPackageManager().getPackageInfo((String)mContext.getPackageName(), (int) 0).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                version_app.setText(version);
                View.OnClickListener onNewClick = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickItem((String)view.getTag().toString());
                    }
                };
                ((Toolbar)view.findViewById(R.id.toolbar)).setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                });
                ((CardView)view.findViewById(R.id.key_share_app)).setOnClickListener(onNewClick);
                ((CardView)view.findViewById(R.id.key_contato)).setOnClickListener(onNewClick);
                ((CardView)view.findViewById(R.id.key_facebook)).setOnClickListener(onNewClick);
                ((CardView)view.findViewById(R.id.key_twitter)).setOnClickListener(onNewClick);

                dialog.setView(view);
                dialog.show();

                break;
             case "key_share_app":
                intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.SUBJECT", "");
                intent.putExtra("android.intent.extra.TEXT", getTextShare());
                Navigation.context(mContext).onNavigation(Intent.createChooser(intent, "Boletim de Anajatuba"));
                break;
            case "key_rate":
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+mContext.getPackageName()));
                Navigation.context(mContext).onNavigation(intent);
                break;
            case "key_facebook":
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/igor.dutra.3557"));
                Navigation.context(mContext).onNavigation(intent);
                break;
            case "key_twitter":
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/igordutra2014"));
                Navigation.context(mContext).onNavigation(intent);
                break;
            case "key_contato":
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=5598985766514"));
                Navigation.context(mContext).onNavigation(intent);
                break;
            case "key_mais_apps":
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=pub%3AIgor%20Dutra%20Sanches&c=apps"));
                Navigation.context(mContext).onNavigation(intent);
                break;
        }
    }

    private String getTextShare() {
        String text = "Venha ouvir o melhorer do reggae, experimente!\nBaixe de graça agora mesmo.\n\n";
        return text + "https://play.google.com/store/apps/details?id="+mContext.getPackageName();
    }

}
