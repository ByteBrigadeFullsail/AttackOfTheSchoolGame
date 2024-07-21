package com.bytebrigade.attackoftheschool.MenuSelectorScreen;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bytebrigade.attackoftheschool.MainActivity;
import com.bytebrigade.attackoftheschool.MainMenu;
import com.bytebrigade.attackoftheschool.R;
import com.bytebrigade.attackoftheschool.gameplay.AssignmentScreen;

public class Menuselector extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menuscreen);

        Button backbutton = (Button)findViewById(R.id.back);
        Button store = (Button)findViewById(R.id.button3);
        Button classes  = (Button)findViewById(R.id.Classes);
        Button ChallengeTeahcer  = (Button)findViewById(R.id.button5);
        Button PGTBotter  = (Button)findViewById(R.id.botter);
        Button LibraryUpgrades = (Button)findViewById(R.id.LibraryUpgrades);

        ImageView arrow = (ImageView)findViewById(R.id.imageView2);
        arrow.setOnTouchListener(new View.OnTouchListener()
        {
            @SuppressLint("ClickableViewAccessibility")
            @Override

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    Intent intent = new Intent(Menuselector.this, AssignmentScreen.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(Menuselector.this, AssignmentScreen.class);
                startActivity(intent);
                finish();

            }
        });
        store.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Menuselector.this, AssignmentScreen.class);
                startActivity(intent);
                finish();
            }
        });
        classes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Menuselector.this, AssignmentScreen.class);
                startActivity(intent);
                finish();
            }
        });
        ChallengeTeahcer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Menuselector.this, AssignmentScreen.class);
                startActivity(intent);
                finish();
            }
        });
        PGTBotter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Menuselector.this, AssignmentScreen.class);
                startActivity(intent);
                finish();
            }
        });
        LibraryUpgrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Menuselector.this, AssignmentScreen.class);
                startActivity(intent);
                finish();
            }
        });








    }



}
