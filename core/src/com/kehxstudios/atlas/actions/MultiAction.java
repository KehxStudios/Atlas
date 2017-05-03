package com.kehxstudios.atlas.actions;

import java.util.ArrayList;
import com.kehxstudios.atlas.type.ActionType;

/**
 * Created by ReidC on 2017-04-19.
 */

public class MultiAction extends Action {

    private ArrayList<Action> actions;
    
    public MultiAction() {
        actions = new ArrayList<Action>();
    }

    public ArrayList<Action> getActions() { return actions; }
    public void setActions(ArrayList<Action> actions) { this.actions = actions; }
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

    @Override
    public void trigger() {
        for (Action action : actions) {
            action.trigger();
        }
    }
}
