package com.example.chopin_tp_2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    private Button play = null;
    private Button countdown = null;
    private Button records = null;
    private Button settings = null;

    private MediaPlayer mediaPlayer = null;
    private int musicPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        play = (Button)findViewById(R.id.playbutton);
        countdown = (Button)findViewById(R.id.countdownbutton);
        records = (Button) findViewById(R.id.recordbutton);
        settings = (Button) findViewById(R.id.settingsbutton);

        musicPosition = 0;
        startMusic();
    }

    @Override
    protected void onStart(){
        super.onStart();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,MainActivity.class);
                intent.putExtra("VALUE", "true");
                startActivity(intent);
            }
        });

        countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,MainActivity.class);
                intent.putExtra("VALUE", "false");
                startActivity(intent);
            }
        });

        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,Scores.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,Settings.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
            mediaPlayer = MediaPlayer.create(this, R.raw.main_menu_sound);
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
