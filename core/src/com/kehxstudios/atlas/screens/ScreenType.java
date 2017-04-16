package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.kehxstudios.atlas.main.GameManager;
import com.kehxstudios.atlas.tools.DebugTool;

import java.util.HashMap;

/**
 * Created by ReidC on 2017-04-07.
 */

public enum ScreenType {

    INTRO("Intro", IntroScreen.class),
    MAIN_MENU("MainMenu", MainMenuScreen.class),
    FLAPPY_BIRD("FlappyBird", FlappyBirdScreen.class);

    private String id;
    public Class loaderClass;

    private static HashMap<String, ScreenType> screenTypes;

    private ScreenType(String id, Class loaderClass) {
        this.id = id;
        this.loaderClass = loaderClass;
    }

    static {
        screenTypes = new HashMap<String, ScreenType>();
        for (ScreenType type : ScreenType.values()) {
            screenTypes.put(type.id, type);
        }
    }

    public String getId() { return id; }

    public static ScreenType getType(String id) {
        return screenTypes.get(id);
    }
}
