package com.bytebrigade.attackoftheschool.helper;

import com.bytebrigade.attackoftheschool.gameplay.Clickable;
import com.bytebrigade.attackoftheschool.gameplay.assignment.Assignment;
import com.bytebrigade.attackoftheschool.helper.enums.HelperType;
import com.bytebrigade.attackoftheschool.helper.enums.SchoolType;

import java.util.List;

public class Helper {
    private HelperType helperType;
    private Integer noOfAssignments;
    private Assignment currentAssignment;

    public Helper(SchoolType schoolType, Assignment assignment) {
        this.currentAssignment = assignment;
        switch (schoolType) {
            case ELEMENTARY:
                this.helperType = HelperType.MOM;
                this.noOfAssignments = 1;
                break;
            case HIGH_SCHOOL:
                this.helperType = HelperType.ANSWER_SHEET;
                this.noOfAssignments = 5;
                break;
            case COLLAGE:
                this.helperType = HelperType.PGT_BOTTER;
                this.noOfAssignments = 10;
                break;
        }
    }

    public HelperType getHelperType() {
        return helperType;
    }

    public void setHelperType(HelperType helperType) {
        this.helperType = helperType;
    }

    public void activeHelper(SchoolType schoolType, Clickable clickable) {
        switch (schoolType) {
            case ELEMENTARY:
                setHelperType(HelperType.MOM);
                activeHelper(clickable);
                break;
            case HIGH_SCHOOL:
                setHelperType(HelperType.ANSWER_SHEET);
                activeHelper(clickable);
                break;
            case COLLAGE:
                setHelperType(HelperType.PGT_BOTTER);
                activeHelper(clickable);
                break;

        }
    }

    private void activeHelper(Clickable clickable) {
        for (int i = 0; i < noOfAssignments; i++) {
            //this.currentAssignment.progressToNextLevel();
        }
    }

}