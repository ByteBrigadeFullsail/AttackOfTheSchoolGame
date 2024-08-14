package com.bytebrigade.attackoftheschool.gameplay.assignment.enums;

public enum AssignmentName {
    ENGLISH_ASSIGNMENT("English"),
    MATH_ASSIGNMENT("Math"),
    PE_ASSIGNMENT("P.E"),
    SCIENCE_ASSIGNMENT("Science"),
    HISTORY_ASSIGNMENT("History"),
    DOE("Department Of Education");
    private String assignmentName;
    AssignmentName(String assignmentName){
        this.assignmentName = assignmentName;
    }
    public String getAssignmentName(){
        return assignmentName;
    }
}
