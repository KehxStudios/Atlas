package com.kehxstudios.atlas.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.kehxstudios.atlas.screens.AScreenData;
import com.kehxstudios.atlas.screens.ScreenType;

/**
 * Created by ReidC on 2017-04-15.
 */

public class DataLoader {

    private static Json json = new Json();

    public static AScreenData load(ScreenType type) {
        DebugTool.log("Loading screen data");
        FileHandle file = Gdx.files.internal("screens/" + type.getId() + ".sd");
        DebugTool.log("File set");
        if (file.exists()) {
            AScreenData screenData = json.fromJson(AScreenData.class, file.readString());
            DebugTool.log("AScreenData loaded");
            return screenData;
        }
        DebugTool.log("Returning null");
        return null;
    }
}
