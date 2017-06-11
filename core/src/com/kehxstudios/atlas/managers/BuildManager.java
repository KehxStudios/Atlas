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

package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.actions.ChangeFloatingTextAction;
import com.kehxstudios.atlas.actions.EnableComponentAction;
import com.kehxstudios.atlas.actions.FollowAction;
import com.kehxstudios.atlas.actions.LaunchWebsiteAction;
import com.kehxstudios.atlas.actions.ResetScreenAction;
import com.kehxstudios.atlas.actions.SoundAction;
import com.kehxstudios.atlas.components.CameraComponent;
import com.kehxstudios.atlas.components.CollisionComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.GeneRocketComponent;
import com.kehxstudios.atlas.components.MusicComponent;
import com.kehxstudios.atlas.components.SoundComponent;
import com.kehxstudios.atlas.data.HighScores;
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
import com.kehxstudios.atlas.type.ScreenType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Used to turn Entity/Component/Action data classes to their full active class
 */

public class BuildManager extends Manager {

    private EntityManager entityManager;
    private GraphicsManager graphicsManager;
    private InputManager inputManager;
    private PhysicsManager physicsManager;
    private PositionManager positionManager;
    private ScreenManager screenManager;
    private SoundManager soundManager;

    public BitmapFont regularSmallFont, regularLargeFont;
    public BitmapFont boldSmallFont, boldLargeFont;

    private int uniqueId;

    public BuildManager(GameManager gm) {
        super(gm);
        init();
    }

    @Override
    public void tick(float delta) {

    }

    @Override
    public void add(Component component) {

    }

    @Override
    public void remove(Component component) {

    }

    @Override
    protected void init() {
        entityManager = gm.getEntityManager();
        graphicsManager = gm.getGraphicsManager();
        inputManager = gm.getInputManager();
        physicsManager = gm.getPhysicsManager();
        positionManager = gm.getPositionManager();
        screenManager = gm.getScreenManager();
        soundManager = gm.getSoundManager();

        uniqueId = 0;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/TruenoRg.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 14;
        regularSmallFont = generator.generateFont(parameter);
        parameter.size = 30;
        regularLargeFont = generator.generateFont(parameter);
        generator.dispose();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/TruenoBd.ttf"));
        boldLargeFont = generator.generateFont(parameter);
        parameter.size = 14;
        boldSmallFont = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    protected void loadSettings() {
        uniqueId = 0;
    }

    @Override
    protected void removeSettings() {

    }

    private int getUniqueId() { return ++uniqueId; }

    public Entity createEntity(float x, float y) {
        Entity entity = new Entity();
        entity.id = getUniqueId();
        entity.position = new Vector2(x,y);
        entity.components = new HashMap<Integer, ComponentType>();
        entityManager.add(entity);
        return entity;
    }

    public GraphicsComponent createGraphicsComponent(Entity entity, int layer, TextureType textureType) {
        GraphicsComponent graphics = new GraphicsComponent();
        graphics.entityId = entity.id;
        graphics.id = getUniqueId();
        graphics.type = ComponentType.GRAPHICS;
        graphics.enabled = true;
        graphics.layer = layer;
        graphics.rotate = false;
        graphics.textureType = textureType;
        graphics.sprite = new Sprite(graphicsManager.getTexture(textureType));
        graphics.sprite.setCenter(entity.position.x, entity.position.y);
        graphics.sprite.setOriginCenter();
        entityManager.add(graphics);
        graphicsManager.add(graphics);
        positionManager.add(graphics);
        return graphics;
    }

    public PhysicsComponent createPhysicsComponent(Entity entity, Vector2 maxAcceleration,
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
        entityManager.add(physics);
        physicsManager.add(physics);
        positionManager.add(physics);
        return physics;
    }

    public FloatingTextComponent createFloatingTextComponent(Entity entity, boolean large, boolean
            bold, String label, String text, Color color) {
        FloatingTextComponent floatingText = new FloatingTextComponent();
        floatingText.entityId = entity.id;
        floatingText.id = getUniqueId();
        floatingText.type = ComponentType.FLOATING_TEXT;
        floatingText.enabled = true;
        floatingText.position = new Vector2(entity.position);
        if (large) {
            if (bold) {
                floatingText.font = boldLargeFont;
            } else {
                floatingText.font = regularLargeFont;
            }
        } else {
            if (bold) {
                floatingText.font = boldSmallFont;
            } else {
                floatingText.font = regularSmallFont;
            }
        }
        floatingText.label = label;
        floatingText.text = text;
        floatingText.color = color;
        floatingText.layout = new GlyphLayout(floatingText.font,
                floatingText.label + floatingText.text);
        floatingText.layout.setText(floatingText.font,
                floatingText.label + floatingText.text,
                color, 0, Align.left, true);
        entityManager.add(floatingText);
        graphicsManager.add(floatingText);
        positionManager.add(floatingText);
        return floatingText;
    }

    public CollisionComponent createCollisionComponent(Entity entity, float width, float height,
                                                       boolean staticPosition, boolean dynamicCollision,
                                                       boolean collided, Action action) {
        CollisionComponent collision = new CollisionComponent();
        collision.entityId = entity.id;
        collision.id = getUniqueId();
        collision.type = ComponentType.COLLISION;
        collision.enabled = true;
        collision.bounds = new Rectangle(0, 0, width, height);
        collision.bounds.setCenter(entity.position);
        collision.staticPosition = staticPosition;
        collision.dynamicCollision = dynamicCollision;
        collision.collided = collided;
        collision.action = action;
        entityManager.add(collision);
        physicsManager.add(collision);
        positionManager.add(collision);
        return collision;
    }

    public ClickableComponent createClickableComponent(Entity entity, float width, float height,
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
        clickable.clickedPosition = new Vector2(0,0);
        clickable.action = action;
        entityManager.add(clickable);
        inputManager.add(clickable);
        positionManager.add(clickable);
        return clickable;
    }
    
    public CameraComponent createCameraComponent(Entity entity, float width, float height, boolean flipped) {
        CameraComponent camera = new CameraComponent();
        camera.entityId = entity.id;
        camera.id = getUniqueId();
        camera.type = ComponentType.CAMERA;
        camera.enabled = true;
        camera.camera = new OrthographicCamera();
        camera.camera.position.set(entity.position.x, entity.position.y, 0);
        camera.camera.setToOrtho(flipped, width, height);
        camera.camera.update();
        entityManager.add(camera);
        graphicsManager.add(camera);
        inputManager.add(camera);
        positionManager.add(camera);
        return camera;
    }
    
    public MusicComponent createMusicComponent(Entity entity, MusicType musicType, float volume) {
        MusicComponent music = new MusicComponent();
        music.entityId = entity.id;
        music.id = getUniqueId();
        music.type = ComponentType.MUSIC;
        music.enabled = true;
        music.musicType = musicType;
        music.music = soundManager.getMusic(musicType);
        music.volume = volume;
        music.music.setVolume(volume);
        music.music.setLooping(true);
        entityManager.add(music);
        soundManager.add(music);
        return music;
    }
    
    public SoundComponent createSoundComponent(Entity entity, SoundType soundType, float volume) {
        SoundComponent sound = new SoundComponent();
        sound.entityId = entity.id;
        sound.id = getUniqueId();
        sound.type = ComponentType.SOUND;
        sound.enabled = true;
        sound.soundType = soundType;
        sound.sound = soundManager.getSound(soundType);
        sound.volume = volume;
        entityManager.add(sound);
        soundManager.add(sound);
        return sound;
    }
    
    public GeneRocketComponent createGeneRocketComponent(Entity entity, ArrayList<Vector2> genes) {
        GeneRocketComponent geneRocket = new GeneRocketComponent();
        geneRocket.id = getUniqueId();
        geneRocket.entityId = entity.id;
        geneRocket.type = ComponentType.GENE_ROCKET;
        geneRocket.enabled = true;
        geneRocket.genes = genes;
        geneRocket.fitness = 0f;
        entityManager.add(geneRocket);
        return geneRocket;
    }
    
    public DestroyEntityAction createDestroyEntityAction(Entity entity) {
        DestroyEntityAction destroyEntity = new DestroyEntityAction();
        destroyEntity.type = ActionType.DESTROY_ENTITY;
        destroyEntity.entityManager = entityManager;
        destroyEntity.entityId = entity.id;
        return destroyEntity;
    }

    public EnableComponentAction createEnableComponentAction(int componentId, boolean enable) {
        EnableComponentAction enableComponent = new EnableComponentAction();
        enableComponent.type = ActionType.ENABLE_COMPONENT;
        enableComponent.entityManager = entityManager;
        enableComponent.componentId = componentId;
        enableComponent.enableComponent = enable;
        return enableComponent;
    }
    
    public HighScoreResetAction createHighScoreResetAction(ScreenType screenType) {
        HighScoreResetAction highScoreReset = new HighScoreResetAction();
        highScoreReset.type = ActionType.HIGH_SCORE_RESET;
        highScoreReset.screenType = screenType;
        return highScoreReset;
    }
    
    public MultiAction createMultiAction(ArrayList<Action> actions) {
        MultiAction multi = new MultiAction();
        multi.type = ActionType.MULTI;
        multi.actions = actions;
        return multi;
    }
    
    public PhysicsAction createPhysicsAction(PhysicsComponent physicsComponent, Vector2 triggerValue) {
        PhysicsAction physics = new PhysicsAction();
        physics.type = ActionType.PHYSICS;
        physics.triggerValue = triggerValue;
        physics.physicsComponent = physicsComponent;
        return physics;
    }

    public SoundAction createSoundAction(SoundComponent soundComponent) {
        SoundAction sound = new SoundAction();
        sound.type = ActionType.SOUND;
        sound.soundManager = soundManager;
        sound.soundComponent = soundComponent;
        return sound;
    }
    
    public RepositionAction createRepositionAction(int entityId, Vector2 triggerValue, boolean teleport) {
        RepositionAction reposition = new RepositionAction();
        reposition.type = ActionType.REPOSITION;
        reposition.positionManager = positionManager;
        reposition.entityId = entityId;
        reposition.triggerValue = triggerValue;
        reposition.teleport = teleport;
        return reposition;
    }
    
    public ScoreAction createScoreAction(HighScores highScores, int scoreValue) {
        ScoreAction score = new ScoreAction();
        score.type = ActionType.SCORE;
        score.highScores = highScores;
        score.scoreValue = scoreValue;
        return score;
    }
    
    public LaunchScreenAction createLaunchScreenAction(ScreenType screenType) {
        LaunchScreenAction launchScreen = new LaunchScreenAction();
        launchScreen.type = ActionType.LAUNCH_SCREEN;
        launchScreen.screenManager = screenManager;
        launchScreen.screenType = screenType;
        return launchScreen;
    }

    public LaunchWebsiteAction createLaunchWebsiteAction(String site) {
        LaunchWebsiteAction launchWebsite = new LaunchWebsiteAction();
        launchWebsite.type = ActionType.LAUNCH_WEBSITE;
        launchWebsite.site = site;
        return launchWebsite;
    }

    public ResetScreenAction createResetScreenAction() {
        ResetScreenAction resetScreen = new ResetScreenAction();
        resetScreen.type = ActionType.RESET_SCREEN;
        resetScreen.screenManager = screenManager;
        return resetScreen;
    }

    public ChangeFloatingTextAction createChangeFloatingTextAction(FloatingTextComponent floatingTextComponent,
                String[] changeTextArray, boolean changeLabelOverride, boolean loopTextArray) {
        ChangeFloatingTextAction changeFloatingText = new ChangeFloatingTextAction();
        changeFloatingText.type = ActionType.CHANGE_FLOATING_TEXT;
        changeFloatingText.floatingTextComponent = floatingTextComponent;
        changeFloatingText.changeTextArray = changeTextArray;
        changeFloatingText.changeTextIndex = 0;
        changeFloatingText.changeLabelOverride = changeLabelOverride;
        changeFloatingText.loopTextArray = loopTextArray;
        return changeFloatingText;
    }

    public FollowAction createFollowAction(PhysicsComponent physicsComponent, boolean horizontalAllowed,
                                           boolean verticalAllowed) {
        FollowAction follow = new FollowAction();
        follow.physicsComponent = physicsComponent;
        follow.horizontalAllowed = horizontalAllowed;
        follow.verticalAllowed = verticalAllowed;
        return null;
    }

}
