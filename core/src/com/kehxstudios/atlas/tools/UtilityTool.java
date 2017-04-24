package com.kehxstudios.atlas.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.kehxstudios.atlas.components.ComponentData;
import com.kehxstudios.atlas.entities.EntityData;
import com.kehxstudios.atlas.screens.AScreenData;
import com.kehxstudios.atlas.screens.ScreenType;

/**
 * Created by ReidC on 2017-04-20.
 */

public class UtilityTool {


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
    public static <T> String getStringFromDataClass(T data) {
        return json.prettyPrint(data);
    }

    public static String getDataClassFromString(String jsonString, Class dataClass) {
        return json.fromJson(dataClass, jsonString).toString();
    }

    public static AScreenData getAScreenDataFromString(String jsonString) {
        return json.fromJson(AScreenData.class, jsonString);
    }

    public static EntityData getEntityDataFromString(String jsonString) {
        return json.fromJson(EntityData.class, jsonString);
    }

    public static ComponentData getComponentDataFromString(String jsonString) {
        return json.fromJson(ComponentData.class, jsonString);
    }

}
