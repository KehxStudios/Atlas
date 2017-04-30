package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.actions.Action;

/**
 * Created by ReidC on 2017-04-16.
 */

public class InViewComponent extends Component {

    private float width, height;
    private Action action;

    public float getWidth() { return width; }
    public void setWidth(float width) { this.width = width; }

    public float getHeight() { return height; }
    public void setHeight(float height) { this.height = height; }

    public Action getAction() { return action; }
    public void setAction(Action action) { this.action = action; }
}
