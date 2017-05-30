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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.managers.ScreenManager;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.type.ScreenType;

import java.awt.Font;

/**
 * The loading screen used when loading assets
 */

public class LoadingScreen extends AScreen {

    private ScreenType loadingType;
    private TextureAtlas textureAtlas;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Sprite hexSprite1, hexSprite2;

    private BitmapFont loadingFont;
    private float loadingFontScale = 2f;
    private GlyphLayout loadingLayout;
    private String loadingText = "Loading...";

    public LoadingScreen() {
        super(ScreenType.LOADING);
        loadingType = ScreenType.VOID;
        batch = gm.getBatch();
        camera = new OrthographicCamera();
        camera.position.set(width/2, height/2, 0);
        camera.setToOrtho(false, width, height);
        hexSprite1 = new Sprite(new Texture(Gdx.files.internal("loading/hexagon_outline.png")));
        hexSprite1.setCenter(width/2, height/2);
        hexSprite2 = new Sprite(new Texture(Gdx.files.internal("loading/hexagon_outline.png")));
        hexSprite2.setCenter(width/2, height/2);
        loadingFont = new BitmapFont();
        loadingFont.getData().setScale(loadingFontScale, loadingFontScale);
        loadingLayout = new GlyphLayout(loadingFont, loadingText);
        loadingLayout.setText(loadingFont, loadingText, graphicsManager.COLOR_BLUE, 0, Align.center, false);
    }

    @Override
    public void render(float delta) {
        if (gm.getAssetManager().update()) {
            screenManager.finishedLoadingScreen();
            return;
        }
        // Clear the current graphics
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        loadingFont.draw(batch, loadingLayout, width/2, height/4);
        
        hexSprite1.rotate(5f);
        hexSprite1.draw(batch);
        
        hexSprite2.rotate(-5f);
        hexSprite2.draw(batch);

        batch.end();
    }

    public void startLoadingScreen(ScreenType type) {
        loadingType = type;
        if (!gm.getAssetManager().isLoaded(loadingType.getAtlasPath())) {
            gm.getAssetManager().load(loadingType.getAtlasPath(), TextureAtlas.class);
        } else {
            screenManager.finishedLoadingScreen();
        }
    }

    public void dispose() {
        if (!disposed) {
            super.dispose();
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

}
