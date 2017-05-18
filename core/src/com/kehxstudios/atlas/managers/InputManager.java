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

package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.ErrorTool;
import com.kehxstudios.atlas.type.ComponentType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Used to control all user input
 */

public class InputManager extends Manager {

    // Holds instance of class, create new if not set
    private static InputManager instance;
    public static InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }

    // HashMap for all ClickableComponents created
    private HashMap<Integer, ClickableComponent> clickableComponents;
    
    private Vector2 clickedPosition;

    // Constructor
    public InputManager() {
        super();
        init();
    }
    
    // Initalizes the @clickableComponents
    @Override
    protected void init() {
        clickableComponents = new HashMap<Integer,ClickableComponent>();
        clickedPosition = new Vector2(0,0);
        DebugTool.log("InputManager_setup: Complete");
    }

    // Called to check if any clickableComponents were touched
    @Override
    public void tick(float delta) {
        if (Gdx.input.justTouched() && clickableComponents.size() > 0) {
            float x = screen.getWidth() - Gdx.input.getX() / screen.getScaleWidth() + screen.getCamera().position.x - 
                screen.getCamera().viewportWidth/2;
            float y = screen.getHeight() - Gdx.input.getY() / screen.getScaleHeight() + screen.getCamera().position.y - 
                screen.getCamera().viewportHeight/2;
            clickedPosition.set(x, y);

            for (ClickableComponent clickable : clickableComponents.values()) {
                if (clickable.enabled) {
                    if (x > clickable.bounds.x && x < clickable.bounds.x + clickable.bounds.width &&
                            y > clickable.bounds.y && y < clickable.bounds.y + clickable.bounds.height) {
                        clickable.action.trigger();
                    }
                }
            }
        } else {
            clickedPosition.set(0, 0);
        }
    }

    // Called when loading a new screen
    @Override
    protected void loadSettings() {
        DebugTool.log("InputManager_loadSettings: Complete");
    }
    
    // Called when unloading the current screen
    @Override
    protected void removeSettings() {
        DebugTool.log("InputManager_removeSettings: Complete");
    }

    @Override
    public void add(Component component) {
        if (component.type == ComponentType.CLICKABLE) {
            ClickableComponent clickable = (ClickableComponent)component;
            if (!clickableComponents.containsKey(clickable.id)) {
                clickableComponents.put(clickable.id, clickable);
            } else {
                ErrorTool.log("Failed to add clickable to clickableComponents");
            }
        }
    }

    @Override
    public void remove(Component component) {
        if (component.type == ComponentType.CLICKABLE) {
            ClickableComponent clickable = (ClickableComponent)component;
            if (clickableComponents.containsKey(clickable.id)) {
                clickableComponents.values().remove(clickable);
            } else {
                ErrorTool.log("Failed to add clickable to clickableComponents");
            }
        }
    }

    public Vector2 getClickedPosition() { return clickedPosition; }
}
