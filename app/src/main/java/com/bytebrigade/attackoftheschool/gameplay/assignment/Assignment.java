package com.bytebrigade.attackoftheschool.gameplay.assignment;

import com.bytebrigade.attackoftheschool.gameplay.Clickable;
import com.bytebrigade.attackoftheschool.gameplay.assignment.enums.AssignmentName;

import java.util.Random;

public class Assignment implements Clickable {

    private Long maxClickAmount;
    private Long currentClickAmount;
    private AssignmentName assignmentName;

    public Assignment(Long clickAmount, AssignmentName assignmentName) {
        this.currentClickAmount = 0L;
        this.maxClickAmount = clickAmount;
        this.assignmentName = assignmentName;
    }

    @Override
    public void incrementClick() {
        if (this.currentClickAmount < this.maxClickAmount) {
            this.currentClickAmount++;
        } else {
            changeClickable();
        }
    }

    @Override
    public void changeClickable() {
        this.maxClickAmount = (this.maxClickAmount * 4) / 2;
        this.assignmentName = AssignmentName.values()[new Random().nextInt(AssignmentName.values().length)];
        this.currentClickAmount = 0L;
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
}