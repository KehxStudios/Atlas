package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.actions.ActionData;
import com.kehxstudios.atlas.actions.ActionType;
import com.kehxstudios.atlas.actions.HighScoreResetAction;
import com.kehxstudios.atlas.actions.LaunchScreenAction;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.ComponentData;
import com.kehxstudios.atlas.components.ComponentType;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.data.Factory;
import com.kehxstudios.atlas.data.Templates;
import com.kehxstudios.atlas.data.TextureType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.entities.EntityData;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.UtilityTool;


/**
 * Created by ReidC on 2017-04-06.
 */

public class MainMenuScreen extends AScreen {

    public MainMenuScreen() {
        super();
        type = ScreenType.MAIN_MENU;
        screenGraphics.setTextureType(TextureType.MAINMENU_BACKGROUND);
    }

    public void finalize() {
        Entity flappyBird = Factory.createEntity(Templates.createEntityData(width/2, height/5*2));
        GraphicsComponent menuComponent = (GraphicsComponent)Factory.createComponent(
                flappyBird, Templates.createGraphicsComponentData(200, 100, 2, TextureType.MAINMENU_BORDERS));
        Factory.createComponent(flappyBird, Templates.createClickableComponentData(
                menuComponent.getWidth(), menuComponent.getHeight(), true, Templates.createLaunchScreenActionData(ScreenType.FLAPPY_BIRD)));
        Factory.createComponent(flappyBird, Templates.createFloatingTextComponentData("< ", "Flappy Bird >"));

        Entity intro = Factory.createEntity(Templates.createEntityData(width, height/5*3));
        Factory.createComponent(intro, Templates.createGraphicsComponentData(200, 100, 2, TextureType.MAINMENU_BORDERS));
        Factory.createComponent(intro, Templates.createClickableComponentData(200, 100, true, Templates.createLaunchScreenActionData(ScreenType.FLAPPY_BIRD)));
        Factory.createComponent(intro, Templates.createFloatingTextComponentData("< ", "Intro >"));

        Entity highScoreReset = Factory.createEntity(Templates.createEntityData(width, height/5*4));
        Factory.createComponent(highScoreReset, Templates.createGraphicsComponentData(200, 100, 2, TextureType.MAINMENU_BORDERS));
        Factory.createComponent(highScoreReset, Templates.createClickableComponentData(200, 100, true, Templates.createLaunchScreenActionData(ScreenType.FLAPPY_BIRD)));
        Factory.createComponent(highScoreReset, Templates.createFloatingTextComponentData("< ", "High-Score Reset >"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
