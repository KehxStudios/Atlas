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

package com.kehxstudios.atlas.type;

import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.actions.DestroyEntityAction;
import com.kehxstudios.atlas.actions.HighScoreResetAction;
import com.kehxstudios.atlas.actions.FollowAction;
import com.kehxstudios.atlas.actions.LaunchScreenAction;
import com.kehxstudios.atlas.actions.MultiAction;
import com.kehxstudios.atlas.actions.PhysicsAction;
import com.kehxstudios.atlas.actions.RepositionAction;
import com.kehxstudios.atlas.actions.ScoreAction;

import java.util.HashMap;

/**
 * Used to store all types of Action's
 */

public enum ActionType {

    DESTROY_ENTITY("Destroy Entity", DestroyEntityAction.class),
    HIGH_SCORE_RESET("High-Score Reset", HighScoreResetAction.class),
    FOLLOW("Follow", FollowAction.class),
    LAUNCH_SCREEN("Launch Screen", LaunchScreenAction.class),
    MULTI("Multi", MultiAction.class), // TO BE USED
    PHYSICS("Physics", PhysicsAction.class),
    REPOSITION("Reposition", RepositionAction.class),
    SCORE("Score", ScoreAction.class),
    VOID("Void", Action.class);

    private static HashMap<String, ActionType> actionTypes;

    private String id;
    private Class loaderClass;

    private ActionType(String id, Class loaderClass) {
        this.id = id;
        this.loaderClass = loaderClass;
    }

    static {
        actionTypes = new HashMap<String, ActionType>();
        for (ActionType type : ActionType.values()) {
            actionTypes.put(type.getId(), type);
        }
    }

    public static ActionType getTypeById(String id) { return actionTypes.get(id); }

    public String getId() { return id; }

    public Class getLoaderClass() { return loaderClass; }
}
