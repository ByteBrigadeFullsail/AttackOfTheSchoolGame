package com.bytebrigade.attackoftheschool.gameplay;
import android.content.Context;
import android.util.Log;
import com.bytebrigade.attackoftheschool.helper.enums.SchoolType;

import java.io.*;

public class Profile {

    // Data members
    public static SchoolType playthroughs = SchoolType.ELEMENTARY;
    public static int x5T;
    public static int x2T;
    public static int x10T;
    public static int FurthestLevel = 1;
    public static int CurrentLevel = 1;
    public static int amountOfClickIncreasedUpgrades = 0;
    public static int momUses = 0;
    public static boolean hasTutor = false;
    public static int tutorLevel = 1;
    public static String x2ButtonText = " ";
    public static String x10ButtonText = " ";
    public static String x5ButtonText = " ";
    public static boolean initialAccess = true;
    private static final String FILE_NAME = "profile_data.dat";
    public static long clickStrength = 10L;
    public static int clickStrengthMultiplier = 1;
    public static long points = 0L;
    public static int StudentLoansPoints = 0;
    public static String profileName = "";

    private Profile() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    public static void finishedPlaythrough() {
        playthroughs = switch (playthroughs) {
            case ELEMENTARY -> SchoolType.HIGH_SCHOOL;
            case HIGH_SCHOOL -> SchoolType.COLLEGE;
            default -> SchoolType.COLLEGE;
        };
        initializeDefaults();
    }

    public static void saveAll(Context context) {
        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(playthroughs);
            oos.writeBoolean(hasTutor);
            oos.writeInt(tutorLevel);
            oos.writeInt(FurthestLevel);
            oos.writeInt(CurrentLevel);
            oos.writeInt(amountOfClickIncreasedUpgrades);
            oos.writeLong(clickStrength);
            oos.writeInt(clickStrengthMultiplier);
            oos.writeLong(points);
            oos.writeInt(momUses);
            oos.writeUTF(x2ButtonText != null ? x2ButtonText : "");
            oos.writeUTF(x5ButtonText != null ? x5ButtonText : "");
            oos.writeUTF(x10ButtonText != null ? x10ButtonText : "");
            oos.writeInt(x2T);
            oos.writeInt(x10T);
            oos.writeInt(x5T);
            oos.writeUTF(profileName != null ? profileName : "");

            Log.i("Profile", "Profile data saved successfully.");
        } catch (IOException e) {
            Log.e("Profile", "Error saving profile data", e);
        }
    }

    public static void loadClassFromFile(Context context) {
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            playthroughs = (SchoolType) ois.readObject();
            hasTutor = ois.readBoolean();
            tutorLevel = ois.readInt();
            FurthestLevel = ois.readInt();
            CurrentLevel = ois.readInt();
            amountOfClickIncreasedUpgrades = ois.readInt();
            clickStrength = ois.readLong();
            clickStrengthMultiplier = ois.readInt();
            points = ois.readLong();
            momUses = ois.readInt();
            x2ButtonText = ois.readUTF();
            x5ButtonText = ois.readUTF();
            x10ButtonText = ois.readUTF();
            x2T = ois.readInt();
            x10T = ois.readInt();
            x5T = ois.readInt();
            profileName = ois.readUTF();

            Log.i("Profile", "Profile data loaded successfully.");
        } catch (FileNotFoundException e) {
            Log.w("Profile", "Profile data file not found. Initializing with default values.");
            initializeDefaults();
            saveAll(context); // Save default values to file
        } catch (IOException | ClassNotFoundException e) {
            Log.e("Profile", "Error loading profile data. Initializing with default values.", e);
            initializeDefaults();
            saveAll(context); // Save default values to file
        }
    }

    private static void initializeDefaults() {
        playthroughs = SchoolType.ELEMENTARY;
        hasTutor = false;
        tutorLevel = 1;
        FurthestLevel = 1;
        CurrentLevel = 1;
        amountOfClickIncreasedUpgrades = 0;
        clickStrength = 10L;
        clickStrengthMultiplier = 1;
        points = 0L;
        momUses = 0;
        x2ButtonText = " ";
        x5ButtonText = " ";
        x10ButtonText = " ";
        x2T = 0;
        x10T = 0;
        x5T = 0;
        profileName = "";
    }

    public static void updateMomUses(int amount){
        if (momUses + amount > 4) {
            momUses = 5;
        } else if (momUses + amount < 0) {
            momUses = 0;
        } else {
            momUses += amount;
        } // Figure out the "indexing" it seems like the text on the button on the assignment screen skips a click.
    }
}
