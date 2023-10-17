package com.dreamrevealer.meanings.interpretation.journaldictionary.Activities;

import static com.dreamrevealer.meanings.interpretation.journaldictionary.Utils.AFFIRMATION;
import static com.dreamrevealer.meanings.interpretation.journaldictionary.Utils.MAIN_ACTIVITY;
import static com.dreamrevealer.meanings.interpretation.journaldictionary.Utils.PHYSCOLOGICAL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.dreamrevealer.meanings.interpretation.journaldictionary.CustomDialog;
import com.dreamrevealer.meanings.interpretation.journaldictionary.MobileAds;
import com.dreamrevealer.meanings.interpretation.journaldictionary.R;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Utils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;

public class MainActivity extends AppCompatActivity {

    CardView cv1,cv2,cv3,cv4,askDream;
    ImageView menu;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Utils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.menu);
        cv1 = findViewById(R.id.cv1);
        cv2 = findViewById(R.id.cv2);
        cv3 = findViewById(R.id.cv3);
        cv4 = findViewById(R.id.cv4);
        askDream = findViewById(R.id.askDream);
        utils = new Utils(this);
        sp = getSharedPreferences("BASE_APP",MODE_PRIVATE);
        editor = sp.edit();
        cv1.setOnClickListener(view -> {
            Intent i = new Intent(this,Act_Dream_Meanings.class);
            startActivity(i);

        });
        askDream.setOnClickListener(view -> {
            Intent i = new Intent(this,Ask_Dream.class);
            startActivity(i);

        });
        cv2.setOnClickListener(view -> {
            Intent i = new Intent(this, Act_Dream_Book.class);
            startActivity(i);

        });

        cv3.setOnClickListener(view -> {
            editor.putString("CONTENT","TODAY'S AFFIRMATION"+"!!");
            editor.putInt("NAVIGATION",AFFIRMATION);
            editor.putBoolean("SHOW_AD",true);
            editor.apply();
            utils.navigateToLoading();

        });

        cv4.setOnClickListener(view -> {
            editor.putString("CONTENT","FACT OF THE DAY"+"!!");
            editor.putInt("NAVIGATION",PHYSCOLOGICAL);
            editor.putBoolean("SHOW_AD",true);
            editor.apply();
            utils.navigateToLoading();

        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(MainActivity.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                customDialog.show();
            }
        });

        editor.putBoolean("Intro",true);
        editor.apply();

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        MobileAds mobileAds = new MobileAds(this);
        mobileAds.loadBannerAd(findViewById(R.id.adView3));

        AdLoader adLoader = new AdLoader.Builder(this, getString(R.string.native_banner))
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        FrameLayout frameLayout =
                                findViewById(R.id.fl_adplaceholder);
                        NativeAdView adView = (NativeAdView) getLayoutInflater()
                                .inflate(R.layout.native_ad_layout, null);
                        TextView headlineView = adView.findViewById(R.id.ad_headline);
                        headlineView.setText(nativeAd.getHeadline());
                        adView.setHeadlineView(headlineView);

                        TextView body = adView.findViewById(R.id.description);
                        body.setText(nativeAd.getBody());
                        adView.setBodyView(body);

                        ImageView iv1 = adView.findViewById(R.id.iv1);
                        iv1.setImageDrawable(nativeAd.getIcon().getDrawable());


                        CardView cta = adView.findViewById(R.id.cta);

                        adView.setCallToActionView(cta);

                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        .build())
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());



    }

}