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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.actions.Action;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.data.HighScores;
import com.kehxstudios.atlas.managers.BuildManager;
import com.kehxstudios.atlas.tools.DebugTool;
import com.kehxstudios.atlas.type.TextureType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.type.ScreenType;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Game of Flappy Bat
 */

public class FlappyBirdScreen extends AScreen {

    private static final int WALL_SPACING = 160;
    private static final int WALL_COUNT = 3;
    private static final int WALL_WIDTH = 52;
    private static final int WALL_HEIGHT = 320;
    private static final int WALL_FLUCTUATION = 140;
    private static final int WALL_GAP = 100;
    private static final int WALL_Y_START = 510;
    private static final int WALL_X_START = 250;

    private static final int GROUND_COUNT = 2;
    private static final int GROUND_Y_OFFSET = 20;
    private static final int GROUND_WIDTH = 336;
    
    private static final float BAT_X_MOVEMENT = 100;
    private static final float BAT_Y_JUMP = 300;

    private static final float GRAVITY = -17;

    private Entity batEntity;
    private ArrayList<Entity> groundEntities;
    private ArrayList<Entity> wallEntities;

    private PhysicsComponent batPhysics;
    private float batStartX, batCurrentX;

    private int lowScore, highScore;
    private FloatingTextComponent scoreText, lowScoreText, highScoreText;

    private HighScores highScores;
    private int score;

    private Random random = new Random();

    public FlappyBirdScreen() {
        super(ScreenType.FLAPPY_BIRD);
        init();
    }

    protected void init() {
        super.init();

        Entity mainMenuLaunchEntity = buildManager.createEntity(50, height-50);
        buildManager.createClickableComponent(mainMenuLaunchEntity, 100, 100, true, false,
                buildManager.createLaunchScreenAction(ScreenType.MAIN_MENU));

        batStartX = width/4;
        batCurrentX = batStartX;

        highScores = new HighScores(ScreenType.FLAPPY_BIRD);
        score = 0;

        lowScore = highScores.getLowScore();
        highScore = highScores.getHighScore();

        buildManager.createGraphicsComponent(screenEntity, 0, TextureType.FLAPPY_BIRD_BACKGROUND);

        float batWidth = TextureType.FLAPPY_BIRD_BIRD.getWidth();
        float batHeight = TextureType.FLAPPY_BIRD_BIRD.getHeight();
        batEntity = buildManager.createEntity(width/4, height/2);
        buildManager.createGraphicsComponent(batEntity, 3, TextureType.FLAPPY_BIRD_BIRD);
        batPhysics = buildManager.createPhysicsComponent(batEntity, new Vector2(100, 300), new Vector2(100,300));
        buildManager.createCollisionComponent(batEntity, batWidth, batHeight, false, false,
                buildManager.createResetScreenAction());
        buildManager.createClickableComponent(screenEntity, width, height, false, false,
                buildManager.createPhysicsAction(batPhysics, new Vector2(0, BAT_Y_JUMP)));

        float groundWidth = TextureType.FLAPPY_BIRD_GROUND.getWidth();
        float groundHeight = TextureType.FLAPPY_BIRD_GROUND.getHeight();
        groundEntities = new ArrayList<Entity>();
        for (int i = 0; i < GROUND_COUNT; i++) {
            Entity groundEntity = buildManager.createEntity(i * GROUND_WIDTH, GROUND_Y_OFFSET);
            buildManager.createGraphicsComponent(groundEntity, 2, TextureType.FLAPPY_BIRD_GROUND);
            buildManager.createCollisionComponent(groundEntity, groundWidth, groundHeight, true, false,
                    buildManager.createResetScreenAction());
            groundEntities.add(groundEntity);
        }

        float wallWidth = TextureType.FLAPPY_BIRD_WALL.getWidth();
        float wallHeight = TextureType.FLAPPY_BIRD_WALL.getHeight();
        wallEntities = new ArrayList<Entity>();
        for (int i = 0; i < WALL_COUNT; i++) {
            float topWallX = WALL_X_START + i * WALL_SPACING;
            float topWallY = wallRandomY();
            Entity topWallEntity = buildManager.createEntity(topWallX, topWallY);
            buildManager.createGraphicsComponent(topWallEntity, 1, TextureType.FLAPPY_BIRD_WALL);
            buildManager.createCollisionComponent(topWallEntity, wallWidth, wallHeight, true, false, new Action());
            wallEntities.add(topWallEntity);

            Entity bottomWallEntity = buildManager.createEntity(topWallX, topWallY - WALL_GAP - WALL_HEIGHT);
            buildManager.createGraphicsComponent(bottomWallEntity, 1, TextureType.FLAPPY_BIRD_WALL);
            buildManager.createCollisionComponent(bottomWallEntity, wallWidth, wallHeight, true, false, new Action());
            wallEntities.add(bottomWallEntity);
        }

        Entity scoreEntity = buildManager.createEntity(0, 54);
        scoreText = buildManager.createFloatingTextComponent(scoreEntity, false, true, "Score: ", "",Color.BLACK);

        Entity lowScoreEntity = buildManager.createEntity(0, 36);
        lowScoreText = buildManager.createFloatingTextComponent(lowScoreEntity, false, true, "Low-Score: ", "", Color.BLACK);
        lowScore = highScores.getLowScore();
        lowScoreText.text = lowScore+"";
        lowScoreText.layout.setText(lowScoreText.font, lowScoreText.label + lowScoreText.text,
                Color.BLACK, 0, Align.left, true);

        Entity highScoreEntity = buildManager.createEntity(0, 18);
        highScoreText = buildManager.createFloatingTextComponent(highScoreEntity, false, true, "High-Score: ", "", Color.BLACK);
        highScore = highScores.getHighScore();
        highScoreText.text = highScore+"";
        highScoreText.layout.setText(highScoreText.font, highScoreText.label + highScoreText.text,
                Color.BLACK, 0, Align.left, true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (batEntity.position.y > height || batEntity.position.y < 20) {
            screenManager.requestScreenReset();
            return;
        }
        positionManager.setPosition(screenEntity.id, new Vector2(batEntity.position.x + 80, height/2));
        positionManager.setPosition(scoreText.entityId, new Vector2(batEntity.position.x + 80, 54));
        positionManager.setPosition(lowScoreText.entityId, new Vector2(batEntity.position.x + 80, 36));
        positionManager.setPosition(highScoreText.entityId, new Vector2(batEntity.position.x + 80, 18));
        batPhysics.velocity.x = BAT_X_MOVEMENT;
        batPhysics.velocity.y += GRAVITY;

        updateGroundEntities();
        updateWallEntities();
        updateScore();
    }

    private void updateScore() {
        batCurrentX = batEntity.position.x;
        score = (int)(batCurrentX - batStartX);
        scoreText.text = score+"";
        scoreText.layout.setText(scoreText.font, scoreText.label + scoreText.text,
                Color.BLACK, 0, Align.left, true);
    }
    
    public float wallRandomY() {
        return WALL_Y_START - random.nextFloat() * WALL_FLUCTUATION;
    }

    public void reset() {
        super.reset();
        if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Vibrator)) {
            Gdx.input.vibrate(50);
        }
        if (score > lowScore) {
            highScores.addToHighScores(gm.player.getName(),score);
            lowScore = highScores.getLowScore();
            lowScoreText.text = lowScore+"";
            lowScoreText.layout.setText(lowScoreText.font, lowScoreText.label + lowScoreText.text,
                    lowScoreText.color, 0, Align.left, true);
            if (highScore != highScores.getHighScore()) {
                highScore = highScores.getHighScore();
                highScoreText.text = highScore+"";
                highScoreText.layout.setText(highScoreText.font, highScoreText.label + highScoreText.text,
                        highScoreText.color, 0, Align.left, true);
            }
            gm.player.addScore(score/100);
        }
        positionManager.setPosition(batEntity.id, new Vector2(width/4, height/2));
        positionManager.setPosition(screenEntity.id, new Vector2(batEntity.position.x + 80, height/2));
        batPhysics.velocity.set(0,0);
        for (int i = 0; i < GROUND_COUNT; i++) {
            positionManager.setPosition(groundEntities.get(i).id, new Vector2(
                    i * GROUND_WIDTH, GROUND_Y_OFFSET));
        }
        int j = 0;
        for (int i = 0; i < WALL_COUNT * 2; i+=2) {
            int topId = wallEntities.get(i).id;
            int bottomId = wallEntities.get(i + 1).id;
            float topWallX = WALL_X_START + j++ * WALL_SPACING;
            float topWallY = wallRandomY();
            positionManager.setPosition(topId, new Vector2(topWallX, topWallY));
            positionManager.setPosition(bottomId, new Vector2(
                    topWallX, topWallY - WALL_GAP - WALL_HEIGHT));
        }

        batStartX = batEntity.position.x;
        batCurrentX = batStartX;
        score = 0;
    }

    private void updateWallEntities() {
        ArrayList<Entity> resetWalls = new ArrayList<Entity>();
        for (Entity wallEntity : wallEntities) {
            if (screenEntity.position.x - width / 2 >= wallEntity.position.x + WALL_WIDTH/2) {
                resetWalls.add(wallEntity);
            }
        }
        if (resetWalls.size() == 2) {
            DebugTool.log("WALL RESETTING");
            float topWallX = resetWalls.get(0).position.x + WALL_COUNT * WALL_SPACING;
            float topWallY = wallRandomY();
            positionManager.setPosition(resetWalls.get(0).id, new Vector2(topWallX, topWallY));
            positionManager.setPosition(resetWalls.get(1).id, new Vector2(topWallX,
                    topWallY - WALL_GAP - WALL_HEIGHT));
        }
    }

    private void updateGroundEntities() {
        for (Entity groundEntity : groundEntities) {
            if(screenEntity.position.x - (width / 2) > groundEntity.position.x + GROUND_WIDTH/2) {
                positionManager.movePosition(groundEntity.id, GROUND_WIDTH * GROUND_COUNT, 0);
            }
        }
    }

    public void dispose() {
        if (!disposed) {
            super.dispose();
        }
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