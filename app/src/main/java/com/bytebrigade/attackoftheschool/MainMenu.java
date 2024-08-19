package com.bytebrigade.attackoftheschool;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.bytebrigade.attackoftheschool.gameplay.AssignmentScreen;

import static com.bytebrigade.attackoftheschool.gameplay.Profile.FurthestLevel;
import static com.bytebrigade.attackoftheschool.gameplay.Profile.profileName;

public class MainMenu extends AppCompatActivity {
    Button newGameButton;
    Button continueButton;
    Button storeButton;
    Button BackButton;
    Button momButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        newGameButton = findViewById(R.id.newGameButton);
        continueButton = findViewById(R.id.continueButton);
        if(profileName != null){
            continueButton.setEnabled(true);
            continueButton.setText("Continue: Level " + FurthestLevel);
        }
        storeButton = findViewById(R.id.storeButton);
        newGameButton.setOnClickListener(v ->{
            Intent intent = new Intent(MainMenu.this, NewGameMenu.class);
            startActivity(intent);
            finish();
        });
        continueButton.setOnClickListener(v ->{
            Intent intent = new Intent(MainMenu.this, AssignmentScreen.class);
            startActivity(intent);
            finish();
        });
        storeButton.setOnClickListener(v ->{
            //Store button

            setContentView(R.layout.store_menu);
            BackButton = findViewById(R.id.button66);
            momButton = findViewById(R.id.button5);
            BackButton.setOnClickListener(v1 -> {
                Intent intent = new Intent(MainMenu.this, MainMenu.class);
                startActivity(intent);
                finish();
            });



        });
    }

}
