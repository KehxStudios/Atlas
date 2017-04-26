package com.kehxstudios.atlas.managers;

import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.main.GameManager;
import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.screens.ScreenType;

/**
 * Created by ReidC on 2017-04-07.
 */

public abstract class Manager {

    protected GameManager gm;
    protected AScreen screen;
    protected ScreenType screenType;

    public abstract void tick(float delta);

    public void setScreenType(AScreen screen) {
        if (screen != null)
            removeScreenTypeSettings();
        this.screen = screen;
        screenType = screen.getType();
        loadScreenTypeSettings();
    }

    public Manager() {
        gm = GameManager.getInstance();
        screenType = null;
    }

    protected abstract void loadScreenTypeSettings();
    protected abstract void removeScreenTypeSettings();
}
