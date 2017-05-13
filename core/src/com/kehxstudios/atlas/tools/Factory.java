package com.kehxstudios.atlas.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.google.gwt.user.server.Util;
import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.actions.FollowAction;
import com.kehxstudios.atlas.components.CameraComponent;
import com.kehxstudios.atlas.components.CollisionComponent;
import com.kehxstudios.atlas.components.MusicComponent;
import com.kehxstudios.atlas.components.SoundComponent;
import com.kehxstudios.atlas.data.ActionData;
import com.kehxstudios.atlas.managers.SoundManager;
import com.kehxstudios.atlas.type.ActionType;
import com.kehxstudios.atlas.actions.DestroyEntityAction;
import com.kehxstudios.atlas.actions.HighScoreResetAction;
import com.kehxstudios.atlas.actions.LaunchScreenAction;
import com.kehxstudios.atlas.actions.MultiAction;
import com.kehxstudios.atlas.actions.PhysicsAction;
import com.kehxstudios.atlas.actions.RepositionAction;
import com.kehxstudios.atlas.actions.ScoreAction;
import com.kehxstudios.atlas.actions.SpawnEntityAction;
import com.kehxstudios.atlas.components.AnimationComponent;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.type.ComponentType;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.InViewComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.components.PointerDirectionComponent;
import com.kehxstudios.atlas.data.EntityData;
import com.kehxstudios.atlas.type.MusicType;
import com.kehxstudios.atlas.type.SoundType;
import com.kehxstudios.atlas.type.TextureType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.EntityManager;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.managers.InputManager;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.type.ScreenType;

/**
 * Created by ReidC on 2017-04-23.
 */

public class Factory {

    static {
        uniqueId = 0;
    }

    private static int uniqueId;

    public static Entity createEntity(EntityData entityData) {
        Entity entity = new Entity();
        entity.setId("Entity_" + ++uniqueId);
        entity.setPosition(entityData.getX(), entityData.getY());

        for (String componentString : entityData.data.values()) {
            entity.addComponent(createComponent(entity, UtilityTool.getComponentDataFromString(componentString)));
        }

        EntityManager.getInstance().addEntity(entity);

        return entity;
    }

    public static Component createComponent(Entity entity, ComponentData componentData) {
        try {
            ComponentType componentType = ComponentType.getTypeById(componentData.getType());
            Component component = (Component) ClassReflection.newInstance(componentType.getLoaderClass());
            component.setEntity(entity);
            component.setType(componentType);
            component.setUseComponentPosition(componentData.getUseComponentPosition());
            component.setUsePositionAsOffset(componentData.getUsePositionAsOffset());
            if (component.getUseComponentPosition() || component.getUsePositionAsOffset()) {
                component.setPosition(componentData.getX(), componentData.getY());
            }
            component.setEnabled(componentData.isEnabled());

            EntityManager.getInstance().addComponent(entity, component);

            if (componentType == ComponentType.ANIMATION) {
                AnimationComponent animation = (AnimationComponent)component;
                // TO BE ADDED
                GraphicsManager.getInstance().add(animation);
                return animation;
            } else if (componentType == ComponentType.CAMERA) {
                CameraComponent camera = (CameraComponent)component;
                camera.setWidth(componentData.getFloat("width", 0));
                camera.setHeight(componentData.getFloat("height", 0));
                camera.setFlipped(componentData.getBoolean("flipped", false));
                camera.getCamera().setToOrtho(camera.isFlipped(), camera.getWidth(), camera.getHeight());
                camera.update();
                GraphicsManager.getInstance().add(camera);
                return camera;
            } else if (componentType == ComponentType.CLICKABLE) {
                ClickableComponent clickable = (ClickableComponent)component;
                clickable.setWidth(componentData.getFloat("width", 0));
                clickable.setHeight(componentData.getFloat("height", 0));
                clickable.setSingleTrigger(componentData.getBoolean("singleTrigger", false));
                clickable.setTriggered(false);
                clickable.setAction(createAction(entity, UtilityTool.getActionDataFromString(componentData.getString("action", "Void"))));
                InputManager.getInstance().add(clickable);
                return clickable;
            } else if (componentType == ComponentType.COLLISION) {
                CollisionComponent collision = (CollisionComponent)component;
                collision.setWidth(componentData.getFloat("width", 0));
                collision.setHeight(componentData.getFloat("height", 0));
                collision.setStaticPosition(componentData.getBoolean("staticPosition", true));
                collision.setSingleTrigger(componentData.getBoolean("singleTrigger", false));
                collision.setTriggered(componentData.getBoolean("triggered", false));
                collision.setAction(createAction(entity, UtilityTool.getActionDataFromString(componentData.getString("action", "Void"))));
                PhysicsManager.getInstance().add(collision);
                return collision;
            } else if (componentType == ComponentType.FLOATING_TEXT) {
                FloatingTextComponent floatingText = (FloatingTextComponent)component;
                floatingText.setFont(new BitmapFont());
                floatingText.setScale(componentData.getFloat("scale", 1));
                floatingText.setLabel(componentData.getString("label", "-"));
                floatingText.setText(componentData.getString("text", "-"));
                floatingText.setLayout(new GlyphLayout(floatingText.getFont(),
                        floatingText.getLabel() + floatingText.getText()));
                floatingText.getLayout().setText(floatingText.getFont(),
                        floatingText.getLabel() + floatingText.getText(),
                        Color.BLACK, 0, Align.left, true);
                GraphicsManager.getInstance().add(floatingText);
                return floatingText;
            } else if (componentType == ComponentType.GRAPHICS) {
                GraphicsComponent graphics = (GraphicsComponent)component;
                graphics.setWidth(componentData.getFloat("width", 0));
                graphics.setHeight(componentData.getFloat("height", 0));
                graphics.setLayer(componentData.getInt("layer", 0));
                graphics.setTextureType(TextureType.getTypeFromId(componentData.getString("textureType", "Void")));
                if (graphics.getWidth() == 0) {
                    graphics.setWidth(graphics.getTextureType().getWidth());
                }
                if (graphics.getHeight() == 0) {
                    graphics.setHeight(graphics.getTextureType().getHeight());
                }
                if (graphics.getTextureType() == TextureType.VOID) {
                    graphics.setEnabled(false);
                }
                GraphicsManager.getInstance().add(graphics);
                return graphics;
            } else if (componentType == ComponentType.IN_VIEW) {
                InViewComponent inView = (InViewComponent)component;
                inView.setWidth(componentData.getFloat("width", 0));
                inView.setHeight(componentData.getFloat("height", 0));
                inView.setAction(createAction(entity, UtilityTool.getActionDataFromString(componentData.getString("action", "Void"))));
                // ADD TO MANAGER
                return inView;
            } else if (componentType == ComponentType.MUSIC) {
                MusicComponent music = (MusicComponent)component;
                music.setMusicType(MusicType.getTypeById(componentData.getString("musicType", "Void")));
                music.setVolume(componentData.getFloat("volume", 0f));
                SoundManager.getInstance().add(music);
                return music;
            } else if (componentType == ComponentType.PHYSICS) {
                PhysicsComponent physics = (PhysicsComponent)component;
                physics.setAcceleration(componentData.getFloat("acceleraton_x", 0),
                        componentData.getFloat("acceleration_y", 0));
                physics.setMaxAcceleration(componentData.getFloat("maxAcceleration_x", 0),
                        componentData.getFloat("maxAcceleration_y", 0));
                physics.setVelocity(componentData.getFloat("velocity_x", 0),
                        componentData.getFloat("velocity_y", 0));
                physics.setMaxVelocity(componentData.getFloat("maxVelocity_x", 0),
                        componentData.getFloat("maxVelocity_y", 0));
                PhysicsManager.getInstance().add(physics);
                return physics;
            } else if (componentType == ComponentType.POINTER_DIRECTION) {
                PointerDirectionComponent pointerDirection = (PointerDirectionComponent)component;
                // TO BE ADDED
                return pointerDirection;
            } else if (componentType == ComponentType.SOUND) {
                SoundComponent sound = (SoundComponent)component;
                sound.setSoundType(SoundType.getTypeById(componentData.getString("soundType", "Void")));
                sound.setVolume(componentData.getFloat("volume", 0f));
                SoundManager.getInstance().add(sound);
                return sound;
            }
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Action createAction(Entity entity, ActionData actionData) {
        try {
            ActionType actionType = ActionType.getTypeById(actionData.getType());
            Action action = (Action) ClassReflection.newInstance(actionType.getLoaderClass());
            action.setType(actionType);

            if (actionType == ActionType.DESTROY_ENTITY) {
                DestroyEntityAction destroyEntity = (DestroyEntityAction)action;
                destroyEntity.setEntity(entity);
                return destroyEntity;
            } else if (actionType == ActionType.FOLLOW) {
                FollowAction follow = (FollowAction)action;
                follow.setVerticalAllowed(actionData.getBoolean("veritical", false));
                follow.setHorizontalAllowed(actionData.getBoolean("horizontal", false));
                follow.setPosition(entity.getPosition());
                return follow;
            } else if (actionType == ActionType.HIGH_SCORE_RESET) {
                HighScoreResetAction highScoreReset = (HighScoreResetAction)action;
                highScoreReset.setScreenType(ScreenType.getTypeById(actionData.getString("screenType", "Void")));
                return highScoreReset;
            } else if (actionType == ActionType.LAUNCH_SCREEN) {
                LaunchScreenAction launchScreen = (LaunchScreenAction)action;
                launchScreen.setScreenType(ScreenType.getTypeById(actionData.getString("screenType", "Void")));
                return launchScreen;
            } else if (actionType == ActionType.MULTI) {
                MultiAction multi = (MultiAction)action;
                for (String data : actionData.data.values()) {
                    multi.addAction(createAction(entity, UtilityTool.getActionDataFromString(data)),99);
                }
                return multi;
            } else if (actionType == ActionType.PHYSICS) {
                PhysicsAction physics = (PhysicsAction)action;
                physics.setTriggerValue(actionData.getFloat("actionValue_x", 0),
                        actionData.getFloat("actionValue_y", 0));
                physics.setPhysicsComponent((PhysicsComponent)entity.getComponentOfType(ComponentType.PHYSICS));
                return physics;
            } else if (actionType == ActionType.REPOSITION) {
                RepositionAction reposition = (RepositionAction)action;
                reposition.setPosition(entity.getPosition());
                reposition.setActionValue(actionData.getFloat("actionValue_x", 0),
                        actionData.getFloat("actionValue_y", 0));
                reposition.setTeleportToActionValue(actionData.getBoolean("teleport", false));
                return reposition;
            } else if (actionType == ActionType.SCORE) {
                ScoreAction score = (ScoreAction)action;
                score.setScoreValue(actionData.getInt("actionValue", 0));
                return score;
            } else if (actionType == ActionType.SPAWN_ENTITY) {
                SpawnEntityAction spawnEntity = (SpawnEntityAction)action;
                spawnEntity.setEntityData(UtilityTool.getEntityDataFromString(actionData.getString("entityData", "Void")));
                return action;
            }
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
