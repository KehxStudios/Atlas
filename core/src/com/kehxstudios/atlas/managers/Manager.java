package com.kehxstudios.atlas.managers;

import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.type.ScreenType;

/**
 * Created by ReidC on 2017-04-07.
 */

public abstract class Manager {

    protected GameManager gm;
    protected AScreen screen;

    public Manager() {
        gm = GameManager.getInstance();
        screen = null;
    }

    public abstract void tick(float delta);
    
    protected abstract void setup();
    protected abstract void loadScreenSettings();
    protected abstract void removeScreenSettings();
    
    public void setScreen(AScreen newScreen) { 
        if (screen != null) {
            removeScreenSettings();
        }
        screen = newScreen;
        loadScreenSettings();
    }
}
