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

package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gwt.user.server.Util;
import com.kehxstudios.atlas.data.GameSettings;
import com.kehxstudios.atlas.data.Player;
import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.tools.GPSTracker;
import com.kehxstudios.atlas.tools.UtilityTool;
import com.kehxstudios.atlas.type.ScreenType;

/**
 * Main game class that handles the main game loop
 */

public class GameManager extends Game {

	private static GameManager instance;
	public static GameManager getInstance() {
		return instance;
	}

	// All Atlas-made Managers master reference
	private BuildManager buildManager;
	private ScreenManager screenManager;
	private EntityManager entityManager;
	private GraphicsManager graphicsManager;
	private InputManager inputManager;
	private PhysicsManager physicsManager;
	private PositionManager positionManager;
	private SoundManager soundManager;

	// AssetManager's master reference
	private AssetManager assetManager;

	// SpriteBatch that is passed around
	private SpriteBatch batch;

	// Current state of the game
	private GameState gameState;

	public GameSettings gameSettings;

	public Player player;

	// Booleans to print debug/error log
	public boolean showDebugLog = true;
	public boolean showErrorLog = true;

	// Used for Desktop window size, will later update for size options
	public static final float D_WIDTH = 480, D_HEIGHT = 800;

	public GPSTracker gpsTracker;
	public GPSTracker getGpsTracker() { return gpsTracker; }

	public GameManager(GPSTracker gpsTracker) {
		this.gpsTracker = gpsTracker;
	}

	@Override
	public void create () {
		// Set instance to the current one created
		instance = this;

		gameSettings = new GameSettings();

		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		// Setup Camera and Batch
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

        setupManagers();
		player = new Player();

		// Demand a new Screen be started now
		screenManager.demandNewScreen(ScreenType.INTRO);
	}

	private void setupManagers() {
        // Setup instances of each Manager including AssetManager
        buildManager = new BuildManager(this);
        screenManager = new ScreenManager(this);
        entityManager = new EntityManager(this);
        graphicsManager = new GraphicsManager(this);
        inputManager = new InputManager(this);
        physicsManager = new PhysicsManager(this);
        positionManager = new PositionManager(this);
        soundManager = new SoundManager(this);
        assetManager = new AssetManager();

        buildManager.init();
        screenManager.init();
        entityManager.init();
        graphicsManager.init();
        inputManager.init();
        physicsManager.init();
        positionManager.init();
        soundManager.init();
        Texture.setAssetManager(assetManager);
    }

	@Override
	public void pause() {
		DebugTool.log("GAME_PAUSE");
		gameState = GameState.Paused;
	}

	@Override
	public void resume() {
		super.resume();
		gameState = GameState.Running;
		Texture.setAssetManager(assetManager);
	}

	@Override
	public void render () {
		// Get the current delta time to pass to Managers
		float delta = Gdx.graphics.getDeltaTime();

		if (delta <= 0 || gameState == GameState.Paused) {
			return;
		}
		if (gameState == GameState.Running) {
			// render the Screen that is set allowing screen functions
			super.render();
			// tick InputManager to check for input changes
			inputManager.tick(delta);
			// tick PhysicsManager to change any locations by the means of physics
			physicsManager.tick(delta);
			// tick EntityManager to check if anything needs to be removed
			entityManager.tick(delta);
			// tick GraphicsManager to check if any animations require changing
			graphicsManager.tick(delta);
			// render everything inside of GraphicsManager
			graphicsManager.render(batch);
			// tick ScreenManager to check if screen change is needed
			screenManager.tick(delta);
		} else if (gameState == GameState.Loading){
			// render the Screen that is set allowing screen functions
			super.render();
		}
	}

	public void setScreen(Screen screen) {
		super.setScreen(screen);
        if (((AScreen)screen).getType() != ScreenType.LOADING) {
			gameState = GameState.Running;
        } else {
			gameState = GameState.Loading;
		}
	}

	// Dispose of everything that might need disposal
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		screen.dispose();
		graphicsManager.dispose();
		assetManager.dispose();
		Gdx.app.error("Disposal", "COMPLETED");
	}
	
	private enum GameState {
		Running, Paused, Loading
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public AssetManager getAssetManager() { return assetManager; }

    public BuildManager getBuildManager() { return buildManager; }
	public ScreenManager getScreenManager() { return screenManager; }
	public EntityManager getEntityManager() { return entityManager; }
	public GraphicsManager getGraphicsManager() { return graphicsManager; }
	public InputManager getInputManager() { return inputManager; }
	public PhysicsManager getPhysicsManager() { return physicsManager; }
	public PositionManager getPositionManager() { return positionManager; }
	public SoundManager getSoundManager() { return soundManager; }
}
