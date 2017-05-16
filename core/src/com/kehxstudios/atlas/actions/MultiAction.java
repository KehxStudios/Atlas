/*******************************************************************************
 * Copyright 2017 See AUTHORS file.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
 * associated documentation files (the "Software"), to deal in the Software without restriction, 
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial 
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/

package com.kehxstudios.atlas.actions;

import java.util.ArrayList;
import com.kehxstudios.atlas.type.ActionType;

/**
 * Used to trigger multiple actions after one another
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
