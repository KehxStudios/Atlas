package com.kehxstudios.atlas.entities;

import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.ComponentType;
import com.kehxstudios.atlas.managers.EntityManager;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-06.
 */

public class Entity {

    private Vector2 pos;
    private ArrayList<Component> components;

    public Entity() {
        components = new ArrayList<Component>();
        pos = new Vector2(0,0);
        EntityManager.getInstance().addEntity(this);
    }


    public Entity(float x, float y) {
        pos = new Vector2(x,y);
        components = new ArrayList<Component>();
        EntityManager.getInstance().addEntity(this);
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

    public void setLocation(float x, float y) {
        pos.set(x,y);
    }

    public float getX() {
        return pos.x;
    }

    public void setX(float value) {
        pos.x = value;
    }

    public float getY() {
        return pos.y;
    }

    public void setY(float value) {
        pos.y = value;
    }

    public void move(float x, float y) {
        pos.x += x;
        pos.y += y;
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
