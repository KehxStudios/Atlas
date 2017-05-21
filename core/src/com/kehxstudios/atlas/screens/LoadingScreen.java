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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.kehxstudios.atlas.managers.ScreenManager;
import com.kehxstudios.atlas.type.ScreenType;

/**
 * The loading screen used when loading assets
 */

public class LoadingScreen extends AScreen {

    private ScreenType loadingType;
    private TextureAtlas textureAtlas;
    private boolean finishedLoading;

    public LoadingScreen() {
        super(ScreenType.LOADING);
        loadingType = ScreenType.VOID;
        finishedLoading = true;
    }

    protected void startLoading() {
        gm.getAssetManager().load(loadingType.getAtlasPath(), TextureAtlas.class);
        finishedLoading = false;
    }

    @Override
    public void render(float delta) {
        if (gm.getAssetManager().update()) {
            ScreenManager.getInstance().finishedLoadingScreen();
            finishedLoading = true;
            return;
        }
        gm.getBatch().begin();

        // DRAW LOADING GRAPHICS

        gm.getBatch().end();
    }

    public boolean isFinishedLoading() { return finishedLoading; }

    public void setLoadingType(ScreenType type) {
        loadingType = type;
        startLoading();

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
