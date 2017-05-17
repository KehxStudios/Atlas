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

package com.kehxstudios.atlas.entities;

import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.type.ComponentType;

import java.util.ArrayList;

/**
 * The main holder class for attached Components
 */

public class Entity {

    // Unique identifier for Entity
    public int id;
    // Origin position for all linked @components
    public Vector2 position;
    // Hashmap of the Component's id and it's type
    public HashMap<Integer, ComponentType> components;
    
    // Basic constructor to initalize @position and @components
    public Entity() {
        position = new Vector2();
        components = new HashMap<Integer, ComponentType>();
    }
}
