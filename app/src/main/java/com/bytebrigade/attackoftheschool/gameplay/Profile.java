package com.bytebrigade.attackoftheschool.gameplay;
import com.bytebrigade.attackoftheschool.helper.enums.SchoolType;

public class Profile {

    // data members
    public static SchoolType playthroughs = SchoolType.ELEMENTARY;
    //0 if in elementary, 1 is in high school, 2 is in college,
    // 3 is continuing, but no increased challenge

    public static int FurthestLevel =1;
    public static int CurrentLevel = 1;
    public static int amountOfClickIncreasedUpgrades = 0;
    //1-1000

    public static Long clickStrength = 10L;
    public static int clickStrengthMultiplier = 1;
    public static long points = 0;


    public static int StudentLoansPoints = 0;

    public static String profileName;

}
