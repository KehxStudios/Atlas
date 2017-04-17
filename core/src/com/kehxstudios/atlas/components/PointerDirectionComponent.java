package com.kehxstudios.atlas.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.actions.PhysicsAction;
import com.kehxstudios.atlas.entities.Entity;

/**
 * Created by ReidC on 2017-04-16.
 */

public class PointerDirectionComponent extends Component {

    private PhysicsAction physicsAction;
    private Vector2 direction;

    public PointerDirectionComponent(Entity entity, PhysicsAction physicsAction) {
        super(entity);
        this.physicsAction = physicsAction;
        direction = new Vector2(0,0);
    }

    public void trigger() {
        direction = new Vector2(Gdx.input.getX(), Gdx.input.getY()).sub(entity.getLocation());
        physicsAction.changeTriggerValue(direction);
        physicsAction.trigger();
    }
}
