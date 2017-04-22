package com.kehxstudios.atlas.entities;

import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.ComponentType;
import com.kehxstudios.atlas.managers.EntityManager;
import com.kehxstudios.atlas.tools.UtilityTool;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-06.
 */

public class Entity {

    protected  String id;
    protected Vector2 position;
    protected ArrayList<Component> components;
    protected EntityData entityData;

    public Entity() {
        position = new Vector2(0,0);
        components = new ArrayList<Component>();
        entityData = null;
        id = "ENTITY_" + EntityManager.getInstance().getUniqueId();
        EntityManager.getInstance().addEntity(this);
    }

    public Entity(float x, float y) {
        position = new Vector2(x,y);
        components = new ArrayList<Component>();
        entityData = null;
        id = "ENTITY_" + EntityManager.getInstance().getUniqueId();
        EntityManager.getInstance().addEntity(this);
    }

    public Entity(EntityData entityData) {
        this.entityData = entityData;
        position = new Vector2(entityData.getX(),entityData.getY());
        components = new ArrayList<Component>();
        id = "ENTITY_" + EntityManager.getInstance().getUniqueId();
        for (String componentString : entityData.data.values()) {
            components.add(new Component(this, UtilityTool.getComponentDataFromString(componentString)));
        }
    }

    public EntityData getEntityData() {
        entityData = new EntityData(id, position.x, position.y);
        for (Component component : components) {
            entityData.putString(component.getType().getId(), UtilityTool.getStringFromDataClass(component.getComponentData()));
        }
        return entityData;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public boolean hasComponent(Component component) {
        for (Component component2 : components) {
            if (component2.getClass().getName() == component.getClass().getName()) {
                return true;
            }
        }
        return false;
    }

    public String getId() { return id; }

    public void moveLocation(float x, float y) { position.add(x,y); }

    public void setLocation(float x, float y) {
        position.set(x,y);
    }

    public Vector2 getLocation() { return position; }

    public float getX() {
        return position.x;
    }

    public void setX(float value) {
        position.x = value;
    }

    public float getY() {
        return position.y;
    }

    public void setY(float value) {
        position.y = value;
    }

    public void move(float x, float y) {
        position.x += x;
        position.y += y;
    }

    public Component getComponentWithId(String id) {
        for (Component component : components) {
            if (component.getId() == id) {
                return component;
            }
        }
        return null;
    }

    public Component getComponentByType(ComponentType type) {
        for (Component component : components) {
            if (component.getType() == type) {
                return component;
            }
        }
        return null;
    }

    public void destroy() {
        EntityManager.getInstance().removeEntity(this);
    }
}
