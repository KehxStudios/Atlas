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

    public Vector2 acceleration;
    public Vector2 maxAcceleration;
    public Vector2 velocity;
    public Vector2 maxVelocity;
    public Rectangle bounds;
    public boolean collidable;
    public boolean collided; // Reset to false on creation

    public Vector2 getAcceleration() { return acceleration; }
    public void setAcceleration(float x, float y) { acceleration.set(x,y); }
    public void addAcceleration(float x, float y) { acceleration.add(x,y); }

    public Vector2 getMaxAcceleration() { return maxAcceleration; }
    public void setMaxAcceleration(float x, float y) { maxAcceleration.set(x,y); }

    public Vector2 getVelocity() { return velocity; }
    public void setVelocity(float x, float y) { velocity.set(x,y); }
    public void addVelocity(float x, float y) { velocity.add(x,y); }

    public Vector2 getMaxVelocity() { return maxVelocity; }
    public void setMaxVelocity(float x, float y) { maxVelocity.set(x,y); }

    public Rectangle getBounds() { return bounds; };
    public void setBounds(Rectangle bounds) { this.bounds = bounds; }
    public void moveBounds(float x, float y) {
        bounds.setX(bounds.x + x);
        bounds.setY(bounds.y + y);
    }

    public boolean isCollidable() { return collidable; }
    public void setCollidable(boolean collidable) { this.collidable = collidable; }

    public boolean hasCollided() { return collided; }
    public void setCollided(boolean collided) { this.collided = collided; }
}
