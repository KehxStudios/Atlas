package com.kehxstudios.atlas.components;

import com.badlogic.gdx.Input.Keys;
import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.actions.PhysicsAction;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.managers.InputManager;
import com.kehxstudios.atlas.tools.DebugTool;

/**
 * Created by ReidC on 2017-04-09.
 */

public class ButtonComponent extends Component {

    private PhysicsComponent physics;
    private Action action;
    private int key;

    public ButtonComponent(Entity entity) {
        super(entity);
    }

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

    public void setPhysics(PhysicsComponent physics) { this.physics = physics; }

    public int getKey() {
        return key;
    }

    public void setKey(int value) { key = value; }

    public Action getAction() { return action; }

    @Override
    public void dispose() {
        super.dispose();
        DebugTool.log("ButtonComponent disposal");
        InputManager.getInstance().removeButton(this);
    }
}
