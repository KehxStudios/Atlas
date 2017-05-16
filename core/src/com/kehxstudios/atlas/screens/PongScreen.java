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
import com.kehxstudios.atlas.managers.PhysicsManager;
import com.kehxstudios.atlas.data.ComponentData;
import com.kehxstudios.atlas.components.GraphicsComponent;
import com.kehxstudios.atlas.components.PhysicsComponent;
import com.kehxstudios.atlas.tools.Factory;
import com.kehxstudios.atlas.tools.Templates;
import com.kehxstudios.atlas.type.ScreenType;
import com.kehxstudios.atlas.type.TextureType;

import java.util.Random;

/**
 * The game of Pong, to be functional in the near future
 */

public class PongScreen extends AScreen {

    private final float paddleWidth, paddleHeight, paddleSpeed;
    
    private final float ballWidth, ballHeight, ballSpeed;

    Entity player1Entity, player2Entity, ballEntity;
    PhysicsComponent player1Physics, player2Physics, ballPhysics;

    private int player2Score;
    
    private Random random = new Random();

    public PongScreen() {
        super(ScreenType.PONG);

        paddleWidth = TextureType.PONG_PADDLE.getWidth();
        paddleHeight = TextureType.PONG_PADDLE.getHeight();
        
        ballWidth = TextureType.PONG_BALL.getWidth();
        ballHeight = TextureType.PONG_BALL.getHeight();

        paddleSpeed = 5f;
        ballSpeed = 6f;
    }
    
    public void finalizeSetup() {
        super.finalizeSetup();
        screenGraphics.setTextureType(TextureType.PONG_BACKGROUND);
        screenGraphics.setEnabled(true);

        player2Score = 0;
        
        ComponentData paddleGraphicsData = Templates.graphicsComponentData(0, 0, 3, TextureType.PONG_PADDLE);
        ComponentData paddlePhysicsData = Templates.physicsComponentData(0, paddleSpeed, 0, paddleSpeed);
        
        player1Entity = Factory.createEntity(Templates.createEntityData(paddleWidth, height/2));
        GraphicsComponent player1Graphics = (GraphicsComponent)Factory.createComponent(player1Entity, paddleGraphicsData);
        player1Physics = (PhysicsComponent)Factory.createComponent(player1Entity, paddlePhysicsData);
        
        player2Entity = Factory.createEntity(Templates.createEntityData(width - paddleWidth, height/2));
        GraphicsComponent player2Graphics = (GraphicsComponent)Factory.createComponent(player2Entity, paddleGraphicsData);
        player2Physics = (PhysicsComponent)Factory.createComponent(player2Entity, paddlePhysicsData);
        
        
        ComponentData ballGraphicsData = Templates.graphicsComponentData(0, 0, 3, TextureType.PONG_BALL);
        ComponentData ballPhysicsData = Templates.physicsComponentData(ballSpeed, ballSpeed, ballSpeed, ballSpeed);
        
        ballEntity = Factory.createEntity(Templates.createEntityData(width/2, height/2));
        GraphicsComponent ballGraphics = (GraphicsComponent)Factory.createComponent(ballEntity, ballGraphicsData);
        ballPhysics = (PhysicsComponent)Factory.createComponent(ballEntity, ballPhysicsData);
    }

    @Override
    public void render(float delta) {
        // increase screenTime in super method
        super.render(delta);
    
        if (ballEntity.getPosition().x + ballWidth/2 > width) {
            score++;
            roundReset();
        } else if (ballEntity.getPosition().x - ballWidth/2 < 0) {
            player2Score++;
            roundReset();
        }
        
        if (ballEntity.getPosition().y + ballHeight/2 > height || ballEntity.getPosition().y - ballHeight/2 < 0) {
            ballPhysics.setYVelocity(-ballPhysics.getVelocity().y);
        }
        
        if (player1Entity.getPosition().y + paddleHeight/2 > height) {
            player1Entity.setY(height - paddleHeight/2);
        } else if (player1Entity.getPosition().y - paddleHeight/2 < 0) {
            player1Entity.setY(paddleHeight/2);
        }
        
        if (player2Entity.getPosition().y + paddleHeight/2 > height) {
            player2Entity.setY(height - paddleHeight/2);
        } else if (player2Entity.getPosition().y - paddleHeight/2 < 0) {
            player2Entity.setY(paddleHeight/2);
        }
    }
    
    private void roundReset() {
        ballEntity.setPosition(width/2, height/2);
    }
    
    private void gameReset() {
        roundReset();
        screenTime = 0f;
        score = 0;
        player2Score = 0;
        player1Entity.setPosition(paddleWidth, height/2);
        player1Physics.setVelocity(0,0);
        player2Entity.setPosition(width - paddleWidth, height/2);
        player2Physics.setVelocity(0,0);
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
