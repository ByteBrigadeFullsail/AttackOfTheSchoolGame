package com.bytebrigade.attackoftheschool.helper;

import com.bytebrigade.attackoftheschool.helper.enums.HelperType;
import com.bytebrigade.attackoftheschool.helper.enums.SchoolType;

public class Helper {
    private HelperType helperType;
    private Integer noOfAssignments;

    public Helper(SchoolType schoolType) {
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

    public void activeHelper(Clickable clickable) {
        switch (helperType) {
            case MOM:

        }
    }

}
