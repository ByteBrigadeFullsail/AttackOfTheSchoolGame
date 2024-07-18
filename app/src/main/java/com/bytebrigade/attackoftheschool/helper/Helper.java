package com.bytebrigade.attackoftheschool.helper;

import com.bytebrigade.attackoftheschool.gameplay.Clickable;
import com.bytebrigade.attackoftheschool.gameplay.assignment.Assignment;
import com.bytebrigade.attackoftheschool.helper.enums.HelperType;
import com.bytebrigade.attackoftheschool.helper.enums.SchoolType;

import java.util.List;

public class Helper {
    private HelperType helperType;
    private Integer noOfAssignments;
    private List<Assignment> currentAssignment;

    public Helper(SchoolType schoolType, List<Assignment> assignment) {
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

    public void activeHelper(SchoolType schoolType) {
        switch (schoolType) {
            case ELEMENTARY:
                setHelperType(HelperType.MOM);
                break;
            case HIGH_SCHOOL:
                setHelperType(HelperType.ANSWER_SHEET);
                break;
            case COLLAGE:
                setHelperType(HelperType.PGT_BOTTER);
                break;

        }
    }

    private void activeHelper(Clickable clickable) {

    }

}
