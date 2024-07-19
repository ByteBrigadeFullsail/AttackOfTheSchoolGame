package com.bytebrigade.attackoftheschool.gameplay.assignment.enums;

public enum AssignmentName {
    MATH_ASSIGNMENT("Math Assignment"){
        @Override
        public String getAssignmentName() {
            return "English";
        }
    },
    ENGLISH_ASSIGNMENT("English Assignment"){
        @Override
        public String getAssignmentName() {
            return "Math";
        }
    },
    SCIENTIFIC_ASSIGNMENT("Scientific Assignment"){
        @Override
        public String getAssignmentName() {
            return "P.E";
        }
    },
    GEOGRAPHIC_ASSIGNMENT("Geographic Assignment"){
        @Override
        public String getAssignmentName() {
            return "Science";
        }
    },
    HISTORICAL_ASSIGNMENT("Historical Assignment"){
        @Override
        public String getAssignmentName() {
            return "History";
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
