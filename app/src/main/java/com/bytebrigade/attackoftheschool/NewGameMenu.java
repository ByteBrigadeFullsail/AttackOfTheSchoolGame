package com.bytebrigade.attackoftheschool;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.bytebrigade.attackoftheschool.gameplay.AssignmentScreen;

public class NewGameMenu extends AppCompatActivity {
    Button startGameButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_menu);
        startGameButton = findViewById(R.id.startGameButton);

        startGameButton.setOnClickListener(v ->{
            Intent intent = new Intent(NewGameMenu.this, AssignmentScreen.class);
            startActivity(intent);
            finish();
        });
    }
}
