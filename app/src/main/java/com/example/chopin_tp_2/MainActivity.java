package com.example.chopin_tp_2;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int nbCards = 8;

    private GridLayout grid = null;
    private List<String> cardTags = null;
    private Button resetButton = null;
    private TextView timer = null;
    private CountDownTimer countDownTimer = null;
    private Timer timeTimer = null;
    private boolean isCountDown = false;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetButton = (Button) findViewById(R.id.resetbutton);
        timer = (TextView) findViewById(R.id.timer);
        grid = (GridLayout) findViewById(R.id.myGridLayout);
        cardTags = new ArrayList<String>();

        customedGrid();

        InitMemory();

        if(isCountDown)
        {
            initCountDown(10);
        }
        else
        {
            initTimer();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        AddListener();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(String tag : cardTags)
                {
                    Carte carte = (Carte) getSupportFragmentManager().findFragmentByTag(tag);

                    carte.swapToVerso();
                }

                if(isCountDown)
                {
                    countDownTimer.cancel();

                    countDownTimer.start();
                }
                else
                {
                    timeTimer.cancel();

                    timeTimer.purge();

                    initTimer();
                }
            }
        });
    }

    private void customedGrid()
    {
        int nbRow = nbCards / 2 ;

        grid.setColumnCount(2);
        grid.setRowCount(nbRow);

        for(int i =0; i < nbCards; i++)
        {
            String tempoTag = "cards_" + String.valueOf(i);
            cardTags.add(tempoTag);
        }

    }



    private void InitMemory()
    {
        for(int i = 0 ; i < nbCards ; i++)
        {
            Carte carte = new Carte();
            carte.setRecto(R.drawable.cancer);
            getSupportFragmentManager().beginTransaction().add(grid.getId(),carte, cardTags.get(i)).commit();
        }
    }

    private void AddListener()
    {
        int childCount = grid.getChildCount();

        for (int i= 0; i < childCount; i++){

            View container =  grid.getChildAt(i);

            container.setTag(cardTags.get(i));

            container.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){

                    String tag = String.valueOf(view.getTag());

                    Carte carte = (Carte) getSupportFragmentManager().findFragmentByTag(tag);

                    carte.swapToRecto();
                }
            });
        }
    }

    private void initCountDown(int time)
    {
        long stopTime = time * 1000;

        countDownTimer = new CountDownTimer(stopTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

                Toast.makeText(getBaseContext(), "Loser",Toast.LENGTH_LONG).show();

            }
        };

        countDownTimer.start();
    }

    private void initTimer()
    {
        count = 0;
        timeTimer = new Timer();
        timeTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timer.setText(String.valueOf(count/60) + ":" + String.valueOf(count%60));
                count++;
            }
        }, 1000, 1000);
    }
}
