package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.entities.EntityData;
import com.kehxstudios.atlas.tools.UtilityTool;

/**
 * Created by ReidC on 2017-04-16.
 */

public class SpawnEntityAction extends Action {

    private EntityData entityData;

    public SpawnEntityAction(EntityData entityData) {
        this.entityData = entityData;
        init();
    }

    public SpawnEntityAction(ActionData actionData) {
        entityData = UtilityTool.getEntityDataFromString(actionData.getString("entity", "-"));
        init();
    }

    @Override
    protected void init() {
        type = ActionType.SPAWN_ENTITY;
    }

    @Override
    public ActionData getActionData() {
        ActionData actionData = super.getActionData();
        actionData.putString("entity", UtilityTool.getStringFromDataClass(entityData));
        return actionData;
    }

    public void changeEntityData(EntityData entityData) {
        this.entityData = entityData;
    }

    @Override
    public void trigger() {
        new Entity(entityData);
    }
}
