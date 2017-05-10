package com.kehxstudios.atlas.managers;

import com.kehxstudios.atlas.components.AnimationComponent;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.type.ComponentType;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.ErrorTool;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-06.
 */

public class EntityManager extends Manager {

    // Holds instance of class, create new if not set
    private static EntityManager instance;
    public static EntityManager getInstance() {
        if (instance == null) {
            instance = new EntityManager();
        }
        return instance;
    }

    // ArrayList for all Entities created
    private ArrayList<Entity> entities;

    // ArrayLists for Entities/Components to be removed on tick
    private ArrayList<Entity> markedEntities;
    private ArrayList<Component> markedComponents;

    // Constructor
    private EntityManager() {
        super();
        setup();
    }
    
    // Initalizes the ArrayLists
    @Override
    protected void setup() {
        entities = new ArrayList<Entity>();
        markedEntities = new ArrayList<Entity>();
        markedComponents = new ArrayList<Component>();
        DebugTool.log("EntityManager_setup: Complete");
    }
    
    // Called to removed any marked Components or Entities
    @Override
    public void tick(float delta) {
        while (markedComponents.size() > 0) {
            removeComponent(markedComponents.get(0).getEntity(), markedComponents.get(0));
            markedComponents.remove(markedComponents.get(0));
        }
        while (markedEntities.size() > 0) {
            removeEntity(entities.get(0));
            markedEntities.remove(markedEntities.get(0));
        }
    }

    // Called when loading a new screen
    @Override
    protected void loadScreenSettings() {
        setup();
        DebugTool.log("EntityManager_loadScreenSettings: Complete");
    }

    // Called when unloading the current screen
    @Override
    protected void removeScreenSettings() {
        while (entities.size() > 0) {
            removeEntity(entities.get(0));
        }
        DebugTool.log("EntityManager_removeScreenSettings: Complete");
    }
    
    // Called to mark an Entity to be removed on @tick()
    public void markEntityForRemoval(Entity entity) {
        if (!markedEntities.contains(entity)) {
            markedEntities.add(entity);
        } else {
            ErrorTool.log("Failed to mark entity, already marked");  
        }
    }

    // Called to mark a Component to be removed on @tick()
    public void markComponentForRemoval(Component component) {
        if (!markedComponents.contains(component)) {
            markedComponents.add(component);
        } else {
            ErrorTool.log("Failed to mark component, already marked");  
        }
    }

    // Adds an Entity to current @entities ArrayList
    public void addEntity(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        } else {
            ErrorTool.log("Failed to add entity to entities");
        }
    }

    // Removes an Entity from current @entities ArrayList including Components
    private void removeEntity(Entity entity) {
        if (entities.contains(entity)) {
            if (entity.getComponents().size() > 0) {
                ArrayList<Component> components = entity.getComponents();
                for (;components.size() > 0;) {
                    removeComponent(entity, components.get(0));
                }
            }
            entities.remove(entity);
        } else {
            ErrorTool.log("Failed to find entity in entities");
        }
    }

    // Adds a Component to @entity
    public void addComponent(Entity entity, Component component) {
        if (entities.contains(entity)) {
            if (!entity.hasComponent(component)) {
                entity.getComponents().add(component);
            } else {
                ErrorTool.log("entity already contains component");
            }
        } else {
            ErrorTool.log("Failed to find entity in entities to add component_1");
        }
    }

    // Removed a Component from @entity
    private void removeComponent(Entity entity, Component component) {
        if (entities.contains(entity)) {
            if (entity.hasComponent(component)) {
                if (component.getType() == ComponentType.ANIMATION) {
                    GraphicsManager.getInstance().remove(component);
                } else if (component.getType() == ComponentType.CLICKABLE) {
                    InputManager.getInstance().remove((ClickableComponent)component);
                } else if (component.getType() == ComponentType.FLOATING_TEXT) {
                    GraphicsManager.getInstance().remove(component);
                } else if (component.getType() == ComponentType.GRAPHICS) {
                    GraphicsManager.getInstance().remove(component);
                } else if (component.getType() == ComponentType.IN_VIEW) {

                } else if (component.getType() == ComponentType.PHYSICS) {
                    PhysicsManager.getInstance().remove((PhysicsComponent)component);
                } else if (component.getType() == ComponentType.POINTER_DIRECTION) {

                }
                entity.getComponents().remove(component);
            } else {
                ErrorTool.log("Failed to find component in entity to remove");
            }
        } else {
            ErrorTool.log("Failed to find entity in entities to remove component_2");
        }
    }
}
