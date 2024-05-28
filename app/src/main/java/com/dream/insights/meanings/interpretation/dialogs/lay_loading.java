package com.dream.insights.meanings.interpretation.dialogs;

import static com.dream.insights.meanings.interpretation.util.Utils.AFFIRMATION;
import static com.dream.insights.meanings.interpretation.util.Utils.CALENDER;
import static com.dream.insights.meanings.interpretation.util.Utils.DIVINE;
import static com.dream.insights.meanings.interpretation.util.Utils.HEALING;
import static com.dream.insights.meanings.interpretation.util.Utils.INTERPRET;
import static com.dream.insights.meanings.interpretation.util.Utils.MAIN_ACTIVITY;
import static com.dream.insights.meanings.interpretation.util.Utils.PHYSCOLOGICAL;
import static com.dream.insights.meanings.interpretation.util.Utils.SUBCAT;
import static com.dream.insights.meanings.interpretation.util.Utils.WIKI;
import static com.dream.insights.meanings.interpretation.util.Utils.WIKI_FINAL;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dream.insights.meanings.interpretation.R;
import com.dream.insights.meanings.interpretation.util.Utils;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class lay_loading extends AppCompatActivity {
    boolean showAd;
    
    private static InterstitialAd mInterstitialAd;
    private TextView animatedTextView,loading;
    private String originalText;
    private String original = "Loading...";
    private int currentIndex = 0;
    private int currentIndex2 = 0;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Handler handler2 = new Handler(Looper.getMainLooper());
    private Handler handler3 = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lay_loading);
        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        animatedTextView = findViewById(R.id.tv_loading);
        loading = findViewById(R.id.loading);
        SharedPreferences sp = getSharedPreferences("BASE_APP",MODE_PRIVATE);
        originalText = sp.getString("CONTENT","");
        loadInterstitialAward();
        animateText();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) { // true: for prevent back and do something in handleOnBackPressed
            @Override
            public void handleOnBackPressed() {
                // Do Something
            }
        });
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
        SharedPreferences sp = getSharedPreferences("BASE_APP",MODE_PRIVATE);
        showAd = sp.getBoolean("SHOW_AD",true);
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
    public void loadInterstitialAward() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(lay_loading.this,getString(R.string.interstitial), adRequest,
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
                                Log.d("MOBILE_ADS", "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d("MOBILE_ADS", "Ad dismissed fullscreen content.");
                                mInterstitialAd = null;
                                dismiss();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.e("MOBILE_ADS", "Ad failed to show fullscreen content.");
                                mInterstitialAd = null;

                                dismiss();

                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d("MOBILE_ADS", "Ad recorded an impression.");

                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d("MOBILE_ADS", "Ad showed fullscreen content.");
                            }
                        });
                        Log.i("MOBILE_ADS", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d("MOBILE_ADS", loadAdError.toString());
                        mInterstitialAd = null;

                    }

                });


    }
    public void showInter(){
        if (mInterstitialAd != null) {
            mInterstitialAd.show(lay_loading.this);
        } else {
            dismiss();
            Log.d("MOBILE_ADS", "The interstitial ad wasn't ready yet.");
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(null);
        handler2.removeCallbacks(null);
        handler3.removeCallbacks(null);
    }

    private void dismiss(){
        SharedPreferences sp = getSharedPreferences("BASE_APP",MODE_PRIVATE);
        int nav = sp.getInt("NAVIGATION",0);
        Utils utils = new Utils(lay_loading.this);

        switch (nav){
            case MAIN_ACTIVITY:
                utils.navigateToMain();
                break;
            case AFFIRMATION:
                utils.navigateToAffirmation();
                break;
            case PHYSCOLOGICAL:
                utils.navigateToPhyscological();
                break;
            case SUBCAT:
                utils.navigateToSub();
                break;
            case INTERPRET:
                utils.navigateToInterpret();
                break;
            case HEALING:
                utils.navigateToHealing();
                break;
            case DIVINE:
                utils.navigateToDivinePreview();
                break;
            case CALENDER:
                utils.navigateToDreamCalender();
                break;
            case WIKI:
                utils.navigateToDreamWiki();
                break;
            case WIKI_FINAL:
                utils.navigateToDreamWikiInterpretation();
                break;
        }
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);


    }



}