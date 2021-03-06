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
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.managers.ScreenManager;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.ErrorTool;
import com.kehxstudios.atlas.type.MusicType;
import com.kehxstudios.atlas.type.ScreenType;

import java.awt.Font;

/**
 * The loading screen used when loading assets
 */

public class LoadingScreen extends AScreen {

    private ScreenType loadingType;

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private Sprite hexSprite;
    private FloatingTextComponent loadingFloatingText;

    private boolean loadingMusic;
    private Music music;

    private boolean initialLoad;

    public LoadingScreen() {
        super(ScreenType.LOADING);
        loadingType = ScreenType.VOID;
        batch = gm.getBatch();
        camera = new OrthographicCamera();
        camera.position.set(width/2, height/2, 0);
        camera.setToOrtho(false, width, height);

        createLoadingHexagonSprite();
        createLoadingFloatingText();

        loadingMusic = gm.gameSettings.loadingMusic;
        music = null;

        graphicsManager.startLoadingIconAtlas();

        initialLoad = true;
    }

    private void createLoadingHexagonSprite() {
        hexSprite = new Sprite(new Texture(Gdx.files.internal("loading/hex.png")));
        hexSprite.setCenter(width/2, height/2);
    }

    private void createLoadingFloatingText() {
        loadingFloatingText = new FloatingTextComponent();
        loadingFloatingText.position = new Vector2(width/2, height/4);
        loadingFloatingText.font = buildManager.boldLargeFont;
        loadingFloatingText.text = "Loading...";
        loadingFloatingText.color = graphicsManager.COLOR_BLUE;
        loadingFloatingText.layout = new GlyphLayout(loadingFloatingText.font, loadingFloatingText.text);
        loadingFloatingText.layout.setText(loadingFloatingText.font, loadingFloatingText.text,
                loadingFloatingText.color, 0, Align.left, false);
    }

    @Override
    public void render(float delta) {
        if (gm.getAssetManager().update()) {
            if (loadingMusic && music != null) {
                music.stop();
                music.dispose();
            }
            if (initialLoad) {
                graphicsManager.loadIconAtlas();
                initialLoad = false;
            }
            screenManager.finishedLoadingScreen();
            return;
        }
        // Clear the current graphics
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        hexSprite.rotate(-5f);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        hexSprite.draw(batch);
        loadingFloatingText.font.draw(batch,  loadingFloatingText.layout, loadingFloatingText.position.x
                - loadingFloatingText.layout.width/2, loadingFloatingText.position.y
                - loadingFloatingText.layout.height/2);

        batch.end();
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public void startLoadingScreen(ScreenType type) {
        if (loadingType != ScreenType.VOID)
            gm.getAssetManager().unload(loadingType.getAtlasPath());
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
        if (music != null && loadingMusic && music.isPlaying()) {
            music.stop();
        }
    }

}
