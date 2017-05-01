package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.components.GraphicsComponent;
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
    protected GraphicsComponent screenGraphics;

    protected float screenTime;
    protected HighScores highScores;
    protected int score;

    public AScreen(com.kehxstudios.atlas.type.ScreenType type) {
        this.type = type;
        gm = GameManager.getInstance();
        width = type.getWidth();
        height = type.getHeight();
        highScores = new HighScores(type);
        screenTime = 0;
        score = 0;

        screenEntity = Factory.createEntity(Templates.createEntityData(width/2, height/2));

        screenGraphics = (GraphicsComponent)Factory.createComponent(screenEntity, 
                  Templates.createGraphicsComponentData(0, 0, 1, TextureType.VOID));

        gm.getCamera().setToOrtho(false, width, height);
        gm.getCamera().update();
    }

    @Override
    public void render(float delta) {
        screenTime += delta;
    }

    @Override
    public void dispose() {
        highScores.dispose();
    }

    public float getGraphicsWidth() {
        return Gdx.graphics.getWidth();
    }

    public float getGraphicsHeight() {
        return Gdx.graphics.getHeight();
    }

    public float getWidth() { return width; }

    public float getHeight() { return height; }

    public int getScore() { return score; }

    public void addScore(int value) { score += value; }

    public void setScore(int score) { this.score = score; }
}
