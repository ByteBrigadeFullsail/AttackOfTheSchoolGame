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
        AtomicInteger x10Tracker = new AtomicInteger();
        int x5T = 0;
        int x2T = 0;
        int x10T = 0;





        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_menu);



        //buttons
        Button BackButton = findViewById(R.id.button105);
        Button x2 = findViewById(R.id.button101);
        Button x5 = findViewById(R.id.button106);
        Button x10 =findViewById(R.id.button113);


        Bundle x5Text = getIntent().getExtras();
        x5T = x5Text.getInt("x5Tracker");
        x2T = x5Text.getInt("x2Tracker");
       x10T = x5Text.getInt("x10Tracker");

        String x5string =x5.getText().toString();
        String x2string =x2.getText().toString();
        String x10String =x10.getText().toString();
        if(x5T > 0)
        {
          x5.setText(x5Text.getString("x5"));
          x5Tracker.set(x5T);
        }
        if(x2T > 0)
        {
            x2.setText(x5Text.getString("x2"));
            x2Tracker.set(x2T);
        }
        if(x10T > 0)
        {
            x10.setText(x5Text.getString("x10"));
            x10Tracker.set(x10T);
        }

        //x10 Button Function
        x10.setOnClickListener(v ->
        {
          if(x10Tracker.get() == 0)
          {
              points = points -6000;
              clickStrength = clickStrength*10;
              x10.setText(R.string.x10_clicks_7_000pts);

          }
           else if(x10Tracker.get() == 1)
            {
                points = points -7000;
                clickStrength = clickStrength*10;
                x10.setText(R.string.x10_clicks_8_000pts);

            }
           else if(x10Tracker.get() == 2)
            {
                points = points -8000;
                clickStrength = clickStrength*10;
                x10.setText(R.string.x10_clicks_9_000pts);

            }
          else if(x10Tracker.get() >= 3)
            {
                points = points -9000;
                clickStrength = clickStrength*10;
                x10.setText(R.string.x10_clicks_10_000pts);

            }
            else
          {
              System.out.println("Sorry You Dont Have Enough Points.");
          }

            x10Tracker.getAndIncrement();

        });

        //x5 button Function
         x5.setOnClickListener(v ->
         {
             if(x5Tracker.get() == 0)
             {
                 points = points -2000;
                 clickStrength = clickStrength*5;
                 x5.setText(R.string.string_x5_clicks_3_000pts);



             }
             else if(x5Tracker.get() == 1)
             {
                 points = points -3000;
                 clickStrength = clickStrength*5;
                 x5.setText(R.string.string_x5_clicks_4_000pts);

             }
             else if (x5Tracker.get() == 2)
             {
                 points = points -4000;
                 clickStrength = clickStrength*5;
                 x5.setText(R.string.string_x5_clicks_5_000pts);


             }
             else if(x5Tracker.get() >= 3)
             {
                 points = points -5000;
                 clickStrength = clickStrength*5;
                ;

             }
             else
             {
                 System.out.println("Sorry You Dont Have Enough Points.");
             }
             x5Tracker.getAndIncrement();
         });


       //x2 button Functionality
        x2.setOnClickListener(v1 ->
        {
            if(x2Tracker.get() == 0)
            {
                points = points -200;
                clickStrength = clickStrength*2;
                x2.setText(R.string.string_x_clicks_400pts);


            }
            else if(x2Tracker.get() == 1)
            {
                points = points -400;
                clickStrength = clickStrength*2;
                x2.setText(R.string.string_x_clicks_800pts);

            }
            else if (x2Tracker.get() == 2)
            {
                points = points -800;
                clickStrength = clickStrength*2;
                x2.setText(R.string.string_x_clicks_1000pts);


            } else if (x2Tracker.get() == 3)
            {
                points = points -1000;
                clickStrength = clickStrength*2;
                x2.setText(R.string.string_x_clicks_2000pts);

            }
            else if(x2Tracker.get() >= 4)
            {
                points = points -2000;
                clickStrength = clickStrength*2;

            }
            else
            {
               System.out.println("Sorry You Dont Have Enough Points.");
            }
            x2Tracker.getAndIncrement();
        });

       // BackButton Functionality
        BackButton.setOnClickListener(v1 ->
        {
            String intentData = getIntent().getExtras().getString("Uniqid");
            if(intentData.equals("From_Activity_A"))
            {

                Intent intent = new Intent(StoreFunctionality.this, MainMenu.class);
                intent.putExtra("x5name",x5.getText());
                intent.putExtra("x2name",x2.getText());
                intent.putExtra("x10name",x10.getText());
                intent.putExtra("x5Tracker",x5Tracker.get());
                intent.putExtra("x2Tracker",x2Tracker.get());
                intent.putExtra("x10Tracker",x10Tracker.get());
                startActivity(intent);
            }
            else if(intentData.equals("From_Activity_B"))
            {


               Intent intent = new Intent(StoreFunctionality.this, AssignmentScreen.class);
                intent.putExtra("Uniqid","From_Store");
                intent.putExtra("x5name",x5.getText());
                intent.putExtra("x2name",x2.getText());
               intent.putExtra("x10name",x10.getText());
                intent.putExtra("x5Tracker",x5Tracker.get());
                intent.putExtra("x2Tracker",x2Tracker.get());
               intent.putExtra("x10Tracker",x10Tracker.get());
                startActivity(intent);
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });


    }
}
