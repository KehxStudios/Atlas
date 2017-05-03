package com.kehxstudios.atlas.type;

import com.kehxstudios.atlas.entities.Entity;

import java.util.HashMap;

/**
 * Will be used in the future
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
