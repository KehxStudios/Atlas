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

    Entity bottomPlayerEntity, topPlayerEntity, ballEntity;
    PhysicsComponent bottomPlayerPhysics, topPlayerPhysics, ballPhysics;

    private int bottomPlayerScore, topPlayerScore;
    
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

        bottomPlayerScore = 0;
        topPlayerScore = 0;

        Entity mainMenuLaunchEntity = buildManager.createEntity(50, height-50);
        buildManager.createClickableComponent(mainMenuLaunchEntity, 100, 100, true, false,
                buildManager.createLaunchScreenAction(ScreenType.MAIN_MENU));
        
        bottomPlayerEntity = buildManager.createEntity(width/2, paddleHeight);
        buildManager.createGraphicsComponent(bottomPlayerEntity, 1, TextureType.PONG_PADDLE);
        bottomPlayerPhysics = buildManager.createPhysicsComponent(bottomPlayerEntity, new Vector2(paddleSpeed, 0),
                                                                new Vector2(paddleSpeed, 0));
                                                                
        topPlayerEntity = buildManager.createEntity(width/2, height-paddleHeight);
        buildManager.createGraphicsComponent(topPlayerEntity, 1, TextureType.PONG_PADDLE);
        bottomPlayerPhysics = buildManager.createPhysicsComponent(topPlayerEntity, new Vector2(paddleSpeed, 0),
                                                                new Vector2(paddleSpeed, 0));
                                                                
        ballEntity = buildManager.createEntity(width/2, height/2);
        buildManager.createGraphicsComponent(ballEntity, 1, TextureType.PONG_BALL);
        ballPhysics = buildManager.createPhysicsComponent(ballEntity, new Vector2(ballSpeed, ballSpeed),
                                                          new Vector2(ballSpeed, ballSpeed));
        ballPhysics.velocity.set(2f, 5f);
    }

    @Override
    public void render(float delta) {
        // increase screenTime in super method
        super.render(delta);
        
        if (ballEntity.position.x + ballWidth/2 > width) {
            bottomPlayerScore++;
            roundReset();
        } else if (ballEntity.position.x - ballWidth/2 < 0) {
            topPlayerScore++;
            roundReset();
        }
        
        if (ballEntity.position.y + ballHeight/2 > height || ballEntity.position.y - ballHeight/2 < 0) {
            ballPhysics.velocity.y *= -1;
        }
        
        if (bottomPlayerEntity.position.x + paddleWidth/2 > width) {
            positionManager.setPosition(bottomPlayerEntity.id, new Vector2(width - paddleWidth/2, paddleHeight));
        } else if (bottomPlayerEntity.position.x - paddleWidth/2 < 0) {
            positionManager.setPosition(bottomPlayerEntity.id, new Vector2(paddleWidth/2, paddleHeight));
        }
        
        if (topPlayerEntity.position.x + paddleWidth/2 > width) {
            positionManager.setPosition(topPlayerEntity.id, new Vector2(width - paddleWidth/2, height-paddleHeight));
            topPlayerEntity.position.x = height - paddleHeight/2;
        } else if (topPlayerEntity.position.x - paddleWidth/2 < 0) {
            positionManager.setPosition(topPlayerEntity.id, new Vector2(paddleWidth/2, height-paddleHeight));
        }
    }
    
    private void roundReset() {
        positionManager.setPosition(ballEntity.id, new Vector2(width/2, height/2));
    }
    
    private void gameReset() {
        roundReset();
        screenTime = 0f;
        bottomPlayerScore = 0;
        topPlayerScore = 0;
       
        positionManager.setPosition(bottomPlayerEntity.id, new Vector2(width/2, paddleHeight));
        bottomPlayerPhysics.velocity.set(0,0);
        positionManager.setPosition(topPlayerEntity.id, new Vector2(width/2, height - paddleHeight));
        topPlayerPhysics.velocity.set(0,0);
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
