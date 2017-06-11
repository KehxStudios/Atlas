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

        buildManager.createGraphicsComponent(screenEntity, 0, TextureType.MAIN_MENU_BACKGROUND);

        float buttonWidth = TextureType.MAIN_MENU_BUTTON.getWidth();
        float buttonHeight = TextureType.MAIN_MENU_BUTTON.getHeight();

        Entity flappyBirdEntity = buildManager.createEntity(width/2, height/10*8);
        buildManager.createGraphicsComponent(flappyBirdEntity, 1, TextureType.MAIN_MENU_BUTTON);
        buildManager.createFloatingTextComponent(flappyBirdEntity, true, true, "", "Flappy Bird", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(flappyBirdEntity, buttonWidth, buttonHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.FLAPPY_BIRD));

        Entity geneRocketsEntity = buildManager.createEntity(width/2, height/10*7);
        buildManager.createGraphicsComponent(geneRocketsEntity, 1, TextureType.MAIN_MENU_BUTTON);
        buildManager.createFloatingTextComponent(geneRocketsEntity, true, true, "", "Gene Rockets", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(geneRocketsEntity, buttonWidth, buttonHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.GENE_ROCKETS));

        Entity pongEntity = buildManager.createEntity(width/2, height/10*6);
        buildManager.createGraphicsComponent(pongEntity, 1, TextureType.MAIN_MENU_BUTTON);
        buildManager.createFloatingTextComponent(pongEntity, true, true, "", "Pong", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(pongEntity, buttonWidth, buttonHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.PONG));

        Entity deviceInformationEntity = buildManager.createEntity(width/2, height/10*5);
        buildManager.createGraphicsComponent(deviceInformationEntity, 1, TextureType.MAIN_MENU_BUTTON);
        buildManager.createFloatingTextComponent(deviceInformationEntity, true, true, "", "Device Information", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(deviceInformationEntity, buttonWidth, buttonHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.DEVICE_INFORMATION));

        Entity settingsEntity = buildManager.createEntity(width/2, height/10*4);
        buildManager.createGraphicsComponent(settingsEntity, 1, TextureType.MAIN_MENU_BUTTON);
        buildManager.createFloatingTextComponent(settingsEntity, true, true, "", "Settings", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(settingsEntity, buttonWidth, buttonHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.SETTINGS));

        Entity resetEntity = buildManager.createEntity(width/2, height/10*3);
        buildManager.createGraphicsComponent(resetEntity, 1, TextureType.MAIN_MENU_BUTTON);
        buildManager.createFloatingTextComponent(resetEntity, true, true, "", "Reset", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(resetEntity, buttonWidth, buttonHeight, true, false,
                buildManager.createLaunchScreenAction(ScreenType.INTRO));


        Entity websiteEntity = buildManager.createEntity(width/2, height/10*2);
        buildManager.createGraphicsComponent(websiteEntity, 1, TextureType.MAIN_MENU_BUTTON);
        buildManager.createFloatingTextComponent(websiteEntity, true, true, "", "Website", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(websiteEntity, buttonWidth, buttonHeight, true, false,
                buildManager.createLaunchWebsiteAction("http://www.KehxStudios.com"));

        Entity gitHubEntity = buildManager.createEntity(width/2, height/10);
        buildManager.createGraphicsComponent(gitHubEntity, 1, TextureType.MAIN_MENU_BUTTON);
        buildManager.createFloatingTextComponent(gitHubEntity, true, true, "", "GitHub", graphicsManager.COLOR_BLUE);
        buildManager.createClickableComponent(gitHubEntity, buttonWidth, buttonHeight, true, false,
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
