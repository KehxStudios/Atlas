package com.kehxstudios.atlas.actions;

import java.util.HashMap;

/**
 * Created by ReidC on 2017-04-16.
 */

public enum ActionType {

    DESTROY_ENTITY("Destroy Entity", DestroyEntityAction.class),
    HIGH_SCORE_RESET("High-Score Reset", HighScoreResetAction.class),
    LAUNCH_SCREEN("Launch Screen", LaunchScreenAction.class),
    MULTI("Multi", MultiAction.class),
    PHYSICS("Physics", PhysicsAction.class),
    REPOSITION("Reposition", RepositionAction.class),
    SCORE("Score", ScoreAction.class),
    SPAWN_ENTITY("Spawn Entity", SpawnEntityAction.class),
    TELEPORT("Teleport", TeleportAction.class);

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

    public static ActionType getType(String id) { return actionTypes.get(id); }

    public String getId() { return id; }

    public Class getLoaderClass() { return loaderClass; }
}
