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
import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.managers.BuildManager;
import com.kehxstudios.atlas.managers.GraphicsManager;
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

        Entity flappyBirdEntity = buildManager.createEntity(width/4, height/6*5);
        buildManager.createGraphicsComponent(flappyBirdEntity, 1, TextureType.MAIN_MENU_BORDER);
        buildManager.createFloatingTextComponent(flappyBirdEntity, true, true, "", "Flappy Bird", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(flappyBirdEntity, borderWidth, borderHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.FLAPPY_BIRD));

        Entity deviceInformationEntity = buildManager.createEntity(width/4*3, height/6*5);
        buildManager.createGraphicsComponent(deviceInformationEntity, 1, TextureType.MAIN_MENU_BORDER);
        buildManager.createFloatingTextComponent(deviceInformationEntity, true, true, "", "Device Information", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(deviceInformationEntity, borderWidth, borderHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.DEVICE_INFORMATION));

        Entity geneRocketsEntity = buildManager.createEntity(width/4, height/6*4);
        buildManager.createGraphicsComponent(geneRocketsEntity, 1, TextureType.MAIN_MENU_BORDER);
        buildManager.createFloatingTextComponent(geneRocketsEntity, true, true, "", "Gene Rockets", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(geneRocketsEntity, borderWidth, borderHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.GENE_ROCKETS));

        Entity pongEntity = buildManager.createEntity(width/4*3, height/6*4);
        buildManager.createGraphicsComponent(pongEntity, 1, TextureType.MAIN_MENU_BORDER);
        buildManager.createFloatingTextComponent(pongEntity, true, true, "", "Pong", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(pongEntity, borderWidth, borderHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.PONG));

        Entity settingsEntity = buildManager.createEntity(width/4, height/6*2);
        buildManager.createGraphicsComponent(settingsEntity, 1, TextureType.MAIN_MENU_BORDER);
        buildManager.createFloatingTextComponent(settingsEntity, true, true, "", "Settings", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(settingsEntity, borderWidth, borderHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.SETTINGS));

        Entity resetEntity = buildManager.createEntity(width/4*3, height/6*2);
        buildManager.createGraphicsComponent(resetEntity, 1, TextureType.MAIN_MENU_BORDER);
        buildManager.createFloatingTextComponent(resetEntity, true, true, "", "Reset", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(resetEntity, borderWidth, borderHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.INTRO));


        Entity websiteEntity = buildManager.createEntity(width/4, height/6);
        buildManager.createGraphicsComponent(websiteEntity, 1, TextureType.MAIN_MENU_BORDER);
        buildManager.createFloatingTextComponent(websiteEntity, true, true, "", "Website", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(websiteEntity, borderWidth, borderHeight, true, false,
                buildManager.createLaunchWebsiteAction("http://www.KehxStudios.com"));

        Entity gitHubEntity = buildManager.createEntity(width/4*3, height/6);
        buildManager.createGraphicsComponent(gitHubEntity, 1, TextureType.MAIN_MENU_BORDER);
        buildManager.createFloatingTextComponent(gitHubEntity, true, true, "", "GitHub", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(gitHubEntity, borderWidth, borderHeight, true, false,
                buildManager.createLaunchWebsiteAction("https://github.com/KehxStudios/Atlas"));

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        
        if (screenTime >= 60f) {
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
        if (!disposed) {
            super.dispose();
        }
    }
}
