package com.meanings.interpretation.journaldictionary.dreamrevealer.Activities;

import static com.meanings.interpretation.journaldictionary.dreamrevealer.util.Utils.AFFIRMATION;
import static com.meanings.interpretation.journaldictionary.dreamrevealer.util.Utils.CALENDER;
import static com.meanings.interpretation.journaldictionary.dreamrevealer.util.Utils.DIVINE;
import static com.meanings.interpretation.journaldictionary.dreamrevealer.util.Utils.HEALING;
import static com.meanings.interpretation.journaldictionary.dreamrevealer.util.Utils.PHYSCOLOGICAL;
import static com.meanings.interpretation.journaldictionary.dreamrevealer.util.Utils.WIKI;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.meanings.interpretation.journaldictionary.dreamrevealer.dialogs.CustomDialog;
import com.meanings.interpretation.journaldictionary.dreamrevealer.ads.MobileAds;
import com.meanings.interpretation.journaldictionary.dreamrevealer.R;
import com.meanings.interpretation.journaldictionary.dreamrevealer.util.Utils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

public class MainActivity extends AppCompatActivity {

    CardView cv1,cv2,cv3,cv4,askDream,cv5,cv6,cv7,cv8;
    ImageView menu;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Utils utils;
    private static final String ONESIGNAL_APP_ID = "17b6fd0b-0c8c-4ffd-ad70-862b0652ea76";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.menu);
        cv1 = findViewById(R.id.cv1);
        cv2 = findViewById(R.id.cv2);
        cv3 = findViewById(R.id.cv3);
        cv4 = findViewById(R.id.cv4);
        cv5 = findViewById(R.id.cv5);
        cv6 = findViewById(R.id.cv6);
        cv7 = findViewById(R.id.cv7);
        cv8 = findViewById(R.id.cv8);
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
        configureOnesignal();

        cv4.setOnClickListener(view -> {
            editor.putString("CONTENT","FACT OF THE DAY"+"!!");
            editor.putInt("NAVIGATION",PHYSCOLOGICAL);
            editor.putBoolean("SHOW_AD",true);
            editor.apply();
            utils.navigateToLoading();

        });

        cv5.setOnClickListener(view -> {
            editor.putString("CONTENT","HEALING SOUNDS"+"!!");
            editor.putInt("NAVIGATION",HEALING);
            editor.putBoolean("SHOW_AD",true);
            editor.apply();
            utils.navigateToLoading();

        });

        cv6.setOnClickListener(view -> {
            editor.putString("CONTENT","DIVINE PRAYERS"+"!!");
            editor.putInt("NAVIGATION",DIVINE);
            editor.putBoolean("SHOW_AD",true);
            editor.apply();
            utils.navigateToLoading();

        });

        cv7.setOnClickListener(view -> {
            editor.putString("CONTENT","DREAM WIKI"+"!!");
            editor.putInt("NAVIGATION",WIKI);
            editor.putBoolean("SHOW_AD",true);
            editor.apply();
            utils.navigateToLoading();

        });

        cv8.setOnClickListener(view -> {
            editor.putString("CONTENT","DREAM CALENDER"+"!!");
            editor.putInt("NAVIGATION",CALENDER);
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


        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.exit);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                CardView dialogButton = dialog.findViewById(R.id.ok);
                CardView no =  dialog.findViewById(R.id.cancel);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishAffinity();
                        finish();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }
    private void configureOnesignal(){
        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);

        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        OneSignal.getNotifications().requestPermission(true, Continue.with(r -> {
            if (r.isSuccess()) {
                if (r.getData()) {
                    // `requestPermission` completed successfully and the user has accepted permission
                }
                else {
                    // `requestPermission` completed successfully but the user has rejected permission
                }
            }
            else {
                // `requestPermission` completed unsuccessfully, check `r.getThrowable()` for more info on the failure reason
            }
        }));
    }



}