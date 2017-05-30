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

import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.screens.FlappyBatScreen;
import com.kehxstudios.atlas.screens.GeneRocketsScreen;
import com.kehxstudios.atlas.screens.IntroScreen;
import com.kehxstudios.atlas.screens.LoadingScreen;
import com.kehxstudios.atlas.screens.MainMenuScreen;
import com.kehxstudios.atlas.screens.DeviceInformationScreen;
import com.kehxstudios.atlas.screens.PongScreen;

import java.util.HashMap;

/**
 * Used to store all types of Screen's
 */

public enum ScreenType {

    INTRO("Intro", "atlas/intro.atlas", 512, 910, IntroScreen.class),
    MAIN_MENU("Main Menu", "atlas/mainMenu.atlas", 1440, 2560, MainMenuScreen.class),
    FLAPPY_BAT("Flappy Bat", "atlas/flappyBird.atlas", 240, 400, FlappyBatScreen.class),
    GENE_ROCKETS("Gene Rockets", "atlas/geneRockets.atlas", 512, 910, GeneRocketsScreen.class),
    PONG("Pong", "atlas/pong.atlas", 512, 910, PongScreen.class),
    LOADING("Loading", "loading/loading.atlas", 512, 910, LoadingScreen.class),
    DEVICE_INFORMATION("Device Information", "atlas/deviceInformation.atlas", 256, 455, DeviceInformationScreen.class),
    VOID("Void", "", 0, 0, AScreen.class);

    private String id, atlasPath;
    private float width, height;
    private Class loaderClass;

    private ScreenType(String id, String atlasPath, float width, float height, Class loaderClass) {
        this.id = id;
        this.atlasPath = atlasPath;
        this.width = width;
        this.height = height;
        this.loaderClass = loaderClass;
    }

    public String getId() { return id; }

    public String getAtlasPath() { return atlasPath; }

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
