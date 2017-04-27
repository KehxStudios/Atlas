package com.kehxstudios.atlas.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.kehxstudios.atlas.actions.ActionData;
import com.kehxstudios.atlas.components.ComponentData;
import com.kehxstudios.atlas.entities.EntityData;
import com.kehxstudios.atlas.screens.ScreenType;

/**
 * Created by ReidC on 2017-04-20.
 */

public class UtilityTool {


    private static Json json = new Json();

    // Transforms EntityData, ComponentData to string in json format
    public static <T> String getStringFromDataClass(T data) {
        return json.prettyPrint(data);
    }

    public static EntityData getEntityDataFromString(String jsonString) {
        return json.fromJson(EntityData.class, jsonString);
    }

    public static ComponentData getComponentDataFromString(String jsonString) {
        return json.fromJson(ComponentData.class, jsonString);
    }

    public static ActionData getActionDataFromString(String jsonString) {
        return json.fromJson(ActionData.class, jsonString);
    }
}
