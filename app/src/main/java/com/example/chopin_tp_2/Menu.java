package com.example.chopin_tp_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    private Button play = null;
    private Button countdown = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        play = (Button)findViewById(R.id.playbutton);
        countdown = (Button)findViewById(R.id.countdownbutton);
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
    }
}
