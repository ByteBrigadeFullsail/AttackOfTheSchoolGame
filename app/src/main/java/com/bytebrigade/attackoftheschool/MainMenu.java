package com.bytebrigade.attackoftheschool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.bytebrigade.attackoftheschool.gameplay.AssignmentScreen;
import com.bytebrigade.attackoftheschool.gameplay.Profile;

import static com.bytebrigade.attackoftheschool.gameplay.Profile.*;


public class MainMenu extends AppCompatActivity {
    Button newGameButton;
    Button continueButton;
    Button storeButton;

    Button BackButton;
    Button momButton;
    Bundle x5;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        if(initialAccess == false)
        {
         x5 = getIntent().getExtras();
        }


        newGameButton = findViewById(R.id.newGameButton);
        continueButton = findViewById(R.id.continueButton);
        if(profileName != null){
            continueButton.setEnabled(true);
            continueButton.setText("Continue: Loading... ");
            new Handler(Looper.getMainLooper()).postDelayed(()->{

                continueButton.setText("Continue: Level " + FurthestLevel);
            }, 2000);
        }
        storeButton = findViewById(R.id.storeButton);
        newGameButton.setOnClickListener(v ->{
            initialAccess = false;
            Intent intent = new Intent(MainMenu.this, NewGameMenu.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
        continueButton.setOnClickListener(this::onClick);
        storeButton.setOnClickListener(this::onClick);

        Profile.loadClassFromFile(this);


    }


    private void onClick(View v) {
        int x2Tracker;
        int x5Tracker;
        int x10Tracker;


        if (!initialAccess)
        {
            x5 = getIntent().getExtras();
             intent = getIntent();
        }
        else
        {
            intent = new Intent();
        }
        //Store button
        if(v == continueButton)
        {
            x5 = getIntent().getExtras();
            intent.setClass(MainMenu.this, AssignmentScreen.class);
           // onClick(this.getCurrentFocus());
            intent.putExtra("Uniqid", "From_Activity_B");

        }
        else if(v == storeButton)
        {
            x5 = getIntent().getExtras();
            intent.setClass(MainMenu.this, StoreFunctionality.class);
            intent.putExtra("Uniqid", "From_Activity_A");
        }
        if (x5 != null)
        {
            String x5changetext = x5ButtonText;
            String x2changetext = x2ButtonText;
            String x10changetext = x5.getString("x10name");
            intent.putExtra("x5", x5changetext);
            intent.putExtra("x2", x2changetext);
            intent.putExtra("x10", x10changetext);
            x5Tracker = x5.getInt("x5Tracker");
            x2Tracker = x5.getInt("x2Tracker");
            x10Tracker = x5.getInt("x10Tracker");
            intent.putExtra("x5Tracker", x5Tracker);
            intent.putExtra("x2Tracker", x2Tracker);
            intent.putExtra("x10Tracker", x10Tracker);

        }
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
       finish();

    }
}
