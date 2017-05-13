package com.kehxstudios.atlas.components;

import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.type.ComponentType;

/**
 * Created by ReidC on 2017-04-06.
 */

public class Component {

    protected String id;
    protected ComponentType type;
    protected Entity entity;

    protected Vector2 position;
    protected boolean useComponentPosition;
    protected boolean usePositionAsOffset;

    protected boolean enabled;

    public String getId() { return id; }
    public void setId(String id) {
        this.id = id;
    }

    public ComponentType getType() { return type; }
    public void setType(ComponentType type) { this.type = type; }

    public Entity getEntity() {
        return entity;
    }
    public void setEntity(Entity entity) { this.entity = entity; }

    public Component() {
        position = new Vector2(0,0);
    }

    public Vector2 getPosition() {
        if (useComponentPosition) {
            if (usePositionAsOffset) {
                return new Vector2(position).add(entity.getPosition());
            } else {
                return position;   
            }
        } else {
            return entity.getPosition();   
        }
    }
    public void setPosition(float x, float y) {
        if (useComponentPosition) {
            position.set(x,y);
        } else {
            entity.setPosition(x,y);
        }
    }
    public void movePosition(float x, float y) {
        if (useComponentPosition) {
            position.add(x,y);
        } else {
            entity.movePosition(x,y);
        }
    }

    public boolean getUseComponentPosition() { return useComponentPosition; }
    public void setUseComponentPosition(boolean value) {
        useComponentPosition = value;
    }

    public boolean getUsePositionAsOffset() { return usePositionAsOffset; }
    public void setUsePositionAsOffset(boolean value) {
        if (value && !useComponentPosition) {
            setUseComponentPosition(value);
        }
        usePositionAsOffset = value;
    }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean value) { enabled = value; }
}
