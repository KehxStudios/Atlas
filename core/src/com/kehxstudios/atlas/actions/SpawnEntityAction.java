package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.entities.EntityData;

/**
 * Created by ReidC on 2017-04-16.
 */

public class SpawnEntityAction extends Action {

    private EntityData entityData;

    public SpawnEntityAction(EntityData entityData) {
        this.entityData = entityData;
    }

    public void changeEntityData(EntityData entityData) {
        this.entityData = entityData;
    }

    @Override
    public void trigger() {
        new Entity(entityData);
    }
}
