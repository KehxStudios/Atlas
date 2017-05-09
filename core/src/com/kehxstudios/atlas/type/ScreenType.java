package com.kehxstudios.atlas.type;

import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.screens.FlappyBatScreen;
import com.kehxstudios.atlas.screens.IntroScreen;
import com.kehxstudios.atlas.screens.LoadingScreen;
import com.kehxstudios.atlas.screens.MainMenuScreen;

import java.util.HashMap;

/**
 * Created by ReidC on 2017-04-07.
 */

public enum ScreenType {

    INTRO("Intro", "texturePacks/intro.atlas", 1080, 1920, IntroScreen.class),
    MAIN_MENU("Main Menu", "texturePacks/mainMenu.atlas", 1440, 2560, MainMenuScreen.class),
    FLAPPY_BAT("Flappy Bat", "texturePacks/flappyBat.atlas", 240, 400, FlappyBatScreen.class),
    GENE_ROCKETS("Gene Rockets", "texturePacks/geneRockets.atlas", 512, 910, GeneRocketsScreen.class),
    PONG("Pong", "texturePacks/pong.atlas", 512, 910, PongScreen.class),
    RECAP_JOURNAL("Recap Journal", "texturePacks/recapJournal.atlas", 512, 910, RecapJournalScreen.class),
    LOADING("Loading", "texturePacks/loading.atlas", 512, 910, LoadingScreen.class),
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
