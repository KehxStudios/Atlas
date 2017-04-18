package com.kehxstudios.atlas.data;

import java.util.HashMap;

/**
 * Created by ReidC on 2017-04-16.
 */

public enum SpriteType {

    INTRO_DEV_LOGO("DevLogo", "screens/intro/devLogo.png"),
    INTRO_GAME_LOGO("GameLogo", "screens/intro/gameLogo.png"),

    MAINMENU_BACKGROUND("MainMenuBackground", "screens/mainMenu/background.png"),
    MAINMENU_BORDERS("MainMenuBorders", "screens/flappyBird/flappyBird.png"),

    FLAPPYBIRD_BACKGROUND("FlappyBirdBackground", "screens/flappyBird/bg.png"),
    FLAPPYBIRD_BIRD("FlappyBirdBird", "screens/flappyBird/bird.png"),
    FLAPPYBIRD_TOPTUBE("FlappyBirdTopTube", "screens/flappyBird/toptube.png"),
    FLAPPYBIRD_BOTTOMTUBE("FlappyBirdBottomTube", "screens/flappyBird/bottomtube.png"),
    FLAPPYBIRD_GROUND("FlappyBirdGround", "screens/flappyBird/ground.png");

    private static HashMap<String, SpriteType> textureTypes;

    private String id;
    private String path;

    private SpriteType(String id, String path) {
        this.id = id;
        this.path = path;
    }

    static {
        textureTypes = new HashMap<String, SpriteType>();
        for (SpriteType type : SpriteType.values()) {
            textureTypes.put(type.id, type);
        }
    }

    public static String getPath(String id) {
        return textureTypes.get(id).getPath();
    }

    public static SpriteType getTypeByString(String id) {
        for (SpriteType type : textureTypes.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public String getId() { return id; }

    public String getPath() { return path; }
}
