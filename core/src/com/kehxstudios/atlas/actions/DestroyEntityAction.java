package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.entities.Entity;

/**
 * Created by ReidC on 2017-04-16.
 */

public class DestroyEntityAction extends Action {

    private Entity entity;
    private float delay;

    public DestroyEntityAction(Entity entity, float delay) {
        this.entity = entity;
        this.delay = delay;
        init();
    }

    public DestroyEntityAction(Entity entity, ActionData actionData) {
        super(actionData);
        this.entity = entity;
        delay = actionData.getFloat("delay", 0f);
        init();
    }

    @Override
    protected void init() {
        type = ActionType.DESTROY_ENTITY;
    }

    @Override
    public void trigger() {
        entity.destroy();
    }

    public ActionData getActionData() {
        ActionData actionData = super.getActionData();
        actionData.putString("entityId", entity.getId());
        actionData.putFloat("delay", delay);
        return actionData;
    }
}
