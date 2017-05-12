package com.kehxstudios.atlas.components;

import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.type.ComponentType;

/**
 * Created by ReidC on 2017-04-06.
 */

public class CollisionComponent extends Component {

    private float width, height;
    private Action action;
    private boolean singleTrigger, triggered;
    
    public CollisionComponent() {
        super();
        width = 0;
        height = 0;
        singleTrigger = false;
        triggered = false;
    }
    
    public void setWidth(float width) { this.width = width; }
    public float getWidth() { return width; }
    
    public void setHeight(float height) { this.height = height; }
    public float getHeight() { return height; }
    
    public void setAction(Action action) { this.action = action; }
    public Action getAction() { return action; }
    
    public void setSingleTrigger(boolean singleTrigger) { this.singleTrigger = singleTrigger; }
    
    public void trigger() {
        if (!singleTrigger || singleTrigger && !triggered) {
            action.trigger();
            triggered = true;
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle(getPosition().x - width/2, getPosition().y - height/2, width, height);
    }
}
