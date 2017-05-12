
package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.kehxstudios.atlas.managers.ScreenManager;
import com.kehxstudios.atlas.type.ScreenType;

public class LoadingScreen extends AScreen {

    private ScreenType loadingType;

    public LoadingScreen() {
        super(ScreenType.LOADING);
        loadingType = ScreenType.VOID;
    }

    public void finalizeSetup() {
        gm.getAssetManager().load(loadingType.getAtlasPath(), TextureAtlas.class);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,1,1);
        /*
        if (checkAllFiles()) {
            gm.finishedLoading();
        } else {
            super.render(delta);
            gm.getAssetManager().update();

        }
        */
    }

    public void setLoadingType(ScreenType type) { loadingType = type; }

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
