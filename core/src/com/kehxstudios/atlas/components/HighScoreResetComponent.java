package com.kehxstudios.atlas.components;

import com.badlogic.gdx.math.Rectangle;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.main.GameManager;
import com.kehxstudios.atlas.screens.ScreenLoader;
import com.kehxstudios.atlas.screens.ScreenType;
import com.kehxstudios.atlas.stats.HighScores;

/**
 * Created by ReidC on 2017-04-13.
 */

public class HighScoreResetComponent extends Component {

    private ScreenType type;
    private Rectangle bounds;

    public HighScoreResetComponent(Entity entity, Rectangle bounds, ScreenType type) {
        super(entity);
        this.bounds = bounds;
        this.type = type;
    }

    public void hits(float x, float y) {
        if (bounds.contains(x,y)) {
            reset();
        }
    }

    private void reset() {
        HighScores highScores = new HighScores(type);
        highScores.reset();
    }
}
