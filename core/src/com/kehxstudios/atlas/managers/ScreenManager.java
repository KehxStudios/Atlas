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
        EntityManager.getInstance().setScreenType(screenType);
        GraphicsManager.getInstance().setScreenType(screenType);
        InputManager.getInstance().setScreenType(screenType);
        PhysicsManager.getInstance().setScreenType(screenType);
    }

    @Override
    protected void removeScreenTypeSettings() {

    }

    public void changeScreen(ScreenType type) {
        if (screenType != null)
            removeScreenTypeSettings();
        screenType = type;
        loadScreenTypeSettings();
        try {
            gm.setScreen((AScreen) ClassReflection.newInstance(type.loaderClass));
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
    }
}
