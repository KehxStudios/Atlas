package com.kehxstudios.atlas.screens;

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

    protected GameManager gm;
    protected ScreenType type;
    protected AScreenData screenData;

    protected float width, height;

    protected float screenTime;

    protected Entity screenEntity;


    protected HighScores highScores;
    protected int score;

    public AScreen() {
        gm = GameManager.getInstance();
        screenTime = 0;
        score = 0;
        screenData = null;
    }

    public void updateScore(int value) {
        score += value;
    }

    protected void reset() {
        screenEntity.setPosition(width/2, height/2);
        screenTime = 0;
        score = 0;
    }

    protected void init() {
        if (screenData == null) {
            return;
        }

        type = ScreenType.getTypeById(screenData.getType());
        width = screenData.WIDTH;
        height = screenData.HEIGHT;

        Gdx.graphics.setTitle(type.getId());
        gm.getCamera().setToOrtho(false, width, height);
        gm.getCamera().update();

        screenEntity = new Entity();
        screenEntity.setPosition(width/2, height/2);

        highScores = new HighScores(type);
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

    public float getHeight() { return height; }

    public float getScreenTime() { return screenTime; }

    public void setScreenTime(float screenTime) { this.screenTime = screenTime; }
}
