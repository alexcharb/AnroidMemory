package com.example.chopin_tp_2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Settings extends AppCompatActivity {

    private RadioGroup groupDifficulte = null;
    private RadioButton facileBtn = null;
    private RadioButton moyenBtn = null;
    private RadioButton difficileBtn = null;

    private RadioGroup groupLanguage = null;
    private RadioButton rbLfr = null;
    private RadioButton rbLen = null;

    private String mLanguageCode = "fr";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        groupDifficulte = (RadioGroup) findViewById(R.id.groupDifficulte);
        groupLanguage = (RadioGroup) findViewById(R.id.groupLanguage);

        facileBtn = (RadioButton) findViewById(R.id.facileBtn);
        moyenBtn = (RadioButton) findViewById(R.id.moyenBtn);
        difficileBtn = (RadioButton) findViewById(R.id.difficileBtn);

    }

    protected void onStart()
    {
        super.onStart();
        setContentView(R.layout.activity_settings);

        groupDifficulte.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selected = (RadioButton) findViewById(groupDifficulte.getCheckedRadioButtonId());


                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Settings.this);
                SharedPreferences.Editor editor = prefs.edit();


                if (selected.getText().equals("Facile") || selected.getText().equals("Easy"))
                {
                    editor.putString("difficulteValue", "F_");
                }
                else if (selected.getText().equals("Moyen") || selected.getText().equals("Normal"))
                {
                    editor.putString("difficulteValue", "M_");
                }
                else if (selected.getText().equals("Difficile") || selected.getText().equals("Hard"))
                {
                    editor.putString("difficulteValue", "D_");
                }

                editor.apply();
            }
        });

    }
}
