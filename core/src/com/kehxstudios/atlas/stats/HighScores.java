package com.kehxstudios.atlas.stats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.TimeUtils;
import com.kehxstudios.atlas.screens.ScreenType;
import com.kehxstudios.atlas.tools.DebugTool;

import java.util.Date;

/**
 * Created by ReidC on 2017-04-13.
 */

public class HighScores {

    private ScreenType type;
    private final int size;

    private String[] names;
    private int[] scores;
    private String[] dates;

    public HighScores(ScreenType type) {
        this.type = type;
        size = 5;
        loadScores();
    }

    private void loadScores() {
        names = new String[size];
        scores = new int[size];
        dates = new String[size];
        Preferences preferences = Gdx.app.getPreferences(type.getId()+"_HighScores");
        for (int i = 0; i < size; i++) {
            names[i] = preferences.getString(i+"_Name", "-");
            scores[i] = preferences.getInteger(i+"_Score",0);
            dates[i] = preferences.getString(i+"_Date","-");
        }
        DebugTool.log("High Scores Loaded");
    }

    private void saveScores() {
        Preferences preferences = Gdx.app.getPreferences(type.getId()+"_HighScores");
        for (int i = 0; i < size; i++) {
            preferences.putString(i+"_Name",names[i]);
            preferences.putInteger(i+"_Score",scores[i]);
            preferences.putString(i+"_Date",dates[i]);
            preferences.flush();
            DebugTool.log(i +"__"+ preferences.getString(i+"_Name", "-") +"__"+ preferences.getInteger(i+"_Score", 0) +"__"+ preferences.getString(i+"_Date", "-"));
        }
        DebugTool.log("High Scores Saved");
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
    }

    public int getLowScore() {
        return scores[size-1];
    }

    public int getHighScore() {
        return scores[0];
    }

    public boolean withinTopTen(int score) {
        if (score > scores[size-1]) {
            return true;
        } else {
            return false;
        }
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
}
