
package com.kehxstudios.atlas.screens;

import com.kehxstudios.atlas.managers.ScreenManager;
import com.kehxstudios.atlas.type.ScreenType;

public class LoadingScreen extends AScreen {

    private ScreenType loadingType;
    private boolean finishedLoading;

    public LoadingScreen() {
      super(ScreenType.LOADING);
      finishedLoading = false;
      
    }
    
    @Override
    public void render(float delta) {
      super.render(delta);
      
      if (finishedLoading) {
          ScreenManager.getInstance().requestNewScreenType(loadingType);
      }
    }
 
    public ScreenType getloadingType() { return loadingType; }
    public void setLoadingType(ScreenType type) { loadingType = type; }
    
    public boolean isFinishedLoading() { return finishedLoading; }
    public void setFinishedLoading(boolean value) { finishedLoading = value; }
  
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
