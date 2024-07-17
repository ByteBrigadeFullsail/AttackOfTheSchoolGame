package com.bytebrigade.attackoftheschool;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v ->{
            //
            //Intent intent = new Intent(MainActivity.this, NEXT_SCREENS_CLASS.class);
            //startActivity(intent);
        });
    }
}