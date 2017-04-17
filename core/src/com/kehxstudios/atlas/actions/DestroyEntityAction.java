package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.entities.Entity;

/**
 * Created by ReidC on 2017-04-16.
 */

public class DestroyEntityAction extends Action {

    private Entity entity;

    public DestroyEntityAction(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void trigger() {
        entity.destroy();
    }
}
