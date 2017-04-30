package com.kehxstudios.atlas.type;

import com.kehxstudios.atlas.entities.Entity;

import java.util.HashMap;

/**
 * Could be removed in the future
 */

public enum EntityType {

    SCREEN("Screen", Entity.class),
    PLAYER("Player", Entity.class),
    VOID("void", Entity.class);

    private String id;
    private Class loaderClass;

    private EntityType(String name, Class loaderClass) {
        this.id = name;
        this.loaderClass = loaderClass;
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
