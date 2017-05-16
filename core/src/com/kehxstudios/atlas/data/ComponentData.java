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

package com.kehxstudios.atlas.data;

import java.util.HashMap;

/**
 * Holds all data containing to an Component required for creation
 */

public class ComponentData {

    public String id;
    public String type;
    public String entityId;
    public boolean enabled;
    public float x, y;
    public boolean useComponentPosition;
    public boolean usePositionAsOffset;
    public HashMap<String, String> data;

    public ComponentData() {
        x = 0;
        y = 0;
        usePositionAsOffset = false;
        useComponentPosition = false;
        enabled = true;
        data = new HashMap<String, String>();
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public boolean getUseComponentPosition() { return useComponentPosition; }

    public void setUseComponentPosition(boolean value) { useComponentPosition = value; }

    public boolean getUsePositionAsOffset() { return usePositionAsOffset; }

    public void setUsePositionAsOffset(boolean value) { usePositionAsOffset = value; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntityId() { return entityId; }

    public void setEntityId(String entityId) { this.entityId = entityId; }

    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean value) { enabled = value; }

    public float getX() { return x; }

    public void setX(float x) { this.x = x; }

    public float getY() { return y; }

    public void setY(float y) { this.y = y; }

    public void putFloat(String key, float value) {
        data.put(key, "" + value);
    }

    public void putInt(String key, int value) {
        data.put(key, "" + value);
    }

    public void putBoolean(String key, boolean value) {
        data.put(key, "" + value);
    }

    public void putString(String key, String value) {
        data.put(key, value);
    }

    public float getFloat(String key, float defaultValue) {
        if (data.containsKey(key)) {
            try {
                return Float.parseFloat(data.get(key));
            } catch(Exception e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }
    public int getInt(String key, int defaultValue) {
        if (data.containsKey(key)) {
            try {
                return Integer.parseInt(data.get(key));
            } catch(Exception e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        if (data.containsKey(key)) {
            try {
                return Boolean.parseBoolean(data.get(key));
            } catch(Exception e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public String getString(String key, String defaultValue) {
        if (data.containsKey(key)) {
            try {
                return data.get(key);
            } catch(Exception e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public boolean hasKey(String key) {
        return data.containsKey(key);
    }
}
