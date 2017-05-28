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
        super.init();
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
        entities = new HashMap<Integer, Entity>();
        components = new HashMap<Integer, Component>();
        markedEntities = new ArrayList<Integer>();
        markedComponents = new ArrayList<Integer>();
    }

    // Called when unloading the current screen
    @Override
    protected void removeSettings() {
        while (entities.size() > 0 && false) {
            DebugTool.log("Removing Entities{0} in removeSettings");
            remove(entities.get(0));
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
                Integer[] componentIds = (Integer[]) entity.components.keySet().toArray();
                for (Integer componentId : componentIds) {
                    remove(components.get(componentId));
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
                    gm.getGraphicsManager().remove(component);
                } else if (component.type == ComponentType.CLICKABLE) {
                    gm.getInputManager().remove(component);
                    gm.getPositionManager().remove(component);
                } else if (component.type == ComponentType.COLLISION) {
                    gm.getPhysicsManager().remove(component);
                    gm.getPositionManager().remove(component);
                }else if (component.type == ComponentType.FLOATING_TEXT) {
                    gm.getGraphicsManager().remove(component);
                    gm.getPositionManager().remove(component);
                } else if (component.type == ComponentType.GENE_ROCKET) {

                } else if (component.type == ComponentType.GRAPHICS) {
                    gm.getGraphicsManager().remove(component);
                    gm.getPositionManager().remove(component);
                } else if (component.type == ComponentType.MUSIC) {
                    gm.getSoundManager().remove(component);
                } else if (component.type == ComponentType.PHYSICS) {
                    gm.getPhysicsManager().remove(component);
                    gm.getPositionManager().remove(component);
                } else if (component.type == ComponentType.SOUND) {
                    gm.getSoundManager().remove(component);
                }
                entities.get(component.entityId).components.values().remove(component.id);
                components.values().remove(component.id);
            } else {
                ErrorTool.log("Failed to find component in entity to remove");
            }
        } else {
            ErrorTool.log("Failed to find entity in entities to remove component_2");
        }
    }

    public void setEntityPosition(int entityId, float x, float y) {
        entities.get(entityId).position.set(x, y);
    }

    public void moveEntityPosition(int entityId, float x, float y) {
        entities.get(entityId).position.add(x, y);
    }
}
