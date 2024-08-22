package com.bytebrigade.attackoftheschool.gameplay;
import com.bytebrigade.attackoftheschool.helper.enums.SchoolType;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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

    // Jason save-load section
    private static final String PREFS_NAME = "prefs_name";
    private static final String KEY_CURRENT_LEVEL = "current_level";
    private static final String KEY_POINTS = "points";
    private static final String KEY_CLICK_STRENGTH = "click_strength";
    private static final String KEY_CLICK_STRENGTH_MULTIPLIER = "click_strength_multiplier";
    private static final String KEY_FURTHEST_LEVEL = "furthest_level";
    private static final String KEY_MOM_USES = "mom_uses";
    private static final String KEY_AMOUNT_OF_UPGRADES = "amount_of_upgrades";


    // TODO: Determine if these are important and if so create key-value pairs for them.
//    x2ButtonText ="";
//    x5ButtonText ="";
//    x10ButtonText = "";
//    x2T= 0;
//    x10T = 0;
//    x5T = 0;


    private SharedPreferences sharedPreferences;

    public Profile(Context context){
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveProgress(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_CURRENT_LEVEL, CurrentLevel);
        editor.putLong(KEY_POINTS, points);
        editor.putLong(KEY_CLICK_STRENGTH, clickStrength);
        editor.putInt(KEY_CLICK_STRENGTH_MULTIPLIER, clickStrengthMultiplier);
        editor.putInt(KEY_FURTHEST_LEVEL, FurthestLevel);
        editor.putInt(KEY_MOM_USES, momUses);
        editor.putInt(KEY_AMOUNT_OF_UPGRADES, amountOfClickIncreasedUpgrades);
        editor.apply();
        Log.d("Profile", "Saving progress: CurrentLevel = " + CurrentLevel);
    }

    public void loadProgress(){
        CurrentLevel = sharedPreferences.getInt(KEY_CURRENT_LEVEL, CurrentLevel);
        points = sharedPreferences.getLong(KEY_POINTS, points);
        clickStrength = sharedPreferences.getLong(KEY_CLICK_STRENGTH, clickStrength);
        clickStrengthMultiplier = sharedPreferences.getInt(KEY_CLICK_STRENGTH_MULTIPLIER, clickStrengthMultiplier);
        FurthestLevel = sharedPreferences.getInt(KEY_FURTHEST_LEVEL, FurthestLevel);
        momUses = sharedPreferences.getInt(KEY_MOM_USES, momUses);
        amountOfClickIncreasedUpgrades = sharedPreferences.getInt(KEY_AMOUNT_OF_UPGRADES, amountOfClickIncreasedUpgrades);
        Log.d("Profile", "Loaded progress: CurrentLevel = " + CurrentLevel);
    }
}
