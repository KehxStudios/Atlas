/*******************************************************************************
 * Copyright 2017 See AUTHORS file.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
 * associated documentation files (the "Software"), to deal in the Software without restriction, 
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial 
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/

package com.kehxstudios.atlas.components;

import com.badlogic.gdx.math.Rectangle;
import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.type.ComponentType;

/**
 * Used to trigger an Action when collision happens
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
        bounds.x = getPosition().x - width/2;
        bounds.y = getPosition().y - height/2;
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
    public boolean isTriggered() { return triggered; }
    
    public void trigger() {
        if (!singleTrigger || singleTrigger && !triggered) {
            action.trigger();
            triggered = true;
        }
    }
}
