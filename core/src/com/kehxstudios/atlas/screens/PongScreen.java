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

    Entity player1Entity, player2Entity, ballEntity;
    PhysicsComponent player1Physics, player2Physics, ballPhysics;

    private int score, player2Score;
    
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

        score = 0;
        player2Score = 0;

        Entity mainMenuLaunchEntity = buildManager.createEntity(50, height-50);
        buildManager.createClickableComponent(mainMenuLaunchEntity, 100, 100, true, false,
                buildManager.createLaunchScreenAction(ScreenType.MAIN_MENU));
        /*
        ComponentData paddleGraphicsData = Templates.graphicsComponentData(0, 0, 3, 0, TextureType.PONG_PADDLE);
        ComponentData paddlePhysicsData = Templates.physicsComponentData(0, paddleSpeed, 0, paddleSpeed);
        
        player1Entity = BuildManager.createEntity(Templates.createEntityData(paddleWidth, height/2));
        GraphicsComponent player1Graphics = (GraphicsComponent)BuildManager.createComponent(player1Entity, paddleGraphicsData);
        player1Physics = (PhysicsComponent)BuildManager.createComponent(player1Entity, paddlePhysicsData);
        
        player2Entity = BuildManager.createEntity(Templates.createEntityData(width - paddleWidth, height/2));
        GraphicsComponent player2Graphics = (GraphicsComponent)BuildManager.createComponent(player2Entity, paddleGraphicsData);
        player2Physics = (PhysicsComponent)BuildManager.createComponent(player2Entity, paddlePhysicsData);
        
        
        ComponentData ballGraphicsData = Templates.graphicsComponentData(0, 0, 3, 0, TextureType.PONG_BALL);
        ComponentData ballPhysicsData = Templates.physicsComponentData(ballSpeed, ballSpeed, ballSpeed, ballSpeed);
        
        ballEntity = BuildManager.createEntity(Templates.createEntityData(width/2, height/2));
        GraphicsComponent ballGraphics = (GraphicsComponent)BuildManager.createComponent(ballEntity, ballGraphicsData);
        ballPhysics = (PhysicsComponent)BuildManager.createComponent(ballEntity, ballPhysicsData);
        */
    }

    @Override
    public void render(float delta) {
        // increase screenTime in super method
        super.render(delta);
        /*
        if (ballEntity.position.x + ballWidth/2 > width) {
            score++;
            roundReset();
        } else if (ballEntity.position.x - ballWidth/2 < 0) {
            player2Score++;
            roundReset();
        }
        
        if (ballEntity.position.y + ballHeight/2 > height || ballEntity.position.y - ballHeight/2 < 0) {
            ballPhysics.velocity.y *= -1;
        }
        
        if (player1Entity.position.y + paddleHeight/2 > height) {
            player1Entity.position.y = height - paddleHeight/2;
        } else if (player1Entity.position.y - paddleHeight/2 < 0) {
            player1Entity.position.y = paddleHeight/2;
        }
        
        if (player2Entity.position.y + paddleHeight/2 > height) {
            player2Entity.position.y = height - paddleHeight/2;
        } else if (player2Entity.position.y - paddleHeight/2 < 0) {
            player2Entity.position.y = paddleHeight/2;
        }
        */
    }
    
    private void roundReset() {
        ballEntity.position.set(width/2, height/2);
    }
    
    private void gameReset() {
        roundReset();
        screenTime = 0f;
        score = 0;
        player2Score = 0;
        player1Entity.position.set(paddleWidth, height/2);
        player1Physics.velocity.set(0,0);
        player2Entity.position.set(width - paddleWidth, height/2);
        player2Physics.velocity.set(0,0);
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
