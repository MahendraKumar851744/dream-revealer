package com.meanings.interpretation.journaldictionary.dreamrevealer.Activities;

import static com.meanings.interpretation.journaldictionary.dreamrevealer.util.Constant.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meanings.interpretation.journaldictionary.dreamrevealer.R;
import com.meanings.interpretation.journaldictionary.dreamrevealer.ads.MobileAds;

import java.util.Locale;

public class Act_Dream_Wiki_Interpretation extends AppCompatActivity implements TextToSpeech.OnInitListener{

    ImageView iv,iv_back,speaker;
    TextView title1,title2,pm,eo;
    TextToSpeech t1;
    private Handler handler;
    private static final int SPEAKING_CHECK_DELAY = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dream_wiki_interpretation);
        iv = findViewById(R.id.iv);
        iv_back = findViewById(R.id.iv_back);
        title1 = findViewById(R.id.title1);
        title2 = findViewById(R.id.title2);
        speaker = findViewById(R.id.speaker);
        pm = findViewById(R.id.pm);
        eo = findViewById(R.id.eo);

        SharedPreferences sp = getSharedPreferences("BASE_APP",MODE_PRIVATE);
        String title = sp.getString("WIKI_TITLE","");
        String image = sp.getString("WIKI_IMAGE","");
        String direction = sp.getString("WIKI_DIRECTION","");
        String pm_ = sp.getString("WIKI_PM","");


        title1.setText(title);
        title2.setText(title);
        pm.setText(pm_);
        eo.setText(direction);
        Glide.with(this).load(BASE_URL +  image).into(iv);

        t1 = new TextToSpeech(this, this);
        t1.setSpeechRate(0.8f);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        handler = new Handler();
        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String content = "Possible Meaning."+pm.getText().toString()+"Direction."+eo.getText().toString();
                t1.speak(content, TextToSpeech.QUEUE_FLUSH, null,null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!t1.isSpeaking()) {
                            startTextToSpeech(content);
                        }
                    }
                }, SPEAKING_CHECK_DELAY);


            }
        });

        MobileAds mobileAds = new MobileAds(this);
        mobileAds.loadBannerAd(findViewById(R.id.adView3));

        iv_back.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
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

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = t1.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            }

        } else {
        }
    }

    private void startTextToSpeech(String content) {
        t1 = new TextToSpeech(Act_Dream_Wiki_Interpretation.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    t1.setLanguage(Locale.US);
                    t1.speak(content, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);
                }
            }
        });
    }
}