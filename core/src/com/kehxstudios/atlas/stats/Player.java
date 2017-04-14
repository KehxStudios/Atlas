package com.kehxstudios.atlas.stats;

import com.kehxstudios.atlas.tools.DebugTool;

/**
 * Created by ReidC on 2017-04-13.
 */

public class Player {

    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void changeName(String newName) {
        
        name = newName;
        DebugTool.log("Players name changed to " + name);
    }
}
