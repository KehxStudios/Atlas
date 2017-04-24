package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.screens.ScreenType;
import com.kehxstudios.atlas.stats.HighScores;

/**
 * Created by ReidC on 2017-04-16.
 */

public class HighScoreResetAction extends Action {

    private ScreenType screenType;

    public HighScoreResetAction(ScreenType screenType) {
        this.screenType = screenType;
    }

    public HighScoreResetAction(ActionData actionData) {
        super(actionData);
        screenType = ScreenType.getTypeById(actionData.getString("screenType", "intro"));
    }

    public void changeScreenType(ScreenType screenType) {
        this.screenType = screenType;
    }

    @Override
    protected void init() {
        type = ActionType.HIGH_SCORE_RESET;
    }

    @Override
    public void trigger() {
        HighScores.resetScores(screenType);
    }

    public ActionData getActionData() {
        ActionData actionData = super.getActionData();
        actionData.putString("screenType", screenType.getId());
        return actionData;
    }
}
