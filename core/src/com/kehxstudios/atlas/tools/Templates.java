package com.kehxstudios.atlas.tools;

import com.kehxstudios.atlas.data.ActionData;
import com.kehxstudios.atlas.type.ActionType;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.type.ComponentType;
import com.kehxstudios.atlas.data.EntityData;
import com.kehxstudios.atlas.type.TextureType;
import com.kehxstudios.atlas.type.ScreenType;

/**
 * Created by ReidC on 2017-04-26.
 */

public class Templates {

    public static ActionData createLaunchScreenActionData(ScreenType screenType) {
        ActionData actionData = new ActionData();
        actionData.type = ActionType.LAUNCH_SCREEN.getId();
        actionData.putString("screenType", screenType.getId());
        return actionData;
    }

    public static ActionData createHighScoreResetActionData(ScreenType screenType){
        ActionData actionData = new ActionData();
        actionData.type = ActionType.HIGH_SCORE_RESET.getId();
        actionData.putString("screenType", screenType.getId());
        return actionData;
    }
    
    public static ActionData createMultiActionData(ArrayList<ActionData> actionsData) {
        ActionData actionData = new ActionData();
        actionData.type = ActionType.MULTI.getId();
        for (ActionData data : actionsData) {
            actionData.putString(data.getType().getId(), UtilityTools.getStringFromDataClass(data));
        }
        return actionData;
    }

    public static ActionData createPhysicsActionData(float x, float y) {
        ActionData actionData = new ActionData();
        actionData.setType(ActionType.PHYSICS.getId());
        actionData.putFloat("actionValue_x", x);
        actionData.putFloat("actionValue_y", y);
        return actionData;
    }

    public static ActionData createRepositionActionData(float x, float y, boolean teleport) {
        ActionData actionData = new ActionData();
        actionData.setType(ActionType.REPOSITION.getId());
        actionData.putFloat("actionValue_x", x);
        actionData.putFloat("actionValue_y", y);
        actionData.putBoolean("teleport", teleport);
        return actionData;
    }

    public static ActionData createScoreActionData(int score) {
        ActionData actionData = new ActionData();
        actionData.setType(ActionType.SCORE.getId());
        actionData.putInt("actionValue", score);
        return actionData;
    }

    public static ActionData createSpawnEntityActionData(EntityData entityData) {
        ActionData actionData = new ActionData();
        actionData.setType(ActionType.SPAWN_ENTITY.getId());
        actionData.putString("entityData", UtilityTool.getStringFromDataClass(entityData));
        return actionData;
    }

    private static ComponentData createComponentData() {
        ComponentData componentData = new ComponentData();
        componentData.setUseComponentPosition(false);
        componentData.setUsePositionAsOffset(false);
        componentData.setEnabled(true);
        return componentData;
    }
    
    public static ComponentData createCameraComponentData(float width, float height, boolean flipped) {
        ComponentData cameraData = createComponentData();
        cameraData.putFloat("width", width);
        cameraData.putFloat("height", height);
        cameraData.putBoolean("flipped", flipped);
        return cameraData;
    }

    public static ComponentData createPhysicsComponentData(float maxAccelerationX, float maxAccelerationY,
                                                           float maxVelocityX, float maxVelocityY,
                                                           float bounds_width, float bounds_height,
                                                           boolean collidable) {
        ComponentData physicsData = createComponentData();
        physicsData.setType(ComponentType.PHYSICS.getId());
        physicsData.putFloat("maxAccerlation_x", maxAccelerationX);
        physicsData.putFloat("maxAcceleration_y", maxAccelerationY);
        physicsData.putFloat("maxVeloity_x", maxVelocityX);
        physicsData.putFloat("maxVelocity_y", maxVelocityY);
        physicsData.putFloat("bounds_width", bounds_width);
        physicsData.putFloat("bounds_height", bounds_height);
        physicsData.putBoolean("collidable", collidable);
        return physicsData;
    }

    public static ComponentData createClickableComponentData(float width, float height, boolean singleTrigger, ActionData actionData) {
        ComponentData clickableData = createComponentData();
        clickableData.setType(ComponentType.CLICKABLE.getId());
        clickableData.putFloat("width", width);
        clickableData.putFloat("height", height);
        clickableData.putBoolean("singleTrigger", singleTrigger);
        clickableData.putString("action", UtilityTool.getStringFromDataClass(actionData));
        return clickableData;
    }

    public static ComponentData createFloatingTextComponentData(String label, String text) {
        ComponentData floatingTextData = createComponentData();
        floatingTextData.setType(ComponentType.FLOATING_TEXT.getId());
        floatingTextData.putString("label", label);
        floatingTextData.putString("text", text);
        return floatingTextData;
    }

    public static ComponentData createGraphicsComponentData(float width, float height, int layer, TextureType textureType) {
        ComponentData graphicsData = createComponentData();
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
