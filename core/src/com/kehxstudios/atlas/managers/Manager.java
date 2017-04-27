package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.Screen;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.main.GameManager;
import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.screens.ScreenType;

/**
 * Created by ReidC on 2017-04-07.
 */

public abstract class Manager {

    protected GameManager gm;
    protected ScreenType screenType;

    public abstract void tick(float delta);

    public Manager() {
        gm = GameManager.getInstance();
        screenType = ScreenType.VOID;
    }

    public ScreenType getScreenType() { return screenType; }
    public void setScreenType(ScreenType screenType) { this.screenType = screenType; }

    protected abstract void loadScreenTypeSettings();
    protected abstract void removeScreenTypeSettings();
}
