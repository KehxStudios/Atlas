package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Gdx;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.tools.Factory;
import com.kehxstudios.atlas.tools.Templates;
import com.kehxstudios.atlas.type.TextureType;
import com.kehxstudios.atlas.type.ScreenType;

/**
 * Created by ReidC on 2017-04-06.
 */

public class IntroScreen extends AScreen {

    private boolean finalLogo;
    private boolean clickToContinue;

    public IntroScreen() {
        super(ScreenType.INTRO);
    }

    public void finalizeSetup() {
        super.finalizeSetup();
        screenGraphics.setTextureType(TextureType.INTRO_DEV_LOGO);
        screenGraphics.setEnabled(true);
        finalLogo = false;
        clickToContinue = false;
    }

    @Override
    public void render(float delta) {
        // increase screenTime in super method
        super.render(delta);
        if (!clickToContinue) {
            // If index is not on last path
            if (screenTime >= 2f || screenTime > 1f && Gdx.input.isTouched()) {
                if (!finalLogo) {
                    screenGraphics.setTextureType(TextureType.INTRO_GAME_LOGO);
                    screenTime = 0f;
                    finalLogo = true;
                } else if (finalLogo) {
                    createFinalComponents();
                    clickToContinue = true;
                }
            }
        }
    }
    
    private void createFinalComponents() {
        // FloatingText
        ComponentData floatingTextData = Templates.floatingTextComponentData("", "Click to Continue", 3);
        floatingTextData.setUseComponentPosition(true);
        floatingTextData.setX(width/2);
        floatingTextData.setY(height/5);
        Factory.createComponent(screenEntity, floatingTextData);
        
        // Clickable
        ComponentData clickableData = Templates.clickableComponentData(width, height, true,
                Templates.launchScreenActionData(ScreenType.MAIN_MENU));
        Factory.createComponent(screenEntity, clickableData);
    }


    @Override
    public void show() {
        super.show();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }
}
