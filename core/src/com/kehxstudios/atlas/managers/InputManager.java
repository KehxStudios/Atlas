package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
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

    public InputManager() {
        super();
        clickableComponents = new ArrayList<ClickableComponent>();
    }

    public void tick(float delta) {
        if (Gdx.input.justTouched() && clickableComponents.size() > 0) {
            checkClickable();
        }
    }

    private void checkClickable() {
        float x = screen.getWidth() - Gdx.input.getX() / screen.getScaleWidth();
        float y = screen.getHeight() - Gdx.input.getY() / screen.getScaleHeight();
        DebugTool.log("Checking Clickable", "x : "+x+" _ y: " + y);
        //Vector3 input = gm.getCamera().unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0));
        //checkClickable(input.x / screen.getScaleWidth(), input.y / screen.getScaleHeight());
        //x = x + gm.getCamera().position.x - gm.getCamera().viewportWidth/2;
        //y = y + gm.getCamera().position.y - gm.getCamera().viewportHeight/2;

        for (ClickableComponent clickable : clickableComponents) {
            if (clickable.isEnabled()) {
                if (x > clickable.getPosition().x - clickable.getWidth() / 2 &&
                        x < clickable.getPosition().x + clickable.getWidth() / 2 &&
                        y > clickable.getPosition().y - clickable.getHeight() / 2 &&
                        y < clickable.getPosition().y + clickable.getHeight() / 2) {
                    clickable.trigger();
                    DebugTool.log("Clickable Triggered");
                }
            }
        }
    }

    public void add(ClickableComponent component) {
        if (!clickableComponents.contains(component)) {
            clickableComponents.add(component);
        }
    }

    public void remove(ClickableComponent component) {
        if (clickableComponents.contains(component)) {
            clickableComponents.remove(component);
        }
    }

    @Override
    protected void loadScreenTypeSettings() {
        clickableComponents = new ArrayList<ClickableComponent>();
    }

    @Override
    protected void removeScreenTypeSettings() {

    }
}
