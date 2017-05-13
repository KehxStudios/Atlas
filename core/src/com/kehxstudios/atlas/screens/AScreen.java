package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.kehxstudios.atlas.components.CameraComponent;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.managers.ScreenManager;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.Factory;
import com.kehxstudios.atlas.tools.Templates;
import com.kehxstudios.atlas.type.TextureType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.data.EntityData;
import com.kehxstudios.atlas.managers.GameManager;
import com.kehxstudios.atlas.data.HighScores;
import com.kehxstudios.atlas.type.ScreenType;

/**
 * Created by ReidC on 2017-04-07.
 */

public abstract class AScreen implements Screen {

    protected GameManager gm;
    protected ScreenType type;
    protected float width, height;

    protected Entity screenEntity;
    protected CameraComponent screenCamera;
    protected GraphicsComponent screenGraphics;

    protected float screenTime;
    protected HighScores highScores;
    protected int score;
    
    public AScreen(ScreenType type) {
        this.type = type;
        gm = GameManager.getInstance();
        width = type.getWidth();
        height = type.getHeight();
        highScores = new HighScores(type);
        screenTime = 0f;
        score = 0;
    }

    public void finalizeSetup() {
        screenEntity = Factory.createEntity(Templates.createEntityData(width/2, height/2));

        screenCamera = (CameraComponent)Factory.createComponent(screenEntity,
                Templates.cameraComponentData(width, height, false));
        screenCamera.getCamera().setToOrtho(false, width, height);
        screenCamera.update();

        screenGraphics = (GraphicsComponent)Factory.createComponent(screenEntity,
                Templates.graphicsComponentData(0, 0, 0, TextureType.VOID));
    }

    public void reset() {
        screenEntity.setPosition(width/2, height/2);
        screenCamera.update();
    }
    
    @Override
    public void render(float delta) {
        screenTime += delta;
        screenCamera.update();
    }

    @Override
    public void dispose() {
        highScores.dispose();
        DebugTool.log("SCREEN_DISPOSAL");
    }
    
    @Override
    public void show() {
        DebugTool.log("SCREEN_SHOW");
    }

    @Override
    public void resize(int width, int height) {
        DebugTool.log("SCREEN_RESIZE");
    }

    @Override
    public void pause() {
        DebugTool.log("SCREEN_PAUSE");
    }

    @Override
    public void resume() {
        DebugTool.log("SCREEN_RESUME");
    }

    @Override
    public void hide() {
        DebugTool.log("SCREEN_HIDE");
    }

    public ScreenType getType() { return type; }

    public OrthographicCamera getCamera() { return screenCamera.getCamera(); }

    public float getWidth() { return width; }

    public float getHeight() { return height; }

    public float getScaleWidth() { return Gdx.graphics.getWidth() / width; }

    public float getScaleHeight() { return Gdx.graphics.getHeight() / height; }

    public int getScore() { return score; }

    public void addScore(int value) { score += value; }

    public void setScore(int score) { this.score = score; }
}
