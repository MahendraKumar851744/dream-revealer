package com.dreamrevealer.meanings.interpretation.journaldictionary.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamrevealer.meanings.interpretation.journaldictionary.ads.MobileAds;
import com.dreamrevealer.meanings.interpretation.journaldictionary.R;
import com.dreamrevealer.meanings.interpretation.journaldictionary.dialogs.RatingDialog;
import com.dreamrevealer.meanings.interpretation.journaldictionary.util.ScratchViewr;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Act_Physcological extends AppCompatActivity {
    TextView content,date;
    ScratchViewr scratchView;
    TextToSpeech t1;
    ImageView iv_back;
    ImageView speaker,share,like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_physcological);
        iv_back = findViewById(R.id.iv_back);
        content = findViewById(R.id.content);
        share = findViewById(R.id.share);
        speaker = findViewById(R.id.speaker);
        like = findViewById(R.id.like);
        date = findViewById(R.id.date);
        scratchView = findViewById(R.id.scratch_view);
        scratchView.setStrokeWidth(6);
        SharedPreferences sp = getSharedPreferences("BASE_APP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String affirmation = sp.getString("physcological","");
        content.setText(affirmation);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

         Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMMM, yyyy", Locale.US);
        String formattedDate = sdf.format(currentDate);
        date.setText(formattedDate);
        MobileAds mobileAds = new MobileAds(this);
        mobileAds.loadBannerAd(findViewById(R.id.adView3));

        t1 = new TextToSpeech(Act_Physcological.this, new TextToSpeech.OnInitListener() {
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


        if(sp.getString("updated_phys","-1").equalsIgnoreCase(affirmation)){
            scratchView.setVisibility(View.GONE);
            share.setVisibility(View.VISIBLE);
            like.setVisibility(View.VISIBLE);
            speaker.setVisibility(View.VISIBLE);
        }
        scratchView.setRevealListener(new ScratchViewr.IRevealListener() {
            @Override
            public void onRevealed(ScratchViewr scratchView) {
                editor.putString("updated_phys",affirmation);
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
                t1.speak(affirmation, TextToSpeech.QUEUE_FLUSH, null);
                if(!t1.isSpeaking()) {
                    t1 = new TextToSpeech(Act_Physcological.this, new TextToSpeech.OnInitListener() {
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
                }
            }
        });

        like.setOnClickListener(view -> {
            RatingDialog customDialog = new RatingDialog(Act_Physcological.this);
            customDialog.show();
        });

        share.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Fact of the day:\n" + affirmation);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });
    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}