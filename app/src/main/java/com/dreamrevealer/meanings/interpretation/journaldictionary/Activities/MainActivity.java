package com.dreamrevealer.meanings.interpretation.journaldictionary.Activities;

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

import com.anjlab.android.iab.v3.BillingProcessor;
import com.dreamrevealer.meanings.interpretation.journaldictionary.CustomDialog;
import com.dreamrevealer.meanings.interpretation.journaldictionary.LoadingDialog;
import com.dreamrevealer.meanings.interpretation.journaldictionary.MobileAds;
import com.dreamrevealer.meanings.interpretation.journaldictionary.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;

public class MainActivity extends AppCompatActivity {

    BillingProcessor bp;
    CardView cv1,cv2,cv3,cv4,askDream;
    ImageView menu;


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
            Intent i = new Intent(this, Act_Affirmation.class);
            startActivity(i);

        });

        cv4.setOnClickListener(view -> {
            Intent i = new Intent(this, Act_Physcological.class);
            startActivity(i);

        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(MainActivity.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                customDialog.show();
            }
        });

        LoadingDialog customDialog = new LoadingDialog(MainActivity.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen,"UNCOVER HIDDEN DREAM MEANINGS!!",true);
        customDialog.show();

        SharedPreferences sp = getSharedPreferences("BASE_APP",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("Intro",true);
        editor.apply();

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        MobileAds mobileAds = new MobileAds(this);
        mobileAds.loadBannerAd(findViewById(R.id.adView3));




        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        FrameLayout frameLayout =
                                findViewById(R.id.fl_adplaceholder);
                        // Assumes that your ad layout is in a file call native_ad_layout.xml
                        // in the res/layout folder
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
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());



    }

}