package com.kehxstudios.atlas.tools;

import com.badlogic.gdx.utils.Json;
import com.kehxstudios.atlas.data.ActionData;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.data.EntityData;

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
