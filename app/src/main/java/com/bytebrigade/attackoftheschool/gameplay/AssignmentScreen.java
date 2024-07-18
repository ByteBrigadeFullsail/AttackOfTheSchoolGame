package com.bytebrigade.attackoftheschool.gameplay;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.bytebrigade.attackoftheschool.R;
import com.bytebrigade.attackoftheschool.databinding.ActivityAssignmentScreenBinding;

public class AssignmentScreen extends AppCompatActivity {

    private Assignment assignment = new Assignment(25L, "Assignment");

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
