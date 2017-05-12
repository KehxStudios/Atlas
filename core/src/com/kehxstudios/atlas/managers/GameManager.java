package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.screens.LoadingScreen;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.type.ScreenType;

public class GameManager extends Game {

	private static GameManager instance;
	public static GameManager getInstance() {
		return instance;
	}

	private ScreenManager screenManager;
	private EntityManager entityManager;
	private GraphicsManager graphicsManager;
	private InputManager inputManager;
	private PhysicsManager physicsManager;

	private AssetManager assetManager;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private GameState gameState;
	private LoadingScreen loadingScreen;
	private ScreenType loadingScreenType;

	// Used for Desktop window size, will later update for size options
	public static final float D_WIDTH = 480, D_HEIGHT = 800;

	@Override
	public void create () {
		DebugTool.log("GameManager.create() started");
		// Set instance to the current one created
		instance = this;

		// Setup instances of each Manager including AssetManager
		screenManager = ScreenManager.getInstance();
		entityManager = EntityManager.getInstance();
		graphicsManager = GraphicsManager.getInstance();
		inputManager = InputManager.getInstance();
		physicsManager = PhysicsManager.getInstance();
		assetManager = new AssetManager();
		Texture.setAssetManager(assetManager);

		// If running on the Desktop set title, window size and lock, will update for size options later
		if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
			Gdx.app.getGraphics().setTitle("Atlas");
			Gdx.graphics.setWindowedMode((int)D_WIDTH, (int)D_HEIGHT);
			Gdx.graphics.setResizable(false);
		}

		// Setup Camera and Batch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

		gameState = GameState.Running;
		
		loadingScreen = new LoadingScreen();

		// Demand a new Screen be started now
        	screenManager.demandNewScreen(ScreenType.INTRO);
		
		DebugTool.log("GameManager.create() complete");
	}

	public void reload() {
		// Set instance to the current one created
		instance = this;

		// Setup instances of each Manager including AssetManager
		screenManager = ScreenManager.getInstance();
		entityManager = EntityManager.getInstance();
		graphicsManager = GraphicsManager.getInstance();
		inputManager = InputManager.getInstance();
		physicsManager = PhysicsManager.getInstance();
		assetManager = new AssetManager();

		// If running on the Desktop set title, window size and lock, will update for size options later
		if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
			Gdx.app.getGraphics().setTitle("Atlas");
			Gdx.graphics.setWindowedMode((int)D_WIDTH, (int)D_HEIGHT);
			Gdx.graphics.setResizable(false);
		}

		// Setup Camera and Batch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

		DebugTool.log("GameManager.reload() complete");
	}

	@Override
	public void pause() {
		DebugTool.log("GAME_PAUSE");
		gameState = GameState.Paused;
	}

	@Override
	public void resume() {
		DebugTool.log("GAME_RESUME");
		gameState = GameState.Running;
	}

	@Override
	public void render () {
		// Clear the current graphics
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// render the Screen that is set allowing screen functions
		super.render();

		if (gameState == GameState.Running) {
			// Get the current delta time to pass to Managers
			float delta = Gdx.graphics.getDeltaTime();

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
		} else if (gameState == GameState.Loading) {
			if (assetManager.update()) {
				finishedLoading();
			}
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
	
	public enum GameState {
		Running, Paused, Loading
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	
	public void startLoading(ScreenType screenType) {
		loadingScreenType = screenType;
		loadingScreen.setLoadingType(loadingScreenType);
		setScreen(loadingScreen);
		loadingScreen.finalizeSetup();
		gameState = GameState.Loading;
	}
	private void finishedLoading() {
		screenManager.finishedLoadingScreen();
		gameState = GameState.Running;
	}

	public void setBatch(SpriteBatch batch) { this.batch = batch; }

	public AssetManager getAssetManager() { return assetManager; }

	public ScreenManager getScreenManager() { return screenManager; }
}
