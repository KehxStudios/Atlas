package com.kehxstudios.atlas.entities;

import java.util.HashMap;

/**
 * Could be removed in the future
 */

public enum EntityType {

    VOID("void", Entity.class, 0, 0),
    PLAYER("Player", Entity.class, 32, 32);

    private String id;
    private Class loaderClass;
    private int width, height;

    private EntityType(String name, Class loaderClass, int width, int height) {
        this.id = name;
        this.loaderClass = loaderClass;
        this.width = width;
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
