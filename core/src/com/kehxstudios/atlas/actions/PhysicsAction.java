package com.kehxstudios.atlas.actions;

import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.components.PhysicsComponent;

/**
 * Created by ReidC on 2017-04-16.
 */

public class PhysicsAction extends Action {

    private PhysicsComponent physicsComponent;
    private Vector2 triggerValue;

    public PhysicsAction() {
        triggerValue = new Vector2();
    }

    public PhysicsComponent getPhysicsComponent() { return physicsComponent; }
    public void setPhysicsComponent(PhysicsComponent physicsComponent) { this.physicsComponent = physicsComponent; }

    public Vector2 getTriggerValue() { return triggerValue; }
    public void setTriggerValue(float x, float y) { triggerValue.set(x,y); }
    public void addTriggerValue(float x, float y) { triggerValue.add(x,y); }

    @Override
    public void trigger() {
        physicsComponent.addAcceleration(triggerValue.x, triggerValue.y);
    }
}
