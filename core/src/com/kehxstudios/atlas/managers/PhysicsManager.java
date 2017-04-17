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
            if (!physics.isEnabled()) {
                continue;
            }
            if (physics.getY() > 0) {
                physics.velocity.add(0, physics.speeds.y);
            }
            physics.velocity.scl(delta);
            physics.moveLocation(physics.speeds.x * delta, physics.velocity.y);
            if (physics.getY() < 0) {
                physics.setY(0);
            }
            physics.velocity.scl(1/delta);
            physics.updateBounds();
        }

        if (player == null) {
            return;
        }
        for (PhysicsComponent physic : physicsComponents) {
            if (physic.isEnabled() && physic.collidable && physic != player) {
                physic.hasCollided = player.bounds.overlaps(physic.bounds);
            }
        }

    }

    public void setPlayer(PhysicsComponent player) {
        this.player = player;
        DebugTool.log("Player set in PhysicsManager");
    }

    @Override
    protected void loadScreenTypeSettings() {

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
        physicsComponents = new ArrayList<PhysicsComponent>();
    }




}
