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
        init();
    }

    public PhysicsAction(PhysicsComponent physicsComponent, ActionData actionData) {
        this.physicsComponent = physicsComponent;
        triggerValue = new Vector2(actionData.getFloat("triggerValue_x", 0f),
                actionData.getFloat("triggerValue_y", 0f));
        init();
    }

    @Override
    protected void init() {
        type = ActionType.PHYSICS;
    }

    public void changeTriggerValue(Vector2 triggerValue) {
        this.triggerValue = triggerValue;
    }

    public ActionData getActionData() {
        ActionData actionData = super.getActionData();
        actionData.putFloat("triggerValue_x", triggerValue.x);
        actionData.putFloat("triggerValue_y", triggerValue.y);
        return actionData;
    }

    @Override
    public void trigger() {
        physicsComponent.moveLocation(triggerValue.x, triggerValue.y);
    }
}
