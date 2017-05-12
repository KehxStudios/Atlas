package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.ErrorTool;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-06.
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

    // ArrayList for all ClickableComponents created
    private ArrayList<ClickableComponent> clickableComponents;
    
    private Vector2 clickedPosition;

    // Constructor
    public InputManager() {
        super();
        setup();
    }
    
    // Initalizes the @clickableComponents
    @Override
    protected void setup() {
        clickableComponents = new ArrayList<ClickableComponent>();
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

            for (ClickableComponent clickable : clickableComponents) {
                if (clickable.isEnabled()) {
                    if (x > clickable.getPosition().x - clickable.getWidth() / 2 &&
                            x < clickable.getPosition().x + clickable.getWidth() / 2 &&
                            y > clickable.getPosition().y - clickable.getHeight() / 2 &&
                            y < clickable.getPosition().y + clickable.getHeight() / 2) {
                        clickable.trigger();
                    }
                }
            }
        } else {
            clickedPosition.set(0, 0);
        }
    }
    
    // Called when loading a new screen
    @Override
    protected void loadScreenSettings() {
        DebugTool.log("InputManager_loadScreenSettings: Complete");
    }
    
    // Called when unloading the current screen
    @Override
    protected void removeScreenSettings() {
        DebugTool.log("InputManager_removeScreenSettings: Complete");
    }
    
    // Called to add ClickableComponent to @clickableComponents
    public void add(ClickableComponent clickable) {
        if (!clickableComponents.contains(clickable)) {
            clickableComponents.add(clickable);
        } else {
            ErrorTool.log("Failed to add clickable to clickableComponents");
        }
    }

    // Called to remove ClickableComponent from @clickableComponents
    public void remove(ClickableComponent clickable) {
        if (clickableComponents.contains(clickable)) {
            clickableComponents.remove(clickable);
        } else {
            ErrorTool.log("Failed to remove clickable to clickableComponents");  
        }
    }

    public Vector2 getClickedPosition() { return clickedPosition; }
}
