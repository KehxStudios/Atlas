package com.kehxstudios.atlas.data;

import com.kehxstudios.atlas.tools.DebugTool;

/**
 * Holds the players data through the entire app, will be set at main menu in the future
 */

public class Player {

    // Name of the player
    private String name;

    // Constructor only requires a string name
    public Player(String name) {
        this.name = name;
    }

    // Returns name of player
    public String getName() {
        return name;
    }

    // Changes the name of the player
    public void changeName(String newName) {
        name = newName;
        DebugTool.log("Players name changed to " + name);
    }
}
