package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.tools.DataTool;
import com.kehxstudios.atlas.tools.DebugTool;

/**
 * Created by ReidC on 2017-04-06.
 */

public class IntroScreen extends AScreen {

    private BitmapFont font;
    private GlyphLayout layout;
    private String continueText = "Click to Continue";

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
        if (Gdx.input.justTouched()) {
            screenTime++;
        }
        // If index is not on last path
        if (backgroundIndex < backgroundPaths.length - 1) {
            // Check if past background screen time
            if (screenTime >= backgroundTimes[backgroundIndex]) {
                // Cycle to next background
                nextBackground();
            }
        } else {
            // Draw continue text to screen
            gm.getBatch().begin();
            font.draw(gm.getBatch(), layout, WIDTH/2 - layout.width/2, HEIGHT/5);
            gm.getBatch().end();
            // Check if user clicked to continue after minimum 2 seconds
            if (screenTime >= 2f && Gdx.input.justTouched() || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                // Start main menu
                launchNextScreen(ScreenType.MAIN_MENU);
            }
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

    private void nextBackground() {
        backgroundIndex++;
        if (backgroundIndex < backgroundPaths.length) {
            backgroundGraphics.setTexture(new Texture(backgroundPaths[backgroundIndex]));
            screenTime = 0f;
        }
    }

    @Override
    public void dispose() {
        font.dispose();
        super.dispose();
    }
}
