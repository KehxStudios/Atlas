/*******************************************************************************
 * Copyright 2017 See AUTHORS file.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
 * associated documentation files (the "Software"), to deal in the Software without restriction, 
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial 
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/

package com.kehxstudios.atlas.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.actions.FollowAction;
import com.kehxstudios.atlas.components.CameraComponent;
import com.kehxstudios.atlas.components.CollisionComponent;
import com.kehxstudios.atlas.components.GeneRocketComponent;
import com.kehxstudios.atlas.components.MusicComponent;
import com.kehxstudios.atlas.components.SoundComponent;
import com.kehxstudios.atlas.data.ActionData;
import com.kehxstudios.atlas.managers.GameManager;
import com.kehxstudios.atlas.managers.SoundManager;
import com.kehxstudios.atlas.screens.AScreen;
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
import com.kehxstudios.atlas.components.PhysicsComponent;
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

import java.util.ArrayList;

/**
 * Used to turn Entity/Component/Action data classes to their full active class
 */

public class Factory {

    static {
        uniqueId = 0;
    }

    private static int uniqueId;

    public static Entity createEntity(EntityData entityData) {
        Entity entity = new Entity();
        entity.position.x = entityData.x;
        entity.position.y = entityData.y;
        entity.id = ++uniqueId;

        for (String componentString : entityData.data.values()) {
            createComponent(entity, UtilityTool.getComponentDataFromString(componentString));
        }

        EntityManager.getInstance().add(entity);

        return entity;
    }

    public static Entity createEntity(float x, float y) {
        Entity entity = new Entity();
        entity.id = ++uniqueId;
        entity.position = new Vector2(x,y);
        EntityManager.getInstance().add(entity);
        return entity;
    }

    public static GraphicsComponent createGraphicsComponent(Entity entity, int layer, TextureType textureType) {
        GraphicsComponent graphics = new GraphicsComponent();
        graphics.entityId = entity.id;
        graphics.id = ++uniqueId;
        graphics.type = ComponentType.GRAPHICS;
        graphics.enabled = true;
        graphics.rotation = 0;
        graphics.layer = layer;
        graphics.textureType = textureType;
        graphics.texture = GraphicsManager.getInstance().getTexture(textureType);
        graphics.bounds = new Rectangle(0,0, textureType.getWidth(), textureType.getHeight());
        graphics.bounds.setCenter(entity.position);
        EntityManager.getInstance().add(graphics);
        GraphicsManager.getInstance().add(graphics);
        return graphics;
    }

    public static PhysicsComponent createPhysicsComponent(Entity entity, Vector2 maxAcceleration,
                                                          Vector2 maxVelocity) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.entityId = entity.id;
        physics.id = ++uniqueId;
        physics.type = ComponentType.PHYSICS;
        physics.enabled = true;
        physics.acceleration = new Vector2(0,0);
        physics.maxAcceleration = maxAcceleration;
        physics.velocity = new Vector2(0,0);
        physics.maxVelocity = maxVelocity;
        EntityManager.getInstance().add(physics);
        PhysicsManager.getInstance().add(physics);
        return physics;
    }

    public static FloatingTextComponent createFloatingTextComponent(Entity entity, float scale, String label,
                                                                    String text, Color color) {
        FloatingTextComponent floatingText = new FloatingTextComponent();
        floatingText.entityId = entity.id;
        floatingText.id = ++uniqueId;
        floatingText.type = ComponentType.FLOATING_TEXT;
        floatingText.enabled = true;
        floatingText.position = entity.position;
        floatingText.font = new BitmapFont();
        floatingText.scale = scale;
        floatingText.font.getData().setScale(scale, scale);
        floatingText.label = label;
        floatingText.text = text;
        floatingText.layout = new GlyphLayout(floatingText.font,
                floatingText.label + floatingText.text);
        floatingText.layout.setText(floatingText.font,
                floatingText.label + floatingText.text,
                color, 0, Align.left, true);
        EntityManager.getInstance().add(floatingText);
        GraphicsManager.getInstance().add(floatingText);
        return floatingText;
    }

    public static CollisionComponent createCollisionComponent(Entity entity, float width, float height,
                                                              boolean staticPosition, boolean collided,
                                                              Action action) {
        CollisionComponent collision = new CollisionComponent();
        collision.entityId = entity.id;
        collision.id = ++uniqueId;
        collision.type = ComponentType.CLICKABLE;
        collision.enabled = true;
        collision.bounds = new Rectangle(0, 0, width, height);
        collision.bounds.setCenter(entity.position);
        collision.staticPosition = staticPosition;
        collision.collided = collided;
        collision.action = action;
        return collision;
    }

    public static ClickableComponent createClickableComponent(Entity entity, float width, float height,
                                                              boolean singleTrigger, boolean triggered, Action action) {
        ClickableComponent clickable = new ClickableComponent();
        clickable.entityId = entity.id;
        clickable.id = ++uniqueId;
        clickable.type = ComponentType.CLICKABLE;
        clickable.enabled = true;
        clickable.bounds = new Rectangle(0, 0, width, height);
        clickable.bounds.setCenter(entity.position);
        clickable.singleTrigger = singleTrigger;
        clickable.triggered = triggered;
        clickable.action = action;
        EntityManager.getInstance().add(clickable);
        InputManager.getInstance().add(clickable);
        return clickable;
    }

    public static LaunchScreenAction createLaunchScreenAction(ScreenType screenType) {
        LaunchScreenAction launchScreen = new LaunchScreenAction();
        launchScreen.type = ActionType.LAUNCH_SCREEN;
        launchScreen.screenType = screenType;
        return launchScreen;
    }

    public static Component createComponent(Entity entity, ComponentData componentData) {
        try {
            ComponentType componentType = ComponentType.getTypeById(componentData.type);
            Component component = (Component) ClassReflection.newInstance(componentType.getLoaderClass());
            component.id = ++uniqueId;
            component.entityId = entity.id;
            component.type = componentType;
            component.enabled = componentData.enabled;

            EntityManager.getInstance().add(component);

            if (componentType == ComponentType.ANIMATION) {
                AnimationComponent animation = (AnimationComponent)component;
                // TO BE ADDED
                GraphicsManager.getInstance().add(animation);
                return animation;
            } else if (componentType == ComponentType.CAMERA) {
                CameraComponent camera = (CameraComponent)component;
                camera.camera = new OrthographicCamera();
                camera.camera.position.set(entity.position.x, entity.position.y, 0);
                camera.camera.setToOrtho(componentData.getBoolean("flipped", false), 
                                              componentData.getFloat("width", 0), 
                                              componentData.getFloat("height", 0));
                GraphicsManager.getInstance().add(camera);
                return camera;
            } else if (componentType == ComponentType.CLICKABLE) {
                ClickableComponent clickable = (ClickableComponent)component;
                float width = componentData.getFloat("width", 0);
                float height = componentData.getFloat("height", 0);
                clickable.bounds = new Rectangle(entity.position.x - width/2, entity.position.y - height/2, width, height);
                clickable.singleTrigger = componentData.getBoolean("singleTrigger", false);
                clickable.triggered = false;
                clickable.action = createAction(entity, UtilityTool.getActionDataFromString(componentData.getString("action", "Void")));
                InputManager.getInstance().add(clickable);
                return clickable;
            } else if (componentType == ComponentType.COLLISION) {
                CollisionComponent collision = (CollisionComponent)component;
                float width = componentData.getFloat("width", 0);
                float height = componentData.getFloat("height", 0);
                collision.bounds = new Rectangle(entity.position.x - width/2, entity.position.y - height/2, width, height);
               
                collision.staticPosition = componentData.getBoolean("staticPosition", true);
                collision.collided = false;
                collision.action = createAction(entity, UtilityTool.getActionDataFromString(componentData.getString("action", "Void")));
                PhysicsManager.getInstance().add(collision);
                return collision;
            } else if (componentType == ComponentType.FLOATING_TEXT) {
                FloatingTextComponent floatingText = (FloatingTextComponent)component;
                floatingText.position = entity.position;
                floatingText.font = new BitmapFont();
                floatingText.scale = componentData.getFloat("scale", 1);
                floatingText.label = componentData.getString("label", "-");
                floatingText.text = componentData.getString("text", "-");
                floatingText.layout = new GlyphLayout(floatingText.font,
                        floatingText.label + floatingText.text);
                floatingText.layout.setText(floatingText.font,
                        floatingText.label + floatingText.text,
                        Color.BLACK, 0, Align.left, true);
                GraphicsManager.getInstance().add(floatingText);
                return floatingText;
            } else if (componentType == ComponentType.GENE_ROCKET) {
                GeneRocketComponent geneRocket = (GeneRocketComponent)component;
                geneRocket.genes = new ArrayList<Vector2>(componentData.getInt("numberOfGenes", 0));
                geneRocket.fitness = 0f;
                return geneRocket;
            }else if (componentType == ComponentType.GRAPHICS) {
                GraphicsComponent graphics = (GraphicsComponent)component;
                float width = componentData.getFloat("width", 0);
                float height = componentData.getFloat("height", 0);
                graphics.bounds = new Rectangle(0 ,0 , width, height);
                graphics.bounds.setCenter(entity.position);
                graphics.layer = componentData.getInt("layer", 0);
                graphics.rotation = componentData.getFloat("rotation", 0);
                graphics.textureType = TextureType.getTypeFromId(componentData.getString("textureType", "Void"));
                if (graphics.textureType == TextureType.VOID) {
                    graphics.enabled = false;
                } else {
                    graphics.texture = GraphicsManager.getInstance().getTexture(graphics.textureType);
                }
                GraphicsManager.getInstance().add(graphics);
                return graphics;
            } else if (componentType == ComponentType.MUSIC) {
                MusicComponent music = (MusicComponent)component;
                music.musicType = MusicType.getTypeById(componentData.getString("musicType", "Void"));
                music.music = SoundManager.getInstance().getMusic(music.musicType);
                music.volume = componentData.getFloat("volume", 0f);
                SoundManager.getInstance().add(music);
                return music;
            } else if (componentType == ComponentType.PHYSICS) {
                PhysicsComponent physics = (PhysicsComponent)component;
                physics.acceleration.set(componentData.getFloat("acceleraton_x", 0),
                        componentData.getFloat("acceleration_y", 0));
                physics.maxAcceleration.set(componentData.getFloat("maxAcceleration_x", 0),
                        componentData.getFloat("maxAcceleration_y", 0));
                physics.velocity.set(componentData.getFloat("velocity_x", 0),
                        componentData.getFloat("velocity_y", 0));
                physics.maxVelocity.set(componentData.getFloat("maxVelocity_x", 0),
                        componentData.getFloat("maxVelocity_y", 0));
                PhysicsManager.getInstance().add(physics);
                return physics;
            } else if (componentType == ComponentType.SOUND) {
                SoundComponent sound = (SoundComponent)component;
                sound.soundType = SoundType.getTypeById(componentData.getString("soundType", "Void"));
                sound.volume = componentData.getFloat("volume", 0f);
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
            ActionType actionType = ActionType.getTypeById(actionData.type);
            Action action = (Action) ClassReflection.newInstance(actionType.getLoaderClass());
            action.type = actionType;

            if (actionType == ActionType.DESTROY_ENTITY) {
                DestroyEntityAction destroyEntity = (DestroyEntityAction)action;
                destroyEntity.entityId = entity.id;
                return destroyEntity;
            } else if (actionType == ActionType.FOLLOW) {
                FollowAction follow = (FollowAction)action;
                follow.verticalAllowed = (actionData.getBoolean("veritical", false));
                follow.horizontalAllowed = actionData.getBoolean("horizontal", false);
                follow.position.set(entity.position);
                return follow;
            } else if (actionType == ActionType.HIGH_SCORE_RESET) {
                HighScoreResetAction highScoreReset = (HighScoreResetAction)action;
                highScoreReset.screenType = ScreenType.getTypeById(actionData.getString("screenType", "Void"));
                return highScoreReset;
            } else if (actionType == ActionType.LAUNCH_SCREEN) {
                LaunchScreenAction launchScreen = (LaunchScreenAction)action;
                launchScreen.screenType = ScreenType.getTypeById(actionData.getString("screenType", "Void"));
                return launchScreen;
            } else if (actionType == ActionType.MULTI) {
                MultiAction multi = (MultiAction)action;
                for (String data : actionData.data.values()) {
                    multi.actions.add(createAction(entity, UtilityTool.getActionDataFromString(data)));
                }
                return multi;
            } else if (actionType == ActionType.PHYSICS) {
                PhysicsAction physics = (PhysicsAction)action;
                physics.triggerValue.set(actionData.getFloat("actionValue_x", 0),
                        actionData.getFloat("actionValue_y", 0));
                physics.physicsComponent = (PhysicsComponent)entity.getComponentOfType(ComponentType.PHYSICS);
                return physics;
            } else if (actionType == ActionType.REPOSITION) {
                RepositionAction reposition = (RepositionAction)action;
                reposition.position = entity.position;
                reposition.actionValue.set(actionData.getFloat("actionValue_x", 0),
                        actionData.getFloat("actionValue_y", 0));
                reposition.teleportToActionValue = actionData.getBoolean("teleport", false);
                return reposition;
            } else if (actionType == ActionType.SCORE) {
                ScoreAction score = (ScoreAction)action;
                score.scoreValue = actionData.getInt("actionValue", 0);
                score.screen = (AScreen)GameManager.getInstance().getScreen();
                return score;
            } else if (actionType == ActionType.SPAWN_ENTITY) {
                SpawnEntityAction spawnEntity = (SpawnEntityAction)action;
                spawnEntity.entityData = UtilityTool.getEntityDataFromString(actionData.getString("entityData", "Void"));
                return action;
            }
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
