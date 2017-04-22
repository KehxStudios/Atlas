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
    protected int MAX_ID;

    public abstract void tick(float delta);

    public void setScreenType(ScreenType type) {
        if (screenType != null)
            removeScreenTypeSettings();
        screenType = type;
        loadScreenTypeSettings();
    }

    public int getUniqueId() {
        return ++MAX_ID;
    }

    public Manager() {
        gm = GameManager.getInstance();
        MAX_ID = 0;
    }

    protected abstract void loadScreenTypeSettings();
    protected abstract void removeScreenTypeSettings();
}
