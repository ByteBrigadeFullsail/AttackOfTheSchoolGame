package com.bytebrigade.attackoftheschool;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, MainMenu.class);
            startActivity(intent);
            finish();
        });
    }
}