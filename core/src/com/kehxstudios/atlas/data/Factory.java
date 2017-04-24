package com.kehxstudios.atlas.data;

import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.actions.ActionData;
import com.kehxstudios.atlas.actions.ActionType;
import com.kehxstudios.atlas.components.AnimationComponent;
import com.kehxstudios.atlas.components.ButtonComponent;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.ComponentData;
import com.kehxstudios.atlas.components.ComponentType;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.entities.EntityData;
import com.kehxstudios.atlas.managers.EntityManager;
import com.kehxstudios.atlas.tools.UtilityTool;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-23.
 */

public class Factory {

    private static Factory instance;
    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    private ArrayList<EntityData> entitiesQueue;
    private ArrayList<ComponentData> componentQueue;

    public Factory() {
        entitiesQueue = new ArrayList<EntityData>();
        componentQueue = new ArrayList<ComponentData>();
    }

    public void tick() {
        while (entitiesQueue.size() != 0) {
            createEntity(entitiesQueue.get(0));
            entitiesQueue.remove(0);
        }
    }

    public void orderEntity(EntityData entityData) {
        entitiesQueue.add(entityData);
    }

    private void createEntity(EntityData entityData) {
        Entity entity = new Entity();
        entity.setId("Entity_" + EntityManager.getInstance().getUniqueId());
        entity.setLocation(entityData.getX(), entityData.getY());
        EntityManager.getInstance().addEntity(entity);

        for (String componentString : entityData.data.values()) {
            //components.add(new Component(this, UtilityTool.getComponentDataFromString(componentString)));
        }
    }

    private void createAnimationComponent(Entity entity, ComponentData componentData) {
        AnimationComponent animation = new AnimationComponent(entity);
        animation.setType(ComponentType.ANIMATION);
        // FINISH ANIMATION DATA
    }

    private void createButtonComponent(Entity entity, ComponentData componentData) {
        ButtonComponent button = new ButtonComponent(entity);
        button.setType(ComponentType.BUTTON);
        button.setKey(componentData.getInt("key", 0));
        // CHANGE TO ACTION
        button.setPhysics((PhysicsComponent)entity.getComponentByType(ComponentType.PHYSICS));
    }

    private void createClickableComponent(Entity entity, ComponentData componentData) {
        ClickableComponent clickable = new ClickableComponent(entity);
        clickable.setType(ComponentType.CLICKABLE);
        clickable.setWidth(componentData.getFloat("width", 0));
        clickable.setHeight(componentData.getFloat("height", 0));
        clickable.setSingleTrigger(componentData.getBoolean("singleTrigger", false));
        // SET ACTION
    }


    private ComponentData createAnimationComponentData(AnimationComponent animation) {
        ComponentData componentData = createComponentData(animation);
        // FINISH ANIMATION DATA
        return componentData;
    }

    private ComponentData createButtonComponentData(ButtonComponent button) {
        ComponentData componentData = createComponentData(button);
        componentData.putInt("key", button.getKey());
        // SAVE ACTION
        componentData.putString("action", "");
        return componentData;
    }

    private ComponentData createClickableComponentData(ClickableComponent clickable) {
        ComponentData componentData = createComponentData(clickable);
        componentData.putFloat("width", clickable.getWidth());
        componentData.putFloat("height", clickable.getHeight());
        componentData.putBoolean("singleTrigger", clickable.isSingleTrigger());
        // SAVE ACTION
        componentData.putString("action", "");
        return componentData;
    }

    private EntityData createEntityData(Entity entity) {
        EntityData entityData = new EntityData();
        entityData.setX(entity.getX());
        entityData.setY(entity.getY());
        for (Component component : entity.getComponents()) {
            // entityData.putFloat(component.getId(), );
        }
        return entityData;
    }

    private ComponentData createComponentData(Component component) {
        ComponentData componentData = new ComponentData();
        componentData.setX(component.getX());
        componentData.setY(component.getY());
        componentData.setType(component.getType().getId());
        componentData.setEnabled(component.isEnabled());
        componentData.setUsePositionAsOffset(component.getUsePositionAsOffset());
        componentData.setUseComponentPosition(component.getUseComponentPosition());
        if (component.getType() == ComponentType.ANIMATION) {

        } else if (component.getType() == ComponentType.BUTTON) {
            componentData.putInt("key", ((ButtonComponent)component).getKey());

        } else if (component.getType() == ComponentType.CLICKABLE) {

        }
        return componentData;
    }

    private ActionData createActionData(Action action) {
        ActionData actionData = new ActionData();
        actionData.setType(action.getActionType().getId());
        if (action.getActionType() == ActionType.DESTROY_ENTITY) {

        } else if (action.getActionType() == ActionType.HIGH_SCORE_RESET) {

        } else if (action.getActionType() == ActionType.LAUNCH_SCREEN) {

        } else if (action.getActionType() == ActionType.MULTI) {

        }
        return actionData;
    }
}