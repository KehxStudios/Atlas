package com.kehxstudios.atlas.actions;

/**
 * Created by ReidC on 2017-04-16.
 */

public abstract class Action {

    protected ActionType type;

    public ActionType getType() { return type; }
    public void setType(ActionType type) { this.type = type; }

    public abstract void trigger();
}
