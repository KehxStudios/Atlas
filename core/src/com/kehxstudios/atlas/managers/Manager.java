package com.kehxstudios.atlas.managers;

import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.type.ScreenType;

/**
 * Created by ReidC on 2017-04-07.
 */

public abstract class Manager {

    protected GameManager gm;
    protected AScreen screen;

    public abstract void tick(float delta);

    public Manager() {
        gm = GameManager.getInstance();
        screenType = ScreenType.VOID;
    }

    public AScreen getScreen() { return screen; }
    public void setScreen(AScreen screen) { 
        if (screen != null) {
            removeScreenTypeSettings
        }
        this.screen = screen;
        loadScreenTypeSettings();
    }

    protected abstract void loadScreenTypeSettings();
    protected abstract void removeScreenTypeSettings();
}
