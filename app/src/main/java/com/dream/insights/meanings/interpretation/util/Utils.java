package com.dream.insights.meanings.interpretation.util;

import static com.dream.insights.meanings.interpretation.util.Constant.BASE_URL;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dream.insights.meanings.interpretation.Activities.Act_Affirmation;
import com.dream.insights.meanings.interpretation.Activities.Act_Divine_Prayers_Preview;
import com.dream.insights.meanings.interpretation.Activities.Act_Dream_Calender;
import com.dream.insights.meanings.interpretation.Activities.Act_Dream_Interpretation;
import com.dream.insights.meanings.interpretation.Activities.Act_Dream_Meanings_subcategories;
import com.dream.insights.meanings.interpretation.Activities.Act_Dream_Wiki_Interpretation;
import com.dream.insights.meanings.interpretation.Activities.Act_Healing_Sound;
import com.dream.insights.meanings.interpretation.Activities.Act_Physcological;
import com.dream.insights.meanings.interpretation.Activities.Act_Wiki_List;
import com.dream.insights.meanings.interpretation.Activities.MainActivity;
import com.dream.insights.meanings.interpretation.Databases.ads.Ads;
import com.dream.insights.meanings.interpretation.Databases.ads.ads_database;
import com.dream.insights.meanings.interpretation.R;
import com.dream.insights.meanings.interpretation.dialogs.lay_loading;

public class Utils {
    Context context;

    public static final int MAIN_ACTIVITY = 0;
    public static final int AFFIRMATION = 1;
    public static final int PHYSCOLOGICAL = 2;
    public static final int SUBCAT = 3;
    public static final int INTERPRET = 4;
    public static final int HEALING = 5;
    public static final int DIVINE = 6;
    public static final int CALENDER = 8;
    public static final int WIKI = 7;
    public static final int WIKI_FINAL = 9;


    public  Utils(Context ct){
        context = ct;
    }

    public void privacy_Policy(){
        intentToLink("https://sites.google.com/view/dream-revealer-privacy-policy/privacy-policy");
    }
    public void tos(){
        intentToLink("https://sites.google.com/view/dream-revealer-privacy-policy/terms-of-service");
    }

    public void intentToLink(String url){
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

    public void navigateToDivinePreview() {
        Intent i = new Intent(context, Act_Divine_Prayers_Preview.class);
        context.startActivity(i);
    }

    public void navigateToDreamWiki() {
        Intent i = new Intent(context, Act_Wiki_List.class);
        context.startActivity(i);
    }

    public void navigateToDreamCalender() {
        Intent i = new Intent(context, Act_Dream_Calender.class);
        context.startActivity(i);
    }

    public void navigateToDreamWikiInterpretation() {
        Intent i = new Intent(context, Act_Dream_Wiki_Interpretation.class);
        context.startActivity(i);
    }


    public void setBannerAd(FrameLayout fl,int id){
        ads_database db = ads_database.getDbInstance(context);
        Ads ad = db.dao().getAd(id);
        View v = getAdlayout(BASE_URL+ad.getImage(),ad.getLink(),fl);
        fl.addView(v);
    }
    public View getAdlayout(String imageurl, String redirectUrl, FrameLayout parent){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_bannerad,parent,false);
        Glide.with(context).load(imageurl).into((ImageView) view.findViewById(R.id.banner_img));
        view.setOnClickListener(v->{
            intentToLink(redirectUrl);
        });
        return view;
    }
}
