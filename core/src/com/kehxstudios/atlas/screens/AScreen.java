package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.main.GameManager;
import com.kehxstudios.atlas.managers.EntityManager;
import com.kehxstudios.atlas.managers.GraphicsManager;
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
    protected AScreenData screenData;

    protected int WIDTH, HEIGHT;

    protected float screenTime;
    protected Vector2 mouse;

    protected Entity screenEntity;
    protected GraphicsComponent backgroundGraphics;

    protected int backgroundIndex;
    protected String[] backgroundPaths;
    protected float[] backgroundTimes;

    protected HighScores highScores;

    public AScreen() {
        DebugTool.log("AScreen");
        gm = GameManager.getInstance();
        mouse = new Vector2(0,0);
        screenTime = 0;
        backgroundIndex = 0;
        screenData = null;
    }

    protected void init() {
        DebugTool.log("AScreen.init");
        if (screenData == null) {
            DebugTool.log("screenData is null");
            return;
        }

        type = ScreenType.getType(screenData.getType());
        WIDTH = screenData.WIDTH;
        HEIGHT = screenData.HEIGHT;

        Gdx.graphics.setTitle(type.getId());
        gm.getCamera().setToOrtho(false, WIDTH, HEIGHT);
        gm.getCamera().update();

        screenEntity = new Entity(WIDTH / 2, HEIGHT / 2);
        backgroundGraphics = new GraphicsComponent(screenEntity);

        ArrayList<String> paths = new ArrayList<String>();
        ArrayList<Float> times = new ArrayList<Float>();

        for (int i = 0; screenData.hasKey("background_" + i); i++) {
            if (screenData.hasKey("backgroundTime_" + i)) {
                paths.add(screenData.getString("background_" + i, ""));
                times.add(screenData.getFloat("backgroundTime_" + i, 0f));
            }
        }
        backgroundPaths = paths.toArray(new String[paths.size()]);
        backgroundTimes = new float[times.size()];
        for (int i = 0; i < times.size(); i++) {
            backgroundTimes[i] = times.get(i);
        }

        backgroundGraphics.setTexture(new Texture(backgroundPaths[backgroundIndex]));

        highScores = new HighScores(type);
    }

    private AScreenData getScreenData() {
        screenData = new AScreenData(type.getId(), WIDTH, HEIGHT);
        for (int i = 0; i < backgroundPaths.length; i++) {
            screenData.putString("background_" + i, backgroundPaths[i]);
            screenData.putFloat("backgroundTime_" + i, backgroundTimes[i]);
        }
        return screenData;
    }

    @Override
    public void render(float delta) {
        screenTime += delta;

        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            mouse.set(Gdx.input.getX(), Gdx.input.getY());
        } else {
            mouse.set(Gdx.input.getX() / getScaleWidth(), Gdx.input.getY() / getScaleHeight());
        }
    }

    public abstract void resetScreen();

    protected void launchNextScreen(ScreenType type) {
        gm.launchNewScreen(type);
    }

    @Override
    public void dispose() {
        highScores.dispose();
        screenEntity.destroy();
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

    public int getScreenWidth() { return WIDTH; }

    public int getScreenHeight() { return HEIGHT; }
}
