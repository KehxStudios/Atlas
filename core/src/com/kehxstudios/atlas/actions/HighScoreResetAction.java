package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.screens.ScreenType;
import com.kehxstudios.atlas.stats.HighScores;

/**
 * Created by ReidC on 2017-04-16.
 */

public class HighScoreResetAction extends Action {

    private ScreenType screenType;

    public ScreenType getScreenType() { return screenType; }
    public void setScreenType(ScreenType screenType) { this.screenType = screenType; }

    @Override
    public void trigger() {
        HighScores.resetScores(screenType);
    }
}
