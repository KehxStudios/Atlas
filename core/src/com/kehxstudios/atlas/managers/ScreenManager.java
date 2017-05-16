/*******************************************************************************
 * Copyright 2017 See AUTHORS file.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
 * associated documentation files (the "Software"), to deal in the Software without restriction, 
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial 
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/

package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.type.ScreenType;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.ErrorTool;

/**
 * Controls the changing of screens
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

    private boolean resetRequested;

    // Constructor
    private ScreenManager() {
        super();
        init();
    }
    
    // Set the default variables
    @Override
    protected void init() {
        newScreenType = ScreenType.VOID;
        screenRequested = false;
        resetRequested = false;
        DebugTool.log("ScreenManager_init: Complete");
    }

    // Called to check if new screen is requested
    @Override
    public void tick(float delta) {
        if (screenRequested) {
            startLoadingScreen();
            screenRequested = false;
        }
        if (resetRequested) {
            resetScreen();
            resetRequested = false;
        }
    }

    // Called when loading a new screen
    @Override
    protected void loadSettings() {
        EntityManager.getInstance().setScreen(screen);
        GraphicsManager.getInstance().setScreen(screen);
        InputManager.getInstance().setScreen(screen);
        PhysicsManager.getInstance().setScreen(screen);
        GameManager.getInstance().setScreen(screen);
        DebugTool.log("ScreenManager_loadSettings: Complete");
    }

    // Called when unloading the current screen
    @Override
    protected void removeSettings() {
        DebugTool.log("ScreenManager_removeSettings: Complete");
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
        startLoadingScreen();
    }

    private void startLoadingScreen() {
        gm.startLoading(newScreenType);
    }

    public void finishedLoadingScreen() {
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

    public void requestScreenReset() {
        resetRequested = true;
        DebugTool.log("ScreenManager_requestScreenReset");
    }

    public void demandScreenReset() {
        resetScreen();
        DebugTool.log("ScreenManager_demandScreenReset");
    }

    private void resetScreen() {
        screen.reset();
    }
    
    public void add(Component component) {}
    public void remove(Component component) {}
}
