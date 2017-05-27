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
import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.actions.ResetScreenAction;
import com.kehxstudios.atlas.components.CameraComponent;
import com.kehxstudios.atlas.components.CollisionComponent;
import com.kehxstudios.atlas.components.GeneRocketComponent;
import com.kehxstudios.atlas.components.MusicComponent;
import com.kehxstudios.atlas.components.SoundComponent;
import com.kehxstudios.atlas.managers.PositionManager;
import com.kehxstudios.atlas.managers.SoundManager;
import com.kehxstudios.atlas.type.ActionType;
import com.kehxstudios.atlas.actions.DestroyEntityAction;
import com.kehxstudios.atlas.actions.HighScoreResetAction;
import com.kehxstudios.atlas.actions.LaunchScreenAction;
import com.kehxstudios.atlas.actions.MultiAction;
import com.kehxstudios.atlas.actions.PhysicsAction;
import com.kehxstudios.atlas.actions.RepositionAction;
import com.kehxstudios.atlas.actions.ScoreAction;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.type.ComponentType;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
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

    static { uniqueId = 0; }
    private static int uniqueId;
    private static int getUniqueId() { return ++uniqueId; }

    public static Entity createEntity(float x, float y) {
        Entity entity = new Entity();
        entity.id = getUniqueId();
        entity.position = new Vector2(x,y);
        EntityManager.getInstance().add(entity);
        return entity;
    }

    public static GraphicsComponent createGraphicsComponent(Entity entity, int layer, TextureType textureType) {
        GraphicsComponent graphics = new GraphicsComponent();
        graphics.entityId = entity.id;
        graphics.id = getUniqueId();
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
        PositionManager.getInstance().add(graphics);
        return graphics;
    }

    public static PhysicsComponent createPhysicsComponent(Entity entity, Vector2 maxAcceleration,
                                                          Vector2 maxVelocity) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.entityId = entity.id;
        physics.id = getUniqueId();
        physics.type = ComponentType.PHYSICS;
        physics.enabled = true;
        physics.position = new Vector2(entity.position);
        physics.acceleration = new Vector2(0,0);
        physics.maxAcceleration = maxAcceleration;
        physics.velocity = new Vector2(0,0);
        physics.maxVelocity = maxVelocity;
        EntityManager.getInstance().add(physics);
        PhysicsManager.getInstance().add(physics);
        PositionManager.getInstance().add(physics);
        return physics;
    }

    public static FloatingTextComponent createFloatingTextComponent(Entity entity, float scale, String label,
                                                                    String text, Color color) {
        FloatingTextComponent floatingText = new FloatingTextComponent();
        floatingText.entityId = entity.id;
        floatingText.id = getUniqueId();
        floatingText.type = ComponentType.FLOATING_TEXT;
        floatingText.enabled = true;
        floatingText.position = new Vector2(entity.position);
        floatingText.font = new BitmapFont();
        floatingText.scale = scale;
        floatingText.font.getData().setScale(scale, scale);
        floatingText.label = label;
        floatingText.text = text;
        floatingText.color = color;
        floatingText.layout = new GlyphLayout(floatingText.font,
                floatingText.label + floatingText.text);
        floatingText.layout.setText(floatingText.font,
                floatingText.label + floatingText.text,
                color, 0, Align.left, true);
        EntityManager.getInstance().add(floatingText);
        GraphicsManager.getInstance().add(floatingText);
        PositionManager.getInstance().add(floatingText);
        return floatingText;
    }

    public static CollisionComponent createCollisionComponent(Entity entity, float width, float height,
                                                              boolean staticPosition, boolean collided,
                                                              Action action) {
        CollisionComponent collision = new CollisionComponent();
        collision.entityId = entity.id;
        collision.id = getUniqueId();
        collision.type = ComponentType.COLLISION;
        collision.enabled = true;
        collision.bounds = new Rectangle(0, 0, width, height);
        collision.bounds.setCenter(entity.position);
        collision.staticPosition = staticPosition;
        collision.collided = collided;
        collision.action = action;
        EntityManager.getInstance().add(collision);
        PhysicsManager.getInstance().add(collision);
        PositionManager.getInstance().add(collision);
        return collision;
    }

    public static ClickableComponent createClickableComponent(Entity entity, float width, float height,
                                                              boolean singleTrigger, boolean triggered, Action action) {
        ClickableComponent clickable = new ClickableComponent();
        clickable.entityId = entity.id;
        clickable.id = getUniqueId();
        clickable.type = ComponentType.CLICKABLE;
        clickable.enabled = true;
        clickable.bounds = new Rectangle(0, 0, width, height);
        clickable.bounds.setCenter(entity.position);
        clickable.singleTrigger = singleTrigger;
        clickable.triggered = triggered;
        clickable.action = action;
        EntityManager.getInstance().add(clickable);
        InputManager.getInstance().add(clickable);
        PositionManager.getInstance().add(clickable);
        return clickable;
    }
    
    public static CameraComponent createCameraComponent(Entity entity, float width, float height, boolean flipped) {
        CameraComponent camera = new CameraComponent();
        camera.entityId = entity.id;
        camera.id = getUniqueId();
        camera.type = ComponentType.CAMERA;
        camera.enabled = true;
        camera.camera = new OrthographicCamera();
        camera.camera.position.set(entity.position.x, entity.position.y, 0);
        camera.camera.setToOrtho(flipped, width, height);
        camera.camera.update();
        EntityManager.getInstance().add(camera);
        GraphicsManager.getInstance().add(camera);
        PositionManager.getInstance().add(camera);
        return camera;
    }
    
    public static MusicComponent createMusicComponent(Entity entity, MusicType musicType, float volume) {
        MusicComponent music = new MusicComponent();
        music.entityId = entity.id;
        music.id = getUniqueId();
        music.type = ComponentType.MUSIC;
        music.enabled = true;
        music.musicType = musicType;
        music.music = SoundManager.getInstance().getMusic(musicType);
        music.volume = volume;
        music.music.setVolume(volume);
        music.music.setLooping(true);
        EntityManager.getInstance().add(music);
        SoundManager.getInstance().add(music);
        return music;
    }
    
    public static SoundComponent createSoundComponent(Entity entity, SoundType soundType, float volume) {
        SoundComponent sound = new SoundComponent();
        sound.entityId = entity.id;
        sound.id = getUniqueId();
        sound.type = ComponentType.SOUND;
        sound.enabled = true;
        sound.soundType = soundType;
        sound.sound = SoundManager.getInstance().getSound(soundType);
        sound.volume = volume;
        EntityManager.getInstance().add(sound);
        SoundManager.getInstance().add(sound);
        return sound;
    }
    
    public static GeneRocketComponent createGeneRocketComponent(Entity entity, ArrayList<Vector2> genes) {
        GeneRocketComponent geneRocket = new GeneRocketComponent();
        geneRocket.id = getUniqueId();
        geneRocket.entityId = entity.id;
        geneRocket.type = ComponentType.GENE_ROCKET;
        geneRocket.enabled = true;
        geneRocket.genes = genes;
        geneRocket.fitness = 0f;
        EntityManager.getInstance().add(geneRocket);
        return geneRocket;
    }
    
    public static DestroyEntityAction createDestroyEntityAction(Entity entity) {
        DestroyEntityAction destroyEntity = new DestroyEntityAction();
        destroyEntity.type = ActionType.DESTROY_ENTITY;
        destroyEntity.entityId = entity.id;
        return destroyEntity;
    }
    
    public static HighScoreResetAction createHighScoreResetAction(ScreenType screenType) {
        HighScoreResetAction highScoreReset = new HighScoreResetAction();
        highScoreReset.type = ActionType.HIGH_SCORE_RESET;
        highScoreReset.screenType = screenType;
        return highScoreReset;
    }
    
    public static MultiAction createMultiAction() {
        MultiAction multi = new MultiAction();
        multi.type = ActionType.MULTI;
        // Add array or hashmap
        return multi;
    }
    
    public static PhysicsAction createPhysicsAction(PhysicsComponent physicsComponent, Vector2 triggerValue) {
        PhysicsAction physics = new PhysicsAction();
        physics.type = ActionType.PHYSICS;
        physics.triggerValue = triggerValue;
        physics.physicsComponent = physicsComponent;
        return physics;
    }
    
    public static RepositionAction createRepositionAction(Entity entity, Vector2 triggerValue, boolean teleport) {
        RepositionAction reposition = new RepositionAction();
        reposition.type = ActionType.REPOSITION;
        reposition.position = entity.position;
        reposition.triggerValue = triggerValue;
        reposition.teleport = teleport;
        return reposition;
    }
    
    public static ScoreAction createScoreAction(ScreenType screenType, int scoreValue) {
        ScoreAction score = new ScoreAction();
        score.type = ActionType.SCORE;
        score.screenType = screenType;
        score.scoreValue = scoreValue;
        return score;
    }
    
    public static LaunchScreenAction createLaunchScreenAction(ScreenType screenType) {
        LaunchScreenAction launchScreen = new LaunchScreenAction();
        launchScreen.type = ActionType.LAUNCH_SCREEN;
        launchScreen.screenType = screenType;
        return launchScreen;
    }

    public static ResetScreenAction createResetScreenAction() {
        ResetScreenAction resetScreen = new ResetScreenAction();
        return resetScreen;
    }

}
