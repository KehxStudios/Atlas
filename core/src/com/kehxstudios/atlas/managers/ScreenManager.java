package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
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


    @Override
    public void tick(float delta) {

    }

    @Override
    protected void loadScreenTypeSettings() {
        EntityManager.getInstance().setScreenType(screen);
        GraphicsManager.getInstance().setScreenType(screen);
        InputManager.getInstance().setScreenType(screen);
        PhysicsManager.getInstance().setScreenType(screen);
    }

    @Override
    protected void removeScreenTypeSettings() {

    }

    public void changeScreen(AScreen screen) {
        if (screen != null)
            removeScreenTypeSettings();
        this.screen = screen;
        screenType = screen.getType();
        loadScreenTypeSettings();
    }
}
