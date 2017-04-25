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

    protected float WIDTH, HEIGHT;

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
        screenEntity.setPosition(WIDTH/2, HEIGHT/2);
        screenTime = 0;
        score = 0;
    }

    protected void init() {
        DebugTool.log("AScreen.init");
        if (screenData == null) {
            DebugTool.log("screenData is null");
            return;
        }

        type = ScreenType.getTypeById(screenData.getType());
        WIDTH = screenData.WIDTH;
        HEIGHT = screenData.HEIGHT;

        Gdx.graphics.setTitle(type.getId());
        gm.getCamera().setToOrtho(false, WIDTH, HEIGHT);
        gm.getCamera().update();

        screenEntity = new Entity();
        screenEntity.setPosition(WIDTH/2, HEIGHT/2);

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

    public float getScaleWidth() {
        return Gdx.graphics.getWidth()/WIDTH;
    }

    public float getScaleHeight() {
        return Gdx.graphics.getHeight()/HEIGHT;
    }

    public float getScreenWidth() { return WIDTH; }

    public float getScreenHeight() { return HEIGHT; }
}
