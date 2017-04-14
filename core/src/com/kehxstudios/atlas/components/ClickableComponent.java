package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.InputManager;
import com.kehxstudios.atlas.tools.DebugTool;

/**
 * Created by ReidC on 2017-04-07.
 */

public class ClickableComponent extends Component {

    private int width;
    private int height;
    private PhysicsComponent physics;

    public ClickableComponent(Entity entity, PhysicsComponent physics, int width, int height) {
        super(entity);
        this.physics = physics;
        this.width = width;
        this.height = height;
        type = ComponentType.CLICKABLE;
        InputManager.getInstance().addClickable(this);
    }

    public void trigger() {
        if (physics != null) {
            physics.velocity.y = 250;
        }
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
        DebugTool.log("ClickableComponent disposal");
        InputManager.getInstance().removeClickable(this);
    }
}
