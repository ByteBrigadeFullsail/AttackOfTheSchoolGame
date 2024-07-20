package com.bytebrigade.attackoftheschool.gameplay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.bytebrigade.attackoftheschool.MenuSelectorScreen.Menuselector;
import com.bytebrigade.attackoftheschool.R;
import com.bytebrigade.attackoftheschool.databinding.ActivityAssignmentScreenBinding;
import com.bytebrigade.attackoftheschool.gameplay.assignment.Assignment;
import com.bytebrigade.attackoftheschool.gameplay.assignment.enums.AssignmentName;
import  com.bytebrigade.attackoftheschool.MenuSelectorScreen.Menuselector;

public class AssignmentScreen extends AppCompatActivity {

    private Assignment assignment = new Assignment(10L, AssignmentName.values()[0]);

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAssignmentScreenBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_assignment_screen);
        assignment.setBackgroundSetter(binding.clickableBlock);
        binding.setAssignment(assignment);

        binding.progressBar.setMax(assignment.getMaxClickAmount().intValue());

        binding.clickableBlock.setOnClickListener(v -> {
            assignment.incrementClick();
            binding.progressBar.setProgress(assignment.getCurrentClickAmount().intValue());
            binding.progressBar.setMax(assignment.getMaxClickAmount().intValue());
            binding.nameEditText.setText(assignment.getAssignmentName());
            Log.i("CURRENTSTATS", "Health: " + assignment.getCurrentClickAmount() + "/" + assignment.getMaxClickAmount());

        });

     //   binding.menuButton.setOnClickListener(new View.OnClickListener() {
     /*       @Override
            public void onClick(View v)
            {
               // Toast.makeText(AssignmentScreen.this, "Menu button clicked", Toast.LENGTH_SHORT).show();
             Intent intent = new Intent(AssignmentScreen.this, Menuselector.class);
             startActivity(intent);
             finish();


            }



        });*/


        binding.menuButton.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
               if (event.getAction() == MotionEvent.ACTION_DOWN)
               {
                   Intent intent = new Intent(AssignmentScreen.this, Menuselector.class);
                   startActivity(intent);
                   finish();
                   return true;
               }


                return false;
            }
        });

    }






}
