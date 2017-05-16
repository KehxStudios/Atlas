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

/**
 * Used to hold all created Entity's and controls adding/removing of Component's 
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
        init();
    }
    
    // Initalizes the ArrayLists
    @Override
    protected void init() {
        entities = new ArrayList<Entity>();
        markedEntities = new ArrayList<Entity>();
        markedComponents = new ArrayList<Component>();
        DebugTool.log("EntityManager_init: Complete");
    }
    
    // Called to removed any marked Components or Entities
    @Override
    public void tick(float delta) {
        while (markedComponents.size() > 0) {
            remove(markedComponents.get(0));
            markedComponents.remove(markedComponents.get(0));
        }
        while (markedEntities.size() > 0) {
            remove(entities.get(0));
            markedEntities.remove(markedEntities.get(0));
        }
    }

    // Called when loading a new screen
    @Override
    protected void loadSettings() {
        setup();
        DebugTool.log("EntityManager_loadSettings: Complete");
    }

    // Called when unloading the current screen
    @Override
    protected void removeSettings() {
        while (entities.size() > 0) {
            remove(entities.get(0));
        }
        DebugTool.log("EntityManager_removeSettings: Complete");
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
    public void add(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        } else {
            ErrorTool.log("Failed to add entity to entities");
        }
    }

    // Removes an Entity from current @entities ArrayList including Components
    private void remove(Entity entity) {
        if (entities.contains(entity)) {
            if (entity.getComponents().size() > 0) {
                ArrayList<Component> components = entity.getComponents();
                for (;components.size() > 0;) {
                    remove(components.get(0));
                }
            }
            entities.remove(entity);
        } else {
            ErrorTool.log("Failed to find entity in entities");
        }
    }

    // Adds a Component to @entity
    public void add(Component component) {
        if (entities.contains(component.getEntity())) {
            if (!entities.contains(component.getEntity().hasComponent(component)) {
                entities.contains(component.getEntity().getComponents().add(component);
            } else {
                ErrorTool.log("entity already contains component");
            }
        } else {
            ErrorTool.log("Failed to find entity in entities to add component_1");
        }
    }

    // Removed a Component from @entity
    private void remove(Component component) {
        if (entities.contains(component.getEntity())) {
            if (entities.contains(component.getEntity().hasComponent(component)) {
                if (component.getType() == ComponentType.ANIMATION) {
                    GraphicsManager.getInstance().remove(component);
                } else if (component.getType() == ComponentType.CLICKABLE) {
                    InputManager.getInstance().remove((ClickableComponent)component);
                } else if (component.getType() == ComponentType.COLLISION) {
                    PhysicsManager.getInstance().remove(component);
                }else if (component.getType() == ComponentType.FLOATING_TEXT) {
                    GraphicsManager.getInstance().remove(component);
                } else if (component.getType() == ComponentType.GENE_ROCKET) {

                } else if (component.getType() == ComponentType.GRAPHICS) {
                    GraphicsManager.getInstance().remove(component);
                } else if (component.getType() == ComponentType.IN_VIEW) {

                } else if (component.getType() == ComponentType.MUSIC) {
                    SoundManager.getInstance().remove(component);
                } else if (component.getType() == ComponentType.PHYSICS) {
                    PhysicsManager.getInstance().remove((PhysicsComponent)component);
                } else if (component.getType() == ComponentType.POINTER_DIRECTION) {

                } else if (component.getType() == ComponentType.SOUND) {
                    SoundManager.getInstance().remove(component);
                }
                entities.contains(component.getEntity().getComponents().remove(component);
            } else {
                ErrorTool.log("Failed to find component in entity to remove");
            }
        } else {
            ErrorTool.log("Failed to find entity in entities to remove component_2");
        }
    }
}
