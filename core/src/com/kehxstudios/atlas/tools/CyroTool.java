package com.kehxstudios.atlas.tools;


import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.InViewComponent;
import com.kehxstudios.atlas.data.ActionData;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.data.EntityData;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.type.ActionType;
import com.kehxstudios.atlas.type.ComponentType;

public class CyroTool {

    public static EntityData freezeEntity(Entity entity) {
        EntityData entityData = new EntityData();
        entityData.setId(entity.getId());
        entityData.setX(entity.getPosition().x);
        entityData.setY(entity.getPosition().y);
        
        for (Component component : entity.getComponents()) {
            entityData.putString(component.getType().getId(), UtilityTool.getStringFromDataClass(freezeComponent(component)));
        }
        
        return entityData;
    }

    public static ComponentData freezeComponent(Component component) {
        ComponentData componentData = new ComponentData();
        componentData.setX(component.getPosition().x);
        componentData.setY(component.getPosition().y);
        componentData.setType(component.getType().getId());
        componentData.setEnabled(component.isEnabled());
        componentData.setUsePositionAsOffset(component.getUsePositionAsOffset());
        componentData.setUseComponentPosition(component.getUseComponentPosition());
        if (component.getType() == ComponentType.ANIMATION) {

        } else if (component.getType() == ComponentType.CLICKABLE) {
            componentData.putFloat("width", (((ClickableComponent)component).getWidth()));
            componentData.putFloat("height", (((ClickableComponent)component).getHeight()));
            componentData.putBoolean("singleTarget", (((ClickableComponent)component).isSingleTrigger()));
            componentData.putString("action", UtilityTool.getStringFromDataClass(freezeAction(((ClickableComponent)component).getAction())));
        } else if (component.getType() == ComponentType.FLOATING_TEXT) {

        } else if (component.getType() == ComponentType.GRAPHICS) {
            componentData.putFloat("width", ((GraphicsComponent)component).getWidth());
            componentData.putFloat("height", ((GraphicsComponent)component).getHeight());
            componentData.putInt("layer", ((GraphicsComponent)component).getLayer());
            componentData.putString("textureType", ((GraphicsComponent)component).getTextureType().getId());
        } else if (component.getType() == ComponentType.IN_VIEW) {
            componentData.putFloat("width", ((InViewComponent)component).getWidth());
            componentData.putFloat("height", ((InViewComponent)component).getHeight());
            componentData.putString("action", UtilityTool.getStringFromDataClass(freezeAction(((InViewComponent)component).getAction())));
        } else if (component.getType() == ComponentType.PHYSICS) {


        } else if (component.getType() == ComponentType.POINTER_DIRECTION) {

        }
        return componentData;
    }

    public static ActionData freezeAction(Action action) {
        ActionData actionData = new ActionData();
        actionData.setType(action.getType().getId());
        if (action.getType() == ActionType.DESTROY_ENTITY) {

        } else if (action.getType() == ActionType.HIGH_SCORE_RESET) {

        } else if (action.getType() == ActionType.LAUNCH_SCREEN) {

        } else if (action.getType() == ActionType.MULTI) {

        } else if (action.getType() == ActionType.PHYSICS) {

        } else if (action.getType() == ActionType.REPOSITION) {

        } else if (action.getType() == ActionType.SCORE) {

        } else if (action.getType() == ActionType.SPAWN_ENTITY) {

        }
        return actionData;
    }
}
