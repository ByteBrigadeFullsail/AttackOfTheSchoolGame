package com.bytebrigade.attackoftheschool.gameplay.assignment;

import android.content.Intent;
import android.util.Log;
import com.bytebrigade.attackoftheschool.MainMenu;
import com.bytebrigade.attackoftheschool.NewGameMenu;
import com.bytebrigade.attackoftheschool.gameplay.Clickable;
import com.bytebrigade.attackoftheschool.gameplay.assignment.enums.AssignmentName;

import static com.bytebrigade.attackoftheschool.gameplay.Profile.*;

public class Assignment implements Clickable {
    private long maxClickAmount;
    public long currentClickAmount;
    private AssignmentName assignmentName;
    private CallBack caller;
    private String className;

    //aaa
    public Assignment(long clickAmount, AssignmentName assignmentName) {
        this.currentClickAmount = 0L;
        this.maxClickAmount = clickAmount;
        this.assignmentName = assignmentName;
        className = "English";
    }

    public void startCheatSheet() {
        clickStrengthMultiplier = 2;
    }

    public void endCheatSheet() {
        clickStrengthMultiplier = 1;
    }

    public long calculatePoints(int stage) {
        double basePoints = 5;
        double points;

        if (stage % 1001 == 0) {
            points = basePoints * Math.pow(1.1, (stage - 1) / 5) * 3 * 1.5 * 10;
        } else if (stage % 200 == 0) {
            points = basePoints * Math.pow(1.1, (stage - 1) / 5) * 3;
        } else if (stage % 50 == 0) {
            points = basePoints * Math.pow(1.1, (stage - 1) / 5) * 1.5;
        } else if (stage % 5 == 0) {
            points = basePoints * Math.pow(1.1, (stage - 1) / 5) * 1.2;
        } else {
            points = basePoints * Math.pow(1.1, (stage - 1) / 5);
        }

        return (long) points;
    }

    public void incrementPoints() {

        long pointsToAdd = calculatePoints(CurrentLevel);
        points += pointsToAdd;
        caller.showAddedPoints("+" + pointsToAdd + " Points");

    }

    @Override
    public void incrementClick() {
        if (this.currentClickAmount + clickStrength * clickStrengthMultiplier < this.maxClickAmount) {
            this.currentClickAmount += clickStrength * clickStrengthMultiplier;

            Log.i("CURRENTSTATS", this.currentClickAmount + " / " + this.maxClickAmount);
        } else { //defeated clickable below

            incrementPoints();
            if (CurrentLevel == FurthestLevel) {

                progressToNextLevel();
            } else {
                defeatedClickableButNoProgression();
            }
        }
        caller.changeClickableBackground();

    }


    public void currentLevelChanged() {
        this.maxClickAmount = calculateHealth(CurrentLevel);

        this.assignmentName = AssignmentName.values()[(CurrentLevel / 200)];
        this.currentClickAmount = 0L;
        caller.changeMainBackground();
        caller.changeClickableBackground();
    }

    //Defeated boss/assignment code here, while NOT progressing
    //Also means While NOT at FurthestStage(they pressed the previous stage button)
    public void defeatedClickableButNoProgression() {
        this.maxClickAmount = calculateHealth(CurrentLevel);
        this.currentClickAmount = 0L;
    }

    public long getUpgradePrice() {
        long basePrice = 50; // Initial price for the first upgrade
        return (long) (basePrice * Math.pow(1.1, amountOfClickIncreasedUpgrades));
    }

    public long calculateHealth(int stage) {
        // Base health for regular stages
        double baseHealth = 100;
        double health;
        double basePercent = switch (playthroughs) {
            case ELEMENTARY -> 1.04;
            case HIGH_SCHOOL -> 1.06;
            case COLLAGE -> 1.09;
        };
        // Apply incremental increases based on stage type
        if (stage % 1001 == 0) {
            // Final boss
            health = baseHealth * Math.pow(basePercent, (stage - 1) / 5) * 3 * 1.5 * 10;
        } else if (stage % 200 == 0) {
            // Professor boss
            health = baseHealth * Math.pow(basePercent, (stage - 1) / 5) * 3;
        } else if (stage % 50 == 0) {
            // Test
            health = baseHealth * Math.pow(basePercent, (stage - 1) / 5) * 1.5;
        } else if (stage % 5 == 0) {
            // Quiz/packet
            health = baseHealth * Math.pow(basePercent, (stage - 1) / 5) * 1.2;
        } else {
            // Regular stage
            health = baseHealth * Math.pow(basePercent, (stage - 1) / 5);
        }

        return (long) health;
    }

    @Override
    public void progressToNextLevel() {
        if (FurthestLevel != 1001) {
            Log.i("CURRENTSTATS", "PROGRESS WAS CALLED! " + CurrentLevel + " is current level then Furthest level: " + FurthestLevel);
            FurthestLevel++;
            CurrentLevel = FurthestLevel;
            this.maxClickAmount = calculateHealth(FurthestLevel);
            this.assignmentName = AssignmentName.values()[(FurthestLevel / 200)];
            this.currentClickAmount = 0L;
            Log.i("CURRENTSTATS", this.maxClickAmount + "");
            caller.changeMainBackground();
        } else {

            finishedPlaythrough();

            caller.sendToCredits();
        }
    }

    public void setBackgroundSetter(CallBack b) {
        this.caller = b;
    }

    public Long getMaxClickAmount() {
        return maxClickAmount;
    }


    public Long getCurrentClickAmount() {
        return currentClickAmount;
    }

    public void setClassName(String s) {
        className = s;
    }

    public String getClassName() {
        return className;
    }

    public String getAssignmentName() {
        return assignmentName.getAssignmentName();
        //return "\n Level: " + level + assignmentName.getAssignmentName();
    }

    public void incrementClickBy(int additionalClicks) {
        this.currentClickAmount += additionalClicks;
    }

    public interface CallBack {
        void changeClickableBackground();

        void changeMainBackground();

        void showAddedPoints(String message);

        void start30SecondBossTimer();

        void stop30SecondBossTimer();
        void sendToCredits();
    }
}