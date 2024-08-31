package com.bytebrigade.attackoftheschool;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bytebrigade.attackoftheschool.gameplay.Profile;


public class CreditsActivity extends AppCompatActivity {

    Button endGameBackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        endGameBackButton = findViewById(R.id.endGameBackButton);
        endGameBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreditsActivity.this, MainMenu.class);
            startActivity(intent);
            Profile.LivingExpensePoints = 5;
            Profile.livingExpenseText = "Living Expenses:" + String.valueOf(Profile.LivingExpensePoints);

        });
    }
}