package com.kehxstudios.atlas.actions;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by ReidC on 2017-04-16.
 */

public class RepositionAction extends Action {

    private Vector2 position;
    private Vector2 actionValue;
    private boolean teleportToActionValue;

    public Vector2 getPosition() { return position; }
    public void setPosition(Vector2 position) { this.position = position; }

    public Vector2 getActionValue() { return actionValue; }
    public void setActionValue(float x, float y) { actionValue.set(x,y); }
    public void addActionValue(float x, float y) { actionValue.add(x,y); }
    
    public boolean isTeleportToActionValue() { return teleportToActionValue; }
    public void setTeleportToActionValue(boolean value) { teleportToActionValue = value; }

    @Override
    public void trigger() {
        if (teleportToActionValue) {
            position.set(actionValue);
        } else {
            position.add(actionValue);
        }
    }
}
