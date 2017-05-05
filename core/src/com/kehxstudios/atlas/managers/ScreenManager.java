package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.type.ScreenType;
import com.kehxstudios.atlas.tools.DebugTool;

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
            loadNewScreen();
        }
    }

    public void requestNewScreen(ScreenType newScreenType) {
        DebugTool.log("New Screen Requested: "+ newScreenType.getId());
        this.newScreenType = newScreenType;
        loadNewScreenOnTick = true;
    }

    public void demandNewScreen(ScreenType newScreenType) {
        DebugTool.log("New Screen Demanded: "+ newScreenType.getId());
        this.newScreenType = newScreenType;
        loadNewScreen();
    }

    private void loadNewScreen() {
        DebugTool.log("New Screen Loading: "+ newScreenType.getId());
        if (getScreenType() != ScreenType.VOID)
            removeScreenTypeSettings();
        setScreenType(newScreenType);
        setAllManagersScreenTypes();
        loadScreenTypeSettings();
        try {
            screen = (AScreen)ClassReflection.newInstance(screenType.getLoaderClass());
            setAllManagersScreens();
            loadNewScreenOnTick = false;
        } catch (ReflectionException e) {
            e.printStackTrace();
            DebugTool.log("ERROR_loadNewScreen");
            newScreenType = ScreenType.INTRO;
            loadNewScreen();
        }
    }

    @Override
    protected void loadScreenTypeSettings() {
        DebugTool.log("Loading ScreenType into Managers: "+ newScreenType.getId());
        EntityManager.getInstance().loadScreenTypeSettings();
        GraphicsManager.getInstance().loadScreenTypeSettings();
        InputManager.getInstance().loadScreenTypeSettings();
        PhysicsManager.getInstance().loadScreenTypeSettings();
    }

    @Override
    protected void removeScreenTypeSettings() {
        DebugTool.log("Removing ScreenType into Managers: "+ newScreenType.getId());
        EntityManager.getInstance().removeScreenTypeSettings();
        GraphicsManager.getInstance().removeScreenTypeSettings();
        InputManager.getInstance().removeScreenTypeSettings();
        PhysicsManager.getInstance().removeScreenTypeSettings();
    }

    private void setAllManagersScreenTypes() {
        DebugTool.log("Setting ScreenType in Managers: "+ newScreenType.getId());
        EntityManager.getInstance().setScreenType(screenType);
        GraphicsManager.getInstance().setScreenType(screenType);
        InputManager.getInstance().setScreenType(screenType);
        PhysicsManager.getInstance().setScreenType(screenType);
    }

    private void setAllManagersScreens() {
        DebugTool.log("Setting AScreen in Managers: "+ screen.getType().getId());
        EntityManager.getInstance().setScreen(screen);
        GraphicsManager.getInstance().setScreen(screen);
        InputManager.getInstance().setScreen(screen);
        PhysicsManager.getInstance().setScreen(screen);
        GameManager.getInstance().setScreen(screen);
    }
}
