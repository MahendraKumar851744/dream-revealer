package com.meanings.interpretation.journaldictionary.dreamrevealer.Activities;

import static com.meanings.interpretation.journaldictionary.dreamrevealer.util.Constant.BASE_URL;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.meanings.interpretation.journaldictionary.dreamrevealer.Databases.healing.Healing;
import com.meanings.interpretation.journaldictionary.dreamrevealer.MyMediaPlayer;
import com.meanings.interpretation.journaldictionary.dreamrevealer.R;
import com.meanings.interpretation.journaldictionary.dreamrevealer.util.SwipeGestureListener;
import com.meanings.interpretation.journaldictionary.dreamrevealer.viewModel.HealingViewModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Act_Final_Healing extends AppCompatActivity {

    private TextView currentTimeTv;
    private TextView totalTimeTv;
    SeekBar seekBar;
    ImageView pausePlay,nextBtn,previousBtn,iv_back;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
    int x=0;

    ArrayList<Healing> songsList;

    ImageView backg,loop,iv1;

    private Handler handler = new Handler();
    private Runnable updateSeekBar;
    private GestureDetector gestureDetector;

    static int play_back = 0;
    static int player_loop = 0;
    TextView heading;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_final_healing);

        seekBar = findViewById(R.id.seekBar);
        currentTimeTv = findViewById(R.id.textViewStartTime);
        totalTimeTv = findViewById(R.id.textViewEndTime);
        pausePlay = findViewById(R.id.play_pause);
        previousBtn = findViewById(R.id.prev);
        nextBtn = findViewById(R.id.next);
        iv_back = findViewById(R.id.iv_back);
        backg = findViewById(R.id.backg);
        loop = findViewById(R.id.loop);
        heading = findViewById(R.id.heading);
        iv1 = findViewById(R.id.iv1);


        backg.setOnClickListener(view -> {
            if(play_back==0){
                play_back = 1;
                backg.setImageDrawable(getDrawable(R.drawable.group_3077));
            }else{
                play_back = 0;
                backg.setImageDrawable(getDrawable(R.drawable.group_3078));
            }
        });

        loop.setOnClickListener(view -> {
            if(player_loop==0){
                player_loop = 1;
                mediaPlayer.setLooping(true);
                loop.setImageDrawable(getDrawable(R.drawable.group_3077));
            }else{
                mediaPlayer.setLooping(false);

                player_loop = 0;
                loop.setImageDrawable(getDrawable(R.drawable.group_3078));
            }
        });

        if (mediaPlayer.isPlaying()) {
            MyMediaPlayer.release();
            mediaPlayer = MyMediaPlayer.getInstance();
        }

        mediaPlayer.reset();

        SharedPreferences sp = getSharedPreferences("BASE_APP",MODE_PRIVATE);
        String fileName = sp.getString("HEALING_PATH","");
        String titl = sp.getString("HEALING_TITLE","");
        String img = sp.getString("HEALING_IMAGE","");
        Glide.with(this).load(img).into(iv1);
        heading.setText(titl);

        MyMediaPlayer.currentIndex  = sp.getInt("HEALING_ID",0);

        File audioFile = new File(getFilesDir(), fileName);
        if (!TextUtils.isEmpty(fileName)) {
            if (audioFile.exists()) {
                try {
                        mediaPlayer.setDataSource(audioFile.getAbsolutePath());
                        mediaPlayer.prepare();
                        seekBar.setProgress(0);
                        seekBar.setMax(mediaPlayer.getDuration());
                        totalTimeTv.setText(formatTime(mediaPlayer.getDuration()));
                        mediaPlayer.start();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
            }
        } else {
        }


        HealingViewModel viewModel = new HealingViewModel(getApplication());
        viewModel.getHealingItems().observe(this, new Observer<List<Healing>>() {
            @Override
            public void onChanged(List<Healing> healings) {
                songsList = (ArrayList<Healing>) healings;
            }
        });

        iv_back.setOnClickListener(view -> {
            finish();
        });

        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTimeTv.setText(convertToMMSS(mediaPlayer.getCurrentPosition() + ""));

                    if (mediaPlayer.isPlaying()) {
                        pausePlay.setImageResource(R.drawable.baseline_pause_circle_filled_24);
                    } else {
                        pausePlay.setImageResource(R.drawable.path_5984);
                    }
                }

                handler.postDelayed(this, 100);
            }
        };

        handler.post(updateSeekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pausePlay.setOnClickListener(v-> pausePlay());
        nextBtn.setOnClickListener(v-> playNextSong());
        previousBtn.setOnClickListener(v-> playPreviousSong());
        SwipeGestureListener gestureListener = new SwipeGestureListener() {
            @Override
            public void onSwipeRight() {
                playPreviousSong();
            }

            @Override
            public void onSwipeLeft() {
                playNextSong();
            }
        };
        gestureDetector = new GestureDetector(this, gestureListener);
    }

    void setResourcesWithMusic(){

        playMusic();

    }


    private void playMusic(){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(BASE_URL+songsList.get(MyMediaPlayer.currentIndex).getUrl());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
            totalTimeTv.setText(formatTime(mediaPlayer.getDuration()));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            if (play_back == 0) {
                mediaPlayer.stop();
            }
        }

        handler.removeCallbacks(updateSeekBar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (play_back == 1) {
            } else {
                mediaPlayer.stop();
//                mediaPlayer.release();
                MyMediaPlayer.release();
            }
        }
        handler.removeCallbacks(updateSeekBar);
    }

    private void playNextSong(){

        if(MyMediaPlayer.currentIndex== songsList.size()-1)
            return;
        MyMediaPlayer.currentIndex +=1;
        mediaPlayer.reset();
        setResourcesWithMusic();

    }

    private void playPreviousSong(){
        if(MyMediaPlayer.currentIndex== 0)
            return;
        MyMediaPlayer.currentIndex -=1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }

    private void pausePlay(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();
    }

    @SuppressLint("DefaultLocale")
    public static String convertToMMSS(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1), TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private String formatTime(int milliseconds) {
        int seconds = (milliseconds / 1000) % 60;
        int minutes = (milliseconds / 1000) / 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

}