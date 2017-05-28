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
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.screens.LoadingScreen;
import com.kehxstudios.atlas.type.ScreenType;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.ErrorTool;

/**
 * Controls the changing of screens
 */

public class ScreenManager extends Manager {

    private AScreen screen;

    // ScreenType for the next screen to be loaded
    private ScreenType newScreenType;
    // Used to start new screen on @tick()
    private boolean screenRequested;
    private boolean resetRequested;

    private LoadingScreen loadingScreen;

    // Constructor
    public ScreenManager(GameManager gm) {
        super(gm);
        DebugTool.log("ScreenManager: Constructed");
    }
    
    // Set the default variables
    @Override
    protected void init() {
        DebugTool.log("ScreenManager_init: Starting...");
        super.init();
        newScreenType = ScreenType.VOID;
        screenRequested = false;
        resetRequested = false;
        loadingScreen = new LoadingScreen();
        DebugTool.log("ScreenManager_init: Complete");
    }

    // Called to check if new screen is requested
    @Override
    public void tick(float delta) {
        if (screenRequested) {
            startLoadingScreen();
            screenRequested = false;
        } else if (resetRequested) {
            screen.reset();
            resetRequested = false;
        }
    }

    public void requestScreenReset() {
        resetRequested = true;
    }

    // Called when loading a new screen
    @Override
    protected void loadSettings() {
        gm.getEntityManager().setScreenType(screenType);
        gm.getGraphicsManager().setScreenType(screenType);
        gm.getInputManager().setScreenType(screenType);
        gm.getPhysicsManager().setScreenType(screenType);
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
        loadingScreen.startLoadingScreen(newScreenType);
        gm.setScreen(loadingScreen);
    }

    public void finishedLoadingScreen() {
        DebugTool.log("New Screen Loading: "+ newScreenType.getId());
        try {
            setScreenType(newScreenType);
            screen = (AScreen)ClassReflection.newInstance(screenType.getLoaderClass());
            gm.setScreen(screen);
        } catch (Exception e) {
            ErrorTool.log("Failed to load " + newScreenType.getId() + " screen, demanding Intro screen");
            e.printStackTrace();
            demandNewScreen(ScreenType.INTRO);
        }
    }

    public void add(Component component) {

    }

    public void remove(Component component) {

    }
}
