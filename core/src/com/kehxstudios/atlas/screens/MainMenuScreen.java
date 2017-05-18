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

import com.kehxstudios.atlas.actions.LaunchScreenAction;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.managers.ScreenManager;
import com.kehxstudios.atlas.tools.Factory;
import com.kehxstudios.atlas.tools.Templates;
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
        screenGraphics.textureType = TextureType.MAIN_MENU_BACKGROUND;
        screenGraphics.enabled = true;

        ComponentData borderGraphics = Templates.graphicsComponentData(0, 0, 3, 0, TextureType.MAIN_MENU_BORDER);
        
        Entity flappyBirdEntity = Factory.createEntity(Templates.createEntityData(width/2, height/5*4));
        Factory.createComponent(flappyBirdEntity, borderGraphics);
        Factory.createComponent(flappyBirdEntity, Templates.floatingTextComponentData("", "Flappy Bird", 4));
        Factory.createComponent(flappyBirdEntity, Templates.clickableComponentData(TextureType.MAIN_MENU_BORDER.getWidth(),
                TextureType.MAIN_MENU_BORDER.getHeight(), true, Templates.launchScreenActionData(ScreenType.FLAPPY_BAT)));
        
        Entity pongEntity = Factory.createEntity(Templates.createEntityData(width/2, height/5*3));
        Factory.createComponent(pongEntity, borderGraphics);
        Factory.createComponent(pongEntity, Templates.floatingTextComponentData("", "Pong", 4));
        Factory.createComponent(pongEntity, Templates.clickableComponentData(TextureType.MAIN_MENU_BORDER.getWidth(),
                TextureType.MAIN_MENU_BORDER.getHeight(), true, Templates.launchScreenActionData(ScreenType.INTRO)));

        Entity geneRocketsEntity = Factory.createEntity(Templates.createEntityData(width/2, height/5*2));
        Factory.createComponent(geneRocketsEntity, borderGraphics);
        Factory.createComponent(geneRocketsEntity, Templates.floatingTextComponentData("", "Gene Rockets", 4));
        Factory.createComponent(geneRocketsEntity, Templates.clickableComponentData(TextureType.MAIN_MENU_BORDER.getWidth(),
                TextureType.MAIN_MENU_BORDER.getHeight(), true, Templates.launchScreenActionData(ScreenType.GENE_ROCKETS)));
        
        Entity highScoreResetEntity = Factory.createEntity(Templates.createEntityData(width/2, height/5));
        Factory.createComponent(highScoreResetEntity, borderGraphics);
        Factory.createComponent(highScoreResetEntity, Templates.clickableComponentData(
                TextureType.MAIN_MENU_BORDER.getWidth(), TextureType.MAIN_MENU_BORDER.getHeight(),
                false, Templates.highScoreResetActionData(ScreenType.FLAPPY_BAT)));
        Factory.createComponent(highScoreResetEntity, Templates.floatingTextComponentData("", "High-Score Reset", 4));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        
        if (screenTime >= 10f) {
            ScreenManager.getInstance().requestNewScreen(ScreenType.INTRO);
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
