package com.bytebrigade.attackoftheschool;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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
            // Check profile name is valid and update Profile class w/ name
            if (isProfileNameValid(characterName)) {
                Profile.profileName = characterName;
                Intent intent = new Intent(NewGameMenu.this, AssignmentScreen.class);
                intent.putExtra("Uniqid","From_NewGameMenu");
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(NewGameMenu.this, "Please enter a valid character name.", Toast.LENGTH_SHORT).show();
            }
        });


        cancelButton.setOnClickListener(v ->{
            Intent intent = new Intent(NewGameMenu.this, MainMenu.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean isProfileNameValid(String profileName){
        // Check if the name is not empty and has at least 3 characters
        return profileName != null && !profileName.trim().isEmpty() && profileName.length() >= 3;
    }
}
