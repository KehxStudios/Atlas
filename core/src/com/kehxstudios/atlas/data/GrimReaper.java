package com.kehxstudios.atlas.data;

import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.entities.Entity;

/**
 * Created by ReidC on 2017-04-24.
 */

public class GrimReaper {

    private static GrimReaper instance;
    public static GrimReaper getInstance() {
        if (instance == null) {
            instance = new GrimReaper();
        }
        return instance;
    }

    public void destroyEntity(Entity entity) {

    }

    public void destroyComponent(Component component) {

    }

    public void destroyAction(Action action) {

    }
}
