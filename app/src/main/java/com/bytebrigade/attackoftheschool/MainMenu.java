package com.bytebrigade.attackoftheschool;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {
    Button newGameButton;
    Button continueButton;
    Button storeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        newGameButton = findViewById(R.id.newGameButton);
        continueButton = findViewById(R.id.continueButton);
        storeButton = findViewById(R.id.storeButton);
        newGameButton.setOnClickListener(v ->{
            //new game button
        });
        continueButton.setOnClickListener(v ->{
            //continue button
        });
        storeButton.setOnClickListener(v ->{
            //Store button
        });
    }

}
