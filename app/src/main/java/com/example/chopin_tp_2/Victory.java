package com.example.chopin_tp_2;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Victory extends AppCompatActivity {

    private MediaPlayer mediaPlayer = null;
    private int musicPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);

        musicPosition = 0;
        startMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusic();
    }

    private void startMusic()
    {

        if(mediaPlayer == null)
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.victory_sound);
            mediaPlayer.seekTo(musicPosition);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
        else if(!mediaPlayer.isPlaying())
        {
            mediaPlayer.start();
        }
    }

    private void stopMusic()
    {
        if(mediaPlayer != null) {
            musicPosition = mediaPlayer.getCurrentPosition();
            if(mediaPlayer.isPlaying())
            {
                mediaPlayer.pause();
            }

            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
