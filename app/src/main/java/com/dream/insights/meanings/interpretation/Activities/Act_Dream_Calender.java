package com.dream.insights.meanings.interpretation.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.insights.meanings.interpretation.Databases.dream_calender.Dream_Calender;
import com.dream.insights.meanings.interpretation.Databases.dream_calender.cal_database;
import com.dream.insights.meanings.interpretation.R;
import com.dream.insights.meanings.interpretation.ads.MobileAds;
import com.dream.insights.meanings.interpretation.dialogs.RatingDialog;
import com.dream.insights.meanings.interpretation.util.SwipeGestureListener;
import com.dream.insights.meanings.interpretation.util.Utils;

import java.util.ArrayList;
import java.util.Locale;

public class Act_Dream_Calender extends AppCompatActivity {
    TextView content,date;
    TextToSpeech t1;
    ImageView iv_back;
    ImageView speaker,share,like;

    private Handler handler;
    private static final int SPEAKING_CHECK_DELAY = 500;

    static String affirmation = "";
    private GestureDetector gestureDetector;

    private static int position = 0;
    ArrayList<Dream_Calender> contents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dream_calender);
        iv_back = findViewById(R.id.iv_back);
        content = findViewById(R.id.content);
        share = findViewById(R.id.share);
        speaker = findViewById(R.id.speaker);
        like = findViewById(R.id.like);
        date = findViewById(R.id.date);

        cal_database db = cal_database.getDbInstance(this);
        contents = (ArrayList<Dream_Calender>) db.dao().getAllCalender();


//        MobileAds mobileAds = new MobileAds(this);
//        mobileAds.loadBannerAd(findViewById(R.id.adView3));
        new Utils(this).setBannerAd(findViewById(R.id.bannerplacer),24);


        handler = new Handler();
        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
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
        t1.setSpeechRate(0.8f);


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
            RatingDialog customDialog = new RatingDialog(this);
            customDialog.show();
        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        SwipeGestureListener gestureListener = new SwipeGestureListener() {
            @Override
            public void onSwipeRight() {
                if(position!=0){
                    position -= 1;
                    updateUi();
                }

            }

            @Override
            public void onSwipeLeft() {
                position += 1;
                updateUi();
            }
        };
        gestureDetector = new GestureDetector(this, gestureListener);

        updateUi();
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
        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
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

    private void updateUi() {
        int size = contents.size();
        if (size > 0) {
            position %= size; // Ensure position is within a valid range
            affirmation = contents.get(position).getContent();
            content.setText(affirmation);
            date.setText((position + 1) + " Night");
        } else {
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}