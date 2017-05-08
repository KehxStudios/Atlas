package com.kehxstudios.atlas.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.TimeUtils;
import com.kehxstudios.atlas.type.ScreenType;
import com.kehxstudios.atlas.tools.DebugTool;

import java.util.Date;

/**
 * Holds all high-score data for a screen
 */

public class HighScores {

    // Type of screen the high-scores are for
    private ScreenType type;
    // Size of the high-scores list
    private int size;

    // Arrays of the highscore data
    private String[] names;
    private int[] scores;
    private String[] dates;

    // Default constructor only requires ScreenType
    public HighScores(ScreenType type) {
        this.type = type;
        // Populate score data
        loadScores();
    }

    private void loadScores() {
        // Get preferences of type id
        Preferences preferences = Gdx.app.getPreferences(type.getId()+"_HighScores");
        size = preferences.getInteger("size", 5);
        // Initalize arrays
        names = new String[size];
        scores = new int[size];
        dates = new String[size];
        // Cycle though each high-score rank
        for (int i = 0; i < size; i++) {
            // Set high-score data from preferences
            names[i] = preferences.getString(i+"_Name", "-");
            scores[i] = preferences.getInteger(i+"_Score",0);
            dates[i] = preferences.getString(i+"_Date","-");
        }
        DebugTool.log("High Scores Loaded");
    }

    private void saveScores() {
        // Get preferences of type id
        Preferences preferences = Gdx.app.getPreferences(type.getId()+"_HighScores");
        preferences.putInteger("size", size);
        // Cycle through each high-score rank
        for (int i = 0; i < size; i++) {
            // Save high-score data to preferences
            preferences.putString(i+"_Name",names[i]);
            preferences.putInteger(i+"_Score",scores[i]);
            preferences.putString(i+"_Date",dates[i]);
            // Flush to save data
            preferences.flush();
            // Print off saved high-scores
            DebugTool.log(i +"__"+ preferences.getString(i+"_Name", "-") +"__"+ preferences.getInteger(i+"_Score", 0) +"__"+ preferences.getString(i+"_Date", "-"));
        }
        DebugTool.log("High Scores Saved");
    }

    public static void resetScores(ScreenType type) {
        Preferences preferences = Gdx.app.getPreferences(type.getId()+"_HighScores");
        int size = preferences.getInteger("size");
        for (int i = 0; i < size; i++) {
            // Save high-score data to preferences
            preferences.putString(i+"_Name","-");
            preferences.putInteger(i+"_Score",0);
            preferences.putString(i+"_Date","-");
            // Flush to save data
            preferences.flush();
        }
    }

    public void addToHighScores(String name, int score) {
        int scorePosition = size;
        int emptyPosition = size - 1;
        for (int i = size-1; i >= 0; i--) {
            if (scores[i] == 0) {
                emptyPosition = i;
            }
            if (score > scores[i]) {
                scorePosition = i;
            } else {
                break;
            }
        }
        if (scorePosition == size) {
            return;
        }
        for (int i = emptyPosition; i > scorePosition; i--) {
            names[i] = names[i-1];
            scores[i] = scores[i-1];
            dates[i] = dates[i-1];
        }
        names[scorePosition] = name;
        scores[scorePosition] = score;
        dates[scorePosition] = (new Date(TimeUtils.millis())).toString();

        saveScores();
    }

    public int getLowScore() {
        return scores[size-1];
    }

    public int getHighScore() {
        return scores[0];
    }

    public void dispose() {
        saveScores();
    }

    public String getName(int position) {
        if (position >= 0 && position < size) {
            return names[position];
        } else {
            return "Error";
        }
    }

    public int getScore(int position) {
        if (position >= 0 && position < size) {
            return scores[position];
        } else {
            return -1;
        }
    }

    public String getDate(int position) {
        if (position >= 0 && position < size) {
            return dates[position];
        } else {
            return "Error";
        }
    }

    public void reset() {
        names = new String[size];
        scores = new int[size];
        dates = new String[size];
        for (int i = 0; i < size; i++) {
            names[i] = "-";
            scores[i] = 0;
            dates[i] = "-";
        }
        DebugTool.log("High Scores Reset");
        saveScores();
    }

    public ScreenType getType() { return type; }
}
