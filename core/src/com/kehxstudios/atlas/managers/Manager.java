package com.kehxstudios.atlas.managers;

import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.screens.AScreen;

/**
 * Created by ReidC on 2017-04-07.
 */

public abstract class Manager {

    protected AScreen screen;
    protected int MAX_ID;

    public abstract void tick(float delta);

    public void setScreen(AScreen newScreen) {
        if (screen != null)
            removeScreenTypeSettings();
        screen = newScreen;
        loadScreenTypeSettings();
    }

    public int getUniqueId() {
        return ++MAX_ID;
    }

    public Manager() {
        MAX_ID = 0;
    }

    protected abstract void loadScreenTypeSettings();
    protected abstract void removeScreenTypeSettings();
}
