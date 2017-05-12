package com.kehxstudios.atlas.components;

import com.badlogic.gdx.math.Rectangle;
import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.type.ComponentType;

/**
 * Created by ReidC on 2017-04-06.
 */

public class CollisionComponent extends Component {

    private float width, height;
    private Rectangle bounds;
    private Action action;
    private boolean staticPosition, singleTrigger, triggered;
    
    public CollisionComponent() {
        super();
        type = ComponentType.COLLISION;
        width = 0;
        height = 0;
        staticPosition = false;
        singleTrigger = false;
        triggered = false;
    }
    
    public void setWidth(float width) { this.width = width; }
    public float getWidth() { return width; }
    
    public void setHeight(float height) { this.height = height; }
    public float getHeight() { return height; }
    
    public void setBounds(Rectangle bounds) { this.bounds = bounds; }
    public void updateBounds() {
        bounds.x = getPosition().x;
        bounds.y = getPosition().y;
    }
    public Rectangle getBounds() { 
        if (!staticPosition) {
            updateBounds();
        }
        return bounds; 
    }
    
    public void setAction(Action action) { this.action = action; }
    public Action getAction() { return action; }
    
    public boolean isStaticPosition() { return staticPosition; }
    public void setStaticPosition(boolean staticPosition) { this.staticPosition = staticPosition; }
    public void setSingleTrigger(boolean singleTrigger) { this.singleTrigger = singleTrigger; }
    public void setTriggered(boolean triggered) { this.triggered = triggered; }
    
    public void trigger() {
        if (!singleTrigger || singleTrigger && !triggered) {
            action.trigger();
            triggered = true;
        }
    }
}
