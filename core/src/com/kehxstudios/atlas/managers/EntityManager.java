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

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-06.
 */

public class EntityManager extends Manager {

    private static EntityManager instance;
    public static EntityManager getInstance() {
        if (instance == null) {
            instance = new EntityManager();
        }
        return instance;
    }

    private ArrayList<Entity> entities;

    // ArrayLists for Entities/Components to be removed on tick
    private ArrayList<Entity> markedEntities;
    private ArrayList<Component> markedComponents;

    private EntityManager() {
        super();
        setup();
    }
    
    public void markEntityForRemoval(Entity entity) {
        if (!markedEntities.contains(entity)) {
            markedEntities.add(entity);
        }
    }

    public void markComponentForRemoval(Component component) {
        if (!markedComponents.contains(component)) {
            markedComponents.add(component);
        }
    }

    public void addEntity(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        } else {
            DebugTool.log("Failed to add entity to entities");
        }
    }

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
            DebugTool.log("Failed to find entity in entities");
        }
    }

    public void addComponent(Entity entity, Component component) {
        if (entities.contains(entity)) {
            if (!entity.hasComponent(component)) {
                entity.getComponents().add(component);
            } else {
            }
        } else {
            DebugTool.log("Failed to find entity in entities to add component");
        }
    }

    private void removeComponent(Entity entity, Component component) {
        if (entities.contains(entity)) {
            if (entity.hasComponent(component)) {
                if (component.getType() == ComponentType.ANIMATION) {
                    GraphicsManager.getInstance().remove((AnimationComponent)component);
                } else if (component.getType() == ComponentType.CLICKABLE) {
                    InputManager.getInstance().remove((ClickableComponent)component);
                } else if (component.getType() == ComponentType.FLOATING_TEXT) {
                    GraphicsManager.getInstance().remove((FloatingTextComponent)component);
                } else if (component.getType() == ComponentType.GRAPHICS) {
                    GraphicsManager.getInstance().remove((GraphicsComponent)component);
                } else if (component.getType() == ComponentType.IN_VIEW) {

                } else if (component.getType() == ComponentType.PHYSICS) {
                    PhysicsManager.getInstance().remove((PhysicsComponent)component);
                } else if (component.getType() == ComponentType.POINTER_DIRECTION) {

                }
                entity.getComponents().remove(component);
            } else {
                DebugTool.log("Failed to find component in entity to remove component");
            }
        } else {
            DebugTool.log("Failed to find entity in entities to remove component");
        }
    }

    @Override
    public void tick(float delta) {
        while (markedComponents.size() != 0) {
            removeComponent(markedComponents.get(0).getEntity(), markedComponents.get(0));
        }
        while (markedEntities.size() != 0) {
            removeEntity(entities.get(0));
        }
    }
    
    private void setup() {
        entities = new ArrayList<Entity>();
        markedEntities = new ArrayList<Entity>();
        markedComponents = new ArrayList<Component>();
    }

    @Override
    protected void loadScreenSettings() {
        setup();
    }

    @Override
    protected void removeScreenSettings() {
        while (entities.size() != 0) {
            removeEntity(entities.get(0));
        }
    }
}
