package com.kehxstudios.atlas.managers;

import com.kehxstudios.atlas.screens.AScreen;

/**
 * Created by ReidC on 2017-04-07.
 */

public abstract class Manager {

    protected AScreen screen;

    public abstract void tick(float delta);

    public void setScreen(AScreen newScreen) {
        if (screen != null)
            removeScreenTypeSettings();
        screen = newScreen;
        loadScreenTypeSettings();
    }

    protected abstract void loadScreenTypeSettings();
    protected abstract void removeScreenTypeSettings();
}
