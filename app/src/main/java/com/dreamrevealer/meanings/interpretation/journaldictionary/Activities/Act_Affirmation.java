package com.dreamrevealer.meanings.interpretation.journaldictionary.Activities;

import static com.dreamrevealer.meanings.interpretation.journaldictionary.Utils.AFFIRMATION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamrevealer.meanings.interpretation.journaldictionary.LoadingDialog;
import com.dreamrevealer.meanings.interpretation.journaldictionary.MobileAds;
import com.dreamrevealer.meanings.interpretation.journaldictionary.R;
import com.dreamrevealer.meanings.interpretation.journaldictionary.RatingDialog;
import com.dreamrevealer.meanings.interpretation.journaldictionary.ScratchViewr;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Act_Affirmation extends AppCompatActivity {

    TextView content,date;
    ScratchViewr scratchView;
    TextToSpeech t1;
    ImageView iv_back;
    ImageView speaker,share,like;
    RelativeLayout ss;

    private Handler handler;
    private static final int SPEAKING_CHECK_DELAY = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_affirmation);
        iv_back = findViewById(R.id.iv_back);
        content = findViewById(R.id.content);
        share = findViewById(R.id.share);
        speaker = findViewById(R.id.speaker);
        like = findViewById(R.id.like);
        date = findViewById(R.id.date);
        ss = findViewById(R.id.ss);
        scratchView = findViewById(R.id.scratch_view);
        scratchView.setStrokeWidth(6);
        SharedPreferences sp = getSharedPreferences("BASE_APP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String affirmation = sp.getString("affirmation","");
        content.setText(affirmation);

        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMMM, yyyy", Locale.US);
        String formattedDate = sdf.format(currentDate);
        date.setText(formattedDate);


        MobileAds mobileAds = new MobileAds(this);
        mobileAds.loadBannerAd(findViewById(R.id.adView3));

        handler = new Handler();
        t1 = new TextToSpeech(Act_Affirmation.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int result = t1.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        // Text-to-Speech is initialized successfully
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });


        if(sp.getString("updated_affir","-1").equalsIgnoreCase(affirmation)){
            scratchView.setVisibility(View.GONE);
            share.setVisibility(View.VISIBLE);
            like.setVisibility(View.VISIBLE);
            speaker.setVisibility(View.VISIBLE);
        }
        scratchView.setRevealListener(new ScratchViewr.IRevealListener() {
            @Override
            public void onRevealed(ScratchViewr scratchView) {
                editor.putString("updated_affir",affirmation);
                editor.apply();
                share.setVisibility(View.VISIBLE);
                like.setVisibility(View.VISIBLE);
                speaker.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRevealPercentChangedListener(ScratchViewr scratchView, float percent) {
                if(percent>0.4){
                    scratchView.reveal();
                }
            }

        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.speak(affirmation, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!t1.isSpeaking()) {
                            startTextToSpeech(affirmation);
                        }
                    }
                }, SPEAKING_CHECK_DELAY);
            }
        });

        share.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Today's Affirmation:\n" + affirmation);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });


        like.setOnClickListener(view -> {
            RatingDialog customDialog = new RatingDialog(Act_Affirmation.this);
            customDialog.show();
        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
    public void onPause(){

        super.onPause();
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        handler.removeCallbacks(null);
    }
    private void startTextToSpeech(String affirmation) {
        t1 = new TextToSpeech(Act_Affirmation.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    t1.setLanguage(Locale.US);
                    // Start speaking
                    t1.speak(affirmation, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);
                }
            }
        });
    }
}