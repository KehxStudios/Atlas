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

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.components.CameraComponent;
import com.kehxstudios.atlas.components.MusicComponent;
import com.kehxstudios.atlas.managers.EntityManager;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.managers.InputManager;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.managers.PositionManager;
import com.kehxstudios.atlas.managers.ScreenManager;
import com.kehxstudios.atlas.managers.SoundManager;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.managers.BuildManager;
import com.kehxstudios.atlas.type.MusicType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.GameManager;
import com.kehxstudios.atlas.type.ScreenType;

/**
 * Abstract class used by all Screens
 */

public abstract class AScreen implements Screen {

    protected GameManager gm;
    protected ScreenType type;
    protected float width, height;

    protected BuildManager buildManager;
    protected EntityManager entityManager;
    protected GraphicsManager graphicsManager;
    protected InputManager inputManager;
    protected PhysicsManager physicsManager;
    protected PositionManager positionManager;
    protected ScreenManager screenManager;
    protected SoundManager soundManager;

    protected Entity screenEntity;
    protected CameraComponent screenCamera;
    protected MusicComponent screenMusic;

    protected float screenTime;
    
    public AScreen(ScreenType type) {
        this.type = type;
        gm = GameManager.getInstance();
        buildManager = gm.getBuildManager();
        entityManager = gm.getEntityManager();
        graphicsManager = gm.getGraphicsManager();
        inputManager = gm.getInputManager();
        physicsManager = gm.getPhysicsManager();
        positionManager = gm.getPositionManager();
        screenManager = gm.getScreenManager();
        soundManager = gm.getSoundManager();
        width = type.getWidth();
        height = type.getHeight();
        screenTime = 0f;
    }

    protected void init() {
        screenEntity = buildManager.createEntity(width/2, height/2);

        screenCamera = buildManager.createCameraComponent(screenEntity, width, height, false);

        screenMusic = buildManager.createMusicComponent(screenEntity, MusicType.getTypeById(type.getId()), 0.2f);
        screenMusic.music.play();
    }

    public void reset() {
        gm.getPositionManager().setPosition(screenEntity.id, new Vector2(width/2, height/2));
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

    public float getWidth() { return width; }

    public float getHeight() { return height; }
}
