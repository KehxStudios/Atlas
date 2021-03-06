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
import java.util.HashMap;
import java.util.Set;

/**
 * Used to hold all created Entity's and controls adding/removing of Component's 
 */

public class EntityManager extends Manager {

    private GraphicsManager graphicsManager;
    private InputManager inputManager;
    private PhysicsManager physicsManager;
    private PositionManager positionManager;
    private SoundManager soundManager;

    // HashMap for all created Entity's
    private HashMap<Integer, Entity> entities;
    // HashMap for all created Component's
    private HashMap<Integer, Component> components;
    
    // ArrayList for Entity Ids to be removed
    private ArrayList<Integer> markedEntities;
    // ArrayList for Component Ids to be removed
    private ArrayList<Integer> markedComponents;

    // Constructor
    public EntityManager(GameManager gm) {
        super(gm);
        DebugTool.log("EntityManager: Constructed");
    }
    
    // Initalizes the ArrayLists
    @Override
    protected void init() {
        DebugTool.log("EntityManager_init: Starting...");
        graphicsManager = gm.getGraphicsManager();
        inputManager = gm.getInputManager();
        physicsManager = gm.getPhysicsManager();
        positionManager = gm.getPositionManager();
        soundManager = gm.getSoundManager();

        entities = new HashMap<Integer, Entity>();
        components = new HashMap<Integer, Component>();
        markedEntities = new ArrayList<Integer>();
        markedComponents = new ArrayList<Integer>();
        DebugTool.log("EntityManager_init: Complete");
    }
    
    // Called to removed any marked Components or Entities
    @Override
    public void tick(float delta) {
        while (markedComponents.size() > 0) {
            remove(components.get(markedComponents.get(0)));
            markedComponents.remove(markedComponents.get(0));
        }
        while (markedEntities.size() > 0) {
            remove(entities.get(markedEntities.get(0)));
            markedEntities.remove(markedEntities.get(0));
        }
    }

    // Called when loading a new screen
    @Override
    protected void loadSettings() {
        DebugTool.log("EntityManager_loadSettings: Complete");
    }

    // Called when unloading the current screen
    @Override
    protected void removeSettings() {
        if (entities.size() == 0) {
            return;
        }
        for (Entity entity : entities.values()) {
            DebugTool.log("Removing Entity #" + entity.id);
            remove(entity);
        }
        DebugTool.log("EntityManager_removeSettings: Complete");
    }
    
    // Called to mark an Entity to be removed on @tick()
    public void markEntityForRemoval(int entityId) {
        if (!markedEntities.contains(entityId) && entities.containsKey(entityId)) {
            markedEntities.add(entityId);
        } else {
            ErrorTool.log("Failed to mark entity, already marked");  
        }
    }

    // Called to mark a Component to be removed on @tick()
    public void markComponentForRemoval(int componentId) {
        if (!markedComponents.contains(componentId) && components.containsKey(componentId)) {
            markedComponents.add(componentId);
        } else {
            ErrorTool.log("Failed to mark component, already marked");  
        }
    }

    public Component getComponentById(int id) {
        return components.get(id);
    }

    // Adds an Entity to current @entities ArrayList
    public void add(Entity entity) {
        if (!entities.containsKey(entity)) {
            entities.put(entity.id, entity);
        } else {
            ErrorTool.log("Failed to add entity to entities");
        }
    }

    // Removes an Entity from current @entities ArrayList including Components
    private void remove(Entity entity) {
        if (entities.containsKey(entity.id)) {
            if (entity.components.size() > 0) {
                ArrayList<Integer> keys = new ArrayList<Integer>();
                for (Integer componentId : entity.components.keySet()) {
                    keys.add(componentId);
                }
                for (int key : keys) {
                    remove(components.get(key));
                }
            }
            entities.values().remove(entity.id);
        } else {
            ErrorTool.log("Failed to find entity in entities");
        }
    }

    // Adds a Component to @entity
    public void add(Component component) {
        if (entities.containsKey(component.entityId)) {
            if (!components.containsKey(component.id)) {
                components.put(component.id, component);
                entities.get(component.entityId).components.put(component.id, component.type);
            } else {
                ErrorTool.log("entity already contains component");
            }
        } else {
            ErrorTool.log("Failed to find entity in entities to add component_1");
        }
    }

    // Removed a Component from @entity
    public void remove(Component component) {
        if (entities.containsKey(component.entityId)) {
            if (components.containsKey(component.id)) {
                if (component.type == ComponentType.ANIMATION) {
                    graphicsManager.remove(component);
                } else if (component.type == ComponentType.CLICKABLE) {
                    inputManager.remove(component);
                    positionManager.remove(component);
                } else if (component.type == ComponentType.COLLISION) {
                    physicsManager.remove(component);
                    positionManager.remove(component);
                } else if (component.type == ComponentType.CAMERA) {
                    graphicsManager.remove(component);
                    inputManager.remove(component);
                    positionManager.remove(component);
                } else if (component.type == ComponentType.FLOATING_TEXT) {
                    graphicsManager.remove(component);
                    positionManager.remove(component);
                } else if (component.type == ComponentType.GENE_ROCKET) {

                } else if (component.type == ComponentType.GRAPHICS) {
                    graphicsManager.remove(component);
                    positionManager.remove(component);
                } else if (component.type == ComponentType.MUSIC) {
                    soundManager.remove(component);
                } else if (component.type == ComponentType.PHYSICS) {
                    physicsManager.remove(component);
                    positionManager.remove(component);
                } else if (component.type == ComponentType.SOUND) {
                    soundManager.remove(component);
                }
                entities.get(component.entityId).components.remove(component.id);
                components.remove(component.id);
            } else {
                ErrorTool.log("Failed to find component in entity to remove");
            }
        } else {
            ErrorTool.log("Failed to find entity in entities to remove component_2");
        }
    }

    public void enableComponent(int componentId, boolean enable) {
        if (components.containsKey(componentId)) {
            if (enable) {
                components.get(componentId).enabled = true;
            } else {
                components.get(componentId).enabled = false;
            }
        }
    }

    public void setEntityPosition(int entityId, float x, float y) {
        entities.get(entityId).position.set(x, y);
    }

    public void moveEntityPosition(int entityId, float x, float y) {
        entities.get(entityId).position.add(x, y);
    }
}
