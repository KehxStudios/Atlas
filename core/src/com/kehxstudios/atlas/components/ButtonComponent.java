package com.kehxstudios.atlas.components;

import com.badlogic.gdx.Input.Keys;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.managers.InputManager;
import com.kehxstudios.atlas.tools.DebugTool;

/**
 * Created by ReidC on 2017-04-09.
 */

public class ButtonComponent extends Component {

    private PhysicsComponent physics;
    private int key;

    public ButtonComponent(Entity entity, PhysicsComponent physics, int key) {
        super(entity);
        this.physics = physics;
        this.key = key;
        type = ComponentType.BUTTON;
        InputManager.getInstance().addButton(this);
    }

    public void trigger() {
        if (physics != null) {
            physics.velocity.y = 250;
        }
    }

    public int getKey() {
        return key;
    }

    @Override
    public void dispose() {
        super.dispose();
        DebugTool.log("ButtonComponent disposal");
        InputManager.getInstance().removeButton(this);
    }
}
