package com.bytebrigade.attackoftheschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.bytebrigade.attackoftheschool.gameplay.AssignmentScreen;

public class StoreFunctionality extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//buttons
        Button BackButton = findViewById(R.id.button66);


        setContentView(R.layout.store_menu);

        BackButton = findViewById(R.id.button66);
        BackButton.setOnClickListener(v1 ->
        {
            String intentData = getIntent().getExtras().getString("Uniqid");
            if(intentData.equals("From_Activity_A"))
            {
                Intent intent = new Intent(StoreFunctionality.this, MainMenu.class);
                startActivity(intent);
            }
            else if(intentData.equals("From_Activity_B"))
            {


               Intent intent = new Intent(StoreFunctionality.this, AssignmentScreen.class);
                intent.putExtra("Uniqid","From_Store");
                startActivity(intent);
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });


    }
}
