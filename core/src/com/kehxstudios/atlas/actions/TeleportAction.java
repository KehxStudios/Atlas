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

    public TeleportAction(Vector2 position, ActionData actionData) {
        this.position = position;
        actionValue = new Vector2(actionData.getFloat("actionValue_x", 0f),
                actionData.getFloat("actionValue_y", 0f));
    }

    @Override
    protected void init() {
        type = ActionType.TELEPORT;
    }

    @Override
    public ActionData getActionData() {
        ActionData actionData = super.getActionData();
        actionData.putFloat("actionValue_x", 0f);
        actionData.putFloat("actionValue_y", 0f);
        return actionData;
    }

    public void setActionValue(Vector2 actionValue) {
        this.actionValue = actionValue;
    }

    @Override
    public void trigger() {
        position.set(actionValue);
    }
}
