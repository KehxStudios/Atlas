
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

import com.kehxstudios.atlas.entities.Entity;

import java.util.HashMap;

/**
 * Used to store all types of Entity's, to be used in the future
 */

public enum EntityType {

    SCREEN("Screen"),
    PLAYER("Player"),
    ENEMY("Enemy"),
    WORLD("World"),
    BOUNDS("Bounds"),
    OTHER("Other"),
    VOID("Void");

    private String id;

    private EntityType(String name) {
        this.id = name;
    }

    public String getId() {
        return id;
    }

    public static EntityType getTypeById(String id) {
        return entityTypes.get(id);
    }

    private static HashMap<String, EntityType> entityTypes;

    static {
        entityTypes = new HashMap<String, EntityType>();
        for (EntityType type : EntityType.values()) {
            entityTypes.put(type.id, type);
        }
    }
}
