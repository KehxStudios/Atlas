package com.kehxstudios.atlas.screens;

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
        screenGraphics.setTextureType(TextureType.MAIN_MENU_BACKGROUND);
        screenGraphics.setEnabled(true);

        setup();
    }
    
    private void setup() {
        ComponentData borderGraphics = Templates.createGraphicsComponentData(0, 0, 3, TextureType.MAIN_MENU_BORDER);
        ComponentData clickableData = Templates.createClickableComponentData(TextureType.MAIN_MENU_BORDER.getWidth(), 
                TextureType.MAIN_MENU_BORDER.getHeight(), true, Templates.createLaunchScreenActionData(ScreenType.FLAPPY_BAT)));
        
        Entity flappyBirdEntity = Factory.createEntity(Templates.createEntityData(width/2, height/5*4));
        Factory.createComponent(flappyBirdEntity, borderGraphics);
        Factory.createComponent(flappyBirdEntity, Templates.createFloatingTextComponentData("", "Flappy Bird", 4));
        ClickableComponent flappyBirdClickable = (ClickableComponent)Factory.createComponent(flappyBirdEntity, clickableData);
        flappyBirdClickable.getAction.setScreenType();
        
        Entity pongEntity = Factory.createEntity(Templates.createEntityData(width/2, height/5*3));
        Factory.createComponent(pongEntity, borderGraphics);
        Factory.createComponent(pongEntity, Templates.createFloatingTextComponentData("", "Pong", 4));
        ClickableComponent pongClickable = (ClickableComponent)Factory.createComponent(pongEntity, clickableData);
        pongClickable.getAction.setScreenType(ScreenType.PONG);

        Entity geneRocketsEntity = Factory.createEntity(Templates.createEntityData(width/2, height/5*2));
        Factory.createComponent(geneRocketsEntity, borderGraphics);
        Factory.createComponent(geneRocketsEntity, Templates.createFloatingTextComponentData("", "Gene Rockets", 4));
        ClickableComponent geneRocketsClickable = (ClickableComponent)Factory.createComponent(pongEntity, clickableData);
        geneRocketsClickable.getAction.setScreenType(ScreenType.GENE_ROCKETS);
        
        Entity highScoreResetEntity = Factory.createEntity(Templates.createEntityData(width/2, height/5));
        Factory.createComponent(highScoreResetEntity, borderGraphics);
        Factory.createComponent(highScoreResetEntity, Templates.createClickableComponentData(
                menuComponent.getWidth(), menuComponent.getHeight(), false, Templates.createHighScoreResetActionData(ScreenType.FLAPPY_BAT)));
        Factory.createComponent(highScoreResetEntity, Templates.createFloatingTextComponentData("", "High-Score Reset", 4));
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
