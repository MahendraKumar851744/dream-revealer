package com.dream.insights.meanings.interpretation;

import android.media.MediaPlayer;

public class MyMediaPlayer {
    static MediaPlayer instance;

    public static MediaPlayer getInstance(){
        if(instance == null){
            instance = new MediaPlayer();
        }
        return instance;
    }

    public static void release(){
        instance.stop();
        instance.release();
        instance = null;
    }



    public static int currentIndex = -1;
    public static int loop = 0;
    public static int play_back = 0;
}