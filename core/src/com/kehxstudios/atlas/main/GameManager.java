package com.kehxstudios.atlas.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Method;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.kehxstudios.atlas.managers.EntityManager;
import com.kehxstudios.atlas.managers.GraphicsManager;
import com.kehxstudios.atlas.managers.InputManager;
import com.badlogic.gdx.utils.Json;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.screens.AScreen;
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

	public static final float D_WIDTH = 480, D_HEIGHT = 800;

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

		if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
			Gdx.graphics.setWindowedMode((int)D_WIDTH, (int)D_HEIGHT);
			Gdx.graphics.setResizable(false);
		}
		launchNewScreen(ScreenType.INTRO);


	}

	private void print() {
		DebugTool.log("Print Complete");
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

	private void setNewScreen(Screen newScreen) {
		if (screen != null)
			screen.dispose();
		setScreen(newScreen);
	}

	public void launchNewScreen(ScreenType type) {
		try {
			setNewScreen((AScreen) ClassReflection.newInstance(type.loaderClass));
		} catch (ReflectionException e) {
			e.printStackTrace();
		}

	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}
}
