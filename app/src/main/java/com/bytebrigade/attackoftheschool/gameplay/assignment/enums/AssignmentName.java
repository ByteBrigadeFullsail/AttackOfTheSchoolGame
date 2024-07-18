package com.bytebrigade.attackoftheschool.gameplay.assignment.enums;

public enum AssignmentName {
    MATH_ASSIGNMENT("Math Assignment"){
        @Override
        public String getAssignmentName() {
            return "English Assignment";
        }
    },
    ENGLISH_ASSIGNMENT("English Assignment"){
        @Override
        public String getAssignmentName() {
            return "Math Assignment";
        }
    },
    SCIENTIFIC_ASSIGNMENT("Scientific Assignment"){
        @Override
        public String getAssignmentName() {
            return "Scientific Assignment";
        }
    },
    GEOGRAPHIC_ASSIGNMENT("Geographic Assignment"){
        @Override
        public String getAssignmentName() {
            return "Geographic Assignment";
        }
    },
    HISTORICAL_ASSIGNMENT("Historical Assignment"){
        @Override
        public String getAssignmentName() {
            return "Historical Assignment";
        }
    };
    private String assignmentName;
    AssignmentName(String assignmentName){
        this.assignmentName = assignmentName;
    }
    public String getAssignmentName(){
        return assignmentName;
    }
}
