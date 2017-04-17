package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.InputManager;

/**
 * Created by ReidC on 2017-04-07.
 */

public class ClickableComponent extends Component {

    private int width;
    private int height;
    private Action action;

    public ClickableComponent(Entity entity, int width, int height, Action action) {
        super(entity);
        this.width = width;
        this.height = height;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void dispose() {
        super.dispose();
        InputManager.getInstance().removeClickable(this);
    }
}
