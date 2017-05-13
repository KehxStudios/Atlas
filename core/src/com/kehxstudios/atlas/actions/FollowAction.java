package com.kehxstudios.atlas.actions;

import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.managers.InputManager;
import com.kehxstudios.atlas.type.ActionType;

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