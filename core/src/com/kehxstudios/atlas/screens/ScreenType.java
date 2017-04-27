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

    INTRO("Intro", "texturePacks/intro.atlas", 512, 910, IntroScreen.class),
    MAIN_MENU("MainMenu", "texturePacks/mainMenu.atlas", 1440, 2560, MainMenuScreen.class),
    FLAPPY_BAT("FlappyBat", "texturePacks/flappyBat.atlas", 240, 400, FlappyBatScreen.class),
    VOID("Void", "-", 0, 0, AScreen.class);

    private String id, path;
    private float width, height;
    public Class loaderClass;

    private ScreenType(String id, String path, float width, float height, Class loaderClass) {
        this.id = id;
        this.path = path;
        this.width = width;
        this.height = height;
        this.loaderClass = loaderClass;
    }

    public String getId() { return id; }

    public String getPath() { return path; }

    public float getWidth() { return width; }

    public float getHeight() { return height; }

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
