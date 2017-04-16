package com.kehxstudios.atlas.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.EntityManager;
import com.kehxstudios.atlas.tools.DebugTool;

/**
 * Created by ReidC on 2017-04-06.
 */

public class Component {

    protected String id;
    protected ComponentType type;
    protected Entity entity;

    protected ComponentData componentData;

    protected boolean useComponentPosition;
    protected boolean usePositionAsOffset;
    protected Vector2 position;

    public Component(Entity entity) {
        this.entity = entity;
        useComponentPosition = false;
        usePositionAsOffset = false;
    }

    public Component(Entity entity, ComponentData componentData) {
        this.entity = entity;
        id = componentData.getId();
        type = ComponentType.getType(componentData.getType());
        useComponentPosition = componentData.getUseComponentPosition();
        usePositionAsOffset = componentData.getUsePositionAsOffset();
        if (componentData.getX() != 0 && componentData.getY() != 0) {
            position = new Vector2(componentData.getX(), componentData.getY());
        }
    }

    protected void init() {
        EntityManager.getInstance().addComponent(entity, this);
    }

    public Entity getEntity() {
        return entity;
    }

    public ComponentType getType() { return type; }

    public ComponentData getComponentData() {
        if (position == null) {
            componentData = new ComponentData(id, type.getId(), entity.getId(), 0,0, useComponentPosition, usePositionAsOffset);
        } else {
            componentData = new ComponentData(id, type.getId(), entity.getId(), position.x, position.y, useComponentPosition, usePositionAsOffset);
        }
        return componentData;
    }

    public void dispose() {
        DebugTool.log("Component disposal");
        EntityManager.getInstance().removeComponent(entity, this);
    }

    public void setNewId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void moveLocation(float x, float y) {
        if (!useComponentPosition) {
            entity.moveLocation(x,y);
        } else {
            position.add(x,y);
        }
    }

    public void setLocation(float x, float y) {
        if (!useComponentPosition) {
            entity.setLocation(x,y);
        } else {
            position.set(x,y);
        }
    }

    public Vector2 getLocation() {
        if (!useComponentPosition) {
            return entity.getLocation();
        } else {
            if (usePositionAsOffset) {
                return position.add(entity.getLocation());
            } else {
                return position;
            }
        }
    }

    public void setUsePositionAsOffset(boolean value) {
        if (value && !useComponentPosition) {
            setUseComponentPosition(value);
        }
        usePositionAsOffset = value;
    }

    public boolean getUsePositionAsOffset() { return usePositionAsOffset; }

    public void setUseComponentPosition(boolean value) {
        if (value && position == null) {
            position = new Vector2(0,0);
        }
        useComponentPosition = value;
    }

    public boolean getUseComponentPosition() { return useComponentPosition; }

    public float getX() {
        if (!useComponentPosition){
            return entity.getX();
        } else {
            if (usePositionAsOffset) {
                return entity.getX() + position.x;
            } else {
                return position.x;
            }
        }
    }

    public void setX(float value) {
        if (!useComponentPosition) {
            entity.setX(value);
        } else {
            if (position == null) {
                position = new Vector2(value, 0);
            } else {
                position.x = value;
            }
        }
    }

    public float getY() {
        if (!useComponentPosition){
            return entity.getY();
        } else {
            if (usePositionAsOffset) {
                return entity.getY() + position.y;
            } else {
                return position.x;
            }
        }
    }

    public void setY(float value) {
        if (!useComponentPosition) {
            entity.setY(value);
        } else {
            if (position == null) {
                position = new Vector2(0, value);
            } else {
                position.y = value;
            }
        }
    }
}
