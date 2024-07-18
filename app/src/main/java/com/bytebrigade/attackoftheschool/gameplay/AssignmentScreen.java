package com.bytebrigade.attackoftheschool.gameplay;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.bytebrigade.attackoftheschool.R;
import com.bytebrigade.attackoftheschool.databinding.ActivityAssignmentScreenBinding;
import com.bytebrigade.attackoftheschool.gameplay.assignment.Assignment;
import com.bytebrigade.attackoftheschool.gameplay.assignment.enums.AssignmentName;


import java.util.Random;

public class AssignmentScreen extends AppCompatActivity {

    private Assignment assignment = new Assignment(25L, AssignmentName.values()[new Random().nextInt(AssignmentName.values().length)]);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAssignmentScreenBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_assignment_screen);

        binding.setAssignment(assignment);

        binding.progressBar.setMax(assignment.getMaxClickAmount().intValue());

        binding.clickableBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignment.incrementClick();
                binding.progressBar.setProgress(assignment.getCurrentClickAmount().intValue());
                binding.progressBar.setMax(assignment.getMaxClickAmount().intValue());
                binding.nameEditText.setText(assignment.getAssignmentName());
            }
        });

        binding.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AssignmentScreen.this, "Menu button clicked", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
