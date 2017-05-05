package com.kehxstudios.atlas.screens;

import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.components.GraphicsComponent;
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

        ComponentData borderGraphics = Templates.createGraphicsComponentData(0, 0, 3, TextureType.MAIN_MENU_BORDER);

        Entity flappyBird = Factory.createEntity(Templates.createEntityData(width/2, height/5*2));
        GraphicsComponent menuComponent = (GraphicsComponent)Factory.createComponent(flappyBird, borderGraphics);
        Factory.createComponent(flappyBird, Templates.createClickableComponentData(
                width*2, height*2, true, Templates.createLaunchScreenActionData(ScreenType.FLAPPY_BAT)));
        Factory.createComponent(flappyBird, Templates.createFloatingTextComponentData("< ", "Flappy Bird >", 3));

        Entity intro = Factory.createEntity(Templates.createEntityData(width, height/5*3));
        Factory.createComponent(intro, borderGraphics);
        Factory.createComponent(intro, Templates.createClickableComponentData(
                menuComponent.getWidth(), menuComponent.getHeight(), true, Templates.createLaunchScreenActionData(ScreenType.INTRO)));
        Factory.createComponent(intro, Templates.createFloatingTextComponentData("< ", "Intro >", 3));

        Entity highScoreReset = Factory.createEntity(Templates.createEntityData(width, height/5*4));
        Factory.createComponent(highScoreReset, borderGraphics);
        Factory.createComponent(highScoreReset, Templates.createClickableComponentData(
                menuComponent.getWidth(), menuComponent.getHeight(), false, Templates.createHighScoreResetActionData(ScreenType.FLAPPY_BAT)));
        Factory.createComponent(highScoreReset, Templates.createFloatingTextComponentData("< ", "High-Score Reset >", 3));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
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
