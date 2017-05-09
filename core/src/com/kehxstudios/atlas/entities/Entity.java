package com.kehxstudios.atlas.entities;

import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.type.ComponentType;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-06.
 */

public class Entity {

    protected String id;
    protected Vector2 position;
    protected ArrayList<Component> components;

    public Entity() {
        position = new Vector2();
        components = new ArrayList<Component>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Vector2 getPosition() { return position; }
    public void setPosition(float x, float y) { position.set(x,y); }
    public void movePosition(float x, float y) { position.add(x,y); }
    public void setX(float x) { position.x = x; }
    public void setY(float y) { position.y = y; }

    public ArrayList<Component> getComponents() { return components; }
    public Component getComponentOfType(ComponentType componentType) {
        for (Component component : components) {
            if (component.getType() == componentType) {
                return component;
            }
        }
        return null;
    }
    public boolean hasComponent(Component component) { return components.contains(component); }
    public boolean hasComponentOfType(ComponentType componentType) {
        for (Component component : components) {
            if (component.getType() == componentType) {
                return true;
            }
        }
        return false;
    }
    public void addComponent(Component component) { components.add(component); }
    public void removeComponent(Component component) { components.remove(component); }
}
