package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.InputManager;

/**
 * Created by ReidC on 2017-04-07.
 */

public class ClickableComponent extends Component {

    private float width;
    private float height;
    private boolean singleTrigger;
    private Action action;

    public ClickableComponent(Entity entity) {
        super(entity);
    }

    public ClickableComponent(Entity entity, float width, float height, boolean singleTrigger, Action action) {
        super(entity);
        this.width = width;
        this.height = height;
        this.singleTrigger = singleTrigger;
        this.action = action;
        init();
    }

    @Override
    protected void init() {
        type = ComponentType.CLICKABLE;
        super.init();
        InputManager.getInstance().addClickable(this);
    }

    public void trigger() {
        action.trigger();
    }

    public boolean isSingleTrigger() {
        return singleTrigger;
    }

    public void setSingleTrigger(boolean value) { singleTrigger = value; }

    public float getWidth() {
        return width;
    }

    public void setWidth(float value) { width = value; }

    public float getHeight() {
        return height;
    }

    public void setHeight(float value) { height = value; }

    public Action getAction() { return action; }

    @Override
    public void dispose() {
        super.dispose();
        InputManager.getInstance().removeClickable(this);
    }
}
