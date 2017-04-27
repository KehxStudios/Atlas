package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.kehxstudios.atlas.components.ComponentData;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.data.Factory;
import com.kehxstudios.atlas.data.GrimReaper;
import com.kehxstudios.atlas.data.Templates;
import com.kehxstudios.atlas.data.TextureType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.entities.EntityData;
import com.kehxstudios.atlas.main.GameManager;
import com.kehxstudios.atlas.stats.HighScores;
import com.kehxstudios.atlas.tools.DebugTool;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-07.
 */

public abstract class AScreen implements Screen {

    protected ScreenType type;
    protected float width, height;

    protected Entity screenEntity;
    protected GraphicsComponent screenGraphics;

    protected float screenTime;
    protected HighScores highScores;
    protected int score;

    public AScreen(ScreenType type) {
        this.type = type;
        width = type.getWidth();
        height = type.getHeight();
        highScores = new HighScores(type);
        screenTime = 0;
        score = 0;

        EntityData entityData = Templates.createEntityData(width/2, height/2);
        screenEntity = Factory.createEntity(entityData);

        ComponentData graphicsData = Templates.createGraphicsComponentData(0, 0, 0, TextureType.VOID);
        screenGraphics = (GraphicsComponent)Factory.createComponent(screenEntity, graphicsData);
    }

    @Override
    public void render(float delta) {
        screenTime += delta;
    }

    @Override
    public void dispose() {
        highScores.dispose();
    }

    public ScreenType getType() {
        return type;
    }

    public void setType(ScreenType type) { this.type = type; }

    public float getScaleWidth() {
        return Gdx.graphics.getWidth()/width;
    }

    public float getScaleHeight() {
        return Gdx.graphics.getHeight()/height;
    }

    public float getWidth() { return width; }

    public void setWidth(float width) { this.width = width; }

    public float getHeight() { return height; }

    public void setHeight(float height) { this.height = height; }

    public Entity getScreenEntity() { return screenEntity; }

    public void setScreenEntity(Entity screenEntity) { this.screenEntity = screenEntity; }

    public GraphicsComponent getScreenGraphics() { return screenGraphics; }

    public void setScreenGraphics(GraphicsComponent screenGraphics) { this.screenGraphics = screenGraphics; }

    public float getScreenTime() { return screenTime; }

    public void setScreenTime(float screenTime) { this.screenTime = screenTime; }

    public HighScores getHighScores() { return highScores; }

    public void setHighScores(HighScores highScores) { this.highScores = highScores; }

    public int getScore() { return score; }

    public void addScore(int value) { score += value; }

    public void setScore(int score) { this.score = score; }
}
