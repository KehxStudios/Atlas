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

import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.kehxstudios.atlas.components.CollisionComponent;
import com.kehxstudios.atlas.components.Component;
import com.kehxstudios.atlas.components.FloatingTextComponent;
import com.kehxstudios.atlas.data.ActionData;
import com.kehxstudios.atlas.actions.PhysicsAction;
import com.kehxstudios.atlas.components.ClickableComponent;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.data.HighScores;
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.tools.Factory;
import com.kehxstudios.atlas.tools.Templates;
import com.kehxstudios.atlas.type.ComponentType;
import com.kehxstudios.atlas.type.TextureType;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.managers.GameManager;
import com.kehxstudios.atlas.tools.DebugTool;
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

        batEntity = Factory.createEntity(width/4, height/2);
        Factory.createGraphicsComponent(batEntity, 2, TextureType.FLAPPY_BAT_BAT);
        Factory.createPhysicsComponent(batEntity, new Vector2(100, 300), new Vector2(100,300));


        ActionData batPhysicsAction = Templates.physicsActionData(0, BAT_Y_JUMP);
        ComponentData batCollisionData = Templates.collisionComponentData(TextureType.FLAPPY_BAT_BAT.getWidth(),
                TextureType.FLAPPY_BAT_BAT.getHeight(), false, false, null);
        Factory.createComponent(batEntity, batCollisionData);
        ComponentData batClickableData = Templates.clickableComponentData(width, height, false, batPhysicsAction);
        ClickableComponent batClickable = (ClickableComponent)Factory.createComponent(screenEntity, batClickableData);
        ((PhysicsAction)batClickable.action).physicsComponent = batPhysics;

        ComponentData groundGraphicsData = Templates.graphicsComponentData(0, 0, 2, 0, TextureType.FLAPPY_BAT_GROUND);
        ComponentData groundCollisionData = Templates.collisionComponentData(TextureType.FLAPPY_BAT_GROUND.getWidth(),
                TextureType.FLAPPY_BAT_GROUND.getHeight(), true, false, null);

        groundEntities = new ArrayList<Entity>();

        for (int i = 0; i < GROUND_COUNT; i++) {
            Entity groundEntity = Factory.createEntity(Templates.createEntityData(i * GROUND_WIDTH,GROUND_Y_OFFSET));
            Factory.createComponent(groundEntity, groundGraphicsData);
            Factory.createComponent(groundEntity, groundCollisionData);
            groundEntities.add(groundEntity);
        }

        ComponentData wallGraphicsData = Templates.graphicsComponentData(0, 0, 1, 0, TextureType.FLAPPY_BAT_WALL);
        ComponentData wallCollisionData = Templates.collisionComponentData(TextureType.FLAPPY_BAT_WALL.getWidth(),
                TextureType.FLAPPY_BAT_WALL.getHeight(), true, false, null);

        wallEntities = new ArrayList<Entity>();

        for (int i = 0; i < WALL_COUNT; i++) {
            Entity wallEntity = Factory.createEntity(Templates.createEntityData(
                    screenEntity.position.x + 80 + i * WALL_SPACING, wallRandomY()));

            GraphicsComponent wallTopGraphics = (GraphicsComponent)Factory.createComponent(wallEntity, wallGraphicsData);

            GraphicsComponent wallBottomGraphics = (GraphicsComponent)Factory.createComponent(wallEntity, wallGraphicsData);

            CollisionComponent wallTopCollision= (CollisionComponent)Factory.createComponent(wallEntity, wallCollisionData);

            CollisionComponent wallBottomCollision= (CollisionComponent)Factory.createComponent(wallEntity, wallCollisionData);

            wallEntities.add(wallEntity);
        }

        scoreText = (FloatingTextComponent)Factory.createComponent(screenEntity,
                Templates.floatingTextComponentData(0, -height/2 +60, "Score: ", score+"", 1));

        lowScoreText = (FloatingTextComponent)Factory.createComponent(screenEntity,
                Templates.floatingTextComponentData(0, -height/2 + 40, "Low-Score: ", lowScore+"", 1));

        highScoreText = (FloatingTextComponent)Factory.createComponent(screenEntity,
                Templates.floatingTextComponentData(0, -height/2 + 20, "High-Score: ", highScore+"", 1));
    }

    @Override
    public void render(float delta) {
        if (batEntity.position.y > height) {
            reset();
        }

        screenEntity.position.set(batEntity.position.x + 80, height/2);
        batPhysics.velocity.x = BAT_X_MOVEMENT;
        batPhysics.velocity.y += GRAVITY;

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
        batPhysics.velocity.set(0,0);
        for (int i = 0; i < GROUND_COUNT; i++) {
            groundEntities.get(i).position.set(i * GROUND_WIDTH, GROUND_Y_OFFSET);
            //((CollisionComponent)groundEntities.get(i).getComponentOfType(ComponentType.COLLISION)).updateBounds();
        }
        updateGroundEntities();
        for (int i = 0; i < WALL_COUNT; i++) {
            wallEntities.get(i).position.set(screenEntity.position.x + 80 + i * WALL_SPACING, wallRandomY());
            //for (Component component : wallEntities.get(i).getAllComponentsOfType(ComponentType.COLLISION)) {
            //    ((CollisionComponent)component).updateBounds();
            //}
        }
        updateWallEntities();

        batStartX = batEntity.position.x;
        batCurrentX = batStartX;
        score = 0;
    }

    private void updateWallEntities() {
        for (Entity wallEntity : wallEntities) {
            /*
            if (screenEntity.getPosition().x - width / 2 > wallEntity.getPosition().x + WALL_WIDTH/2) {
                wallEntity.setPosition(wallEntity.getPosition().x + WALL_SPACING * WALL_COUNT, wallRandomY());
                for (Component component : wallEntity.getAllComponentsOfType(ComponentType.COLLISION)) {
                    ((CollisionComponent)component).updateBounds();
                }
            }
            */
        }
    }

    private void updateGroundEntities() {
        for (Entity groundEntity : groundEntities) {
            /*
            if(screenEntity.getPosition().x - (width / 2) > groundEntity.getPosition().x + GROUND_WIDTH/2) {
                groundEntity.movePosition(GROUND_WIDTH * GROUND_COUNT, 0);
                ((CollisionComponent)groundEntity.getComponentOfType(ComponentType.COLLISION)).updateBounds();
            }
            */
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