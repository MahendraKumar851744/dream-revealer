package com.meanings.interpretation.journaldictionary.dreamrevealer.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.meanings.interpretation.journaldictionary.dreamrevealer.R;
import com.meanings.interpretation.journaldictionary.dreamrevealer.listeners.OnFInishListner;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MobileAds {

    Context context;
    OnFInishListner onFInishListner;
    private static InterstitialAd mInterstitialAd;
    public MobileAds(Context context){

        this.context = context;
    }



    public void loadInterstitialAward() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context,context.getString(R.string.interstitial), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {
                                Log.d("MOBILE_ADS", "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Log.d("MOBILE_ADS", "Ad dismissed fullscreen content.");
                                mInterstitialAd = null;
                                loadInterstitialAward();
                                onFInishListner.onfinish();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                Log.e("MOBILE_ADS", "Ad failed to show fullscreen content.");
                                mInterstitialAd = null;
                                onFInishListner.onfinish();
                            }

                            @Override
                            public void onAdImpression() {
                                Log.d("MOBILE_ADS", "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                Log.d("MOBILE_ADS", "Ad showed fullscreen content.");
                            }
                        });
                        Log.i("MOBILE_ADS", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.d("MOBILE_ADS", loadAdError.toString());
                        mInterstitialAd = null;
                        onFInishListner.onfinish();
                    }

                });

    }
    public void showInter(){
        if (mInterstitialAd != null) {
            mInterstitialAd.show((Activity) context);
        } else {
            onFInishListner.onfinish();
            loadInterstitialAward();
            Log.d("MOBILE_ADS", "The interstitial ad wasn't ready yet.");
        }
    }
    public void loadBannerAd(AdView mAdView){
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdClosed() {

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
            }

            @Override
            public void onAdImpression() {

            }

            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdOpened() {

            }
        });
        mAdView.loadAd(adRequest);
    }
}
