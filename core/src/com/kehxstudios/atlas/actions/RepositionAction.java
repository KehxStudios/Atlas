package com.kehxstudios.atlas.actions;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by ReidC on 2017-04-16.
 */

public class RepositionAction extends Action {

    private Vector2 position;
    private Vector2 actionValue;

    public RepositionAction(Vector2 position, Vector2 actionValue) {
        this.position = position;
        this.actionValue = actionValue;
        init();
    }

    public RepositionAction(Vector2 position, ActionData actionData) {
        this.position = position;
        actionValue = new Vector2(actionData.getFloat("actionValue_x", 0f),
                actionData.getFloat("actionValue_y", 0f));
        init();
    }

    @Override
    protected void init() {
        type = ActionType.REPOSITION;
    }

    public void setActionValue(Vector2 actionValue) {
        this.actionValue = actionValue;
    }

    public ActionData getActionData() {
        ActionData actionData = super.getActionData();
        actionData.putFloat("actionValue_x", actionValue.x);
        actionData.putFloat("actionValue_y", actionValue.y);
        return actionData;
    }

    @Override
    public void trigger() {
        position.add(actionValue);
    }
}
