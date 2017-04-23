package com.kehxstudios.atlas.actions;

import com.kehxstudios.atlas.entities.Entity;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-19.
 */

public class MultiAction extends Action {

    private ArrayList<Action> actions;

    public MultiAction(ArrayList<Action> actions) {
        this.actions = actions;
        init();
    }

    public MultiAction(Entity entity, ActionData actionData) {

        init();
    }

    public void addAction(Action action, int position) {
        if (position < 0)
            position = 0;
        else if (position > actions.size())
            position = actions.size();
        actions.add(position, action);
    }

    public void removeAction(Action action) {
        actions.remove(action);
    }

    public void removeAllActionsOfType(ActionType type) {
        for (int i = 0; i < actions.size(); i++) {
            if (actions.get(i).type == type) {
                actions.remove(i--);
            }
        }
    }

    public ActionData getActionData() {
        ActionData actionData = super.getActionData();
        return actionData;
    }

    @Override
    public void trigger() {
        for (Action action : actions) {
            action.trigger();
        }
    }

    @Override
    protected void init() {
        type = ActionType.MULTI;
    }
}
