package com.kehxstudios.atlas.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.google.gwt.aria.client.LandmarkRole;
import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.actions.ActionData;
import com.kehxstudios.atlas.actions.ActionType;
import com.kehxstudios.atlas.actions.DestroyEntityAction;
import com.kehxstudios.atlas.actions.HighScoreResetAction;
import com.kehxstudios.atlas.actions.LaunchScreenAction;
import com.kehxstudios.atlas.actions.MultiAction;
import com.kehxstudios.atlas.actions.PhysicsAction;
import com.kehxstudios.atlas.actions.RepositionAction;
import com.kehxstudios.atlas.actions.ScoreAction;
import com.kehxstudios.atlas.actions.SpawnEntityAction;
import com.kehxstudios.atlas.actions.TeleportAction;
import com.kehxstudios.atlas.components.AnimationComponent;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.ComponentData;
import com.kehxstudios.atlas.components.ComponentType;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.InViewComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.components.PointerDirectionComponent;
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
        try {
            AScreen screen = (AScreen) ClassReflection.newInstance(ScreenType.getTypeById(screenData.getType()).getLoaderClass());



        } catch (ReflectionException e) {
            e.printStackTrace();
        }


        return null;
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

            EntityManager.getInstance().addComponent(entity, component);

            if (component.getType() == ComponentType.ANIMATION) {
                AnimationComponent animation = (AnimationComponent)component;
                // TO BE ADDED
                GraphicsManager.getInstance().add(animation);
                return animation;
            } else if (component.getType() == ComponentType.CLICKABLE) {
                ClickableComponent clickable = (ClickableComponent)component;
                clickable.setWidth(componentData.getFloat("width", 0));
                clickable.setHeight(componentData.getFloat("height", 0));
                clickable.setSingleTrigger(componentData.getBoolean("singleTrigger", false));
                clickable.setAction(createAction(entity, UtilityTool.getActionDataFromString(componentData.getString("action", "Void"))));
                InputManager.getInstance().add(clickable);
                return clickable;
            } else if (component.getType() == ComponentType.FLOATING_TEXT) {
                FloatingTextComponent floatingText = (FloatingTextComponent)component;
                floatingText.setFont(new BitmapFont());
                floatingText.setScale(2f);
                floatingText.setLabel(componentData.getString("label", "-"));
                floatingText.setText(componentData.getString("text", "-"));
                floatingText.setLayout(new GlyphLayout(floatingText.getFont(),
                        floatingText.getLabel() + floatingText.getText()));
                floatingText.getLayout().setText(floatingText.getFont(),
                        floatingText.getLabel() + floatingText.getText(),
                        Color.BLACK, 0, Align.center, true);
                GraphicsManager.getInstance().add(floatingText);
                return floatingText;
            } else if (component.getType() == ComponentType.GRAPHICS) {
                GraphicsComponent graphics = (GraphicsComponent)component;
                graphics.setWidth(componentData.getFloat("width", 0));
                graphics.setHeight(componentData.getFloat("height", 0));
                graphics.setLayer(componentData.getInt("layer", 0));
                graphics.setTextureType(TextureType.getTypeFromId(componentData.getString("textureType", "Void")));
                GraphicsManager.getInstance().add(graphics);
                return graphics;
            } else if (component.getType() == ComponentType.IN_VIEW) {
                InViewComponent inView = (InViewComponent)component;
                inView.setWidth(componentData.getFloat("width", 0));
                inView.setHeight(componentData.getFloat("height", 0));
                inView.setAction(createAction(entity, UtilityTool.getActionDataFromString(componentData.getString("action", "Void"))));
                return inView;
            } else if (component.getType() == ComponentType.PHYSICS) {
                PhysicsComponent physics = (PhysicsComponent)component;
                physics.setAcceleration(componentData.getFloat("acceleraton_x", 0),
                        componentData.getFloat("acceleration_y", 0));
                physics.setMaxAcceleration(componentData.getFloat("maxAcceleration_x", 0),
                        componentData.getFloat("maxAcceleration_y", 0));
                physics.setVelocity(componentData.getFloat("velocity_x", 0),
                        componentData.getFloat("velocity_y", 0));
                physics.setMaxVelocity(componentData.getFloat("maxVelocity_x", 0),
                        componentData.getFloat("maxVelocity_y", 0));
                physics.setBounds( new Rectangle(componentData.getFloat("bounds_x", 0),
                        componentData.getFloat("bounds_y", 0), componentData.getFloat("bounds_width", 0),
                        componentData.getFloat("bounds_height", 0)));
                physics.setCollidable(componentData.getBoolean("collidable", false));
                physics.setCollided(componentData.getBoolean("collided", false));
                PhysicsManager.getInstance().add(physics);
                return physics;
            } else if (component.getType() == ComponentType.POINTER_DIRECTION) {
                PointerDirectionComponent pointerDirection = (PointerDirectionComponent)component;
                // TO BE ADDED
                return pointerDirection;
            }
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Action createAction(Entity entity, ActionData actionData) {
        try {
            Action action = (Action) ClassReflection.newInstance(ActionType.getType(actionData.getType()).getLoaderClass());
            action.setType(ActionType.getType(actionData.getString("type", "Void")));

            if (action.getType() == ActionType.DESTROY_ENTITY) {
                DestroyEntityAction destroyEntity = (DestroyEntityAction)action;
                // TO BE ADDED
                return destroyEntity;
            } else if (action.getType() == ActionType.HIGH_SCORE_RESET) {
                HighScoreResetAction highScoreReset = (HighScoreResetAction)action;
                highScoreReset.setScreenType(ScreenType.getTypeById(actionData.getString("screenType", "Void")));
                return highScoreReset;
            } else if (action.getType() == ActionType.LAUNCH_SCREEN) {
                LaunchScreenAction launchScreen = (LaunchScreenAction)action;
                launchScreen.setScreenType(ScreenType.getTypeById(actionData.getString("screenType", "Void")));
                return launchScreen;
            } else if (action.getType() == ActionType.MULTI) {
                MultiAction multi = (MultiAction)action;
                // TO BE ADDED
                return multi;
            } else if (action.getType() == ActionType.PHYSICS) {
                PhysicsAction physics = (PhysicsAction)action;
                physics.setTriggerValue(actionData.getFloat("triggerValue_x", 0),
                        actionData.getFloat("triggerValue_y", 0));
                physics.setPhysicsComponent((PhysicsComponent)entity.getComponentOfType(ComponentType.PHYSICS));
                return physics;
            } else if (action.getType() == ActionType.REPOSITION) {
                RepositionAction reposition = (RepositionAction)action;
                reposition.setPosition(entity.getPosition());
                reposition.setActionValue(actionData.getFloat("actionValue_x", 0),
                        actionData.getFloat("actionValue_y", 0));
                return reposition;
            } else if (action.getType() == ActionType.SCORE) {
                ScoreAction score = (ScoreAction)action;
                score.setScoreValue(actionData.getInt("scoreValue", 0));
                return score;
            } else if (action.getType() == ActionType.SPAWN_ENTITY) {
                SpawnEntityAction spawnEntity = (SpawnEntityAction)action;
                spawnEntity.setEntityData(UtilityTool.getEntityDataFromString(actionData.getString("entityData", "Void")));
                return action;
            } else if (action.getType() == ActionType.TELEPORT) {
                TeleportAction teleport = (TeleportAction)action;
                teleport.setPosition(entity.getPosition());
                teleport.setTeleportPosition(new Vector2(actionData.getFloat("teleportPosition_x", 0),
                        actionData.getFloat("teleportPosition_y", 0)));
                return teleport;
            }
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
        return null;
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