package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.data.Factory;
import com.kehxstudios.atlas.main.GameManager;
import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.screens.AScreenData;
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

    public void changeScreen(ScreenType screenType) {
        if (screen != null)
            removeScreenTypeSettings();
        screen = Factory.createScreen(screenType);
        GameManager.getInstance().setScreen(screen);
        this.screenType = screen.getType();
        setAllManagersScreens();
        loadScreenTypeSettings();
    }

    private void setAllManagersScreens() {
        EntityManager.getInstance().setScreen(screen);
        GraphicsManager.getInstance().setScreen(screen);
        InputManager.getInstance().setScreen(screen);
        PhysicsManager.getInstance().setScreen(screen);
    }
}
