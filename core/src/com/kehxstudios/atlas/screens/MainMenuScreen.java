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
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.data.Factory;
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
        DebugTool.log("Starting Main Menu");
        screenData = UtilityTool.load(ScreenType.MAIN_MENU);
        init();
    }

    public void init() {
        super.init();

        ((GraphicsComponent)screenEntity.getComponentOfType(ComponentType.GRAPHICS)).setTextureType(TextureType.MAINMENU_BACKGROUND);

        EntityData flappyBirdData = new EntityData();
        flappyBirdData.setX(screenEntity.getPosition().x);
        flappyBirdData.setY(screenEntity.getPosition().y);

        ComponentData flappyBirdGraphicsData = new ComponentData();
        flappyBirdGraphicsData.putString("textureType", TextureType.MAINMENU_BORDERS.getId());
        flappyBirdGraphicsData.putInt("layer", 2);
        flappyBirdData.putString("GraphicsComponent", UtilityTool.getStringFromDataClass(flappyBirdGraphicsData));

        ActionData actionData = new ActionData();
        actionData.setType(ActionType.LAUNCH_SCREEN.getId());
        actionData.putString("screenType", ScreenType.FLAPPY_BIRD.getId());

        ComponentData flappyBirdClickableData = new ComponentData();
        flappyBirdClickableData.putFloat("width", 400);
        flappyBirdClickableData.putFloat("height", 200);
        flappyBirdClickableData.putString("action", UtilityTool.getStringFromDataClass(actionData));

        Factory.getInstance().createEntity(flappyBirdData);
        /*
        introEntity = new Entity(screenEntity.getX(), screenEntity.getY());
        GraphicsComponent introGraphics = new GraphicsComponent(introEntity, TextureType.MAINMENU_BORDERS,2);
        ClickableComponent introClickable = new ClickableComponent(introEntity, introGraphics.getWidth(),
                introGraphics.getHeight(), true, new LaunchScreenAction(ScreenType.INTRO));
        introLayout = new GlyphLayout(font, introText);
        introLayout.setText(font, introText, Color.WHITE,WIDTH/2, Align.left, true);

        resetScoresEntity = new Entity(screenEntity.getX(), screenEntity.getY() + 500);
        GraphicsComponent resetScoreGraphics = new GraphicsComponent(resetScoresEntity, TextureType.MAINMENU_BORDERS,2);
        ClickableComponent ressetScoreClickable = new ClickableComponent(resetScoresEntity, resetScoreGraphics.getWidth(),
                resetScoreGraphics.getHeight(), true, new HighScoreResetAction(type));
        resetLayout = new GlyphLayout(font, resetText);
        resetLayout.setText(font, resetText, Color.WHITE,WIDTH/2, Align.left, true);
        */
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
