package com.dreamrevealer.meanings.interpretation.journaldictionary;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Utils {
    Context context;

    public  Utils(Context ct){
        context = ct;
    }

    public void privacy_Policy(){
        intentToLink("https://sites.google.com/view/dream-revealer-privacy-policy/privacy-policy");
    }
    public void tos(){
        intentToLink("https://sites.google.com/view/dream-revealer-privacy-policy/terms-of-service");
    }

    private void intentToLink(String url){
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

    }

}
