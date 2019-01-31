package com.example.chopin_tp_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity that will be displayed if the player win the game and will cj^heck and save if the time is better than an older record.
 */
public class Victory extends AppCompatActivity {

    private MediaPlayer mediaPlayer = null;
    private int musicPosition;
    private String stringFromGame = "N_D_14";
    private boolean isRecord = false;
    private boolean isSaved = false;

    private EditText entryNameUser = null;
    private Button saveRecord = null;
    private Button returnMenu = null;
    private Button replay = null;
    private TextView text = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);

        entryNameUser = (EditText) findViewById(R.id.entry_user_name);
        saveRecord = (Button) findViewById(R.id.button_save_record);
        returnMenu = (Button) findViewById(R.id.button_return_menu);
        replay = (Button) findViewById(R.id.button_replay);
        text = (TextView) findViewById(R.id.text_victory);

        Intent intent = getIntent();
        stringFromGame = intent.getStringExtra("game_time");

        musicPosition = 0;
        startMusic();

        isRecord = checkIfRecord();

        // On masque des éléments si ce n'est pas un record
        if(!isRecord)
        {
            entryNameUser.setVisibility(View.GONE);
            saveRecord.setVisibility(View.GONE);
            text.setText("C'est une belle victoire");
        }
        else
        {
            text.setText("ET c'est un nouveau record !!!");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // On ajoute une fonction pour mettre à jour les records sur un bouton
        if(isRecord) {
            saveRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (entryNameUser.getText().toString().length() > 8 || entryNameUser.getText().toString().contains(";") || entryNameUser.getText().toString().contains("_")) {
                        Toast.makeText(v.getContext(), "Le formate de votre pseudo n'est pas correcte, il doit contenir moins de 8 caractères et ne pas contenir de caractères spéciaux.", Toast.LENGTH_LONG).show();
                    } else {
                        if (!isSaved) {
                            updateRecords();
                            isSaved = true;
                        } else {
                            Toast.makeText(v.getContext(), "Le record a déja été sauvegradé.", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            });
        }

        // Boutons pour relancer une partie ou revenir au menu principal
        returnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Victory.this,Menu.class);
                startActivity(intent);
            }
        });

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Victory.this,MainActivity.class);
                if(stringFromGame.split("_")[1].equals("N"))
                {
                    intent.putExtra("VALUE", "true");
                }
                else
                {
                    intent.putExtra("VALUE", "false");
                }
                startActivity(intent);
            }
        });

    }

    /**
     * Fonction that will parse the actuals records to insert the actual result if it is a record and save it into the shared preferencies.
     */
    private void updateRecords()
    {
        // Get and parse the result from the last game
        String[] strings = stringFromGame.split("_");

        String game_mode = strings[0];
        String difficulty = strings[1];
        int timeToFinish = Integer.valueOf(strings[2]);

        String newRecordsString = "";

        boolean recordApply = false;

        // Get a string with all the records from the specified categorie
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String actualRecords = prefs.getString(game_mode + "_" + difficulty,"Aucun records encore établi");

        String[] records = actualRecords.split(";");

        // On vérifie si le record existe ou pas
        if(actualRecords.contains(";") )
        {
            // Check if they already are 3 records into the categorie
            if(records.length > 3)
            {
                int index = 0;

                for (String record : records)
                {
                    if (index < 3)
                    {
                        // Get the time of the record we are observing
                        int tempoTimeRecord = Integer.valueOf(record.split(":")[1]);

                        // Check if the result time is better than an old record and that the actual time has not been already insert
                        if (tempoTimeRecord > timeToFinish && !recordApply)
                        {
                            newRecordsString += String.valueOf(entryNameUser.getText()) + ":" + String.valueOf(timeToFinish) + ";";
                            recordApply = true;
                            index++;

                            if (index < 3)
                            {
                                newRecordsString += record + ";";
                                index++;
                            }
                        }
                        else
                            {
                            newRecordsString += record + ";";
                            index++;
                        }
                    }
                }
            }
            else
            {
                for (String record : records)
                {
                    int tempoTimeRecord = Integer.valueOf(record.split(":")[1]);

                    if (tempoTimeRecord > timeToFinish && !recordApply)
                    {
                        newRecordsString += String.valueOf(entryNameUser.getText()) + ":" + String.valueOf(timeToFinish) + ";";
                        newRecordsString += record + ";";
                        recordApply = true;
                    }
                    else
                    {
                        newRecordsString += record + ";";
                    }
                }
            }

        }
        else
        {
            newRecordsString += String.valueOf(entryNameUser.getText()) + ":" + String.valueOf(timeToFinish) + ";";
        }

        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(game_mode + "_" + difficulty, newRecordsString);

        editor.apply();
    }

    /**
     * Fonction that will check if the actual result is  record or not.
     * @return Boolean that is equal to true if the result is a record.
     */
    private boolean checkIfRecord()
    {
        String[] strings = stringFromGame.split("_");

        String game_mode = strings[0];
        String difficulty = strings[1];
        int timeToFinish = Integer.valueOf(strings[2]);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String actualRecors = prefs.getString(game_mode + "_" + difficulty,"Aucun records encore établi");

        String[] records = actualRecors.split(";");

        if(actualRecors.contains(";") && records.length >= 3) {

            for (String record : records) {
                    int tempoTime = Integer.valueOf(record.split(":")[1]);
                    if (tempoTime > timeToFinish)
                    {
                        return true;
                    }
            }
            return false;
        }
        else
        {
            return true;
        }
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
