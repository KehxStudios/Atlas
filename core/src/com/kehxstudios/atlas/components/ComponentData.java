package com.kehxstudios.atlas.components;

import java.util.HashMap;

/**
 * Created by ReidC on 2017-04-15.
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
        data = new HashMap<String, String>();
    }

    public ComponentData(String id, String type, String entityId, boolean enabled, float x, float y,
                         boolean useComponentPosition, boolean usePositionAsOffset) {
        this.id = id;
        this.type = type;
        this.entityId = entityId;
        this.enabled = enabled;
        this.x = x;
        this.y = y;
        this.useComponentPosition = useComponentPosition;
        this.usePositionAsOffset = usePositionAsOffset;
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
