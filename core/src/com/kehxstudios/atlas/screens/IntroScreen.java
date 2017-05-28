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

package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.BuildManager;
import com.kehxstudios.atlas.type.TextureType;
import com.kehxstudios.atlas.type.ScreenType;

/**
 * The Startup screen for the Intro
 */

public class IntroScreen extends AScreen {

    private boolean finalLogo;
    private boolean clickToContinue;

    private Entity textEntity;
    private GraphicsComponent devLogoGraphics;

    public IntroScreen() {
        super(ScreenType.INTRO);
        init();
    }

    protected void init() {
        super.init();
        finalLogo = false;
        clickToContinue = false;

        devLogoGraphics = buildManager.createGraphicsComponent(screenEntity, 1, TextureType.INTRO_DEV_LOGO);
        textEntity = buildManager.createEntity(width/2, height/5);
    }

    @Override
    public void render(float delta) {
        // increase screenTime in super method
        super.render(delta);
        if (!clickToContinue) {
            // If index is not on last path
            if (!finalLogo && (screenTime >= 2f || screenTime > 1f && Gdx.input.isTouched())) {
                entityManager.remove(devLogoGraphics);
                devLogoGraphics = null;
                buildManager.createGraphicsComponent(screenEntity, 1, TextureType.INTRO_GAME_LOGO);
                finalLogo = true;
            } else if (finalLogo && screenTime > 4f || screenTime > 2f && Gdx.input.isTouched()) {
                buildManager.createFloatingTextComponent(textEntity, 2, "", "Click to Continue", Color.BLUE);
                buildManager.createClickableComponent(screenEntity, width, height, true, false,
                        buildManager.createLaunchScreenAction(ScreenType.MAIN_MENU));
                clickToContinue = true;
            }
        }
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
