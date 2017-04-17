package com.kehxstudios.atlas.actions;

import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.components.PhysicsComponent;

/**
 * Created by ReidC on 2017-04-16.
 */

public class PhysicsAction extends Action {

    private PhysicsComponent physicsComponent;
    private Vector2 triggerValue;

    public PhysicsAction(PhysicsComponent physicsComponent, Vector2 triggerValue) {
        this.physicsComponent = physicsComponent;
        this.triggerValue = triggerValue;
    }

    public void changeTriggerValue(Vector2 triggerValue) {
        this.triggerValue = triggerValue;
    }

    public void changePhysicsComponent(PhysicsComponent physicsComponent) {
        this.physicsComponent = physicsComponent;
    }

    @Override
    public void trigger() {
        physicsComponent.moveLocation(triggerValue.x, triggerValue.y);
    }
}
