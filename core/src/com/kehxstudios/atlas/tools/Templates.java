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
 * Used to construct Entity/Component/Action data classes from variables
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

    
    private static ComponentData componentData(ComponentType type) {
        ComponentData data = new ComponentData(type, true);
        return data;
    }
    
    public static ComponentData animationComponentData() {
        ComponentData data = componentData(ComponentType.ANIMATION);
        // ADD ANIMATION DATA
        return data;
    }
    
    public static ComponentData cameraComponentData(float width, float height, boolean flipped) {
        ComponentData data = componentData(ComponentType.CAMERA);
        data.putFloat("width", width);
        data.putFloat("height", height);
        data.putBoolean("flipped", flipped);
        return data;
    }

    public static ComponentData clickableComponentData(float width, float height, boolean singleTrigger, ActionData actionData) {
        ComponentData data = componentData(ComponentType.CLICKABLE);
        data.putFloat("width", width);
        data.putFloat("height", height);
        data.putBoolean("singleTrigger", singleTrigger);
        data.putString("action", UtilityTool.getStringFromDataClass(actionData));
        return data;
    }
    
    public static ComponentData collisionComponentData(float width, float height, boolean staticPosition,
                boolean collided, ActionData actionData) {
        ComponentData data = componentData(ComponentType.COLLISION);
        data.putFloat("width", width);
        data.putFloat("height", height);
        data.putBoolean("staticPosition", staticPosition);
        data.putBoolean("collided", collided);
        data.putString("action", UtilityTool.getStringFromDataClass(actionData));
        return data; 
    }
    
    public static ComponentData floatingTextComponentData(String label, String text, float scale) {
        ComponentData data = componentData(ComponentType.FLOATING_TEXT);
        data.putString("label", label);
        data.putString("text", text);
        data.putFloat("scale", scale);
        return data;
    }

    public static ComponentData geneRocketComponentData(int numberOfGenes) {
        ComponentData data = componentData(ComponentType.GENE_ROCKET);
        data.putInt("numberOfGenes", numberOfGenes);
        return data;
    }
    
    public static ComponentData graphicsComponentData(float width, float height, int layer, float rotation,
                                                      TextureType textureType) {
        ComponentData data = componentData(ComponentType.GRAPHICS);
        data.putFloat("width", width);
        data.putFloat("height", height);
        data.putInt("layer", layer);
        data.putFloat("rotation", rotation);
        data.putString("textureType", textureType.getId());
        return data;
    }
    
    public static ComponentData inViewComponentData(float width, float height, ActionData actionData) {
        ComponentData data = componentData(ComponentType.IN_VIEW);
        data.putFloat("width", width);
        data.putFloat("height", height);
        data.putString("action", UtilityTool.getStringFromDataClass(actionData));
        return data;
    }
    
    public static ComponentData musicComponentData(MusicType musicType, float volume) {
        ComponentData data = componentData(ComponentType.MUSIC);
        data.putString("musicType", musicType.getId());
        data.putFloat("volume", volume);
        return data;
    }
    
    public static ComponentData physicsComponentData(float maxAccelerationX, float maxAccelerationY,
                                                           float maxVelocityX, float maxVelocityY) {
        ComponentData data = componentData(ComponentType.PHYSICS);
        data.putFloat("maxAccerlation_x", maxAccelerationX);
        data.putFloat("maxAcceleration_y", maxAccelerationY);
        data.putFloat("maxVeloity_x", maxVelocityX);
        data.putFloat("maxVelocity_y", maxVelocityY);
        return data;
    }
    
    public static ComponentData soundComponentData(SoundType soundType, float volume) {
        ComponentData data = componentData(ComponentType.SOUND);
        data.putString("soundType", soundType.getId());
        data.putFloat("volume", volume);
        return data;
    }

    public static EntityData createEntityData(float x, float y) {
        EntityData data = new EntityData(x, y);
        return data;
    }
}
