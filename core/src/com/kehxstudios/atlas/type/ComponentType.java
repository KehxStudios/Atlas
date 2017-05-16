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

package com.kehxstudios.atlas.type;

import com.kehxstudios.atlas.components.AnimationComponent;
import com.kehxstudios.atlas.components.CameraComponent;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.CollisionComponent;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.components.GeneRocketComponent;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.InViewComponent;
import com.kehxstudios.atlas.components.MusicComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.components.PointerDirectionComponent;
import com.kehxstudios.atlas.components.SoundComponent;

import java.util.HashMap;

/**
 * Used to store all types of Component's
 */

public enum ComponentType {

    ANIMATION("Animation", AnimationComponent.class),
    CAMERA("Camera", CameraComponent.class),
    CLICKABLE("Clickable", ClickableComponent.class),
    COLLISION("Collision", CollisionComponent.class),
    FLOATING_TEXT("Floating_Text", FloatingTextComponent.class),
    GENE_ROCKET("Gene Rocket", GeneRocketComponent.class),
    GRAPHICS("Graphics", GraphicsComponent.class),
    IN_VIEW("In_View", InViewComponent.class),
    MUSIC("Music", MusicComponent.class),
    PHYSICS("Physics", PhysicsComponent.class),
    POINTER_DIRECTION("Pointer_Direction", PointerDirectionComponent.class),
    SOUND("Sound", SoundComponent.class),
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
