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

    private Vector2 direction;
    private Action action;

    public Vector2 getDirection() { return direction; }
    public void setDirection() { direction = new Vector2(0,0); }
    public void setDirection(float x, float y) { direction.set(x,y); }
    public void moveDirection(float x, float y) { direction.add(x,y); }

    public Action getAction() { return action; }
    public void setAction(Action action) { this.action = action; }
}
