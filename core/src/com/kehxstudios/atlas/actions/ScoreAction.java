package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.screens.AScreen;

/**
 * Created by ReidC on 2017-04-16.
 */

public class ScoreAction extends Action {

    private AScreen screen;
    private int scoreValue;

    public AScreen getScreen() { return screen; }
    public void setScreen(AScreen screen) {
        this.screen = screen;
    }

    public int getScoreValue() { return scoreValue; }
    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    @Override
    public void trigger() {
        screen.addScore(scoreValue);
    }
}
