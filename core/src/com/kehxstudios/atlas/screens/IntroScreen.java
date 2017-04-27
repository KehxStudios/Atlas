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
import com.kehxstudios.atlas.data.Templates;
import com.kehxstudios.atlas.data.TextureType;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.UtilityTool;

/**
 * Created by ReidC on 2017-04-06.
 */

public class IntroScreen extends AScreen {

    private boolean finalLogo;
    private boolean clickToContinue;

    private ComponentData floatingTextData;
    private ComponentData clickableData;

    public IntroScreen() {
        super(ScreenType.INTRO);

        screenGraphics.setTextureType(TextureType.DEV_LOGO);
        screenGraphics.setEnabled(true);
        finalLogo = false;
        clickToContinue = false;

        // FloatingTextData
        floatingTextData = Templates.createFloatingTextComponentData(": ", "Click to Continue :");

        // ClickableData
        clickableData = Templates.createClickableComponentData(width, height, true,
                Templates.createLaunchScreenActionData(ScreenType.INTRO));
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // increase screenTime in super method
        super.render(delta);
        if (!clickToContinue) {
            // If index is not on last path
            if (screenTime >= 2f) {
                if (!finalLogo) {
                    screenGraphics.setTextureType(TextureType.GAME_LOGO);
                    screenTime = 0f;
                    finalLogo = true;
                } else if (finalLogo) {
                    Factory.createComponent(screenEntity, clickableData);
                    Factory.createComponent(screenEntity, floatingTextData);
                    clickToContinue = true;
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        super.dispose();
    }
}
