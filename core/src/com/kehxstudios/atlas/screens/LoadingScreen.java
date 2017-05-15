
package com.kehxstudios.atlas.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.kehxstudios.atlas.managers.ScreenManager;
import com.kehxstudios.atlas.type.ScreenType;

public class LoadingScreen extends AScreen {

    private ScreenType loadingType;
    private TextureAtlas textureAtlas;
    private boolean finishedLoading;

    public LoadingScreen() {
        super(ScreenType.LOADING);
        loadingType = ScreenType.VOID;
        finishedLoading = true;
    }

    public void finalizeSetup() {
        gm.getAssetManager().load(loadingType.getAtlasPath(), TextureAtlas.class);
        finishedLoading = false;
    }

    @Override
    public void render(float delta) {
        if (gm.getAssetManager().update()) {
            ScreenManager.getInstance().finishedLoadingScreen();
            finishedLoading = true;
        }
        gm.getBatch().begin();
        Gdx.gl.glClearColor(0,0,1,1);


        gm.getBatch().end();
    }

    public boolean isFinishedLoading() { return finishedLoading; }

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
