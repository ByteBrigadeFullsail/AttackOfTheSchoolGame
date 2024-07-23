package com.bytebrigade.attackoftheschool;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.bytebrigade.attackoftheschool.gameplay.AssignmentScreen;
import com.bytebrigade.attackoftheschool.gameplay.Profile;

public class NewGameMenu extends AppCompatActivity {
    Button startGameButton;
    Button cancelButton;
    EditText characterNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_menu);
        startGameButton = findViewById(R.id.startGameButton);
        cancelButton = findViewById(R.id.cancelButton);
        characterNameInput = findViewById(R.id.characterNameInput);



        startGameButton.setOnClickListener(v ->{
            // Capture the input from the user
            String characterName = characterNameInput.getText().toString();
            // Update Profile class w/ name
            Profile.profileName = characterName;


            Intent intent = new Intent(NewGameMenu.this, AssignmentScreen.class);
            startActivity(intent);
            finish();
        });
        cancelButton.setOnClickListener(v ->{
            Intent intent = new Intent(NewGameMenu.this, MainMenu.class);
            startActivity(intent);
            finish();
        });
    }
}
