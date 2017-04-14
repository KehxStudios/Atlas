package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.main.GameManager;
import com.kehxstudios.atlas.managers.InputManager;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.stats.HighScores;
import com.kehxstudios.atlas.tools.DebugTool;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-07.
 */

public abstract class AScreen implements Screen {

    protected GameManager gm;
    protected ScreenType type;
    protected ScreenSnapShot snapShot;

    protected int WIDTH, HEIGHT;

    protected float screenTime;
    protected Vector2 mouse;

    protected Entity screenEntity;
    protected GraphicsComponent backgroundGraphics;

    protected int backgroundIndex;
    protected String[] backgroundPaths;
    protected float[] backgroundTimes;

    protected HighScores highScores;

    public void create(ScreenSnapShot snapShot, ScreenType type, GameManager gm) {
        DebugTool.log("Starting the creation from snapshot");
        this.gm = gm;
        this.type = type;
        this.snapShot = snapShot;
        WIDTH = snapShot.WIDTH;
        HEIGHT = snapShot.HEIGHT;

        Gdx.graphics.setTitle(type.getId());
        gm.getCamera().setToOrtho(false, WIDTH, HEIGHT);
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            Gdx.graphics.setWindowedMode(WIDTH, HEIGHT);
        }
        gm.getCamera().update();

        mouse = new Vector2(0,0);
        screenEntity = new Entity(WIDTH/2, HEIGHT/2);
        backgroundGraphics = new GraphicsComponent(screenEntity);

        ArrayList<String> paths = new ArrayList<String>();
        ArrayList<Float> times = new ArrayList<Float>();

        for(int i = 0; snapShot.hasKey("background_" + i); i++) {
            if (snapShot.hasKey("backgroundTime_" + i)) {
                paths.add(snapShot.getString("background_" + i, ""));
                times.add(snapShot.getFloat("backgroundTime_" + i, 0f));
            }
        }
        screenTime = 0;

        backgroundIndex = 0;
        backgroundPaths = paths.toArray(new String[paths.size()]);
        backgroundTimes = new float[times.size()];
        for (int i = 0; i < times.size(); i++) {
            backgroundTimes[i] = times.get(i);
        }

        backgroundGraphics.setTexture(new Texture(backgroundPaths[backgroundIndex]));

        InputManager.getInstance().setScreen(this);
        PhysicsManager.getInstance().setScreen(this);

        highScores = new HighScores(type);

        init();
    }

    public ScreenSnapShot getSaveSnapShot() {
        DebugTool.log("AScreen.getSaveSnapShot()");
        snapShot = new ScreenSnapShot(type.getId(), WIDTH, HEIGHT);
        for (int i = 0; i < backgroundPaths.length; i++) {
            snapShot.putString("background_" + i, backgroundPaths[i]);
            snapShot.putFloat("backgroundTime_" + i, backgroundTimes[i]);
        }
        return snapShot;
    }

    public void launchNextScreen(ScreenType newType) {
        gm.setNewScreen(ScreenLoader.loadScreen(newType));
    }

    public abstract void init();

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        screenTime += delta;
        mouse.set(Gdx.input.getX(), Gdx.input.getY());
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public abstract void resetScreen();

    @Override
    public void dispose() {
        highScores.dispose();
        screenEntity.destroy();
    }

    public ScreenType getType() {
        return type;
    }

    public int getScaleWidth() {
        return Gdx.graphics.getWidth()/WIDTH;
    }

    public int getScaleHeight() {
        return Gdx.graphics.getHeight()/HEIGHT;
    }
}
