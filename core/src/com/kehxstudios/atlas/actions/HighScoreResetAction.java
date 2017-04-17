package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.stats.HighScores;

/**
 * Created by ReidC on 2017-04-16.
 */

public class HighScoreResetAction extends Action {

    private HighScores highScores;

    public HighScoreResetAction(HighScores highScores) {
        this.highScores = highScores;
    }

    public void changeHighScores(HighScores highScores) {
        this.highScores = highScores;
    }

    @Override
    public void trigger() {
        highScores.reset();
    }
}
