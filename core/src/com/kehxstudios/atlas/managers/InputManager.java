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
            DebugTool.log("Attempt to check clickables");
            DebugTool.log(screen.getType()+"");

            ClickableComponent component = checkClickable(Gdx.input.getX()/screen.getScaleWidth(), Gdx.input.getY()/screen.getScaleHeight());

            DebugTool.log("Clickables checked");
            if (component != null) {
                DebugTool.log("location trigger");
                component.trigger();
            }
        }
        for (ButtonComponent button : buttonComponents) {
            if (Gdx.input.isKeyJustPressed(button.getKey())) {
                DebugTool.log("button trigger");
                button.trigger();
            }
        }
    }

    private ClickableComponent checkClickable(float x, float y) {
        DebugTool.log(x+"", y+"");
        for (ClickableComponent component : clickableComponents) {
            if (x > component.getX() - component.getWidth()/2 && x < component.getX() + component.getWidth()/2 &&
                    y > component.getY() - component.getHeight()/2 && y < component.getY() + component.getHeight()/2) {
                return component;
            }
        }
        return null;
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
