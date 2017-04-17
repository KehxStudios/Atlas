package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.screens.AScreen;

/**
 * Created by ReidC on 2017-04-16.
 */

public class ScoreAction extends Action {

    private AScreen screen;
    private int actionValue;

    public ScoreAction(AScreen screen, int actionValue) {
        this.screen = screen;
        this.actionValue = actionValue;
    }

    public void setScreen(AScreen screen) {
        this.screen = screen;
    }

    public void setActionValue(int actionValue) {
        this.actionValue = actionValue;
    }

    @Override
    public void trigger() {
        screen.updateScore(actionValue);
    }
}
