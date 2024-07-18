package com.bytebrigade.attackoftheschool.gameplay.assignment;

import android.util.Log;
import com.bytebrigade.attackoftheschool.gameplay.Clickable;
import com.bytebrigade.attackoftheschool.gameplay.assignment.enums.AssignmentName;

import static com.bytebrigade.attackoftheschool.gameplay.Profile.level;

public class Assignment implements Clickable {
    private Long maxClickAmount;
    private Long clickStrength = 1L;
    private Long currentClickAmount;
    private AssignmentName assignmentName;
    private CallBack caller;

    public Assignment(Long clickAmount, AssignmentName assignmentName) {
        this.currentClickAmount = 0L;
        this.maxClickAmount = clickAmount;
        this.assignmentName = assignmentName;
    }

    @Override
    public void incrementClick() {
        if (this.currentClickAmount+clickStrength < this.maxClickAmount) {
            this.currentClickAmount+= clickStrength;

            Log.i("CURRENTSTATS", this.currentClickAmount + " / " + this.maxClickAmount);
        } else {
            changeClickable();
        }

        caller.changeClickableBackground();
    }


    public long calculateHealth(int stage) {
        // Base taps calculation
        double baseTaps = 10 * Math.pow(1.0406, stage - 1);
        double health;

        // Adjust for special stages
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

    public String getAssignmentName() {
        return assignmentName.getAssignmentName();
    }
    public interface CallBack {
        void changeClickableBackground();
        void changeMainBackground();
    }
}