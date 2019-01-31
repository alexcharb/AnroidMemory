package com.example.chopin_tp_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
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
    private Game game = null;
    private List<String> listTag = null;
    private List<Integer> listPosition = null;
    private String victoryString;
    private long timeRemaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetButton = (Button) findViewById(R.id.resetbutton);
        timer = (TextView) findViewById(R.id.timer);
        grid = (GridLayout) findViewById(R.id.myGridLayout);
        cardTags = new ArrayList<>();
        listTag = new ArrayList<>();
        listPosition = new ArrayList<>();



        // On récupère le menu
        Intent intent = getIntent();
        isCountDown = Boolean.parseBoolean(intent.getStringExtra("VALUE"));

        // On récupère les shared preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String difficulte = prefs.getString("difficulteValue", "D_");

        // Create the game - GRAPHICS AND ALGORITHM
        if (difficulte == "D_")
            game = new Game(4);
        else if (difficulte == "M_")
            game = new Game(3);
        else if (difficulte == "F_")
            game = new Game(2);


        customedGrid();
        InitMemory();

        // Contre la montre ou non
        if(isCountDown)
        {
            initCountDown(10);
            victoryString = "CM_";
        }
        else
        {
            initTimer();
            victoryString = "N_";
        }

        victoryString += difficulte;
    }

    @Override
    protected void onStart() {
        super.onStart();
        AddListener();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                game.resetAllCards();

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
                AddListener();
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
        Card[] cards = game.getCards();

        for(int i = 0 ; i < game.getNbPair() * 2 ; i++)
        {
            Carte carte = new Carte();

            switch(cards[i].getId()) {
                case 0:
                    carte.setRecto(R.drawable.cancer);
                    carte.setIdCard(0);
                    break;
                case 2:
                    carte.setRecto(R.drawable.leo);
                    carte.setIdCard(2);
                    break;
                case 4:
                    carte.setRecto(R.drawable.scorpio);
                    carte.setIdCard(4);
                    break;
                case 6:
                    carte.setRecto(R.drawable.taurus);
                    carte.setIdCard(6);
                    break;
            }
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

                    Card[] cards = game.getCards();

                    String tag = String.valueOf(view.getTag());

                    Carte carte = (Carte) getSupportFragmentManager().findFragmentByTag(tag);
                    carte.swapToRecto();

                    int position = Integer.valueOf(tag.split("_")[1]);
                    // On ajoute la carte courrante
                    listTag.add(tag);
                    listPosition.add(position);

                    game.OnCardClick(position);

                    if (listTag.size() == 2)
                        if (isSameCard(listPosition.get(0), listPosition.get(1)))
                        {
                            // Si on a choisi la même carte, on enlève le coup qu'on vient de faire et on revient en arrière
                            listTag.remove(1);
                            listPosition.remove(1);
                        }


                    // Si on a une paire et qu'on a deux cartes, on les laisse tournées
                    if(game.isPairOkOrNot() && listTag.size() == 2)
                    {
                        // On disable les deux cartes
                        disableListener();
                        listTag.clear();

                    } // Sinon, si on est à la troisième carte, on vide et on ajoute la carte courrante
                    else if(listTag.size() == 3)
                    {
                        // On retire les deux première cartes, on les retournes
                        for (int i=0;i<2;i++)
                            ((Carte) getSupportFragmentManager().findFragmentByTag(listTag.get(i))).swapToVerso();
                        listTag.clear();
                        listPosition.clear();

                        // Si on a cliqué sur une des deux cartes, on la re retourne
                        ((Carte) getSupportFragmentManager().findFragmentByTag(tag)).swapToRecto();

                        // On ajoute la nouvelle carte
                        listTag.add(tag);
                        listPosition.add(position);
                    }

                    if (game.isGameFinished())
                    {
                        if (isCountDown)
                            victoryString += String.valueOf(timeRemaining);
                        else
                            // On ajoute en dernier le temps qu'on a mis
                            victoryString += String.valueOf(count);

                        Toast.makeText(MainActivity.this, "Félicitations, vous avez gagné !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Victory.class);
                        intent.putExtra("game_time", victoryString);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private boolean isSameCard(int position1, int position2)
    {
        if (position1 == position2)
            return true;
        else
            return false;
    }

    private void initCountDown(int time)
    {
        long stopTime = time * 1000;

        countDownTimer = new CountDownTimer(stopTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
                timeRemaining = millisUntilFinished / 1000;
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

    private void disableListener()
    {
        for (String tag : listTag)
        {
            View container =  grid.getChildAt(Integer.valueOf(tag.split("_")[1]));

            container.setOnClickListener(null);
        }
    }
}
