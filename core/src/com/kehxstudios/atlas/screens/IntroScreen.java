package com.kehxstudios.atlas.screens;

import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.tools.Factory;
import com.kehxstudios.atlas.tools.Templates;
import com.kehxstudios.atlas.type.TextureType;

/**
 * Created by ReidC on 2017-04-06.
 */

public class IntroScreen extends AScreen {

    private boolean finalLogo;
    private boolean clickToContinue;

    private ComponentData floatingTextData;
    private ComponentData clickableData;

    public IntroScreen() {
        super(com.kehxstudios.atlas.type.ScreenType.INTRO);

        screenGraphics.setTextureType(TextureType.DEV_LOGO);
        screenGraphics.setEnabled(true);
        finalLogo = false;
        clickToContinue = false;

        // FloatingTextData
        floatingTextData = Templates.createFloatingTextComponentData(": ", "Click to Continue :");

        // ClickableData
        clickableData = Templates.createClickableComponentData(width, height, true,
                Templates.createLaunchScreenActionData(com.kehxstudios.atlas.type.ScreenType.MAIN_MENU));

        screenEntity.setPosition(getWidth()/2, getHeight()/2);
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
