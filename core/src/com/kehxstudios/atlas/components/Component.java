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

    protected Entity entity;
    protected ComponentType type;
    protected String id;

    protected Vector2 position;

    public Component(Entity entity) {
        this.entity = entity;
        id = EntityManager.getInstance().getUniqueId();
        EntityManager.getInstance().addComponent(entity, this);
    }

    public Entity getEntity() {
        return entity;
    }

    public ComponentType getType() { return type; }

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

    public void move(float x, float y) {
        if (position == null) {
            entity.move(x,y);
        } else {
            position.add(x,y);
        }
    }

    public void setLocation(float x, float y) {
        if (position == null) {
            entity.setLocation(x,y);
        } else {
            position.set(x,y);
        }
    }

    public void setUniqueLocation(float x, float y) {
        if (position == null) {
            position = new Vector2(x,y);
        } else {
            position.set(x,y);
        }
    }

    public float getX() {
        if (position == null){
            return entity.getX();
        } else {
            return position.x;
        }
    }

    public void setX(float value) {
        if (position == null) {
            entity.setX(value);
        } else {
            position.x = value;
        }
    }

    public float getY() {
        if (position == null){
            return entity.getY();
        } else {
            return position.y;
        }
    }

    public void setY(float value) {
        if (position == null) {
            entity.setY(value);
        } else {
            position.y = value;
        }
    }
}
