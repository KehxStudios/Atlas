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

package com.kehxstudios.atlas.actions;

import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.components.PhysicsComponent;

/**
 * Used to change PhysicsComponent when triggered
 */

public class PhysicsAction extends Action {

    private PhysicsComponent physicsComponent;
    private Vector2 triggerValue;

    public PhysicsAction() {
        triggerValue = new Vector2();
    }

    public PhysicsComponent getPhysicsComponent() { return physicsComponent; }
    public void setPhysicsComponent(PhysicsComponent physicsComponent) { this.physicsComponent = physicsComponent; }

    public Vector2 getTriggerValue() { return triggerValue; }
    public void setTriggerValue(float x, float y) { triggerValue.set(x,y); }
    public void addTriggerValue(float x, float y) { triggerValue.add(x,y); }

    @Override
    public void trigger() {
        physicsComponent.setVelocity(triggerValue.x, triggerValue.y);
    }
}
