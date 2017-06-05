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

import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.data.GameSettings;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.type.ScreenType;
import com.kehxstudios.atlas.type.TextureType;

/**
 * Created by ReidC on 2017-05-30.
 */

public class SettingsScreen extends AScreen {

    private GameSettings gameSettings;

    public SettingsScreen() {
        super(ScreenType.SETTINGS);
        init();
    }

    protected void init() {
        super.init();

        Entity mainMenuLaunchEntity = buildManager.createEntity(50, height-50);
        buildManager.createClickableComponent(mainMenuLaunchEntity, 100, 100, true, false,
                buildManager.createLaunchScreenAction(ScreenType.MAIN_MENU));

        gameSettings = gm.gameSettings;

        float borderWidth = TextureType.MAIN_MENU_BORDER.getWidth();
        float borderHeight = TextureType.MAIN_MENU_BORDER.getHeight();

        Entity profileEntity = buildManager.createEntity(width/2, height/2);
        buildManager.createGraphicsComponent(profileEntity, 1, TextureType.MAIN_MENU_BORDER);
        buildManager.createFloatingTextComponent(profileEntity, false, true, gm.player.getName(), " - " +
                gm.player.getScore(), graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(profileEntity, borderWidth, borderHeight, true, false,
                new Action());
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

    @Override
    public void dispose() {
        if (!disposed) {
            super.dispose();
        }
    }
}
