package com.kehxstudios.atlas.actions;

/**
 * Created by ReidC on 2017-04-16.
 */

public abstract class Action {

    protected ActionType type;

    public Action() { }

    public Action(ActionData actionData) { }

    public abstract void trigger();
    protected abstract void init();

    public ActionData getActionData() {
        return new ActionData(type.getId());
    }

    public ActionType getActionType() { return type; }
}
