package com.kehxstudios.atlas.screens;

import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.tools.Templates;
import com.kehxstudios.atlas.type.ScreenType;
import com.kehxstudios.atlas.type.TextureType;


public class RecapJournalScreen extends AScreen {


    public RecapJournalScreen() {
        super(ScreenType.RECAP_JOURNAL);
        
    }


    public void finalizeSetup() {
        super.finalizeSetup();
    }

    @Override
    public void render(float delta) {
        // increase screenTime in super method
        super.render(delta);
    
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
