package com.kehxstudios.atlas.data;

import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.components.AnimationComponent;
import com.kehxstudios.atlas.components.ButtonComponent;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.ComponentType;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.EntityManager;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.managers.InputManager;
import com.kehxstudios.atlas.managers.PhysicsManager;

/**
 * Created by ReidC on 2017-04-24.
 */

public class GrimReaper {

    public static void destroyEntity(Entity entity) {
        for (; entity.getComponents().size() != 0;) {
            destroyComponent(entity.getComponents().get(0));
        }
        EntityManager.getInstance().removeEntity(entity);
    }

    public static void destroyComponent(Component component) {
        if (component.getType() == ComponentType.ANIMATION) {
            GraphicsManager.getInstance().remove((AnimationComponent)component);
        } else if (component.getType() == ComponentType.BUTTON) {
            InputManager.getInstance().removeButton((ButtonComponent)component);
        } else if (component.getType() == ComponentType.CLICKABLE) {
            InputManager.getInstance().removeClickable((ClickableComponent)component);
        } else if (component.getType() == ComponentType.FLOATING_TEXT) {

        } else if (component.getType() == ComponentType.GRAPHICS) {
            GraphicsManager.getInstance().remove((GraphicsComponent)component);
        } else if (component.getType() == ComponentType.IN_VIEW) {

        } else if (component.getType() == ComponentType.PHYSICS) {
            PhysicsManager.getInstance().remove((PhysicsComponent)component);
        } else if (component.getType() == ComponentType.POINTER_DIRECTION) {

        }
        EntityManager.getInstance().removeComponent(component.getEntity(), component);
        component.getEntity().removeComponent(component);
    }

    public static void destroyAction(Action action) {

    }
}
