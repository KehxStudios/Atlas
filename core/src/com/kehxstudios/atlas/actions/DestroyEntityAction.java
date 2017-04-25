package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.data.GrimReaper;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.entities.EntityType;

/**
 * Created by ReidC on 2017-04-16.
 */

public class DestroyEntityAction extends Action {

    private Entity entity;

    public Entity getEntity() { return entity; }
    public void setEntity(Entity entity) { this.entity = entity; }

    @Override
    public void trigger() {
        GrimReaper.destroyEntity(entity);
    }
}
