package com.kehxstudios.atlas.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kehxstudios.atlas.managers.EntityManager;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.managers.InputManager;
import com.badlogic.gdx.utils.Json;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.screens.AScreen;
import com.kehxstudios.atlas.screens.ScreenLoader;
import com.kehxstudios.atlas.screens.ScreenSnapShot;
import com.kehxstudios.atlas.screens.ScreenType;
import com.kehxstudios.atlas.tools.DebugTool;

public class GameManager extends Game {

	private static GameManager instance;
	public static GameManager getInstance() {
		return instance;
	}

	EntityManager entityManager;
	GraphicsManager graphicsManager;
	InputManager inputManager;
	PhysicsManager physicsManager;

	SpriteBatch batch;
	OrthographicCamera camera;

	private static Json json = new Json();

	@Override
	public void create () {
		instance = this;

		entityManager = EntityManager.getInstance();
		graphicsManager = GraphicsManager.getInstance();
		inputManager = InputManager.getInstance();
		physicsManager = PhysicsManager.getInstance();

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

		Gdx.app.getGraphics().setTitle("Atlas");

		setNewScreen(ScreenLoader.loadScreen(ScreenType.INTRO));
	}

	@Override
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();
		inputManager.tick(delta);
		physicsManager.tick(delta);
		graphicsManager.tick(delta);

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		graphicsManager.render(batch, camera);
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		screen.dispose();
		graphicsManager.dispose();
		Gdx.app.error("Disposal", "COMPLETED");
	}

	public void setNewScreen(Screen newScreen) {
		if (screen != null)
			screen.dispose();
		setScreen(newScreen);
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}
}
