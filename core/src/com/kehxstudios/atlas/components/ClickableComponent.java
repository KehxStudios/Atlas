package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.InputManager;
import com.kehxstudios.atlas.tools.DebugTool;

/**
 * Created by ReidC on 2017-04-07.
 */

public class ClickableComponent extends Component {

    private float width;
    private float height;
    private boolean singleTrigger;
    private boolean triggered;
    private Action action;

    public float getWidth() { return width; }
    public void setWidth(float width) { this.width = width; }

    public float getHeight() { return height; }
    public void setHeight(float height) { this.height = height; }

    public boolean isSingleTrigger() {
        return singleTrigger;
    }
    public void setSingleTrigger(boolean singleTrigger) { this.singleTrigger = singleTrigger; }

    public boolean isTriggered() { return triggered; }
    public void setTriggered(boolean triggered) { this.triggered = triggered; }

    public Action getAction() { return action; }
    public void setAction(Action action) { this.action = action;}

    public void trigger() {
        if (!triggered) {
            triggered = true;
            action.trigger();
        }
    }
}
