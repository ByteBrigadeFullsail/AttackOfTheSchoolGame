package com.bytebrigade.attackoftheschool;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bytebrigade.attackoftheschool.gameplay.AssignmentScreen;
import com.bytebrigade.attackoftheschool.gameplay.assignment.Assignment;

import java.util.concurrent.atomic.AtomicInteger;

import static com.bytebrigade.attackoftheschool.gameplay.Profile.*;

public class StoreFunctionality extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AtomicInteger x2Tracker = new AtomicInteger();
        AtomicInteger x5Tracker = new AtomicInteger();
        AtomicInteger x10Tracker = new AtomicInteger();
        int x5T;
        int x2T;
        int x10T;



        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_menu);


        //buttons
        Button goBack = findViewById(R.id.button105);
        Button x2 = findViewById(R.id.button101);
        Button x5 = findViewById(R.id.button106);
        Button x10 = findViewById(R.id.button113);
        Button buttonMom = findViewById(R.id.button104);
        Button buttonSkip = findViewById(R.id.button114);
        Button Ppoints = findViewById(R.id.button107);

        buttonMom.setOnClickListener(v -> {

            if (momUses == 5){
                Toast.makeText(getApplicationContext(), "You can't hold anymore, you're gonna burst!", Toast.LENGTH_SHORT).show();
            }
            else if (momUses > 5){
                Toast.makeText(getApplicationContext(), "You can't hold anymore, you're gonna burst!", Toast.LENGTH_SHORT).show();
            }
            else {
                updateMomUses(1);
            }

        });




        Bundle x5Text = getIntent().getExtras();
        x5T = x5Text.getInt("x5Tracker");
        x2T = x5Text.getInt("x2Tracker");
        x10T = x5Text.getInt("x10Tracker");


        if (x5T > 0)
        {
            x5.setText(x5ButtonText);
            x5Tracker.set(x5T);
        }
        if (x2T > 0)
        {
            x2.setText(x2ButtonText);
            x2Tracker.set(x2T);
        }
        if (x10T > 0)
        {
            x10.setText(x10ButtonText);
            x10Tracker.set(x10T);
        }

        //x2 button Functionality
        x2.setOnClickListener(v ->
        {
            if (x2Tracker.get() == 0)
            {
                if (points > 200)
                {
                    points = points - 200;
                    clickStrengthMultiplier = clickStrengthMultiplier * 2;
                    x2ButtonText = "x2 Clicks: 400pts.";
                    x2.setText(x2ButtonText);
                    x2Tracker.getAndIncrement();
                }


            }
            else if (x2Tracker.get() == 1)
            {
                if (points > 400)
                {
                    points = points - 400;
                    clickStrengthMultiplier = clickStrengthMultiplier * 2;
                    x2ButtonText = "x2 Clicks: 800pts.";
                    x2.setText(x2ButtonText);
                    x2Tracker.getAndIncrement();
                }
            }
            else if (x2Tracker.get() == 2)
            {
                if (points > 800)
                {
                    points = points - 800;
                    clickStrengthMultiplier = clickStrengthMultiplier * 2;
                    x2ButtonText = "x2 Clicks: 1000pts.";
                    x2.setText(x2ButtonText);
                    x2Tracker.getAndIncrement();
                }

            }
            else if (x2Tracker.get() == 3)
            {
                if (points > 1000)
                {
                    points = points - 1000;
                    clickStrengthMultiplier = clickStrengthMultiplier * 2;
                    x2ButtonText = "x2 Clicks: 2000pts.";
                    x2.setText(x2ButtonText);
                    x2Tracker.getAndIncrement();
                }

            }
            else if (x2Tracker.get() >= 4)
            {
                if (points > 2000)
                {
                    points = points - 2000;
                    clickStrengthMultiplier = clickStrengthMultiplier * 2;
                    x2Tracker.getAndIncrement();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Sorry You Dont Have Enough Points.",Toast.LENGTH_SHORT).show();
            }

        });



        //x10 Button Function
        x10.setOnClickListener(v ->
        {
            if (x10Tracker.get() == 0)
            {
                if (points > 0)
                {
                    points = points - 6000;
                    clickStrengthMultiplier = clickStrengthMultiplier * 10;
                    x10ButtonText = "x10 Clicks: 7000pts.";
                    x10.setText(x10ButtonText);
                    x10Tracker.getAndIncrement();
                }

            }
            else if (x10Tracker.get() == 1)
            {
                if (points > 0)
                {
                    points = points - 7000;
                    clickStrengthMultiplier = clickStrengthMultiplier * 10;
                    x10ButtonText = "x10 Clicks: 8000pts.";
                    x10.setText(x10ButtonText);
                    x10Tracker.getAndIncrement();
                }

            }
            else if (x10Tracker.get() == 2)
            {
                if (points > 0) {
                    points = points - 8000;
                    clickStrengthMultiplier = clickStrengthMultiplier * 10;
                    x10ButtonText = "x10 Clicks: 9000pts.";
                    x10.setText(x10ButtonText);
                    x10Tracker.getAndIncrement();
                }

            }
            else if (x10Tracker.get() >= 3)
            {
                if (points > 9000) {
                    points = points - 9000;
                    clickStrengthMultiplier = clickStrengthMultiplier * 10;
                    x10ButtonText = "x10 Clicks: 7000pts.";
                    x10.setText(x10ButtonText);
                    x10Tracker.getAndIncrement();
                }

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Sorry You Dont Have Enough Points.",Toast.LENGTH_SHORT).show();

            }


        });

        //x5 button Function
        x5.setOnClickListener(v ->
        {
            if (x5Tracker.get() == 0) {
                if (points > 2000) {
                    points = points - 2000;
                    clickStrengthMultiplier = clickStrengthMultiplier * 5;
                    x5ButtonText = "x5 Clicks: 3000pts.";
                    x5.setText(x5ButtonText);
                    x5Tracker.getAndIncrement();
                }


            } else if (x5Tracker.get() == 1) {
                if (points > 3000) {
                    points = points - 3000;
                    clickStrengthMultiplier = clickStrengthMultiplier * 5;
                    x5ButtonText = "x5 Clicks: 4000pts.";
                    x5.setText(x5ButtonText);
                    x5Tracker.getAndIncrement();
                }

            } else if (x5Tracker.get() == 2) {
                if (points > 4000) {
                    points = points - 4000;
                    clickStrengthMultiplier = clickStrengthMultiplier * 5;
                    x5ButtonText = "x5 Clicks: 5000pts.";
                    x5.setText(x5ButtonText);
                    x5Tracker.getAndIncrement();
                }


            }
            else if (x5Tracker.get() > 3 || x5Tracker.get() == 3)
            {
                if (points > 5000)
                {
                    points = points - 5000;
                    clickStrengthMultiplier = clickStrengthMultiplier * 5;
                    x5Tracker.getAndIncrement();


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Sorry You Dont Have Enough Points.",Toast.LENGTH_SHORT).show();
                }

            }





        });
        // BackButton Functionality
        goBack.setOnClickListener(v ->
        {

            String intentData = getIntent().getExtras().getString("Uniqid");

            if (intentData.equals("From_Activity_A"))
            {
                Intent intent = new Intent();
                intent.setClass(StoreFunctionality.this, MainMenu.class);
                updateIntent(x2Tracker, x5Tracker, x10Tracker, x2, x5, x10, intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(intent);
                finish();
            }
            else if (intentData.equals("From_Activity_B"))
            {

                Intent intent = new Intent();
                intent.setClass(StoreFunctionality.this, AssignmentScreen.class);
                intent.putExtra("Uniqid", "From_Store");
                updateIntent(x2Tracker, x5Tracker, x10Tracker, x2, x5, x10, intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(intent);
                finish();
            }


        });

        // +points per page button functionality
        Ppoints.setOnClickListener(v ->
        {
           Assignment.basePoints = Assignment.basePoints + Assignment.basePoints * .5;
        });
    }


    public void updateIntent(AtomicInteger x2Tracker, AtomicInteger x5Tracker, AtomicInteger x10Tracker, Button x2, Button x5, Button x10, Intent intent) {
        intent.putExtra("x5name", x5.getText());
        intent.putExtra("x2name", x2.getText());
        intent.putExtra("x10name", x10.getText());
        intent.putExtra("x5Tracker", x5Tracker.get());
        intent.putExtra("x2Tracker", x2Tracker.get());
        intent.putExtra("x10Tracker", x10Tracker.get());
        startActivity(intent);
        finish();
    }
}
