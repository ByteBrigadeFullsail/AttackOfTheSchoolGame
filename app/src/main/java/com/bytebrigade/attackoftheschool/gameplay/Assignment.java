package com.bytebrigade.attackoftheschool.gameplay;

public class Assignment implements Clickable {

    private Long maxClickAmount;
    private Long currentClickAmount;
    private String assignmentName;

    public Assignment(Long clickAmount, String assignmentName) {
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
        this.assignmentName = "Assignment";
        this.currentClickAmount = 0L;
    }

    public Long getMaxClickAmount() {
        return maxClickAmount;
    }

    public Long getCurrentClickAmount() {
        return currentClickAmount;
    }

    public String getAssignmentName() {
        return assignmentName;
    }
}