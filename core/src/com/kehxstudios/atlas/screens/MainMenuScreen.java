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

import com.badlogic.gdx.graphics.Color;
import com.kehxstudios.atlas.managers.BuildManager;
import com.kehxstudios.atlas.type.TextureType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.type.ScreenType;


/**
 * The main menu to select the Game or Project
 */

public class MainMenuScreen extends AScreen {

    public MainMenuScreen() {
        super(ScreenType.MAIN_MENU);
        init();
    }
    
    protected void init() {
        super.init();

        float borderWidth = TextureType.MAIN_MENU_BORDER.getWidth();
        float borderHeight = TextureType.MAIN_MENU_BORDER.getHeight();

        buildManager.createGraphicsComponent(screenEntity, 0, TextureType.MAIN_MENU_BACKGROUND);

        Entity flappyBirdEntity = buildManager.createEntity(width/2, height/6*5);
        buildManager.createGraphicsComponent(flappyBirdEntity, 1, TextureType.MAIN_MENU_BORDER);
        buildManager.createFloatingTextComponent(flappyBirdEntity, 3, "", "Flappy Bird", Color.BLACK);
        buildManager.createClickableComponent(flappyBirdEntity, borderWidth, borderHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.FLAPPY_BAT));

        Entity pongEntity = buildManager.createEntity(width/2, height/6*4);
        buildManager.createGraphicsComponent(pongEntity, 1, TextureType.MAIN_MENU_BORDER);
        buildManager.createFloatingTextComponent(pongEntity, 3, "", "Pong", Color.BLACK);
        buildManager.createClickableComponent(pongEntity, borderWidth, borderHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.PONG));

        Entity geneRocketsEntity = buildManager.createEntity(width/2, height/6*3);
        buildManager.createGraphicsComponent(geneRocketsEntity, 1, TextureType.MAIN_MENU_BORDER);
        buildManager.createFloatingTextComponent(geneRocketsEntity, 3, "", "Gene Rockets", Color.BLACK);
        buildManager.createClickableComponent(geneRocketsEntity, borderWidth, borderHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.GENE_ROCKETS));

        Entity phoneInformationEntity = buildManager.createEntity(width/2, height/6*2);
        buildManager.createGraphicsComponent(phoneInformationEntity, 1, TextureType.MAIN_MENU_BORDER);
        buildManager.createFloatingTextComponent(phoneInformationEntity, 3, "", "Phone Information", Color.BLACK);
        buildManager.createClickableComponent(phoneInformationEntity, borderWidth, borderHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.PHONE_INFORMATION));

        Entity highScoreResetEntity = buildManager.createEntity(width/2, height/6);
        buildManager.createGraphicsComponent(highScoreResetEntity, 1, TextureType.MAIN_MENU_BORDER);
        buildManager.createFloatingTextComponent(highScoreResetEntity, 3, "", "Reset", Color.BLACK);
        buildManager.createClickableComponent(highScoreResetEntity, borderWidth, borderHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.INTRO));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        
        if (screenTime >= 180f) {
            screenManager.requestNewScreen(ScreenType.INTRO);
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

    @Override
    public void dispose() {
        super.dispose();
    }
}
