package com.example.chopin_tp_2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;

public class Settings extends AppCompatActivity {

    private RadioGroup groupDifficulte = null;

    private Button bfr = null;
    private Button ben = null;

    private String mLanguageCode = "fr";

    private boolean test = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        groupDifficulte = (RadioGroup) findViewById(R.id.groupDifficulte);


    }

    protected void onStart()
    {
        super.onStart();

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

        findViewById(R.id.bfr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Change Application level locale
                mLanguageCode = "en";
                LocaleHelper.setLocale(Settings.this, mLanguageCode);

                //It is required to recreate the activity to reflect the change in UI.
                recreate();
            }
        });

        findViewById(R.id.ben).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Change Application level locale
                //mLanguageCode = "en";
                LocaleHelper.setLocale(Settings.this, mLanguageCode);

                //It is required to recreate the activity to reflect the change in UI.
                recreate();
            }
        });

    }
}
