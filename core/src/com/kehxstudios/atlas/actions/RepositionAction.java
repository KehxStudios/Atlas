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

/**
 * Used to reposition an Entity when triggered
 */

public class RepositionAction extends Action {

    private Vector2 position;
    private Vector2 actionValue;
    private boolean teleportToActionValue;

    public Vector2 getPosition() { return position; }
    public void setPosition(Vector2 position) { this.position = position; }

    public Vector2 getActionValue() { return actionValue; }
    public void setActionValue(float x, float y) { actionValue.set(x,y); }
    public void addActionValue(float x, float y) { actionValue.add(x,y); }
    
    public boolean isTeleportToActionValue() { return teleportToActionValue; }
    public void setTeleportToActionValue(boolean value) { teleportToActionValue = value; }

    @Override
    public void trigger() {
        if (teleportToActionValue) {
            position.set(actionValue);
        } else {
            position.add(actionValue);
        }
    }
}
