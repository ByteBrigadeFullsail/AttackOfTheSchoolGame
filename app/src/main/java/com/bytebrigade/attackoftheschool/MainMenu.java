package com.bytebrigade.attackoftheschool;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bytebrigade.attackoftheschool.gameplay.AssignmentScreen;



public class MainMenu extends AppCompatActivity {
    Button newGameButton;
    Button continueButton;
    Button storeButton;
    int x5Tracker;
    int x2Tracker;
    int x10Tracker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);



        newGameButton = findViewById(R.id.newGameButton);
        continueButton = findViewById(R.id.continueButton);
        storeButton = findViewById(R.id.storeButton);
        newGameButton.setOnClickListener(v ->{
            Intent intent = new Intent(MainMenu.this, NewGameMenu.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
        continueButton.setOnClickListener(v ->{
            //continue button
        });
        storeButton.setOnClickListener(v ->{
            //Store button
            Bundle x5 = getIntent().getExtras();

            Intent intent = new Intent();
            intent.setClass(MainMenu.this, StoreFunctionality.class);
            intent.putExtra("Uniqid","From_Activity_A");
            if(x5 != null)
            {
                String x5changetext= x5.getString("x5name");
                String x2changetext= x5.getString("x2name");
                String x10changetext= x5.getString("x10name");
                intent.putExtra("x5",x5changetext);
                intent.putExtra("x2",x2changetext);
                intent.putExtra("x10",x10changetext);
                x5Tracker = x5.getInt("x5Tracker");
                x2Tracker = x5.getInt("x2Tracker");
                x10Tracker = x5.getInt("x10Tracker");
                intent.putExtra("x5Tracker",x5Tracker);
                intent.putExtra("x2Tracker",x2Tracker);
                intent.putExtra("x10Tracker",x10Tracker);

            }
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();

        });
    }

}
