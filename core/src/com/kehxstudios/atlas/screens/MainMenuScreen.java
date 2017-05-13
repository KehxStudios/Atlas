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
 * Created by ReidC on 2017-04-06.
 */

public class MainMenuScreen extends AScreen {

    public MainMenuScreen() {
        super(ScreenType.MAIN_MENU);
    }
    
    public void finalizeSetup() {
        super.finalizeSetup();
        screenGraphics.setTextureType(TextureType.MAIN_MENU_BACKGROUND);
        screenGraphics.setEnabled(true);

        ComponentData borderGraphics = Templates.graphicsComponentData(0, 0, 3, TextureType.MAIN_MENU_BORDER);
        
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
                TextureType.MAIN_MENU_BORDER.getHeight(), true, Templates.launchScreenActionData(ScreenType.INTRO)));
        
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
