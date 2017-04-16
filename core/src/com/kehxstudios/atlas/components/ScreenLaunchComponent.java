package com.kehxstudios.atlas.components;

import com.badlogic.gdx.math.Rectangle;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.main.GameManager;
import com.kehxstudios.atlas.screens.ScreenType;

/**
 * Created by ReidC on 2017-04-13.
 */

public class ScreenLaunchComponent extends Component {

    private ScreenType type;
    private Rectangle bounds;

    public ScreenLaunchComponent(Entity entity, int width, int height, ScreenType type) {
        super(entity);
        this.bounds = new Rectangle(entity.getX() - width/2, entity.getY() - height/2, width, height);
        this.type = type;
    }

    public void hits(float x, float y) {
        if (bounds.contains(x,y)) {
            launch();
        }
    }

    private void launch() {
        GameManager.getInstance().launchNewScreen(type);
    }
}
