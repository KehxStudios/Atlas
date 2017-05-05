package com.kehxstudios.atlas.type;

import com.kehxstudios.atlas.components.AnimationComponent;
import com.kehxstudios.atlas.components.CameraComponent;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.InViewComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.components.PointerDirectionComponent;

import java.util.HashMap;

/**
 * Created by ReidC on 2017-04-09.
 */

public enum ComponentType {

    ANIMATION("Animation", AnimationComponent.class),
    CAMERA("Camera", CameraComponent.class),
    CLICKABLE("Clickable", ClickableComponent.class),
    FLOATING_TEXT("Floating_Text", FloatingTextComponent.class),
    GRAPHICS("Graphics", GraphicsComponent.class),
    IN_VIEW("In_View", InViewComponent.class),
    PHYSICS("Physics", PhysicsComponent.class),
    POINTER_DIRECTION("Pointer_Direction", PointerDirectionComponent.class),
    VOID("void", Component.class);

    private static HashMap<String, ComponentType> componentTypes;

    private String id;
    private Class loaderClass;

    private ComponentType(String id, Class loaderClass) {
        this.id = id;
        this.loaderClass = loaderClass;
    }

    static {
        componentTypes = new HashMap<String, ComponentType>();
        for (ComponentType type : ComponentType.values()) {
            componentTypes.put(type.id, type);
        }
    }

    public static ComponentType getTypeById(String id) {
        return componentTypes.get(id);
    }

    public String getId() {
        return id;
    }

    public Class getLoaderClass() { return loaderClass; }
}
