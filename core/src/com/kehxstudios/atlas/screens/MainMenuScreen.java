package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.actions.HighScoreResetAction;
import com.kehxstudios.atlas.actions.LaunchScreenAction;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.data.SpriteType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.tools.DataTool;


/**
 * Created by ReidC on 2017-04-06.
 */

public class MainMenuScreen extends AScreen {

    private BitmapFont font;

    private Entity flappyBirdEntity;
    private GlyphLayout flappyBirdLayout;
    private String flappyBirdText = "Flappy Bird";

    private Entity introEntity;
    private GlyphLayout introLayout;
    private String introText = "Intro Restart";

    private Entity resetScoresEntity;
    private GlyphLayout resetLayout;
    private String resetText = "Reset Scores";

    public MainMenuScreen() {
        super();
        screenData = DataTool.load(ScreenType.MAIN_MENU);
        init();
    }

    public void init() {
        super.init();

        font = new BitmapFont();
        font.getData().setScale(5f);

        flappyBirdEntity = new Entity(screenEntity.getX(), screenEntity.getY() - 500);
        GraphicsComponent flappyBirdGraphics = new GraphicsComponent(flappyBirdEntity, SpriteType.MAINMENU_BORDERS,2);
        ClickableComponent flappyBirdClickable = new ClickableComponent(flappyBirdEntity, flappyBirdGraphics.getWidth(),
                flappyBirdGraphics.getHeight(), new LaunchScreenAction(ScreenType.FLAPPY_BIRD));
        flappyBirdLayout = new GlyphLayout(font, flappyBirdText);
        flappyBirdLayout.setText(font, flappyBirdText, Color.WHITE,WIDTH/2, Align.left, true);

        introEntity = new Entity(screenEntity.getX(), screenEntity.getY());
        GraphicsComponent introGraphics = new GraphicsComponent(introEntity, SpriteType.MAINMENU_BORDERS,2);
        ClickableComponent introClickable = new ClickableComponent(introEntity, introGraphics.getWidth(),
                introGraphics.getHeight(), new LaunchScreenAction(ScreenType.INTRO));
        introLayout = new GlyphLayout(font, introText);
        introLayout.setText(font, introText, Color.WHITE,WIDTH/2, Align.left, true);

        resetScoresEntity = new Entity(screenEntity.getX(), screenEntity.getY() + 500);
        GraphicsComponent resetScoreGraphics = new GraphicsComponent(resetScoresEntity, SpriteType.MAINMENU_BORDERS,2);
        ClickableComponent ressetScoreClickable = new ClickableComponent(resetScoresEntity, resetScoreGraphics.getWidth(),
                resetScoreGraphics.getHeight(), new HighScoreResetAction(type));
        resetLayout = new GlyphLayout(font, resetText);
        resetLayout.setText(font, resetText, Color.WHITE,WIDTH/2, Align.left, true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        gm.getBatch().begin();
        font.draw(gm.getBatch(), flappyBirdLayout, resetScoresEntity.getX() - flappyBirdLayout.width/2,
                resetScoresEntity.getY() + flappyBirdLayout.height/2);
        font.draw(gm.getBatch(), introLayout, introEntity.getX() - introLayout.width/2,
                introEntity.getY() + introLayout.height/2);
        font.draw(gm.getBatch(), resetLayout, flappyBirdEntity.getX() - resetLayout.width/2,
                flappyBirdEntity.getY() + resetLayout.height/2);
        gm.getBatch().end();
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
        flappyBirdEntity.destroy();
        introEntity.destroy();
        resetScoresEntity.destroy();
        font.dispose();
        super.dispose();
    }
}
