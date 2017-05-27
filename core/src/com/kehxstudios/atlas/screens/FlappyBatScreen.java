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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.data.HighScores;
import com.kehxstudios.atlas.managers.PositionManager;
import com.kehxstudios.atlas.tools.Factory;
import com.kehxstudios.atlas.type.TextureType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.type.ScreenType;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Game of Flappy Bat
 */

public class FlappyBatScreen extends AScreen {

    private static final int WALL_SPACING = 125;
    private static final int WALL_COUNT = 3;
    private static final int WALL_WIDTH = 52;
    private static final int WALL_HEIGHT = 320;
    private static final int WALL_FLUCTUATION = 160;
    private static final int WALL_GAP = 100;
    private static final int WALL_LOWEST_OPENING = 155;

    private static final int GROUND_COUNT = 2;
    private static final int GROUND_Y_OFFSET = 5;
    private static final int GROUND_WIDTH = 366;
    
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

    public FlappyBatScreen() {
        super(ScreenType.FLAPPY_BAT);

        batStartX = width/4;
        batCurrentX = batStartX;

        highScores = new HighScores(ScreenType.FLAPPY_BAT);
        score = 0;

        lowScore = highScores.getLowScore();
        highScore = highScores.getHighScore();

        init();
    }

    protected void init() {
        super.init();

        Factory.createGraphicsComponent(screenEntity, 0, TextureType.FLAPPY_BAT_BACKGROUND);
        Factory.createCameraComponent(screenEntity, width, height, false);

        float batWidth = TextureType.FLAPPY_BAT_BAT.getWidth();
        float batHeight = TextureType.FLAPPY_BAT_BAT.getHeight();
        batEntity = Factory.createEntity(width/4, height/2);
        Factory.createGraphicsComponent(batEntity, 2, TextureType.FLAPPY_BAT_BAT);
        batPhysics = Factory.createPhysicsComponent(batEntity, new Vector2(100, 300), new Vector2(100,300));
        Factory.createCollisionComponent(batEntity, batWidth, batHeight, false, false,
                Factory.createResetScreenAction());
        Factory.createClickableComponent(screenEntity, width, height, false, false,
                Factory.createPhysicsAction(batPhysics, new Vector2(0, BAT_Y_JUMP)));

        float groundWidth = TextureType.FLAPPY_BAT_GROUND.getWidth();
        float groundHeight = TextureType.FLAPPY_BAT_GROUND.getHeight();
        groundEntities = new ArrayList<Entity>();
        for (int i = 0; i < GROUND_COUNT; i++) {
            Entity groundEntity = Factory.createEntity(i * GROUND_WIDTH, GROUND_Y_OFFSET);
            Factory.createGraphicsComponent(groundEntity, 1, TextureType.FLAPPY_BAT_GROUND);
            Factory.createCollisionComponent(groundEntity, groundWidth, groundHeight, true, false,
                    Factory.createResetScreenAction());
            groundEntities.add(groundEntity);
        }

        float wallWidth = TextureType.FLAPPY_BAT_WALL.getWidth();
        float wallHeight = TextureType.FLAPPY_BAT_WALL.getHeight();
        wallEntities = new ArrayList<Entity>();
        for (int i = 0; i < WALL_COUNT; i++) {
            Entity topWallEntity = Factory.createEntity(screenEntity.position.x + 80 + i * WALL_SPACING, wallRandomY());
            Factory.createGraphicsComponent(topWallEntity, 0, TextureType.FLAPPY_BAT_WALL);
            Factory.createCollisionComponent(topWallEntity, wallWidth, wallHeight, true, false,
                    Factory.createResetScreenAction());
            wallEntities.add(topWallEntity);

            Entity bottomWallEntity = Factory.createEntity(topWallEntity.position.x, topWallEntity.position.y - WALL_GAP);
            Factory.createGraphicsComponent(bottomWallEntity, 0, TextureType.FLAPPY_BAT_WALL);
            Factory.createCollisionComponent(bottomWallEntity, wallWidth, wallHeight, true, false,
                    Factory.createResetScreenAction());
            wallEntities.add(bottomWallEntity);
        }

        Entity scoreEntity =Factory.createEntity(0, -height/2 + 60);
        scoreText = Factory.createFloatingTextComponent(scoreEntity, 1, "Score: ", "",Color.BLACK);

        Entity lowScoreEntity = Factory.createEntity(0, -height/2 + 40);
        lowScoreText = Factory.createFloatingTextComponent(lowScoreEntity, 1, "Low-Score: ", "", Color.BLACK);

        Entity highScoreEntity =Factory.createEntity(0, -height/2 + 20);
        highScoreText = Factory.createFloatingTextComponent(highScoreEntity, 1, "High-Score: ", "", Color.BLACK);
    }

    @Override
    public void render(float delta) {
        if (batEntity.position.y > height) {
            reset();
        }
        PositionManager.getInstance().setPosition(screenEntity.id, new Vector2(batEntity.position.x
                + 80, height/2));
        batPhysics.velocity.x = BAT_X_MOVEMENT;
        batPhysics.velocity.y += GRAVITY * delta;

        updateGroundEntities();
        updateWallEntities();

        batCurrentX = batEntity.position.x;
        score = (int)(batCurrentX - batStartX);
        scoreText.text = score+"";
        scoreText.layout.setText(scoreText.font, scoreText.label + scoreText.text,
            Color.BLACK, 0, Align.left, true);

        super.render(delta);
    }
    
    public float wallRandomY() {
        return random.nextFloat() * WALL_FLUCTUATION + WALL_LOWEST_OPENING;
    }

    public void reset() {
        super.reset();
        if (score > lowScore) {
            highScores.addToHighScores("Test",score);
            lowScore = highScores.getLowScore();
            lowScoreText.text = lowScore+"";
            lowScoreText.layout.setText(lowScoreText.font, lowScoreText.label + lowScoreText.text,
                    Color.BLACK, 0, Align.left, true);
            if (highScore != highScores.getHighScore()) {
                highScore = highScores.getHighScore();
                highScoreText.text = highScore+"";
                highScoreText.layout.setText(highScoreText.font, highScoreText.label + highScoreText.text,
                    Color.BLACK, 0, Align.left, true);
            }
        }
        batEntity.position.set(width/4, height/2);
        PositionManager.getInstance().setPosition(batEntity.id, new Vector2(width/4, height/2));
        batPhysics.velocity.set(0,0);
        for (int i = 0; i < GROUND_COUNT; i++) {
            PositionManager.getInstance().setPosition(groundEntities.get(i).id, new Vector2(
                    i * GROUND_WIDTH, GROUND_Y_OFFSET));
        }
        for (int i = 0; i < WALL_COUNT * 2; i+=2) {
            int topId = wallEntities.get(i).id;
            int bottomId = wallEntities.get(i + 1).id;
            PositionManager.getInstance().setPosition(topId, new Vector2(
                    screenEntity.position.x + 80 + i * WALL_SPACING, wallRandomY()));
            PositionManager.getInstance().setPosition(bottomId, new Vector2(
                    wallEntities.get(i).position.x, wallEntities.get(i).position.y - WALL_GAP));
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
            PositionManager.getInstance().movePosition(resetWalls.get(0).id, WALL_SPACING * WALL_COUNT,
                    wallRandomY());
            PositionManager.getInstance().movePosition(resetWalls.get(1).id, resetWalls.get(0).position.x,
                    resetWalls.get(0).position.y - WALL_GAP);
        }
    }

    private void updateGroundEntities() {
        for (Entity groundEntity : groundEntities) {
            if(screenEntity.position.x - (width / 2) > groundEntity.position.x + GROUND_WIDTH/2) {
                PositionManager.getInstance().movePosition(groundEntity.id, GROUND_WIDTH * GROUND_COUNT, 0);
            }
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