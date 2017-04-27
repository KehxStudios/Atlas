package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.kehxstudios.atlas.main.GameManager;
import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.screens.ScreenType;

/**
 * Created by ReidC on 2017-04-22.
 */

public class ScreenManager extends Manager {

    private static ScreenManager instance;
    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    private ScreenType newScreenType;
    private boolean loadNewScreenOnTick;

    private ScreenManager() {
        super();
        newScreenType = ScreenType.VOID;
        loadNewScreenOnTick = false;
    }

    @Override
    public void tick(float delta) {
        if (loadNewScreenOnTick) {
            loadNewScreen(newScreenType);
        }
    }

    public void requestNewScreenType(ScreenType screenType) {
        newScreenType = screenType;
        loadNewScreenOnTick = true;
    }

    private void loadNewScreen(ScreenType screenType) {
        if (getScreenType() != ScreenType.VOID)
            removeScreenTypeSettings();
        setScreenType(screenType);
        setAllManagersScreenTypes();
        loadScreenTypeSettings();
        try {
            GameManager.getInstance().setScreen((AScreen) ClassReflection.newInstance(screenType.getLoaderClass()));
            loadNewScreenOnTick = false;
        } catch (ReflectionException e) {
            e.printStackTrace();
            loadNewScreen(ScreenType.INTRO);
        }
    }

    @Override
    protected void loadScreenTypeSettings() {
        EntityManager.getInstance().loadScreenTypeSettings();
        GraphicsManager.getInstance().loadScreenTypeSettings();
        InputManager.getInstance().loadScreenTypeSettings();
        PhysicsManager.getInstance().loadScreenTypeSettings();
    }

    @Override
    protected void removeScreenTypeSettings() {
        EntityManager.getInstance().removeScreenTypeSettings();
        GraphicsManager.getInstance().removeScreenTypeSettings();
        InputManager.getInstance().removeScreenTypeSettings();
        PhysicsManager.getInstance().removeScreenTypeSettings();
    }

    private void setAllManagersScreenTypes() {
        EntityManager.getInstance().setScreenType(screenType);
        GraphicsManager.getInstance().setScreenType(screenType);
        InputManager.getInstance().setScreenType(screenType);
        PhysicsManager.getInstance().setScreenType(screenType);
    }
}
