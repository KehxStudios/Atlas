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

    public Vector2 getPosition() {
        if (!useComponentPosition) {
            return entity.getPosition();
        } else {
            if (usePositionAsOffset) {
                return position.add(entity.getPosition());
            } else {
                return position;
            }
        }
    }
    public void setPosition(float x, float y) {
        if (!useComponentPosition) {
            entity.setPosition(x,y);
        } else {
            position.set(x,y);
        }
    }
    public void movePosition(float x, float y) {
        if (!useComponentPosition) {
            entity.movePosition(x,y);
        } else {
            position.add(x,y);
        }
    }

    public boolean getUseComponentPosition() { return useComponentPosition; }
    public void setUseComponentPosition(boolean value) {
        if (value && position == null) {
            position = new Vector2(0,0);
        }
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
