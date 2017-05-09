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
            loadNewScreenOnTick = false;
        }
    }

    public void requestNewScreen(ScreenType screenType) {
        DebugTool.log("New Screen Requested: "+ screenType.getId());
        newScreenType = screenType;
        loadNewScreenOnTick = true;
    }

    public void demandNewScreen(ScreenType screenType) {
        DebugTool.log("New Screen Demanded: "+ screenType.getId());
        newScreenType = screenType;
        loadNewScreen();
    }

    private void loadNewScreen() {
        DebugTool.log("New Screen Loading: "+ newScreenType.getId());
        try {
            setScreen((AScreen)ClassReflection.newInstance(newScreenType.getLoaderClass()));
            screen.finalizeSetup();
        } catch (ReflectionException e) {
            DebugTool.log("ERROR_loadNewScreen");
            e.printStackTrace();
            demandNewScreen(ScreenType.INTRO);
        }
    }

    @Override
    protected void loadScreenSettings() {
        DebugTool.log("Loading Screen into Managers: "+ newScreenType.getId());
        EntityManager.getInstance().setScreen(screen);
        GraphicsManager.getInstance().setScreen(screen);
        InputManager.getInstance().setScreen(screen);
        PhysicsManager.getInstance().setScreen(screen);
        GameManager.getInstance().setScreen(screen);
    }

    @Override
    protected void removeScreenSettings() {
        DebugTool.log("Removing ScreenType into Managers: "+ newScreenType.getId());
    }
}
