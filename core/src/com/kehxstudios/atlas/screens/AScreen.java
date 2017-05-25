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
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kehxstudios.atlas.components.CameraComponent;
import com.kehxstudios.atlas.components.MusicComponent;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.managers.ScreenManager;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.Factory;
import com.kehxstudios.atlas.tools.Templates;
import com.kehxstudios.atlas.type.MusicType;
import com.kehxstudios.atlas.type.TextureType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.data.EntityData;
import com.kehxstudios.atlas.managers.GameManager;
import com.kehxstudios.atlas.data.HighScores;
import com.kehxstudios.atlas.type.ScreenType;

/**
 * Abstract class used by all Screens
 */

public abstract class AScreen implements Screen {

    protected GameManager gm;
    protected ScreenType type;
    protected float width, height;

    protected Entity screenEntity;
    protected CameraComponent screenCamera;
    protected MusicComponent screenMusic;

    protected float screenTime;
    
    public AScreen(ScreenType type) {
        this.type = type;
        gm = GameManager.getInstance();
        width = type.getWidth();
        height = type.getHeight();
        screenTime = 0f;
    }

    protected void init() {
        DebugTool.log("AScreen.init() Start");

        DebugTool.log("AScreen.init() End");
    }

    public void createEntities() {
        screenEntity = Factory.createEntity(width/2, height/2);

        screenCamera = (CameraComponent)Factory.createComponent(screenEntity,
                Templates.cameraComponentData(width, height, false));
        screenCamera.camera.update();

        screenMusic = (MusicComponent)Factory.createComponent(screenEntity,
                Templates.musicComponentData(MusicType.getTypeById(type.getId()), 0.5f));
        screenMusic.music.play();
    }

    public void reset() {
        screenEntity.position.set(width/2, height/2);
        screenCamera.camera.update();
    }

    @Override
    public void render(float delta) {
        screenTime += delta;
    }

    @Override
    public void dispose() {
        screenMusic.music.stop();
        DebugTool.log("SCREEN_DISPOSAL");
    }
    
    @Override
    public void show() {
        DebugTool.log("SCREEN_SHOW");
    }

    @Override
    public void resize(int width, int height) {
        DebugTool.log("SCREEN_RESIZE");
    }

    @Override
    public void pause() {
        DebugTool.log("SCREEN_PAUSE");
    }

    @Override
    public void resume() {
        DebugTool.log("SCREEN_RESUME");
    }

    @Override
    public void hide() {
        DebugTool.log("SCREEN_HIDE");
    }

    public ScreenType getType() { return type; }

    public OrthographicCamera getCamera() { return screenCamera.camera; }

    public float getWidth() { return width; }

    public float getHeight() { return height; }

    public float getScaleWidth() { return Gdx.graphics.getWidth() / width; }

    public float getScaleHeight() { return Gdx.graphics.getHeight() / height; }
}
