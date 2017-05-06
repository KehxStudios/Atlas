package com.kehxstudios.atlas.managers;

import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.tools.DebugTool;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-06.
 */

public class PhysicsManager extends Manager {

    private static PhysicsManager instance;
    public static PhysicsManager getInstance() {
        if (instance == null) {
            instance = new PhysicsManager();
        }
        return instance;
    }

    private ArrayList<PhysicsComponent> physicsComponents;
    private PhysicsComponent player;

    public void tick(float delta) {
        for (PhysicsComponent physics : physicsComponents) {
            if (physics.isEnabled()) {
                physics.getVelocity().set(physics.getAcceleration());
                //physics.getAcceleration().set(0,0);
                physics.getVelocity().scl(delta);
                physics.movePosition(physics.getVelocity().x, physics.getVelocity().y);
                //physics.setVelocity(0,0);
                physics.getVelocity().scl(1 / delta);
            }
        }

        if (player == null) {
            return;
        }
        for (PhysicsComponent physic : physicsComponents) {
            if (physic.isEnabled() && physic.collidable && physic != player) {
                physic.collided = player.bounds.overlaps(physic.bounds);
            }
        }

    }

    public void setPlayer(PhysicsComponent player) {
        this.player = player;
        DebugTool.log("Player set in PhysicsManager");
    }

    @Override
    protected void loadScreenTypeSettings() {
        physicsComponents = new ArrayList<PhysicsComponent>();
    }

    @Override
    protected void removeScreenTypeSettings() {

    }

    public void add(PhysicsComponent physics) {
        if (!physicsComponents.contains(physics)) {
            physicsComponents.add(physics);
        } else {
            DebugTool.log("Failed to add physics to physicsComponents");
        }
    }

    public void remove(PhysicsComponent physics) {
        if (physicsComponents.contains(physics)) {
            physicsComponents.remove(physics);
        } else {
            DebugTool.log("Failed to find physics in physicsComponents");
        }
    }

    private PhysicsManager() {
        super();
        physicsComponents = new ArrayList<PhysicsComponent>();
    }
}
