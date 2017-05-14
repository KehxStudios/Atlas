package com.kehxstudios.atlas.tools;

import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.data.ActionData;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.type.ActionType;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.type.ComponentType;
import com.kehxstudios.atlas.data.EntityData;
import com.kehxstudios.atlas.type.MusicType;
import com.kehxstudios.atlas.type.SoundType;
import com.kehxstudios.atlas.type.TextureType;
import com.kehxstudios.atlas.type.ScreenType;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-26.
 */

public class Templates {

    public static ActionData destroyEntityActionData(Entity entity) {
        ActionData data = new ActionData();   
        data.type = ActionType.DESTROY_ENTITY.getId();
        data.putString("entityId", entity.getId());
        return data;
    }
    
    public static ActionData followActionData(boolean vertical, boolean horizontal) {
        ActionData data = new ActionData();
        data.type = ActionType.FOLLOW.getId();   
        data.putBoolean("vertical", vertical);
        data.putBoolean("horizontal", horizontal);
        return data;
    }
    
    public static ActionData highScoreResetActionData(ScreenType screenType){
        ActionData data = new ActionData();
        data.type = ActionType.HIGH_SCORE_RESET.getId();
        data.putString("screenType", screenType.getId());
        return data;
    }
    
    public static ActionData launchScreenActionData(ScreenType screenType) {
        ActionData data = new ActionData();
        data.type = ActionType.LAUNCH_SCREEN.getId();
        data.putString("screenType", screenType.getId());
        return data;
    }
    
    public static ActionData multiActionData(ArrayList<ActionData> actionsData) {
        ActionData data = new ActionData();
        data.type = ActionType.MULTI.getId();
        for (ActionData action : actionsData) {
            data.putString(data.getType(), UtilityTool.getStringFromDataClass(action));
        }
        return data;
    }

    public static ActionData physicsActionData(float x, float y) {
        ActionData data = new ActionData();
        data.setType(ActionType.PHYSICS.getId());
        data.putFloat("actionValue_x", x);
        data.putFloat("actionValue_y", y);
        return data;
    }

    public static ActionData repositionActionData(float x, float y, boolean teleport) {
        ActionData data = new ActionData();
        data.setType(ActionType.REPOSITION.getId());
        data.putFloat("actionValue_x", x);
        data.putFloat("actionValue_y", y);
        data.putBoolean("teleport", teleport);
        return data;
    }

    public static ActionData resetScreenActionData() {
        ActionData data = new ActionData();
        data.setType(ActionType.RESET_SCREEN.getId());
        return data;
    }

    public static ActionData scoreActionData(int score) {
        ActionData data = new ActionData();
        data.setType(ActionType.SCORE.getId());
        data.putInt("actionValue", score);
        return data;
    }

    public static ActionData spawnEntityActionData(EntityData entityData) {
        ActionData data = new ActionData();
        data.setType(ActionType.SPAWN_ENTITY.getId());
        data.putString("entityData", UtilityTool.getStringFromDataClass(entityData));
        return data;
    }

    
    private static ComponentData componentData() {
        ComponentData data = new ComponentData();
        data.setUseComponentPosition(false);
        data.setUsePositionAsOffset(false);
        data.setEnabled(true);
        return data;
    }
    
    public static ComponentData animationComponentData() {
        ComponentData data = componentData();
        data.setType(ComponentType.ANIMATION.getId());
        
        return data;
    }
    
    public static ComponentData cameraComponentData(float width, float height, boolean flipped) {
        ComponentData data = componentData();
        data.setType(ComponentType.CAMERA.getId());
        data.putFloat("width", width);
        data.putFloat("height", height);
        data.putBoolean("flipped", flipped);
        return data;
    }

    public static ComponentData clickableComponentData(float width, float height, boolean singleTrigger, ActionData actionData) {
        ComponentData data = componentData();
        data.setType(ComponentType.CLICKABLE.getId());
        data.putFloat("width", width);
        data.putFloat("height", height);
        data.putBoolean("singleTrigger", singleTrigger);
        data.putString("action", UtilityTool.getStringFromDataClass(actionData));
        return data;
    }
    
    public static ComponentData collisionComponentData(float width, float height, boolean staticPosition, boolean singleTrigger,
                boolean triggered, ActionData actionData) {
        ComponentData data = componentData();
        data.setType(ComponentType.COLLISION.getId());
        data.putFloat("width", width);
        data.putFloat("height", height);
        data.putBoolean("staticPosition", staticPosition);
        data.putBoolean("singleTrigger", singleTrigger);
        data.putBoolean("triggered", triggered);
        data.putString("action", UtilityTool.getStringFromDataClass(actionData));
        return data; 
    }
    
    public static ComponentData floatingTextComponentData(String label, String text, float scale) {
        ComponentData data = componentData();
        data.setType(ComponentType.FLOATING_TEXT.getId());
        data.putString("label", label);
        data.putString("text", text);
        data.putFloat("scale", scale);
        return data;
    }

    public static ComponentData geneRocketComponentData() {
        ComponentData data = componentData();
        return data;
    }
    
    public static ComponentData graphicsComponentData(float width, float height, int layer, TextureType textureType) {
        ComponentData data = componentData();
        data.setType(ComponentType.GRAPHICS.getId());
        data.putFloat("width", width);
        data.putFloat("height", height);
        data.putInt("layer", layer);
        data.putString("textureType", textureType.getId());
        return data;
    }
    
    public static ComponentData inViewComponentData() {
        ComponentData data = componentData();
        data.setType(ComponentType.IN_VIEW.getId());
        
        return data;
    }
    
    public static ComponentData musicComponentData(MusicType musicType, float volume) {
        ComponentData data = componentData();
        data.setType(ComponentType.MUSIC.getId());
        data.putString("musicType", musicType.getId());
        data.putFloat("volume", volume);
        return data;
    }
    
    public static ComponentData physicsComponentData(float maxAccelerationX, float maxAccelerationY,
                                                           float maxVelocityX, float maxVelocityY) {
        ComponentData data = componentData();
        data.setType(ComponentType.PHYSICS.getId());
        data.putFloat("maxAccerlation_x", maxAccelerationX);
        data.putFloat("maxAcceleration_y", maxAccelerationY);
        data.putFloat("maxVeloity_x", maxVelocityX);
        data.putFloat("maxVelocity_y", maxVelocityY);
        return data;
    }

    public static ComponentData pointerDirectionComponentData() {
        ComponentData data = componentData();
        data.setType(ComponentType.POINTER_DIRECTION.getId());
        
        return data;
    }
    
    public static ComponentData soundComponentData(SoundType soundType, float volume) {
        ComponentData data = componentData();
        data.setType(ComponentType.SOUND.getId());
        data.putString("soundType", soundType.getId());
        data.putFloat("volume", volume);
        return data;
    }

    public static EntityData createEntityData(float x, float y) {
        EntityData data = new EntityData();
        data.setX(x);
        data.setY(y);

        return data;
    }
}
