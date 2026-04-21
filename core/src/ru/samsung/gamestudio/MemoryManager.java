package ru.samsung.gamestudio;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;

public class MemoryManager {
    public static void saveTableOfRecords(ArrayList<Integer> table) {}

    public static ArrayList<Integer> loadRecordsTable() {}
    private static final Preferences preferences = Gdx.app.getPreferences("User saves");
    public static void saveSoundSettings(boolean isOn) {
        preferences.putBoolean("isSoundOn", isOn);
        preferences.flush();
    }
    public static boolean loadIsSoundOn() {
        return preferences.getBoolean("isSoundOn", true);
    }
    public static void saveMusicSettings(boolean isOn) {
        preferences.putBoolean("isMusicOn", isOn);
        preferences.flush();
    }
    public static boolean loadIsMusicOn() {
        return preferences.getBoolean("isMusicOn", true);
    }

}
