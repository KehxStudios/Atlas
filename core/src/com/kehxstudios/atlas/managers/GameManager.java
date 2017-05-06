package com.kehxstudios.atlas.managers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

	// Used for Desktop window size, will later update for size options
	public static final float D_WIDTH = 480, D_HEIGHT = 800;

	@Override
	public void create () {
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

		// Demand a new Screen be started now
        screenManager.demandNewScreen(ScreenType.INTRO);
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

		// Demand a new Screen be started now
		screenManager.demandNewScreen(ScreenType.INTRO);

		DebugTool.log("GameManager.reload() complete");
	}

	@Override
	public void render () {
		// Get the current delta time to pass to Managers
		float delta = Gdx.graphics.getDeltaTime();

		// render the Screen that is set allowing screen functions
		super.render();

		// tick InputManager to check for input changes
		inputManager.tick(delta);

		// tick PhysicsManager to change any locations by the means of physics
		physicsManager.tick(delta);

		// tick ScreenManager to check if screen change is needed
		screenManager.tick(delta);

		// tick EntityManager to check if anything needs to be removed
		entityManager.tick(delta);

		// tick GraphicsManager to check if any animations require changing
		graphicsManager.tick(delta);

		// Clear the current graphics
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// render everything inside of GraphicsManager
		graphicsManager.render(batch, camera);
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

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) { this.batch = batch; }

	public AssetManager getAssetManager() { return assetManager; }

	public ScreenManager getScreenManager() { return screenManager; }
}
