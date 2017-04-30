package com.kehxstudios.atlas.data;

import java.util.HashMap;

/**
 * Created by ReidC on 2017-04-17.
 */

public class ActionData {

    public String type;
    public HashMap<String, String> data;

    public ActionData(String type) {
        this.type = type;
        data = new HashMap<String, String>();
    }

    public ActionData() {
        data = new HashMap<String, String>();
    }

    public void setType(String type) { this.type = type; }

    public String getType() { return type;}

    public void putFloat(String key, float value) {
        data.put(key, "" + value);
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

    public void putInt(String key, int value) {
        data.put(key, "" + value);
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

    public void putBoolean(String key, boolean value) {
        data.put(key, "" + value);
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

    public void putString(String key, String value) {
        data.put(key, value);
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
