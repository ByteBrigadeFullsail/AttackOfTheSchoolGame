package com.bytebrigade.attackoftheschool.gameplay;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import com.bytebrigade.attackoftheschool.R;
import com.bytebrigade.attackoftheschool.databinding.ActivityAssignmentScreenBinding;
import com.bytebrigade.attackoftheschool.gameplay.assignment.Assignment;
import com.bytebrigade.attackoftheschool.gameplay.assignment.enums.AssignmentName;

import static com.bytebrigade.attackoftheschool.gameplay.Profile.level;
import static com.bytebrigade.attackoftheschool.gameplay.Profile.playthroughs;

public class AssignmentScreen extends AppCompatActivity implements Assignment.CallBack {

    private Assignment assignment = new Assignment(10L, AssignmentName.values()[0]);
    public ActivityAssignmentScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_assignment_screen);
        assignment.setBackgroundSetter(this);
        binding.setAssignment(assignment);

        binding.progressBar.setMax(assignment.getMaxClickAmount().intValue());

        binding.clickableBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignment.incrementClick();
                binding.progressBar.setProgress(assignment.getCurrentClickAmount().intValue());
                binding.progressBar.setMax(assignment.getMaxClickAmount().intValue());
                binding.nameEditText.setText(new String("\n Level: " + level + assignment.getAssignmentName() ));
                Log.i("CURRENTSTATS", "Health: " + assignment.getCurrentClickAmount() + "/" + assignment.getMaxClickAmount());
                changeMainBackground();

            }
        });

        binding.godMode.setOnClickListener(v->{
            assignment.clickStrength += 1000000;
        });
        binding.plus49.setOnClickListener(v->{
            level += 49;
        });

        binding.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(AssignmentScreen.this, "Menu button clicked", Toast.LENGTH_SHORT).show();
                int menuscreen = R.layout.menuscreen;
                setContentView(menuscreen);

            }
        });
    }
    @Override
    public void changeMainBackground(){
        switch (level){
            //starts with english

            case 201:// switch to math
                binding.getRoot().setBackgroundResource(R.drawable.math_0);
                break;
            case 401:// switch to PE
                binding.getRoot().setBackgroundResource(R.drawable.pe_0);
                break;
            case 601: // switch to Science
                binding.getRoot().setBackgroundResource(R.drawable.science_0);
                break;
            case 801: // switch to History
                binding.getRoot().setBackgroundResource(R.drawable.history_0);
                break;
            case 1000: // switch to final boss
                binding.getRoot().setBackgroundResource(R.drawable.math_0);
                break;
        }
    }
    @Override
    public void changeClickableBackground() {
            int completionPercentage = (int)Math.floor((assignment.getCurrentClickAmount() / (double)assignment.getMaxClickAmount())*10);
            // Adjust for special stages
            int imgID;
            if (level % 1001 == 0) {
                // Final boss
                imgID = R.drawable.assignmenttemp1;
            } else if (level % 200 == 0) {
                // Professor boss
                imgID = switch (level){
                    //ENGLISH PROFESSOR BELOW
                    case 200 -> switch (playthroughs){
                        case 0 -> R.drawable.englishproff_0;
                        case 1 -> R.drawable.englishproff_0;
                        case 2 -> R.drawable.englishproff_0;
                        default -> R.drawable.englishproff_0;
                    };

                    //MATH PROFESSOR BELOW
                    case 400 -> switch (playthroughs){
                        case 0 -> R.drawable.englishproff_0;
                        case 1 -> R.drawable.englishproff_0;
                        case 2 -> R.drawable.englishproff_0;
                        default -> R.drawable.englishproff_0;
                    };

                    //PE PROFESSOR BELOW
                    case 600 -> switch (playthroughs){
                        case 0 -> R.drawable.englishproff_0;
                        case 1 -> R.drawable.englishproff_0;
                        case 2 -> R.drawable.englishproff_0;
                        default -> R.drawable.englishproff_0;
                    };

                    //SCIENCE PROFESSOR BELOW
                    case 800 -> switch (playthroughs){
                        case 0 -> R.drawable.englishproff_0;
                        case 1 -> R.drawable.englishproff_0;
                        case 2 -> R.drawable.englishproff_0;
                        default -> R.drawable.englishproff_0;
                    };

                    //HISTORY PROFESSOR BELOW
                    case 1000 -> switch (playthroughs){
                        case 0 -> R.drawable.englishproff_0;
                        case 1 -> R.drawable.englishproff_0;
                        case 2 -> R.drawable.englishproff_0;
                        default -> R.drawable.englishproff_0;
                    };

                    default -> R.drawable.englishproff_0;
                };
            } else if (level % 50 == 0) {
                // Test
                imgID = R.drawable.testtemp;


                //test's picture animation





            } else if (level % 5 == 0)
                // quiz/packet
                imgID = R.drawable.quiztemp;



            //more quiztemp's animation







            else {
                imgID = switch(completionPercentage){
                    case 0 -> R.drawable.assignmenttemp1;
                    case 1 -> R.drawable.assignmenttemp2;
                    case 2 -> R.drawable.assignmenttemp3;
                    case 3 -> R.drawable.assignmenttemp4;
                    case 4 -> R.drawable.assignmenttemp5;
                    case 5 -> R.drawable.assignmenttemp6;
                    case 6 -> R.drawable.assignmenttemp7;
                    case 7 -> R.drawable.assignmenttemp8;
                    case 8 -> R.drawable.assignmenttemp9;
                    case 9 -> R.drawable.assignmenttemp10;
                    default -> R.drawable.assignmenttemp1;
                };
            }
            binding.clickableBlock.setBackground(ContextCompat.getDrawable(binding.clickableBlock.getContext(), imgID));
            //Log.i("CURRENTSTATS", completionPercentage + " " + imgID + " " + String.valueOf((currentClickAmount/(double)maxClickAmount)));
    }
}
