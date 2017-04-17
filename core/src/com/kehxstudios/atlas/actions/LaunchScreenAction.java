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

    public LaunchScreenAction(ActionData actionData) {
        super(actionData);
        init();
    }

    @Override
    protected void init() {
        type = ActionType.LAUNCH_SCREEN;
    }

    public void changeScreenType(ScreenType screenType) {
        this.screenType = screenType;
    }

    public ActionData getActionData() {
        ActionData actionData = super.getActionData();
        actionData.putString("screenType", screenType.getId());
        return actionData;
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
