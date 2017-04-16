package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.components.ButtonComponent;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.HighScoreResetComponent;
import com.kehxstudios.atlas.components.ScreenLaunchComponent;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.other.ActionType;
import com.kehxstudios.atlas.tools.DataLoader;
import com.kehxstudios.atlas.tools.DebugTool;

import java.util.ArrayList;


/**
 * Created by ReidC on 2017-04-06.
 */

public class MainMenuScreen extends AScreen {

    private ArrayList<ScreenLaunchComponent> screenLaunchComponents;
    private HighScoreResetComponent highScoreResetComponent;

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
        screenData = DataLoader.load(ScreenType.MAIN_MENU);
        init();
    }

    public void init() {
        super.init();
        screenLaunchComponents = new ArrayList<ScreenLaunchComponent>();

        font = new BitmapFont();
        font.getData().setScale(5f);

        flappyBirdEntity = new Entity(screenEntity.getX(), screenEntity.getY() - 500);
        GraphicsComponent flappyBirdGraphics = new GraphicsComponent(flappyBirdEntity);
        flappyBirdGraphics.setTexture(new Texture("screens/flappyBird/flappyBird.png"));
        screenLaunchComponents.add(new ScreenLaunchComponent(flappyBirdEntity,
                flappyBirdGraphics.getWidth(), flappyBirdGraphics.getHeight(),
                ScreenType.FLAPPY_BIRD));
        flappyBirdLayout = new GlyphLayout(font, flappyBirdText);
        flappyBirdLayout.setText(font, flappyBirdText, Color.WHITE,WIDTH/2, Align.left, true);

        introEntity = new Entity(screenEntity.getX(), screenEntity.getY());
        GraphicsComponent introGraphics = new GraphicsComponent(introEntity);
        introGraphics.setTexture(new Texture("screens/flappyBird/flappyBird.png"));
        screenLaunchComponents.add(new ScreenLaunchComponent(introEntity,
                        introGraphics.getWidth(), introGraphics.getHeight(),
                ScreenType.INTRO));
        introLayout = new GlyphLayout(font, introText);
        introLayout.setText(font, introText, Color.WHITE,WIDTH/2, Align.left, true);

        resetScoresEntity = new Entity(screenEntity.getX(), screenEntity.getY() + 500);
        GraphicsComponent resetScoreGraphics = new GraphicsComponent(resetScoresEntity);
        resetScoreGraphics.setTexture(new Texture("screens/flappyBird/flappyBird.png"));
        highScoreResetComponent = new HighScoreResetComponent(resetScoresEntity,
                        resetScoreGraphics.getWidth(), resetScoreGraphics.getHeight(),
                ScreenType.FLAPPY_BIRD);
        resetLayout = new GlyphLayout(font, resetText);
        resetLayout.setText(font, resetText, Color.WHITE,WIDTH/2, Align.left, true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (screenTime >= 0.2f && Gdx.input.justTouched()) {
            DebugTool.log("x: "+mouse.x, "y: "+mouse.y);
            for (ScreenLaunchComponent component : screenLaunchComponents) {
                component.hits(mouse.x, mouse.y);
            }
            highScoreResetComponent.hits(mouse.x, mouse.y);
        }

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
    public void resetScreen() {

    }

    @Override
    public void dispose() {
        flappyBirdEntity.destroy();
        introEntity.destroy();
        resetScoresEntity.destroy();
        font.dispose();
        super.dispose();
    }

    @Override
    public void trigger(ActionType type) {

    }
}
