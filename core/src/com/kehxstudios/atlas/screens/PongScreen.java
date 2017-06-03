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

import com.badlogic.gdx.math.Vector2;
import com.kehxstudios.atlas.entities.Entity;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.type.ScreenType;
import com.kehxstudios.atlas.type.TextureType;

import java.util.Random;

/**
 * The game of Pong, to be functional in the near future
 */

public class PongScreen extends AScreen {

    private float paddleWidth, paddleHeight, paddleSpeed;
    
    private float ballWidth, ballHeight, ballSpeed;

    Entity leftPlayerEntity, rightPlayerEntity, ballEntity;
    PhysicsComponent leftPlayerPhysics, rightPlayerPhysics, ballPhysics;

    private int leftPlayerScore, rightPlayerScore;
    
    private Random random = new Random();

    public PongScreen() {
        super(ScreenType.PONG);
        init();
    }
    
    protected void init() {
        super.init();

        paddleWidth = TextureType.PONG_PADDLE.getWidth();
        paddleHeight = TextureType.PONG_PADDLE.getHeight();

        ballWidth = TextureType.PONG_BALL.getWidth();
        ballHeight = TextureType.PONG_BALL.getHeight();

        paddleSpeed = 5f;
        ballSpeed = 6f;

        leftPlayerScore = 0;
        rightPlayerScore = 0;

        Entity mainMenuLaunchEntity = buildManager.createEntity(50, height-50);
        buildManager.createClickableComponent(mainMenuLaunchEntity, 100, 100, true, false,
                buildManager.createLaunchScreenAction(ScreenType.MAIN_MENU));
        
        leftPlayerEntity = buildManager.createEntity(paddleWidth, height/2);
        buildManager.createGraphicsComponent(leftPlayerEntity, 1, TextureType.PONG_PADDLE);
        leftPlayerPhysics = buildManager.createPhysicsComponent(leftPlayerEntity, new Vector2(0, paddleSpeed),
                                                                new Vector2(0, paddleSpeed));
                                                                
        rightPlayerEntity = buildManager.createEntity(width-paddleWidth, height/2);
        buildManager.createGraphicsComponent(rightPlayerEntity, 1, TextureType.PONG_PADDLE);
        leftPlayerPhysics = buildManager.createPhysicsComponent(rightPlayerEntity, new Vector2(0, paddleSpeed),
                                                                new Vector2(0, paddleSpeed));
                                                                
        ballEntity = buildManager.createEntity(width/2, height/2);
        buildManager.createGraphicsComponent(ballEntity, 1, TextureType.PONG_BALL);
        ballPhysics = buildManager.createPhysicsComponent(ballEntity, new Vector2(ballSpeed, ballSpeed),
                                                          new Vector2(ballSpeed, ballSpeed));
    }

    @Override
    public void render(float delta) {
        // increase screenTime in super method
        super.render(delta);
        
        if (ballEntity.position.x + ballWidth/2 > width) {
            leftPlayerScore++;
            roundReset();
        } else if (ballEntity.position.x - ballWidth/2 < 0) {
            rightPlayerScore++;
            roundReset();
        }
        
        if (ballEntity.position.y + ballHeight/2 > height || ballEntity.position.y - ballHeight/2 < 0) {
            ballPhysics.velocity.y *= -1;
        }
        
        if (leftPlayerEntity.position.y + paddleHeight/2 > height) {
            positionManager.setPosition(leftPlayerEntity.id, new Vector2(paddleWidth, height- paddleHeight/2));
        } else if (leftPlayerEntity.position.y - paddleHeight/2 < 0) {
            positionManager.setPosition(leftPlayerEntity.id, new Vector2(paddleWidth, paddleHeight/2));
        }
        
        if (rightPlayerEntity.position.y + paddleHeight/2 > height) {
            positionManager.setPosition(rightPlayerEntity.id, new Vector2(width-paddleWidth, height- paddleHeight/2));
            rightPlayerEntity.position.y = height - paddleHeight/2;
        } else if (rightPlayerEntity.position.y - paddleHeight/2 < 0) {
            positionManager.setPosition(rightPlayerEntity.id, new Vector2(width-paddleWidth, paddleHeight/2));
        }
    }
    
    private void roundReset() {
        positionManager.setPosition(ballEntity.id, new Vector2(width/2, height/2));
    }
    
    private void gameReset() {
        roundReset();
        screenTime = 0f;
        leftPlayerScore = 0;
        rightPlayerScore = 0;
       
        positionManager.setPosition(leftPlayerEntity.id, new Vector2(paddleWidth, height/2));
        leftPlayerPhysics.velocity.set(0,0);
        positionManager.setPosition(rightPlayerEntity.id, new Vector2(paddleWidth, height/2));
        rightPlayerPhysics.velocity.set(0,0);
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
