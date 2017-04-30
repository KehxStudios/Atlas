package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.managers.ScreenManager;
import com.kehxstudios.atlas.type.ScreenType;

/**
 * Created by ReidC on 2017-04-16.
 */

public class LaunchScreenAction extends Action {

    private ScreenType screenType;

    public ScreenType getScreenType() { return screenType; }
    public void setScreenType(ScreenType screenType) { this.screenType = screenType; }

    @Override
    public void trigger() {
        ScreenManager.getInstance().requestNewScreenType(screenType);
    }
}
