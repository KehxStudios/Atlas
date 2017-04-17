package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.actions.LaunchScreenAction;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.data.TextureType;
import com.kehxstudios.atlas.tools.DataTool;
import com.kehxstudios.atlas.tools.DebugTool;

/**
 * Created by ReidC on 2017-04-06.
 */

public class IntroScreen extends AScreen {

    private BitmapFont font;
    private GlyphLayout layout;
    private String continueText = "Click to Continue";
    boolean finalLogo = false;
    boolean clickToContinue = false;

    public IntroScreen() {
        super();
        DebugTool.log("IntroScreen");
        screenData = DataTool.load(ScreenType.INTRO);
        init();
    }

    protected void init() {
        super.init();
        DebugTool.log("IntroScreen.init");
        font = new BitmapFont();
        font.getData().setScale(2f);
        layout = new GlyphLayout(font, continueText);
        layout.setText(font, continueText,Color.BLACK,WIDTH/2, Align.left, true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // increase screenTime in super method
        super.render(delta);
        if (!clickToContinue) {
            // If index is not on last path
            if (screenTime >= 2f && !finalLogo) {
                setGameLogo();
                screenTime = 0f;
                finalLogo = true;
            } else {
                ClickableComponent clickableComponent = new ClickableComponent(screenEntity, WIDTH, HEIGHT,
                        new LaunchScreenAction(ScreenType.MAIN_MENU));
                clickToContinue = true;
            }
        } else {
            gm.getBatch().begin();
            font.draw(gm.getBatch(), layout, WIDTH / 2 - layout.width / 2, HEIGHT / 5);
            gm.getBatch().end();
        }
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

    private void setDevLogo() {
        backgroundGraphics.setTexture(TextureType.INTRO_DEV_LOGO);
    }

    private void setGameLogo() {
        backgroundGraphics.setTexture(TextureType.INTRO_GAME_LOGO);
    }

    @Override
    public void dispose() {
        font.dispose();
        super.dispose();
    }
}
