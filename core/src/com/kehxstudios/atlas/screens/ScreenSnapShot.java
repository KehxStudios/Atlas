package com.kehxstudios.atlas.screens;

import java.util.HashMap;

/**
 * Created by ReidC on 2017-04-07.
 */

public class ScreenSnapShot {

    public String type;
    public int WIDTH, HEIGHT;
    public HashMap<String, String> data;

    public ScreenSnapShot() {
        data = new HashMap<String, String>();
    }

    public ScreenSnapShot(String type, int WIDTH, int HEIGHT) {
        this.type = type;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        data = new HashMap<String, String>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
