package com.kehxstudios.atlas.data;

import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.actions.ActionData;
import com.kehxstudios.atlas.actions.ActionType;
import com.kehxstudios.atlas.components.ComponentData;
import com.kehxstudios.atlas.components.ComponentType;
import com.kehxstudios.atlas.entities.EntityData;
import com.kehxstudios.atlas.screens.ScreenType;
import com.kehxstudios.atlas.tools.UtilityTool;

/**
 * Created by ReidC on 2017-04-26.
 */

public class Templates {

    public static ActionData createLaunchScreenData(ScreenType screenType) {
        ActionData actionData = new ActionData();
        actionData.type = ActionType.LAUNCH_SCREEN.getId();
        actionData.putString("screenType", screenType.getId());
        return actionData;
    }

    public static ActionData createHighScoreReset(ScreenType screenType){
        ActionData actionData = new ActionData();
        actionData.type = ActionType.HIGH_SCORE_RESET.getId();
        actionData.putString("screenType", screenType.getId());
        return actionData;
    }

    private static ComponentData createComponentData() {
        ComponentData componentData = new ComponentData();
        componentData.setUseComponentPosition(false);
        componentData.setUsePositionAsOffset(false);
        componentData.setEnabled(true);
        return componentData;
    }

    public static ComponentData createClickableData(float width, float height, boolean singleTrigger, ActionData actionData) {
        ComponentData clickableData = createComponentData();
        clickableData.setType(ComponentType.CLICKABLE.getId());
        clickableData.putFloat("width", width);
        clickableData.putFloat("height", height);
        clickableData.putBoolean("singleTrigger", singleTrigger);
        clickableData.putString("action", UtilityTool.getStringFromDataClass(actionData));
        return clickableData;
    }

    public static ComponentData createFloatingTextData(String label, String text) {
        ComponentData floatingTextData = createComponentData();
        floatingTextData.setType(ComponentType.FLOATING_TEXT.getId());
        floatingTextData.putString("label", label);
        floatingTextData.putString("text", text);
        return floatingTextData;
    }

    public static ComponentData createGraphicsData(float width, float height, int layer, TextureType textureType) {
        ComponentData graphicsData =createComponentData();
        graphicsData.setType(ComponentType.GRAPHICS.getId());
        graphicsData.putFloat("width", width);
        graphicsData.putFloat("height", height);
        graphicsData.putInt("layer", layer);
        graphicsData.putString("textureType", textureType.getId());
        return graphicsData;
    }

    public static EntityData createEntityData(float x, float y) {
        EntityData entityData = new EntityData();
        entityData.setX(x);
        entityData.setY(y);

        return entityData;
    }



}
