package com.bytebrigade.attackoftheschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bytebrigade.attackoftheschool.gameplay.AssignmentScreen;
import com.bytebrigade.attackoftheschool.gameplay.Profile;

import static com.bytebrigade.attackoftheschool.gameplay.Profile.*;


public class MainMenu extends AppCompatActivity {
    Button newGameButton;
    Button continueButton;
    Button storeButton;

    private Profile profile;

    Button BackButton;
    Button momButton;
    Bundle x5;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        profile = new Profile(this);
        profile.loadProgress();

        if(initialAccess == false)
        {
         x5 = getIntent().getExtras();
        }


        newGameButton = findViewById(R.id.newGameButton);
        continueButton = findViewById(R.id.continueButton);
        storeButton = findViewById(R.id.storeButton);
        continueButton.setEnabled(true);
//        if(Profile.profileName != null){
//
//            continueButton.setText("Continue: Level " + Profile.FurthestLevel);
//        }

        newGameButton.setOnClickListener(v ->{
            Profile.initialAccess = false;
            Intent intent = new Intent(MainMenu.this, NewGameMenu.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

        continueButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, AssignmentScreen.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
        });



        storeButton.setOnClickListener(this::onClick);
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
