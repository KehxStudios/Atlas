package com.kehxstudios.atlas.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.actions.ActionData;
import com.kehxstudios.atlas.actions.ActionType;
import com.kehxstudios.atlas.actions.LaunchScreenAction;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.ComponentData;
import com.kehxstudios.atlas.components.ComponentType;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.InViewComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.entities.EntityData;
import com.kehxstudios.atlas.managers.EntityManager;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.managers.InputManager;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.screens.AScreenData;
import com.kehxstudios.atlas.screens.ScreenType;
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

    private int unqiueId;

    public AScreen createScreen(AScreenData screenData) {



    }

    public Entity createEntity(EntityData entityData) {
        Entity entity = new Entity();
        entity.setId("Entity_" + ++unqiueId);
        entity.setPosition(entityData.getX(), entityData.getY());

        entity.setComponents();
        for (String componentString : entityData.data.values()) {
            entity.addComponent(createComponent(entity, UtilityTool.getComponentDataFromString(componentString)));
        }

        EntityManager.getInstance().addEntity(entity);

        return entity;
    }

    public Component createComponent(Entity entity, ComponentData componentData) {
        try {
            Component component = (Component) ClassReflection.newInstance(ComponentType.getType(componentData.getType()).getLoaderClass());
            component.setEntity(entity);
            component.setType(ComponentType.getType(componentData.getType()));
            component.setUseComponentPosition(componentData.getUseComponentPosition());
            component.setUsePositionAsOffset(componentData.getUsePositionAsOffset());
            component.setPosition(componentData.getX(), componentData.getY());
            component.setEnabled(componentData.isEnabled());

            if (component.getType() == ComponentType.ANIMATION) {

            } else if (component.getType() == ComponentType.CLICKABLE) {
                ((ClickableComponent) component).setWidth(componentData.getFloat("width", 0));
                ((ClickableComponent) component).setHeight(componentData.getFloat("height", 0));
                ((ClickableComponent) component).setSingleTrigger(componentData.getBoolean("singleTrigger", false));
                ((ClickableComponent)component).setAction(createAction(UtilityTool.getActionDataFromString(componentData.getString("action", "Void"))));
                InputManager.getInstance().addClickable((ClickableComponent)component);
            } else if (component.getType() == ComponentType.FLOATING_TEXT) {
                ((FloatingTextComponent) component).setFont(new BitmapFont());
                ((FloatingTextComponent) component).setScale(2f);
                ((FloatingTextComponent) component).setLabel(componentData.getString("label", "-"));
                ((FloatingTextComponent) component).setText(componentData.getString("text", "-"));
                ((FloatingTextComponent) component).setLayout(new GlyphLayout(((FloatingTextComponent) component).getFont(),
                        ((FloatingTextComponent) component).getLabel() + ((FloatingTextComponent) component).getText()));
                ((FloatingTextComponent) component).getLayout().setText(((FloatingTextComponent) component).getFont(),
                        ((FloatingTextComponent) component).getLabel() + ((FloatingTextComponent) component).getText(),
                        Color.BLACK, 0, Align.center, true);
            } else if (component.getType() == ComponentType.GRAPHICS) {
                ((GraphicsComponent) component).setWidth(componentData.getFloat("width", 0));
                ((GraphicsComponent) component).setHeight(componentData.getFloat("height", 0));
                ((GraphicsComponent) component).setLayer(componentData.getInt("layer", 0));
                ((GraphicsComponent) component).setTextureType(TextureType.getTypeFromId(componentData.getString("textureType", "Void")));
                GraphicsManager.getInstance().add((GraphicsComponent)component);
            } else if (component.getType() == ComponentType.IN_VIEW) {
                ((InViewComponent) component).setWidth(componentData.getFloat("width", 0));
                ((InViewComponent) component).setHeight(componentData.getFloat("height", 0));
                ((InViewComponent) component).setAction(createAction(UtilityTool.getActionDataFromString(componentData.getString("action", "Void"))));
            } else if (component.getType() == ComponentType.PHYSICS) {
                ((PhysicsComponent) component).setAcceleration(componentData.getFloat("acceleraton_x", 0),
                        componentData.getFloat("acceleration_y", 0));
                ((PhysicsComponent) component).setMaxAcceleration(componentData.getFloat("maxAcceleration_x", 0),
                        componentData.getFloat("maxAcceleration_y", 0));
                ((PhysicsComponent) component).setVelocity(componentData.getFloat("velocity_x", 0),
                        componentData.getFloat("velocity_y", 0));
                ((PhysicsComponent) component).setMaxVelocity(componentData.getFloat("maxVelocity_x", 0),
                        componentData.getFloat("maxVelocity_y", 0));
                ((PhysicsComponent) component).setBounds( new Rectangle(componentData.getFloat("bounds_x", 0),
                        componentData.getFloat("bounds_y", 0), componentData.getFloat("bounds_width", 0),
                        componentData.getFloat("bounds_height", 0)));
                ((PhysicsComponent) component).setCollidable(componentData.getBoolean("collidable", false));
                ((PhysicsComponent) component).setCollided(componentData.getBoolean("collided", false));
                PhysicsManager.getInstance().add((PhysicsComponent)component);
            } else if (component.getType() == ComponentType.POINTER_DIRECTION) {

            }

            EntityManager.getInstance().addComponent(entity, component);

            return component;
        } catch (ReflectionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Action createAction(ActionData actionData) {
        try {
            Action action = (Action) ClassReflection.newInstance(ActionType.getType(actionData.getType()).getLoaderClass());
            action.setType(ActionType.getType(actionData.getString("type", "Void")));

            if (action.getType() == ActionType.DESTROY_ENTITY) {

            } else if (action.getType() == ActionType.HIGH_SCORE_RESET) {

            } else if (action.getType() == ActionType.LAUNCH_SCREEN) {
                ((LaunchScreenAction)action).setScreenType(ScreenType.getTypeById(actionData.getString("screenType", "Void")));
            } else if (action.getType() == ActionType.MULTI) {

            } else if (action.getType() == ActionType.PHYSICS) {

            } else if (action.getType() == ActionType.REPOSITION) {

            } else if (action.getType() == ActionType.SCORE) {

            } else if (action.getType() == ActionType.SPAWN_ENTITY) {

            } else if (action.getType() == ActionType.TELEPORT) {

            }

            return action;
        } catch (ReflectionException e) {
            e.printStackTrace();
            return null;
        }
    }


    private EntityData createEntityData(Entity entity) {
        EntityData entityData = new EntityData();
        entityData.setX(entity.getPosition().x);
        entityData.setY(entity.getPosition().y);
        for (Component component : entity.getComponents()) {
            ComponentData componentData = createComponentData(component);
            String stringData = UtilityTool.getStringFromDataClass(componentData);
            entityData.putString(componentData.getType(), stringData);
        }
        return entityData;
    }

    private ComponentData createComponentData(Component component) {
        ComponentData componentData = new ComponentData();
        componentData.setX(component.getPosition().x);
        componentData.setY(component.getPosition().y);
        componentData.setType(component.getType().getId());
        componentData.setEnabled(component.isEnabled());
        componentData.setUsePositionAsOffset(component.getUsePositionAsOffset());
        componentData.setUseComponentPosition(component.getUseComponentPosition());
        if (component.getType() == ComponentType.ANIMATION) {

        } else if (component.getType() == ComponentType.CLICKABLE) {
            componentData.putFloat("width", (((ClickableComponent)component).getWidth()));
            componentData.putFloat("height", (((ClickableComponent)component).getHeight()));
            componentData.putBoolean("singleTarget", (((ClickableComponent)component).isSingleTrigger()));
            componentData.putString("action", UtilityTool.getStringFromDataClass(createActionData(((ClickableComponent)component).getAction())));
        } else if (component.getType() == ComponentType.FLOATING_TEXT) {

        } else if (component.getType() == ComponentType.GRAPHICS) {
            componentData.putFloat("width", ((GraphicsComponent)component).getWidth());
            componentData.putFloat("height", ((GraphicsComponent)component).getHeight());
            componentData.putInt("layer", ((GraphicsComponent)component).getLayer());
            componentData.putString("textureType", ((GraphicsComponent)component).getTextureType().getId());
        } else if (component.getType() == ComponentType.IN_VIEW) {
            componentData.putFloat("width", ((InViewComponent)component).getWidth());
            componentData.putFloat("height", ((InViewComponent)component).getHeight());
            componentData.putString("action", UtilityTool.getStringFromDataClass(createActionData(((InViewComponent)component).getAction())));
        } else if (component.getType() == ComponentType.PHYSICS) {

        } else if (component.getType() == ComponentType.POINTER_DIRECTION) {

        }
        return componentData;
    }

    private ActionData createActionData(Action action) {
        ActionData actionData = new ActionData();
        actionData.setType(action.getType().getId());
        if (action.getType() == ActionType.DESTROY_ENTITY) {

        } else if (action.getType() == ActionType.HIGH_SCORE_RESET) {

        } else if (action.getType() == ActionType.LAUNCH_SCREEN) {

        } else if (action.getType() == ActionType.MULTI) {

        } else if (action.getType() == ActionType.PHYSICS) {

        } else if (action.getType() == ActionType.REPOSITION) {

        } else if (action.getType() == ActionType.SCORE) {

        } else if (action.getType() == ActionType.SPAWN_ENTITY) {

        } else if (action.getType() == ActionType.TELEPORT) {

        }
        return actionData;
    }
}