package com.bytebrigade.attackoftheschool;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.bytebrigade.attackoftheschool.gameplay.AssignmentScreen;

import java.util.concurrent.atomic.AtomicInteger;

import static com.bytebrigade.attackoftheschool.gameplay.Profile.*;

public class StoreFunctionality extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        AtomicInteger x2Tracker = new AtomicInteger();
        AtomicInteger x5Tracker = new AtomicInteger();
        int x10Tracker = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_menu);

        //buttons
        Button BackButton = findViewById(R.id.button66);
        Button x2 = findViewById(R.id.button42);
       Button x5 = findViewById(R.id.button77);

        //x5 button Function
         x5.setOnClickListener(v ->
         {
             if(x5Tracker.get() == 0)
             {
                 points = points -2000;
                 clickStrength = clickStrength*5;
                 x5.setText("X5 Clicks 3,000Pts");
                 x5Tracker.getAndIncrement();

             }
             else if(x5Tracker.get() == 1)
             {
                 points = points -3000;
                 clickStrength = clickStrength*5;
                 x5.setText("X5 Clicks 4,000Pts");
                 x5Tracker.getAndIncrement();
             }
             else if (x5Tracker.get() == 2)
             {
                 points = points -4000;
                 clickStrength = clickStrength*5;
                 x5.setText("X5 Clicks 5,000Pts");
                 x5Tracker.getAndIncrement();

             }
             else if(x5Tracker.get() >= 4)
             {
                 points = points -5000;
                 clickStrength = clickStrength*5;
                 x5Tracker.getAndIncrement();

             }

             else
             {
                 System.out.println("Sorry You Dont Have Enough Points.");
             }
         });


       //x2 button Functionality
        x2.setOnClickListener(v1 ->
        {
            if(x2Tracker.get() == 0)
            {
                points = points -200;
                clickStrength = clickStrength*2;
                x2.setText("X2 Clicks 400Pts");
                x2Tracker.getAndIncrement();

            }
            else if(x2Tracker.get() == 1)
            {
                points = points -400;
                clickStrength = clickStrength*2;
                x2.setText("X2 Clicks 800Pts");
                x2Tracker.getAndIncrement();
            }
            else if (x2Tracker.get() == 2)
            {
                points = points -800;
                clickStrength = clickStrength*2;
                x2.setText("X2 Clicks 1000Pts");
                x2Tracker.getAndIncrement();

            } else if (x2Tracker.get() == 3)
            {
                points = points -1000;
                clickStrength = clickStrength*2;
                x2.setText("X2 Clicks 2000Pts");
                x2Tracker.getAndIncrement();
            }
            else if(x2Tracker.get() >= 4)
            {
                points = points -2000;
                clickStrength = clickStrength*2;
                x2Tracker.getAndIncrement();
            }
            else
            {
               System.out.println("Sorry You Dont Have Enough Points.");
            }
        });

       // BackButton Functionality
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
