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
import com.kehxstudios.atlas.managers.InputManager;
import com.kehxstudios.atlas.type.ActionType;

/**
 * Used to make Vector2 follow the input location on trigger
 */

public class FollowAction extends Action {

    private Vector2 position;
    private boolean verticalAllowed, horizontalAllowed;

    public FollowAction() {
        type = ActionType.FOLLOW;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setVerticalAllowed(boolean value) {
        verticalAllowed = value;
    }

    public void setHorizontalAllowed(boolean value) {
        horizontalAllowed = value;
    }

    public void trigger() {
        Vector2 clicked = InputManager.getInstance().getClickedPosition();
        if (verticalAllowed && horizontalAllowed) {
            position.set(clicked.x, clicked.y);
        } else if (verticalAllowed) {
            position.x = clicked.x;
        } else if (horizontalAllowed) {
            position.y = clicked.y;
        }
    }
}
