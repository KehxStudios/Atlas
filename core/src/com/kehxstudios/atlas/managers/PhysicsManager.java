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

package com.kehxstudios.atlas.managers;

import com.kehxstudios.atlas.components.CollisionComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.ErrorTool;
import com.kehxstudios.atlas.type.ComponentType;

import java.util.ArrayList;

/**
 * Used to control any physics movement along with collisions
 */

public class PhysicsManager extends Manager {

    // Holds instance of class, create new if not set
    private static PhysicsManager instance;
    public static PhysicsManager getInstance() {
        if (instance == null) {
            instance = new PhysicsManager();
        }
        return instance;
    }

    // HashMap for all PhysicsComponents created
    private HashMap<int,PhysicsComponent> physicsComponents;
    
    // HashMap's for all CollisionComponents created based on if static or dynamic position
    private HashMap<int,CollisionComponent> staticCollisionComponents;
    private HashMap<int,CollisionComponent> dynamicCollisionComponents;

    // Constructor
    private PhysicsManager() {
        super();
        init();
    }
    
    // Initalize @physicsComponents
    @Override
    protected void init() {
        physicsComponents = new HashMap<int,PhysicsComponent>();
        staticCollisionComponents = new HashMap<int,CollisionComponent>();
        dynamicCollisionComponents = new HashMap<int,CollisionComponent>();
        DebugTool.log("PhysicsManager_init: Complete");
    }
    
    // Called to update all PhysicsComponents and then check for collision
    @Override
    public void tick(float delta) {
        for (PhysicsComponent physics : physicsComponents.values()) {
            if (physics.enabled()) {
                //physics.getVelocity().set(physics.getAcceleration());
                //physics.getAcceleration().set(0,0);
                physics.velocity().scl(delta);
                physics.movePosition(physics.velocity().x, physics.velocity().y);
                //physics.setVelocity(0,0);
                physics.velocity().scl(1 / delta);
            }
        }
        if (dynamicCollisionComponents.size() > 0) {
            for (CollisionComponent collision : dynamicCollisionComponents.values()) {
                if (collision.enabled()) {
                    if (staticCollisionComponents.size() > 0) {
                        for (CollisionComponent staticCollision : staticCollisionComponents.values()) {
                            if (staticCollision.enabled() && collision.bounds().overlaps(staticCollision.bounds())) {
                                DebugTool.log("Static Collision");
                                collision.trigger();
                                staticCollision.trigger();
                            }
                        }
                    }
                    for (CollisionComponent dynamicCollision : dynamicCollisionComponents.values()) {
                        if (collision != dynamicCollision &&  dynamicCollision.enabled() &&
                                collision.bounds().overlaps(dynamicCollision.bounds())) {
                            DebugTool.log("Dynamic Collision");
                            collision.trigger();
                        }
                    }
                }
            }
        }
    }
    
    // Called when loading a new screen
    @Override
    protected void loadSettings() {
         DebugTool.log("PhysicsManager_loadSettings: Complete");
    }

    // Called when unloading the current screen
    @Override
    protected void removeSettings() {
        DebugTool.log("PhysicsManager_removeSettings: Complete");
    }
    
    public void add(Component component) {
        if (component.getType() == ComponentType.PHYSICS) {
            PhysicsComponent physics = (PhysicsComponent)component;
            if (!physicsComponents.contains(physics)) {
                physicsComponents.put(physics.id, physics);
            }
        } else if (component.getType() == ComponentType.COLLISION) {
            CollisionComponent collision = (CollisionComponent)component;
            if (collision.isStaticPosition()) {
                if (!staticCollisionComponents.contains(collision)) {
                    staticCollisionComponents.put(collision.id, collision);
                }
            } else {
                if (!dynamicCollisionComponents.contains(collision)) {
                    dynamicCollisionComponents.put(collision.id, collision);
                }
            }
        }
    }

    public void remove(Component component) {
        if (component.getType() == ComponentType.PHYSICS) {
            PhysicsComponent physics = (PhysicsComponent)component;
            if (physicsComponents.contains(physics)) {
                physicsComponents.values().remove(physics);
            }
        } else if (component.getType() == ComponentType.COLLISION) {
            CollisionComponent collision = (CollisionComponent)component;
            if (collision.isStaticPosition()) {
                if (staticCollisionComponents.contains(collision)) {
                    staticCollisionComponents.values().remove(collision);
                }
            } else {
                if (dynamicCollisionComponents.contains(collision)) {
                    dynamicCollisionComponents.values().remove(collision);
                }
            }
        }
    }
}
