package com.bytebrigade.attackoftheschool.gameplay;
import com.bytebrigade.attackoftheschool.helper.enums.SchoolType;

public class Profile {

    // data members
    public static SchoolType playthroughs = SchoolType.ELEMENTARY;
    //0 if in elementary, 1 is in high school, 2 is in college,
    // 3 is continuing, but no increased challenge
    public static int x5T;
    public static int  x2T;
    public static int  x10T;
    public static int FurthestLevel =1;
    public static int CurrentLevel = 1;
    public static int amountOfClickIncreasedUpgrades = 0;
   public static int momUses = 0;
   public static String x2ButtonText;
   public static String x10ButtonText;
   public static String x5ButtonText;
   public static Boolean initialAccess = true;
    //1-1000
    public static  void finishedPlaythrough(){
        playthroughs = switch (playthroughs){
            case ELEMENTARY -> SchoolType.HIGH_SCHOOL;
            case HIGH_SCHOOL -> SchoolType.COLLAGE;
            default -> SchoolType.COLLAGE;
        };
        FurthestLevel = 1;
        CurrentLevel = 1;
        amountOfClickIncreasedUpgrades = 0;
        clickStrength = 10L;
        clickStrengthMultiplier = 1;
        points = 0;
        momUses = 0;
        x2ButtonText ="";
        x5ButtonText ="";
        x10ButtonText = "";
        x2T= 0;
        x10T = 0;
        x5T = 0;


    }

    public static Long clickStrength = 10L;

    public static int clickStrengthMultiplier = 1;
    public static long points = 10000000;


    public static int StudentLoansPoints = 0;

    public static String profileName = null;

}
