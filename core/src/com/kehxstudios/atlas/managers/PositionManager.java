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

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kehxstudios.atlas.components.CameraComponent;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.CollisionComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.type.ComponentType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages the position of an Entity across all linked Components
 */

public class PositionManager extends Manager {

    private static PositionManager instance;
    public static PositionManager getInstance() {
        if (instance == null) {
            instance = new PositionManager();
        }
        return instance;
    }

    private HashMap<Integer,ArrayList<Component>> positionHashMap;
    private ArrayList<Integer> positionsToUpdate;

    private PositionManager() {
        super();
        init();
    }

    @Override
    protected void init() {
        positionHashMap = new HashMap<Integer, ArrayList<Component>>();
    }

    @Override
    public void tick(float delta) {

    }

    @Override
    public void add(Component component) {
        int entityId = component.entityId;
        if (!positionHashMap.containsKey(entityId)) {
            ArrayList<Component> newComponentList = new ArrayList<Component>();
            newComponentList.add(component);
            positionHashMap.put(entityId, newComponentList);
        } else {
            if (!positionHashMap.get(entityId).contains(component)) {
                positionHashMap.get(entityId).add(component);
            }
        }
    }

    @Override
    public void remove(Component component) {
        int entityId = component.entityId;
        if (positionHashMap.containsKey(entityId)) {
            if (positionHashMap.get(entityId).contains(component)) {
                positionHashMap.get(entityId).remove(component);
                if (positionHashMap.get(entityId).size() == 0) {
                    positionHashMap.remove(entityId);
                }
            }
        }
    }

    public void setPosition(int entityId, Vector2 position) {
        if (positionHashMap.containsKey(entityId)) {
            EntityManager.getInstance().setEntityPosition(entityId, position.x, position.y);
            for (Component component : positionHashMap.get(entityId)) {
                if (component.type == ComponentType.GRAPHICS) {
                    ((GraphicsComponent)component).bounds.setCenter(position);
                } else if (component.type == ComponentType.CAMERA) {
                    CameraComponent camera = (CameraComponent)component;
                    camera.camera.position.set(new Vector3(position.x, position.y, 0));
                    camera.camera.update();
                } else if (component.type == ComponentType.CLICKABLE) {
                    ((ClickableComponent)component).bounds.setCenter(position);
                } else if (component.type == ComponentType.COLLISION) {
                    ((CollisionComponent)component).bounds.setCenter(position);
                } else if (component.type == ComponentType.FLOATING_TEXT) {
                    ((FloatingTextComponent)component).position = new Vector2(position);
                } else if (component.type == ComponentType.PHYSICS) {
                    ((PhysicsComponent)component).position = new Vector2(position);
                }
            }
        }
    }
    public void movePosition(int entityId, float x, float y) {
        if (positionHashMap.containsKey(entityId)) {
            EntityManager.getInstance().moveEntityPosition(entityId, x, y);
            for (Component component : positionHashMap.get(entityId)) {
                if (component.type == ComponentType.GRAPHICS) {
                    GraphicsComponent graphics = ((GraphicsComponent)component);
                    graphics.bounds.x += x;
                    graphics.bounds.y += y;
                } else if (component.type == ComponentType.CAMERA) {
                    CameraComponent camera = (CameraComponent)component;
                    camera.camera.position.add(new Vector3(x, y, 0));
                    camera.camera.update();
                } else if (component.type == ComponentType.CLICKABLE) {
                    ClickableComponent clickableComponent = ((ClickableComponent)component);
                    clickableComponent.bounds.x += x;
                    clickableComponent.bounds.y += y;
                } else if (component.type == ComponentType.COLLISION) {
                    CollisionComponent collision = ((CollisionComponent)component);
                    collision.bounds.x += x;
                    collision.bounds.y += y;
                } else if (component.type == ComponentType.FLOATING_TEXT) {
                    ((FloatingTextComponent)component).position.add(x, y);
                } else if (component.type == ComponentType.PHYSICS) {
                    ((PhysicsComponent)component).position.add(x, y);
                }
            }
        }
    }

    @Override
    protected void loadSettings() {

    }

    @Override
    protected void removeSettings() {

    }
}
