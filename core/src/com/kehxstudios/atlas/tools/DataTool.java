package com.kehxstudios.atlas.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.kehxstudios.atlas.components.ComponentData;
import com.kehxstudios.atlas.data.TextureType;
import com.kehxstudios.atlas.entities.EntityData;
import com.kehxstudios.atlas.screens.AScreenData;
import com.kehxstudios.atlas.screens.ScreenType;

/**
 * Created by ReidC on 2017-04-15.
 */

public class DataTool {

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

    // Transforms AScreenData, EntityData, ComponentData to string in json format
    public static <T> String dataClassToJsonString(T data) {
        return json.prettyPrint(data);
    }

    public static AScreenData getScreenDataFromString(String jsonString) {
        return json.fromJson(AScreenData.class, jsonString);
    }

    public static EntityData getEntityDatFromString(String jsonString) {
        return json.fromJson(EntityData.class, jsonString);
    }

    public static ComponentData getComponentDataFromString(String jsonString) {
        return json.fromJson(ComponentData.class, jsonString);
    }

    public static String getTexturePathFromName(String textureName) {


        return null;
    }

    public static TextureType getTexturePaths() {
        FileHandle file = Gdx.files.internal("screens/TextureType.pd");
        if (file.exists()) {
            return json.fromJson(TextureType.class, file.readString());
        } else {
            return null;
        }
    }
}
