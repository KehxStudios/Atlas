package com.kehxstudios.atlas.data;

import java.util.HashMap;

/**
 * Created by ReidC on 2017-04-19.
 */

public enum TextureType {

    DEV_LOGO("DevLogo", "screens/intro/", "devLogo.png"),
    GAME_LOGO("GameLogo", "screens/intro/" ,"gameLogo.png"),

    MAINMENU_BACKGROUND("MainMenuBackground", "screens/mainMenu/", "background.png"),
    MAINMENU_BORDERS("MainMenuBorders", "screens/flappyBird/", "flappyBird.png"),

    FLAPPYBIRD_BACKGROUND("FlappyBirdBackground", "screens/flappyBird/", "bg.png"),
    FLAPPYBIRD_BIRD("FlappyBirdBird", "screens/flappyBird/", "bird.png"),
    FLAPPYBIRD_TOPTUBE("FlappyBirdTopTube", "screens/flappyBird/", "toptube.png"),
    FLAPPYBIRD_BOTTOMTUBE("FlappyBirdBottomTube", "screens/flappyBird/", "bottomtube.png"),
    FLAPPYBIRD_GROUND("FlappyBirdGround", "screens/flappyBird/", "ground.png");

    private String id, path, fileName;

    private static HashMap<String, TextureType> textureTypes;

    private TextureType(String id, String path, String fileName) {
        this.id = id;
        this.path = path;
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() { return fileName; }

    public String getFullPath() { return path + fileName; }

    public TextureType getTypeFromId(String id) {
        return textureTypes.get(id);
    }

    static {
        textureTypes = new HashMap<String, TextureType>();
        for (TextureType type : TextureType.values()) {
            textureTypes.put(type.getId(), type);
        }
    }
}
