package com.kehxstudios.atlas.other;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Method;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.kehxstudios.atlas.screens.FlappyBirdScreen;
import com.kehxstudios.atlas.screens.IntroScreen;
import com.kehxstudios.atlas.screens.MainMenuScreen;

import java.util.HashMap;

/**
 * April 15, 12,017 H.E.
 */

public enum ActionType {

    LAUNCH_INTRO("Launch Intro", IntroScreen.class, true),
    LAUNCH_MAIN_MENU("Launch Main Menu", MainMenuScreen.class, true),
    LAUNCH_FLAPPY_BAT("Launch Flappy Bat", FlappyBirdScreen.class, true);

    private String id;
    private Class loaderClass;
    private boolean newInstancedRequired;

    private static HashMap<String, ActionType> actionTypes;

    private ActionType(String id, Class loaderClass, boolean newInstancedRequired) {
        this.id = id;
        this.loaderClass = loaderClass;
        this.newInstancedRequired = newInstancedRequired;
    }

    private static void trigger(ActionType type) {
        if (type.isNewInstancedRequired()) {
            try {
                ClassReflection.newInstance(type.loaderClass);
            } catch (ReflectionException e) {
                e.printStackTrace();
            }
        } else {

        }
    }

    static {
        actionTypes = new HashMap<String, ActionType>();
        for (ActionType type : ActionType.values()) {
            actionTypes.put(type.id, type);
        }
    }

    public String getId() { return id; }

    public Class getLoaderClass() { return loaderClass; }

    public boolean isNewInstancedRequired() { return newInstancedRequired; }
}
