package com.dreamrevealer.meanings.interpretation.journaldictionary.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.dreamrevealer.meanings.interpretation.journaldictionary.Activities.Act_Affirmation;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Activities.Act_Dream_Interpretation;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Activities.Act_Dream_Meanings_subcategories;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Activities.Act_Healing_Sound;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Activities.Act_Physcological;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Activities.MainActivity;
import com.dreamrevealer.meanings.interpretation.journaldictionary.dialogs.lay_loading;

public class Utils {
    Context context;

    public static final int MAIN_ACTIVITY = 0;
    public static final int AFFIRMATION = 1;
    public static final int PHYSCOLOGICAL = 2;
    public static final int SUBCAT = 3;
    public static final int INTERPRET = 4;
    public static final int HEALING = 5;


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

    public void navigateToLoading(){
        Intent i = new Intent(context, lay_loading.class);
        context.startActivity(i);
    }


    public void navigateToMain() {
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
    }
    public void navigateToAffirmation() {
        Intent i = new Intent(context, Act_Affirmation.class);
        context.startActivity(i);
    }

    public void navigateToPhyscological() {
        Intent i = new Intent(context, Act_Physcological.class);
        context.startActivity(i);
    }

    public void navigateToSub() {
        Intent i = new Intent(context, Act_Dream_Meanings_subcategories.class);
        context.startActivity(i);
    }

    public void navigateToInterpret() {
        Intent i = new Intent(context, Act_Dream_Interpretation.class);
        context.startActivity(i);
    }

    public void navigateToHealing() {
        Intent i = new Intent(context, Act_Healing_Sound.class);
        context.startActivity(i);
    }
}
