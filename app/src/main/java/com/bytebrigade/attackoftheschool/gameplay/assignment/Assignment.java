package com.bytebrigade.attackoftheschool.gameplay.assignment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import com.bytebrigade.attackoftheschool.R;
import com.bytebrigade.attackoftheschool.gameplay.Clickable;
import com.bytebrigade.attackoftheschool.gameplay.assignment.enums.AssignmentName;
import android.animation.ValueAnimator;

import java.util.Random;

import static com.bytebrigade.attackoftheschool.gameplay.Profile.level;

public class Assignment implements Clickable {
    private Long maxClickAmount;
    private Long clickStrength = 1L;
    private Long currentClickAmount;
    private AssignmentName assignmentName;
    private LinearLayout background;
    ValueAnimator animator;

    public Assignment(Long clickAmount, AssignmentName assignmentName) {
        this.currentClickAmount = 0L;
        this.maxClickAmount = clickAmount;
        this.assignmentName = assignmentName;

        animator = ValueAnimator.ofFloat(-200f, 200f);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                background.setTranslationX(-200 + new Random().nextFloat() * 700);
                background.setTranslationY(-200 + new Random().nextFloat() * 700);
            }
        });

    }

    @Override
    public void incrementClick() {
        if (this.currentClickAmount+clickStrength < this.maxClickAmount) {
            this.currentClickAmount+= clickStrength;

            Log.i("CURRENTSTATS", this.currentClickAmount + " / " + this.maxClickAmount);
        } else {
            changeClickable();
        }

        changeBackground();
    }


    public long calculateHealth(int stage) {
        // Base taps calculation
        double baseTaps = 10 * Math.pow(1.0406, stage - 1);
        double health;
        if (stage % 1000 == 0) {
            // Final boss
            health = baseTaps * 10;
        } else if (stage % 200 == 0) {
            // Professor boss
            health = baseTaps * 2;
        } else if (stage % 50 == 0) {
            // Test
            health = baseTaps * 1.5;
        } else if (stage % 5 == 0) {
            // quiz/packet
            health = baseTaps * 1.2;
        } else {
            // Regular stage
            health = baseTaps;
        }
        return (long) health;
    }

    @Override
    public void changeClickable() {
        level++;
        this.maxClickAmount = calculateHealth(level);
        this.assignmentName = AssignmentName.values()[(int)Math.ceil(level /200)];
        this.currentClickAmount = 0L;
        Log.i("CURRENTSTATS", this.maxClickAmount + "");
        if (level % 1000 == 0) {
            // Final boss
            startAnimation(1000);
        } else if (level % 200 == 0) {
            // Professor boss
            startAnimation(2000);
        } else if (level % 50 == 0) {
            // Test
            startAnimation(4000);
        } else if (level % 5 == 0)
            // quiz/packet
            startAnimation(6000);
        else if(animator.isRunning()) stopAnimation();
    }
    public void setBackgroundSetter(LinearLayout b){
        this.background = b;
    }

    void changeBackground(){
        int completionPercentage = (int)Math.floor((currentClickAmount / (double)maxClickAmount)*10);
        // Adjust for special stages
        int imgID;
        if (level % 1000 == 0) {
            // Final boss
            imgID = R.drawable.assignmenttemp1;
        } else if (level % 200 == 0) {
            // Professor boss
            imgID = R.drawable.assignmenttemp2;
        } else if (level % 50 == 0) {
            // Test
            imgID = R.drawable.testtemp;
        } else if (level % 5 == 0)
            // quiz/packet
            imgID = R.drawable.quiztemp;
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
        //Log.i("CURRENTSTATS", completionPercentage + " " + imgID + " " + String.valueOf((currentClickAmount/(double)maxClickAmount)));
        background.setBackground(ContextCompat.getDrawable(background.getContext(), imgID));
    }

    public Long getMaxClickAmount() {
        return maxClickAmount;
    }


    public Long getCurrentClickAmount() {
        return currentClickAmount;
    }

    public String getAssignmentName() {
        return assignmentName.getAssignmentName();
    }
    public void startAnimation(int duration){
        animator.setDuration(duration); // duration in milliseconds
        animator.start();
        Log.i("CURRENTSTATS", animator.getDuration() + " IS THE DURATION");
    }
    public void stopAnimation(){
        animator.end();
    }
}