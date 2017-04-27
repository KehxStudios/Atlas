package com.kehxstudios.atlas.data;

import java.util.HashMap;

/**
 * Created by ReidC on 2017-04-19.
 */

public enum TextureType {

    DEV_LOGO("DevLogo", "devLogo", 512, 512),
    GAME_LOGO("GameLogo","gameLogo", 512, 512),

    MAINMENU_BACKGROUND("MainMenuBackground", "background", 1440, 2560),
    MAINMENU_BORDERS("MainMenuBorders", "menuBorder", 625, 435),

    FLAPPYBIRD_BACKGROUND("FlappyBirdBackground", "background", 288, 512),
    FLAPPYBIRD_BIRD("FlappyBirdBird", "bird", 34, 24),
    FLAPPYBIRD_TOPTUBE("FlappyBirdTopTube",  "toptube", 32, 320),
    FLAPPYBIRD_BOTTOMTUBE("FlappyBirdBottomTube", "bottomtube", 32, 320),
    FLAPPYBIRD_GROUND("FlappyBirdGround", "ground", 366, 112),

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
