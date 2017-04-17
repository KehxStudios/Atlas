package com.kehxstudios.atlas.actions;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.kehxstudios.atlas.main.GameManager;
import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.screens.ScreenType;

/**
 * Created by ReidC on 2017-04-16.
 */

public class LaunchScreenAction extends Action {

    private ScreenType screenType;

    public LaunchScreenAction(ScreenType screenType) {
        this.screenType = screenType;
    }

    public void changeScreenType(ScreenType screenType) {
        this.screenType = screenType;
    }

    @Override
    public void trigger() {
        try {
            GameManager.getInstance().setScreen((AScreen)ClassReflection.newInstance(screenType.loaderClass));
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
    }
}
