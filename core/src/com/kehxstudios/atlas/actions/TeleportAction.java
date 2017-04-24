package com.kehxstudios.atlas.actions;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by ReidC on 2017-04-16.
 */

public class TeleportAction extends Action {

    private Vector2 position;
    private Vector2 teleportPosition;

    public Vector2 getPosition() { return position; }
    public void setPosition(Vector2 position) { this.position = position; }

    public Vector2 getTeleportPosition() { return teleportPosition; }
    public void setTeleportPosition(Vector2 teleportPosition) { this.teleportPosition = teleportPosition; }

    @Override
    public void trigger() {
        position.set(teleportPosition);
    }
}
