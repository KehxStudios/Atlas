package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.Gdx;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.data.GrimReaper;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.tools.DebugTool;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-06.
 */

public class EntityManager extends Manager {

    private static EntityManager instance;
    public static EntityManager getInstance() {
        if (instance == null) {
            instance = new EntityManager();
        }
        return instance;
    }
    private EntityManager() {
        entities = new ArrayList<Entity>();
    }

    private ArrayList<Entity> entities;

    public void addEntity(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        } else {
            DebugTool.log("Failed to add entity to entities");
        }
    }

    public void removeEntity(Entity entity) {
        if (entities.contains(entity)) {
            if (entity.getComponents().size() > 0) {
                ArrayList<Component> components = entity.getComponents();
                for (;components.size() > 0;) {
                    removeComponent(entity, components.get(0));
                }
            }
            entities.remove(entity);
        } else {
            DebugTool.log("Failed to find entity in entities");
        }
    }

    public void addComponent(Entity entity, Component component) {
        if (entities.contains(entity)) {
            if (!entity.hasComponent(component)) {
                entity.getComponents().add(component);
            } else {
            }
        } else {
            DebugTool.log("Failed to find entity in entities to add component");
        }
    }

    public void removeComponent(Entity entity, Component component) {
        if (entities.contains(entity)) {
            if (entity.hasComponent(component)) {
                entity.getComponents().remove(component);
            } else {

            }
        } else {
            DebugTool.log("Failed to find entity in entities to add component");
        }
    }

    @Override
    public void tick(float delta) {

    }

    @Override
    protected void loadScreenTypeSettings() {

    }

    @Override
    protected void removeScreenTypeSettings() {
        for (; entities.size() > 0;) {
            removeEntity(entities.get(0));
        }
    }
}
