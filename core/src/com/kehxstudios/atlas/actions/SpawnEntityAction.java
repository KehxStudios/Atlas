package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.data.Factory;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.entities.EntityData;
import com.kehxstudios.atlas.tools.UtilityTool;

/**
 * Created by ReidC on 2017-04-16.
 */

public class SpawnEntityAction extends Action {

    private EntityData entityData;

    public EntityData getEntityData() { return entityData; }
    public void setEntityData(EntityData entityData) {
        this.entityData = entityData;
    }

    @Override
    public void trigger() {
        Factory.getInstance().createEntity(entityData);
    }
}
