package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.type.ScreenType;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.ErrorTool;

/**
 * Created by ReidC on 2017-04-22.
 */

public class ScreenManager extends Manager {

    // Holds instance of class, create new if not set
    private static ScreenManager instance;
    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    // ScreenType for the next screen to be loaded
    private ScreenType newScreenType;
    // Used to start new screen on @tick()
    private boolean screenRequested;

    // Constructor
    private ScreenManager() {
        super();
        setup();
    }
    
    // Set the default variables
    @Override
    protected void setup() {
        newScreenType = ScreenType.VOID;
        screenRequested = false;
        DebugTool.log("ScreenManager_setup: Complete");
    }

    // Called to check if new screen is requested
    @Override
    public void tick(float delta) {
        if (screenRequested) {
            loadNewScreen();
            screenRequested = false;
        }
    }

    // Called when loading a new screen
    @Override
    protected void loadScreenSettings() {
        EntityManager.getInstance().setScreen(screen);
        GraphicsManager.getInstance().setScreen(screen);
        InputManager.getInstance().setScreen(screen);
        PhysicsManager.getInstance().setScreen(screen);
        GameManager.getInstance().setScreenBeingLoaded(screen);
        DebugTool.log("ScreenManager_loadScreenSettings: Complete");
    }

    // Called when unloading the current screen
    @Override
    protected void removeScreenSettings() {
        DebugTool.log("ScreenManager_removeScreenSettings: Complete");
    }
    
    // Called when new screen is requested on next @tick()
    public void requestNewScreen(ScreenType screenType) {
        newScreenType = screenType;
        screenRequested = true;
        DebugTool.log("ScreenManager_requestNewScreen: " + newScreenType.getId());
    }

    // Called to demand a new screen be started now
    public void demandNewScreen(ScreenType screenType) {
        newScreenType = screenType;
        DebugTool.log("ScreenManager_demandNewScreen: " + newScreenType.getId());
        loadNewScreen();
    }

    // Called to load a new screen
    private void loadNewScreen() {
        DebugTool.log("New Screen Loading: "+ newScreenType.getId());
        try {
            setScreen((AScreen)ClassReflection.newInstance(newScreenType.getLoaderClass()));
            screen.finalizeSetup();
        } catch (ReflectionException e) {
            ErrorTool.log("Failed to load " + newScreenType.getId() + " screen, demanding Intro screen");
            e.printStackTrace();
            demandNewScreen(ScreenType.INTRO);
        }
    }

}
