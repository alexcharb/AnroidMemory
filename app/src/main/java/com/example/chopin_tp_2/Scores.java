package com.example.chopin_tp_2;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Activity that will contain two tabs for the records in normal and rush mode.
 */
public class Scores extends AppCompatActivity {

    private ViewPager viewPager = null;
    private TabLayout tabLayout = null;
    private String test = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        viewPager = (ViewPager) findViewById(R.id.myGridView);
        SimpleFragmentPageAdapter adapter = new SimpleFragmentPageAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout = (TabLayout) findViewById(R.id.myTabLayout);
        tabLayout.setupWithViewPager(viewPager);


    }
}
