package com.kehxstudios.atlas.components;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.tools.DebugTool;

/**
 * Created by ReidC on 2017-04-06.
 */

public class PhysicsComponent extends Component {

    public Vector2 velocity;
    public Vector2 speeds;
    public Rectangle bounds;
    public boolean collidable;

    public PhysicsComponent(Entity entity, int width, int height, float xSpeed, float ySpeed, boolean collidable) {
        super(entity);
        this.collidable = collidable;
        type = ComponentType.PHYSICS;
        velocity = new Vector2(0,0);
        speeds = new Vector2(xSpeed, ySpeed);
        bounds = new Rectangle(entity.getX() - width/2, entity.getY() - height/2, width, height);
        PhysicsManager.getInstance().add(this);
    }

    public void updateBounds() {
        bounds.x = entity.getX() - bounds.width/2;
        bounds.y = entity.getY() - bounds.height/2;
    }


    @Override
    public void dispose() {
        super.dispose();
        DebugTool.log("PhysicsComponent disposal");
        PhysicsManager.getInstance().remove(this);
    }
}
