package com.kehxstudios.atlas.actions;

/**
 * Created by ReidC on 2017-04-16.
 */

public abstract class Action {

    protected com.kehxstudios.atlas.type.ActionType type;

    public com.kehxstudios.atlas.type.ActionType getType() { return type; }
    public void setType(com.kehxstudios.atlas.type.ActionType type) { this.type = type; }

    public abstract void trigger();
}
