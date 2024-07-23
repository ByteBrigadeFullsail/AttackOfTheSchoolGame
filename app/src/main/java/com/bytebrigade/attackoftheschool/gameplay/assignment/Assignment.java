package com.bytebrigade.attackoftheschool.gameplay.assignment;

import android.util.Log;
import com.bytebrigade.attackoftheschool.gameplay.Clickable;
import com.bytebrigade.attackoftheschool.gameplay.assignment.enums.AssignmentName;

import java.math.BigDecimal;

import static com.bytebrigade.attackoftheschool.gameplay.Profile.*;

public class Assignment implements Clickable {
    private Long maxClickAmount;
    public Long clickStrength = 1L;
    private Long currentClickAmount;
    private AssignmentName assignmentName;
    private CallBack caller;
    private String className;

    public Assignment(Long clickAmount, AssignmentName assignmentName) {
        this.currentClickAmount = 0L;
        this.maxClickAmount = clickAmount;
        this.assignmentName = assignmentName;
        className = "English";
    }

    @Override
    public void incrementClick() {
        if (this.currentClickAmount+clickStrength < this.maxClickAmount) {
            this.currentClickAmount+= clickStrength;

            Log.i("CURRENTSTATS", this.currentClickAmount + " / " + this.maxClickAmount);
        } else { //defeated clickable below



            long pointsToAdd = CurrentLevel;
            points += pointsToAdd;
            caller.showAddedPoints("+" + pointsToAdd + " Points");



            if(CurrentLevel == FurthestLevel) {

                progressToNextLevel();
            } else{
                defeatedClickableButNoProgression();
            }
        }
        caller.changeClickableBackground();

    }
    public void currentLevelChanged(){
        this.maxClickAmount = calculateHealth(CurrentLevel);
        this.assignmentName = AssignmentName.values()[(int)Math.ceil(CurrentLevel /200)];
        this.currentClickAmount = 0L;
        caller.changeMainBackground();
        caller.changeClickableBackground();
    }

    //Defeated boss/assignment code here, while NOT progressing
    //Also means While NOT at FurthestStage(they pressed the previous stage button)
    public void defeatedClickableButNoProgression(){
        this.maxClickAmount = calculateHealth(CurrentLevel);
        this.currentClickAmount = 0L;
    }

    public long getUpgradePrice() {
        long basePrice = 50; // Initial price for the first upgrade
        return (long) (basePrice * Math.pow(1.15, amountOfClickIncreasedUpgrades));
    }
    public long calculateHealth(int stage) {
        // Base health for regular stages
        double baseHealth = 10;
        double health;

        // Apply incremental increases based on stage type
        if (stage % 1000 == 0) {
            // Final boss
            health = baseHealth * Math.pow(1.12, (stage - 1) / 5) * 3 * 1.5 * 10;
        } else if (stage % 200 == 0) {
            // Professor boss
            health = baseHealth * Math.pow(1.12, (stage - 1) / 5) * 3;
        } else if (stage % 50 == 0) {
            // Test
            health = baseHealth * Math.pow(1.12, (stage - 1) / 5) * 1.5;
        } else if (stage % 5 == 0) {
            // Quiz/packet
            health = baseHealth * Math.pow(1.12, (stage - 1) / 5) * 1.2;
        } else {
            // Regular stage
            health = baseHealth * Math.pow(1.12, (stage - 1) / 5);
        }

        return (long) health;
    }

    @Override
    public void progressToNextLevel() {
        Log.i("CURRENTSTATS", "PROGRESS WAS CALLED! " + CurrentLevel + " is current level then Furthest level: " + FurthestLevel);
        FurthestLevel++;
        CurrentLevel = FurthestLevel;
        this.maxClickAmount = calculateHealth(FurthestLevel);
        this.assignmentName = AssignmentName.values()[(int)Math.ceil(FurthestLevel /200)];
        this.currentClickAmount = 0L;
        Log.i("CURRENTSTATS", this.maxClickAmount + "");
        caller.changeMainBackground();
    }
    public void setBackgroundSetter(CallBack b){
        this.caller = b;
    }

    public Long getMaxClickAmount() {
        return maxClickAmount;
    }


    public Long getCurrentClickAmount() {
        return currentClickAmount;
    }
    public void  setClassName(String s) {
        className = s;
    }
    public String getClassName(){
        return className;
    }

    public String getAssignmentName() {
        return assignmentName.getAssignmentName();
        //return "\n Level: " + level + assignmentName.getAssignmentName();
    }
    public interface CallBack {
        void changeClickableBackground();
        void changeMainBackground();
        void showAddedPoints(String message);
    }
}