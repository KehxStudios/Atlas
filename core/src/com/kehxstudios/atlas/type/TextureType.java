/*******************************************************************************
 * Copyright 2017 See AUTHORS file.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
 * associated documentation files (the "Software"), to deal in the Software without restriction, 
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial 
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/

package com.kehxstudios.atlas.type;

import java.util.HashMap;

/**
 * Used to store all types of Texture's
 */

public enum TextureType {

    INTRO_DEV_LOGO("IntroDevLogo", "devLogo", 512, 512),
    INTRO_GAME_LOGO("IntroGameLogo","gameLogo", 512, 512),

    ICONS_BACK("IconBack", "back", 128, 128),

    MAIN_MENU_BACKGROUND("MainMenuBackground", "background", 720, 1280),
    MAIN_MENU_BUTTON("MainMenuButton", "button", 380, 98),

    FLAPPY_BIRD_BACKGROUND("FlappyBirdBackground", "background", 288, 512),
    FLAPPY_BIRD_BIRD("FlappyBirdBird", "bird", 34, 24),
    FLAPPY_BIRD_WALL("FlappyBirdWall",  "wall", 52, 320),
    FLAPPY_BIRD_GROUND("FlappyBirdGround", "ground", 366, 112),
    
    PONG_BACKGROUND("PongBackground", "background", 512, 910),
    PONG_PADDLE("PongPaddle", "paddle", 10, 50),
    PONG_PADDLE_BLUE("PongPaddle", "paddleBlue", 104, 24),
    PONG_PADDLE_RED("PongPaddle", "paddleRed", 104, 24),
    PONG_BALL("PongBall", "ball", 10, 10),

    GENE_ROCKETS_BACKGROUND("GeneRocketBackground", "background", 512, 910),
    GENE_ROCKETS_ROCKET("GeneRocketRocket", "rocket", 21, 50),
    GENE_ROCKETS_TARGET("GeneRocketTarget", "target", 100, 100),

    PLATFORM_WINDOWS_COMPUTER("WindowsComputer", "windows_computer", 128, 128),
    PLATFORM_ANDROID_PHONE("AndroidPhone", "android_phone", 128, 128),
    PLATFORM_APPLE_PHONE("ApplePhone", "apple_phone", 128, 128),

    LOADING_BACKGROUND("LoadingBackground", "background", 512, 910),
    LOADING_GAME_LOGO("LoadingGameLogo", "gameLogo", 512, 512),
    LOADING_HEX_FILLED("LoadingHexFilled", "hexFilled", 128, 128),
    LOADING_HEX_OUTLINE("LoadingHexOutline", "hexOutline", 128, 128),

    SETTINGS_BACKGROUND("SettingsBackground", "background", 720, 1280),
    SETTINGS_BUTTON("SettingsButton", "button", 380, 98),


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
