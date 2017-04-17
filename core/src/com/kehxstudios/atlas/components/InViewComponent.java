package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.main.GameManager;

/**
 * Created by ReidC on 2017-04-16.
 */

public class InViewComponent extends Component {

    private int width, height;
    private Action action;

    public InViewComponent(Entity entity, int width, int height, Action action) {
        super(entity);
        this.width = width;
        this.height = height;
        this.action = action;
    }

    public void check() {
        float camX = GameManager.getInstance().getCamera().position.x;
        float camY = GameManager.getInstance().getCamera().position.y;
        float camWidth = GameManager.getInstance().getCamera().viewportWidth;
        float camHeight = GameManager.getInstance().getCamera().viewportHeight;
        if ((getX() - width/2 > camX - camWidth/2 || getX() + width/2 < camX + camWidth/2) &&
                (getY() - height/2 > camY - camHeight/2 || getY() + height/2 < camY + camHeight/2)) {
            action.trigger();
        }
    }
}
