package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.kehxstudios.atlas.components.ButtonComponent;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.tools.DebugTool;

import java.util.ArrayList;

/**
 * Created by ReidC on 2017-04-06.
 */

public class InputManager extends Manager {

    private static InputManager instance;
    public static InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }

    private ArrayList<ClickableComponent> clickableComponents;
    private ArrayList<ButtonComponent> buttonComponents;

    public InputManager() {
        clickableComponents = new ArrayList<ClickableComponent>();
        buttonComponents = new ArrayList<ButtonComponent>();
    }

    public void tick(float delta) {
        if (screen == null) {
            return;
        }
        if (Gdx.input.justTouched() && clickableComponents.size() != 0) {
            DebugTool.log("Checking Input", Gdx.input.getX()+"_" + Gdx.input.getY());
            checkClickable(Gdx.input.getX(), Gdx.input.getY());
        }
    }

    private void checkClickable(float x, float y) {
        DebugTool.log("Checking Clickable", x+"_" + y);
        x = x + gm.getCamera().position.x - gm.getCamera().viewportWidth/2;
        y = y + gm.getCamera().position.y - gm.getCamera().viewportHeight/2;

        for (int i = 0; i < clickableComponents.size(); i++) {
            if (!clickableComponents.get(i).isEnabled()) {
                continue;
            }
            if (x > clickableComponents.get(i).getX() - clickableComponents.get(i).getWidth()/2 &&
                    x < clickableComponents.get(i).getX() + clickableComponents.get(i).getWidth()/2 &&
                    y > clickableComponents.get(i).getY() - clickableComponents.get(i).getHeight()/2 &&
                    y < clickableComponents.get(i).getY() + clickableComponents.get(i).getHeight()/2) {
                clickableComponents.get(i).trigger();
                clickableComponents.remove(i);
                i--;
            }
        }

    }

    public void addClickable(ClickableComponent component) {
        if (!clickableComponents.contains(component)) {
            clickableComponents.add(component);
        }
    }

    public void removeClickable(ClickableComponent component) {
        if (clickableComponents.contains(component)) {
            clickableComponents.remove(component);
        }
    }

    public void addButton(ButtonComponent component) {
        if (!buttonComponents.contains(component)) {
            buttonComponents.add(component);
        }
    }

    public void removeButton(ButtonComponent component) {
        if (buttonComponents.contains(component)) {
            buttonComponents.remove(component);
        }
    }

    @Override
    protected void loadScreenTypeSettings() {

    }

    @Override
    protected void removeScreenTypeSettings() {

    }
}
