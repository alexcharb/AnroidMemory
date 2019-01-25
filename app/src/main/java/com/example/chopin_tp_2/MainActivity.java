package com.example.chopin_tp_2;

import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Carte> listCarte = null;
    private GridLayout grid = null;

    private int nbCards = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listCarte = new ArrayList<Carte>();
        grid = (GridLayout) findViewById(R.id.myGridLayout);

        customedGrid();

        InitMemory();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void customedGrid()
    {
        int nbRow = nbCards / 2 ;

        grid.setColumnCount(2);
        grid.setRowCount(nbRow);
    }

    private void InitMemory()
    {
        for(int i = 0 ; i < nbCards ; i++)
        {
            Carte carte = new Carte();
            getSupportFragmentManager().beginTransaction().add(grid.getId(),carte,null).commit();
        }
    }
}
