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

    INTRO("Intro", "texturePacks/intro.atlas", IntroScreen.class),
    MAIN_MENU("MainMenu", "texturePacks/mainMenu.atlas", MainMenuScreen.class),
    FLAPPY_BIRD("FlappyBird", "texturePacks/flappyBat.atlas", FlappyBirdScreen.class),
    VOID("Void", "-", AScreen.class);

    private String id, path;
    public Class loaderClass;

    private ScreenType(String id, String path, Class loaderClass) {
        this.id = id;
        this.path = path;
        this.loaderClass = loaderClass;
    }

    public String getId() { return id; }

    public String getPath() { return path; }

    public Class getLoaderClass() { return loaderClass; }

    public static ScreenType getTypeById(String id) {
        return screenTypes.get(id);
    }

    private static HashMap<String, ScreenType> screenTypes;

    static {
        screenTypes = new HashMap<String, ScreenType>();
        for (ScreenType type : ScreenType.values()) {
            screenTypes.put(type.id, type);
        }
    }
}
