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
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
        continueButton.setOnClickListener(v ->{
            //continue button
        });
        storeButton.setOnClickListener(v ->{
            //Store button
            Intent intent = new Intent();
            intent.setClass(MainMenu.this, StoreFunctionality.class);
            intent.putExtra("Uniqid","From_Activity_A");
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
        });
    }

}
