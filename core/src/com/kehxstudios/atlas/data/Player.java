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

package com.kehxstudios.atlas.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.ErrorTool;

/**
 * Holds the players data through the entire app, will be set at main menu in the future
 */

public class Player {

    private static String preferenceName = "Players";
    private static Preferences preferences;

    // Name of the player
    private String name;
    private int score;

    public Player(){
        preferences = Gdx.app.getPreferences(preferenceName);
        changePlayer(preferences.getString("lastPlayer", "Default"));
    }

    // Returns name of player
    public String getName() {
        return name;
    }

    // Changes the name of the player
    public void changeName(String newName) {
        if (name == newName)
            return;
        DebugTool.log(name + " changed names to " + newName);
        String currentPlayers = preferences.getString("allPlayers", "");
        if (currentPlayers.contains(newName)) {
            ErrorTool.log("Change Name, name already used");
        } else {
            if (currentPlayers.contains(name)) {
                currentPlayers.replace(name, newName);
                preferences.putString("allPlayers", currentPlayers);
                removePlayer(name);
                name = newName;
                savePlayer();
            } else {
                ErrorTool.log("Change Name, cannot find previous name, creating new Player");
                newPlayer(newName);
            }
        }
    }

    public void changePlayer(String player) {
        if (player != null && player != "") {
            if (savedPlayer(player)) {
                loadPlayer(player);
            } else {
                newPlayer(player);
            }
            preferences.putString("lastPlayer", name);
            preferences.flush();
        } else {
            ErrorTool.log("Changing Player, invalid name");
        }
    }

    private void newPlayer(String player) {
        addToAllPlayers(player);
        loadPlayer(player);
        savePlayer();
    }

    private void loadPlayer(String player) {
        name = player;
        score = preferences.getInteger(player + "_score", 0);
    }

    public void savePlayer() {
        preferences.putInteger(name + "_score", score);
        preferences.flush();
    }

    private void removePlayer(String player) {
        preferences.remove(player + "_score");
        preferences.flush();
    }

    public String[] getAllPlayers() {
        return preferences.getString("allPlayers", "").split(",");
    }
    private void addToAllPlayers(String player) {
        String currentPlayers = preferences.getString("allPlayers", "");
        if (currentPlayers != "") {
            currentPlayers += ",";
        }
        currentPlayers += player;
        preferences.putString("allPlayers", currentPlayers);
        preferences.flush();
    }
    private boolean savedPlayer(String player) {
        for (String savedPlayers : getAllPlayers()) {
            if (savedPlayers == player) {
                return true;
            }
        }
        return false;
    }

    public int getScore() { return score; }
    public void addScore(int value) {
        score += value;
        savePlayer();
    }
    public void setScore(int score) {
        this.score = score;
        savePlayer();
    }
}
