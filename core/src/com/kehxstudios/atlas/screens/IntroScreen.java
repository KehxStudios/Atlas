package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.actions.ActionData;
import com.kehxstudios.atlas.actions.ActionType;
import com.kehxstudios.atlas.actions.LaunchScreenAction;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.ComponentData;
import com.kehxstudios.atlas.components.ComponentType;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.data.Factory;
import com.kehxstudios.atlas.data.TextureType;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.UtilityTool;

/**
 * Created by ReidC on 2017-04-06.
 */

public class IntroScreen extends AScreen {

    boolean finalLogo;
    boolean clickToContinue;

    ComponentData floatingTextData;
    ComponentData clickableData;

    public IntroScreen() {
        super();
        screenData = UtilityTool.load(ScreenType.INTRO);
        init();
    }

    protected void init() {
        super.init();
        ((GraphicsComponent)screenEntity.getComponentOfType(ComponentType.GRAPHICS)).setTextureType(TextureType.DEV_LOGO);

        clickToContinue = false;
        finalLogo = false;

        // ClickableData
        clickableData = new ComponentData();
        clickableData.putFloat("width", WIDTH);
        clickableData.putFloat("height", HEIGHT);
        clickableData.putBoolean("singleTrigger", true);
        ActionData actionData = new ActionData();
        actionData.type = ActionType.LAUNCH_SCREEN.getId();
        actionData.putString("screenType", ScreenType.MAIN_MENU.getId());
        clickableData.putString("action", UtilityTool.getStringFromDataClass(actionData));

        // FloatingTextData
        floatingTextData = new ComponentData();
        floatingTextData.putString("label", "");
        floatingTextData.putString("text", "Click to Continue");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // increase screenTime in super method
        super.render(delta);
        if (!clickToContinue) {
            // If index is not on last path
            if (screenTime >= 2f) {
                if (!finalLogo) {
                    ((GraphicsComponent)screenEntity.getComponentOfType(ComponentType.GRAPHICS)).setTextureType(TextureType.GAME_LOGO);
                    screenTime = 0f;
                    finalLogo = true;
                } else if (finalLogo) {
                    Factory.getInstance().createComponent(screenEntity, clickableData);
                    Factory.getInstance().createComponent(screenEntity, floatingTextData);
                    clickToContinue = true;
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
