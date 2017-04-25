package com.kehxstudios.atlas.data;

import java.util.HashMap;

/**
 * Created by ReidC on 2017-04-19.
 */

public enum TextureType {

    DEV_LOGO("DevLogo", "devLogo"),
    GAME_LOGO("GameLogo","gameLogo"),

    MAINMENU_BACKGROUND("MainMenuBackground", "background"),
    MAINMENU_BORDERS("MainMenuBorders", "menuBorder"),

    FLAPPYBIRD_BACKGROUND("FlappyBirdBackground", "background"),
    FLAPPYBIRD_BIRD("FlappyBirdBird", "bird"),
    FLAPPYBIRD_TOPTUBE("FlappyBirdTopTube",  "toptube"),
    FLAPPYBIRD_BOTTOMTUBE("FlappyBirdBottomTube", "bottomtube"),
    FLAPPYBIRD_GROUND("FlappyBirdGround", "ground"),

    VOID("Void", "void");

    private String id, fileName;

    private static HashMap<String, TextureType> textureTypes;

    private TextureType(String id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public String getFileName() { return fileName; }

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
