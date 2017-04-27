package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.EntityManager;

/**
 * Created by ReidC on 2017-04-16.
 */

public class DestroyEntityAction extends Action {

    private Entity entity;

    public Entity getEntity() { return entity; }
    public void setEntity(Entity entity) { this.entity = entity; }

    @Override
    public void trigger() {
        EntityManager.getInstance().markEntityForRemoval(entity);
    }
}
