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

    private PhysicsComponent physicsComponent;
    private Action action;
    private int key;

    public PhysicsComponent getPhysicsComponent() { return physicsComponent; }
    public void setPhysicsComponent(PhysicsComponent physicsComponent) { this.physicsComponent = physicsComponent; }

    public int getKey() {
        return key;
    }
    public void setKey(int key) { this.key = key; }

    public Action getAction() { return action; }
    public void setAction(Action action) { this.action = action; }
}
