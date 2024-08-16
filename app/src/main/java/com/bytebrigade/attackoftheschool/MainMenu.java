package com.bytebrigade.attackoftheschool;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.bytebrigade.attackoftheschool.gameplay.AssignmentScreen;

public class MainMenu extends AppCompatActivity {
    Button newGameButton;
    Button continueButton;
    Button storeButton;
    Button BackButton;


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
            finish();
        });
        continueButton.setOnClickListener(v ->{
            //continue button
        });
        storeButton.setOnClickListener(v ->{
            //Store button

            setContentView(R.layout.store_menu);
            BackButton = findViewById(R.id.goback);
            BackButton.setOnClickListener(v1 -> {
                Intent intent = new Intent(MainMenu.this, MainMenu.class);
                startActivity(intent);
                finish();
            });



        });
    }

}
