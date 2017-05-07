package com.kehxstudios.atlas.type;

import java.util.HashMap;

/**
 * Created by ReidC on 2017-04-19.
 */

public enum TextureType {

    INTRO_DEV_LOGO("IntroDevLogo", "devLogo", 700, 200),
    INTRO_GAME_LOGO("IntroGameLogo","gameLogo", 360, 360),

    MAIN_MENU_BACKGROUND("MainMenuBackground", "background", 1440, 2560),
    MAIN_MENU_BORDER("MainMenuBorder", "menuBorder", 625, 435),

    FLAPPY_BAT_BACKGROUND("FlappyBatBackground", "background", 288, 512),
    FLAPPY_BAT_BAT("FlappyBatBat", "bat", 34, 24),
    FLAPPY_BAT_WALL("FlappyBatWall",  "wall", 32, 320),
    FLAPPY_BAT_GROUND("FlappyBatGround", "ground", 366, 112),

    VOID("Void", "void", 0, 0);

    private String id, fileName;
    private float width, height;

    private static HashMap<String, TextureType> textureTypes;

    private TextureType(String id, String fileName, float width, float height) {
        this.id = id;
        this.fileName = fileName;
        this.width = width;
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public String getFileName() { return fileName; }

    public float getWidth() { return width; }

    public float getHeight() { return height; }

    public static TextureType getTypeFromId(String id) {
        return textureTypes.get(id);
    }

    static {
        textureTypes = new HashMap<String, TextureType>();
        for (TextureType type : TextureType.values()) {
            textureTypes.put(type.getId(), type);
        }
    }
}
