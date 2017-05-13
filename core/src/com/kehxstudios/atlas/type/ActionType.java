package com.kehxstudios.atlas.type;

import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.actions.DestroyEntityAction;
import com.kehxstudios.atlas.actions.HighScoreResetAction;
import com.kehxstudios.atlas.actions.FollowAction;
import com.kehxstudios.atlas.actions.LaunchScreenAction;
import com.kehxstudios.atlas.actions.MultiAction;
import com.kehxstudios.atlas.actions.PhysicsAction;
import com.kehxstudios.atlas.actions.RepositionAction;
import com.kehxstudios.atlas.actions.ResetScreenAction;
import com.kehxstudios.atlas.actions.ScoreAction;
import com.kehxstudios.atlas.actions.SpawnEntityAction;

import java.util.HashMap;

/**
 * Created by ReidC on 2017-04-16.
 */

public enum ActionType {

    DESTROY_ENTITY("Destroy Entity", DestroyEntityAction.class),
    HIGH_SCORE_RESET("High-Score Reset", HighScoreResetAction.class),
    FOLLOW("Follow", FollowAction.class),
    LAUNCH_SCREEN("Launch Screen", LaunchScreenAction.class),
    MULTI("Multi", MultiAction.class), // TO BE USED
    PHYSICS("Physics", PhysicsAction.class),
    REPOSITION("Reposition", RepositionAction.class),
    RESET_SCREEN("Reset Screen", ResetScreenAction.class),
    SCORE("Score", ScoreAction.class),
    SPAWN_ENTITY("Spawn Entity", SpawnEntityAction.class),
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
