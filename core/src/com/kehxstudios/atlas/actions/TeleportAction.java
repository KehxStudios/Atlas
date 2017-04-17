package com.kehxstudios.atlas.actions;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by ReidC on 2017-04-16.
 */

public class TeleportAction extends Action {

    private Vector2 position;
    private Vector2 actionValue;

    public TeleportAction(Vector2 position, Vector2 actionValue) {
        this.position = position;
        this.actionValue = actionValue;
    }

    public void setActionValue(Vector2 actionValue) {
        this.actionValue = actionValue;
    }

    @Override
    public void trigger() {
        position.set(actionValue);
    }
}
