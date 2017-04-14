package com.kehxstudios.atlas.entities;

import java.util.HashMap;

/**
 * Created by ReidC on 2017-04-06.
 */

public enum EntityType {

    PLAYER("Player", Entity.class, 32, 32, 40);

    private String name;
    private Class loaderClass;
    private int width, height;
    private float weight;

    private EntityType(String name, Class loaderClass, int width, int height, float weight) {
        this.name = name;
        this.loaderClass = loaderClass;
        this.width = width;
        this.height = height;
        this.weight = weight;
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

    public float getWeight() {
        return weight;
    }

    private static HashMap<String, EntityType> entityTypes;

    static {
        entityTypes = new HashMap<String, EntityType>();
        for (EntityType type : EntityType.values()) {
            entityTypes.put(type.name, type);
        }
    }
}
