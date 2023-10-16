package com.dreamrevealer.meanings.interpretation.journaldictionary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class LoadingDialog extends Dialog {
    String content;
    boolean showAd;

    public LoadingDialog(@NonNull Context context, int themeResId,String content,boolean showAd) {
        super(context, themeResId);
        this.content = content;
        this.showAd = showAd;
    }
    private static InterstitialAd mInterstitialAd;
    private TextView animatedTextView,loading;
    private String originalText = content;
    private String original = "Loading...";
    private int currentIndex = 0;
    private int currentIndex2 = 0;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Handler handler2 = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lay_loading);
        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        animatedTextView = findViewById(R.id.tv_loading);
        loading = findViewById(R.id.loading);
        originalText = content;
        new com.dreamrevealer.meanings.interpretation.journaldictionary.MobileAds(getContext()).loadInterstitialAward();
        loadInterstitialAward();
        animateText();
        setCancelable(false);
    }

    private void animateText() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentIndex <= originalText.length()) {
                    animatedTextView.setText(originalText.substring(0, currentIndex));
                    currentIndex++;
                    handler.postDelayed(this, 150);
                } else {
                    currentIndex2 = 0;
                    animateText2();
                }
            }
        }, 500);
    }

    private void animateText2() {
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentIndex2 <= original.length()) {
                    loading.setText(original.substring(0, currentIndex2));
                    currentIndex2++;
                    handler2.postDelayed(this, 150);
                }else{
                    if(showAd){
                        showInter();
                    }else{
                        dismiss();
                    }


                }
            }
        }, 500);
    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(null);
    }


    public void loadInterstitialAward() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(getContext(),"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d("TAG", "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d("TAG", "Ad dismissed fullscreen content.");
                                mInterstitialAd = null;
                                dismiss();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.e("TAG", "Ad failed to show fullscreen content.");
                                mInterstitialAd = null;
                                dismiss();
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d("TAG", "Ad recorded an impression.");
                                dismiss();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d("TAG", "Ad showed fullscreen content.");
                            }
                        });
                        Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d("TAG", loadAdError.toString());
                        mInterstitialAd = null;

                    }

                });


    }
    public void showInter(){
        if (mInterstitialAd != null) {
            mInterstitialAd.show((Activity) getOwnerActivity());
        } else {
            dismiss();
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }


}
