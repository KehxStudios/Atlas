
package com.kehxstudios.atlas.screens;

import com.kehxstudios.atlas.managers.ScreenManager;
import com.kehxstudios.atlas.type.ScreenType;

public class LoadingScreen extends AScreen {

    private String[] filesLoading;

    public LoadingScreen() {
        super(ScreenType.LOADING);
        fileLoading = new String[];
    }

    public void finalizeSetup() {
    }

    @Override
    public void render(float delta) {
        if (checkAllFiles()) {
            gm.finishedLoading();
        } else {
            super.render(delta);
            gm.getAssetManager().update();
            
			Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            
            
        }
    }
    
    private void checkAllFiles() {
        if (filesLoading.size() > 0) {
            for (String fileName : filesLoading) {
                if (!gm.getAssetManager.isLoaded(fileName))
                    return false;
            }
        }
        return true;
    }
    
    public void setFilesLoading(String[] filesLoading) { this.filesLoading = filesLoading; }
    
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
