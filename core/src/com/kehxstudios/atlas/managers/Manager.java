package com.kehxstudios.atlas.managers;

import com.kehxstudios.atlas.type.ScreenType;

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
