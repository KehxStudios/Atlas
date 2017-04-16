package com.kehxstudios.atlas.entities;

import java.util.HashMap;

/**
 * Could be removed in the future
 */

public enum EntityType {

    PLAYER("Player", Entity.class, 32, 32);

    private String name;
    private Class loaderClass;
    private int width, height;

    private EntityType(String name, Class loaderClass, int width, int height) {
        this.name = name;
        this.loaderClass = loaderClass;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private static HashMap<String, EntityType> entityTypes;

    static {
        entityTypes = new HashMap<String, EntityType>();
        for (EntityType type : EntityType.values()) {
            entityTypes.put(type.name, type);
        }
    }
}
