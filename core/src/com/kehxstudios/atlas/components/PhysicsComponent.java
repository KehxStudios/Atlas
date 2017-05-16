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

package com.kehxstudios.atlas.components;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.tools.DebugTool;

/**
 * Used to process any physics causing movement
 */

public class PhysicsComponent extends Component {

    public Vector2 acceleration;
    public Vector2 maxAcceleration;
    public Vector2 velocity;
    public Vector2 maxVelocity;
    public Rectangle bounds;
    public boolean collidable;
    public boolean collided; // Reset to false on creation

    public PhysicsComponent() {
        acceleration = new Vector2();
        maxAcceleration = new Vector2();
        velocity = new Vector2();
        maxVelocity = new Vector2();
    }

    public Vector2 getAcceleration() { return acceleration; }
    public void setAcceleration(float x, float y) { acceleration.set(x,y); }
    public void addAcceleration(float x, float y) { acceleration.add(x,y); }

    public Vector2 getMaxAcceleration() { return maxAcceleration; }
    public void setMaxAcceleration(float x, float y) { maxAcceleration.set(x,y); }

    public Vector2 getVelocity() { return velocity; }
    public void setVelocity(float x, float y) { velocity.set(x,y); }
    public void setVelocity(Vector2 velocity) { this.velocity = velocity; }
    public void addVelocity(float x, float y) { velocity.add(x,y); }
    public void setXVelocity(float x) { velocity.x = x; }
    public void setYVelocity(float y) { velocity.y = y; }
    public void addXVelocity(float x) { velocity.x += x; }
    public void addYVelocity(float y) { velocity.y += y; }

    public Vector2 getMaxVelocity() { return maxVelocity; }
    public void setMaxVelocity(float x, float y) { maxVelocity.set(x,y); }

    public Rectangle getBounds() {
        bounds.setX(getPosition().x - bounds.width/2);
        bounds.setY(getPosition().y - bounds.height/2);
        return bounds;
    };
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
