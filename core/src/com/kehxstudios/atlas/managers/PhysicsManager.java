package com.kehxstudios.atlas.managers;

import com.kehxstudios.atlas.components.CollisionComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.ErrorTool;
import com.kehxstudios.atlas.type.ComponentType;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-06.
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

    // ArrayList for all PhysicsComponents created
    private ArrayList<PhysicsComponent> physicsComponents;
    
    // ArrayList's for all CollisionComponents created based on if static or dynamic position
    private ArrayList<CollisionComponent> staticCollisionComponents;
    private ArrayList<CollisionComponent> dynamicCollisionComponents;

    // Constructor
    private PhysicsManager() {
        super();
        setup();
    }
    
    // Initalize @physicsComponents
    @Override
    protected void setup() {
        physicsComponents = new ArrayList<PhysicsComponent>();
        staticCollisionComponents = new ArrayList<CollisionComponent>();
        dynamicCollisionComponents = new ArrayList<CollisionComponent>();
        DebugTool.log("PhysicsManager_setup: Complete");
    }
    
    // Called to update all PhysicsComponents and then check for collision
    @Override
    public void tick(float delta) {
        for (PhysicsComponent physics : physicsComponents) {
            if (physics.isEnabled()) {
                //physics.getVelocity().set(physics.getAcceleration());
                //physics.getAcceleration().set(0,0);
                physics.getVelocity().scl(delta);
                physics.movePosition(physics.getVelocity().x, physics.getVelocity().y);
                //physics.setVelocity(0,0);
                physics.getVelocity().scl(1 / delta);
            }
        }
        if (dynamicCollisionComponents.size() > 0) {
            for (CollisionComponent collision : dynamicCollisionComponents) {
                if (collision.isEnabled()) {
                    if (staticCollisionComponents.size() > 0) {
                        for (CollisionComponent staticCollision : staticCollisionComponents) {
                            if (staticCollision.isEnabled() && collision.getBounds().overlaps(staticCollision.getBounds())) {
                                DebugTool.log("Static Collision");
                                collision.trigger();
                                staticCollision.trigger();
                            }
                        }
                    }
                    for (CollisionComponent dynamicCollision : dynamicCollisionComponents) {
                        if (collision != dynamicCollision &&  dynamicCollision.isEnabled() &&
                                collision.getBounds().overlaps(dynamicCollision.getBounds())) {
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
    protected void loadScreenSettings() {
         DebugTool.log("PhysicsManager_loadScreenSettings: Complete");
    }

    // Called when unloading the current screen
    @Override
    protected void removeScreenSettings() {
        DebugTool.log("PhysicsManager_removeScreenSettings: Complete");
    }
    
    // Adds a physics to @physicsComponents
    public void add(PhysicsComponent physics) {
        if (!physicsComponents.contains(physics)) {
            physicsComponents.add(physics);
        } else {
            ErrorTool.log("Failed to add physics to physicsComponents");
        }
    }

    public void add(Component component) {
        if (component.getType() == ComponentType.PHYSICS) {
            PhysicsComponent physics = (PhysicsComponent)component;
            if (!physicsComponents.contains(physics)) {
                physicsComponents.add(physics);
            }
        } else if (component.getType() == ComponentType.COLLISION) {
            CollisionComponent collision = (CollisionComponent)component;
            if (collision.isStaticPosition()) {
                if (!staticCollisionComponents.contains(collision)) {
                    staticCollisionComponents.add(collision);
                }
            } else {
                if (!dynamicCollisionComponents.contains(collision)) {
                    dynamicCollisionComponents.add(collision);
                }
            }
        }
    }

    public void remove(Component component) {

    }

    // Removes a physics from @physicsComponents
    public void remove(PhysicsComponent physics) {
        if (physicsComponents.contains(physics)) {
            physicsComponents.remove(physics);
        } else {
            ErrorTool.log("Failed to find physics in physicsComponents");
        }
    }
}
