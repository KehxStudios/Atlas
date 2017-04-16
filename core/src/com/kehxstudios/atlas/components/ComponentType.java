package com.kehxstudios.atlas.components;

import com.kehxstudios.atlas.screens.ScreenType;

import java.util.HashMap;

/**
 * Created by ReidC on 2017-04-09.
 */

public enum ComponentType {

    ANIMATION("Animation", AnimationComponent.class),
    CLICKABLE("Clickable", ClickableComponent.class),
    GRAPHICS("Graphics", GraphicsComponent.class),
    INPUT("Input", InputComponent.class),
    PHYSICS("Physics", PhysicsComponent.class),
    // to be removed
    BUTTON("Button", ButtonComponent.class),
    FLOATING_TEXT("Floating_Text", FloatingTextComponent.class),
    HIGH_SCORE_RESET("High_Score_Reset", HighScoreResetComponent.class),
    SCREEN_LAUNCH("Screen_Launch", ScreenLaunchComponent.class);

    private String id;
    private Class loaderClass;

    private static HashMap<String, ComponentType> componentTypes;

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

    public static ComponentType getType(String id) {
        return componentTypes.get(id);
    }

    public String getId() {
        return id;
    }
}
