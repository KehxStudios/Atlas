package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.data.GrimReaper;
import com.kehxstudios.atlas.entities.Entity;
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

    public AScreen() {
        type = ScreenType.VOID;
        screenTime = 0;
        score = 0;
    }

    public abstract void finalize();

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
