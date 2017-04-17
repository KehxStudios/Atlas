package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.screens.ScreenType;

/**
 * Created by ReidC on 2017-04-16.
 */

public class ScoreAction extends Action {

    private AScreen screen;
    private int actionValue;

    public ScoreAction(AScreen screen, int actionValue) {
        this.screen = screen;
        this.actionValue = actionValue;
        init();
    }

    public ScoreAction(AScreen screen, ActionData actionData) {
        this.screen = screen;
        actionValue = actionData.getInt("actionValue", 0);
        init();
    }

    protected void init() {
        type = ActionType.SCORE;
    }

    public ActionData getActionData() {
        ActionData actionData = super.getActionData();
        actionData.putInt("actionValue", actionValue);
        return actionData;
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
